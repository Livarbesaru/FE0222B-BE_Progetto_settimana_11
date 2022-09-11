package com.dblibrary.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.dblibrary.model.library.Category;

@Transactional
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	public Optional<Category> findByName(String name);
	
	@Modifying
	@Query(value="DELETE FROM public.category_book as cb"
			+ "	WHERE cb.category_id = :idCategory",nativeQuery = true)
	public Integer deleteCategoryBook(@Param("idCategory") Integer idCategory);
	
	@Modifying
	@Query(value="DELETE FROM public.category as c"
			+ "	WHERE c.id_category = :idCategory",nativeQuery = true)
	public Integer deleteCategory(@Param("idCategory") Integer idCategory);
}
