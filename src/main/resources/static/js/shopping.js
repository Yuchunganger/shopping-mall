// 定義商品功能變數
var datas = {};
var products = [];
// 定義商品功能變數：搜索條件及分頁
var query = {
    search: null,
    orderBy: null,
    limit: 10,  // 此參數可調整頁面呈現數量
    offset: null
};
var pagination = {
    currentPage: 1,
    pageNum: [],
    pageLimit: [],
    pageOffset: [],
}
// 定義帳號功能變數
var isLogin = true; // 切換為登入頁面(true)或註冊頁面(false)
var isMember = false; // 是否已登入
var isUniqueAccount = true; // 判斷創建的帳號是否有重複
// 定義帳號功能變數：使用者輸入的登入帳密
var loginVariable = {
    accountNumber: null,
    accountPassword: null
}
// 定義帳號功能變數：使用者輸入的創建帳號資訊
var requestBodyForCreateMember = {
    accountNumber: null,
    accountPassword: null,
    memberFirstname: null,
    memberLastname: null,
    contactPhone: null,
    photoUrl: null,
}
// 定義帳號功能變數：Cookie取出後要存放的地方
var cookieObj = {
    accountNumber: null
};
// 定義帳號功能變數：已登入者的帳號資訊
var member = {
    memberId: null,
    accountNumber: null,
    accountPassword: null,
    memberFirstname: null,
    memberLastname: null,
    contactPhone: null,
    photoUrl: null,
    createdDate: null,
    lastModifiedDate: null
}
// 定義商品功能變數：使用者輸入的創建商品資訊
var requestBodyForCreateProduct = {
    productName: "",
    category: "",
    imageUrl: null,
    price: "",
    stock: "",
    description: ""
}
// 定義商品功能變數：修改商品資訊
var requestBodyForUpdateProduct = {
    productName: "",
    category: "",
    imageUrl: null,
    price: "",
    stock: "",
    description: ""
}
// 定義訂單功能變數：是否已選購商品(true方可創建訂單)
var chooseProduct = false;
// 定義訂單功能變數：選購商品
var car = [
    {
        productId: null,
        quantity: null
    }
]
// 定義商品功能變數：修改商品的ID陣列
var updateIdArray = []
// 定義訂單功能變數：使用者訂單列表的詳細訂單資料
var orderDetailsWithProducts = [
    {
        orderDetailsId: null,
        orderId: null,
        productId: null,
        productName: null,
        category: null,
        imageUrl: null,
        price: null,
        quantity: null,
        total: null, //JS加總計算
        description: null
    }
];
// 定義訂單功能變數：使用者的訂單列表
var orders = [
    {
        orderId: null,
        total: null, //JS加總計算
        memberId: null,
        createdDate: null,
        lastModifiedDate: null
    }
];
// 定義訂單功能變數：判斷創建訂單狀態
var isCreateOrderSuccess = false;
// 定義訂單功能變數：判斷創建訂單的購買量小於庫存量
var checkQuantity = true;
// 定義商品功能變數：判斷商品更新狀態
var updateSuccess = false;
// 定義商品功能變數：判斷商品刪除狀態
var deleteSuccess = false;
// 定義訂單功能變數：使用者的訂單列表+詳細訂單資料
var ordersWithDetails = [
    {
        orderId: null,
        total: null, //JS加總計算
        createdDate: null,
        orderDetailsWithProducts: [
            {
                orderDetailsId: null,
                productId: null,
                productName: null,
                category: null,
                price: null,
                quantity: null,
                detail_total: null, //JS加總計算
            }
        ]
    }
];
var vm = new Vue({
    el: "#screen",
    data: {
        datas: datas,
        products: products,
        querys: query,
        pagination: pagination,
        isLogin: isLogin,
        isMember: isMember,
        isUniqueAccount: isUniqueAccount,
        loginVariable: loginVariable,
        requestBodyForCreateMember: requestBodyForCreateMember,
        requestBodyForCreateProduct: requestBodyForCreateProduct,
        member: member,
        cookieObj: cookieObj,
        chooseProduct: chooseProduct,
        choosePro: false,
        car: car,
        ordersWithDetails: [],
        noOrder: false,
        isCreateOrderSuccess: isCreateOrderSuccess
    },
    methods: {
        updatePage: function (query) {
            callPage(query)
        },
        changePage: function (pageNum) {
            vm.pagination.currentPage = pageNum;
            vm.querys.limit = vm.pagination.pageLimit[pageNum - 1];
            vm.querys.offset = vm.pagination.pageOffset[pageNum - 1];
            callPage(vm.querys);
        },
        changeAnother: function () {
            if (vm.isLogin === true) {
                vm.isLogin = false;
            } else {
                vm.isLogin = true;
            }
        },
        login: function () {
            loginMember();
        },
        showLoginPage: function () {
            showLoginPage();
        },
        register: function (requestBody) {
            createMember(requestBody);
        },
        logout: function () {
            logout();
        },
        createProduct: function () {
            createProduct();
        },
        createOrder: function () {
            createOrder();
        },
        getOrders: function () {
            if (isMember || vm.isMember) {
                getOrders();
            } else {
                alert("請先登入帳號！");
            }
        },
        checkValue: function () {
            checkValue();
        },
        updateProduct: function (productId, requestBodyForUpdateProduct) {
            updateProduct(productId, requestBodyForUpdateProduct);
        },
        checkAndCreateOrder: function () {
            if (isMember || vm.isMember) {
                checkValue();
                if (checkQuantity) {
                    createOrder();
                    $('input:checkbox:checked').prop('checked', false);
                    vm.chooseProduct = false;
                }
            } else {
                $('input:checkbox:checked').prop('checked', false);
                vm.chooseProduct = false;
                alert("請先登入帳號！");
            }
            callPage(query);
        },
        checkUpdateValue: function () {
            checkUpdateValue();
            callPage(query);
            if (updateSuccess === true) {
                alert("更新成功！\n請重整頁面以顯示最新資料。")
                updateSuccess = false;
                $('input:checkbox:checked').prop('checked', false);
                vm.chooseProduct = false;
            }
        },
        checkDeleteValue: function () {
            checkDeleteValue();
            callPage(query);
            if (deleteSuccess === true) {
                alert("刪除成功！\n請重整頁面以顯示最新資料。");
                deleteSuccess = false;
                $('input:checkbox:checked').prop('checked', false);
                vm.chooseProduct = false;
            }
        }
    },
    mounted: function () {
        parseCookie(); // 將瀏覽器的cookie資訊取出放入cookieObj內
        // checkIsMember();
        // 若有cookie會員登入資訊，則取得會員資訊
        if (cookieObj["accountNumber"] !== null) {
            getMember(cookieObj["accountNumber"]);
        }
        // 取得商品頁面
        callPage(query);
    }
})

