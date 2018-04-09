function exeData(num, type) {
    $("#currentPage").val(num);
    SubmitPageForm('frmSearch', num);
}
function loadpage() {
    var currentPage = parseInt($("#currentPage").val());
    var pageSize = parseInt($("#pageSize").val());
    var totalPages = parseInt($("#totalPages").val());
    $("#totalPages").val(totalPages);

    $.jqPaginator('#pagination', {
        totalCounts: 0,
        totalPages: parseInt($("#totalPages").val()),
        visiblePages: parseInt($("#visiblePages").val()),
        currentPage: currentPage,
        first: '<li class="first"><a href="javascript:;">首页</a></li>',
        prev: '<li class="prev"><a href="javascript:;"><i class="arrow arrow2"></i>上一页</a></li>',
        next: '<li class="next"><a href="javascript:;">下一页<i class="arrow arrow3"></i></a></li>',
        last: '<li class="last"><a href="javascript:;">末页</a></li>',
        page: '<li class="page"><a href="javascript:;">{{page}}</a></li>',
        onPageChange: function (num, type) {
            if (type == "change") {
                exeData(num, type);
            }
        }
    });
}
/*
$(function () {
    $("#PageCount").val(1);
    loadpage();
});*/
