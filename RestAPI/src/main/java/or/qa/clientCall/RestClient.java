package or.qa.clientCall;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.RepaintManager;
import javax.swing.text.html.parser.Entity;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RestClient {

	
	//Get Everything in Response Payload Method
	public void getEverything(String URL) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient= HttpClients.createDefault();
		HttpGet httpget=new HttpGet(URL); //http get request
		CloseableHttpResponse closeableHTTPResponse= httpClient.execute(httpget);//response from execution
		
		//Status Code
		int statusCode=closeableHTTPResponse.getStatusLine().getStatusCode();
		//System.out.println("statusCode for request---->"+statusCode);
		
		//JSON Response
		String responseString= EntityUtils.toString(closeableHTTPResponse.getEntity());
		JSONObject responseJson=new JSONObject(responseString);
		System.out.println("Response JSON from request"+responseJson);
		
		//All Headers
		Header[] headerArray=closeableHTTPResponse.getAllHeaders();
		HashMap<String, String> allHeaders=new HashMap<String, String>();
		
		for(Header header:headerArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("All Headers ---->"+ allHeaders);
	} 
	
	//Get Response without header
	public CloseableHttpResponse getResponse(String URL) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient= HttpClients.createDefault();
		HttpGet httpGet=new HttpGet(URL);
		CloseableHttpResponse response= httpClient.execute(httpGet);
		return response;
	}
	
	//Get Response with Header
	public CloseableHttpResponse getResponse(String URL,HashMap<String, String> headers) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient= HttpClients.createDefault();
		HttpGet httpGet=new HttpGet(URL);
		
		for(Map.Entry<String,String> entry:headers.entrySet()) {
			httpGet.addHeader(entry.getKey(),entry.getValue());
		}
		
		
		CloseableHttpResponse response= httpClient.execute(httpGet);
		return response;
	}
	
	//Post Method
	public CloseableHttpResponse Post(String url,String entityString,HashMap<String,String> headers) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient=HttpClients.createDefault();
		HttpPost httpPost=new HttpPost(url);
		httpPost.setEntity(new StringEntity(entityString));
		
		for(Map.Entry<String, String> entry:headers.entrySet())
			httpPost.addHeader(entry.getKey(), entry.getValue());
		
		CloseableHttpResponse response= httpClient.execute(httpPost);
		return response;
		
	}
	
	
	public JSONObject getJsonResponse(CloseableHttpResponse response) throws ParseException, IOException {
		String JsonEntity=EntityUtils.toString(response.getEntity());
		JSONObject json=new JSONObject(JsonEntity);
		return json;
		
	}
	
	public HashMap<String, String> getAllHeader(CloseableHttpResponse response) {
		Header[] headerArray=response.getAllHeaders();
		HashMap<String, String> allHeaders=new HashMap<String, String>();
		//System.out.println("All Headers in response");
		for(Header header:headerArray) {
			//System.out.println("\""+header.getName()+" : "+"\""+header.getValue());
			allHeaders.put(header.getName(), header.getValue());
			
		}
		return allHeaders;
	}
		
	
	
	public String getValueByJPath(CloseableHttpResponse closeableHttpResponse, String Element) throws JSONException, ParseException, IOException {
		
		Object obj=getJsonResponse(closeableHttpResponse);
		for(String s:Element.split("/"))
			if(!s.isEmpty())
				if(!(s.contains("[")||s.contains("]")))
					obj=((JSONObject)obj).get(s);
				else if(s.contains("[")||s.contains("]"))
				obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0])).get(Integer.parseInt(s.split("\\[")[1].replace("]", "")));
		return obj.toString();
		
	}
	
	public void getValue(CloseableHttpResponse closeableHttpResponse, String Element) throws ParseException, IOException {
		Object obj=getJsonResponse(closeableHttpResponse);
		((JSONObject)obj).get(Element);
	}
	
	
	public CloseableHttpResponse post(String url,String entityString,HashMap<String, String> headerMap) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient=HttpClients.createDefault();
		HttpPost httpPost=new HttpPost(url);
		httpPost.setEntity(new StringEntity(entityString));
		
		for(Map.Entry<String, String> entry:headerMap.entrySet())
			httpPost.addHeader(entry.getKey(), entry.getValue());
		
		CloseableHttpResponse response= httpClient.execute(httpPost);
		return response;
		
	}
	
}
