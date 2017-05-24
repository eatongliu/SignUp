<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>


<!-- 微博向下滚动 -->
<script src="${basePath}style/js/jQuery.textSlider.js" type="text/javascript"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $("#weibo_list").textSlider({
            line: 1,
            speed: 1000,
            timer: 4000
        });
        // 每次滚动条数，滚动速度，滚动间隔，
    });
</script>

<!--- 地区监测，职能监测 【滚动效果】 -->
<%--<script type="text/javascript" src="${basePath}style/js/jcarousellite_1.0.1.min.js"></script>--%>
<%--<script type="text/javascript">--%>
<%--$(document).ready(function() {--%>
<%--$(".scroll").jCarouselLite({--%>
<%--auto : 0,--%>
<%--scroll : 2,--%>
<%--visible : 9,--%>
<%--speed : 2000,--%>
<%--btnNext : ".next",--%>
<%--btnPrev : ".prev"--%>
<%--});--%>
<%--});--%>

<%--$(document).ready(function() {--%>
<%--$(".scroll2").jCarouselLite({--%>
<%--auto : 0,--%>
<%--scroll : 2,--%>
<%--visible : 9,--%>
<%--speed : 2000,--%>
<%--btnNext : ".next2",--%>
<%--btnPrev : ".prev2"--%>
<%--});--%>
<%--});--%>
<%--</script>--%>

<!-- 浮动层效果 -->
<script type="text/javascript">
    function tool() {
        //$("#returnTop_layout").css("top",($(document).scrollTop()+250)+"px");
    }
    window.onscroll = tool; //固定位置
    $(function () {
        tool();

        $("#returnTop_layout a").hover(function () { //IE6的伪类
            $(this).find("var").show();
        }, function () {
            $(this).find("var").hide();
        })

        $("#tab,#yj,#redian").tab({
            "eventType": "click"
        });//监听式页签切换

    })
</script>
<!-- 存入简报夹 -->
<div id="savesmyreport" class="alertDiv" style="display: none;">
    <div class="titleDiv">
        <h2 class="name n14">
            <em id="myReportBagLabel1">存入我的报告夹</em>
        </h2>
        <em class="close"><img
                onclick="javascript:closeDialog('savesmyreport');"
                src="${basePath}style/images/close.gif" alt="关闭"/></em>
    </div>
    <div class="alertCon">
        <p id="myReportBagLabel2">请选择要保存的报告夹：</p>
        <div class="listA" id="myReportBagSelDiv">
            <select id="myReportBagSelect" name="myReportBagSelect" size="10">

            </select>
        </div>
        <p>
            <em id="myReportBagLabel3">您也可以新建报告夹：</em> <input class="txt"
                                                              type="text" id="myreportBagName" value="" maxlength="15"
                                                              onclick="this.value=''"/> <input class="btn add2"
                                                                                               type="button"
                                                                                               value="添加"
                                                                                               onclick="addMyReportBag();"/>
        </p>
        <div class="alertBtn">
            <input class="btn submit" type="button" value="确定"
                   onclick="javascript:addMyReportBagArticle();"/> <input
                class="btn submit" type="button" value="取消"
                onclick="javascript:closeDialog('savesmyreport');"/>
        </div>
    </div>
</div>
<!-- 存入简报夹 -->
<div id="saves" class="alertDiv" style="display: none;">
    <form action="" method="post">
        <input type="hidden" name="bagType" id="bagType" value="1"/>
    </form>
    <div class="titleDiv">
        <h2 class="name n14">
            <em id="bagLabel1">存入简报夹</em>
        </h2>
        <em class="close"><img onclick="javascript:closeDialog('saves');"
                               src="${basePath}style/images/close.gif" alt="关闭"/></em>
    </div>
    <div class="alertCon">
        <p id="bagLabel2">请选择要保存的简报夹：</p>
        <div class="listA" id="bagSelDiv">
            <select id="bagSelect" name="bagSelect" size="10"></select>

        </div>
        <p>
            <em id="bagLabel3">您也可以新建简报夹：</em> <input class="txt" type="text"
                                                      id="bagName" name="rename" value="" maxlength="15"
                                                      onclick="this.value=''"/> <input class="btn add2" type="button"
                                                                                       id="checknamebtn" value="添加"/>
            <!-- onclick="addBag();" -->
        </p>
        <div class="alertBtn">
            <input class="btn submit" type="button" value="确定"
                   onclick="javascript:addBagArticle();"/> <input class="btn submit"
                                                                  type="button" value="取消"
                                                                  onclick="javascript:closeDialog('saves');"/>
        </div>
    </div>
