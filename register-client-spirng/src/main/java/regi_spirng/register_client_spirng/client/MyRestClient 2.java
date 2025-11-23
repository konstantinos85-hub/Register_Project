package regi_spirng.register_client_spirng.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import regi_spirng.register.model.register;
import regi_spirng.register_client_spirng.configuration.ImmutableApiConfiguration;


@Component
public class MyRestClient {
	
private final ImmutableApiConfiguration details;
	
	private RestClient client;
	
	private static int counter = 1;
	
	public MyRestClient(ImmutableApiConfiguration details){
		this.details = details;
		initClient();
	}
	
	private void initClient() {
		String url = "http://" + details.getHost() + ":" + details.getPort() + "/" + details.getApi();
		client = RestClient.create(url);
	}
	
	public void getregisters(MediaType type) {
		client.get()
			.accept(type)
			.exchange(
				(request, response) -> {
					if (response.getStatusCode().is4xxClientError())
						System.out.println("Client error with HTTP Status Code: " + response.getStatusCode().value() +
							" and message: " + response.bodyTo(String.class));
					else if (response.getStatusCode().is5xxServerError())
						System.out.println("Server error with HTTP Status Code: " + response.getStatusCode().value() +
							" and message: " + response.bodyTo(String.class));
					else if (response.getStatusCode().is2xxSuccessful()) {
						System.out.println("The HTTP Status Code is: " + response.getStatusCode().value());
						System.out.println("The list of registers is: " + response.bodyTo(String.class));
					}
					return null;	
				}
			);
	}
	
	public void getregistersWithParams(String name, String date_of_birth, MediaType type) {
		String queryPart = "";
		if (name != null && !name.isBlank()) queryPart += "name=" + name;
		if (date_of_birth != null && !date_of_birth.isBlank()) queryPart += "date_of_birth=" + date_of_birth;
		if (!queryPart.isBlank()) queryPart = "?" + queryPart;
		client.get()
			.uri(queryPart)
			.accept(type)
			.exchange(
					
					(request, response) -> {
						if (response.getStatusCode().is4xxClientError())
							System.out.println("Client error with HTTP Status Code: " + response.getStatusCode().value() +
								" and message: " + response.bodyTo(String.class));
						else if (response.getStatusCode().is5xxServerError())
							System.out.println("Server error with HTTP Status Code: " + response.getStatusCode().value() +
								" and message: " + response.bodyTo(String.class));
						else if (response.getStatusCode().is2xxSuccessful()) {
							System.out.println("The HTTP Status Code is: " + response.getStatusCode().value());
							System.out.println("The list of books is: " + response.bodyTo(String.class));
						}
						return null;	
					}
				);
		}
	
	public void getregister(String idn, MediaType type) {
		client.get()
			.uri("/{id}",idn)
			.accept(type)
			.exchange(
				(request, response) -> {
					if (response.getStatusCode().is4xxClientError())
						System.out.println("Client error with HTTP Status Code: " + response.getStatusCode().value() +
							" and message: " + response.bodyTo(String.class));
					else if (response.getStatusCode().is5xxServerError())
						System.out.println("Server error with HTTP Status Code: " + response.getStatusCode().value() +
							" and message: " + response.bodyTo(String.class));
					else if (response.getStatusCode().is2xxSuccessful()) {
						System.out.println("The HTTP Status Code is: " + response.getStatusCode().value());
						System.out.println("The register with idn: " + isbn + " is: " + response.bodyTo(String.class));
					}
					return null;	
				}
			);
	}
	
	private register createBook(String idn) {
    	register register = new register();
    	register.setIdn(idn);
    	register.setName("Name" + counter);
    	register.setDate_of_birth("Date_of_birth" + counter);
    	List<String> amka = new ArrayList<String>();
    	amka.add("Amka1");
    	amka.add("Amka2");
    	register.setAmka(amka);
    	counter++;
    	
    	return register;
    }
	
	public void addregister(String idn, MediaType type) {
		register register = createregister(idn);
		client.post()
			.contentType(type)
			.body(register)
			.exchange(
				(request, response) -> {
					
					if (response.getStatusCode().is4xxClientError()) {
						System.out.println("Client error with HTTP Status Code: " + response.getStatusCode().value() +
									" and message: " + response.bodyTo(String.class));
					}
					else if (response.getStatusCode().is5xxServerError()) {
						System.out.println("Server error with HTTP Status Code: " + response.getStatusCode().value() +
									" and message: " + response.bodyTo(String.class));
					}
					else if (response.getStatusCode().is2xxSuccessful()) {
						System.out.println("The HTTP Status Code is: " + response.getStatusCode().value());
						System.out.println("The register with idn: " + isbn + " was created successfully");
						System.out.println("The created book's URL is: " + response.getHeaders().get("Location"));
					}
					return null;
				}
			);
	}
	
	public void updateregister(String idn, MediaType type) {
		register register = createregister(idn);
		register.setName("Name3");
		client.put()
			.uri("/{id}",idn)
			.contentType(type)
			.body(register)
			.exchange(
					
					(request, response) -> {
						if (response.getStatusCode().is4xxClientError()) {
							System.out.println("Client error with HTTP Status Code: " + response.getStatusCode().value() +
									" and message: " + response.bodyTo(String.class));
						}
						else if (response.getStatusCode().is5xxServerError()) {
							System.out.println("Server error with HTTP Status Code: " + response.getStatusCode().value() +
									" and message: " + response.bodyTo(String.class));
						}
						else if (response.getStatusCode().is2xxSuccessful()) {
							System.out.println("The HTTP Status Code is: " + response.getStatusCode().value());
							System.out.println("The register with idn: " + idn + " has been successfully updated");
						}
						return null;
					}
				);
		}
		
	public void deleteregister(String idn) {
		client.delete()
			.uri("/{id}",idn)
			.exchange(
				(request, response) -> {
					if (response.getStatusCode().is4xxClientError()) {
						System.out.println("Client error with HTTP Status Code: " + response.getStatusCode().value() +
								" and message: " + response.bodyTo(String.class));
					}
					else if (response.getStatusCode().is5xxServerError()) {
						System.out.println("Server error with HTTP Status Code: " + response.getStatusCode().value() +
								" and message: " + response.bodyTo(String.class));
					}
					else if (response.getStatusCode().is2xxSuccessful()) {
						System.out.println("The HTTP Status Code is: " + response.getStatusCode().value());
						System.out.println("The register with idn: " + idn + " has been successfully deleted");
					}
					return null;
				}
			);
	}
	

}
