package com.api.test.PUT;

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

public class UpdateUserWithPutCallWithPojo {
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
        UserLombok user1 =   UserLombok.builder()
                .name("OmkarShendgeLabs")
                .email(getRandomEmailId())
                .gender("male")
                .status("active").build();


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
        Assert.assertEquals(actUser.getStatus(),user1.getStatus() );
        Assert.assertEquals(actUser.getName(), user1.getName());
        Assert.assertEquals(actUser.getEmail(), user1.getEmail());
        Assert.assertNotNull(actUser.getId());

        String user1Id = actUser.getId();
        System.out.println("New user id is: " + user1Id);

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
//PUT CALL
        user1.setStatus("inactive");
        user1.setName("OmkarnitinShendgeONS");

        System.out.println("------------------------------------");
        APIResponse apiPutResponse = requestContext.put("https://gorest.co.in/public/v2/users/"+ user1Id, RequestOptions.create()
                .setHeader("Content-type", "application/json")
                .setHeader("Authorization", "Bearer cc2edcce80da3354682e3443eb2df0e0615f1a2007cae0a24bf613b5b1cefe29")
                .setData(user1));
        System.out.println(apiPutResponse.status() + ":" + apiPutResponse.statusText());
        Assert.assertEquals(apiPutResponse.status(), 200);

        System.out.println("Updated user" + apiPutResponse.text());
        UserLombok actputUser = objectMapper.readValue(apiPutResponse.text(),UserLombok.class);
        Assert.assertEquals(actputUser.getId(),user1Id);
        Assert.assertEquals(actputUser.getStatus(),user1.getStatus() );
        Assert.assertEquals(actputUser.getName(),user1.getName());

        // Get the updates with the get call
        APIResponse getcall = requestContext.get("https://gorest.co.in/public/v2/users/"+ user1Id,RequestOptions.create()
                .setHeader("Authorization","Bearer cc2edcce80da3354682e3443eb2df0e0615f1a2007cae0a24bf613b5b1cefe29"));
        int status = getcall.status();
        System.out.println(status);
        Assert.assertEquals(status,200);
        Assert.assertEquals(getcall.ok(),true);
        String statusResponseText = getcall.text();
        System.out.println(statusResponseText);

    }

}
