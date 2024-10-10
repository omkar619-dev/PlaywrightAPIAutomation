package com.api.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

public class GETAPI {
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
public void getusers () throws IOException {

    APIResponse apiResponse = requestContext.get("https://gorest.co.in/public/v2/posts");
    int statuscode = apiResponse.status();
    System.out.println(statuscode);
        Assert.assertEquals(statuscode, 200);

    String text = apiResponse.statusText();
    System.out.println(text);
    apiResponse.body();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(apiResponse.body());
        String jsonRes = jsonResponse.toPrettyString();
        System.out.println(jsonRes);
    System.out.println("---URL---");
    System.out.println(apiResponse.url());
    HashMap<String,String> mp = (HashMap<String, String>) apiResponse.headers();
    System.out.println(mp);
    Assert.assertEquals(mp.get("content-type"), "application/json; charset=utf-8");

}
@Test
public void getUsersWithParams () throws IOException {
    APIResponse apiResponse = requestContext.get("https://gorest.co.in/public/v2/posts", RequestOptions.create().setQueryParam("title","Crudelis vulnus crur abduco admoneo delibero carbo accommodo."));
    int statuscode = apiResponse.status();
    System.out.println(statuscode);
    Assert.assertEquals(statuscode, 200);

    String text = apiResponse.statusText();
    System.out.println(text);
//    System.out.println(apiResponse.text());
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonResponse = objectMapper.readTree(apiResponse.body());
    String jsonRes = jsonResponse.toPrettyString();
    System.out.println(jsonRes);

}
@AfterTest
    public void afterTest() {
        playwright.close();
}
}
