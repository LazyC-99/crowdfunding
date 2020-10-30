// 显示确认模态框
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


//生成页面数据
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

        const checkBtn = "<button type='button' class='btn btn-success btn-xs'><i class=' glyphicon glyphicon-check'></i></button>"
        const pencilBtn = "<button type='button' class='btn btn-primary btn-xs pencilBtn '><i class=' glyphicon glyphicon-pencil'></i></button>"
        const removeBtn = "<button type='button' class='btn btn-danger btn-xs removeBtn'><i class=' glyphicon glyphicon-remove'></i></button>"
        const buttonTd = "<td>"+checkBtn+" "+pencilBtn+" "+removeBtn+"</td>"

        const tr = "<tr id='"+roleId+"'>"+numberTd+checkboxTd+roleNameTd+buttonTd+"</tr>"
        $("#data-container").append(tr)
    }
    generateNavigator(pageInfo)
}
//生成分页条
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