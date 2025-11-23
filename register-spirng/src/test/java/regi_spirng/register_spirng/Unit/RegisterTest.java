package regi_spirng.register_spirng.Unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import regi_spirng.register_spirng.model.register;

public class registerTest implements TestInterface{
	
	@ParameterizedTest
	@CsvFileSource(resources="/positiveSingleregister.csv")
	void checkPositiveSingleregister(ArgumentsAccessor accessor) {
		register register = registerUtility.createPositiveregister(accessor,0);
		registerUtility.checkregister(register,accessor);
	}
	
	@ParameterizedTest
	@CsvFileSource(resources="/negativeSingleregister.csv")
	void checkNegativeSingleregister(ArgumentsAccessor accessor) {
		Exception e = assertThrows(IllegalArgumentException.class, ()-> registerUtility.createNegativeregister(accessor));
		assertEquals(accessor.getString(6), e.getMessage());
	}
	
	@Test
	void checkEmptyConstructor() {
		register register = new register();
		assertNull(register.getIdn());
		assertNull(register.getName());
		assertNull(register.getAmka());
		assertNull(register.getCategory());
		assertNull(register.getDate_of_birth());
		assertNull(register.getAdress());
		assertNull(register.getLastname());
		assertNull(register.getAge());
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { " ", "   ", "\t", "\n" })
	void nullEmptyAndBlankStringsforIDN(String idn) {
		register register = new register();
		Exception e = assertThrows(IllegalArgumentException.class, ()-> register.setIdn(idn));
		assertEquals("IDN cannot be null or empty", e.getMessage());
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "IDN1", "IDN2", "I", "123" })
	void checkProperStringsforISBN(String idn) {
		register register = new register();
		register.setIsbn(idn);
		assertEquals(idn,register.getIdn());
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { " ", "   ", "\t", "\n" })
	void nullEmptyAndBlankStringsforName(String name) {
		register register = new register();
		Exception e = assertThrows(IllegalArgumentException.class, ()-> register.setName(name));
		assertEquals("Name cannot be null or empty", e.getMessage());
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "Name1", "Name1", "T", "Here is a Name" })
	void checkProperStringsforName(String name) {
		register register = new register();
		register.setName(name);
		assertEquals(name,register.getName());
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { " ", "   ", "\t", "\n" })
	void nullEmptyAndBlankStringsforDate_of_birth(String date_of_birth) {
		register register = new register();
		Exception e = assertThrows(IllegalArgumentException.class, ()-> register.setDate_of_birth(date_of_birth));
		assertEquals("Date_of_birth cannot be null or empty", e.getMessage());
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "P1", "Date_of_birth2", "Date_of_birth", "Date_of_birth Name1" })
	void checkProperStringsforDate_of_birth(String date_of_birth) {
		register register = new register();
		register.setDate_of_birth(date_of_birth);
		assertEquals(date_of_birth,register.getDate_of_birth());
	}
	
	@ParameterizedTest
	@MethodSource("negativeStringList")
	void nullEmptyListforAmka(List<String> amka) {
		register register = new register();
		Exception e = assertThrows(IllegalArgumentException.class, ()-> register.setAmka(amka));
		assertEquals("The list of amka cannot be null or empty", e.getMessage());
	}
	
	static Stream<List<String>> negativeStringList() {
		return Stream.of(null, new ArrayList<String>());
	}
	
	@ParameterizedTest
	@MethodSource("positiveStringList")
	void checkProperListforAmka(List<String> amka) {
		register register = new register();
		register.setAmka(amka);
		assertEquals(amka,register.getAmka());
	}
	
	static Stream<List<String>> positiveStringList() {
		return Stream.of(Arrays.asList("Amka1","Amka2"), Arrays.asList("A1","A2"), Arrays.asList("Amka A1","Amka A2"));
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { " ", "   ", "\t", "\n" })
	void checkNegativeValsforCategory(String category) {
		register register = new register();
		register.setCategory(category);
		assertNull(register.getCategory());
	}
	
	//Checking positive values for category
	@ParameterizedTest
	@ValueSource(strings = { "c", "Cat1", "Cat X", "C C C" })
	void checkPositiveValsforCategory(String category) {
		register register = new register();
		register.setCategory(category);
		assertEquals(register.getCategory(),category);
	}
	
	//Checking negative values for summary
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { " ", "   ", "\t", "\n" })
	void checkNegativeValsforAdress(String adress) {
		register register = new register();
		register.setAdress(adress);
		assertNull(register.getAdress());
	}
		
	//Checking positive values for summary
	@ParameterizedTest
	@ValueSource(strings = { "s", "Adr1", "ADR X", "S X D" })
	void checkPositiveValsforAdress(String adress) {
		register register = new register();
		register.setAdress(adress);
		assertEquals(register.getAdress(),adress);
	}
	
	//Checking negative values for language
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { " ", "   ", "\t", "\n" })
	void checkNegativeValsforLastname(String lastname) {
		register register = new register();
		register.setLastname(lastname);
		assertNull(register.getLastname());
	}
			
	//Checking positive values for category
	@ParameterizedTest
	@ValueSource(strings = { "l", "Last1", "Last X", "L X D" })
	void checkPositiveValsforLastname(String lastname) {
		register register = new register();
		register.setLastname(lastname);
		assertEquals(register.getLastname(),lastname);
	}
	
	//Checking negative values for date
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { " ", "   ", "\t", "\n" })
	void checkNegativeValsforDate(String age) {
		register register = new register();
		register.setAge(age);
		assertNull(register.getAge());
	}
				
	//Checking positive values for date
	@ParameterizedTest
	@ValueSource(strings = { "d", "AGE", "12/12/1212", "22/12/2222" })
	void checkPositiveValsforDate(String age) {
		register register = new register();
		register.setDate(age);
		assertEquals(register.getAge(),age);
	}
	
	
	
}
	
