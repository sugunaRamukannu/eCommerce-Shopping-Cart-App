package sg.nus.iss.service.ecommerceapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sg.nus.iss.service.ecommerceapp.model.ProductCategory;
import sg.nus.iss.service.ecommerceapp.model.ProductCategoryDto;
import sg.nus.iss.service.ecommerceapp.repository.ProductCategoryRepository;
import sg.nus.iss.service.ecommerceapp.service.ProductCategoryService;

//Author(s): Yu Yaotian, Li Zhuoxuan, Pang Siang Lian, Irene Chan Oei Lin

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	@Override
	public List<ProductCategory> getAllCategories() {
		return productCategoryRepository.findAll();
	}

	@Override
	public List<ProductCategoryDto> findCategories() {
		return productCategoryRepository.findDistinctCategories();
	}

	@Override
	public boolean productCategoryExists(int categoryId) {
		return productCategoryRepository.existsById(categoryId);
	}

}
