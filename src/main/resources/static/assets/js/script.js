const currentPage = document.title.toLowerCase()

const links = document.querySelectorAll(".nav-link")

links.forEach(link => {
	linkPage = link.dataset.page
	if (currentPage.includes(linkPage)) {
		link.classList.add("active-link")
	}
})
