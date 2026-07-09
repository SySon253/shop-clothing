
    (function() {
    document.addEventListener('DOMContentLoaded', function() {
        // dropdown toggle
        const userBtn = document.getElementById('userBtn');
        const userDropdown = document.getElementById('userDropdown');
        if (userBtn) {
            userBtn.addEventListener('click', function() {
                userDropdown.classList.toggle('hidden');
            });
            document.addEventListener('click', function(e) {
                if (!userBtn.contains(e.target) && !userDropdown.contains(e.target)) {
                    userDropdown.classList.add('hidden');
                }
            });
        }

        // If on orders page, load orders
        const ordersList = document.getElementById('ordersList');
        if (ordersList) {
            fetchOrders();
        }

        // If on account page, load account
        const accountForm = document.getElementById('accountForm');
        if (accountForm) {
            loadAccount();
        }
    });

    // Fetch orders from backend endpoint /api/orders (JSON array)
    function fetchOrders() {
    fetch('/api/orders')
    .then(resp => {
    if (!resp.ok) throw new Error('Network response was not ok');
    return resp.json();
})
    .then(data => populateOrders(data))
    .catch(err => {
    const ordersList = document.getElementById('ordersList');
    ordersList.innerHTML = '<tr><td colspan="5">Không thể tải đơn hàng.</td></tr>';
    console.error(err);
});
}

    function populateOrders(orders) {
    const tbody = document.getElementById('ordersList');
    if (!Array.isArray(orders) || orders.length === 0) {
    tbody.innerHTML = '<tr><td colspan="5">Chưa có đơn hàng.</td></tr>';
    return;
}
    tbody.innerHTML = '';
    orders.forEach(o => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
                <td>${escapeHtml(o.id || '')}</td>
                <td>${escapeHtml(o.date || '')}</td>
                <td>${escapeHtml(o.total || '')}</td>
                <td>${escapeHtml(o.status || '')}</td>
                <td><button onclick="alert('Chi tiết đơn ${escapeHtml(o.id || '')}')">Xem</button></td>
            `;
    tbody.appendChild(tr);
});
}

    // Load account info from /api/account (GET) and populate form
    function loadAccount() {
    fetch('/api/account')
    .then(r => {
    if (!r.ok) throw new Error('Failed to load account');
    return r.json();
})
    .then(data => {
    document.getElementById('accFullName').value = data.fullName || '';
    document.getElementById('accEmail').value = data.email || '';
    document.getElementById('accPhone').value = data.phone || '';
    document.getElementById('accAddress').value = data.address || '';
})
    .catch(err => console.warn('Account load failed', err));
}
// Save account via PUT to /api/account
    window.saveAccount = function(event) {
    event.preventDefault();
    const payload = {
    fullName: document.getElementById('accFullName').value,
    email: document.getElementById('accEmail').value,
    phone: document.getElementById('accPhone').value,
    address: document.getElementById('accAddress').value
};
    fetch('/api/account', {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
}).then(r => {
    if (r.ok) {
    alert('Cập nhật thông tin thành công');
} else {
    alert('Lỗi khi cập nhật');
}
}).catch(err => {
    alert('Lỗi kết nối');
    console.error(err);
});
};

    // Simple escape
    function escapeHtml(text) {
    if (!text) return '';
    return String(text)
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;')
    .replace(/'/g, '&#039;');
}
})();
