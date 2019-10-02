package com.screen.rest.api;

import com.screen.rest.BingMapsApi;
import com.screen.rest.TestData;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RestApiTests {

    @BeforeClass
    public void beforeTest() {
        RestAssured.baseURI = BingMapsApi.API_URL;
    }

    @Test
    public void verifyGetAddressByPoint() {

        given().param("key", BingMapsApi.API_KEY)
               .basePath(BingMapsApi.LOCATION_URL + TestData.REICHSTAG_LOCATION)
               .get()
               .then()
               .assertThat()
               .statusCode(200)
               .contentType(ContentType.JSON)
               .body("resourceSets.resources[0].name", hasItem(TestData.REICHSTAG_ADDRESS));

    }

    @Test
    public void verify401UnauthorizedOnInvalidApiKey() {

        given().param("key", BingMapsApi.API_KEY + 1234)
               .basePath(BingMapsApi.LOCATION_URL + TestData.REICHSTAG_LOCATION)
               .get()
               .then()
               .assertThat()
               .statusCode(401);
    }

    @Test
    public void verifyPostAddPushPinOnTheMap() {
        given().config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8")))
                .queryParam("key", BingMapsApi.API_KEY)
               .basePath(BingMapsApi.POST_URL)
               .contentType(ContentType.TEXT)
               .body(TestData.PUSH_PINS_BODY)
               .post()
               .then()
               .assertThat()
               .statusCode(200)
               .contentType("image/jpeg");
    }

    @Test
    public void verifyPost404ForInvalidAPI() {
        given().config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8")))
               .queryParam("key", BingMapsApi.API_KEY)
               .basePath(BingMapsApi.LOCATION_URL + "invalid/")
               .contentType(ContentType.TEXT)
               .body(TestData.PUSH_PINS_BODY)
               .post()
               .then()
               .assertThat()
               .statusCode(404);
    }

    @Test
    public void verifyPost500OnInvalidBodyContent() {
        given().queryParam("key", BingMapsApi.API_KEY)
               .basePath(BingMapsApi.POST_URL)
               .contentType(ContentType.XML)
               .body(TestData.INVALID_QUERY_STRING)
               .post()
               .then()
               .assertThat()
               .statusCode(500);
        }
}
