package pageobjects;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import tests.Specification;

import static io.restassured.RestAssured.given;
import static pageobjects.Api.USER_DELETE;
import static pageobjects.Api.USER_REGISTER;

public class UserApi extends Specification {


    @Step("Создание пользователя")
    public String createUser(User user) {
        ValidatableResponse response = given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(USER_REGISTER)
                .then()
                .log().all();
        String accessToken = response.extract().path("accessToken");
        return accessToken;

    }

    @Step("Удаление пользователя")
    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .body(accessToken)
                .when()
                .delete(USER_DELETE)
                .then()
                .log().all();
    }
}

