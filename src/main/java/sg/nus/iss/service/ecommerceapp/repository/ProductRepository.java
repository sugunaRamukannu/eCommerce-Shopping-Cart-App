package sg.nus.iss.service.ecommerceapp.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.service.ecommerceapp.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	Optional<Product> findById(int productId);

	boolean existsById(Integer id);

	// Query method to find products by category and label
//	List<Product> findByCategoryCategoryNameAndLabelsContaining(String categoryName, String labels);

	@Query("SELECT p FROM Product as p where p.name like CONCAT('%',:k, '%') ")
	public ArrayList<Product> SearchProductByName(@Param("k") String keyword);

	@Query("Select p from Product as p JOIN p.productCategory c WHERE c.category like CONCAT('%', :k, '%') ")
	public ArrayList<Product> SearchProductByCategory(@Param("k") String keyword);

// Find products by category ID
	@Query("SELECT p FROM Product p WHERE p.productCategory.id = :id")
	List<Product> findByCategoryId(@Param("id")int categoryId);

}
