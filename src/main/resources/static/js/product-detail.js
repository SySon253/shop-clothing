const API_PRODUCT =
    "http://localhost:8080/api/products/id/";
const API_CART =
    "http://localhost:8080/api/carts/item";
let product = null;
let selectedColor = null;

let selectedSize = null;

let selectedVariant = null;

document.addEventListener(
    "DOMContentLoaded",
    () => {
        document
            .getElementById("btn-add-cart")
            .addEventListener(
                "click",
                addToCart
            );
        const productId =
            getProductId();

        loadProduct(productId);
        initAddCart();
    }
);
function initAddCart(){

    document
        .getElementById("btn-add-cart")
        .addEventListener(
            "click",
            addToCart
        );

}
async function addToCart(){

    if(!selectedVariant){

        alert("Hãy chọn Size và Color");

        return;

    }

    const quantity =
        Number(
            document
                .getElementById("quantity")
                .value
        );

    const body = {

        variantId:
        selectedVariant.id,

        quantity:
        quantity

    };

    const response =
        await fetch(

            API_CART,

            {

                method:"POST",

                headers:{
                    "Content-Type":"application/json"
                },

                body:JSON.stringify(body)

            }

        );

    if(response.ok){

        alert("Đã thêm vào giỏ hàng");

    }

    else{

        const message =
            await response.text();

        alert(message);

    }

}
function getProductId(){

    const arr =
        window.location.pathname.split("/");

    return arr[arr.length - 1];

}
async function loadProduct(productId){

    const response =
        await fetch(
            API_PRODUCT + productId
        );

    if(!response.ok){

        alert("Không tìm thấy sản phẩm");

        return;

    }

    product =
        await response.json();

    console.log(product);

    renderProduct(product);

}
function renderProduct(product){

    renderName(product);

    renderPrice(product);

    renderDescription(product);

    renderImages(product.images);

    renderColors(product.variants);

    renderSizes(product.variants);
    selectedColor =
        product.variants[0].color;

    selectedSize =
        product.variants[0].size;

    document.querySelector(
        `input[name="color"][value="${selectedColor}"]`
    ).checked = true;

    document.querySelector(
        `input[name="size"][value="${selectedSize}"]`
    ).checked = true;

    findVariant();
}
function renderName(product){

    document
        .getElementById("product-name")
        .innerText =
        product.name;

}
function renderDescription(product){

    document
        .getElementById("product-description")
        .innerText =
        product.description;

}
function renderPrice(product){

    let price = 0;

    if(product.variants.length > 0){

        const variant =
            product.variants[0];

        price =
            variant.discountPrice ??
            variant.price;

    }

    document
        .getElementById("product-price")
        .innerText =
        price.toLocaleString() + " VNĐ";

}
function renderImages(images){

    const container =
        document.getElementById(
            "product-images"
        );

    container.innerHTML = "";

    images.forEach((image,index)=>{

        container.innerHTML += `

            <div class="
                carousel-item
                ${index===0?"active":""}
            ">

                <img
                    class="w-100 h-100"
                    src="${image.imageUrl}"
                >

            </div>

        `;

    });

}
function renderColors(variants){

    const container =
        document.getElementById(
            "color-container"
        );

    container.innerHTML = "";

    const colors =
        [...new Set(
            variants.map(
                v=>v.color
            )
        )];

    colors.forEach((color,index)=>{

        container.innerHTML += `

        <div class="
            custom-control
            custom-radio
            custom-control-inline
        ">

            <input

                class="custom-control-input"

                type="radio"

                id="color-${index}"

                name="color"

                value="${color}"

            >

            <label

                class="custom-control-label"

                for="color-${index}"

            >

                ${color}

            </label>

        </div>

        `;

    });

    document
        .querySelectorAll(
            'input[name="color"]'
        )
        .forEach(radio => {

            radio.addEventListener(
                "change",
                function () {

                    // selectedColor = this.value;
                    //
                    // renderSizesByColor();
                    //
                    // findVariant();
                    selectedColor = this.value;

                    syncSize();

                    findVariant();
                    console.log(selectedColor);

                }
            );

        });
    const firstColor =
        document.querySelector(
            'input[name="color"]'
        );

    if(firstColor){

        firstColor.checked = true;

        selectedColor =
            firstColor.value;

        //renderSizesByColor();

    }
}
// function renderSizesByColor(){
//
//     if(!selectedColor){
//         return;
//     }
//
//     const variants =
//         product.variants.filter(
//
//             variant =>
//
//                 variant.color === selectedColor
//
//         );
//
//     renderSizes(variants);
//
// }
function renderSizes(variants){

    const container =
        document.getElementById(
            "size-container"
        );

    container.innerHTML = "";

    const sizes =
        [...new Set(
            variants.map(
                v=>v.size
            )
        )];

    sizes.forEach((size,index)=>{

        container.innerHTML += `

        <div class="
            custom-control
            custom-radio
            custom-control-inline
        ">

            <input

                class="custom-control-input"

                type="radio"

                id="size-${index}"

                name="size"

                value="${size}"

            >

            <label

                class="custom-control-label"

                for="size-${index}"

            >

                ${size}

            </label>

        </div>

        `;

    });

    document
        .querySelectorAll(
            'input[name="size"]'
        )
        .forEach(radio => {

            radio.addEventListener(
                "change",
                function () {

                    // selectedSize = this.value;
                    //
                    // console.log(selectedSize);
                    selectedSize = this.value;

                    syncColor();

                    findVariant();

                }
            );

        });
    const firstSize =
        document.querySelector(
            'input[name="size"]'
        );

    if(firstSize){

        firstSize.checked = true;

        selectedSize =
            firstSize.value;

        findVariant();

    }
}
function syncSize(){

    if(!selectedColor){
        return;
    }

    const variant =
        product.variants.find(

            v =>

                v.color === selectedColor &&

                v.size === selectedSize

        );

    if(variant){
        return;
    }

    const newVariant =
        product.variants.find(

            v =>

                v.color === selectedColor

        );

    if(!newVariant){
        return;
    }

    selectedSize =
        newVariant.size;

    document
        .querySelector(
            `input[name="size"][value="${selectedSize}"]`
        )
        .checked = true;

}
function syncColor(){

    if(!selectedSize){
        return;
    }

    const variant =
        product.variants.find(

            v =>

                v.color === selectedColor &&

                v.size === selectedSize

        );

    if(variant){
        return;
    }

    const newVariant =
        product.variants.find(

            v =>

                v.size === selectedSize

        );

    if(!newVariant){
        return;
    }

    selectedColor =
        newVariant.color;

    document
        .querySelector(
            `input[name="color"][value="${selectedColor}"]`
        )
        .checked = true;

}
function findVariant(){
    selectedVariant =
        product.variants.find(

            variant =>

                variant.color === selectedColor &&

                variant.size === selectedSize

        );

    if(selectedVariant){

        updateVariantInfo();

    }

    console.log(selectedVariant);

}
function updateVariantInfo(){
    const quantity =
        document.getElementById("quantity");

    quantity.max =
        selectedVariant.stock;
    if(!selectedVariant){
        return;
    }

    const price =
        selectedVariant.discountPrice ??
        selectedVariant.price;

    document
        .getElementById("product-price")
        .innerText =
        price.toLocaleString() + " VNĐ";

    document
        .getElementById("product-sku")
        .innerText =
        selectedVariant.sku;

    document
        .getElementById("product-stock")
        .innerText =
        selectedVariant.stock;

    const button =
        document.getElementById(
            "btn-add-cart"
        );

    if(selectedVariant.stock <= 0){

        button.disabled = true;

        button.innerText = "Hết hàng";

    }
    else{

        button.disabled = false;

        button.innerHTML =
            '<i class="fa fa-shopping-cart mr-1"></i> Add To Cart';

    }

}
// async function addToCart(){
//
//     if(!selectedVariant){
//
//         alert("Vui lòng chọn sản phẩm");
//
//         return;
//
//     }
//
//     const quantity =
//         Number(
//             document.getElementById(
//                 "quantity"
//             ).value
//         );
//
//     console.log({
//
//         variantId:
//         selectedVariant.id,
//
//         quantity
//
//     });
//
// }