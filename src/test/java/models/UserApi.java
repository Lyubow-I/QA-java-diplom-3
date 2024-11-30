package models;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import tests.Specification;

import static io.restassured.RestAssured.given;
import static models.Api.USER_DELETE;
import static models.Api.USER_REGISTER;

public class UserApi extends Specification {
    @Step("Создание пользователя")
        public Response createUser (User user) {
            return given()
                    .log().all()
                    .header("Content-type", "application/json")
                    .body(user)
                    .when()
                    .post(USER_REGISTER);

    }


    @Step("Удаление пользователя")
    public Response deleteUser(String token) {
        return given()
                .log().all()
                .headers(
                        "Content-type", "application/json",
                        "Authorization", token)
                .and()
                .delete(USER_DELETE);
    }
}

