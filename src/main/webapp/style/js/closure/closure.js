$(function () {
    sleep(writeNum,2000);
});

function foo(x) {
    var tmp = 1;
    return function (y) {
        return x + y + (tmp++);
    }
}

function writeNum() {
    var bar = foo(5); // bar 现在是一个闭包
    document.getElementById("div1").innerText=bar(5);
}

//等待delay（毫秒）后，执行func方法；
function sleep(func,delay) {
    window.setTimeout(func,delay);
}