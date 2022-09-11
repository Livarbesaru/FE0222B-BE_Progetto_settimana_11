package com.dblibrary.controller.library;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dblibrary.model.library.Author;
import com.dblibrary.model.library.dto.AuthorDTO;
import com.dblibrary.service.library.AuthorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/author")
public class AuthorController {

	@Autowired
	private AuthorService authorService;
	
	@PostMapping("/add")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@SecurityRequirement(name="bearerAuth")
	@Operation(summary = "To add an author do not specify an id or it will throw an error,"
			+ " there can't be two author with same name")
	public ResponseEntity<AuthorDTO> addAuthor(@RequestBody Author author){
		return new ResponseEntity<AuthorDTO>(authorService.addAuthor(author).get(),HttpStatus.OK);
	}
	
	@PutMapping("/edit")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@SecurityRequirement(name="bearerAuth")
	@Operation(summary = "To edit an author do specify an id or it will throw an error,"
			+ " there can't be two author with same name")
	public ResponseEntity<AuthorDTO> editAuthor(@RequestBody Author author){
		return new ResponseEntity<AuthorDTO>(authorService.editAuthor(author).get(),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@SecurityRequirement(name="bearerAuth")
	@Operation(summary = "To delete author do specify an id or it will throw an error")
	public ResponseEntity<String> deleteAuthor(@PathVariable Long id){
		return new ResponseEntity<String>(authorService.deleteAuthor(id),HttpStatus.OK);
	}
	
	@GetMapping("/get/{id}")
	@Operation(summary = "To get an author do specify id or it will throw an error")
	public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id){
		return new ResponseEntity<AuthorDTO>(authorService.getById(id).get(),HttpStatus.OK);
	}
	
	@GetMapping("/get/all")
	public ResponseEntity<Set<AuthorDTO>> getAllAuthors(){
		return new ResponseEntity<Set<AuthorDTO>>(authorService.getAll().get(),HttpStatus.OK);
	}
}
