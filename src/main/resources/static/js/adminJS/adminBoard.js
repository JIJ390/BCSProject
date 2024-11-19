

const searchBtn = document.querySelector(".searchBtn");
const searchContext = document.querySelector(".searchContext");



searchBtn.addEventListener("click", e => {


    const search = searchContext.value.trim();

    console.log(search);

    location.href = `/admin/adminBoard/search?search=` + search;
});
