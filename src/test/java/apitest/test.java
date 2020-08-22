package apitest;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import com.automation.api.Core.BaseTest;
import com.automation.api.Util.ExtentReport;
import com.automation.api.Util.ReadTestData;
import com.automation.api.Util.configReader;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.pojoClass;

public class test extends BaseTest{
	//public static String ct = ReadTestData.getTestData("contenttypeValue");
//	@Test
//	public static void getUserDetails() {
//	String baseUrl = Helper.propertyReader("C:\\Users\\gs-2181\\eclipse-workspace\\APIG6\\common.properties", "baserurl");
//
//	Helper.loginfo("Hitting API URL :- ", baseUrl);
//	
//	Response resp = null;
//		
//    resp = given()
//    		.when().get(baseUrl);
//    System.out.println(resp.getBody().asString());
//    }
	
	@Test(description="Validate 404 status code - GET API", groups="SmokeSuite")
	public void verifyGetUserStatusCode() {
		ExtentReport.extentlog = ExtentReport.extentreport.startTest("Execute post method",
				" validate post method");
		Response resp = given()
				.when().get("https://reqres.in/api/users/2");
		
		int actualstatuscode = resp.getStatusCode();
		assertEquals(actualstatuscode,200);
		System.out.println("Status Code of the GET Request is: " + actualstatuscode);
		System.out.println(resp.getBody().asString());
		
	}
	@Test
	public static void auth() {
		Response resp = given()
				.auth()
				.basic("sid", "sid").when().get("https://reqres.in/api/users/2");
		Response resp1 = given()
				.auth()
				.preemptive().basic("sid", "sid").when().get("https://reqres.in/api/users/2");
		Response resp2 = given()
				.auth()
				.digest("sid", "sid").when().get("https://reqres.in/api/users/2");
		Response resp3 = given()
				.auth()
				.oauth2("accesstoken").when().get("https://reqres.in/api/users/2");
		Response resp4 = given()
				.auth()
				.oauth("consumerKey", "consumerSecret", "accessToken", "secretToken").when().get("https://reqres.in/api/users/2");
		Response resp5 = given().header("Authorization","accessToken")
					.when().get("https://reqres.in/api/users/2");
				 
	}
	
	@Test(description="putpost", groups="RegressionSuite")
	public static void postputwithpojo() throws Exception {
		
		pojoClass pjcpost = new pojoClass("morpheus", "tester");
		pojoClass pjcput = new pojoClass("morpheus", "sdet");
		
		Response resppost = given().header("Content-Type","application/json").
		             body(pjcpost).when().post(ReadTestData.getTestData("uri"));
	    System.out.println(resppost.getBody().asString());
	    assertEquals(resppost.getStatusCode(),201);
	    Thread.sleep(2000);
		Response respput = given().header("Content-Type","application/json").
	             body(pjcput).when().put(ReadTestData.getTestData("putUserUri"));
		 System.out.println(respput.getBody().asString());
		 assertEquals(respput.getStatusCode(),200);
	}
	
	@Test(groups="RegressionSuite")
	public static void putmethodpojo() {
		
		pojoClass pjc = new pojoClass("tester", "sdet");
		
		Response resp = given().header("Content-Type","application/json").
		           // body(requestParams.toString()).
		             body(pjc).
		           
		    when().
		            put("https://reqres.in/api/users/2");
		    System.out.println(resp.getBody().asString());
		    assertEquals(resp.path("job"),"sdet");
		    
	}
	@Test(description="Verify status code for GET method-users/2 as 200",groups="SmokeSuite")
	 public static void verifyStatusCodeGET() {
		 Response resp=given().when().get("https://reqres.in/api/users");
		 System.out.println(resp.path("total").toString());
		 assertEquals(resp.getStatusCode(),200);
		 
		 assertEquals(resp.path("total").toString(),"12");
		
		 List<String> expected = new ArrayList<String>();
		 
		 List<String> jsonResponse = resp.jsonPath().getList("data");
	     System.out.println(jsonResponse.size());
	     System.out.println("The number of data in the list is : " + jsonResponse.size());
	     assertEquals(jsonResponse.size(),6);
	     String usernames = resp.jsonPath().getString("data[1].email");
	     System.out.println(usernames);
	//   //  List<String> jsonResponses = resp.jsonPath().getList("data");
	//   //  System.out.println(jsonResponses.get(0));
	     Map<String, String> company = resp.jsonPath().getMap("data[2]");
	     System.out.println(company);
	     System.out.println("Fetch firtsname using map and get: " + company.get("first_name"));
	   //  List<Map<String, String>> companies = resp.jsonPath().getList("data");
	   //  System.out.println(companies.get(0).get("first_name"));
	    List<String> ids = resp.jsonPath().getList("data.email");
//	    Collections.sort(jsonResponse);
//	    Collections.sort(expected);
//	    assertEquals(jsonResponse,expected);
	     for(String i:ids)
	     {
	    	 System.out.println(i);
	        if (i.contentEquals("charles.morris@reqres.in")) {
	        	System.out.println("JANET is present in the response body");
	        }
	     }
	}
	@Test(description="validate 204 for Delete user")
	public void verifyStatusCodeDelete() {
		
		Response resp = given().delete("https://reqres.in/api/users/2");
		assertEquals(resp.getStatusCode(),204);
		System.out.println("***********************************PASS*******************************");
		
	}
	
