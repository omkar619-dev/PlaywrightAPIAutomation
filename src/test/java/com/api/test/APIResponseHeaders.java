package com.api.test;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.HttpHeader;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.List;

public class APIResponseHeaders {
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
    public void headersTest() {
        APIResponse apiResponse = requestContext.get("https://gorest.co.in/public/v2/users");
        int statusCode = apiResponse.status();
        System.out.println(statusCode);
        Assert.assertEquals(statusCode, 200);
        HashMap<String,String> headersmp = (HashMap<String, String>) apiResponse.headers();
        headersmp.forEach((k,v)->System.out.println(k+":"+v));
        System.out.println(headersmp.size());
        Assert.assertEquals(headersmp.size(), 29);
        Assert.assertEquals(headersmp.get("server"),"cloudflare");
        System.out.println("===============================");
        List<HttpHeader> headersList = apiResponse.headersArray();
        for(HttpHeader hp : headersList){
            System.out.println(hp.name + ":" + hp.value);

        }
    }

    @AfterTest
    public void afterTest() {
        playwright.close();
    }
}
