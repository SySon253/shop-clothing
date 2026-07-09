(function () {
    function addUserTopbar() {
        if (document.querySelector('.topbar') || document.getElementById('userTopbar')) {
            return;
        }

        var style = document.createElement('style');
        style.textContent = [
            '#userTopbar{position:fixed;top:0;left:0;right:0;z-index:50;height:64px;background:rgba(13,13,13,.92);border-bottom:1px solid rgba(201,168,76,.16);backdrop-filter:blur(10px);display:flex;align-items:center;justify-content:space-between;padding:0 24px;font-family:Inter,Arial,sans-serif}',
            '#userTopbar a{color:#f5f0e8;text-decoration:none}',
            '#userTopbar .brand{font-family:"Playfair Display",serif;font-size:24px;font-weight:600;color:#c9a84c}',
            '#userTopbar .nav{display:flex;align-items:center;gap:20px}',
            '#userTopbar .nav a{font-size:14px;color:#d8d3cb}',
            '#userTopbar .nav a:hover,#userTopbar .menu a:hover{color:#c9a84c}',
            '#userTopbar .icon-btn{width:40px;height:40px;border:1px solid rgba(201,168,76,.28);border-radius:8px;background:#161616;color:#c9a84c;display:flex;align-items:center;justify-content:center;cursor:pointer}',
            '#userTopbar .user-wrap{position:relative}',
            '#userTopbar .menu{position:absolute;right:0;top:48px;min-width:190px;background:#161616;border:1px solid rgba(201,168,76,.22);border-radius:8px;padding:8px;box-shadow:0 18px 40px rgba(0,0,0,.35)}',
            '#userTopbar .menu a{display:flex;align-items:center;gap:10px;padding:10px;border-radius:6px;font-size:14px;color:#f5f0e8}',
            '.hidden{display:none!important}',
            '@media(max-width:760px){#userTopbar{height:auto;min-height:64px;flex-wrap:wrap;gap:12px;padding:12px 16px}#userTopbar .nav{order:3;width:100%;overflow-x:auto;gap:16px}}'
        ].join('');
        document.head.appendChild(style);

        var topbar = document.createElement('header');
        topbar.id = 'userTopbar';
        topbar.innerHTML =
            '<a class="brand" href="/home">Shop</a>' +
            '<nav class="nav">' +
            '<a href="/home">Trang chủ</a>' +
            '<a href="/user/products">Sản phẩm</a>' +
            '<a href="/user/promotions">Khuyến mãi</a>' +
            '<a href="/user/wishlist">Yêu thích</a>' +
            '<a href="/cart">Giỏ hàng</a>' +
            '<a href="/checkout">Thanh toán</a>' +
            '</nav>' +
            '<div class="user-wrap">' +
            '<button id="userBtn" class="icon-btn" type="button" aria-label="Mở menu tài khoản"><i data-lucide="user"></i></button>' +
            '<div id="userDropdown" class="menu hidden">' +
            '<a href="/user/account"><i data-lucide="circle-user-round"></i>Account</a>' +
            '<a href="/user/orders"><i data-lucide="history"></i>Lịch sử mua hàng</a>' +
            '<a href="/logout"><i data-lucide="log-out"></i>Logout</a>' +
            '</div>' +
            '</div>';

        document.body.prepend(topbar);
    }

    function clearAuth() {

        // Xóa token ở localStorage nếu có
        localStorage.removeItem('accessToken');
        localStorage.removeItem('roles');


        // Gọi logout backend để xóa cookie ACCESS_TOKEN
        fetch('/logout', {
            method: 'GET',
            credentials: 'include'
        })
            .then(() => {
                window.location.href = '/home';
            })
            .catch(() => {
                window.location.href = '/home';
            });
    }

    document.addEventListener('DOMContentLoaded', function () {
        addUserTopbar();

        var userBtn = document.getElementById('userBtn');
        var userDropdown = document.getElementById('userDropdown');

        if (userBtn && userDropdown) {
            userBtn.addEventListener('click', function (event) {
                event.stopPropagation();
                userDropdown.classList.toggle('hidden');
            });

            document.addEventListener('click', function (event) {
                if (!userBtn.contains(event.target) && !userDropdown.contains(event.target)) {
                    userDropdown.classList.add('hidden');
                }
            });
        }

        document.querySelectorAll('[data-logout], a[href="/logout"]').forEach(function (logoutLink) {
            logoutLink.addEventListener('click', function (event) {
                event.preventDefault();
                clearAuth();
            });
        });

        if (window.lucide) {
            window.lucide.createIcons();
        }
    });
})();
