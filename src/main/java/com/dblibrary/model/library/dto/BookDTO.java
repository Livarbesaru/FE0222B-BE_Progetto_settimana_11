package com.dblibrary.model.library.dto;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;

import com.dblibrary.model.library.Author;
import com.dblibrary.model.library.Book;
import com.dblibrary.model.library.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
public class BookDTO {
	private long idBook;
	private String name;
	private Map<Long,String> authors;
	private Map<Integer,String> categories;
	private double price;
	private Date date;
	
	public BookDTO() {
	}
	
	public BookDTO(Book book) {
		this.idBook=book.getIdBook();
		this.name=book.getName();
		this.price=book.getPrice();
		this.date=book.getDate();
		this.authors=new HashMap<>();
		this.categories=new HashMap<>();
		if(book.getAuthors()!=null) {
			for(Author a:book.getAuthors()) {
				authors.put(a.getIdAuthor(), a.getName()+" "+a.getSurname());
			}
		}
		if(book.getCategories()!=null) {
			for(Category c:book.getCategories()) {
				categories.put(c.getIdCategory(), c.getName());
			}
		}
	}
}
