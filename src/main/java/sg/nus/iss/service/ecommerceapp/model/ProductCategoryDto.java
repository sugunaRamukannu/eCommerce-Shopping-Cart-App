package sg.nus.iss.service.ecommerceapp.model;

public class ProductCategoryDto {

	private String category;
	private int id;
	
	public ProductCategoryDto(Integer id, String category) {
		this.id=id;
		this.category=category;
	}
	
	public int getCategoryId() {
		return id;
	}
	public void setCategoryId(int id) {
		this.id = id;
	}
	public String getCategoryName() {
		return category;
	}
	public void setCategoryName(String category) {
		this.category = category;
	}

}

