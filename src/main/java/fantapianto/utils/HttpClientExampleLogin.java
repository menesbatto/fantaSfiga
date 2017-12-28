package fantapianto.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class HttpClientExampleLogin {

	private final static String USER_AGENT = "Mozilla/5.0";

	
	public static void main(String[] args) {
		ProxySelector proxy = new ProxySelector() {

			@Override
			public List<Proxy> select(URI uri) {
				System.err.println("Nicola #esciilproxy ----> " + uri.toString());
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.0.1.251", 3128));
				ArrayList<Proxy> list = new ArrayList<Proxy>();
				list.add(proxy);
				return list;
//				return ProxySelector.getDefault().select(uri);
			}

			@Override
			public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
				// TODO Auto-generated method stub

			}
		};
		ProxySelector.setDefault(proxy);
		
		try {
			sendGet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main2(String[] args) throws Exception {

		

		
//		
//		 DefaultHttpClient httpclient = new DefaultHttpClient();
//
//	    HttpGet httpget = new HttpGet("http://ipool.ba-berlin.de/stundenplaene.anzeige.php?faculty=15&course=6&type=html");
//
//	    HttpResponse response = httpclient.execute(httpget);
//	    HttpEntity entity = response.getEntity();
//
//	    System.out.println("Login form get: " + response.getStatusLine());
//	    if (entity != null) {
//	        entity.consumeContent();            
//	    }
//	    System.out.println("Initial set of cookies:");
//	    List<Cookie> cookies = httpclient.getCookieStore().getCookies();
//	    if (cookies.isEmpty()) {
//	        System.out.println("None");
//	    } else {
//	        for (int i = 0; i < cookies.size(); i++) {
//	            System.out.println("- " + cookies.get(i).toString());
//	        }
//	    }
//
//	    HttpPost httpost = new HttpPost("http://ipool.ba-berlin.de/main.php?action=login");
//
//	    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//	    nvps.add(new BasicNameValuePair("FORM_LOGIN_NAME", "MY_USERNAME"));
//	    nvps.add(new BasicNameValuePair("FORM_LOGIN_PASS", "MY_PASSWORD"));
//
//	    httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
//
//	    response = httpclient.execute(httpost);
//	    entity = response.getEntity();
//
//	    System.out.println("Login form get: " + response.getStatusLine());
//	    if (entity != null) {
//	        entity.consumeContent();
//	    }
//
//	    System.out.println("Post logon cookies:");
//	    cookies = httpclient.getCookieStore().getCookies();
//	    if (cookies.isEmpty()) {
//	        System.out.println("None");
//	    } else {
//	        for (int i = 0; i < cookies.size(); i++) {
//	            System.out.println("- " + cookies.get(i).toString());
//	        }
//	    }       
//
//	    httpclient.getConnectionManager().shutdown();
//		
//		
//		
		if (AppConstants.PROXY_ACTIVE){
			System.setProperty("http.proxyHost", AppConstants.PROXY_HOST);
			System.setProperty("http.proxyPort", AppConstants.PROXY_PORT);
		}

//		HttpClientExampleLogin http = new HttpClientExampleLogin();
////
////		System.out.println("Testing 1 - Send Http GET request");
////		http.sendGet();
//
//		System.out.println("\nTesting 2 - Send Http POST request");
//		http.sendPost();

	}
	private static void prova4() throws Exception {
	
		JSONObject json = new JSONObject();
		json.put("someKey", "someValue");    

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		try {
		    HttpPost request = new HttpPost("http://leghe.fantagazzetta.com/accaniti-division/formazioni?g=15");
		    StringEntity params = new StringEntity(json.toString());
		    request.addHeader("content-type", "application/json");
		    request.setEntity(params);
		    httpClient.execute(request);
		// handle response here...
		} catch (Exception ex) {
			System.out.println(ex);
		    // handle exception here
		} finally {
		    httpClient.close();
		}
	
	}
	// HTTP GET request
	private static void sendGet() throws Exception {

		String url = "http://www.google.com/search?q=developer";

		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		

		// add request header
		request.addHeader("User-Agent", USER_AGENT);

		HttpResponse response = client.execute(request);

		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " +
                       response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(
                       new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		System.out.println(result.toString());

	}

	// HTTP POST request
	private static void sendPost() throws Exception {
//		String url = "http://leghe.fantagazzetta.com/servizi/LG.asmx/lg";
		String url = "http://leghe.fantagazzetta.com/accaniti-division/formazioni?g=15";

		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);

		// add header
//		post.setHeader("User-Agent", USER_AGENT);
		post.setHeader("Content-Type", "application/json");
		post.setHeader("Cache-Control", "no-cache");
//		post.setHeader("Content-Type", "application/json");
//		post.setHeader("Content-Type", "application/json");
		
//		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
//		urlParameters.add(new BasicNameValuePair("u", AppConstants.user));
//		urlParameters.add(new BasicNameValuePair("p", AppConstants.password));
//		urlParameters.add(new BasicNameValuePair("aliaslega", "hppomezialeague"));
//		urlParameters.add(new BasicNameValuePair("check", "u1-L12"));
		
		//{u: "user", p: "password", aliaslega: "hppomezialeague", check: "u1-L12"}
		
//		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		HttpResponse response = client.execute(post);
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + post.getEntity());
		System.out.println("Response Code : " +
                                    response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		System.out.println(result.toString());

	}
	
	
	
	public static void prova3(){
		try {
			com.mashape.unirest.http.HttpResponse<String> asString = Unirest.post("http://leghe.fantagazzetta.com/servizi/LG.asmx/lg")
					.header("content-type", "application/json")
					.header("cache-control", "no-cache")
//					.header("postman-token", "25fdd741-6d56-f43f-d89f-5c467bc47f51")
					.body("{\"u\": \"nick23asr\", \"p\": \"asroma23\", \"aliaslega\": \"hppomezialeague\", \"check\": \"u1-L12\"}")
					.asString();
			System.out.println(asString);
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public static void prova2(){
//		OkHttpClient client = new OkHttpClient();
//		MediaType mediaType = MediaType.parse("application/json");
//		RequestBody body = RequestBody.create(mediaType, "{\"u\": \"nick23asr\", \"p\": \"asroma23\", \"aliaslega\": \"hppomezialeague\", \"check\": \"u1-L12\"}");
//		Request request = new Request.Builder()
//		.url("http://leghe.fantagazzetta.com/servizi/LG.asmx/lg")
//		.post(body)
//		.addHeader("content-type", "application/json")
//		.addHeader("cache-control", "no-cache")
//		.addHeader("postman-token", "e039b033-9977-d105-fb36-6632f4263d92")
//		.build();
//		Response response = client.newCall(request).execute();
//		
	}
	
	
	
	
	public static void prova(){
		HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead 

		try {

		    HttpPost request = new HttpPost("http://leghe.fantagazzetta.com/servizi/LG.asmx/lg");
		    StringEntity params =new StringEntity("details={\"u\":\"nick23asr\",\"p\":\"asroma23\",\"aliaslega\":\"hppomezialeague\",\"check\":\"u1-L12\"} ");
		    request.addHeader("content-type", "application/x-www-form-urlencoded");
		    request.setEntity(params);
		    HttpResponse response = httpClient.execute(request);
		    System.out.println(response);
		    //handle response here...

		}catch (Exception ex) {
			System.out.println(ex);
		    //handle exception here

		} finally {
		    //Deprecated
		    //httpClient.getConnectionManager().shutdown(); 
		}
		
	}

}