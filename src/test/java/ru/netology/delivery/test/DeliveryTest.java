package ru.netology.delivery.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        int daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        int daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE, firstMeetingDate);
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE, secondMeetingDate);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(), 'Запланировать')]").click();
        $("[data-test-id='success-notification']")
                .shouldBe(visible, text("успешно запланирована"));
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE, firstMeetingDate);
        $x("//*[contains(text(), 'Запланировать')]").click();
        $("[data-test-id='replan-notification']")
                .shouldBe(visible, text("У вас уже"));
        $("[data-test-id='replan-notification'] button").click();
        $("[data-test-id='success-notification']")
                .shouldBe(visible, text("успешно запланирована"));
    }
}