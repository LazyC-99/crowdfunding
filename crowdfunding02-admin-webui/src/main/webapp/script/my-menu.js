/**
 * 在页面生成目录树
 */
function generateTree() {
//1.
    $.ajax({
        url:"/menu/get/whole/tree.json",
        type:"post",
        dataType:"json",
        success:function (response) {
            if (response.result=="SUCCESS"){
                //2.用于存储对zTree的设置
                const setting = {
                    view:{
                        addDiyDom:myAddDiyDom,
                        addHoverDom:myAddHoverDom,
                        removeHoverDom: myRemoveHoverDom
                    },
                    data:{
                        key:{
                            url:"tan90"
                        }
                    }
                }
                //3.获取生成树形结构的JSON数据
                const zNodes = response.data;
                //4.初始化树形结构
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            }
            if (response.result=="FAILED"){
                layer.msg(response.message)
            }
        }
    })
}
/**
 *替换icon
 * @param treeId 整个树形结构附着的ul标签的id
 * @param treeNode 当前树形节点的全部的数据,包括从后端查询得到的Menu对象的全部属性
 */
function myAddDiyDom(treeId,treeNode) {
    const spanId = treeNode.tId + '_ico'
    $("#"+spanId).removeClass().addClass(treeNode.icon)
}

/**
 * 鼠标移入节点范围添加按钮组
 * 按钮组标签结构:<span><a><i></i></a> <a><i></i></a></span>
 */

function myAddHoverDom(treeId,treeNode) {
    const anchorId = treeNode.tId + '_a'
    const  btnGroupId = treeNode.tId+"_btnGrp"
    if($("#"+btnGroupId).length>0){
        return
    }
    //准备各个按钮
    const addBtn = "<a id='"+treeNode.id+"' class='addBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#'title='添加节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg '></i></a>"
    const removeBtn = "<a id='"+treeNode.id+"' class='removeBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='删除节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg '></i></a>"
    const editBtn = "<a id='"+treeNode.id+"' class='editBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='修改节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg '></i></a>"
    let btnGroup = ""
    //获取当前节点的级别
    const level = treeNode.level
    if (level==0){//级别为0时是根节点,只能添加子节点
        btnGroup = addBtn
    }
    if (level==1){//级别为1时是分支节点,可以添加,修改子节点
        btnGroup = addBtn + " " + editBtn

        //获取当前节点的子节点
        if (treeNode.children.length == 0) {//没有子节点,可以删除,添加,修改
            btnGroup = btnGroup + " " + removeBtn
        }
    }
    if (level==2) {//级别为1时是叶子节点,可以添加,修改子节点
        btnGroup = editBtn+" "+removeBtn
    }
    $("#"+anchorId).after("<span id='"+btnGroupId+"'>"+btnGroup+"</span>")
}

/**
 * 鼠标移出节点范围删除按钮组
 */
function myRemoveHoverDom(treeId,treeNode) {
    const  btnGroupId = treeNode.tId+"_btnGrp"
    $("#"+btnGroupId).remove()
}