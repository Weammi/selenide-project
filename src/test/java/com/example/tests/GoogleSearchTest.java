package com.example.tests;

import com.example.page.GooglePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.open;

class GoogleSearchTest {

    @BeforeAll
    static void setup() {
        WebDriverManager.chromedriver().setup();
    }

    @Test
    void testGoogleSearch() {
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
