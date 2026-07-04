let editingProductId = null;
let editingVariantId = null;
let editingImageId = null;

let currentPage = 0;
let totalPages = 0;
const pageSize = 10;
async function loadProducts(){

    try{

        const response = await fetch(

            `http://localhost:8080/api/products/all-product?page=${currentPage}&size=${pageSize}`,

            {
                method:"POST",
                headers:{
                    "Content-Type":"application/json"
                },
                body:JSON.stringify({})
            }

        );

        const result = await response.json();

        renderProducts(result.content);

        totalPages = result.totalPages;

        renderPagination();

    }
    catch(error){

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

        let status = "";

        if (stock === 0) {

            status =
                '<span class="badge badge-dark">Hết hàng</span>';

        }
        else if (stock <= 10) {

            status =
                '<span class="badge badge-warning">Sắp hết</span>';

        }
        else {

            status =
                '<span class="badge badge-success">Còn hàng</span>';

        }

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
        const isEdit = editingProductId !== null;
        const productName = document.getElementById("productName").value;
        const productName1 =
            document.getElementById("productName").value.trim();

        const sku =
            document.getElementById("productSKU").value.trim();

        const price =
            Number(document.getElementById("productPrice").value);

        const stock =
            Number(document.getElementById("productStock").value);

        const category =
            document.getElementById("productCategory").value;

// ===========================
// Validate
// ===========================

        if(productName1 === ""){

            alert("Tên sản phẩm không được để trống.");

            return;
        }

        if(sku === ""){

            alert("SKU không được để trống.");

            return;
        }

        if(category === ""){

            alert("Vui lòng chọn danh mục.");

            return;
        }

        if(price <= 0){

            alert("Giá phải lớn hơn 0.");

            return;
        }

        if(stock < 0){

            alert("Tồn kho không được nhỏ hơn 0.");

            return;
        }
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

        let variantResponse;


        if(editingVariantId){

            variantResponse = await fetch(
                `http://localhost:8080/api/product-variants/${editingVariantId}`,
                {
                    method:"PATCH",
                    headers:{
                        "Content-Type":"application/json"
                    },
                    body:JSON.stringify(variantData)
                }
            );

        }
        else{

            variantResponse = await fetch(
                "http://localhost:8080/api/product-variants",
                {
                    method:"POST",
                    headers:{
                        "Content-Type":"application/json"
                    },
                    body:JSON.stringify(variantData)
                }
            );

        }
        if (!variantResponse.ok) {

            const error = await variantResponse.json();

            throw new Error(error.message);

        }
        const imageUrl =
            document.getElementById("productImage").value;

        if(imageUrl.trim() !== ""){

            const imageData = {

                productId: productId,

                imageUrl: imageUrl,

                thumbnail: true

            };

            let imageResponse;

            if(editingImageId){

                imageResponse = await fetch(

                    `http://localhost:8080/api/product-images/${editingImageId}`,

                    {

                        method:"PATCH",

                        headers:{
                            "Content-Type":"application/json"
                        },

                        body:JSON.stringify(imageData)

                    }

                );

            }else{

                imageResponse = await fetch(

                    "http://localhost:8080/api/product-images",

                    {

                        method:"POST",

                        headers:{
                            "Content-Type":"application/json"
                        },

                        body:JSON.stringify(imageData)

                    }

                );

            }

            if(!imageResponse.ok){

                throw new Error("Lưu ảnh thất bại");

            }

        }


        if(isEdit){

            alert("Cập nhật sản phẩm thành công");

        }else{

            alert("Thêm sản phẩm thành công");

        }
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

            editingVariantId = variant.id;


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

        if(product.images && product.images.length > 0){

            const image = product.images[0];

            editingImageId = image.id;

            document.getElementById("productImage").value =
                image.imageUrl;

        }else{

            editingImageId = null;

            document.getElementById("productImage").value = "";

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
    editingVariantId = null;
    editingImageId = null;
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

function renderPagination(){

    const pagination =
        document.getElementById("pagination");

    pagination.innerHTML = "";

    for(let i = 0; i < totalPages; i++){

        pagination.innerHTML +=

            `
                <button
                    class="${i===currentPage ? 'active-page':''}"
                    onclick="goToPage(${i})">

                    ${i+1}

                </button>
            `;

    }

}
function goToPage(page){

    currentPage = page;

    loadProducts();

}

function renderPagination(){

    const pagination =
        document.getElementById("pagination");

    pagination.innerHTML="";

    pagination.innerHTML +=

        `
        <button

            onclick="previousPage()"

            ${currentPage===0?"disabled":""}

        >

            <<

        </button>

    `;

    for(let i=0;i<totalPages;i++){

        pagination.innerHTML +=

            `
            <button

                class="${currentPage===i?'active-page':''}"

                onclick="goToPage(${i})">

                ${i+1}

            </button>

        `;

    }

    pagination.innerHTML +=

        `
        <button

            onclick="nextPage()"

            ${currentPage===totalPages-1?"disabled":""}

        >

            >>

        </button>

    `;

}
function previousPage(){

    if(currentPage>0){

        currentPage--;

        loadProducts();

    }

}
function nextPage(){

    if(currentPage<totalPages-1){

        currentPage++;

        loadProducts();

    }

}