	@Test(description="Verify email for User with id=2")
	public void verifyEmailUID() throws IOException, ParseException {
		
		Response resp = given().pathParam("raceSeason", "2018").
				when().get("http://ergast.com/api/f1/{raceSeason}/circuits.json");
		assertEquals(resp.path("MRData.total"),ReadTestData.getTestData("total"));
		System.out.println(resp.getBody().asString());
		
	}
	
	@Test(description="Verify email for User with id=2")
	public void verifyEmailUIDheaders() {
		
		Response resp = given().pathParam("raceSeason", "2018").header("Content-Type","application/json").
				when().get("http://ergast.com/api/f1/{raceSeason}/circuits.json");
		assertEquals(resp.path("MRData.total"),"21");
		System.out.println(resp.getBody().asString());
		
	}

	@Test(description="Verify email for User with id=2")
	public void verifyEmailUID2() {
		
		Response resp = given().queryParam("page", "2").queryParam("test", "text").
				when().get("https://reqres.in/api/users");
		assertEquals(resp.path("per_page").toString(),"6");
		
	}
	@Test(description="validate with jsonpath and json object and pass post body as json file")
	public void postMethodValidationDataFile() throws IOException, ParseException {
		FileInputStream file = new FileInputStream(new File (System.getProperty("user.dir")+"/Resources/TestData/postuser.json"));
		
		String url = ReadTestData.getTestData("uri");
		String tailuri = ReadTestData.getTestData("tailURL");
		//String uril = ReadTestData.getdatafromjson("$.uri");
		System.out.println(url);
		
		Response resp = 
				given().headers("Content-Type","application/json").
		            body(IOUtils.toString(file,"UTF-8")).
		            
		    when().
		            post("https://reqres.in/api/users");
		   // Response resp = postUserDetails(tailuri);
		
		    assertEquals(resp.getStatusCode(),201);
		    System.out.println(resp.getBody().asString());
	   
	}	
	
	@Test(description= "Automate post method for users")
	public static void postmethodjson() throws IOException, ParseException {
		ExtentReport.extentlog = ExtentReport.extentreport.startTest("Execute post method",
				" validate post method");
		FileInputStream file = new FileInputStream(new File (System.getProperty("user.dir")+"\\Resources\\TestData\\testdata.json"));
		
		Response resp = given().header(ReadTestData.getTestData("headertypeContent"),ReadTestData.getTestData("contenttypeValue")).
				body(IOUtils.toString(file, ReadTestData.getTestData("encodingType"))).
				when().post(ReadTestData.getTestData("uri"));
		assertEquals(resp.getStatusCode(),201);
		System.out.println("The status code for post method is : " + resp.getStatusCode() );
		System.out.println("The response body of the post method is : " + resp.getBody().asString());
		assertEquals(resp.path("job"),ReadTestData.getTestData("expectedJob"));
		
	}
	
	@Test(description= "Automate post method for users")
	public static void putmethodjson() throws IOException, ParseException {
		ExtentReport.extentlog = ExtentReport.extentreport.startTest("Execute post method",
				" validate post method");
		FileInputStream file = new FileInputStream(new File (System.getProperty("user.dir")+"\\Resources\\TestData\\testdata.json"));
		
		Response resp = given().header(ReadTestData.getTestData("headertypeContent"),ReadTestData.getTestData("contenttypeValue")).
				body(IOUtils.toString(file, ReadTestData.getTestData("encodingType"))).
				when().put(ReadTestData.getTestData("putUserUri"));
		
		assertEquals(resp.getStatusCode(),200);
		System.out.println("The status code for post method is : " + resp.getStatusCode() );
		System.out.println("The response body of the post method is : " + resp.getBody().asString());
		assertEquals(resp.path("job"),ReadTestData.getTestData("expectedJob"));
		
	}
	
	@Test
	public static void postmethodwithpojoclass() {
		ExtentReport.extentlog = ExtentReport.extentreport.startTest("Execute post method",
				" validate post method");
		String job = "tester";
		String name = "automation";
		pojoClass obj = new pojoClass(name,job);
		Response resp = given().headers("Content-Type","application/json").
				body(obj).
				when().post("https://reqres.in/api/users");
		assertEquals(resp.getStatusCode(),201);
		System.out.println("The status code for post method is : " + resp.getStatusCode() );
		System.out.println("The response body of the post method is : " + resp.getBody().asString());
		assertEquals(resp.path("job"),job);
		assertEquals(resp.path("name"),name);
	}
   
   

}