</div>
<!-- div弹框滚动 -->
<!-- 判断简报夹名字是否存在 -->
<script type="text/javascript">
    $(function () {

        $('#checknamebtn').click(function () {
            var bagName = $('#bagName').val();
            $.ajax({
                type: 'post',
                url: "nameexist",
                dataType: "json",
                data: {
                    "bagName": bagName
                },
                success: function (data) {
                    if (data) {
                        alert("简报夹名称已存在！");
                    } else {
                        //	alert(2);
                        addBag();
                    }
                },
                error: function () {
                    alert("添加失败");
                }

            });
        });
    });
</script>
<!-- 简报夹隐藏层结束 -->

<!-- 右侧浮动层：设置属性 -->
<div id="returnTop_layout" class="setBox">
    <a class="toTop" href="#" title="返回顶部"><span class="none">顶部</span></a>

    <a class="toZixun" href="javascript:void(0);" onclick="checkAllArticle();" title="全选资讯"><span class="none">资讯</span></a>
    <a class="toBag" href="javascript:void(0);" onclick="javascript:showUserBag(1);" title="存入文件夹"></a>
    <a class="tofefa" href="javascript:void(0);" onclick="javascript:showUserBag(2);" title="存入收藏夹"></a>
    <a class="toDel" href="javascript:void(0);" onclick="javascript:deleteAll()" title="删除"><span class="none">删除</span></a>
    <!--<a class="toWeibo" href="javascript:void(0);"
        onclick="checkAllWeibo();" title="全选微博"><span class="none">微博</span></a>
    <a class="toEmail" href="javascript:void(0);" title="发送邮件"><span
        class="none">发送邮件</span></a>
        -->
    <!-- 	<a class="toMess" href="javascript:void(0);" title="发送短信"><span -->
    <!-- 		class="none">发送短信</span></a>

    <!--<a class="toSet"
             href="javascript:void(0);" title="设置"><span class="none">设置</span></a> -->

    <%--<span class="none">存入文件夹</span> <var>--%>
    <%--<em onclick="deluser();">恢复首页</em>--%>
    <%--<em onclick="javascript:showUserBag(1);">资讯简报</em>--%>
    <%--<em onclick="javascript:showUserBag(2);">微博简报</em>--%>
    <%--</var> --%>
    <%-- <a class="toSet2" href="reportcenter" title="导出向导"><span--%>
    <%--class="none">导出向导</span></a>--%>
</div>
<script>
    function deleteAll() {

        var articleIds = getArticleIds();
        var weiboIds = getWeiboIds();
        if (articleIds == '' && weiboIds == '') {
            alert("请选择文章或微博");
            return;
        }
        if (articleIds == '') {
            console.log(articleIds);

        } else {
            console.log(articleIds);
            var a = $('.pageDiv span.current').html();
            var mymessage = confirm("确定删除吗？");
            if (mymessage) {
                $.post("${basePath}deleteNewsList", {newsId: articleIds}, function (data) {
                    console.log(data);
                    if (data.data) {
                    <!-- 移除dom节点 -->
                    articleIds.split(',').map(function(one){
                      if(one){
                        $('#'+one).parents('li').remove();
                      }
                    })
                        alert("删除成功！");
                        <!-- jumpPage(a); -->
                    }
                })
            }

        }


    }
</script>

<!-- 自定义导航
<div class="setBox png" id="setMenu">
<div class="addMenuBtn png">模块导航</div>
<dl class="a_setDiv" id="addMenuList">
<dd>
<a href="javascript:void(0);" id="control0" onclick="add_div(0)"
style="display: none;">焦点头条</a> <a href="javascript:void(0);"
id="control1" onclick="add_div(1)" style="display: none;">微博监测</a> <a
href="javascript:void(0);" id="control2" onclick="add_div(2)"
style="display: none;">监测数据</a> <a href="javascript:void(0);"
id="control3" onclick="add_div(3)" style="display: none;">外媒监测</a> <a
href="javascript:void(0);" id="control4" onclick="add_div(4)"
style="display: none;">舆情预警</a> <a href="javascript:void(0);"
id="control5" onclick="add_div(5)" style="display: none;">实时监测</a> <a
href="javascript:void(0);" id="control6" onclick="add_div(6)"
style="display: none;">热点事件</a> <a href="javascript:void(0);"
id="control7" onclick="add_div(7)" style="display: none;">人物监测</a>
</dd>
</dl>
</div>
-->
