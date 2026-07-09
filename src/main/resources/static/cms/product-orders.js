const API_ORDER =
    "http://localhost:8080/api/orders/admin/orders";


// lưu danh sách order hiện tại
let orders = [];

let currentPage = 0;

let totalPages = 0;

const pageSize = 10;

// chạy khi load trang
document.addEventListener(
    "DOMContentLoaded",
    function(){


        loadOrders();



        document
            .getElementById("searchInput")
            .addEventListener(
                "input",
                searchOrders
            );



        document
            .getElementById("statusFilter")
            .addEventListener(
                "change",
                function(){


                    currentPage = 0;


                    loadOrders();


                }
            );


    }
);

// ================================
// FETCH API LẤY DANH SÁCH ORDER
// ================================
async function loadOrders(){
    try {
        const status =
            document
                .getElementById("statusFilter")
                .value;
        let url =
            `${API_ORDER}?page=${currentPage}&size=${pageSize}`;
        if(status){

            url += `&status=${status}`;

        }



        const response =
            await fetch(url);



        const data =
            await response.json();



        console.log(
            "DATA:",
            data
        );


        orders =
            data.content;


        totalPages =
            data.totalPages;



        renderOrders(
            orders
        );


        renderPagination();


    }
    catch(error){

        console.error(
            "LOAD ORDER ERROR:",
            error
        );

    }

}
// ================================
// RENDER TABLE
// ================================


function renderOrders(list){


    const tbody =
        document.getElementById(
            "orderList"
        );


    tbody.innerHTML = "";



    list.forEach(order => {

        console.log(order.orderCode, typeof order.orderCode);

        const productNames =
            order.items
                ?
                order.items
                    .map(item =>
                        item.productName
                    )
                    .join(", ")
                :
                "";



        const tr =
            document.createElement("tr");



        tr.innerHTML = `

            <td>
                #${order.orderCode}
            </td>


            <td>
                ${order.receiverName}
            </td>


            <td>
                ${order.phone}
            </td>


            <td>
                ${order.address}
            </td>


            <td>
                ${productNames}
            </td>


            <td>
                ${formatMoney(order.totalAmount)}
            </td>


            <td>
                ${formatDate(order.createdDate)}
            </td>


            <td>

                <div class="action-buttons">

                    <button 
                    class="btn btn-primary btn-sm"
                    onclick="viewOrder('${order.orderCode}')">

                    Xem

                    </button>

                </div>

            </td>

        `;



        tbody.appendChild(tr);


    });


}






// ================================
// SEARCH CLIENT SIDE
// ================================


function searchOrders(){


    const keyword =
        document
            .getElementById(
                "searchInput"
            )
            .value
            .toLowerCase();



    const result =
        orders.filter(order => {


            return (

                order.receiverName
                    .toLowerCase()
                    .includes(keyword)

                ||

                order.phone
                    .includes(keyword)

                ||

                order.address
                    .toLowerCase()
                    .includes(keyword)

            );


        });



    renderOrders(result);


}







// ================================
// XEM CHI TIẾT ORDER
// ================================
// function viewOrder(code){
//     const order = orders.find(
//         item => String(item.orderCode) === String(code)
//     );
//     if(!order)
//         return;
//
//
//
//     document
//         .getElementById("orderCode")
//         .textContent =
//         order.orderCode;
//
//
//
//     document
//         .getElementById("orderCustomer")
//         .textContent =
//         order.receiverName;
//
//
//
//     document
//         .getElementById("orderTotal")
//         .textContent =
//         formatMoney(order.totalAmount);
//     document
//         .getElementById("orderDate")
//         .textContent =
//         formatDate(order.createdDate);
//
//
//     document
//         .getElementById("orderModal")
//         .classList
//         .add("show");
//
//
// }
function viewOrder(code){

    const order =
        orders.find(
            item => String(item.orderCode) === String(code)
        );

    if(!order)
        return;

    document.getElementById("orderCode").textContent =
        order.orderCode;

    document.getElementById("orderCustomer").textContent =
        order.receiverName;

    document.getElementById("orderDate").textContent =
        formatDate(order.createdDate);

    document.getElementById("orderTotal").textContent =
        formatMoney(order.totalAmount);

    const orderItems =
        document.getElementById("orderItems");

    orderItems.innerHTML = "";

    order.items.forEach(item => {

        const li =
            document.createElement("li");

        li.textContent =
            `${item.productName} x ${item.quantity}`;

        orderItems.appendChild(li);

    });

    document
        .getElementById("orderModal")
        .classList
        .add("show");
}
// ================================
// FORMAT MONEY
// ================================


function formatMoney(value){


    if(!value)
        return "0đ";


    return Number(value)
            .toLocaleString(
                "vi-VN"
            )
        +"đ";

}





// ================================
// FORMAT DATE
// ================================


function formatDate(date){


    if(!date)
        return "";



    return new Date(date)
        .toLocaleDateString(
            "vi-VN"
        );

}






// ================================
// CLOSE MODAL
// ================================


function closeModal(){

    document
        .getElementById(
            "orderModal"
        )
        .classList
        .remove("show");

}
function renderPagination(){


    const pagination =
        document.getElementById(
            "pagination"
        );


    pagination.innerHTML = "";



    for(
        let i = 0;
        i < totalPages;
        i++
    ){


        const button =
            document.createElement(
                "button"
            );


        button.innerText =
            i + 1;



        button.className =
            "btn btn-sm btn-outline-primary mx-1";



        if(i === currentPage){

            button.classList.add(
                "active"
            );

        }



        button.onclick = function(){


            currentPage = i;


            loadOrders();


        };



        pagination.appendChild(
            button
        );

    }

}