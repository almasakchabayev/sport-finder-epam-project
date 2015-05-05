package com.epam.aa.sportfinder.ui

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import spock.lang.Specification

import static com.codeborne.selenide.Condition.visible
import static com.codeborne.selenide.Selectors.byText
import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.open
import static com.codeborne.selenide.WebDriverRunner.getAndCheckWebDriver

class ManagerRegisterSpec extends Specification {
    def setupSpec() {
        open("/manager/register")
    }

    def "user should see errors if fields left blank"() {
        when:
            WebDriver webdriver = getAndCheckWebDriver()
            JavascriptExecutor js = (JavascriptExecutor) webdriver
            js.executeScript("document.getElementById('form-register').setAttribute('novalidate', 'novalidate')")
            $("#account-submit").click()

        then:
            $(byText("first name cannot be blank")).shouldBe(visible)
            $(byText("last name cannot be blank")).shouldBe(visible)
            $(byText("email cannot be blank")).shouldBe(visible)
            $(byText("phone number cannot be empty")).shouldBe(visible)
            $(byText("password must contain at least 4 characters")).shouldBe(visible)
            $(byText("company name cannot be blank")).shouldBe(visible)
            $(byText("country name cannot be blank")).shouldBe(visible)
            $(byText("city cannot be blank")).shouldBe(visible)
            $(byText("address line 1 cannot be blank")).shouldBe(visible)
            $(byText("address line 2 cannot be blank")).shouldBe(visible)
            $(byText("zipcode cannot be blank")).shouldBe(visible)
    }

    def "user should see error if password and confirm-password are not the same"() {
        when:
            $("#form-register-first-name").val("Almas")
            $("#form-register-last-name").val("Akchabayev")
            $("#form-register-email").val("almas.akchabayev@gmail.com")
            $("#form-register-phone-number").val("87017511143")
            $("#form-register-phone-number-2").val("87272920973")
            $("#form-register-password").val("1234")
            $("#form-register-confirm-password").val("123")
            $("#form-register-company-name").val("Almas Ltd")
            $("#form-register-company-country").val("Kazakhstan")
            $("#form-register-company-city").val("Almaty")
            $("#form-register-company-address-line-1").val("Amangeldy 72")
            $("#form-register-company-address-line-2").val("1")
            $("#form-register-company-zipcode").val("050012")
            $("#account-submit").click()

        then:
            $(byText("password and confirm password are not the same")).shouldBe(visible)
    }

    def "if everything is ok, manager should be redirected to /manager/items page"() {
        when:
            $("#form-register-first-name").val("Almas")
            $("#form-register-last-name").val("Akchabayev")
            $("#form-register-email").val("almas.akchabayev@gmail.com")
            $("#form-register-phone-number").val("87017511143")
            $("#form-register-phone-number-2").val("87272920973")
            $("#form-register-password").val("1234")
            $("#form-register-confirm-password").val("1234")
            $("#form-register-company-name").val("Almas Ltd")
            $("#form-register-company-country").val("Kazakhstan")
            $("#form-register-company-city").val("Almaty")
            $("#form-register-company-address-line-1").val("Amangeldy 72")
            $("#form-register-company-address-line-2").val("1")
            $("#form-register-company-zipcode").val("050012")
            $("#account-submit").click()

        then:
            $(byText("Almas Akchabayev")).shouldBe(visible)
    }
}