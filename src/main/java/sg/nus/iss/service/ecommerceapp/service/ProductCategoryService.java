package sg.nus.iss.service.ecommerceapp.service;

import java.util.List;

import sg.nus.iss.service.ecommerceapp.model.ProductCategory;
import sg.nus.iss.service.ecommerceapp.model.ProductCategoryDto;

public interface ProductCategoryService {

	//
	List<ProductCategory> getAllCategories();//home
	
	//API REACT
	List<ProductCategoryDto> findCategories();
	
	boolean productCategoryExists(int catgeoryID);

}
