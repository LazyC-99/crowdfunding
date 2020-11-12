<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <link rel="stylesheet" href="css/login.css">
    <script src="jquery/jquery-3.5.1.js"></script>
    <script src="bootstrap/js/bootstrap.min.js"></script>
    <script src="layer-v3.1.1/layer/layer.js"></script>
    <script>
        $(function () {
            $("button").click(function () {
                //相当于浏览器后退按钮
                window.history.back();
            })
        })
    </script>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <div><a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
    </div>
</nav>

<div class="container" style="text-align: center">
    <h2 class="form-signin-heading">
        <i class="glyphicon glyphicon-log-in"></i> 系统消息
    </h2>
    <h3>错误信息:${requestScope.exception.message}</h3>

    <button>返回上一页</button>

</div>
</body>
</html>