// 取得商品頁面
function callPage(query) {
    var str = "http://localhost:8080/products?search={search}&orderBy={orderBy}&limit={limit}&offset={offset}";
    str = (query.search === null) ? (str.replace("{search}", "")) : (str.replace("{search}", query.search));
    str = (query.orderBy === null) ? (str.replace("{orderBy}", "")) : (str.replace("{orderBy}", query.orderBy));
    str = (query.limit === null) ? (str.replace("{limit}", "")) : (str.replace("{limit}", query.limit));
    str = (query.offset === null) ? (str.replace("{offset}", "")) : (str.replace("{offset}", query.offset));
    $.ajax({
        method: "GET",
        url: str,
        success: function (data) {
            vm.datas = data;
            vm.products = data.results;
            var total = data.total;
            var limit = data.limit;
            var offset = data.offset;
            var pageTotal = parseInt(total / limit);
            (total % limit !== 0) ? pageTotal += 1 : pageTotal += 0
            vm.pagination.pageNum = Array.from({length: pageTotal}, (_, i) => i + 1)
            vm.pagination.pageLimit = Array.from({length: pageTotal}, x => limit)
            vm.pagination.pageOffset = Array.from(Array(pageTotal).keys()).map(x => x * limit);
            vm.pagination.pageOffset[0] = 0;
        },
        error: function (error) {
            console.log("發生錯誤，錯誤代碼為： " + error.status);
        }
    })
}
// 開啟登入/註冊視窗
function showLoginPage() {
    $("#member").css("display", "initial");
}
// 更換為登入/註冊視窗
$("#changeRegister").click(function () {
    // 每三秒檢查一次已輸入的帳號是否已被註冊
    setInterval(function () {
        checkAccountExist(vm.requestBodyForCreateMember.accountNumber);
    }, 3000);
})
// 進行登入：驗證帳密後允准登入
function loginMember() {
    if (vm.loginVariable.accountNumber === null || vm.loginVariable.accountPassword === null) {
        alert("請輸入帳號與密碼。")
    } else if ($.trim(vm.loginVariable.accountNumber) === 0 || $.trim(loginVariable.accountPassword) === 0) {
        alert("帳號與密碼不得為空值。")
    } else {
        $.ajax({
            method: "GET",
            url: "http://localhost:8080/member/" + vm.loginVariable.accountNumber,
            contentType: "application/json",
            success:
                function (res) {
                    if (vm.loginVariable.accountPassword === res.accountPassword) {
                        $("#member").css("display", "none");
                        vm.isMember = true;
                        vm.member = res;
                        checkIsMember();
                        document.cookie = 'accountNumber=' + vm.loginVariable.accountNumber + ';path=/';
                        alert("登入成功！");
                    } else {
                        alert("登入失敗！\n密碼錯誤。")
                    }
                }
            ,
            error:
                function (error) {
                    console.log("Error Status: " + error.status);
                    console.log("Error StatusCode: " + error.statusCode);
                    if (error.status === 404) {
                        alert("登入失敗！\n帳號尚未註冊。")
                    } else {
                        alert("登入失敗！錯誤代碼405不得為空值?????沒阻攔掉嗎？")
                    }
                }
        })

    }
}

