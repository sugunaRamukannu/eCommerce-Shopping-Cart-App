package sg.nus.iss.service.ecommerceapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.service.ecommerceapp.model.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
	@Query("SELECT c.category FROM ProductCategory c WHERE c.category = :category")
	ProductCategory findByCategoryName(@Param("category")String categoryName);

}
