package com.dblibrary.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.dblibrary.model.library.Author;

@Transactional
public interface AuthorRepository extends JpaRepository<Author, Long> {
	
	Optional<Author> findByNameAndSurname(String name,String surname);
	
	@Modifying
	@Query(value="DELETE FROM public.author_book as ab"
			+ "	WHERE ab.author_id = :idAuthor",nativeQuery = true)
	public Integer deleteAuthorBook(@Param("idAuthor") Long idCategory);
	
	@Modifying
	@Query(value="DELETE FROM public.author as a"
			+ "	WHERE a.id_author = :idAuthor",nativeQuery = true)
	public Integer deleteAuthor(@Param("idAuthor") Long idCategory);
}
