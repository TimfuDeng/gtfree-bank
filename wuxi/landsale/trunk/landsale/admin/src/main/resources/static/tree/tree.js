/**
 * Created by zsj on 2017/9/12.
 */
var zTreeObject;
var treeOptions = new Object();
treeOptions["checked"] = function(treeId, parentNode, childNodes){};
treeOptions["click"] = function(treeId, parentNode, childNodes){};
//treeOptions["beforeDrag"] = function(treeId, treeNodes){};
//treeOptions["onDrop"] = function(event, treeId, treeNodes, targetNode, moveType){};
treeOptions["beforeRemove"] = function(treeId, treeNode){};
treeOptions["onRemove"] = function(event, treeId, treeNode){};
treeOptions["beforeRename"] = function(treeId, treeNode, newName, isCancel){};
treeOptions["onRename"] = function(event, treeId, treeNode, isCancel){};

function initTree(treeId, url, data, addBtn, removeBtn, editFlag, delFlag) {
    var setting = {
        view: {
            addHoverDom: addBtn,
            removeHoverDom: removeBtn
        },
        edit: {
            enable: true,
            showRemoveBtn: delFlag,
            showRenameBtn: editFlag,
            removeTitle : "删除",
            renameTitle : "修改",
            drag: {
                isCopy: false,
                isMove: false
            }
        },
        data : {
            simpleData : {
                enable : true
            }
        },
        callback : {
            //beforeDrag: treeOptions.beforeDrag,
            //onDrop: treeOptions.onDrop,
            beforeRemove : treeOptions.beforeRemove,
            onRemove : treeOptions.onRemove,
            beforeRename: treeOptions.beforeRename,
            onRename : treeOptions.onRename
        }
    };

    $.ajax({
        type: "post",
        dataType: "json",
        data: data,
        url: url,
        success: function (data) {
            if (data != null) {
                zTreeObject = $.fn.zTree.init($("#" + treeId), setting, data);
            }
        }
    });
    return zTreeObject;
}