<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" session="false" trimDirectiveWhitespaces="true"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    String n = request.getParameter("active");
    if (n != null && !"".equals(n.trim())) {
        pageContext.setAttribute("active", n);
    }

%>
<!--头部通栏条-->
<div class="topline w_100">
    <div class="line w_980">
        <div class="zcbox">
            <%-- <div class="xlogo"> --%>
                <%--<a href="http://www.gpdata.cn/" target="_Blank">--%>
                    <%-- <span>logo</span> --%>
                <%--</a>--%>
            <%-- </div> --%>
        </div>
        <div class="ycbox">
            <p style="float:left"><span id="ctime"></span></p>
        </div>

        <div class="ycbox" style='float:right'>
            <p>
                <%--<span class="s1"><span><a href="#" class="active">国内版</a></span>|<span><a--%>
                <%--href="#">国际版</a></span></span>--%>
                <span>欢迎您：<shiro:principal/><c:out
                    value="${sessionScope.username }"></c:out></span>|<span><a
                    href="alert">修改密码</a></span>|<span><a href="user-log-out">退出</a></span>
            </p>
        </div>
        <div class="side-search">
            <form method="get" action="${basePath}search">
                <select class="searchScope" name="searchScope">
                    <option value="3" <c:if test="${searchScope == 3}">selected</c:if>>-全文-</option>
                    <option value="1" <c:if test="${searchScope == 1}">selected</c:if>>-标题-</option>
                    <option value="2" <c:if test="${searchScope == 2}">selected</c:if>>-正文-</option>
                    <option value="4" <c:if test="${searchScope == 4}">selected</c:if>>-来源-</option>
                    <option value="5" <c:if test="${searchScope == 5}">selected</c:if>>-作者-</option>
                </select>
                <input class="search-txt" type="text" name="keywords" value="${searchAll}" placeholder="请输入关键字"> <input class="search-btn" type="submit" value="">
            </form>
        </div>
    </div>
</div>
<!--头部通栏条end-->
<!--网站头部-->
<div class="header">
    <div class="logo">
        <a href="#" target="_Blank">
        </a>
        <img src="${basePath}style/images/bannner_0215.jpg" />
    </div>
</div>
<!--网站头部end-->
<div class="topmeau w_980">
    <ul class="nav_ul">
        <%--<li id="主页"><a href="${basePath}index" id="1">主页</a></li>--%>
        <c:out value="${active1}"></c:out>
        <c:forEach items="${list }" var="list">
            <%--${list}--%>
            <shiro:hasPermission name="${list.url}">
                <li id="${list.name }">
                    <a href="${basePath}${list.url }" class="active" id="${list.id }">${list.name }</a>
                    <c:forEach items="${childlist }" var="clist">
                        <%--<c:out value="${list.id}"></c:out><c:out value="${clist.parentId eq list.id}"></c:out>--%>
                        <c:if test="${clist.parentId eq list.id}">
                            <span><a href="${basePath}${clist.url }" style="display: none;position: absolute;left: 0;top:40px; width: 81px;height: 40px;" class="childmore  ${clist.id }">${clist.name }</a></span>
                        </c:if>
                    </c:forEach>
                </li>
            </shiro:hasPermission>

        </c:forEach>
    </ul>
    <ul class="nav_ul_hide"></ul>
    <a href="#" class="setNav" title="导航设置">导航设置</a> <a href="#"
                                                        class="more" title="更多">更多</a>
</div>
<!--导航效果-->
<script>
    //获取当前时间

    $(document).ready(function () {
        var date = new Date();
        var week = null;
        switch (date.getDay()) {
            case 0:
            {
                week = "星期日";
                break;
            }
            case 1:
            {
                week = "星期一";
                break;
            }
            case 2:
            {
                week = "星期二";
                break;
            }
            case 3:
            {
                week = "星期三";
                break;
            }
            case 4:
            {
                week = "星期四";
                break;
            }
            case 5:
            {
                week = "星期五";
                break;
            }
            case 6:
            {
                week = "星期六";
                break;
            }
        }
        var currentTime = date.getFullYear() + "年"
                + (date.getMonth() + 1) + "月" + date.getDate() + "日"
                + " " + week;
        $("#ctime").text(currentTime);

        
        $('.nav_ul li a').each(function () {
        if ($($(this))[0].href == String(window.location))
        $(this).addClass('current');
        });
        $('.topmeau .nav_ul li a').on("mouseover mouseout",function(){
            var childmore=$(this).parent('li').find('span a');
            childmore.fadeIn().css("display","inline-block");
            setTimeout(function(){
                childmore.fadeOut();
            },3000);

        })
        $('.topmeau .nav_ul li a').parent('li').find('span a').on("mouseover mouseout",function(){
            return false;  // 阻止事件冒泡
        });
    })
    console.log(location.pathname)
</script>
<script>
    $(".nav_ul_hide").mouseover(function(){
        $('.more').css({'display':'none'})
    })
    $(".nav_ul_hide").mouseout(function(){
        $('.more').css({'display':'block'})
    })
</script>