// 註冊新帳號
function createMember(requestBody) {
    if (requestBody.accountNumber === null ||
        requestBody.accountPassword === null ||
        requestBody.memberFirstname === null ||
        requestBody.memberLastname === null ||
        requestBody.contactPhone === null) {
        alert("請將資料填寫完畢。\n(上述皆為必填欄位)")
    } else if ($.trim(requestBody.accountNumber).length === 0 ||
        $.trim(requestBody.accountPassword).length === 0 ||
        $.trim(requestBody.memberFirstname).length === 0 ||
        $.trim(requestBody.memberLastname).length === 0 ||
        $.trim(requestBody.contactPhone).length === 0) {
        alert("資料不得為空值。")
    } else {
        $.ajax({
            method: "POST",
            url: "http://localhost:8080/member",
            data: JSON.stringify(vm.requestBodyForCreateMember),
            contentType: "application/json",
            success:
                function (res) {
                    $("#member").css("display", "none");
                    vm.isMember = true;
                    document.cookie = 'accountNumber=' + requestBody.accountNumber + ';path=/';
                    getMember(requestBody.accountNumber);
                    alert("註冊成功！");
                }
            ,
            error:
                function (error) {
                    console.log("Error Status: " + error.status);
                    console.log("Error StatusCode: " + error.statusCode);
                    if (error.status === 500) {
                        alert("註冊失敗！\n帳號已被註冊。")
                    } else if (error.status === 400) {
                        alert("註冊失敗！\n資料皆不可為空值")
                    } else {
                        alert("註冊失敗！\n請聯絡客服0922-461-868！")
                    }
                }
        })

    }
}

// 檢查Cookie是否存在已登入帳號
function checkIsMember() {
    if (member.accountNumber !== null || cookieObj['accountNumber'] !== null) {
        isMember = true;
    } else {
        isMember = false;
    }
}

// 登入會員並取得會員資料
function getMember(accountNumber) {
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/member/" + accountNumber,
        contentType: "application/json",
        success:
            function (res) {
                vm.isMember = true;
                vm.member = res;
            }
    })

}

// 檢查欲創造的帳號是否已存在
function checkAccountExist(checkAccount) {
    if (checkAccount === null || $.trim(checkAccount).length === 0) {
        vm.isUniqueAccount = true;
    } else {
        $.ajax({
            method: "GET",
            url: "http://localhost:8080/member/" + checkAccount,
            contentType: "application/json",
            success:
                function (res) {
                    vm.isUniqueAccount = false;
                },
            error:
                function (error) {
                    if (error.status === 404) {
                        vm.isUniqueAccount = true;
                    }
                }
        })
    }
}

