package models;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class UserClient {
    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site";


    public Response login(String email, String password) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body("{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}")
                .when()
                .post("/api/auth/login")
                .thenReturn();
    }
}
