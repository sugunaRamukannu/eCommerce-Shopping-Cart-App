//Author(s): Lee Yi Cheng, Melvin

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