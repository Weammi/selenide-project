package com.example.tests;

import com.codeborne.selenide.Configuration;
import com.example.page.GooglePage;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.open;

class GoogleSearchTest {

    @Test
    void testGoogleSearch() {
        // Явно указываем путь к ChromeDriver
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        Configuration.browser = "chrome";
        Configuration.headless = true;

        GooglePage googlePage = new GooglePage();

        open("https://google.com");

        googlePage.searchInput().setValue("Selenide");
        googlePage.searchButton().click();

        googlePage.results().shouldHave(text("selenide.org"));
    }

    @Test
    void testGoogleSearch1() {
        GooglePage googlePage = new GooglePage();

        open("https://google.com");

        googlePage.searchInput().setValue("Selenide");
        googlePage.searchButton().click();

        googlePage.results().shouldHave(text("selenide.org"));
    }

    @Test
    void testGoogleSearch2() {
        GooglePage googlePage = new GooglePage();

        open("https://google.com");

        googlePage.searchInput().setValue("Selenide");
        googlePage.searchButton().click();

        googlePage.results().shouldHave(text("selenide.org"));
    }
}
