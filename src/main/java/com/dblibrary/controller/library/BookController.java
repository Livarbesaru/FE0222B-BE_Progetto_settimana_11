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

import com.dblibrary.model.library.Book;
import com.dblibrary.model.library.dto.BookDTO;
import com.dblibrary.service.library.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookService bookService;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/add")
	@SecurityRequirement(name="bearerAuth")
	@Operation(summary = "To add a book do not specify an id or it will throw an error")
	public ResponseEntity<Book> addBook(@RequestBody Book book){
		return new ResponseEntity<Book>(bookService.addBook(book).get(), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/edit")
	@SecurityRequirement(name="bearerAuth")
	@Operation(summary = "To edit a book do specify an id or it will throw an error")
	public ResponseEntity<Book> editBook(@RequestBody Book book){
		return new ResponseEntity<Book>(bookService.editBook(book).get(), HttpStatus.OK);
	}
	
	@GetMapping("/get/{id}")
	@Operation(summary = "To get a book do specify id or it will throw an error")
	public ResponseEntity<BookDTO> getBookById(@PathVariable Long id){
		return new ResponseEntity<BookDTO>(bookService.getBookById(id).get(), HttpStatus.OK);
	}
	
	@GetMapping("/get/authorbooks/{id}")
	@Operation(summary = "To get some books based on the author do specify author's id or it will throw an error")
	public ResponseEntity<Set<BookDTO>> getBookByAuthor(@PathVariable Long id){
		return new ResponseEntity<Set<BookDTO>>(bookService.getBookByAuthor(id).get(), HttpStatus.OK);
	}
	
	@GetMapping("/get/categorybooks/{id}")
	@Operation(summary = "To get some books based on the category do specify category's id or it will throw an error")
	public ResponseEntity<Set<BookDTO>> getBookByCategory(@PathVariable Integer id){
		return new ResponseEntity<Set<BookDTO>>(bookService.getBookByCategory(id).get(), HttpStatus.OK);
	}
	
	@GetMapping("/get/all")
	public ResponseEntity<Set<BookDTO>> getAll(){
		return new ResponseEntity<Set<BookDTO>>(bookService.getAll().get(), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/delete/{id}")
	@SecurityRequirement(name="bearerAuth")
	@Operation(summary = "To delete a book do specify an id or it will throw an error")
	public ResponseEntity<String> deleteBookById(@PathVariable Long id){
		String nome=getBookById(id).getBody().getName();
		bookService.deleteBook(id);
		return new ResponseEntity<String>(nome+" e' stato cancellato", HttpStatus.OK);
	}
}
