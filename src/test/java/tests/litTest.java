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
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import page.loginPage;
import page.mainPage;
import page.pageBook;

import javax.json.JsonObject;

import static com.codeborne.selenide.Selenide.open;
import static config.userConfig.USER_LOGIN;
import static config.userConfig.USER_PASSWORD;

@ExtendWith({TextReportExtension.class}) //логирование - https://ru.selenide.org/documentation/reports.html
public class litTest {
    loginPage loginPage = new loginPage();
    pageBook pageBook = new pageBook();
    mainPage pageMain = new mainPage();

    //реализровать API тест для получения token сессии

    @BeforeAll
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
    }

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

    @DisplayName("CorrectLoginEmail")
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
        loginPage.ChoseEmailAuth();
        loginPage.enterUsername(USER_LOGIN);
        loginPage.enterPassword(USER_PASSWORD);
        loginPage.pushReserveWinClose();
    }

    @Test
    @Owner(value = "Малёнкин")
    @Epic(value = "ЭПИК Алюр - Функционала Тест. Позволяет группировать тесты по эпикам")
    @Feature(value = "ФИЧА Алюр - для тестирования структуры Тестовой модели")
    @Issue(value = "RBCNEW-123") //указывается ID дефекта в баг-треккинговой системе
    @Tag("smoke")
    @Severity(value = SeverityLevel.NORMAL)
    @TmsLinks({@TmsLink(value = "X-ray-135"), @TmsLink(value = "X-ray-136")})
    @DisplayName("SetRatingBook")
    void ratingBooks(){
        pageBook.open().activeRatingForm(0);
        pageBook.ratingChose();
                //.shouldHaveResultRating(0,"3") //ниже тоже самое, только без PO
        pageBook.ratingNumberClick.get(0).shouldHave(Condition.text("3").because("invalid stars rating"));
        pageBook.activeRatingForm(0);
    }

    @ParameterizedTest
    @JsonFileSource(resources = "/logins.json") //https://github.com/joshka/junit-json-params
    @DisplayName("IncorrectUser")
    void incorrectLogin(JsonObject object){
        open("https://www.litres.ru/");
        loginPage.login(object.getString("user"), object.getString("pwd"));
        System.out.println(object.get("key"));
        System.out.println(object.get("value"));
    }

    /*@ParameterizedTest
    @DisplayName("loginDifferents")
    void allTypeLogin(){
        loginPage.open().login("seyewi9798@200cai.com","646_395");
    }*/
}

//Запуск тестов из консоли - https://github.com/selenide-examples/google
