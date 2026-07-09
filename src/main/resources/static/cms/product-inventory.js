const API = "http://localhost:8080/api/product-variants";
const INVENTORY_API = "http://localhost:8080/api/inventory";
const ADJUST_API = INVENTORY_API + "/adjust";
const DASHBOARD_API = "/api/inventory/dashboard";
const HISTORY_API = "/api/inventory-movements/";

let variants = [];

function getProductId() {
    const arr = window.location.pathname.split("/");
    return arr[arr.length - 2];
}

document.addEventListener("DOMContentLoaded", () => {
    loadVariants();
    loadDashboard();

    const qtyInput = document.getElementById("quantity");
    qtyInput.addEventListener("input", calculateResult);
    qtyInput.min = 1;

    document.getElementById("movementType").addEventListener("change", calculateResult);
});

function calculateResult() {
    const current = Number(document.getElementById("currentStock").value) || 0;
    const qty = Number(document.getElementById("quantity").value) || 0;
    const type = document.getElementById("movementType").value;

    let result = current;
    switch (type) {
        case "IMPORT":
        case "RETURN":
        case "ADJUSTMENT":
            result = current + qty;
            break;
        case "DAMAGED":
        case "LOST":
            result = current - qty;
            break;
    }
    document.getElementById("resultStock").innerText = result;
}

// --- API Calls ---

async function loadDashboard() {
    try {
        const response = await fetch(DASHBOARD_API);
        const dashboard = await response.json();

        document.getElementById("totalVariants").innerText = dashboard.totalVariants;
        document.getElementById("lowStock").innerText = dashboard.lowStock;
        document.getElementById("outOfStock").innerText = dashboard.outOfStock;
        document.getElementById("totalSold").innerText = dashboard.totalSold;
        document.getElementById("inventoryValue").innerText = Number(dashboard.inventoryValue).toLocaleString() + " VNĐ";
    } catch (error) {
        console.error("Lỗi tải dashboard:", error);
    }
}

let currentPage = 0;
let pageSize = 5;
let totalPages = 0;

async function loadVariants(page = 0) {

    const response = await fetch(
        `${API}?page=${page}&size=${pageSize}`
    );

    const result = await response.json();

    variants = result.content;

    currentPage = result.pageNumber;
    totalPages = result.totalPages;

    renderTable();
    renderPagination();
}
function renderPagination() {

    const container = document.getElementById("pagination");

    let html = "";

    html += `
        <button
        ${currentPage === 0 ? "disabled" : ""}
        onclick="loadVariants(${currentPage-1})">
        Previous
        </button>
    `;

    for(let i=0;i<totalPages;i++){

        html += `
            <button
            class="${i===currentPage?'active':''}"
            onclick="loadVariants(${i})">
            ${i+1}
            </button>
        `;

    }

    html += `
        <button
        ${currentPage===totalPages-1?"disabled":""}
        onclick="loadVariants(${currentPage+1})">
        Next
        </button>
    `;

    container.innerHTML = html;

}
async function loadHistory(variantId) {
    try {
        const response = await fetch(HISTORY_API + variantId);
        const histories = await response.json();
        renderHistory(histories);
        $("#historyModal").modal("show");
    } catch (error) {
        console.error("Lỗi tải lịch sử:", error);
    }
}

async function deleteVariant(id) {
    if (!confirm("Delete?")) return;
    await fetch(`${API}/${id}`, { method: "DELETE" });
    loadVariants();
}

async function updateVariant() {
    const id = document.getElementById("variantId").value;
    const body = {
        price: Number(document.getElementById("price").value),
        discountPrice: Number(document.getElementById("discountPrice").value),
        stock: Number(document.getElementById("stock").value),
        reservedStock: Number(document.getElementById("reservedStock").value),
        size: document.getElementById("size").value,
        color: document.getElementById("color").value
    };

    await fetch(`${API}/${id}`, {
        method: "PATCH",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body)
    });
    loadVariants();
}

async function createVariant() {
    const body = {
        productId: getProductId(),
        sku: document.getElementById("sku").value,
        price: Number(document.getElementById("price").value),
        discountPrice: Number(document.getElementById("discountPrice").value),
        stock: Number(document.getElementById("stock").value),
        reservedStock: Number(document.getElementById("reservedStock").value),
        size: document.getElementById("size").value,
        color: document.getElementById("color").value
    };

    await fetch(API, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body)
    });
    loadVariants();
}

