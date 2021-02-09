package page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

//Instruction PageObjects - https://selenide.gitbooks.io/user-guide/content/en/pageobjects.html
public class loginPage {
    mainPage mainPage = new mainPage();

    public static SelenideElement //buttonInter = $(By.className("user_container")),
                                    authEmail = $(byText("Электронная почта")),
                                    loginUser = $(By.name("email")),
                                    loginButtonInter = $(byText("Продолжить")),
                                    password = $(By.name("pwd")),
                                    pushReserveInter = $(byText("Резервный")),
                                    closeWindowPushReserveInter = $(By.className("close"));

    @Step
    public loginPage open(String s) {
        Selenide.open("https://www.litres.ru");
        return this;
    }
    @Step
    public void ChoseEmailAuth(){
        //buttonInter.click();
        authEmail.click();
    }
    @Step
    public loginPage enterUsername(String text) {
        loginUser.val(text);
        loginButtonInter.click();
        return this;
    }
    @Step
    public loginPage enterPassword(String text) {
        password.val(text).pressEnter();
        return this;
    }
    @Step
    public void pushReserveWinClose() {
        pushReserveInter.should(appear);
        closeWindowPushReserveInter.click();
    }
    @Step
    public loginPage login(String user, String pwd){
        mainPage.buttonInter();
        authEmail.click();
        loginUser.val(user);
        loginButtonInter.click();
        password.val(pwd).pressEnter();
        pushReserveInter.should(appear);
        closeWindowPushReserveInter.click();
        return this;
    }
    @Step
    public loginPage typeLogin(String user, String pwd){
        mainPage.buttonInter();
        for (int i = 0; i < 9; i++) {
            authEmail.click();
        }
        loginUser.val(user);
        loginButtonInter.click();
        password.val(pwd).pressEnter();
        pushReserveInter.should(appear);
        closeWindowPushReserveInter.click();
        return this;
    }

    public void pageForLogin() {
        Selenide.open("https://www.litres.ru/pages/login/");
    }
}
