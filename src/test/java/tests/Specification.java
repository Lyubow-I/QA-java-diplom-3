package tests;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static models.Api.MAIN_PAGE;

public class Specification {

    public RequestSpecification getBaseSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(MAIN_PAGE)
                .setContentType(ContentType.JSON)
                .build();
    }
}