async function saveAdjustment() {
    const button = document.querySelector("#adjustModal .btn-success");
    const body = {
        variantId: Number(document.getElementById("variantId").value),
        quantity: Number(document.getElementById("quantity").value),
        movementType: document.getElementById("movementType").value,
        reason: document.getElementById("reason").value,
        referenceCode: document.getElementById("reference").value
    };

    if (!body.quantity || body.quantity <= 0) {
        alert("Quantity must be greater than zero");
        return;
    }

    button.disabled = true;
    button.innerHTML = `<span class="spinner-border spinner-border-sm"></span> Saving...`;

    try {
        const response = await fetch(ADJUST_API, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(body)
        });

        if (response.ok) {
            $("#adjustModal").modal("hide");
            await loadVariants();
            await loadDashboard();
            alert("Inventory updated");
            await loadHistory(body.variantId);
        } else {
            const error = await response.json();
            alert(error.message);
        }
    } catch (e) {
        console.error(e);
    } finally {
        button.disabled = false;
        button.innerHTML = "Save";
    }
}

// --- DOM Renders & UI Helpers ---

function renderTable() {
    const tbody = document.getElementById("variantTableBody");
    tbody.innerHTML = "";

    variants.forEach(variant => {
        const onHand = variant.stock ?? 0;
        const reserved = variant.reservedStock ?? 0;
        const available = onHand - reserved;
        const sold = variant.sold ?? 0;
        const price = variant.discountPrice ?? variant.price;
        const inventoryValue = price * available;

        tbody.innerHTML += `
        <tr>
            <td>${variant.productName}</td>
            <td>${variant.sku}</td>
            <td>${variant.size} / ${variant.color}</td>
            <td>${onHand}</td>
            <td>${reserved}</td>
            <td>${available}</td>
            <td>${sold}</td>
            <td class="inventory-value">${Number(inventoryValue).toLocaleString()} VNĐ</td>
            <td>${getStatus(available)}</td>
            <td>
                <button class="btn btn-primary btn-sm" onclick="openAdjustModal(${variant.id})">
                    <i class="fas fa-edit"></i>
                </button>
                <button class="btn btn-info btn-sm" onclick="viewHistory(${variant.id})">
                    <i class="fas fa-history"></i>
                </button>
            </td>
        </tr>
        `;
    });
}

function renderHistory(histories) {
    const tbody = document.getElementById("historyTableBody");
    tbody.innerHTML = "";

    histories.forEach(history => {
        const time = new Date(history.createdDate).toLocaleString();
        const changeBadge = history.quantityChange > 0
            ? `<span class="text-success">+${history.quantityChange}</span>`
            : `<span class="text-danger">${history.quantityChange}</span>`;

        tbody.innerHTML += `
        <tr>
            <td>${time}</td>
            <td>${history.movementType}</td>
            <td>${history.quantityBefore}</td>
            <td>${changeBadge}</td>
            <td>${history.quantityAfter}</td>
            <td>${history.referenceCode ?? "-"}</td>
            <td>${history.reason ?? "-"}</td>
        </tr>
        `;
    });
}

function getStatus(available) {
    if (available <= 0) return `<span class="badge badge-dark">Out Of Stock</span>`;
    if (available <= 5) return `<span class="badge badge-danger">Critical</span>`;
    if (available <= 20) return `<span class="badge badge-warning">Low Stock</span>`;
    return `<span class="badge badge-success">In Stock</span>`;
}

function editVariant(id) {
    const variant = variants.find(v => v.id === id);
    if (!variant) return;

    document.getElementById("variantId").value = variant.id;
    document.getElementById("price").value = variant.price;
    document.getElementById("discountPrice").value = variant.discountPrice;
    document.getElementById("stock").value = variant.stock;
    document.getElementById("reservedStock").value = variant.reservedStock;
    document.getElementById("size").value = variant.size;
    document.getElementById("color").value = variant.color;
}

function openAdjustModal(id) {
    const variant = variants.find(v => v.id === id);
    if (!variant) return;

    document.getElementById("variantId").value = id;
    document.getElementById("currentStock").value = variant.stock;
    document.getElementById("quantity").value = "";
    document.getElementById("reference").value = "";
    document.getElementById("reason").value = "";
    document.getElementById("movementType").selectedIndex = 0;
    document.getElementById("resultStock").innerText = variant.stock;

    $("#adjustModal").modal("show");
}

function viewHistory(id) {
    loadHistory(id);
}