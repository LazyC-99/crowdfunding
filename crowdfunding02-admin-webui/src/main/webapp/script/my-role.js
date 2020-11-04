$(function(){
    //分页
    window.pageNum= 1;
    window.pageSize= 5;
    window.Keyword= "";
    generatePage();

    /**
     * 查询
     */
    $("#searchBtn").click(function () {
        window.keyword = $("#keywordInput").val();
        generatePage();
    })

    /**
     * 全选
     */
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

    /**
     * 新增角色
     */
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

    /**
     * 修改角色
     */
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


    /**
     * 删除角色
     */
    //删除模态框数据构建
    function showConfirmModal(roleArray) {
        //打开模态框`
        $("#confirmModal").modal()
        //清楚旧数据
        $("#roleNameSpan").empty()

        //要删除角色ID
        window.roleIdArray = [];
        //遍历删除数组
        for(let i =0 ;i<roleArray.length;i++){
            const roleName = roleArray[i].roleName
            $("#roleNameSpan").append(roleName+" ")
            roleIdArray.push(roleArray[i].roleId)
        }
    }
    //打开删除模态框
    $("#data-container").on("click",".removeBtn",function () {
        let roleArray = [{
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
    $("#confirmRoleBtn").click(function () {//单个删除
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

    /**
     * 角色权限分配
     */
    //打开模态框
    $("#data-container").on("click",".checkBtn",function () {
        window.roleId =  $(this).parent().parent().attr("id")
        $("#assignModal").modal()
        fillAuthTree();
    })
    //构建数据
    function fillAuthTree() {
        const AuthTreeAjax = $.ajax({
            url:"/assign/get/auth/tree.json",
            type:"post",
            dataType:"json",
            async :false
        });
        if(AuthTreeAjax.status!=200){
            layer.msg("请求错误!"+AuthTree.status+":"+AuthTree.statusText)
            return;
        }
        //2.用于存储对zTree的设置
        const setting = {
            data:{
                simpleData: {
                    enable:true,
                    pIdKey: "categoryId"
                },
                key : {
                    name: "title"
                }
            },
            check: {
                enable: true
            }
        }
        //3.获取生成树形结构的JSON数据
        const zNodes = AuthTreeAjax.responseJSON.data;
        console.log(zNodes)
        //4.初始化树形结构
        $.fn.zTree.init($("#authTreeDemo"), setting, zNodes);
        //5.设置默认展开
        const zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo")
        zTreeObj.expandAll(true)
        //6.回显已有权限
        const AssignedListAjax = $.ajax({
            url:"/assign/get/role/auth.json",
            type:"post",
            data:{
                roleId:window.roleId
            },
            dataType:"json",
            async :false
        });
        if (AssignedListAjax.status!=200){
            layer.msg("请求错误!"+AssignedListAjax.status+":"+AssignedListAjax.statusText)
            return;
        }
        //获取已有权限的id集合
        const AssignedList = AssignedListAjax.responseJSON.data
        for (let i = 0;i<AssignedList.length;i++){
            //根据id找到节点
            const treeNode = zTreeObj.getNodeByParam("id",AssignedList[i])
            //把节点设置为勾选状态
            zTreeObj.checkNode(treeNode,true,false)
        }
    }
    //执行分配
    $("#assignAuthBtn").click(function () {
        //收集勾选的权限id
        let authIdArray = []
        const zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo")
        const checkedNodes = zTreeObj.getCheckedNodes()
        for(let i = 0;i<checkedNodes.length;i++){
            authIdArray.push(checkedNodes[i].id)
        }
        //分配
        let requestBody = {
            "roleId":[window.roleId],//转为数组方便后端接收
            "authIdArray":authIdArray
        }
        requestBody = JSON.stringify(requestBody)
        console.log(requestBody)
        $.ajax({
            url:"/assign/do/auth/assign.json",
            type:"post",
            data:requestBody,
            contentType: "application/json;charset=utf-8",
            dataType:"json",
            success:function (response) {
                if (response.result=="SUCCESS"){
                    layer.msg("操作成功!!")
                }
                if (response.result=="FAILED"){
                    layer.msg("操作失败!!+"+response.statusText)
                }
            },
            error:function (response) {
                layer.msg("操作失败!!+"+response.status+" "+response.statusText)
            }
        });
        $("#assignModal").modal("hide")
    })


});






/**
 *动态生成页面数据
 */
function generatePage() {
    //1.获取数据
    getPageInfoRemote();
}
//访问服务器pageInfo数据
function getPageInfoRemote() {
    $.ajax({
        url: "/role/get/role.json",
        type: "post",
        data: {
            "pageNum": window.pageNum,
            "pageSize": window.pageSize,
            "keyword": window.keyword
        },
        async: false,
        dataType: "json",
        success: function (response) {
            if (response.result == "FAILED") {
                layer.msg(response.message)
                return null;
            } else {
                //将数据填充到页面
                fillTableBody(response.data);
            }
        },
        error: function () {
            layer.msg("失败!")
        }
    });

}
//填充表格
function fillTableBody(pageInfo) {
    $("#data-container").empty()
    $("#keywordInput").empty()
    if (pageInfo==null||pageInfo==undefined||pageInfo.list==null||pageInfo.total==0){
        $("#data-container").append("<tr><td colspan='4'>没有查询到数据!!</td></tr>")
        return;
    }
    for (let i =0;i<pageInfo.list.length;i++){
        let role = pageInfo.list[i]
        let roleId = role.id
        let roleName = role.roleName

        const numberTd = "<td>"+(i+1)+"</td>"
        const checkboxTd = "<td><input class='itemBox' type='checkbox'></td>"
        const roleNameTd = "<td>"+roleName+"</td>"

        const checkBtn = "<button type='button' class='btn btn-success btn-xs checkBtn'><i class=' glyphicon glyphicon-check'></i></button>"
        const pencilBtn = "<button type='button' class='btn btn-primary btn-xs pencilBtn '><i class=' glyphicon glyphicon-pencil'></i></button>"
        const removeBtn = "<button type='button' class='btn btn-danger btn-xs removeBtn'><i class=' glyphicon glyphicon-remove'></i></button>"
        const buttonTd = "<td>"+checkBtn+" "+pencilBtn+" "+removeBtn+"</td>"

        const tr = "<tr id='"+roleId+"'>"+numberTd+checkboxTd+roleNameTd+buttonTd+"</tr>"
        $("#data-container").append(tr)
    }
    generateNavigator(pageInfo)
}

/**
 * 生成分页条
 */
function generateNavigator(pageInfo) {
    //总记录数
    const totalRecord = pageInfo.total

    //声明分页条相关属性
    const properties = {
        num_edge_entries: 1, //边缘页数
        num_display_entries: 4, //主体页数
        callback: pageselectCallback,
        items_per_page:pageInfo.pageSize ,//每页显示数据的数量
        current_page:pageInfo.pageNum - 1,
        prev_text:"上一页",
        next_text:"下一页"
    }
    $("#pagination").pagination(totalRecord,properties)
}

function pageselectCallback(page_index,properties) {
    window.pageNum = page_index+1
    generatePage()
    return false
}

