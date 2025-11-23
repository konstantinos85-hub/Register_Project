package regi_spirng.register_spirng.Unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import regi_spirng.register_spirng.model.register;
import regi_spirng.register_spirng.repository.RegisterRepository;


@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
@DataJpaTest
public class DBTest implements TestInterface{

	@Autowired
	RegisterRepository repo;
	
	
	@ParameterizedTest
	@Order(5)
	@CsvFileSource(resources="/positiveSingleRegister.csv")
	void checkPositiveSingleRegisterWithDB(ArgumentsAccessor accessor) {
		register register = RegisterUtility.createPositiveregister(accessor,0);
		repo.save(register);
		register = repo.findById(accessor.getString(0)).orElse(null);
		assertNotNull(register);
		RegisterUtility.checkregister(register,accessor);
	}
	
	
	@ParameterizedTest
	@Order(2)
	@CsvSource({
		"IDNNNN1, Name1, Amka1, Date_of_birth1",
		"IDNNNN2, Name2, Amka2, Date_of_birth2",
		"IDNNNN3, Name3, Amka3, Date_of_birth3",
	})
	void checkBookDeletion(String idn, String name, String amka, String date_of_birth) {
		List<String> amka = Arrays.asList(amka);
		register register = new register.Builder(idn,name,amka,date_of_birth).build();
		repo.save(register);
		repo.deleteById(idn);
		register = repo.findById(idn).orElse(null);
		assertNull(register);
	}
	
	
	@ParameterizedTest
	@Order(4)
	@CsvSource({
		"IDNNNN1, Name1, Amka1, Date_of_birth1",
		"IDNNNN2, Name2, Amka2, Date_of_birth2",
		"IDNNNN3, Name3, Amka3, Date_of_birth3",
	})
	void checkBookUpdate(String idn, String name, String amka, String date_of_birth) {
		List<String> amka = Arrays.asList(amka);
		register register = new register.Builder(idn,name,amka,date_of_birth).build();
		repo.save(register);
		Random r = new Random();
		int choice = r.nextInt(3);
		switch(choice) {
			case 0: register.setName("NAME"); break;
			case 1: register.setAmka(Arrays.asList("AMKA")); break;
			case 2: register.setDate_of_birth("DATE_OF_BIRTH"); break;
		}
		repo.save(register);
		register = repo.findById(idn).orElse(null);
		assertNotNull(register);
		if (choice == 0) {
			assertEquals(register.getDate_of_birth(),date_of_birth);
			assertEquals(register.getAmka().size(),1);
			assertEquals(register.getAmka().get(0),Amka);
			assertEquals(register.getName(),"NAME");
		}
		else if (choice == 1) {
			assertEquals(register.getDate_of_birth(),date_of_birth);
			assertEquals(register.getName(),name);
			assertEquals(register.getAmka().size(),1);
			assertEquals(register.getAmka().get(0),"AMKA");
		}
		else {
			assertEquals(register.getName(),name);
			assertEquals(register.getAmka().size(),1);
			assertEquals(register.getAmka().get(0),amka);
			assertEquals(register.getDate_of_birth(),"DATE_OF_BIRTH");
		}
	}
	
	
	@Test
	@Order(3)
	void checkBookRetrieval() {
		register register1 = new register.Builder("978-3-16-148410-0", "T1", Arrays.asList("Amka1","Amka2"), "P1").build();
		register register2 = new register.Builder("978-3-16-148410-1", "T2", Arrays.asList("Amka2","Amka3"), "P1").build();
		register register3 = new register.Builder("978-3-16-148410-2", "T3", Arrays.asList("Amka3","Amka1"), "P2").build();
		repo.save(register1);
		repo.save(register2);
		repo.save(register3);
		List<register> register = repo.findAll();
		
		//Checking if we have 3 books and these are book1, book2 & book3
		assertEquals(register.size(),3);
		int matches = 0;
		for (register register: register) {
			if (register.equals(register1) || register.equals(register2) || register.equals(register3)) {
				matches++;
			}
		}
		assertEquals(matches,3);
		
		//Checking that with 1 title, we get only one book
		register = repo.findByName("T1");
		assertEquals(register.size(),1);
		assertEquals(register.get(0),register1);
		register = repo.findByName("T2");
		assertEquals(register.size(),1);
		assertEquals(register.get(0),register2);
		
		//Checking that with one publisher, we get two books
		register = repo.findByPublisher("P1");
		assertEquals(register.size(),2);
		matches = 0;
		for (register register: register) {
			if (register.equals(register1) || register.equals(register2)) {
				matches++;
			}
		}
		assertEquals(matches,2);
	}
}