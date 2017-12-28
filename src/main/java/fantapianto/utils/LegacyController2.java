package fantapianto.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class LegacyController2 {

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
		
//		getGiornata(null);
		
//		doCall("{\"username\": \"" + AppConstants.user + "\", \"password\": \"" + AppConstants.password + "\", \"aliaslega\": \"" + AppConstants.LEAGUE_NAME + "\", \"check\": \"u1-L12\"}");
		doCall("{\"u\": \"" + AppConstants.user + "\", \"p\": \"" + AppConstants.password + "\", \"aliaslega\": \"" + AppConstants.LEAGUE_NAME + "\", \"check\": \"u1-L12\"}");
	}

	private static String doCall(String requestPayload) {
		Client client = Client.create();
		WebResource webResource = null;

//		String urlToCall = "http://www.fantagazzetta.com/api/login/utenti";
		String urlToCall = "http://leghe.fantagazzetta.com/servizi/LG.asmx/lg";
		webResource = client.resource(urlToCall);
		WebResource.Builder builder = webResource.getRequestBuilder();
		// builder = builder.cookie(cookie).type("application/json");
//
//		__gads	ID=3ecdebe843ecb373:T=1484920604:S=ALNI_MadAzaRrGAbZhQ9GtWpGdixWoxbnQ		
//		_ga	GA1.2.1848207468.1484920677
//		_gat	1
//		_iub_cs-710963	%7B%22consent%22%3Atrue%2C%22timestamp%22%3A%222017-01-20T14%3A05%3A51.011Z%22%2C%22version%22%3A%220.13.9%22%2C%22id%22%3A710963%7D
//		comp_accaniti-divisionleghe2016-17	0=144022
		
	
		
		ClientResponse cuResponse = null;
		// case POST:
		builder.header("Content-Type", "application/json");
		
//		CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
		
		 cuResponse = builder.post(ClientResponse.class, requestPayload);
		// break;
		// case GET:
		//cuResponse = builder.get(ClientResponse.class);
		// break;
		 
		// case PUT:
//		 cuResponse = builder.put(ClientResponse.class, requestPayload);
		// break;
		// case DELETE:
		// cuResponse = builder.delete(ClientResponse.class);
		// break;
		//
		// default:
		// break;
		// }
//		 cuResponse.get
		String responseJson = null;

		if (cuResponse.getStatus() == 200) {
			System.out.println(cuResponse);
			MultivaluedMap<String, String> headers = cuResponse.getHeaders();
//			Map<String, String> map = prepareParameters(headers);
			
//			for (String k :map.keySet()){
//				System.out.println(k);
//				System.out.println(map.get(k));
//				System.out.println();
//			}
//			
			System.out.println();
			List<String> list = headers.get("Set-Cookie");
			for (String s:list){
//				System.out.println(s);
//				System.out.println();
			}
			List<NewCookie> cookies = cuResponse.getCookies();
			for(NewCookie c:cookies){
//				System.out.println(c.getName() + "\t" +  c.getValue() + "\n");
				
			}
			
			
			getGiornata(cuResponse);
//			getRegolamento(cuResponse);
			
			
			
			
			byte[] docStream = cuResponse.getEntity(byte[].class);
			
			FileOutputStream fos;
			try {
				fos = new FileOutputStream("C:/pere.txt");
				fos.write(docStream);
				fos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			Response build = Response
		            .ok(docStream, MediaType.APPLICATION_OCTET_STREAM)
		            .header("content-disposition","attachment; filename = doc.xlsx")
		            .build();
			
			System.out.println(build);
//			responseJson = cuResponse.getEntity(String.class);
		} else {
			// String errorMessage = String.format(
			// "LegacyException - JSID [%s]  call %s to %s  returned %s /n payload:/n %s",
			// jSessionId,
			// httpMethod.toString(), methodPath, cuResponse.getStatus(),
			// cuResponse.getStatusInfo(),
			// requestPayload);
		}
		return responseJson;
	}

	
	

	private static Map<String,String> prepareParameters(MultivaluedMap<String, String> queryParameters) {

		   Map<String,String> parameters = new HashMap<String,String>();

		   Iterator<String> it = queryParameters.keySet().iterator();


		         while(it.hasNext()){
		           String theKey = (String)it.next();
		           parameters.put(theKey,queryParameters.getFirst(theKey));
		       }

		   return parameters;

		    }
	
	private static void getRegolamento(ClientResponse cuResponse) {
		
		try {
			
			List<NewCookie> cookies = cuResponse.getCookies();

			
			Connection connect = Jsoup.connect("http://leghe.fantagazzetta.com/accaniti-division/visualizza-opzioni-calcolo1");
			
			connect.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			connect.header("Accept-Encoding", "gzip, deflate, sdch");
			connect.header("Accept-Language", "it-IT,it;q=0.8,en-US;q=0.6,en;q=0.4");
			connect.header("Cache-Control", "max-age=0");
			connect.header("Host", "leghe.fantagazzetta.com");
			connect.header("Proxy-Connection", "keep-alive");
//			connect.header("Referer", "http://leghe.fantagazzetta.com/hppomezialeague/calendario");
			connect.header("Upgrade-Insecure-Requests", "1");
			connect.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
			
			
			
			for (NewCookie c  :cookies){
				connect.cookie(c.getName(), c.getValue());
				System.out.println(c.getName() + "\t" + c.getValue());
			}		
			
			Document doc = connect.get();
			System.out.println(doc);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private static void getGiornata(ClientResponse cuResponse) {
		
		try {
			
			List<NewCookie> cookies = cuResponse.getCookies();

			
			Connection connect = Jsoup.connect("http://leghe.fantagazzetta.com/accaniti-division/formazioni?g=1");
			for (NewCookie c  :cookies){
				connect.cookie(c.getName(), c.getValue());
				System.out.println(c.getName() + "\t" + c.getValue());
			}
			
			
			
			
//			Accept : text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8
//			Accept-Encoding : gzip, deflate, sdch
//			Accept-Language : it-IT,it;q=0.8,en-US;q=0.6,en;q=0.4
//			Cache-Control : max-age=0
//			Host : leghe.fantagazzetta.com
//			Proxy-Connection : keep-alive
//			Referer : http://leghe.fantagazzetta.com/accaniti-division/calendario
//			Upgrade-Insecure-Requests : 1
//			User-Agent : Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36
//			Cookie : hppomezialeagueleghe2016-17=0=-1&1=hppomezialeague&2=sgwObOf11ULEgp5giBnyZA==&3=lokomotiv birra&4=2wSpXbrUP7VFdV8D+VqhRg==&5=0&6=23/01/2017 11:44:25; accaniti-divisionleghe2016-17=0=-1&1=accaniti division&2=GQFVuwVk6BLEgp5giBnyZA==&3=catenaccio united&4=gh+425oRUc8/e0aLvrp7Vw==&5=0&6=23/01/2017 11:44:25; LegheFGleghe2016-17=0=kDHCmsodCEnEgp5giBnyZA==&1=0VaNsL3XhfCtT97DGm4wZNz6S6eifOk6&2=23/01/2017 11:44:25&3=qWo8L42eDWVUdPFJZ1CqeKlqPC+Nng1lRpL1NfLATlKnsfigtaqX3T6zognyFtoYxCOGqE9WC8QZNDx3+bqzXh0EDsYyvrNAazrtjTay6mk=;
			
			connect.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			connect.header("Accept-Encoding", "gzip, deflate, sdch");
			connect.header("Accept-Language", "it-IT,it;q=0.8,en-US;q=0.6,en;q=0.4");
			connect.header("Cache-Control", "max-age=0");
			connect.header("Host", "leghe.fantagazzetta.com");
			connect.header("Proxy-Connection", "keep-alive");
			connect.header("Referer", "http://leghe.fantagazzetta.com/accaniti-division/calendario");
			connect.header("Upgrade-Insecure-Requests", "1");
			connect.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
			
//			connect.header("Cookie", "hppomezialeagueleghe2016-17=0=-1&1=hppomezialeague&2=sgwObOf11ULEgp5giBnyZA==&3=lokomotiv birra&4=2wSpXbrUP7VFdV8D+VqhRg==&5=0&6=23/01/2017 11:44:25; accaniti-divisionleghe2016-17=0=-1&1=accaniti division&2=GQFVuwVk6BLEgp5giBnyZA==&3=catenaccio united&4=gh+425oRUc8/e0aLvrp7Vw==&5=0&6=23/01/2017 11:44:25; LegheFGleghe2016-17=0=kDHCmsodCEnEgp5giBnyZA==&1=0VaNsL3XhfCtT97DGm4wZNz6S6eifOk6&2=23/01/2017 11:44:25&3=qWo8L42eDWVUdPFJZ1CqeKlqPC+Nng1lRpL1NfLATlKnsfigtaqX3T6zognyFtoYxCOGqE9WC8QZNDx3+bqzXh0EDsYyvrNAazrtjTay6mk=;");
//			connect.header("ccc", "ccc");
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
//			connect.cookie("__gads", "ID=3ecdebe843ecb373:T=1484920604:S=ALNI_MadAzaRrGAbZhQ9GtWpGdixWoxbnQ");
//			connect.cookie("_ga","GA1.2.1848207468.1484920677");
//			connect.cookie("_gat","1");
//			connect.cookie("_iub_cs-710963","%7B%22consent%22%3Atrue%2C%22timestamp%22%3A%222017-01-20T14%3A05%3A51.011Z%22%2C%22version%22%3A%220.13.9%22%2C%22id%22%3A710963%7D");
//			connect.cookie("comp_accaniti-divisionleghe2016-17","0=144022");
			
			
			
			
			
			Document doc = connect.get();
			System.out.println(doc);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		CookieStore cookieStore = new BasicCookieStore(); 
//		BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", mySessionId);
//
//		cookieStore.addCookie(cookie); 
//		client.
		
	}

	

	
	
	
//	@Path("PDF-file.pdf/")
//	@GET
//	@Produces({"application/pdf"})
//	public StreamingOutput getPDF() throws Exception {
//	    return new StreamingOutput() {
//	        public void write(OutputStream output) throws IOException, WebApplicationException {
//	            try {
//	                PDFGenerator generator = new PDFGenerator(getEntity());
//	                generator.generatePDF(output);
//	            } catch (Exception e) {
//	                throw new WebApplicationException(e);
//	            }
//	        }
//	    };
//	}
	
}
