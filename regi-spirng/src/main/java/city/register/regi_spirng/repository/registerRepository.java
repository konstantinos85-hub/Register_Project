package city.register.regi_spirng.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import city.register.regi_spirng.model.register;
import jakarta.validation.Valid;

public interface registerRepository extends JpaRepository <register, String> {
	
	List<register> findByName(String name);
	List<register> findByDate_of_birth(String date_of_birth);
	List<register> findByNameAndDate_of_birth(String name, String date_of_birth);
	List<register> findAll();
	
	
	
	
}


