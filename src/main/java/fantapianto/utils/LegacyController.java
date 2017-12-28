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
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class LegacyController {

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
		
		doCall("");
	}

	private static String doCall(String requestPayload) {
		Client client = Client.create();
		WebResource webResource = null;

		String urlToCall = "http://leghe.fantagazzetta.com/accaniti-division/formazioni?g=15";
		webResource = client.resource(urlToCall);
		WebResource.Builder builder = webResource.getRequestBuilder();
		// builder = builder.cookie(cookie).type("application/json");
		ClientResponse cuResponse = null;
		// case POST:
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
