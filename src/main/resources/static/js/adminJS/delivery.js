
const overlayBtn = document.querySelector(".overlay");
//배송중
const deliveryLive = document.querySelectorAll(".deliverylive");

const text = document.querySelectorAll(".text");






for (let i = 0; i < deliveryLive.length; i++) {
    deliveryLive[i].addEventListener("click", () => {



        if (overlayBtn.style.display === "flex") {
            overlayBtn.style.display = "none";
        } else {
            overlayBtn.style.display = "flex";
        }

    })
}

for (let i = 0; i < text.length; i++) {
    text[i].addEventListener("click", () => {
        for (let j = 0; j < deliveryLive.length; j++) {
            deliveryLive[j].innerText = text[i].textContent;
        }
    });
}


for (let i = 0; i < text.length; i++) {

    text[i].addEventListener("click", () => {




        const result = text[i].textContent;



        fetch("/admin/delivery", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: result
        })
            .then((res) => {
                if (res.ok) return res.text();
                throw new Error("비동기 실패");
            })
            .then(result => {
                console.log(result);
            })
            .catch404(err => { console.log(err); })
    })
}
