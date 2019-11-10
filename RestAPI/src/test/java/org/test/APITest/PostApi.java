package org.test.APITest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.qa.Base.TestBase;
import org.qa.data.users;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import or.qa.clientCall.RestClient;

public class PostApi extends TestBase{
	
	  TestBase testbase;
	  String serviceUrl,apiUrl,uri;
	  RestClient restClient;

	@BeforeMethod
	 public void setup() {
		 testbase=new TestBase();
		 serviceUrl=prop.getProperty("serviceURL");
		 apiUrl=prop.getProperty("URL");
		 uri=apiUrl+serviceUrl;
		 
	 }
	
	@Test
	public void PostCall() throws JsonGenerationException, JsonMappingException, IOException {
		restClient=new RestClient();
		HashMap<String,String> headerMap=new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		
		//jackson api to create json file
		ObjectMapper mapper=new ObjectMapper();
		users user=new users("Divesh","leader");
		
		mapper.writeValue(new File(System.getProperty("user.dir")+"\\src\\main\\java\\org\\qa\\data\\user.json"), user);
		
		//object to json as string
		String userJsonString=mapper.writeValueAsString(user);
		
		CloseableHttpResponse response=	restClient.Post(uri, userJsonString, headerMap);
		
		//Status
		System.out.println("Status of post call"+response.getStatusLine().getStatusCode());
		
		//JsonResponse test
		String responseJsonString=EntityUtils.toString(response.getEntity(),"UTF-8");
		
		JSONObject jsonResponse=new JSONObject(responseJsonString);
		System.out.println("Json response "+jsonResponse);
		
		users useObj=mapper.readValue(responseJsonString, users.class);
		System.out.println(useObj.getName()+useObj.getJob());
		
		//validating name sent in json payload is same as json response
		System.out.println(useObj.getName().equals(user.getName()));
		
		
		
	}

}
