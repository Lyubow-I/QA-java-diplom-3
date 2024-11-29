package models;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import tests.Specification;

import static io.restassured.RestAssured.given;
import static models.Api.USER_DELETE;
import static models.Api.USER_REGISTER;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.hamcrest.CoreMatchers.equalTo;

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
    public static void deleteUser(String accessToken) {
        given()
                .header("Authorization", accessToken)
                .delete(USER_DELETE)
                .then()
                .statusCode(SC_ACCEPTED)
                .and()
                .body("success", equalTo(true))
                .body("message", equalTo("User successfully removed"));

        }
    }




