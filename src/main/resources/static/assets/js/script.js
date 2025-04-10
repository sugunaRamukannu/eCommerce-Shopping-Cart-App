const currentPage = document.title.toLowerCase()

const links = document.querySelectorAll(".nav-link")

links.forEach(link => {
	linkPage = link.dataset.page
	if (currentPage.includes(linkPage)) {
		link.classList.add("active-link")
	}
})

// Fetch actual cart summary on page load
		   window.addEventListener('DOMContentLoaded', () => {
		       fetch('/cart/summary')
		           .then(response => response.json())
		           .then(data => {
		               updateCartCount(data.itemCount);
		               updateCartTotal(data.totalPrice);
		           });
				   
				   // Update the cart count on the page load
				   	        updateCartCount(cartCount);
		   });
		      

				
        // Simulate adding to the cart
        document.querySelectorAll(".add-to-cart-btn").forEach(button => {
            button.addEventListener("click", () => {
                cartCount++;
                updateCartCount(cartCount);
				
				localStorage.setItem('cartCount', cartCount);
            });
        });

        function updateCartCount(count) {
            document.getElementById('cart-count').textContent = count;
        }