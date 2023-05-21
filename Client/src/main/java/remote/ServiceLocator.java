package remote;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import org.json.JSONObject;

public class ServiceLocator {
	private String destination;
	private HttpClient client = HttpClient.newHttpClient();
	/**
	 * A class to handle the http exchanges with the server
	 * @param ip ip of the server
	 * @param port port to connect to
	 * @throws IOException Throws if the server can't be reached
	 */
	public ServiceLocator(String ip, int port) throws IOException {
		setService(ip, port);
	}
	/**
	 * Changes the server location to connect to
	 * @param ip ip of the server
	 * @param port port of the server to connect to
	 * @throws IOException Throws if the server can't be reached
	 */
	public void setService(String ip, int port) throws IOException {
		URL url = new URL("http://"+ip+':'+port+'/');
		url.openConnection().connect();
		destination = url.toString();
	}
	/**
	 * Sends a blank get request
	 * @param method method to send the request to in the server
	 * @return the response of the server
	 * @throws URISyntaxException If method doesn't comply with URI syntax
	 * @throws InterruptedException Server connection has been interrupted
	 * @throws ExecutionException Should not be thrown
	 */
	public HttpResponse<String> sendGET(String method) throws URISyntaxException, InterruptedException, ExecutionException {
		return sendGET(method, Map.of());
		
	}
	/**
	 * Sends a get request with custom headers
	 * @param method method to send the request to in the server
	 * @param headers a map of headers where the key it's the name of the header and the value in the map it's the value of the header
	 * @return the response of the server
	 * @throws URISyntaxException If method doesn't comply with URI syntax
	 * @throws InterruptedException Server connection has been interrupted
	 * @throws ExecutionException Should not be thrown
	 */
	public HttpResponse<String> sendGET(String method, Map<String, String> headers) throws URISyntaxException, InterruptedException, ExecutionException {
		HttpRequest.Builder request = HttpRequest.newBuilder()
				 .uri(new URI(destination + method));
		for(Entry<String, String> e : headers.entrySet())
			request.setHeader(e.getKey(), e.getValue());
		HttpResponse<String> response = client.sendAsync(request.GET().build(), BodyHandlers.ofString()).get();
		return response;
	}
	/**
	 * Sends a complete post request with a JSON body
	 * @param method method to send the request to in the server
	 * @param headers a map of headers where the key it's the name of the header and the value in the map it's the value of the header
	 * @param body body of the post request to send
	 * @return the response of the server
	 * @throws URISyntaxException If method doesn't comply with URI syntax
	 * @throws InterruptedException Server connection has been interrupted
	 * @throws ExecutionException Should not be thrown
	 */
	public HttpResponse<String> sendPOST(String method, Map<String, String> headers, JSONObject body) throws URISyntaxException, InterruptedException, ExecutionException {
		HttpRequest.Builder request = HttpRequest.newBuilder()
				 .uri(new URI(destination + method))
				 .setHeader("Content-Type", "application/json");
		for(Entry<String, String> e : headers.entrySet())
			request.setHeader(e.getKey(), e.getValue());
		BodyPublisher bodyP = BodyPublishers.ofString(body.toString());
		HttpResponse<String> response = client.sendAsync(request.POST(bodyP).build(), BodyHandlers.ofString()).get();
		return response;
	}
	/**
	 * Sends a complete post request with a blob body
	 * @param method method to send the request to in the server
	 * @param headers a map of headers where the key it's the name of the header and the value in the map it's the value of the header. WARNING: Content-Type it's not defined by default
	 * @param body body of the post request to send
	 * @return the response of the server
	 * @throws URISyntaxException If method doesn't comply with URI syntax
	 * @throws InterruptedException Server connection has been interrupted
	 * @throws ExecutionException Should not be thrown
	 */
	public HttpResponse<String> sendPOST(String method, Map<String, String> headers, byte[] body) throws URISyntaxException, InterruptedException, ExecutionException {
		HttpRequest.Builder request = HttpRequest.newBuilder()
				 .uri(new URI(destination + method));
		for(Entry<String, String> e : headers.entrySet())
			request.setHeader(e.getKey(), e.getValue());
		BodyPublisher bodyP = BodyPublishers.ofByteArray(body);
		HttpResponse<String> response = client.sendAsync(request.POST(bodyP).build(), BodyHandlers.ofString()).get();
		return response;
	}
	/**
	 * Sends a simple post request with a JSON body
	 * @param method method to send the request to in the server
	 * @param headers a map of headers where the key it's the name of the header and the value in the map it's the value of the header
	 * @param body body of the post request to send
	 * @return the response of the server
	 * @throws URISyntaxException If method doesn't comply with URI syntax
	 * @throws InterruptedException Server connection has been interrupted
	 * @throws ExecutionException Should not be thrown
	 */
	public HttpResponse<String> sendPOST(String method, JSONObject body) throws URISyntaxException, InterruptedException, ExecutionException {
		HttpRequest.Builder request = HttpRequest.newBuilder()
				 .uri(new URI(destination + method))
				 .setHeader("Content-Type", "application/json");
		BodyPublisher bodyP = BodyPublishers.ofString(body.toString());
		HttpResponse<String> response = client.sendAsync(request.POST(bodyP).build(), BodyHandlers.ofString()).get();
		return response;
	}
	/**
	 * Sends a complete delete request
	 * @param method method to send the request to in the server
	 * @param headers a map of headers where the key it's the name of the header and the value in the map it's the value of the header
	 * @return the response of the server
	 * @throws URISyntaxException If method doesn't comply with URI syntax
	 * @throws InterruptedException Server connection has been interrupted
	 * @throws ExecutionException Should not be thrown
	 */
	public HttpResponse<String> sendDELETE(String method, Map<String, String> headers) throws URISyntaxException, InterruptedException, ExecutionException {
		HttpRequest.Builder request = HttpRequest.newBuilder()
				 .uri(new URI(destination + method));
		for(Entry<String, String> e : headers.entrySet())
			request.setHeader(e.getKey(), e.getValue());
		HttpResponse<String> response = client.sendAsync(request.DELETE().build(), BodyHandlers.ofString()).get();
		return response;
	}
}
