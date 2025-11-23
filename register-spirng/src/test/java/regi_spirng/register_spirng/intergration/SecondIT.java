package regi_spirng.register_spirng.intergration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.web.context.WebApplicationContext;

import regi_spirng.register_spirng.model.register;
import io.restassured.common.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import org.junit.jupiter.api.Order;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DisplayName("Adding Register Testing")
@TestMethodOrder(OrderAnnotation.class)
public class SecondIT implements TestLifecycleLogger{
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@BeforeAll
	public void initialiseRestAssuredMockMvcWebApplicationContext() {
	    RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
	}
	
	 @Test
	    @Order(1)
	    public void addWrongRegister() throws Exception{
	    	register register = new register();
	    	given().contentType("application/json").body(register).when().post("/api/registers").then().assertThat().statusCode(400);
	    }
	 
	 private register createRegister(String idn) {
	    	Random r = new Random();
	    	register register = new register();
	    	register.setIdn(idn);
	    	int pubId = r.nextInt(10) + 1;
	    	register.setDate_of_birth("Pub" + pubId);
	    	int nameId = r.nextInt(10) + 1;
	    	register.setName("Name" + nameId);
	    	List<String> amka = new ArrayList<String>();
	    	int amka1 = r.nextInt(10) + 1;
	    	int amka2 = r.nextInt(10) + 1;
	    	amka.add("Amka" + amka1);
	    	amka.add("Amka2" + amka2);
	    	register.setAmka(amka);
	    	
	    	return register;
	    }
	 
	 @Test
	    @Order(2)
	    public void postCorrectregister() throws Exception{
	    	register register = createregister("978-3-16-148410-0");
	    	given().contentType("application/json").body(register).when().post("/api/registers").then().assertThat().statusCode(201);
	    }
	 
	 @Test
	    @Order(3)
	    public void getExistingregister() throws Exception{
	    	given().accept("application/json").get("/api/registers/978-3-16-148410-0").then().
	    	assertThat().statusCode(200).and().body("idn", equalTo("978-3-16-148410-0"));
	    }
	    
	 @ParameterizedTest
	    @ValueSource(strings = { "978-3-16-148410-1", "978-3-16-148410-2", "978-3-16-148410-3" })
	    @Order(4)
	    void addCorrectregister(String idn) {
	    	register register = createregister(idn);
	    	given().contentType("application/json").body(register).when().post("/api/registers").then().assertThat().statusCode(201);
	    }
	    
	 @Test
	    @Order(5)
	    public void getExistingBooks() throws Exception{
	    	List<register> registers = given().accept("application/json").get("/api/registers").then().assertThat().statusCode(200).
	    			extract().as(new TypeRef<List<register>>(){});
	    	assertThat(registers, hasSize(4));
	    	assertThat(registers.get(0).getIdn(),anyOf(equalTo("978-3-16-148410-0"),equalTo("978-3-16-148410-1"),equalTo("978-3-16-148410-2"),equalTo("978-3-16-148410-3")));
	    	assertThat(registers.get(1).getIdn(),anyOf(equalTo("978-3-16-148410-0"),equalTo("978-3-16-148410-1"),equalTo("978-3-16-148410-2"),equalTo("978-3-16-148410-3")));
	    	assertThat(registers.get(2).getIdn(),anyOf(equalTo("978-3-16-148410-0"),equalTo("978-3-16-148410-1"),equalTo("978-3-16-148410-2"),equalTo("978-3-16-148410-3")));
	    	assertThat(registers.get(3).getIdn(),anyOf(equalTo("978-3-16-148410-0"),equalTo("978-3-16-148410-1"),equalTo("978-3-16-148410-2"),equalTo("978-3-16-148410-3")));
	    }
	 
	 @ParameterizedTest
	    @Order(6)
	    @ValueSource(strings = {"11", "12", "15", "19"})
	    public void useWrongDate_of_birth(String date_of_birthId) {
	    	given().accept("application/json").param("date_of_birth","Date_of_birth" + date_of_birthId).get("/api/registers").then().
	    	assertThat().statusCode(200).body(equalTo("[]"));
	    }    
}
	    
	 
	 
