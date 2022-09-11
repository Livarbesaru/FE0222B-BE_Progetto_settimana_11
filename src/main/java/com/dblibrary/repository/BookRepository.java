package com.dblibrary.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dblibrary.model.library.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	
	public Optional<Book> findById(long id);
	
	@Query(value="Select * From Book as b WHERE b.id_book = "
			+ "any (Select ab.book_id From author_book as ab where ab.author_id = :idAuthor)", 
			nativeQuery=true)
	public Optional<Set<Book>> findByAuthor(@Param("idAuthor") long idAuthor);
	
	@Query(value="Select * From Book as b WHERE b.id_book = "
			+ "any (Select cb.book_id From category_book as cb Where cb.category_id = :idCategory)", 
			nativeQuery=true)
	public Optional<Set<Book>> findByCategory(@Param("idCategory") int idCategory);
}
