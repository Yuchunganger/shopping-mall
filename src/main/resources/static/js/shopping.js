var datas = {};
var products = [];
var query = {
    search: null,
    orderBy: null,
    limit: null,
    offset: null
};
var pagination = {
    currentPage: 1,
    pageNum: [],
    pageLimit: [],
    pageOffset: [],
}

// TODO 連結會員資料
var isLogin = true; // 切換為登入頁面(true)或註冊頁面(false)
var isMember = false; // 是否已登入
var isUniqueAccount = true;
var loginVariable = {
    accountNumber: null,
    accountPassword: null
}
var requestBodyForCreateMember = {
    accountNumber: null,
    accountPassword: null,
    memberFirstname: null,
    memberLastname: null,
    contactPhone: null,
    photoUrl: null,
}
// 定義Cookie取出後要存放的地方
var cookieObj = {
    accountNumber: null
};

var member = {
    accountNumber: null,
    accountPassword: null,
    memberFirstname: null,
    memberLastname: null,
    contactPhone: null,
    photoUrl: null,
    createdDate: null,
    lastModifiedDate: null
}

var requestBodyForCreateProduct = {
    productName: "",
    category: "",
    imageUrl: null,
    price: "",
    stock: "",
    description: ""
}

var vm = new Vue({
    el: "#screen",
    data: {
        datas: datas,
        products: products,
        querys: query,
        pagination: pagination,
        // TODO 連結會員資料
        isLogin: isLogin,
        isMember: isMember,
        isUniqueAccount: isUniqueAccount,
        loginVariable: loginVariable,
        requestBodyForCreateMember: requestBodyForCreateMember,
        requestBodyForCreateProduct: requestBodyForCreateProduct,
        member: member,
        cookieObj: cookieObj
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
        register: function (requestBody) {
            createMember(requestBody);
        },
        logout: function () {
            logout();
        },
        createProduct: function () {
            createProduct();
        }
    },
    mounted: function () {
        parseCookie(); // 將瀏覽器的cookie資訊取出放入cookieObj內
        // checkIsMember();
        // 若有cookie會員登入資訊，則取得會員資訊
        if (cookieObj["accountNumber"] !== null) {
            getMember(cookieObj["accountNumber"]);
        }
        console.log('cookie: ' + document.cookie);
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
    console.log("url = " + str);
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
            vm.pagination.pageOffset = Array.from(Array(pageTotal).keys()).map(x => x * 5);
            vm.pagination.pageOffset[0] = 0;
        },
        error: function (error) {
            console.log("發生錯誤，錯誤代碼為： " + error.status);
        }
    })
}

// 點擊「登入/註冊」以開啟登入/註冊視窗
$("#login_btn").click(
    function () {
        console.log("執行點擊登入");
        showLoginPage();
    }
)

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
    console.log("帳號 " + vm.loginVariable.accountNumber)
    console.log("密碼 " + vm.loginVariable.accountPassword)
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
                    console.log("Response: " + res);
                    console.log("Member Account為: " + res.accountNumber);
                    console.log("Member Password為: " + res.accountPassword);
                    if (vm.loginVariable.accountPassword === res.accountPassword) {
                        $("#member").css("display", "none");
                        vm.isMember = true;
                        vm.member = res;
                        checkIsMember();
                        document.cookie = 'accountNumber=' + vm.loginVariable.accountNumber + ';path=/';
                        console.log("Cookie: " + document.cookie);
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
    console.log("是否為專屬帳號 " + isUniqueAccount)
    console.log("帳號 " + requestBody.accountNumber)
    console.log("密碼 " + requestBody.accountPassword)
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
                    console.log("Response String: " + res);
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
                console.log("Response: " + res);
                console.log("Member Account為: " + vm.member.accountNumber);
                console.log("Member Password為: " + vm.member.accountPassword);
                console.log("Member Firstname為: " + res.memberFirstname);
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
                    console.log("檢查結果：帳號已存在")
                    vm.isUniqueAccount = false;
                },
            error:
                function (error) {
                    if (error.status === 404) {
                        console.log("檢查結果：帳號不存在(404)");
                        vm.isUniqueAccount = true;
                    }
                }
        })
    }
}

// TODO 檢查：jquery = $ ?
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
    console.log("登出後cookie: " + document.cookie);
    console.log("登出後member.firstname: " + vm.member.memberFirstname);
}

//滑鼠點擊非登入/註冊視窗時就跳出登入視窗
$(document).mouseup(function (e) {
    var container = $("#member_window");
    var loginBtn = $("#login_btn");

    // if the target of the click isn't the container nor a descendant of the container
    if (!container.is(e.target) && container.has(e.target).length === 0) {
        if (!loginBtn.is(e.target) && loginBtn.has(e.target).length === 0) {
            $("#member").hide();
        }
    }
});

function createProduct(){
    $.ajax({
        method: "POST",
        url: "http://localhost:8080/products",
        data: JSON.stringify(vm.requestBodyForCreateProduct),
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            console.log("requestBody: " + vm.requestBodyForCreateProduct);
            console.log("response product: " + data);
            alert("新增成功！");
        },
        error: function (error) {
            console.log("requestBody: " + vm.requestBodyForCreateProduct);
            console.log("requestBody type: " + typeof (vm.requestBodyForCreateProduct));
            console.log("error status: " + error.status);
            console.log("error: " + error);
            alert("新增失敗！");
        }
    })
}
