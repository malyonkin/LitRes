package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class pageBook {
    public static ElementsCollection ratingNumberClick = $$(".rating-text-wrapper");
    public static SelenideElement ratingValue = $("#user-rating-star-3");

    public static ElementsCollection resultRating = $$(".rating-text-wrapper");

    @Step("Open page book")
    public pageBook open() {
        Selenide.open("https://www.litres.ru/boris-akunin/azazel-spektakl-279062/?rmd=1");
        return this;
    }
    @Step ("activeRatingForm")
    public pageBook activeRatingForm(int index) {
        ratingNumberClick.get(index).click();
        return this;
    }
    @Step ("Выставляем рейтинг")
    public void ratingChose() {
        ratingValue.click();
    }
    @Step
    public pageBook shouldHaveResultRating(int index, String text) {
        ratingNumberClick.get(index).shouldHave(Condition.text(text).because("invalid stars rating"));
        return this;
    }
}
