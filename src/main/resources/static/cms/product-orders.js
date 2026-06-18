const API_ORDER =
    "http://localhost:8080/api/admin/orders";


// lưu danh sách order hiện tại
let orders = [];


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

    }
);




// ================================
// FETCH API LẤY DANH SÁCH ORDER
// ================================

async function loadOrders(){


    try {


        const response =
            await fetch(
                `${API_ORDER}?page=0&size=10`
            );


        if(!response.ok){

            throw new Error(
                "Không lấy được danh sách đơn hàng"
            );

        }



        const data =
            await response.json();



        orders =
            data.content;



        renderOrders(orders);



    }
    catch(error){

        console.error(error);

        alert(
            "Lỗi khi tải danh sách đơn hàng"
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
                #${order.id}
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
                    onclick="viewOrder(${order.id})">

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


function viewOrder(id){


    const order =
        orders.find(
            item => item.id === id
        );



    if(!order)
        return;



    document
        .getElementById("orderCode")
        .textContent =
        "#" + order.id;



    document
        .getElementById("orderCustomer")
        .textContent =
        order.receiverName;



    document
        .getElementById("orderDate")
        .textContent =
        formatDate(order.createdDate);



    document
        .getElementById("orderTotal")
        .textContent =
        formatMoney(
            order.totalAmount
        );



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