// 將Cookie資訊取出
function parseCookie() {
    var cookieAry = document.cookie.split(';');
    var cookie;

    for (var i = 0, l = cookieAry.length; i < l; ++i) {
        cookie = jQuery.trim(cookieAry[i]);
        cookie = cookie.split('=');
        cookieObj[cookie[0]] = cookie[1];
    }
}

// 刪除cookie及Member資訊＝登出
function logout() {
    vm.isMember = false;
    vm.member = member;
    document.cookie = "accountNumber=;path=/;expires=Thu, 01 Jan 1970 00:00:00 GMT";
    alert("登出！");
    $("#member").hide();
}

// 滑鼠點擊非登入/註冊視窗時就跳出登入視窗
$(document).mouseup(function (e) {
    var container = $("#member_window");
    var loginBtn = $("#login_btn");
    var logoutBtn = $("#logout_btn");

    // if the target of the click isn't the container nor a descendant of the container
    if (!container.is(e.target) && container.has(e.target).length === 0) {
        if (!loginBtn.is(e.target) && loginBtn.has(e.target).length === 0) {
            if (!logoutBtn.is(e.target) && logoutBtn.has(e.target).length === 0) {
                $("#member").hide();
            }
        }
    }
});

// 創建商品
function createProduct() {
    $.ajax({
        method: "POST",
        url: "http://localhost:8080/products",
        data: JSON.stringify(vm.requestBodyForCreateProduct),
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            alert("新增成功！");
            $("#input_category").val("");
            $("#input_image_url").val("");
            $("#input_product_name").val("");
            $("#input_description").val("");
            $("#input_price").val("");
            $("#input_stock").val("");
        },
        error: function (error) {
            alert("新增失敗！\n請檢查商品資訊是否皆有填寫。");
        }
    })
}

// 創建訂單
function createOrder() {
    var orderId = null;
    // 判斷是否有選取商品
    if (vm.chooseProduct) {
        $.ajax({
            method: "POST",
            url: "http://localhost:8080/order",
            contentType: "APPLICATION/JSON",
            data: JSON.stringify({
                "memberId": vm.member.memberId
            }),
            dataType: "JSON",
            success: function (res) {
                orderId = res;
                isCreateOrderSuccess = true;
                $.ajax({
                    method: "POST",
                    url: "http://localhost:8080/orders/" + orderId + "/orderDetails",
                    contentType: "APPLICATION/JSON",
                    data: JSON.stringify(vm.car),
                    success: function (res) {
                        alert("購買成功！\n您可前往訂單中心確認訂購品項。");
                    },
                    error: function (error) {
                        console.log("error: " + error);
                        console.log("訂單細項創建失敗！" + error.status);
                    }
                })
            },
            error: function (error) {
                isCreateOrderSuccess = false;
                console.log("購買失敗！請洽客服");
            }
        });
        adjustStock();
    }
    if (orderId !== null) {
        console.log("orderId不為null");
    }
}

// 取得訂單列表
function getOrders() {
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/orders?memberId=" + vm.member.memberId,
        data: "JSON",
        success: function (res) {
            vm.ordersWithDetails = [];
            // 判斷是否有訂單存在
            if (res.length === 0) {
                alert("您尚未建立任何訂單唷！\n快去探索商品吧！")
            } else {
                res.forEach(function (order) {
                    var orderId = order.orderId;
                    var createdDate = order.createdDate;
                    var orderDetails = [];
                    $.ajax({
                        method: "GET",
                        url: "http://localhost:8080/orders/" + order.orderId + "/orderDetailsWithProducts",
                        dataType: "JSON",
                        success: function (res) {
                            var total = 0;
                            res.forEach(function (orderDetail) {
                                var orderDetailsId = orderDetail.orderDetailsId;
                                var productId = orderDetail.productId;
                                var productName = orderDetail.productName;
                                var category = orderDetail.category;
                                var price = orderDetail.price;
                                var quantity = orderDetail.quantity;
                                var detail_total = price * quantity;
                                orderDetails.push({
                                    "orderDetailsId": orderDetailsId,
                                    "productId": productId,
                                    "productName": productName,
                                    "category": category,
                                    "price": price,
                                    "quantity": quantity,
                                    "detail_total": detail_total
                                })
                                total += detail_total;
                            });
                            vm.ordersWithDetails.push({
                                "orderId": orderId,
                                "total": total,
                                "createdDate": createdDate,
                                "orderDetailsWithProducts": orderDetails
                            });
                        },
                        error: function (error) {
                            console.log("此訂單沒有細項" + error.status);
                            vm.ordersWithDetails.push({
                                "orderId": orderId,
                                "total": 0,
                                "createdDate": createdDate,
                                "orderDetailsWithProducts": orderDetails
                            })
                        }
                    })
                })
            }
        },
        error: function (error) {
            console.log("使用者訂單列表查詢失敗！" + error.status);
        }
    })
}

