package sg.nus.iss.service.ecommerceapp.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sg.nus.iss.service.ecommerceapp.model.ProductCategory;
import sg.nus.iss.service.ecommerceapp.model.ProductCategoryDto;

@Repository
public interface CategoryRepository extends JpaRepository<ProductCategory, Integer> {

	@Query("SELECT DISTINCT new sg.nus.iss.service.ecommerceapp.model.ProductCategoryDto(p.id, p.category) FROM ProductCategory p")
	List<ProductCategoryDto> findDistinctCategories();

}

