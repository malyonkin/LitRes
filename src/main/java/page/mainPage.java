package page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class mainPage {
    public static SelenideElement
                                    //Element Header
                                    //Block Banner
                                    topBanner = $(".abonement_banner"), //allPage end not page/URL - https://www.litres.ru/abonement/
                                    //Block TopLine
                                    logoReternMainPage = $(".litres_header_logo"), //allPage
                                    search = $(By.name("q")), //allPage
                                    searchButton = $(By.id("go")), //allPage
                                    userProfile = $(By.className("user_container")), //allPage page/URL - https://www.litres.ru/pages/personal_cabinet_login/
                                    notification = $(".user_info"), //allPage List
                                    historySearch = $(".btn__content"), //allPage page/URL - https://www.litres.ru/pages/my_books_all/
                                        historyList = $(".icon-container"), //Include into button "historySearch" allPage
                                    //Block Menu
                                    menuCompany = $$(".header_menu_item").get(1), //allPage page/URL - https://www.litres.ru/o-kompanii/

                                    //Element Body
                                    //Block leftSight
                                    //Block center
                                    //Block rightSight
                                    //Block history
                                    widgetSidebarHistory = $(".ViewedBooksWidget_ControlWrap"), //allPage
                                    //Function infinityAuto - autogenerate new doc - https://www.litres.ru/page-2/
                                    //Block infinityButton
                                    moreContent = $(".loader_button"),

                                    //Element Footer
                                    //Section contacts
                                    contact = $(byText("Контакты")),
                                    //Section normativeDocuments
                                    coupon = $(byText("Активировать купон")),
                                    //Section cooperation
                                    publishingHouses = $(byText("Издательствам")),
                                    //Section WhatReading
                                    bestsellers = $(byText("Бестселлеры")),
                                    //Section socialNetworks
                                    vk = $$(".footer_soc_icon").get(0),
                                    ok = $$(".footer_soc_icon").get(1),
                                    //Block Partners
                                    aeroflot = $(".partners__item-logo_aeroflot"),
                                    ozon = $$(".partners__item-logo").get(1),

                                    //Element Another
                                    pushWindow = $(".adult-content-agreement"), //Книги для взрослых. Вы согласны видеть книги с элементами эротики?
                                    sadsa = $(By.id("cookie-agreement")); //Мы используем куки-файлы, чтобы вы могли быстрее и удобнее пользоваться сайтом

    @Step
    public mainPage open() {
        Selenide.open("https://www.litres.ru");
        return this;
    }
    @Step
    public mainPage buttonInter() {
        userProfile.click();
        return this;
    }
}
