<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="GB18030">
<head>
    <title>
        角色维护
    </title>
    <%@include file="include-head.jsp" %>
    <link rel="stylesheet" href="css/pagination.css">
    <script src="script/jquery.pagination.js"></script>
</head>

<body>

<%@include file="include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                    <div class="input-group-addon">查询条件</div>
                                <input id="keywordInput" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="searchBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button id="batchRemoveBtn" type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                        <button id="showAddModal" type="button" class="btn btn-primary" style="float:right;" ><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th width="30"><input id="summaryBox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="data-container">
<%--                            <tr>--%>
<%--                                <td>1</td>--%>
<%--                                <td><input type="checkbox"></td>--%>
<%--                                <td>PM - 项目经理</td>--%>
<%--                                <td>--%>
<%--                                    <button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>--%>
<%--                                    <button type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>--%>
<%--                                    <button type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>--%>
<%--                                </td>--%>
<%--                            </tr>--%>
                            </tbody>
                            <tfoot>
                            <tr >
                                <td colspan="6" align="center">
                                    <div id="pagination" class="pagination"></div>
<%--                                    <ul class="pagination">--%>
<%--                                        <li class="disabled"><a href="#">上一页</a></li>--%>
<%--                                        <li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>--%>
<%--                                        <li><a href="#">2</a></li>--%>
<%--                                        <li><a href="#">3</a></li>--%>
<%--                                        <li><a href="#">4</a></li>--%>
<%--                                        <li><a href="#">5</a></li>--%>
<%--                                        <li><a href="#">下一页</a></li>--%>
<%--                                    </ul>--%>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="modal-role-add.jsp"%>
<%@include file="modal-role-edit.jsp"%>
<%@include file="modal-role-confirm.jsp"%>

</body>
<script src="script/rolePage.js"></script>
<script>
    $(function(){
        //分页
        window.pageNum= 1;
        window.pageSize= 5;
        window.Keyword= "";
        generatePage();

        //查询
        $("#searchBtn").click(function () {
            window.keyword = $("#keywordInput").val();
            generatePage();
        })

        //打开新增角色页面
        $("#showAddModal").click(function () {

            $("#addModal").modal()
        })

        //保存角色响应事件
        $("#saveRoleBtn").click(function () {
            const roleName =$("#addModal [name=roleName]").val()
            $.ajax({
                url:"/role/save.json",
                type:"post",
                data:{
                    "roleName":roleName
                },
                dataType:"json"
            })
            .done(function (response) {
                if (response.result=="SUCCESS"){
                    layer.msg("操作成功!!")
                    window.pageNum = 99999999
                    generatePage();
                }
                if (response.result=="FAILED"){
                    layer.msg("操作失败!!+"+response.statusText)
                }
            })
            .fail(function (response) {
                layer.msg("操作失败!!+"+response.status+" "+response.statusText)
            })
            .always(function () {
                //关闭清理模态框
                $("#addModal").modal("hide")
                $("#addModal [name=roleName]").val("")

            })
        })

        //打开修改模态框(on()函数)
        //首先找到所有 动态生成 的元素所附着的 静态 元素
        $("#data-container").on("click",".pencilBtn",function () {
            $("#editModal").modal()

            //获取回显数据
            const roleName = $(this).parent().prev().text()
            window.roleId =  $(this).parent().parent().attr("id")
            $("#editModal [name=roleName]").val(roleName)

        })

        //修改角色响应事件
        $("#editRoleBtn").click(function () {
            $.ajax({
                url:"/role/edit.json",
                type:"post",
                data:{
                    "id":window.roleId,
                    "roleName":$("#editModal [name=roleName]").val()
                },
                dataType:"json"
            })
            .done(function (response) {
                if (response.result=="SUCCESS"){
                    layer.msg("操作成功!!")
                    generatePage();
                }
                if (response.result=="FAILED"){
                    layer.msg("操作失败!!+"+response.statusText)
                }
            })
            .fail(function (response) {
                layer.msg("操作失败!!+"+response.status+" "+response.statusText)
            })
            .always(function () {
                //关闭模态框
                $("#editModal").modal("hide")

            })

        })

        //全选
        $("#summaryBox").click(function () {
            $(".itemBox").prop("checked",this.checked)
        })
        $("#data-container").on("click",".itemBox",function () {
            //已选中数量
            const checkedBoxCount = $(".itemBox:checked").length
            //总数
            const checkBoxCount = $(".itemBox").length

            $("#summaryBox").prop("checked",checkedBoxCount==checkBoxCount)

        })



            //打开删除模态框
        $("#data-container").on("click",".removeBtn",function () {
            let roleArray = [{//单个删除
                roleId:$(this).parent().parent().attr("id"),
                roleName:$(this).parent().prev().text()
            }]
            showConfirmModal(roleArray);
        })

        $("#batchRemoveBtn").click(function () {//批量删除
            let roleArray = []
            $(".itemBox:checked").each(function () {
                roleArray.push({
                    roleId:$(this).parent().parent().attr("id"),
                    roleName:$(this).parent().next().text()
                })
            })
            if (roleArray.length==0){
                layer.msg("请至少选择一个!")
                return ;
            }
            showConfirmModal(roleArray);
        })

        //执行删除
        $("#confirmRoleBtn").click(function () {
            const requestBody = JSON.stringify(window.roleIdArray)
            $.ajax({
                url:"/role/remove.json",
                type:"post",
                data:requestBody,
                contentType:"application/json;charset=UTF-8",
                dataType:"json"
            })
            .done(function (response) {
                if (response.result=="SUCCESS"){
                    layer.msg("操作成功!!")
                    generatePage();
                }
                if (response.result=="FAILED"){
                    layer.msg("操作失败!!+"+response.statusText)
                }
            })
            .fail(function (response) {
                layer.msg("操作失败!!+"+response.status+" "+response.statusText)
            })
            .always(function () {
                //关闭模态框
                $("#confirmModal").modal("hide")

            })
        })

    });
</script>
</html>

