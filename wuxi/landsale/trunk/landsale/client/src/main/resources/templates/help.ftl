<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>国有建设用地网上交易系统-帮助中心</title>
    <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/jquery.min.js"></script>
    <link rel="stylesheet" href="${base}/js/dist/help.css">
</head>
<script>
    $(document).ready(function () {
        loadContent();
    });

    function loadContent() {
        var regionCodes = window.parent.document.getElementById("regionCode").value;
        $("#main").load("${base}/material/helpContent", {regionCode: regionCodes}, function () {
            $("iframe", window.parent.document).attr("height", document.body.scrollHeight);
            var checkTipFunction = function (n) {
                //$("#test").html($(this).val() + "//" + n);
                if ($(this).val() != "") {
                    $(this).parent().removeClass("showPlaceholder");
                } else {
                    $(this).parent().addClass("showPlaceholder");
                }
            };
            if ($("#idInput").val() == "") {
                $("#idInput").parent().addClass("showPlaceholder");
            }
            if ($("#pwdInput").val() == "") {
                $("#pwdInput").parent().addClass("showPlaceholder");
            }
            $("#idInput").on('input', checkTipFunction);
            $("#pwdInput").on('input', checkTipFunction);
            $("#idInput").on('propertychange', checkTipFunction);
            $("#pwdInput").on('propertychange', checkTipFunction);
            $(".placeholder").click(function () {
                $(this).parent().find("input").focus();
            });
        });
    }
</script>
<body class="bg">
<div class="main" id="main">

</div>
</body>
</html>
