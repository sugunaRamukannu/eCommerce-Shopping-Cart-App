package sg.nus.iss.service.ecommerceapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.service.ecommerceapp.model.ProductCategory;
import sg.nus.iss.service.ecommerceapp.repository.ProductCategoryRepository;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
	@Autowired
	private ProductCategoryRepository productCategoryRepository;
	

	@Override
	public List<ProductCategory> findAllCategories() {
		// TODO Auto-generated method stub
		return productCategoryRepository.findAll();
		}

}
