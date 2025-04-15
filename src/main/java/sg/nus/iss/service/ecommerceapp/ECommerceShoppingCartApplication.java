package sg.nus.iss.service.ecommerceapp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ECommerceShoppingCartApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceShoppingCartApplication.class, args);
	}
		
//		@Bean
//		 CommandLineRunner loadData(ProductRepository productrepository) {
//		 return (args) -> {  
//		// Add a few products
//			 
//			 Product product1=new Product();
//			 product1.setName("egg");
////			 product1.setProductCategory("Food");
//			 product1.setDescription("eggproduct");
//			 product1.setPrice(20);
//			 product1.setProductUrl("");
//			 product1.setLabels("new");
//			 productrepository.save(product1);
//			 
//			 Product product2=new Product();
//
//			 product2.setName("rice");
////			 product1.setProductCategory("Food");
//			 product2.setDescription("eggproducts");
//			 product2.setPrice(20);
//			 product2.setProductUrl("");
//			 product2.setLabels("new");
//			 productrepository.save(product2);
//			 
//			
//		 };
//	}
	
	

}
