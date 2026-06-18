let editingProductId = null;
async function loadProducts() {

    try {

        const response = await fetch(
            "http://localhost:8080/api/products/all-product?page=0&size=20",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({})
            }
        );

        const result = await response.json();

        console.log(result);

        renderProducts(result.content);

    } catch (error) {
        console.error(error);
    }
}

function renderProducts(products) {

    const tbody =
        document.getElementById("productList");

    tbody.innerHTML = "";

    products.forEach(product => {

        const image =
            product.images?.length > 0
                ? product.images[0].imageUrl
                : "";

        const variant =
            product.variants?.length > 0
                ? product.variants[0]
                : null;

        const sku =
            variant?.sku || "-";

        const price =
            variant?.price || 0;

        const stock =
            variant?.stock || 0;
        const category =
            product.category?.name || "-";

        const status =
            stock > 10
                ? '<span class="badge badge-success">Còn hàng</span>'
                : '<span class="badge badge-danger">Sắp hết</span>';

        tbody.innerHTML += `
            <tr>

                <td>
                    ${
            image
                ? `<img src="${image}"
                               width="50"
                               height="50"
                               style="object-fit:cover;border-radius:5px;">`
                : `<div style="
                                width:50px;
                                height:50px;
                                background:#ddd;
                                border-radius:5px;">
                           </div>`
        }
                </td>

                <td>${product.name}</td>

                <td>${sku}</td>

                <td>${category}</td>

                <td>${price.toLocaleString()}đ</td>

                <td>${stock}</td>

                <td>${status}</td>

                <td>
                    <div class="action-buttons">
                        
                        <button class="btn btn-warning btn-sm"
                        onclick="editProduct(${product.id})">
                        Sửa
                        </button>
                        
                        <button 
                        class="btn btn-danger btn-sm"
                        onclick="deleteProduct(${product.id})">
                        
                        Xóa
                        
                        </button>

                    </div>
                </td>

            </tr>
        `;
    });
}
document.addEventListener(
    "DOMContentLoaded",
    function () {
        loadCategories();
        loadProducts();
    }
);



async function loadCategories() {

    try {

        const response = await fetch(
            "http://localhost:8080/api/categories/all-category?page=0&size=100",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({})
            }
        );

        const result = await response.json();

        console.log(result);

        const categories = result.content;

        const select =
            document.getElementById("productCategory");

        select.innerHTML =
            '<option value="">Chọn danh mục</option>';

        categories.forEach(category => {

            select.innerHTML += `
                <option value="${category.id}">
                    ${category.name}
                </option>
            `;
        });

    } catch (error) {

        console.error(error);
    }
}

function createSlug(text){
    return text.toLowerCase().trim().replace(/\s+/g,"-");
}



async function saveProduct(event){
    event.preventDefault();
    try{
        const productName = document.getElementById("productName").value;
        const productData = {
            name: document.getElementById("productName").value,
            slug: createSlug(productName),
            description: document.getElementById("productDescription").value,
            brand: document.getElementById("productBrand").value,
            categoryId: Number(document.getElementById("productCategory").value),
            createBy: "admin",
            lastModifiedBy: "admin"
        };
        if(editingProductId){

            url =
                `http://localhost:8080/api/products/id/${editingProductId}`;

            method = "PATCH";

        }
        else{

            url =
                "http://localhost:8080/api/products";

            method = "POST";

        }
        const productResponse = await fetch(url,
            {
                method:method,
                headers:{
                    "Content-Type":"application/json"
                },
                body: JSON.stringify(productData)
            });
        if (!productResponse.ok) {

            const error = await productResponse.json();

            throw new Error(error.message);
        }

        const product = await productResponse.json();

        const productId = product.id;
        if (!productId) {
            throw new Error("Không lấy được productId");
        }

        const variantData = {
            productId: productId,
            sku: document.getElementById("productSKU").value,
            price: Number(document.getElementById("productPrice").value),
            discountPrice: 0,
            stock: Number(document.getElementById("productStock").value),
            reservedStock: 0,
            size: document.getElementById("productSize").value,
            color: document.getElementById("productColor").value
        };
        console.log("variantData =", variantData);

        const variantResponse = await fetch(
            "http://localhost:8080/api/product-variants",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(variantData)
            }
        );

        if (!variantResponse.ok) {

            console.log("status =", variantResponse.status);

            const errorText = await variantResponse.text();

            console.error(errorText);

            throw new Error(errorText);
        }

        const imageUrl = document.getElementById("productImage").value;
        if (imageUrl.trim() !== ""){
            const imageData = {
                productId: productId,
                imageUrl: imageUrl,
                thumbnail: true
            };
            const imageResponse = await fetch("http://localhost:8080/api/product-images",
                {
                    method: "POST",
                    headers:{
                        "Content-Type":"application/json"
                    },
                    body: JSON.stringify(imageData)
                });
            if (!imageResponse.ok){
                throw new Error("Tạo ảnh thất bại");
            }
        }
        alert("Thêm sản phẩm thành công");
        closeModal();
        loadProducts();
    }catch (error){
        console.log(error);
        alert(error.message);
    }
}

async function editProduct(id){

    try {

        const response = await fetch(
            `http://localhost:8080/api/products/id/${id}`
        );


        if(!response.ok){
            throw new Error("Không lấy được sản phẩm");
        }


        const product = await response.json();


        console.log(product);


        // lưu id để khi bấm Lưu biết đang update
        editingProductId = product.id;


        // đổi tiêu đề modal
        document.getElementById("modalTitle").innerText =
            "Chỉnh sửa sản phẩm";


        // đổ dữ liệu vào form

        document.getElementById("productName").value =
            product.name || "";


        document.getElementById("productBrand").value =
            product.brand || "";


        document.getElementById("productDescription").value =
            product.description || "";


        document.getElementById("productCategory").value =
            product.category.id;



        // lấy variant đầu tiên

        if(product.variants && product.variants.length > 0){

            const variant = product.variants[0];


            document.getElementById("productSKU").value =
                variant.sku || "";


            document.getElementById("productPrice").value =
                variant.price || 0;


            document.getElementById("productStock").value =
                variant.stock || 0;


            document.getElementById("productSize").value =
                variant.size || "";


            document.getElementById("productColor").value =
                variant.color || "";
        }



        // mở modal
        openModal();


    }catch(error){

        console.error(error);
        alert("Không thể mở sản phẩm");

    }

}
function openModal(){
    document.getElementById("productModal").style.display = "flex";
}
function closeModal(){
    document.getElementById("productModal").style.display = "none";

    editingProductId = null;
}


async function deleteProduct(id){

    if(!confirm("Bạn có chắc muốn xóa?")){
        return;
    }


    const response = await fetch(
        `http://localhost:8080/api/products/${id}`,
        {
            method:"DELETE"
        }
    );


    if(response.ok){

        alert("Xóa thành công");

        loadProducts();

    }
}