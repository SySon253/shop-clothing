const API_PRODUCT = "http://localhost:8080/api/products/all-product";
document.addEventListener("DOMContentLoaded", () => {
    loadProducts();
});
function loadProducts() {

    fetch(API_PRODUCT, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({})
    })

        .then(response => {

            if (!response.ok) {
                throw new Error("Lỗi gọi API");
            }

            return response.json();

        })

        .then(data => {

            console.log(data);

            renderProducts(data.content);

        })

        .catch(error => {

            console.log(error);

        });

}



function renderProducts(products) {

    const container = document.getElementById("product-list");

    container.innerHTML = "";


    products.forEach(product => {


        let image = "";


        if (product.images && product.images.length > 0) {

            image = product.images[0].imageUrl;

        }



        // let price = 0;
        // let discountPrice = 0;
        //
        // if (product.variants && product.variants.length > 0) {
        //     price = product.variants[0].price;
        //     discountPrice = product.variants[0].discountPrice;
        // }
        let price = 0;
        let discountPrice = 0;
        let variantId = null;

        if (product.variants && product.variants.length > 0) {

            const variant = product.variants[0];

            variantId = variant.id;

            price = variant.price ?? 0;

            discountPrice = variant.discountPrice ?? price;

        }



        const html = `

            <div class="col-lg-3 col-md-6 col-sm-12 pb-1">

                <div class="card product-item border-0 mb-4">


                    <div class="
                        card-header 
                        product-img 
                        position-relative 
                        overflow-hidden 
                        bg-transparent 
                        border 
                        p-0
                    ">

                        <img 
                            class="img-fluid w-100"
                            src="${image}"
                            alt="${product.name}"
                        >

                    </div>

<button 
    onclick="toggleWishlist(${product.id})"
    class="absolute top-3 right-3 rounded-full p-2"
    style="
        background:#0d0d0d;
        color:white
    "
>

    <svg xmlns="http://www.w3.org/2000/svg"
         width="18"
         height="24"
         viewBox="0 0 24 24"
         fill="none"
         stroke="currentColor"
         stroke-width="2">
         
        <path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"></path>

    </svg>

</button>


                    <div class="
                        card-body 
                        border-left 
                        border-right 
                        text-center 
                        p-0 
                        pt-4 
                        pb-3
                    ">


                        <h6 class="text-truncate mb-3">

                            ${product.name}

                        </h6>



                        <div class="d-flex justify-content-center">
                            <!--<h6>
                                ${discountPrice.toLocaleString()} VNĐ
                            </h6>
                        
                            <h6 class="text-muted ml-2">
                                <del>${price.toLocaleString()} VNĐ</del>
                            </h6>--> 
                            <h6>
                                ${discountPrice.toLocaleString()} VNĐ
                            </h6>
                            
                            ${
                                        discountPrice < price
                                            ? `
                                    <h6 class="text-muted ml-2">
                                        <del>${price.toLocaleString()} VNĐ</del>
                                    </h6>
                                  `
                                            : ""
                                    }
                        </div>


                    </div>




                    <div class="
                        card-footer 
                        d-flex 
                        justify-content-between 
                        bg-light 
                        border
                    ">


                        <a 
                            href="/product/${product.id}"
                            class="btn btn-sm text-dark p-0"
                        >

                            <i class="fas fa-eye text-primary mr-1"></i>

                            Xem chi tiết

                        </a>




                        <!--<a 
                            href="#"
                            onclick="addToCart(${product.variants[0].id})"
                            class="btn btn-sm text-dark p-0"
                        >

                            <i class="fas fa-shopping-cart text-primary mr-1"></i>

                            Add To Cart

                        </a>-->
                        ${
                            variantId
                                ? `<a href="#"
                              onclick="addToCart(${variantId})"
                              class="btn btn-sm text-dark p-0">
                                <i class="fas fa-shopping-cart text-primary mr-1"></i>
                                Add To Cart
                           </a>`
                                : `<span class="btn btn-sm text-muted p-0">
                                Hết hàng
                           </span>`
                        }


                    </div>


                </div>

            </div>

        `;



        container.innerHTML += html;


    });

}





async function addToCart(variantId) {


    const response = await fetch("/api/carts/item", {


        method: "POST",


        credentials: "include",


        headers: {

            "Content-Type": "application/json"

        },


        body: JSON.stringify({

            variantId: variantId,

            quantity: 1

        })

    });



    console.log("STATUS:", response.status);



    if (response.ok) {


        alert("Đã thêm vào giỏ hàng");


    } else {


        console.log(await response.text());


        alert("Thêm giỏ hàng thất bại");


    }

}