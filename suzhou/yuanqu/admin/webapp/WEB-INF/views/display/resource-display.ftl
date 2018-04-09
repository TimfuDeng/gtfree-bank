<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <link rel="stylesheet" href="${base}/static/css/landsale.css" >
    <link href="${base}/static/thridparty/H-ui.2.0/static/h-ui/style.css" rel="stylesheet" type="text/css">
    <link href="${base}/static/thridparty/H-ui.2.0/static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css">
    <link href="${base}/static/thridparty/H-ui.1.5.6/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${base}/static/thridparty/jquery/jquery.min.js"></script>
    <script type="text/javascript">

        function send(resourceId){
            $.ajax({
                url:"${base}/console/trans/show.f",
                type:"post",
                data:{resourceId:resourceId},
                success:function(data){
                    $("#sale").html(data);
                    begincheck();
                },
                error:function(){
                    alert("error");
                }
            });
        }

        function deal(resourceId){
            $.ajax({
                url:"${base}/console/trans/show.f",
                type:"post",
                data:{resourceId:resourceId},
                success:function(data){
                    $("#deal").html(data);
                    begincheck();
                },
                error:function(){
                    alert("error");
                }
            });
        }
        function SubmitPageForm(formId,index){
            var formObj=$("#"+formId);
            var indexObjs=$(formObj).find("input [name='index']");
            if (indexObjs!=null && indexObjs.length>0){
                $(indexObjs[0]).val(index);
            }else{
                $('<input type="hidden" name="index" value="'+index+'">').appendTo(formObj);
            }
            $.ajax({
                url:"${base}/console/trans/resourceList.f",
                type:"post" ,
                data: formObj.serialize(),
                success:function(data){
                    $("#resourceSlide").html(data);
                }
            });
        }

        function begincheck(){
            var resourceId=$("#resourceId").val();
            //超时时间1.2分钟
            $.ajax({
                type: "POST",
                url: "./getoffer.f",
                data: {id:resourceId, time:$("#maxOfferTime").val()},
                cache:false,
                timeout: 90*1000,
                success: function(data){
                    if (data=="refresh"){
                        window.location.reload();
                    }else if(data!=null&&data!=''){
                        var result = eval("(" + data + ")");
                        $("#maxOfferTime").val(result.time);
//                        $("#offerFrequency").val(result.offerFrequency);
//                        $("#maxOffer").val(result.maxOffer);
                        begincheck();
                    }
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    if (XMLHttpRequest.status!=0){
//                        $('#myModalError').modal({
//                            keyboard:false,
//                            backdrop: true
//                        });
                    }
                },
                complete : function(XMLHttpRequest,status){
                    if(status=='timeout'){//超时,status还有success,error等值的情况
                        //beginAcceptOffer();
                        //alert(status + "-timeout");
                    }else if (status=='error'){
                        //alert(status + "-error");
                    }
                }
            });
        }

    </script>
    <script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/static/h-ui/js/H-ui.js"></script>
    <script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modal.js"></script>
    <script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modalmanager.js"></script>
    <script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/layer1.8/layer.min.js"></script>
</head>
<body>
<div style="width:1349px;height:1068px;overflow: hidden">
    <!--左-->
    <div style="width:70%;height:100%;float: left">
        <div style="width:100%;height:50%;" id="sale">
        </div>
        <div style="width:100%;height:50%;" id="deal">
        </div>
    </div>

    <!--右-->
    <div style="width:30%;height:100%;float: right;" id="resourceSlide">
    <#include "resource-slide.ftl"/>
    </div>
</div>
</body>
</html>