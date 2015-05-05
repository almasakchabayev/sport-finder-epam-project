package com.epam.aa.sportfinder.ui

import spock.lang.Specification

import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.open
import static com.codeborne.selenide.WebDriverRunner.url

class LoginSpec extends Specification {
    def setupSpec() {
        open("/login")
    }

    def "user should see login form"() {
        expect:
            $("#form-login-account").displayed
    }

    def "when user types in incorrect credentials should be shown an error message"() {
        when:
            $("#form-login-email").val("cg@gmail.com")
            $("#form-login-password").val("0")
            $("#account-submit").click()

        then:
            $(".error").displayed
    }

    def "when user types in correct credentials and he is a manager should be redirected to /manager/items page"() {
        when:
            $("#form-login-email").val("cg@gmail.com")
            $("#form-login-password").val("1234")
            $("#account-submit").click()

        then:
            url().contains("/manager/items")
    }
}
