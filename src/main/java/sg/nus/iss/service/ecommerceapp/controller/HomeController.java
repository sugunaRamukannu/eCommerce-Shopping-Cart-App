package sg.nus.iss.service.ecommerceapp.controller;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sg.nus.iss.service.ecommerceapp.exception.CategoryNotFoundException;
import sg.nus.iss.service.ecommerceapp.exception.InvalidSearchTypeException;
import sg.nus.iss.service.ecommerceapp.model.CartSummary;
import sg.nus.iss.service.ecommerceapp.model.Product;
import sg.nus.iss.service.ecommerceapp.model.ProductCategory;
import sg.nus.iss.service.ecommerceapp.service.ProductCategoryService;
import sg.nus.iss.service.ecommerceapp.service.ProductService;
import sg.nus.iss.service.ecommerceapp.service.ShoppingCartService;

//Author(s): Yu Yaotian, Li Zhuoxuan, Pang Siang Lian, Irene Chan Oei Lin

@Controller
public class HomeController {

	@Autowired
	private ProductService productService;

	@Autowired
	private ShoppingCartService shoppingCartService;

	@Autowired
	private ProductCategoryService productCategoryService;

	@GetMapping("/")
	public String homePage(Model model) {
		List<ProductCategory> categories = productCategoryService.getAllCategories();
		model.addAttribute("categories", categories);
		return "index";
	}

	 @GetMapping("/products/{categoryId}")
	    public String displayProductsByCategory(@PathVariable int categoryId, Model model) {
	        if (!productCategoryService.productCategoryExists(categoryId)) {
	            throw new CategoryNotFoundException("Category ID " + categoryId + " does not exist.");
	        }

	        List<Product> products = productService.getProductsByCategory(categoryId);
	        model.addAttribute("products", products);

	        if (!products.isEmpty()) {
	            String productCategory = products.get(0).getProductCategory().getCategory();
	            model.addAttribute("productCategory", productCategory);
	        }

	        return "products";
	    }
	
	// pending review and exception
	@GetMapping("/products")
	public String displayProducts(@RequestParam(required = false) String sort, Model model) {

		List<Product> products = productService.getAllProducts();
		model.addAttribute("products", products);

		if (sort != null) {
			switch (sort) {
			case "priceLowHigh" -> products.sort(Comparator.comparing(Product::getPrice));
			case "priceHighLow" -> products.sort(Comparator.comparing(Product::getPrice).reversed());
			case "nameAZ" -> products.sort(Comparator.comparing(Product::getProductName));
			case "nameZA" -> products.sort(Comparator.comparing(Product::getProductName).reversed());
			}
		}

		return "products";
	}

	// eventhough we are having dropdown to select the searchtype,however it's still
	// possible for someone to give inputs through dev tools. hence
	// InvalidSearchTypeException is handled here
	@GetMapping("/search")
	public String search(@RequestParam("keyword") String keyword, @RequestParam("searchtype") String searchType,
			Model model) {
		
		 if (keyword == null || keyword.trim().isEmpty()) {
		        throw new IllegalArgumentException("Search keyword is mandatory");
		    }

		    if (searchType == null || searchType.trim().isEmpty()) {
		        throw new IllegalArgumentException("Search type is mandatory");
		    }

		List<Product> products;

		if ("name".equalsIgnoreCase(searchType)) {
			products = productService.searchProductByName(keyword);
		} else if ("category".equalsIgnoreCase(searchType)) {
			products = productService.searchProductByCategory(keyword);
		} else {
			throw new InvalidSearchTypeException("Invalid search type: " + searchType);
		}

		model.addAttribute("products", products);
		return "products";
	}

	@GetMapping("/cart/summary")  //CARTCOUNT
	@ResponseBody
	public ResponseEntity<CartSummary> getCartSummary(Authentication authentication) {
		return ResponseEntity.ok(shoppingCartService.getCartSummary(authentication.getName()));
	}


}
