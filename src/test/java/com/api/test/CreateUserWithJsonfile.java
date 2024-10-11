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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

public class CreateUserWithJsonfile {
    Playwright playwright;
    APIRequest apiRequest;
    APIRequestContext requestContext;
    static String emailId;

    @BeforeTest
    public void beforeTest() {
        playwright = Playwright.create();
        apiRequest = playwright.request();
        requestContext = apiRequest.newContext();
    }
    public String getRandomEmailId(){
        emailId = "OmkarnitinShendge" + System.currentTimeMillis() + "@gmail.com";
        return emailId;
    }
    @Test
    public void POSTResponse() throws IOException {
//        HashMap<String, Object> mp = new HashMap<String, Object>();
//        mp.put("name", "OmkarNitinShendge");
//        mp.put("email", getRandomEmailId());
//        mp.put("gender", "male");
//        mp.put("status", "active");
        byte [] fileBytes = null;
        File file = new File("./src/test/data/user.json");
        fileBytes = Files.readAllBytes(file.toPath());

        APIResponse apiPostResponse = requestContext.post("https://gorest.co.in/public/v2/users", RequestOptions.create()
                .setHeader("Content-type", "application/json")
                .setHeader("Authorization", "Bearer cc2edcce80da3354682e3443eb2df0e0615f1a2007cae0a24bf613b5b1cefe29")
                .setData(fileBytes));
        System.out.println(apiPostResponse.status());
        Assert.assertEquals(apiPostResponse.status(), 201);
        Assert.assertEquals(apiPostResponse.statusText(), "Created");
        System.out.println(apiPostResponse.text());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode postJsonresponsenode = mapper.readTree(apiPostResponse.body());
        System.out.println(postJsonresponsenode.toPrettyString());
        String userId =postJsonresponsenode.get("id").asText();
        System.out.println(userId);

        // GET call the for user by his id
        APIResponse apiGetresponse = requestContext.get("https://gorest.co.in/public/v2/users/" + userId,RequestOptions.create()
                .setHeader("Authorization","Bearer cc2edcce80da3354682e3443eb2df0e0615f1a2007cae0a24bf613b5b1cefe29"));
        Assert.assertEquals(apiGetresponse.status(), 200);
        Assert.assertEquals(apiGetresponse.statusText(), "OK");
        System.out.println(apiGetresponse.text());
        Assert.assertTrue(apiGetresponse.text().contains(userId));
        Assert.assertTrue(apiGetresponse.text().contains("Omkar Shendge"));
//        Assert.assertTrue(apiGetresponse.text().contains(emailId));

    }
    @AfterTest
    public void afterTest() {
        playwright.close();
    }
}
