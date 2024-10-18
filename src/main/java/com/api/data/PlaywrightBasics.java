package com.api.data;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PlaywrightBasics {
    public static void main(String[] args) {
        Playwright playwright =  Playwright.create();
       Browser browser =  playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
       Page page = browser.newPage();
       page.navigate("https://playwright.com/");
       String title = page.title();
       System.out.println(title);
        String url = page.url();
        System.out.println(url);

        browser.close();
        playwright.close();

    }
}
