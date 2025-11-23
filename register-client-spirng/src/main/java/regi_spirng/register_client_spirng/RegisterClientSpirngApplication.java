package regi_spirng.register_client_spirng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.MediaType;

import regi_spirng.register_client_spirng.client.MyRestClient;
import regi_spirng.register_client_spirng.configuration.ImmutableApiConfiguration;



@SpringBootApplication
@EnableConfigurationProperties(ImmutableApiConfiguration.class)
public class RegisterClientSpringApplication implements CommandLineRunner{

	@Autowired
	private MyRestClient mrc;

	public static void main(String[] args) {
		SpringApplication.run(RegisterClientSpirngApplication.class, args);
	}
	
	@Override
	public void run(String... args) {
		MediaType xml = MediaType.APPLICATION_XML;
    	MediaType json = MediaType.APPLICATION_JSON;
    	String idn1 = "978-3-16-148410-0";
    	String idn2 = "978-3-16-148410-1";
    	//Adding two books
    	mrc.addregister(idn1,json);
    	mrc.addregister(idn2,json);
    	mrc.addregister(idn1,json);
    	
    	mrc.getregisters(xml);
    	mrc.getregisters(json);
    	
    	mrc.getregistersWithParams("Name1",null,xml);
    	mrc.getregistersWithParams(null,"Date_of_birth2",json);
    	
    	mrc.getregister(idn1,xml);
    	mrc.getregister(idn1,json);
    	
    	mrc.updateregister(idn1,json);
    	mrc.getregister(idn1,json);
    	
    	mrc.deleteregister(idn1);
    	mrc.deleteregister(idn1);
    	mrc.getregisters(json);
	}

}
