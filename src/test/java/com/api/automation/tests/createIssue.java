package com.api.automation.tests;

import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import java.io.File;
import com.api.automation.config.ConfigManager;

public class createIssue {
   public static void main(String[] args) {
    ConfigManager config = ConfigManager.getInstance();
    RestAssured.baseURI = config.getProperty("jira.base.url");
    String authToken = config.getProperty("jira.auth.token");

   String response = given()
    .header("Content-Type","application/json")
    .header("Authorization", authToken)
    .body(
    "{\n" +
    "  \"fields\": {\n" +
    "    \"project\": {\"key\": \"SMS\"},\n" +
    "    \"summary\": \"app loading issue\",\n" +
    "    \"issuetype\": {\"name\": \"Bug\"}\n" +
    "  }\n" +
    "}"
)
.when()
.post("rest/api/3/issue")
.then()
.statusCode(201)
.log().all()
.extract().response().asString();

JsonPath jsonPath = new JsonPath(response);
String issueId=jsonPath.getString("id");
System.out.println("issueId"+issueId);

// add attachment to the bug
    given()
   .header("X-Atlassian-Token","nocheck")
   .header("Authorization", authToken)
   .multiPart("file",new File("/Users/testvagrant/Downloads/Bug.jpeg"))
   .when()
   .post("rest/api/3/issue/"+issueId+"/attachments")        
   .then()
   .assertThat().statusCode(200)
   .log().all();
}
}
