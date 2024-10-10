package com.api.test;

import com.microsoft.playwright.*;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class APIDispose {
    Playwright playwright;
    APIRequest apiRequest;
    APIRequestContext requestContext;

    @BeforeTest
    public void beforeTest() {
        playwright = Playwright.create();
        apiRequest = playwright.request();
        requestContext = apiRequest.newContext();
    }
    @Test
    public void disposeAPIResponse() {
        APIResponse apiResponse = requestContext.get("https://gorest.co.in/public/v2/posts");
        int statuscode = apiResponse.status();
        System.out.println(statuscode);
        Assert.assertEquals(statuscode, 200);
        Assert.assertEquals(apiResponse.ok(),true);
        String text = apiResponse.statusText();
        System.out.println(text);
        System.out.println(apiResponse.text());

        apiResponse.dispose(); // will only dispose the body and not other stuff
        try{
            System.out.println(apiResponse.text());
        }
        catch (PlaywrightException e) {
            System.out.println(e.getMessage());
        }
        int statuscode1 = apiResponse.status();
        System.out.println(statuscode1);
        System.out.println(apiResponse.statusText());
        System.out.println(apiResponse.url());

        // requestContext dispose
        APIResponse apiResponse1 = requestContext.get("https://reqres.in/api/users?page=2");
        System.out.println(apiResponse1.status());
        System.out.println(apiResponse1.text());
        requestContext.dispose();

        System.out.println(apiResponse1.text());
        System.out.println(apiResponse.text());
    }

    @AfterTest
    public void afterTest() {
        playwright.close();
    }
}
