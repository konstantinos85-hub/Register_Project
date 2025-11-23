package city.register.regi_spirng.controller;

import java.net.InetAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import city.register.regi_spirng.model.register;
import city.register.regi_spirng.repository.registerRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/registers")
public class registerController {
	private final registerRepository repo;
	
	registerController(registerRepository repo){
		this.repo = repo;
	}
	
	@GetMapping(produces = {"application/json", "application/xml"})
	List<register> getregisters(@RequestParam(required = false) String name, @RequestParam(required = false) String date_of_birth){
		boolean nameNotEmpty = (name != null && !name.isBlank());
		boolean date_of_birthNotEmpty = (date_of_birth != null && !date_of_birth.isBlank());
		
		if (!nameNotEmpty && !date_of_birthNotEmpty) return repo.findAll();
		else {
			if (nameNotEmpty && !date_of_birthNotEmpty) return repo.findByName(name);
			else if (nameNotEmpty) return repo.findByNameAndDate_of_birth(name,date_of_birth);
			else return repo.findByDate_of_birth(date_of_birth);
		}
	}
	
	@GetMapping(value = "{id}", produces = {"application/json", "application/xml"})
	register getregister(@PathVariable String id) {
		return repo.findById(id).orElseThrow(
			() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Register with given id does not exist!"));
	}
	
	@PostMapping(consumes = {"application/json", "application/xml"})
	ResponseEntity<?> insertBook(@Valid @RequestBody register register) {
		if (repo.findById(register.getIdn()).isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Register with given id already exists!");
		else {
			repo.save(register);
			try {
				String url = "http://" + InetAddress.getLocalHost().getHostName() + ":8080/api/registers/" + register.getIdn();
				return ResponseEntity.created(new URI(url)).build();
			}
			catch(Exception e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while generating the response!");
			}
		}
	}
	
	@PutMapping(value = "{id}", consumes = {"application/json", "application/xml"})
	ResponseEntity<?> updateregister(@PathVariable String id, @Valid @RequestBody register register) {
		if (!register.getIdn().equals(id))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trying to update book with wrong id!");
		else return repo.findById(id)
	      .map(oldregister -> {
	    	  oldregister.setName(register.getName());
	    	  oldregister.setDate_of_birth(register.getDate_of_birth());
	    	  oldregister.setCategory(register.getCategory());
	    	  oldregister.setAmka(register.getAmka());
	    	  oldregister.setAge(register.getAge());
	    	  oldregister.setLastname(register.getLastname());
	    	  oldregister.setAdress(register.getAdress());
	          repo.save(oldregister);
	          return ResponseEntity.noContent().build();
	        })
	      .orElseThrow(() -> 
	      	new ResponseStatusException(HttpStatus.NOT_FOUND, "Register with given id does not exist!"));
	}
	
	@DeleteMapping("{id}")
	ResponseEntity<?> deleteregister(@PathVariable String id) {
		return repo.findById(id)
			    .map(oldregister -> {
			         repo.deleteById(id);
			         return ResponseEntity.noContent().build();
			    })
			    .orElseThrow(() -> 
			      	 new ResponseStatusException(HttpStatus.NOT_FOUND, "register with given id does not exist!"));
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        System.out.println("Fieldname is: " + fieldName + " ErrorMessage:" + errorMessage);
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
}
	
	
	
	
	
	
	
	
	
	