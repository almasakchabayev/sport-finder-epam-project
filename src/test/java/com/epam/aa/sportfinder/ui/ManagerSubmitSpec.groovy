package com.epam.aa.sportfinder.ui

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import spock.lang.Specification

import static com.codeborne.selenide.Selectors.byText
import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.open
import static com.codeborne.selenide.WebDriverRunner.getAndCheckWebDriver

class ManagerSubmitSpec extends Specification {
    def setupSpec() {
        open("/login")
        $("#form-login-email").val("cg@gmail.com")
        $("#form-login-password").val("1234")
        $("#account-submit").click()
        $(".submit-item").click()
    }

    def "when fields are blank should show errors"() {
        when:
            WebDriver webdriver = getAndCheckWebDriver()
            JavascriptExecutor js = (JavascriptExecutor) webdriver
            js.executeScript("document.getElementById('form-submit').setAttribute('novalidate', 'novalidate')")
            $("#submit").click()

        then:
            $(byText("please specify size")).shouldBe(visible)
            $(byText("please specify capacity")).shouldBe(visible)
            $(byText("please specify price")).shouldBe(visible)
            $(byText("Please specify at least one sport")).shouldBe(visible)
            $(byText("country name cannot be blank")).shouldBe(visible)
            $(byText("city cannot be blank")).shouldBe(visible)
            $(byText("address line 1 cannot be blank")).shouldBe(visible)
            $(byText("address line 2 cannot be blank")).shouldBe(visible)
            $(byText("zipcode cannot be blank")).shouldBe(visible)
    }

    def "when form is valid manager must be redirected to /manager/items"() {
        when:
        $("#description").val("Awesome sport place")
        $("#country").val("Kazakhstan")
        $("#city").val("Almaty")
        $("#address-line-1").val("Amangeldy 72")
        $("#address-line-2").val("1")
        $("#zipcode").val("050012")
        $("#size").val("100x100 sq. m")
        $(By.xpath("/html/body/div/div/div[2]/div/section/div/div[1]/form/section[4]/div[1]/div[2]/div/div[1]/div/div/button")).click()
        $(By.xpath("/html/body/div/div/div[2]/div/section/div/div[1]/form/section[4]/div[1]/div[2]/div/div[1]/div/div/div/ul/li[1]/a")).click()
        $("#capacity").val("22")
        $("#price").val("10000")
        $(By.xpath("/html/body/div/div/div[2]/div/section/div/div[1]/form/section[4]/div[2]/div[1]/div/div/button")).click()
        $(By.xpath("/html/body/div/div/div[2]/div/section/div/div[1]/form/section[4]/div[2]/div[1]/div/div/div/ul/li[1]/a")).click()
        $(By.xpath("/html/body/div/div/div[2]/div/section/div/div[1]/form/section[4]/div[2]/div[1]/div/div/div/ul/li[2]/a")).click()
        $(By.xpath("/html/body/div/div/div[2]/div/section/div/div[1]/form/section[4]/div[2]/div[1]/div/div/button")).click()
        $("#tribune-capacity").val("1000")
//            $(By.name("changing-room")).click()
        $(By.xpath("/html/body/div/div/div[2]/div/section/div/div[1]/form/section[5]/ul/li[1]/div/label/div")).click()
//            $(By.name("shower")).click()
        $(By.xpath("/html/body/div/div/div[2]/div/section/div/div[1]/form/section[5]/ul/li[2]/div/label/div")).click()
//            $(By.name("lightening")).click()
        $(By.xpath("/html/body/div/div/div[2]/div/section/div/div[1]/form/section[5]/ul/li[3]/div/label/div")).click()
        $("#other-infrastructure-features").val("Parking available")
        $("#submit").click()

        then:
        //todo add check that this item appeared in manager/items
        $("#items").shouldBe(visible)
    }

    def "when errors exist fields must be populated with previously submitted values"() {
        when:
        $("#description").val("Awesome sport place")
        $("#city").val("Almaty")
        $("#address-line-1").val("Amangeldy 72")
        $("#address-line-2").val("1")
        $("#zipcode").val("050012")
        $("#size").val("100x100 sq. m")
        $(By.xpath("/html/body/div/div/div[2]/div/section/div/div[1]/form/section[4]/div[1]/div[2]/div/div[1]/div/div/button")).click()
        $(By.xpath("/html/body/div/div/div[2]/div/section/div/div[1]/form/section[4]/div[1]/div[2]/div/div[1]/div/div/div/ul/li[1]/a")).click()
        $("#capacity").val("22")
        $("#price").val("10000")
        $(By.xpath("/html/body/div/div/div[2]/div/section/div/div[1]/form/section[4]/div[2]/div[1]/div/div/button")).click()
        $(By.xpath("/html/body/div/div/div[2]/div/section/div/div[1]/form/section[4]/div[2]/div[1]/div/div/div/ul/li[1]/a")).click()
        $(By.xpath("/html/body/div/div/div[2]/div/section/div/div[1]/form/section[4]/div[2]/div[1]/div/div/div/ul/li[2]/a")).click()
        $(By.xpath("/html/body/div/div/div[2]/div/section/div/div[1]/form/section[4]/div[2]/div[1]/div/div/button")).click()
        $("#tribune-capacity").val("1000")
//            $(By.name("changing-room")).click()
        $(By.xpath("/html/body/div/div/div[2]/div/section/div/div[1]/form/section[5]/ul/li[1]/div/label/div")).click()
//            $(By.name("shower")).click()
        $(By.xpath("/html/body/div/div/div[2]/div/section/div/div[1]/form/section[5]/ul/li[2]/div/label/div")).click()
//            $(By.name("lightening")).click()
        $(By.xpath("/html/body/div/div/div[2]/div/section/div/div[1]/form/section[5]/ul/li[3]/div/label/div")).click()
        $("#other-infrastructure-features").val("Parking available")
        WebDriver webdriver = getAndCheckWebDriver()
        JavascriptExecutor js = (JavascriptExecutor) webdriver
        js.executeScript("document.getElementById('form-submit').setAttribute('novalidate', 'novalidate')")
        $("#submit").click()

        then:
        $("#description").shouldHave("Awesome sport place")
        $("#city").shouldHave("Almaty")
        $("#address-line-2").shouldHave("1")
        $("#zipcode").shouldHave("050012")
        $("#size").shouldHave("100x100 sq. m")
        $(By.xpath("/html/body/div/div/div[2]/div/section/div/div[1]/form/section[4]/div[1]/div[2]/div/div[1]/div/div/button/span[1]")).shouldHave("artificial grass")
        $("#capacity").shouldHave("1")
        $("#price").shouldHave("10000")
        // todo fix test
//        $(By.xpath("/html/body/div/div/div[2]/div/section/div/div[1]/form/section[4]/div[2]/div[1]/div/div/div/ul/li[1]")).shouldHave(cssClass("selected"))
//        $(By.xpath("/html/body/div/div/div[2]/div/section/div/div[1]/form/section[4]/div[2]/div[1]/div/div/div/ul/li[2]")).shouldHave(cssClass("selected"))
        $("#tribune-capacity").shouldHave("10000")
        $("#other-infrastructure-features").shouldHave("Parking available")
        $(byText("country name cannot be blank")).shouldBe(visible)
    }
}
