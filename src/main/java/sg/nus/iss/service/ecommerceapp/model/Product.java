package sg.nus.iss.service.ecommerceapp.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id", nullable = false)
	private int id;
	@Column(name="name",length = 225)
	private String productName;
	@Column(length = 225)
	private String description;
	@Column(nullable = false)
	private double price;
	private String productUrl; 	// for product picture link
	private String labels;
	
	@OneToMany(mappedBy = "product")
	private List<CartItem> cartItems;

	@ManyToOne
	@JoinColumn(name = "product_category_id")
	private ProductCategory productCategory;

	public Product() {
//		super();
	}

	public Product(String productName, ProductCategory productCategory, String description, double price, String productUrl,
			String labels) {
//		super();
		this.productName = productName;
		this.productCategory = productCategory;
		this.description = description;
		this.price = price;
		this.productCategory = productCategory;
		this.productUrl = productUrl;
		this.labels = labels;

//		this.setName(name);
//		this.setProductCategory(productCategory);
//		this.setDescription(description);
//		this.setPrice(price);
//		this.setProductUrl(productUrl);
//		this.setLabels(labels);
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

	public String getProductUrl() {
		return productUrl;
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Product [id=" + getId() + ", name=" + getProductName() + ", description=" + getDescription() + ", price="
				+ getPrice() + ", productUrl=" + getProductUrl() + ", label=" + getLabels() + "]";
	}
}
