const filterBtn = document.querySelectorAll(".filter-btn");


for (let i = 0; i < filterBtn.length; i++) {
    filterBtn[i].addEventListener("click", () => {
        console.log(filterBtn[i].value);
        
    })
}

