package sg.nus.iss.service.ecommerceapp.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_categories")
public class ProductCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_category_id")
	private int id;
	@Column(length = 255)
	private String category;

	@OneToMany(mappedBy = "productCategory")
	private List<Product> products;

	public ProductCategory() {
		setProducts(new ArrayList<>());
	}

	@Override
	public String toString() {
		return "Category no: " + getId() + ", category: " + getCategory();
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
