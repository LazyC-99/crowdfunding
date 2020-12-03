<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="GB18030">
<head>
    <title>用户添加</title>
    <%@include file="include-head.jsp" %>
</head>

<body>

<%@include file="include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="admin/to/login/page.html">首页</a></li>
                <li><a href="admin/get/user.html">数据列表</a></li>
                <li class="active">新增</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <form action="admin/do/save.html" role="form">
                        <p>${requestScope.exception.message}</p>
                        <div class="form-group">
                            <label for="acct">登陆账号</label>
                            <input name="loginAcct" type="text" class="form-control" id="acct" placeholder="请输入登陆账号">
                        </div>
                        <div class="form-group">
                            <label for="password">登密码</label>
                            <input name="userPswd" type="password" class="form-control" id="password" placeholder="请输入登陆密码">
                        </div>
                        <div class="form-group">
                            <label for="username">用户名称</label>
                            <input name="userName" type="text" class="form-control" id="username" placeholder="请输入用户名称">
                        </div>
                        <div class="form-group">
                            <label for="email">邮箱地址</label>
                            <input name="email" type="email" class="form-control" id="email" placeholder="请输入邮箱地址">
                            <p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
                        </div>
                        <button type="submit" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                        <button type="reset" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>

