package regi_spirng.register_spirng.Unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import regi_spirng.register_spirng.model.register;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class RegisterValidTest implements TestInterface{
	private static Validator validator = null;
	
	@BeforeAll
	static void constructValidator() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	private static List<String> getMessages(Set<ConstraintViolation<register>> viols){
		List<String> messages = new ArrayList<String>();
		for (ConstraintViolation<register> viol: viols) {
			messages.add(viol.getMessage());
		}
		return messages;
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "IDN1", "IDN2", "I", "123" })
	void checkInvalidISBN(String idn) {
		register register = new register();
		register.setIdn(idn);
		assertEquals(idn,register.getIdn());
		
		Set<ConstraintViolation<register>> viols = validator.validate(register);
		assertNotEquals(viols.size(), 0);
		
		List<String> messages = getMessages(viols);
		assertTrue(messages.contains("idn is invalid!"));
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "978-3-16-148410-0", "978-3-16-148410-1", "978-3-16-148410-2"})
	void checkValidISBN(String idn) {
		register register = new register();
		register.setIdn(idn);
		assertEquals(idn,register.getIdn());
		
		Set<ConstraintViolation<register>> viols = validator.validate(register);
		assertNotEquals(viols.size(), 0);
		
		List<String> messages = getMessages(viols);
		assertFalse(messages.contains("idn is invalid!"));
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "*register", "^register", "register&", "register%2" })
	void checkInvalidTitle(String name) {
		register register = new register();
		register.setName(name);
		assertEquals(name,register.getName());
		
		Set<ConstraintViolation<register>> viols = validator.validate(register);
		assertNotEquals(viols.size(), 0);
		
		List<String> messages = getMessages(viols);
		assertTrue(messages.contains("name is invalid!"));
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "My Java register", "My Spring Boot register", "My REST register"})
	void checkValidTitle(String name) {
		register register = new register();
		register.setName(name);
		assertEquals(name,register.getName());
		
		Set<ConstraintViolation<register>> viols = validator.validate(register);
		assertNotEquals(viols.size(), 0);
		
		List<String> messages = getMessages(viols);
		assertFalse(messages.contains("name is invalid!"));
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { " ", "\t", "\n", " \t \n" })
	void checkInvalidAmkaName(String amka) {
		register register = new register();
		List<String> amka = new ArrayList<String>();
		amka.add(amka);
		register.setAmka(amka);
		assertIterableEquals(amka,register.getAmka());
		
		Set<ConstraintViolation<register>> viols = validator.validate(register);
		assertNotEquals(viols.size(), 0);
		
		List<String> messages = getMessages(viols);
		assertTrue(messages.contains("amka name cannot be blank!"));
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "A", "Amka1", "Amka", "Amka A" })
	void checkValidAmkaName(String amka) {
		register register = new register();
		List<String> amka = new ArrayList<String>();
		amka.add(amka);
		register.setAmka(amka);
		assertIterableEquals(amka,register.getAmka());
		
		Set<ConstraintViolation<register>> viols = validator.validate(register);
		assertNotEquals(viols.size(), 0);
		
		List<String> messages = getMessages(viols);
		assertFalse(messages.contains("amka cannot be blank!"));
	}
}