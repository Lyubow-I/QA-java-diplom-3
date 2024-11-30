package models;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import tests.Specification;

import static io.restassured.RestAssured.given;
import static models.Api.USER_DELETE;
import static models.Api.USER_REGISTER;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static sun.nio.cs.Surrogate.is;

public class UserApi extends Specification {
    @Step("Создание пользователя")
    public Response createUser(User user) {
        return given()
                .log().all()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(USER_REGISTER);

    }


    @Step("Удалить пользователя")
    public void deleteUser(String accessToken) {
        Response deleteResponse = given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .delete(USER_DELETE);
        assertThat(String.valueOf(deleteResponse.getStatusCode()), is(202));
        assertThat(deleteResponse.jsonPath().getBoolean("success"), equalTo(true));
        String expectedMessage = "User successfully removed";
        assertThat(deleteResponse.jsonPath().getString("message"), equalTo(expectedMessage));
    }

}