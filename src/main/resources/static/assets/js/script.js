// navigation bar & document title
const currentPage = document.title.toLowerCase()

const links = document.querySelectorAll(".nav-link")

const purchaseButton = document.querySelector(".purchases")

//fix clickbox
purchaseButton?.addEventListener('click', function() {
	location.href="/purchase-history"
})

links.forEach(link => {
	const linkPage = link.dataset.page
	if (currentPage.includes(linkPage)) {
		link.classList.add("active-link")
	}
})

// get last part of url in the form of id and append to page title
if (currentPage.includes("order detail")) {
	const orderIdNo = location.pathname.split("/").pop() 
	document.title += " " + orderIdNo
}

//redirect to order details page
/*const orderBoxes = document.querySelectorAll(".order-box")

orderBoxes.forEach(order => {
	order.addEventListener("click", () => {
		const orderId = order.dataset.orderId
		location.href=`/order-detail/${orderId}`
	})
})*/


/*
function updateCartCount(count) {
          document.getElementById('cart-count').textContent = count;
      }*/

function submitDeleteForm(productId) {
	document.getElementById('delete-form-' + productId).submit();
}
				
document.querySelector("#checkout-form")?.addEventListener("submit", function(event) {
	const checkboxes = document.querySelectorAll("#checkout-form input[name='checkedoutItems']:checked");
	if (checkboxes.length === 0) {
		alert("Please select at least one item to checkout.");
		event.preventDefault();
	}
});


