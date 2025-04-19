package sg.nus.iss.service.ecommerceapp.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sg.nus.iss.service.ecommerceapp.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	List<Product> findByProductCategoryId(int categoryId);

	boolean existsById(Integer id);

	@Query("SELECT p FROM Product as p where p.productName like CONCAT('%',:k, '%') ")
	public ArrayList<Product> findByProductName(@Param("k") String keyword);

	@Query("Select p from Product as p JOIN p.productCategory c WHERE c.category like CONCAT('%', :k, '%') ")
	public ArrayList<Product> findByProductCategory(@Param("k") String keyword);

}
