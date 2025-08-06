package com.api.automation.tests;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import com.api.automation.config.ConfigManager;

public class getIssue {
    
    public static void main(String[] args) {
        ConfigManager config = ConfigManager.getInstance();
        RestAssured.baseURI = config.getProperty("jira.base.url");
        String authToken = config.getProperty("jira.auth.token");

        given()
        .header("Accept","application/json")
        .header("Authorization", authToken)
        .when()
        .get("rest/api/3/issue/10059")
        .then()
        .statusCode(200)
        .assertThat().body("id",equalTo("10059"))
        .log().all();
    }
}
