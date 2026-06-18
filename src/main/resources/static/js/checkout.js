const ORDER_API =
    "http://localhost:8080/api/orders/orders";


const CART_API =
    "http://localhost:8080/api/carts/my-cart";



// ================================
// INIT
// ================================

document.addEventListener(
    "DOMContentLoaded",
    function(){


        loadCheckout();



        const button =
            document.querySelector(
                "#placeOrderBtn"
            );


        if(button){

            button.addEventListener(
                "click",
                createOrder
            );

        }
        if(button){

            console.log("FOUND PLACE ORDER BUTTON");


            button.addEventListener(
                "click",
                function(){

                    console.log("CLICK PLACE ORDER");

                    createOrder();

                }
            );

        }


    }
);




// ================================
// LOAD CHECKOUT
// ================================

async function loadCheckout(){


    try{


        const ids =
            JSON.parse(
                localStorage.getItem(
                    "checkoutItems"
                )
            );



        console.log(
            "Checkout ids:",
            ids
        );



        if(!ids || ids.length === 0){


            alert(
                "Chưa chọn sản phẩm"
            );


            return;

        }





        const response =
            await fetch(
                CART_API
            );




        if(!response.ok){


            throw new Error(
                "Cannot load cart"
            );


        }




        const cart =
            await response.json();



        console.log(
            "Cart:",
            cart
        );





        const products =
            cart.items.filter(
                item =>
                    ids.includes(item.id)
            );





        console.log(
            "Selected:",
            products
        );



        renderCheckout(
            products
        );



        calculateTotal(
            products
        );


    }
    catch(error){


        console.error(
            error
        );


    }


}






// ================================
// SHOW PRODUCT
// ================================

function renderCheckout(items){


    const container =
        document.querySelector(
            "#checkout-products"
        );



    container.innerHTML = "";



    items.forEach(
        item => {



            let subtotal =
                Number(item.price)
                *
                Number(item.quantity);



            container.innerHTML += `


            <div class="checkout-item mb-3">


                <h5>
                    ${item.productName}
                </h5>


                <p>
                    Size:
                    ${item.size}
                </p>


                <p>
                    Color:
                    ${item.color}
                </p>



                <p>
                    Price:
                    ${formatMoney(item.price)}
                </p>



                <p>
                    Quantity:
                    ${item.quantity}
                </p>



                <p>
                    Subtotal:
                    ${formatMoney(subtotal)}
                </p>



            </div>


            `;


        }
    );


}








// ================================
// CALCULATE PRICE
// ================================

function calculateTotal(items){



    let subtotal = 0;



    items.forEach(
        item => {


            subtotal +=
                Number(item.price)
                *
                Number(item.quantity);


        }
    );




    let shipping = 0;



    let total =
        subtotal + shipping;





    // UPDATE HTML


    document.querySelector(
        "#subtotal"
    )
        .innerText =
        formatMoney(subtotal);




    document.querySelector(
        "#shipping"
    )
        .innerText =
        formatMoney(shipping);




    document.querySelector(
        "#total"
    )
        .innerText =
        formatMoney(total);



}








// ================================
// CREATE ORDER
// ================================

async function createOrder(){

    console.log("CREATE ORDER RUN");

    try{


        const receiverName =
            document.querySelector(
                "#receiverName"
            ).value;



        const phone =
            document.querySelector(
                "#phone"
            ).value;



        const address =
            document.querySelector(
                "#address"
            ).value;




        const payment =
            document.querySelector(
                "input[name='paymentMethod']:checked"
            );



        if(!payment){


            alert(
                "Chọn phương thức thanh toán"
            );


            return;

        }




        const request = {


            receiverName,

            phone,

            address,


            paymentMethod:
            payment.value,


            cartItemIds:
                JSON.parse(
                    localStorage.getItem(
                        "checkoutItems"
                    )
                )


        };




        console.log(
            "ORDER REQUEST:",
            request
        );





        const response =
            await fetch(
                ORDER_API,
                {


                    method:"POST",


                    headers:{


                        "Content-Type":
                            "application/json"


                    },


                    body:
                        JSON.stringify(
                            request
                        )


                }
            );





        if(!response.ok){


            throw new Error(
                await response.text()
            );


        }





        const data =
            await response.json();




        console.log(
            "ORDER:",
            data
        );





        if(
            payment.value === "BANKING"
        ){


            window.location.href =
                data.paymentUrl;


            return;


        }






        if(
            payment.value === "COD"
        ){



            alert(
                "Đặt hàng thành công"
            );



            localStorage.removeItem(
                "checkoutItems"
            );



            window.location.href =
                "/order-success";


        }





    }
    catch(error){


        console.error(
            error
        );


        alert(
            "Đặt hàng thất bại"
        );


    }


}







// ================================
// MONEY FORMAT
// ================================

function formatMoney(value){


    return new Intl.NumberFormat(
        "vi-VN",
        {

            style:"currency",

            currency:"VND"

        }

    )
        .format(
            Number(value)
        );


}