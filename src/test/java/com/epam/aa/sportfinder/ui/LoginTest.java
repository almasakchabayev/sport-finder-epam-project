package com.epam.aa.sportfinder.ui;

import org.junit.Before;
import org.junit.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import static com.codeborne.selenide.WebDriverRunner.url;
import static org.junit.Assert.*;

public class LoginTest {
    @Before
    public void openLoginPage() {
        open("/login");
    }

    @Test
    public void userSeesLoginForm() {
        $("#form-login-account").shouldBe(visible);
    }

    @Test
    public void ifNotCorrectCredentialsShouldBeRedirectedToTheSamePageWithErrorMessage() {
        $("#form-login-email").val("cg@gmail.com");
        $("#form-login-password").val("0");

        $("#account-submit").click();
        $(".error").shouldBe(visible);
    }

    @Test
    public void ifCorrectCredentialsAndUserIsManagerRedirectToManagerPage() {
        $("#form-login-email").val("cg@gmail.com");
        $("#form-login-password").val("1234");

        $("#account-submit").click();
        assertTrue(url().contains("/manager/items"));
    }
}
