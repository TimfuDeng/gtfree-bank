<@Head_SiteMash title="附件材料" />
    <style type="text/css">
        table p {
            margin-bottom: 0px;
        }

        thead td {
            text-align: center !important;
            background-color: #f5fafe;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function () {
            getAttachments('${applyId!}');
        });

        function getAttachments(applyId){
            var url ='${base}/file/attachments.f?fileKey='+applyId;
            $.get(url,function(data){
                $('#attachments').empty();
                if(data!=null&&data.length>0){
                    for(var i=0;i<data.length;i++){
                        insertAttachment(data[i].fileId,data[i].fileName);
                    }

                }
            })
        }
        function insertAttachment(fileId,fileName){
            $("#attachments").prepend("<div class='attachment"+fileId+"'><a class='btn btn-link' target='_blank' href='${base}/file/get?fileId=" +fileId+ "'>"+fileName+"</a><br/></div>");
        }
    </script>
</head>
<body>
<#include "../common/head.ftl"/>
<div class="wp">
    <div class="row cl" >
        <div class="col-2" style="padding-right: 10px">
            <ul class="nav nav-pills nav-stacked nav-pills-stacked-example">
                <li role="presentation" class="active">
                    <a href="${base}/my/resource-list">
                        <i class="icon-th-large"></i>&nbsp;&nbsp;我的交易
                    <#assign applyCount=ResourceUtil.getApplyCountByStauts()>
                    <#if applyCount gt 0>
                        <span class="label label-success radius" style="width:15px!important;text-align: center;">${applyCount}</span>
                    </#if>
                    </a>
                </li>
                <li role="presentation" class=""><a href="${base}/my/union-list"><i class="icon-money"></i>&nbsp;&nbsp;被联合竞买信息</a></li>
            </ul>
        </div>
        <div class="col-10">
            <nav class="breadcrumb pngfix">
                <i class="iconfont">&#xf012b;</i>
                <a href="${base}/index" class="maincolor">首页</a>
                <span class="c_gray en">&gt;</span>
                <a href="${base}/my/resource-list" class="maincolor">我的竞拍地块</a>
                <span class="c_gray en">&gt;</span><span class="c_gray">附件材料</span>
            </nav>
            <table class="table table-border table-bordered">
                <tr>
                    <td width="100px" style="background-color: #F5F5F5;">
                        附件材料
                    </td>
                    <td width="600px">
                        <div id="attachments">
                        </div>
                    </td>
                </tr>

            </table>
        </div>
    </div>
</div>
<#include "../common/foot.ftl"/>

<script type="text/javascript" src="${base}/static/js/dist/layout.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/layer/layer.js"></script>
</body>
</html>