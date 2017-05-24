<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!-- 尾部 -->

<div class="alertTip"
     style="width: 300px;height:100px;position: fixed;bottom: 20px;right: 25px;font-size: 14px;line-height: 14px;z-index: 1;background: #ddd; display: none;">
    <div class="closeAL btn " style="position:absolute; top:10px;right:10px"><span>&times;</span></div>

    <div style="margin:20px;margin-top:40px;" class="alertContent">
    </div>
</div>
<script>
    function tryShowalert() {
        $.ajax({
            url: "${basePath}getAlertTips",
            type: "get",
//              data: {
//                  timmer:""//new Date()
//              },
            dataType: "json",
            success: function (data) {
                //console.log(data);
                if (data.data === 'NODATA') {
                    return false;
                } else {
                    var audio = new Audio("style/4130.wav");
                    audio.play();
                    $('.alertTip').fadeIn();
                    setTimeout(function () {
                        $('.alertTip').fadeOut()
                    }, 10000);
                    $(".alertContent").html("<a style='color:#333' href='${basePath}warning'>您有新的舆情动态，请查看！共" + data.data + "条</a>");
                }
            },
            error: function () {
            }
        })
    }
    $(document).ready(function () {
        $('.closeAL').click(function (e) {
            e.preventDefault();
            $('.alertTip').fadeOut()
        })
        tryShowalert();
        setInterval(function () {
            tryShowalert();
        }, 60000)
    })
    function replaceTime(str) {
        var mydate = new Date();
        var y = mydate.getFullYear();
        var m = mydate.getMonth() + 1;
        var d = mydate.getDate();
        var dt = y + "-" + (m < 10 ? "0" + m : m) + "-" + (d < 10 ? "0" + d : d);
        var dt2 = (m < 10 ? "0" + m : m) + "-" + (d < 10 ? "0" + d : d);
        return str.replace(new RegExp(dt, "gm"), "<span style='color:#db6203;'>今日</span>");
    }
    function articlecontent(id) {
        <!-- 重写跳转方法覆盖之前的 添加点击后颜色变化 -->
        $('#' + id).css('color', '#810081')
        window.open("${basePath}ArticleDetailAction?articleId=" + id);
    }
    var pssss;
    function keyDown(e, v) {
        <!--重写jumpPage 为了获得 $("#jumpPage") -->
        pssss = $(v).val();
//    console.log(pageNo);
        var ev = window.event || e;
        if (ev.keyCode == 13) {
            getData(-3);
        }

    }
    function getLocalTime(nS) {
        return new Date(parseInt(nS) * 1000).toLocaleString().replace(/:\d{1,2}$/, ' ');
    }
function show_sub(v) {
    if(v!=0){
        var checkResult = checkSelect();
        if (checkResult == true) {
         var   articleIds = getArticleIds();
            $.get("${basePath}updateNewsList",{
                rnd : Math.random(),
                newsId:articleIds,
                typeId:v
            },function (data) {
                console.log(data);
                if(data.data==true){
                    window.location.reload();
                }else{
                    alert(data.cause);
                }
            })
        }
    }
}
</script>
<div id="footer">
    【Copyright2015 by GPData】
    www.gpdata.cn
</div>
