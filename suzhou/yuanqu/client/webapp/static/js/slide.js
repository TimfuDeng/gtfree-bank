var timer = null;
var offset = 5000;
var index = -1;

//大图交替轮换
function slideImage(i){
    var id = 'image_'+ target[i];
    $('#'+ id)
        .animate({opacity: 1}, 100, function(){
            $(this).find('.imageItem').animate({alpha: 'show'}, 'slow');
        }).show()
        .siblings(':visible')
        .find('.imageItem').animate({alpha: 'hide'},'fast',function(){
            $(this).parent().animate({opacity: 0}, 100).hide();
        });
}
//bind thumb a
function hookThumb(){
    $('#thumbs li a')
        .bind('click', function(){
            if (timer) {
                clearTimeout(timer);
            }
            var id = this.id;
            index = getIndex(id.substr(6));
            rechange(index);
            slideImage(index);
            //timer = window.setTimeout(auto, offset);
            this.blur();
            return false;
        });
}
//bind next/prev img
function hookBtn(){
    $('#thumbs ul li').filter('#play_prev,#play_next')
        .bind('click', function(){
            if (timer){
                clearTimeout(timer);
            }
            var id = this.id;


            if (id == 'play_prev') {
                index--;
                if (index < 0) index = target.length-1;
            }else{
                index++;
                if (index > target.length-1) index = 0;
            }
            rechange(index);
            slideImage(index);
           // timer = window.setTimeout(auto, offset);
        });
}
//get index
function getIndex(v){
    for(var i=0; i < target.length; i++){
        if (target[i] == v) return i;
    }
}
function rechange(loop){
    var id = 'thumb_'+ target[loop];
    $('#thumbs li a.current').removeClass('current');
    $('#'+ id).addClass('current');
}
function auto(){
    index++;
    if (index > target.length-1){
        index = 0;
    }
    rechange(index);
    slideImage(index);
    timer = window.setTimeout(auto, offset);
}
$(function(){
    //change opacity
    $('div .imageItem').css({opacity: 0.85});
   // auto();
    hookThumb();
    hookBtn();

});