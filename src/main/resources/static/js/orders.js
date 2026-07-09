const ORDER_API =
    "http://localhost:8080/api/orders/my-orders";


// lưu danh sách order
let orders = [];



document.addEventListener(
    "DOMContentLoaded",
    function(){

        loadOrders();

    }
);

// ==================================
// FETCH ORDERS
// ==================================

async function loadOrders(){


    try {


        const response =
            await fetch(
                ORDER_API,
                {
                    method:"GET",
                    credentials:"include"
                }
            );



        if(!response.ok){

            throw new Error(
                "Load orders failed"
            );

        }



        orders =
            await response.json();



        console.log(
            "ORDERS:",
            orders
        );



        render();


    }
    catch(error){

        console.log(error);

    }

}





// ==================================
// FORMAT MONEY
// ==================================

function formatMoney(number){

    return Number(number)
            .toLocaleString("vi-VN")
        +
        " ₫";

}





// ==================================
// RENDER
// ==================================

function render(){


    const app =
        document.getElementById("app");


    if(orders.length===0){

        app.innerHTML = `

        <div class="text-center text-gray-400 py-20">

            Chưa có đơn hàng nào.

        </div>

        `;

        return;
    }

    app.innerHTML = orders.map(order=>{


        return `


<div class="rounded-xl p-6 mb-5"
style="
background:#161616;
border:1px solid rgba(201,168,76,.1)
">


<div>


<p class="text-gray-400">
Mã đơn:
</p>


<p class="text-white">
#${order.id}
</p>



<p class="text-gray-400 mt-3">
Phương thức thanh toán:
</p>


<p class="text-white">
${order.paymentMethod}
</p>



<p class="text-gray-400 mt-3">
Tổng tiền:
</p>


<p style="color:#c9a84c">
${formatMoney(order.totalAmount)}
</p>


</div>




<div class="mt-5">

<p class="text-gray-400">
Sản phẩm:
</p>



${
            order.items.map(item=>{


                return `


<div class="flex justify-between mt-3">


<div>

<p class="text-white">
${item.productName}
</p>


<p class="text-gray-400">

Size:
${item.size}

-
Color:
${item.color}

</p>


<p class="text-gray-400">

Số lượng:
${item.quantity}

</p>


</div>


<p style="color:#c9a84c">

${formatMoney(
                    item.price * item.quantity
                )}

</p>


</div>


`


            }).join("")
        }



</div>


</div>


`


    }).join("");

}