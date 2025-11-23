package regi_spirng.register_spirng.Unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.params.aggregator.ArgumentsAccessor;

import regi_spirng.register_spirng.model.register;

public class RegisterUtility {
	
	public static register createPositiveregister(ArgumentsAccessor accessor, int start) {
		String idn = accessor.getString(start + 0);
		String name = accessor.getString(start + 1);
		String amka1 = accessor.getString(start + 2);
		String amka2 = accessor.getString(start + 3);
		List<String> amka = new ArrayList<String>();
		amka.add(amka1);
		if (amka2 != null && !amka2.trim().equals("")) amka.add(amka2);
		String date_of_birth = accessor.getString(start + 4);
		String category = accessor.getString(start + 5);
		String adress = accessor.getString(start + 6);
		String lastname = accessor.getString(start + 7);
		String age = accessor.getString(start + 8);
		
		return new register.Builder(idn, name, amka, date_of_birth).
				category(category).adress(adress).lastname(lastname).age(age).build();
	}
	
	
	public static void createNegativeregister(ArgumentsAccessor accessor) {
		String idn = accessor.getString(0);
		String name = accessor.getString(1);
		String amka1 = accessor.getString(2);
		String amka2 = accessor.getString(3);
		List<String> amka = new ArrayList<String>();
		if (amka1 != null && !amka1.trim().equals("")) amka.add(amka1);
		if (amka2 != null && !amka2.trim().equals("")) amka.add(amka2);
		Random r = new Random();
		if (r.nextInt(2) == 1) amka = null;
		String date_of_birth = accessor.getString(4);
		String category = accessor.getString(5);
		
		register register = new register.Builder(idn, name, amka, date_of_birth).
				category(category).build();
		System.out.println("Got register: " + register);
	}
	
	
	public static void checkregister(register register, ArgumentsAccessor accessor) {
		assertEquals(register.getIdn(),accessor.get(0));
		assertEquals(register.getName(),accessor.get(1));
		
		String amka1 = accessor.getString(2);
		String amka2 = accessor.getString(3);
		List<String> amka = new ArrayList<String>();
		amka.add(amka1);
		if (amka2 != null && !amka2.trim().equals("")) amka.add(amka2);
		assertEquals(amka, register.getAmka());
		
		assertEquals(register.getDate_of_birth(),accessor.getString(4));
		assertEquals(register.getCategory(),accessor.getString(5));
		assertEquals(register.getAdress(),accessor.getString(6));
		assertEquals(register.getLastname(),accessor.getString(7));
		assertEquals(register.getAge(),accessor.getString(8));
	}
}
