package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.junit5.TextReportExtension;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import net.joshka.junit.json.params.JsonFileSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.openqa.selenium.By;
import page.loginPage;
import page.mainPage;
import page.pageBook;

import javax.json.JsonObject;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static config.userConfig.*;
import static io.qameta.allure.Allure.step;

@ExtendWith({TextReportExtension.class}) //логирование - https://ru.selenide.org/documentation/reports.html
public class litTest {
    loginPage loginPage = new loginPage();
    pageBook pageBook = new pageBook();
    mainPage pageMain = new mainPage();

    //реализровать API тест для получения token сессии

    /*@BeforeAll
    public static void setUp() {
        String currentBrowser = System.getProperty("selenide.browser", "chrome");
        if ("chrome".equals(currentBrowser)) {
            Configuration.browser = "chrome";
        } else if ("firefox".equals(currentBrowser)) {
            Configuration.browser = "firefox";
        } else if ("safari".equals(currentBrowser)) {
            Configuration.browser = "safari";
        } else if ("edge".equals(currentBrowser)) {
            Configuration.browser = "edge";
        } else if ("opera".equals(currentBrowser)) {
            Configuration.browser = "opera";
        }
    }*/

    @BeforeEach
    public void setup() {
        //loginPage authPage = Selenide.open("https://www.litres.ru/boris-akunin/azazel-spektakl-279062/?rmd=1", loginPage.class);
        //Configuration.baseUrl = "https://www.litres.ru/boris-akunin/azazel-spektakl-279062/?rmd=1";
        Configuration.browserSize = "1366x768";
        Configuration.timeout = 6000;
        Configuration.fastSetValue = true;
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true)); //подключение логирования Selenide к Allure
        Selenide.clearBrowserCookies();
        /*Selenide.clearBrowserLocalStorage();*/
        RestAssured.baseURI = "https://www.litres.ru/pages/ajax_empty2"; //URL API
        RestAssured.useRelaxedHTTPSValidation(); //расслабленной проверки HTTP
        RestAssured.filters(new AllureRestAssured()); //подключение логирования rest assured к Allure
    }

    /*//Используется для авторизации через API. Вынести из теста в main. Пример с json - https://automation-remarks.com/2017/code-generation/index.html
    public Cookie[] APIauthUser() { //Здесь мы берем только куки
        //Set-Cookie: rat=3zln2EfoVYCPz_9HnBINRpAFmO; --он же token
        //Set-Cookie: ra_session=7a016aafcfa7483f86c0028d09d7008f; --он же session_id
        Response response = given().log().cookies()
                .contentType("application/json; charset=UTF-8")
                .accept(ContentType.JSON)
                .body("{\"email\": \"" + USER_LOGIN + "\", \"password\":\"qwerty\"}")
                .when()
                .then().log().all()
                .statusCode(HttpStatus.SC_OK) //SC_OK проверяет статус 200
                .body("data.session_id", notNullValue()) //Проверка на наличие Token
                .with()
                .post("/user/login/");

        String cookieRat = response.getCookie("rat"); //получить Куку из ответа сервера по Имени куки - getCookie("cookieName")
        String cookieRa_session = response.getCookie("ra_session");
        //System.out.println("rat: " + cookieRat);
        //System.out.println("ra_session: " + cookieRa_session);

        Cookie RAT = new Cookie("rat", cookieRat); //сохранение куки в формате - ключ: значение
        //System.out.println("Смотри сюда - rat: " + cookieRat);
        Cookie RA_SESSION = new Cookie("ra_session", cookieRa_session);

        return new Cookie[]{RAT, RA_SESSION};
    }
    @Test
    public void useCookieWithLoginGUI(){
        Cookie result[] = APIauthUser();
        System.out.println(result[0]);
        System.out.println(result[1]);
        open("/"); //сюда записываем значения КУКи в браузере, что не использовать сценарий авторизации (логин и пароль)
        getWebDriver().manage().addCookie(result[0]); //подставляем куку в браузер
        getWebDriver().manage().addCookie(result[1]);
        refresh(); //Обновление страницы, чтобы подтянулись куки и прошла авторизация WebDriverRunner.getWebDriver().navigate().refresh();
        menuUser.clickMenu();
        menuUser.exit();
        sleep(1000);
    }*/

    @Owner(value = "МалёнкинAA")
    @Epic(value = "Пользователь - Страница регистрации, авторизации, профиля, разные типы авторизации/регистрации")
    @Feature(value = "Окно авторизации")
    @Story(value = "Авторизация пользователя")
    @Issue(value = "ТАСК - AUTO-11464")
    @Tag("SMOKE")
    @Tag("WEB")
    @Severity(value = SeverityLevel.CRITICAL)
    @TmsLinks({@TmsLink(value = "allure-135")})
    @DisplayName("Валидный логин по E-MAIL")
    @Test
    void loginUser() {
        /*open("");
        $(By.className("user_container")).click();
        $(byText("Электронная почта")).click();
        $(By.name("email")).val("seyewi9798@200cai.com");
        $(byText("Продолжить")).click();
        $(By.name("pwd")).val("646_395").pressEnter();
        $(byText("Резервный")).should(appear);
        $(By.className("close")).click();*/
        pageMain.open().buttonInter();
        loginPage.ChoseEmailAuth()
                .enterUsername(USER_LOGIN)
                .enterPassword(USER_PASSWORD)
                .pushReserveWinClose();
    }

    @Test
    @Owner(value = "МалёнкинAA")
    @Epic(value = "Epic Функционал Райтинг")
    @Feature(value = "Feature Рейтинг книг")
    @Story(value = "Story Расчет и отображение рейтинга книги")
    @Issue(value = "Task - AUTO-123") //указывается ID дефекта в баг-треккинговой системе
    @Tag("SMOKE")
    @Tag("WEB")
    @Severity(value = SeverityLevel.CRITICAL)
    @TmsLinks({@TmsLink(value = "allure-135"), @TmsLink(value = "X-ray-136")})
    @DisplayName("Name Set Rating Book")
    @Description("Выставление рейтинга на выбранную книгу")
    void ratingBooks(){
        pageBook.open();
        pageMain.buttonInter();
        loginPage.login(USER_LOGIN, USER_PASSWORD);
        pageBook.activeRatingForm(0);
        pageBook.ratingChose();
        //.shouldHaveResultRating(0,"3")
        pageBook.ratingNumberClick.get(0).shouldHave(text("3").because("invalid stars rating")); //этот вариант лучше
        pageBook.activeRatingForm(0);

    }

    @ParameterizedTest
    @DisplayName("Incorrect Inter Users")
    @JsonFileSource(resources = "/logins.json") //https://github.com/joshka/junit-json-params
    @Description("Скоро будет реализована параметризация с входом, используя разные неволидные данные")
    void incorrectLogin(JsonObject object){
        open("https://www.litres.ru/pages/login/");
        $(By.className("user_container")).click();
        $(byText("Электронная почта")).click();
        $(By.name("email")).val(object.getString("key"));
        $(byText("Продолжить")).click();
        $(By.name("pwd")).val("646_395"); //579apxg6
        $(".login-popup__action").click();
        $(byText("Резервный")).should(appear);
        $(By.className("close")).click();
        //loginPage.login(object.getString("key"), object.getString("value"));
        System.out.println(object.get("key"));
        System.out.println(object.get("value"));
    }

    //без использования PO - https://www.youtube.com/watch?app=desktop&v=w5EgCZgj5yE и https://github.com/autotests-cloud/selenide-web-ios-android-tests/blob/master/src/test/java/cloud/autotests/tests/web/LoginTests.java
    @Test
    @DisplayName("Valid login without PO")
    void allTypeLogin(){
        //loginPage.open().login("seyewi9798@200cai.com","646_395");
        step("Go to login page", ()-> {
            open("https://www.litres.ru/pages/login/");
            $(byText("Вход в систему")).shouldHave(text("Вход в систему").because("Должна быть надпись 'Вход в систему'"));
        });

        step("Fill the authorization form", ()-> {
            $(byId("frm_login")).shouldBe(Condition.visible);
            $(byName("login")).setValue(USER_LOGIN);
            $(byId("open_pwd_main")).setValue(USER_PASSWORD);
            $(byId("login_btn")).click();
        });

        step("Verify successful authorization", ()-> {
            $(byId("frm_login")).shouldNot(Condition.exist);
            $(By.className("user_name")).shouldHave(text(DEFAULT_LOGIN));
            $(By.className("user_cash")).shouldHave(Condition.ownText("0"));
        });
    }
}

//Запуск тестов из консоли - https://github.com/selenide-examples/google
//./gradlew clean test; ./gradlew allureServe
