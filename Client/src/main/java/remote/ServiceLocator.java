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

	public ServiceLocator(String ip, int port) throws IOException {
		setService(ip, port);
	}
	public void setService(String ip, int port) throws IOException {
		URL url = new URL("http://"+ip+':'+port+'/');
		url.openConnection().connect();
		destination = url.toString();
	}
	public HttpResponse<String> sendGET(String method) throws URISyntaxException, InterruptedException, ExecutionException {
		return sendGET(method, Map.of());
		
	}
	public HttpResponse<String> sendGET(String method, Map<String, String> headers) throws URISyntaxException, InterruptedException, ExecutionException {
		HttpRequest.Builder request = HttpRequest.newBuilder()
				 .uri(new URI(destination + method));
		for(Entry<String, String> e : headers.entrySet())
			request.setHeader(e.getKey(), e.getValue());
		HttpResponse<String> response = client.sendAsync(request.GET().build(), BodyHandlers.ofString()).get();
		return response;
	}
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
	public HttpResponse<String> sendPOST(String method, JSONObject body) throws URISyntaxException, InterruptedException, ExecutionException {
		HttpRequest.Builder request = HttpRequest.newBuilder()
				 .uri(new URI(destination + method))
				 .setHeader("Content-Type", "application/json");
		BodyPublisher bodyP = BodyPublishers.ofString(body.toString());
		HttpResponse<String> response = client.sendAsync(request.POST(bodyP).build(), BodyHandlers.ofString()).get();
		return response;
	}
	public HttpResponse<String> sendDELETE(String method, Map<String, String> headers) throws URISyntaxException, InterruptedException, ExecutionException {
		HttpRequest.Builder request = HttpRequest.newBuilder()
				 .uri(new URI(destination + method));
		for(Entry<String, String> e : headers.entrySet())
			request.setHeader(e.getKey(), e.getValue());
		HttpResponse<String> response = client.sendAsync(request.DELETE().build(), BodyHandlers.ofString()).get();
		return response;
	}
}
