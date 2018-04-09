function checkInputFileter(){

    $("input").each(function(){
        $("input:visible[type='text'][name='"+this.name+"']").val(stripscript($("input:visible[type='text'][name='"+this.name+"']").val()));
    })

}

//input特殊字符过滤
function stripscript(s) {
    var pattern=new RegExp("java|javascript|script|alert|\'|\"");
    if(undefined!=s&&s.length>0)
        for(var i=0;i<s.length;i++)
        {
            s=s.replace(pattern,"").replace("/","").replace("\\","").replace("<","").replace(">","").replace(/(^\s*)|(\s*$)/g, "");
        }
    return s;
}