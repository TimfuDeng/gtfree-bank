/**
 * Created by liushaoshuai on 2017/2/21.
 */

//基本配置
var setting = {
    view: {
        dblClickExpand: false
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        beforeClick: beforeClick,
        onClick: onClick
    }
};

//加载ztree数据
var zNodes;
function loadZNodes(url,obj){
    $.post(url,function(data){
        zNodes=data;
        $.fn.zTree.init($("#"+obj), setting, zNodes);
    });
}


function beforeClick(treeId, treeNode){
    var check = (treeNode && !treeNode.isParent);
    if(!check) {
        alert("请选择具体用途！");
        return false;
    };
    return true;
}



function hideMenu(){
    $("#menuContent").fadeOut("fast");
    $("body").unbind("mousedown",onBodyDown);
}
function onBodyDown(e){
    if(!(e.target.id == "menuBtn" || e.target.id == "menuContent" || $(e.target).parents("#menuContent").length>0)){
        hideMenu();
    }
}
