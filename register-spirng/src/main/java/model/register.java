package model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Entity
public class register {
	@Id
	@NotBlank(message = "idn cannot be blank!")
	@Pattern(
		regexp = "^(?:IDN(?:-13)?:? )?(?=[0-9]{13}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)97[89][- ]?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9]$", 
		message = "idn is invalid!"
	)
	
	private String idn = null;
	@Basic(optional = false)
	@NotBlank(message = "name cannot be blank!")
	@Pattern(
		regexp = "[A-Za-z0-9\\s]+",
		message = "title is invalid!"
	)
	
	private String name = null;
    private String category = null;
    @Basic(optional = false)
    @NotBlank(message = "date_of_birth cannot be blank!")
    private String date_of_birth = null;
    private String lastname = null;
    private String adress = null;
    private String age = null;
    @NotEmpty(message="list of amka cannot be empty!")
    @Basic(fetch = FetchType.EAGER)
    private List<@NotBlank(message="amka cannot be blank!") String> amka = null;
    
public register() {}
    
    private register(Builder builder) {
    	this.idn = builder.idn;
    	this.name = builder.name;
    	this.amka = builder.amka;
    	this.date_of_birth = builder.date_of_birth;
    	setCategory(builder.category);
    	setLastname(builder.lastname);
    	setAdress(builder.adress);
    	setAge(builder.age);
    	setAmka(builder.amka);
    }
    
    public static class Builder{
    	private String idn = null;
        private String name = null;
        private String category = null;
        private List<String> amka = new ArrayList<String>();
        private String date_of_birth = null;
        private String lastname = null;
        private String adress = null;
        private String age = null;
        
        private static void checkSingleValue(String value, String message) throws IllegalArgumentException{
        	if (value == null || value.trim().equals("")) throw new IllegalArgumentException(message + " cannot be null or empty");
        }
        
        private static void checkListString(List<String> list, String message) throws IllegalArgumentException{
        	if (list == null || list.isEmpty()) throw new IllegalArgumentException(message + " cannot be null or empty");
        }
        
        public Builder(String idn, String name, List<String> amka, String date_of_birth) throws IllegalArgumentException{
        	checkSingleValue(idn, "IDN");
        	checkSingleValue(name, "Name");
        	checkSingleValue(date_of_birth, "Date_of_birth");
        	checkListString(amka, "The list of amka");
        	
        	this.idn = idn;
        	this.name = name;
        	this.amka = amka;
        	this.date_of_birth = date_of_birth;
        }
        
        public Builder category(String value) {
        	this.category = value;
        	return this;
        }
        
        public Builder lastname(String value) {
        	this.lastname = value;
        	return this;
        }
        
        public Builder adress(String value) {
        	this.adress = value;
        	return this;
        }
        
        public Builder age(String value) {
        	this.age = value;
        	return this;
        }
        
        public register build() {
        	return new register(this);
        }
    }
    
    public String toString(){
    	return "register(" + idn + ", " + name + ", " + amka + ")";
    }

	public String getIdn() {
		return idn;
	}

	public void setIsbn(String idn) throws IllegalArgumentException{
		Builder.checkSingleValue(idn, "IDN");
		this.idn = idn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws IllegalArgumentException{
		Builder.checkSingleValue(name, "Name");
		this.name = name;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		if (category == null || category.trim().equals("")) this.category = null;
		else this.category = category.trim();
	}

	public List<String> getAmka() {
		return amka;
	}

	public void setAmka(List<String> amka) {
		Builder.checkListString(amka, "The list of amka");
		this.amka = amka;
	}

	public String getDate_of_birth() {
		return date_of_birth;
	}
	
	public void setDate_of_birth(String date_of_birth) throws IllegalArgumentException{
		Builder.checkSingleValue(date_of_birth, "date_of_birth");
		this.date_of_birth = date_of_birth;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		if (lastname == null || lastname.trim().equals("")) this.lastname = null;
		else this.lastname = lastname.trim();
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		if (adress == null || adress.trim().equals("")) this.adress = null;
		else this.adress = adress.trim();
	}

	public String getAge() {
		return age;
	}
	
	public void setAge(String age) {
		if (age == null || age.trim().equals("")) this.age = null;
		else this.age = age.trim();
	}
	
	public boolean equals(Object o) {
		if (o instanceof register) {
			register b = (register)o;
			if (b.getIdn().equals(idn)) return true;
		}
		
		return false;
	}
}

    
    
    
    
 