let editingProductId = null;
let editingVariantId = null;
let editingImageId = null;
let currentProductId = null;
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

async function openVariantModal(productId){

    currentProductId = productId;

    clearVariantForm();

    document
        .getElementById("variantModal")
        .style.display = "flex";

    await loadVariants();

}
async function loadVariants(){

    try{

        const response =
            await fetch(

                `http://localhost:8080/api/product-variants/product/${currentProductId}`

            );

        if(!response.ok){

            throw new Error(
                "Không tải được danh sách Variant"
            );

        }

        const variants =
            await response.json();

        renderVariants(
            variants
        );

    }
    catch(error){

        console.error(error);

        alert(error.message);

    }

}
function closeVariantModal(){

    currentProductId = null;

    clearVariantForm();

    document
        .getElementById("variantModal")
        .style.display="none";

}
function clearVariantForm(){

    editingVariantId = null;
    document
        .getElementById("saveVariantBtn")
        .textContent =
        "Lưu Variant";

    document.getElementById("variantSku").value = "";

    document.getElementById("variantSize").value = "";

    document.getElementById("variantColor").value = "";

    document.getElementById("variantPrice").value = "";

    document.getElementById("variantStock").value = "";

}

async function saveVariant(){

    const isEdit =
        editingVariantId != null;
    try{
        const sku =
            document
                .getElementById("variantSku")
                .value
                .trim();

        const size =
            document
                .getElementById("variantSize")
                .value
                .trim();

        const color =
            document
                .getElementById("variantColor")
                .value
                .trim();

        const price =
            Number(
                document
                    .getElementById("variantPrice")
                    .value
            );

        const stock =
            Number(
                document
                    .getElementById("variantStock")
                    .value
            );

        if(
            !sku ||
            !size ||
            !color ||
            price <= 0 ||
            stock < 0
        ){
            alert("Vui lòng nhập đầy đủ thông tin.");

            return;

        }

        const data = {

            productId: currentProductId,

            sku: sku,

            size: size,

            color: color,

            price: price,

            stock: stock

        };

        let response;
        if(editingVariantId == null){

            response =
                await fetch(

                    "http://localhost:8080/api/product-variants",

                    {

                        method:"POST",

                        headers:{
                            "Content-Type":"application/json"
                        },

                        body:JSON.stringify(data)

                    }

                );

        }
        else{

            response =
                await fetch(

                    `http://localhost:8080/api/product-variants/${editingVariantId}`,

                    {

                        method:"PATCH",

                        headers:{
                            "Content-Type":"application/json"
                        },

                        body:JSON.stringify(data)

                    }

                );

        }

        if(!response.ok){
            throw new Error(

                isEdit

                    ? "Không thể cập nhật Variant"

                    : "Không thể thêm Variant"

            );
        }

        if(isEdit){

            alert("Cập nhật Variant thành công");

        }
        else{

            alert("Thêm Variant thành công");

        }

        clearVariantForm();

        await loadVariants();

        await loadProducts();

    }
    catch(error){

        console.error(error);

        alert(error.message);

    }

}
async function editVariant(id){

    try{

        const response =
            await fetch(

                `http://localhost:8080/api/product-variants/${id}`

            );

        if(!response.ok){

            throw new Error(
                "Không lấy được Variant"
            );

        }

        const variant =
            await response.json();

        editingVariantId = variant.id;

        document
            .getElementById("variantSku")
            .value =
            variant.sku;

        document
            .getElementById("variantSize")
            .value =
            variant.size;

        document
            .getElementById("variantColor")
            .value =
            variant.color;

        document
            .getElementById("variantPrice")
            .value =
            variant.price;

        document
            .getElementById("variantStock")
            .value =
            variant.stock;

        document
            .getElementById("saveVariantBtn")
            .textContent =
            "Cập nhật Variant";

    }
    catch(error){

        console.error(error);

        alert(error.message);

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
        const variants = product.variants || [];

        const variantCount = variants.length;

        const totalStock =
            variants.reduce(
                (sum, variant) => sum + variant.stock,
                0
            );

        const minPrice =
            variants.length > 0
                ? Math.min(
                    ...variants.map(
                        variant => variant.price
                    )
                )
                : 0;
        const category =
            product.category?.name || "-";

        let status = "";

        if(totalStock === 0){

            status =
                '<span class="badge badge-dark">Hết hàng</span>';

        }
        else if(totalStock <= 10){

            status =
                '<span class="badge badge-warning">Sắp hết</span>';

        }
        else{

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

                <td>${category}</td>

                <td>${minPrice.toLocaleString()}đ</td>
                
                <td>${totalStock}</td>
                
                <td>${variantCount}</td>

                <td>${status}</td>

                <td>
                    <div class="action-buttons">
                        
                        <button class="btn btn-warning btn-sm"
                        onclick="editProduct(${product.id})">
                        Sửa
                        </button>
                        <button

                        class="btn btn-info"
                        
                        onclick="openVariantModal(${product.id})">
                        
                        Variant
                        
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


        const category =
            document.getElementById("productCategory").value;

// ===========================
// Validate
// ===========================

        if(productName1 === ""){

            alert("Tên sản phẩm không được để trống.");

            return;
        }


        if(category === ""){

            alert("Vui lòng chọn danh mục.");

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
        let url;
        let method;
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

function openModal() {

    editingProductId = null;
    editingImageId = null;

    document.getElementById("productForm").reset();

    document.getElementById("modalTitle").textContent =
        "Thêm sản phẩm";

    document.getElementById("productModal").style.display = "flex";
}
function closeModal(){

    document
        .getElementById("productModal")
        .style.display="none";

    document
        .getElementById("productForm")
        .reset();

    editingProductId = null;

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

function renderVariants(variants){

    const tbody =
        document.getElementById("variantList");

    tbody.innerHTML = "";

    if(variants.length === 0){

        tbody.innerHTML =

            `
            <tr>

                <td colspan="6"
                    style="text-align:center">

                    Chưa có Variant

                </td>

            </tr>

        `;

        return;

    }

    variants.forEach(

        variant=>{

            tbody.innerHTML +=

                `
                <tr>

                    <td>${variant.sku}</td>

                    <td>${variant.size}</td>

                    <td>${variant.color}</td>

                    <td>${variant.price.toLocaleString()} đ</td>

                    <td>${variant.stock}</td>

                    <td>

                        <button
                            class="btn btn-warning"
                            onclick="editVariant(${variant.id})">

                            Sửa

                        </button>

                        <button
                            class="btn btn-danger"
                            onclick="deleteVariant(${variant.id})">

                            Xóa

                        </button>

                    </td>

                </tr>

            `;

        }

    );

}
async function deleteVariant(id){

    const confirmDelete =
        confirm(
            "Bạn có chắc chắn muốn xóa Variant này?"
        );

    if(!confirmDelete){

        return;

    }

    try{

        const response =
            await fetch(

                `http://localhost:8080/api/product-variants/${id}`,

                {

                    method:"DELETE"

                }

            );

        if(!response.ok){

            throw new Error(
                "Không thể xóa Variant."
            );

        }

        alert(
            "Xóa Variant thành công."
        );

        clearVariantForm();

        await loadVariants();

        await loadProducts();

    }
    catch(error){

        console.error(error);

        alert(error.message);

    }

}