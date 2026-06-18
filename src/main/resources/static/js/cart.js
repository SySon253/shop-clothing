const CART_API = "http://localhost:8080/api/carts";


// tránh spam request
let updatingItems = {};



// ===============================
// LOAD CART
// ===============================

document.addEventListener(
    "DOMContentLoaded",
    () => {

        loadCart();

    }
);





async function loadCart(){


    try{


        const response = await fetch(
            `${CART_API}/my-cart`
        );


        if(!response.ok){

            throw new Error(
                "Cannot get cart"
            );

        }



        const cart =
            await response.json();



        renderCart(cart);



    }
    catch(error){

        console.error(error);

    }


}







// ===============================
// RENDER CART
// ===============================


function renderCart(cart){


    const tbody =
        document.querySelector(
            "tbody.align-middle"
        );



    tbody.innerHTML = "";



    cart.items.forEach(item=>{


        tbody.innerHTML += `


        <tr data-id="${item.id}">


            <td class="align-middle">


    <input 
type="checkbox"
class="cart-checkbox"
data-id="${item.id}"
data-price="${item.subtotal}"
onchange="calculateSelectedCart()"
>



    <img 
    src="/img/product-default.jpg"
    style="width:50px">


    <div>

        <strong>
        ${item.productName}
        </strong>


        <br>


        <small>

        Size:
        ${item.size}

        |

        Color:
        ${item.color}

        </small>


    </div>


</td>





            <td class="align-middle price">


                ${formatMoney(item.price)}


            </td>






            <td class="align-middle">


                <div class="input-group quantity mx-auto"
                style="width:100px">



                    <div class="input-group-btn">


                        <button

                        class="btn btn-sm btn-primary"

                        onclick="
                        changeQuantity(
                            ${item.id},
                            -1,
                            this
                        )">

                        <i class="fa fa-minus"></i>


                        </button>


                    </div>





                    <input

                    class="
                    form-control
                    form-control-sm
                    bg-secondary
                    text-center
                    quantity-input
                    "

                    value="${item.quantity}"

                    readonly>






                    <div class="input-group-btn">


                        <button

                        class="btn btn-sm btn-primary"

                        onclick="
                        changeQuantity(
                            ${item.id},
                            1,
                            this
                        )">


                        <i class="fa fa-plus"></i>


                        </button>


                    </div>



                </div>



            </td>






            <td class="align-middle subtotal">


                ${formatMoney(item.subtotal)}


            </td>







            <td class="align-middle">


                <button


                class="btn btn-sm btn-primary"


                onclick="
                deleteCartItem(
                    ${item.id}
                )">


                <i class="fa fa-times"></i>


                </button>


            </td>



        </tr>



        `;


    });



    updateSummary(
        cart.totalPrice
    );


}








// ===============================
// CHANGE QUANTITY
// ===============================


async function changeQuantity(
    id,
    change,
    button
){



    // nếu item đang update
    if(updatingItems[id]){

        return;

    }




    const row =
        button.closest("tr");



    const input =
        row.querySelector(
            ".quantity-input"
        );



    let quantity =
        Number(
            input.value
        );



    quantity += change;




    if(quantity <=0){

        return;

    }




    // update giao diện trước

    input.value =
        quantity;



    updateRowSubtotal(row);





    try{


        updatingItems[id] = true;



        disableButton(row,true);



        const response =
            await fetch(

                `${CART_API}/item/${id}`,

                {

                    method:"PUT",

                    headers:{

                        "Content-Type":
                            "application/json"

                    },


                    body:JSON.stringify({

                        quantity:
                        quantity

                    })

                }

            );



        if(!response.ok){

            throw new Error(
                "Update failed"
            );

        }



        const cart =
            await response.json();




        updateSummary(
            cart.totalPrice
        );



    }
    catch(error){


        console.error(error);



        // lỗi thì load lại dữ liệu chuẩn

        loadCart();


    }
    finally{


        updatingItems[id]=false;


        disableButton(row,false);


    }


}








// ===============================
// UPDATE ROW PRICE
// ===============================


function updateRowSubtotal(row){



    const priceText =
        row.querySelector(
            ".price"
        )
            .innerText
            .replace(/[^\d]/g,'');




    const price =
        Number(priceText);



    const quantity =
        Number(

            row.querySelector(
                ".quantity-input"
            )
                .value

        );




    row.querySelector(
        ".subtotal"
    )
        .innerText =
        formatMoney(
            price * quantity
        );

}








// ===============================
// DELETE ITEM
// ===============================


async function deleteCartItem(id){



    if(
        !confirm(
            "Bạn muốn xóa sản phẩm này?"
        )
    ){

        return;

    }





    try{


        const response =
            await fetch(

                `${CART_API}/item/${id}`,

                {

                    method:"DELETE"

                }

            );



        if(response.ok){

            loadCart();

        }



    }
    catch(error){

        console.error(error);

    }


}







// ===============================
// UPDATE SUMMARY
// ===============================


function updateSummary(totalPrice){



    const summary =
        document.querySelectorAll(
            ".card-body h6"
        );



    const shipping = 10000;




    if(summary.length >=2){


        summary[0]
            .innerText =
            formatMoney(
                totalPrice
            );



        summary[1]
            .innerText =
            formatMoney(
                shipping
            );


    }




    const total =
        document.querySelector(
            ".card-footer h5.font-weight-bold:last-child"
        );



    if(total){

        total.innerText =
            formatMoney(
                totalPrice + shipping
            );

    }


}







// ===============================
// BUTTON LOCK
// ===============================


function disableButton(
    row,
    disabled
){


    const buttons =
        row.querySelectorAll(
            "button"
        );


    buttons.forEach(btn=>{

        btn.disabled =
            disabled;

    });


}







// ===============================
// FORMAT MONEY
// ===============================


function formatMoney(value){


    return new Intl.NumberFormat(
        "vi-VN",
        {

            style:"currency",

            currency:"VND"

        }

    )
        .format(value);


}
function calculateSelectedCart(){


    const checkedItems =
        document.querySelectorAll(
            ".cart-checkbox:checked"
        );


    let subtotal = 0;


    checkedItems.forEach(item=>{


        subtotal += Number(
            item.dataset.price
        );


    });



    document.querySelector(
        "#selected-count"
    )
        .innerText =
        checkedItems.length;



    document.querySelector(
        "#selected-subtotal"
    )
        .innerText =
        formatMoney(subtotal);



    let shipping = 0;


    if(subtotal > 0){

        shipping = 10000;

    }


    document.querySelector(
        "#selected-shipping"
    )
        .innerText =
        formatMoney(shipping);


}
function goCheckout(){


    const checked =
        document.querySelectorAll(
            ".cart-checkbox:checked"
        );


    let ids = [];



    checked.forEach(item=>{


        ids.push(
            Number(
                item.dataset.id
            )
        );


    });



    console.log(
        "checkout ids:",
        ids
    );



    if(ids.length === 0){

        alert(
            "Bạn chưa chọn sản phẩm"
        );

        return;

    }



    localStorage.setItem(
        "checkoutItems",
        JSON.stringify(ids)
    );



    window.location.href="/checkout";

}