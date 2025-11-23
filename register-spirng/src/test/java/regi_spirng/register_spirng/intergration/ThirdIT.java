package regi_spirng.register_spirng.intergration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import regi_spirng.register_spirng.model.register;
import io.restassured.common.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import org.junit.jupiter.api.Order;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.List;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("Deleting register Testing")
public class ThirdIT implements TestLifecycleLogger{
	@Autowired
	private WebApplicationContext webApplicationContext;

	@BeforeAll
	public void initialiseRestAssuredMockMvcWebApplicationContext() {
	    RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
	}
	
	@Test
    @Order(1)
    public void deleteWrongregister() throws Exception{
    	given().delete("/api/books/978-3-16-148410-5").then().assertThat().statusCode(404);
    }
    
    @Test
    @Order(2)
    public void deleteCorrectregister() throws Exception{
    	given().delete("/api/registers/978-3-16-148410-0").then().assertThat().statusCode(204);
    }
    
    @Test
    @Order(3)
    public void getNonExistingregister() throws Exception{
    	given().accept("application/json").get("/api/registers/978-3-16-148410-0").then().assertThat().statusCode(404);
    }
    
    @Test
    @Order(4)
    public void getExistingregisters() throws Exception{
    	List<register> registers = given().accept("application/json").get("/api/registers").then().assertThat().
    			statusCode(200).extract().as(new TypeRef<List<register>>(){});
    	assertThat(registers, hasSize(3));
    	assertThat(registers.get(0).getIdn(),anyOf(equalTo("978-3-16-148410-1"),equalTo("978-3-16-148410-2"),equalTo("978-3-16-148410-3")));
    	assertThat(registers.get(1).getIdn(),anyOf(equalTo("978-3-16-148410-1"),equalTo("978-3-16-148410-2"),equalTo("978-3-16-148410-3")));
    	assertThat(registers.get(2).getIdn(),anyOf(equalTo("978-3-16-148410-1"),equalTo("978-3-16-148410-2"),equalTo("978-3-16-148410-3")));
    }
}
