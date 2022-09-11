package com.dblibrary.model.library.dto;

import java.util.HashMap;
import java.util.Map;

import com.dblibrary.model.library.Author;
import com.dblibrary.model.library.Book;

import lombok.Getter;

@Getter
public class AuthorDTO {
	private Long idAuthor;
	private String name;
	private String surname;
	private Map<Long,String> books;
	
	public AuthorDTO() {}
	public AuthorDTO(Author author) {
		this.idAuthor=author.getIdAuthor();
		this.name=author.getName();
		this.surname=author.getSurname();
		this.books=new HashMap<>();
		if(author.getBooks()!=null) {
			for(Book b:author.getBooks()) {
				books.put(b.getIdBook(), b.getName());
			}
		}
	}
}
