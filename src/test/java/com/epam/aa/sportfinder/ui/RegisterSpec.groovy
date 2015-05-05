package com.epam.aa.sportfinder.ui

import spock.lang.Specification

import static com.codeborne.selenide.Condition.visible
import static com.codeborne.selenide.Selectors.byText
import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.open

class RegisterSpec extends Specification {
    def setupSpec() {
        open("/register")
    }

    def "user should see login form"() {
        expect:
            $(byText("Register As")).displayed
    }

    def "when manager link is clicked observe redirected to /manager/register"() {
        when:
            $(byText("Manager")).parent().click()

        then:
            $("#account-submit").shouldBe(visible)
    }
}
