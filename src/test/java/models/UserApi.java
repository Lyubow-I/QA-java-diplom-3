package models;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.ConstructorPage;
import pageobjects.RegistrationPage;
import pageobjects.StellarBurgersPage;
import tests.Specification;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static models.Api.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static sun.nio.cs.Surrogate.is;

public class UserApi extends Specification {
    private WebDriver driver;
    private StellarBurgersPage stellarBurgersPage;
    private RegistrationPage registrationPage;
    private ConstructorPage constructorPage;
    private UserClient userClient;
    private String accessToken;
    public UserApi() {
        this.userClient = new UserClient();
    }

    @Step("Удалить пользователя по токену")
    public void deleteUser(String accessToken) {
        Response deleteResponse = given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .delete(USER_DELETE);
        System.out.println("Delete Response Code: " + deleteResponse.getStatusCode());
        System.out.println("Delete Response Body:\n" + deleteResponse.getBody().prettyPrint());
        assertThat(String.valueOf(deleteResponse.getStatusCode()), is(202));
        assertThat(deleteResponse.jsonPath().getBoolean("success"), equalTo(true));
        String expectedMessage = "User  successfully removed";
        assertThat(deleteResponse.jsonPath().getString("message"), equalTo(expectedMessage));
        System.out.println("Пользователь успешно удален");
    }

    @Step("Вход под логиным")
    public void login(UserRandom user) {
        stellarBurgersPage.setEmail(user.getEmail());
        stellarBurgersPage.setPassword(user.getPassword());
        registrationPage.clickLoginButton();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe(MAIN_PAGE));

        Response loginResponse = userClient.login(user.getEmail(), user.getPassword());
        loginResponse.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("success", equalTo(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue())
                .body("user.email", equalToIgnoringCase(user.getEmail()));

        accessToken = loginResponse.as(UserToken.class).getAccessToken();

    }
    @Step("Изъятие токена")
    public String getAccessToken(String email, String password) {
        Response loginResponse = userClient.login(email, password);
        loginResponse.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("success", equalTo(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue())
                .body("user.email", equalToIgnoringCase(email));

        return loginResponse.as(UserToken.class).getAccessToken();
    }
    @Step("Регистрация через API")
    public Response register(UserRandom user) {
        Map<String, String> userData = new HashMap<>();
        userData.put("email", user.getEmail());
        userData.put("password", user.getPassword());
        userData.put("name", user.getName());

        Response response = RestAssured.given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(userData)
                .when()
                .post("/api/auth/register");
        System.out.println("Response: " + response.getBody().asString());
        return response;
    }
}


