var isLogin = true;
var isMember = false;
var isUniqueAccount = true;
var loginVariable = {
    accountNumber: null,
    accountPassword: null
}
var requestBody = {
    accountNumber: null,
    accountPassword: null,
    memberFirstname: null,
    memberLastname: null,
    contactPhone: null,
    photoUrl: null,
}
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
const vm = new Vue({
    el: ".screen",
    data: {
        isLogin: isLogin,
        isMember: isMember,
        isUniqueAccount: isUniqueAccount,
        loginVariable: loginVariable,
        requestBody: requestBody,
        member: member
    },
    methods: {
        changeAnother: function () {
            if (vm.isLogin === true) {
                vm.isLogin = false;
            } else {
                vm.isLogin = true;
            }
        },
        login: function () {
            getMember();
        },
        register: function (requestBody) {
            createMember(requestBody);
        }
    }
})

// 預設首頁會隱藏登入視窗
$("#member").css("display", "none");

// 點擊「登入/註冊」以開啟登入/註冊視窗
$("#login_btn").click(
    function () {
        console.log("執行點擊登入");
        showLoginPage();
    }
)

// 開啟登入/註冊視窗
function showLoginPage() {
    $("#member").css("display", "unset");
}

// 更換為登入/註冊視窗
$("#changeRegister").click(function () {
    // 每三秒檢查一次已輸入的帳號是否已被註冊
    setInterval(function () {
        checkAccountExist(requestBody.accountNumber);
    }, 3000);
})

// 進行登入：驗證帳密後允准登入
function getMember() {
    console.log("帳號 " + loginVariable.accountNumber)
    console.log("密碼 " + loginVariable.accountPassword)
    if (loginVariable.accountNumber === null || loginVariable.accountPassword === null) {
        alert("請輸入帳號與密碼。")
    } else if ($.trim(loginVariable.accountNumber) === 0 || $.trim(loginVariable.accountPassword) === 0) {
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
                        vm.member = res;
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
            data: JSON.stringify(vm.requestBody),
            contentType: "application/json",
            // contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
            success:
                function (res) {
                    console.log("Response String: " + res);
                    // console.log("Member Account為: " + res.accountNumber);
                    // console.log("Member Password為: " + res.accountPassword);
                    $("#member").css("display", "none");
                    vm.isMember = true;
                    vm.loginVariable.accountNumber = vm.requestBody.accountNumber;
                    vm.loginVariable.accountPassword = vm.requestBody.accountPassword;
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
                                    vm.member = res;
                                }
                            }
                    })
                    alert("註冊成功！");
                    // vm.member = res;
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

//檢查欲創造的帳號是否已存在
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
