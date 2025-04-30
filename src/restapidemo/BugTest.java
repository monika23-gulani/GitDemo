package restapidemo;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.Base64;

public class BugTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RestAssured.baseURI = "https://monikagulani23.atlassian.net/";
		String email = System.getenv("JIRA_EMAIL");
		System.out.println(email);
		String token = System.getenv("JIRA_API_TOKEN");
		System.out.println(token);
		String auth = Base64.getEncoder().encodeToString((email + ":" + token).getBytes());

		String createIssueResponse = given().header("Content-Type", "application/json")
				.header("Authorization", "Basic " + auth)
				.body("{\r\n" + "  \"fields\": {\r\n" + "    \r\n" + "    \"description\": {\r\n"
						+ "      \"content\": [\r\n" + "        {\r\n" + "          \"content\": [\r\n"
						+ "            {\r\n"
						+ "              \"text\": \"Order entry fails when selecting supplier.\",\r\n"
						+ "              \"type\": \"text\"\r\n" + "            }\r\n" + "          ],\r\n"
						+ "          \"type\": \"paragraph\"\r\n" + "        }\r\n" + "      ],\r\n"
						+ "      \"type\": \"doc\",\r\n" + "      \"version\": 1\r\n" + "    }\r\n" + "    ,\r\n"
						+ "    \"issuetype\": {\r\n" + "      \"name\": \"Bug\"\r\n" + "    },\r\n"
						+ "    \"labels\": [\r\n" + "      \"bugfix\",\r\n" + "      \"blitz_test\"\r\n" + "    ],\r\n"
						+ "    \"project\": {\r\n" + "      \"key\": \"MG\"\r\n" + "    },\r\n" + "    \r\n"
						+ "    \"summary\": \"Null response wiped off\"\r\n" + "  },\r\n" + "  \"update\": {}\r\n"
						+ "}")
				.log().all().post("/rest/api/3/issue").then().log().all().assertThat().statusCode(201).extract()
				.response().asString();

		JsonPath js = new JsonPath(createIssueResponse);
		String issueId = js.getString("id");
		System.out.println(issueId);

		given().pathParam("key", issueId).header("X-Atlassian-Token", "no-check")
				.header("Authorization", "Basic " + auth)
				.multiPart("file", new File("C:\\Users\\monika23\\Downloads\\Screenshot 2025-02-04 230710.png")).log()
				.all().post("rest/api/3/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);
				System.out.println("I am user 1");
	}

}
