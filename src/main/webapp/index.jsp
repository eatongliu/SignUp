<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1" />
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title>首页</title>
    <script type="text/javascript" src="style/js/jquery-1.11.2.min.js"></script>
    <style>
        hr{
            border:1px dashed green;
            /*height:1px;*/
        }
    </style>
    <script type="text/javascript">
        $(function () {
            var text = 1;
            setInterval(function() {
                text = text + 1;
                document.getElementById('sp').innerHTML = text+'';
            }, 3000);
        });

    </script>
</head>
<body>
<h2>文明的小伙砸!</h2>
<div>
    <a href="${basePath}/user/1">显示用户</a>
    <hr/>
    <a href="${basePath}/content/closure">测试闭包</a>
    <span id="sp"></span>
</div>
</body>
</html>
