<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="GB18030">
<head>
    <%@include file="include-head.jsp" %>
    <link rel="stylesheet" href="ztree/zTreeStyle.css">
    <script src="ztree/jquery.ztree.all-3.5.min.js"></script>
    <script src="script/my-menu.js"></script>
</head>

<body>

<%@include file="include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <div class="panel panel-default">
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表 <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="modal-menu-add.jsp" %>
<%@include file="modal-menu-edit.jsp" %>
<%@include file="modal-menu-confirm.jsp" %>

</body>
<script>
    $(function () {
        //生成目录树
        generateTree();


        /**
         * 添加节点绑定单击响应函数
         */
        $("#treeDemo").on("click",".addBtn",function () {
            //将当前节点的id,作为新节点的pid(父节点)保存到顶层变量
            window.pid = parseInt(this.id)

            $("#menuAddModal").modal("show")
            return false;
        })
        //给模态框中的保存按钮添加事件
        $("#menuSaveBtn").click(function () {
            const name = $.trim($("#menuAddModal [name=name]").val())
            const url = $.trim($("#menuAddModal [name=url]").val())
            const icon = $.trim($("#menuAddModal [name=icon]:checked").val())
            $.ajax({
                url:"/menu/save.json",
                type:"post",
                data:{
                    pid:window.pid,
                    name:name,
                    url:url,
                    icon:icon
                },
                dataType:"json",
                success: function (response) {
                    if (response.result=="SUCCESS"){
                        layer.msg("操作成功!!")
                        //重新生成目录树
                        generateTree();
                    }
                    if (response.result=="FAILED"){
                        layer.msg("操作失败!!"+response.message)
                    }
                    //关闭模态框
                    $("#menuAddModal").modal("hide")

                    //清空表单
                    $("#menuResetBtn").click()
                },
                error:function (response) {
                    layer.msg(response.status+" "+response.statusText)
                }
            })
        })

        /**
         * 修改节点绑定单击响应函数
         * 回显节点数据
         */
        $("#treeDemo").on("click",".editBtn",function () {
            window.id = this.id
            $("#menuEditModal").modal("show")

            //获取zTreeObj对象
            const zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
            const key = "id"
            const value = window.id
            const  currentNode = zTreeObj.getNodeByParam(key,value)

            $("#menuEditModal [name=name]").val(currentNode.name)
            $("#menuEditModal [name=url]").val(currentNode.url)
            $("#menuEditModal [name=icon]").val([currentNode.icon])
            return false;
        })
        $("#menuEditBtn").click(function () {
            const name = $("#menuEditModal [name=name]").val()
            const url = $("#menuEditModal [name=url]").val()
            const icon = $("#menuEditModal [name=icon]:checked").val()

            $.ajax({
                url:"/menu/edit.json",
                type:"post",
                data:{
                    id:window.id,
                    name:name,
                    url:url,
                    icon:icon
                },
                dataType:"json",
                success: function (response) {
                    if (response.result=="SUCCESS"){
                        layer.msg("操作成功!!")
                        //重新生成目录树
                        generateTree();
                    }
                    if (response.result=="FAILED"){
                        layer.msg("操作失败!!"+response.message)
                    }
                    //关闭模态框
                    $("#menuEditModal").modal("hide")

                },
                error:function (response) {
                    layer.msg(response.status+" "+response.statusText)
                }
            })
        })

        /**
         * 删除节点绑定单击响应函数
         */
        $("#treeDemo").on("click",".removeBtn",function () {
            window.id = this.id
            $("#menuConfirmModal").modal("show")

            //获取zTreeObj对象
            const zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
            const key = "id"
            const value = window.id
            const  currentNode = zTreeObj.getNodeByParam(key,value)

            $("#removeNodeSpan").html("<i class='"+currentNode.icon+"'></i>"+currentNode.name)

            return false;
        })

        $("#confirmBtn").click(function () {

            $.ajax({
                url:"/menu/remove.json",
                type:"post",
                data:{
                    id:window.id
                },
                dataType:"json",
                success: function (response) {
                    if (response.result=="SUCCESS"){
                        layer.msg("操作成功!!")
                        //重新生成目录树
                        generateTree();
                    }
                    if (response.result=="FAILED"){
                        layer.msg("操作失败!!"+response.message)
                    }
                    //关闭模态框
                    $("#menuConfirmModal").modal("hide")

                },
                error:function (response) {
                    layer.msg(response.status+" "+response.statusText)
                }
            })
        })
    })
</script>
</html>

