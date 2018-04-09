<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title></title>
    <style type="text/css">
        <!--
        .STYLE1 {
            font-family: "黑体";
            font-size: 24px;
            font-weight: bold;
        }
        .STYLE2 {
            font-family: "黑体";
            font-style: italic;
            font-size: 14px;
        }
        -->
    </style>
</head>
<body>
<table border="1">
    <thead>
    <th width="200">报名者账号</th>
    <th>报名者</th>
    <th>联系人</th>
    <th>联系方式</th>
    <th>联系地址</th>
    </thead>
    <tbody>
    <tr>
        <td>${transUser.userName!}</td>
        <td>${transUser.viewName!}</td>
        <td>${userApplyInfo.contactPerson}</td>
        <td>${userApplyInfo.contactTelephone}</td>
        <td>${userApplyInfo.contactAddress}</td>
    </tr>
    </tbody>
</table>
</table>
</body>
</html>