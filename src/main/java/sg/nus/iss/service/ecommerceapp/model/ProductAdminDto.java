package sg.nus.iss.service.ecommerceapp.model;

public class ProductAdminDto {
	    private Long productId;
	    private String productName;
	    private Double price;
//	    private Integer stock;
	    private int categoryId;
	    private String categoryName;
	    private String labels;
		public String getLabels() {
			return labels;
		}
		public void setLabels(String labels) {
			this.labels = labels;
		}
		public String getCategoryName() {
			return categoryName;
		}
		public void setCategoryName(String categoryName) {
			this.categoryName = categoryName;
		}
		public int getCategoryId() {
			return categoryId;
		}
		public void setCategoryId(int categoryId) {
			this.categoryId = categoryId;
		}
//		public Integer getStock() {
//			return stock;
//		}
//		public void setStock(Integer stock) {
//			this.stock = stock;
//		}
		public Double getPrice() {
			return price;
		}
		public void setPrice(Double price) {
			this.price = price;
		}
		public String getProductName() {
			return productName;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
		public Long getProductId() {
			return productId;
		}
		public void setProductId(Long productId) {
			this.productId = productId;
		}

}
