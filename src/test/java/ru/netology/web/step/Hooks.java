package ru.netology.web.step;

import com.codeborne.selenide.Configuration;
import cucumber.api.java.Before;

public class Hooks {
    @Before
    public void setUp() {
        Configuration.headless = true;
    }
}
