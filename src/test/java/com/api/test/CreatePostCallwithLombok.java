package com.api.test;

import com.api.data.User;
import com.api.data.UserLombok;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class CreatePostCallwithLombok {
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
// create User object
//        User user = new User("OmkarSDET",getRandomEmailId(),"male","active");
// create userslombok object using builder pattern
      UserLombok user1 =   UserLombok.builder().name("OmkarShendge").email(getRandomEmailId()).gender("male").status("active").build();

        APIResponse apiPostResponse = requestContext.post("https://gorest.co.in/public/v2/users", RequestOptions.create()
                .setHeader("Content-type", "application/json")
                .setHeader("Authorization", "Bearer cc2edcce80da3354682e3443eb2df0e0615f1a2007cae0a24bf613b5b1cefe29")
                .setData(user1));
        System.out.println(apiPostResponse.status());
        Assert.assertEquals(apiPostResponse.status(), 201);
        Assert.assertEquals(apiPostResponse.statusText(), "Created");
        String responseText = apiPostResponse.text();
        System.out.println(responseText);

        ObjectMapper objectMapper = new ObjectMapper();
        User actUser = objectMapper.readValue(responseText,User.class);
        System.out.println(actUser);
        System.out.println(actUser.getEmail());
        Assert.assertEquals(actUser.getName(), user1.getName());
        Assert.assertEquals(actUser.getEmail(), user1.getEmail());
        Assert.assertNotNull(actUser.getId());
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode postJsonresponsenode = mapper.readTree(apiPostResponse.body());
//        System.out.println(postJsonresponsenode.toPrettyString());
//        String userId =postJsonresponsenode.get("id").asText();
//        System.out.println(userId);
//
//        // GET call the for user by his id
//        APIResponse apiGetresponse = requestContext.get("https://gorest.co.in/public/v2/users/" + userId,RequestOptions.create()
//                .setHeader("Authorization","Bearer cc2edcce80da3354682e3443eb2df0e0615f1a2007cae0a24bf613b5b1cefe29"));
//        Assert.assertEquals(apiGetresponse.status(), 200);
//        Assert.assertEquals(apiGetresponse.statusText(), "OK");
//        System.out.println(apiGetresponse.text());
//        Assert.assertTrue(apiGetresponse.text().contains(userId));
//        Assert.assertTrue(apiGetresponse.text().contains("OmkarNOMSShendge"));
//        Assert.assertTrue(apiGetresponse.text().contains(emailId));

    }


}
