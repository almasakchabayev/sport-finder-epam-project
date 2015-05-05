package com.epam.aa.sportfinder.ui

import spock.lang.Specification

import static com.codeborne.selenide.Condition.visible
import static com.codeborne.selenide.Selectors.byText
import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.open

class ManagerItemsSpec extends Specification{
    def setupSpec() {
        open("/login")
        $("#form-login-email").val("cg@gmail.com")
        $("#form-login-password").val("1234")
        $("#account-submit").click()
    }

    def "user should be able to see his name and items or message about not having any items"() {
        expect:
            $(byText("Chris Griffin")).shouldBe(visible)
            $(".item").displayed
    }

    def "clicking submit will redirect to item submit page"() {
        when:
            $(".submit-item").click()

        then:
            $(byText("Submit Sport Place")).shouldBe(visible)
    }
}
