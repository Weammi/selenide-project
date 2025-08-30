package com.example.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class GooglePage {

    public SelenideElement searchInput() {
        return $("input[name='q']");
    }

    public SelenideElement searchButton() {
        return $("input[name='btnK']");
    }

    public SelenideElement results() {
        return $("#search");
    }
}
