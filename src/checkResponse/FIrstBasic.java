package checkResponse;


import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

public class FIrstBasic {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		//given--all input details
		//when--submit the api resource http methods
		//then---validate the response
		
		//https://rahulshettyacademy.com/maps/api/place/add/json?Key=qaclick123
		
		
		//post
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		 String response=given().log().all().queryParam("Key","qaclick123").header("Content-type", "application/json")
		.body(payloads.Addplace())
		.when().post("maps/api/place/add/json")
		.then().assertThat().log().all().statusCode(200)
		.body("scope", equalTo("APP")).extract().response().asString();
		 
	//	 System.out.println(response);
		 
		 
		 //udpate address
	JsonPath js1= ReUsableMethods.rawToJson(response);
	String placeid=js1.getString("place_id");
		 
		// String placeid=js.getString("place_id");
		 
	//	 System.out.println("place id is "+placeid);
		 
		 
		 String newAddress = "Summer Walk, Africa 99";
			
			given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
			.body("{\r\n" + 
					"\"place_id\":\""+placeid+"\",\r\n" + 
					"\"address\":\""+newAddress+"\",\r\n" + 
					"\"key\":\"qaclick123\"\r\n" + 
					"}").
			when().put("maps/api/place/update/json")
			.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
			
		 
		 
		 //get
		String newaddress=	given().log().all().queryParam("key", "qaclick123")
			.queryParam("place_id",placeid)
			.when().get("maps/api/place/get/json")
			.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		JsonPath js=ReUsableMethods.rawToJson(newaddress);
		String actualaddress=js.getString("address");
				 
		 Assert.assertEquals(actualaddress, newAddress);
		 
		 
		
		

	}

}
	