// 將被勾選商品的商品編號(productId)及選取數量(quantity)放入購物車(Car)內
function checkValue() {
    alertNotChooseProduct();
    var checkedValue = $(".checkboxes:checked");
    checkQuantity = true;
    vm.car = [];
    requestBodyForUpdateProduct = [];
    checkedValue.each(function () {
        var productId = $(this).val();
        var category = $("#category_" + productId).text();
        var imageUrl = $("#imageUrl_" + productId).attr("src");
        var productName = $("#name_" + productId).text();
        var description = $("#description_" + productId).text();
        var stock = $("#stock_" + productId).text();
        var price = $("#price_" + productId).text();
        var quantity = $("#quantity_" + productId).val();
        if ((quantity - stock) > 0) {
            alert("購買數量不可大於庫存量。\n請調整「" + productName + "」的購買數量。");
            checkQuantity = false;
        }
        vm.car.push({
            "productId": productId,
            "quantity": quantity
        })
        requestBodyForUpdateProduct.push({
            productName: productName,
            category: category,
            imageUrl: imageUrl,
            price: price,
            stock: (stock - quantity),
            description: description
        })
    })
}

// 更新商品
function updateProduct(productId, requestBodyForUpdateProduct) {
    if (vm.chooseProduct === true) {
        $.ajax({
            method: "PUT",
            url: "http://localhost:8080/products/" + productId,
            contentType: "APPLICATION/JSON",
            data: JSON.stringify(requestBodyForUpdateProduct),
            dataType: "JSON",
            success: function (res) {
                updateSuccess = true;
                console.log("更新商品成功");
            },
            error: function (error) {
                console.log("更新商品失敗");
            }
        })
    } else {
        console.log("尚未選取商品，故無法更新。");
    }
}

// 創建訂單後修改庫存
function adjustStock() {
    if (vm.chooseProduct === true) {
        for (var i = 0; i < vm.car.length; i++) {
            var productId = vm.car[i].productId;
            updateProduct(productId, requestBodyForUpdateProduct[i]);
        }
    }
}

// 更新選取的商品
function checkUpdateValue() {
    var checkedValue = $(".checkboxes:checked");
    alertNotChooseProduct();
    checkedValue.each(function () {
        var productId = $(this).val();
        requestBodyForUpdateProduct.category = $("#category_" + productId).val();
        requestBodyForUpdateProduct.imageUrl = $("#imageUrl_" + productId).val();
        requestBodyForUpdateProduct.productName = $("#name_" + productId).val();
        requestBodyForUpdateProduct.description = $("#description_" + productId).val();
        requestBodyForUpdateProduct.stock = $("#stock_" + productId).val();
        requestBodyForUpdateProduct.price = $("#price_" + productId).val();
        updateProduct(productId, requestBodyForUpdateProduct);
        updateSuccess = true;
    })
    callPage(query);
}

// alert是否有選取商品
function alertNotChooseProduct() {
    var checked = $(".checkboxes:checked");
    if (checked.length > 0) {
        vm.chooseProduct = true;
    } else {
        vm.chooseProduct = false;
        updateSuccess = false;
        deleteSuccess = false;
        isCreateOrderSuccess = false;
        alert("尚未勾選任何品項！");
    }
}

// 刪除選取的商品
// 更新選取的商品
function checkDeleteValue() {
    var checkedValue = $(".checkboxes:checked");
    alertNotChooseProduct();
    checkedValue.each(function () {
        var productId = $(this).val();
        $.ajax({
            method: "DELETE",
            url: "http://localhost:8080/products/" + productId,
            success: function (res) {
                console.log("刪除商品成功！");
                deleteSuccess = true;
            },
            error: function (error) {
                console.log("刪除商品失敗！" + error.status);
            }
        })
    });
    callPage(query);
}