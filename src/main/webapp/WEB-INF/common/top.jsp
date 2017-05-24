<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!--头部通栏条-->
<div class="topline w_100">
	<div class="line w_980">
		<div class="zcbox">
			<div class="xlogo">
<!-- 				<a href="#"><img src="style/picture/logo.png" /></a> -->
			</div>
		</div>
		<div class="ycbox">
			<p>
				<%--<span class="s1">--%>
					<%--<span><a href="#" class="active">国内版</a></span>|<span><a--%>
						<%--href="#">国际版</a></span></span>--%>
				<span>2013年4月22日&nbsp;星期三</span>|<span>欢迎您：admin</span>|<span><a
					href="#">修改密码</a></span>|<span><a href="#">退出</a></span>
			</p>
		</div>
	</div>
</div>
<!--头部通栏条end-->
<!--网站头部-->
<div class="header">
	<div class="logo">
		<a href="index.html"><img src="style/images/banner_0215.jpg" /></a>
	</div>
	<div class="side-search">
		<form method="get" action="">
			<input class="search-txt" type="text" name="text" value=""
				placeholder="请输入关键字"> <input class="search-btn"
				type="submit" value="">
		</form>
	</div>
</div>
<!--网站头部end-->
<!--网站导航-->
<div class="topmeau w_980">
	<ul class="nav_ul">
		<c:forEach items="${list }" var="list">
			<li id="${list.name }"><a href="${list.url }" id="${list.id }">${list.name }</a></li>
		</c:forEach>
	</ul>
	<ul class="nav_ul_hide"></ul>
	<a href="#" class="setNav" title="导航设置">导航设置</a> <a href="#"
		class="more" title="更多">更多</a>
</div>

<!-- 右侧浮动层：设置属性 -->
			<%@include file="../common/setBoxMenu.jsp"%><!-- head -->