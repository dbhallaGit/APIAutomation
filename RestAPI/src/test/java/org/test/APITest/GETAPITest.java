package org.test.APITest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.qa.Base.TestBase;
import org.qa.data.users;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import or.qa.clientCall.RestClient;

public class GETAPITest extends TestBase{
	TestBase testBase;
	String URL;
	String APIurl;
	RestClient restClient;
	users user;
	String ServiceURLGetUserList;
	String getSingleUser;

	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException {

		testBase=new TestBase();
		URL=prop.getProperty("URL");
		ServiceURLGetUserList=prop.getProperty("ServiceURLGetUserList");
		getSingleUser=prop.getProperty("GetSingleUser",prop.getProperty("DefaultGetSingleUser"));
		
		System.out.println("************************** SetUp complete ****************************");
	}

	@Test
	public void getTest() throws ClientProtocolException, IOException {
		System.out.println(new Throwable().getStackTrace()[0].getMethodName()+"-->Execution started");
		restClient=new RestClient();
		APIurl=URL+ServiceURLGetUserList;
		JSONObject json= restClient.getJsonResponse(restClient.getResponse(APIurl));
		System.out.println(json);

		HashMap<String, String> Headers=restClient.getAllHeader(restClient.getResponse(APIurl));
		HashMap<String,String> header=new HashMap<String, String>()
		{
			{
				put("Host","reqres.in");
			}
		};

		System.out.println("Data--0 first name"+restClient.getValueByJPath(restClient.getResponse(APIurl,header ), "/data[0]/first_name"));
		System.out.println("Data--1 first name"+restClient.getValueByJPath(restClient.getResponse(APIurl), "/data[1]/first_name"));
		System.out.println("Data--2 first name"+restClient.getValueByJPath(restClient.getResponse(APIurl), "/data[2]/first_name"));	
		System.out.println("Data--2 first name"+restClient.getValueByJPath(restClient.getResponse(APIurl), "total"));	
		System.out.println(new Throwable().getStackTrace()[0].getMethodName()+"-->Execution completed");
	}

	//@Test
	public void UserResponseNotFound() throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println(new Throwable().getStackTrace()[0].getMethodName()+"-->Execution started");
		restClient=new RestClient();
		APIurl=URL+ServiceURLGetUserList;
				HashMap<String, String> header=new HashMap<String, String>();
		header.put("Content-Type", "application/json");
		ObjectMapper mapper=new ObjectMapper();
		user=new users("DB","QA");
		mapper.writeValue(new File(System.getProperty("user.dir")+"\\src\\main\\java\\org\\qa\\data\\user.json"), user);
		String entity=mapper.writeValueAsString(user);

		CloseableHttpResponse response= restClient.post(APIurl, entity, header);
		if((response.getStatusLine().getStatusCode()==200)?true:false)
			System.out.println("Users created successfully");
		else
			System.out.println("Respponse while creating the user-->"+response.getStatusLine().getStatusCode());
		
		String JsonString=EntityUtils.toString(response.getEntity(),"UTF-8");
		JSONObject jsonResponse=new JSONObject(JsonString);
		System.out.println("Json response-->"+jsonResponse);
		users user1=mapper.readValue(JsonString, users.class);
		APIurl+="/"+user1.getId();
		System.out.println(APIurl);
		
		CloseableHttpResponse response1=restClient.getResponse(APIurl, header);
		if(response1.getStatusLine().getStatusCode()==200) {
		String JsonString2=EntityUtils.toString(response1.getEntity(), "UTF-8");
		JSONObject jsonResponse2=new JSONObject(JsonString2);
		System.out.println("Json response-->"+jsonResponse2);
		users user2=mapper.readValue(JsonString2, users.class);
		System.out.println(user1.getId()+"<---->"+user2.getId());
		Assert.assertTrue(user1.getId()==user2.getId());
		}
		else
			System.out.println("response ----> not found");
			
		

		System.out.println(new Throwable().getStackTrace()[0].getMethodName()+"-->Execution completed");
	}
	
	//@Test
	public void getSingleUser() throws ClientProtocolException, IOException {
		System.out.println(new Throwable().getStackTrace()[0].getMethodName()+"-->Execution started");
		restClient=new RestClient();
		HashMap<String, String> header=new HashMap<String, String>();
		header.put("Content-Type", "application/json");
		
		APIurl=URL+prop.getProperty("DefaultGetSingleUser");
		CloseableHttpResponse response=	restClient.getResponse(APIurl, header);
		
		
		System.out.println("Response code-->"+response.getStatusLine().getStatusCode());
		String Entity=EntityUtils.toString(response.getEntity(),"UTF-8");
		JSONObject jObj=new JSONObject(Entity);
		System.out.println(jObj);
		
		String id=restClient.getValueByJPath(restClient.getResponse(APIurl, header), "/data/id");
		System.out.println("ID is-->"+id);
		
		
		System.out.println(new Throwable().getStackTrace()[0].getMethodName()+"-->Execution completed");
		
	}
	
	@Test
	public void getListOfResources() throws ClientProtocolException, IOException {
		System.out.println(new Throwable().getStackTrace()[0].getMethodName()+"-->Execution started");
		restClient=new RestClient();
		HashMap<String, String> headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		
		APIurl=URL+prop.getProperty("ListOfResourceURL");
		CloseableHttpResponse response=restClient.getResponse(APIurl, headers);
		String Entity=EntityUtils.toString(response.getEntity(),"UTF-8");
		JSONObject jsonRespons=new JSONObject(Entity);
		System.out.println(jsonRespons);
		
		System.out.println(restClient.getValueByJPath(restClient.getResponse(APIurl, headers), "/data[0]"));
		System.out.println(restClient.getValueByJPath(restClient.getResponse(APIurl, headers), "/data[1]"));
		System.out.println(restClient.getValueByJPath(restClient.getResponse(APIurl, headers), "/data[2]"));
		System.out.println(restClient.getValueByJPath(restClient.getResponse(APIurl, headers), "/data[3]"));
		
		
		
	}

}
