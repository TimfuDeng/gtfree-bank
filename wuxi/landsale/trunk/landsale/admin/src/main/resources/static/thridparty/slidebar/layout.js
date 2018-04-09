$(function() {

	var $wndw = $(window),
		$html = $('html'),
		$body = $('body'),
		$both = $('body, html');


	String.prototype.capitalize = function() {
		 return this.charAt(0).toUpperCase() + this.slice(1);
	}



	//	Auto submenu
	var submenu = '';
	$('.submenutext')
		.each(
			function( i )
			{
				var $h = $(this).parent(),
					id = $h.attr( 'id' ) || 'h' + i;

				$h.attr( 'id', id );

				submenu += '<li><a href="#' + id + '">' + $(this).text().capitalize() + '</a></li>';
			}
		);

	if ( submenu.length )
	{
		var $submenu 	= $('<div class="submenu"><div><ul>' + submenu + '</ul></div></div>')
				.insertAfter( 'h1' );
		
		var $subfixed	= $submenu
				.clone()
				.addClass( 'fixed Fixed' )
				.insertAfter( $submenu );

		var fixed = false,
			start = $submenu.offset().top;

		$submenu
			.add( $subfixed )
			.find( 'a' )
			.on( 'click',
				function( e )
				{
					e.preventDefault();
					$both.animate({
						scrollTop: $($(this).attr( 'href' )).offset().top - 120
					});
				}
			);

		$wndw
			.on( 'scroll.submenu',
				function( e )
				{
					var offset = $wndw.scrollTop();
					if ( fixed )
					{
						if ( offset < start )
						{
							$body.removeClass( 'fixedsubmenu' );
							fixed = false;
						}
					}
					else
					{
						if ( offset >= start )
						{
							$body.addClass( 'fixedsubmenu' );
							fixed = true;
						}
					}
				}
			)
			.trigger( 'scroll.submenu' );
	}
	


	//	The menu
	$('#menu')
		.mmenu({
			extensions		: [ 'widescreen', 'theme-white', 'effect-menu-slide', 'pagedim-black' ],
			
			/*counters		: true,*/
			dividers		: {
				fixed 	: true
			},
			navbar 			: false,
			navbars			: [
				{
				position: 'top',
				content : ['searchfield']
			}, /*{
				position: 'top'
			},*/ {
				position: 'bottom',
				content : ['<div id="pageNav" class="pageNav"></div>']
			}],
			searchfield : {
				placeholder: '请键入地块编号按回车查询',
				search	: false
			}
		});


	
	//	Show more examples
	$('a[href="#more-examples"]')
		.on( 'click',
			function( e )
			{
				e.preventDefault();
				$body.addClass( 'more-examples' );
				$both.animate({
					scrollTop: $($(this).attr('href')).offset().top - 55
				});	
			}
		);



	//	Scroll to more info
	$('a[href="#more-info"]')
		.on( 'click',
			function( e )
			{
				e.preventDefault();
				$both.animate({
					scrollTop: $($(this).attr('href')).offset().top + 55
				});	
			}
		);

	$('a[href="#features"]')
		.on( 'click',
			function( e )
			{
				e.preventDefault();
				$both.animate({
					scrollTop: 750
				});	
			}
		);



	//	rotate ipad
	$('a.rotate')
		.on( 'click',
			function( e )
			{
				e.preventDefault();
				$(this).parent().toggleClass( 'portrait' );
			}
		);



	//	Compose email link, please stop sending me spam...
	setTimeout(function() {
		var b = 'frebsite' + '.' + 'nl',
			o = 'info',
			t = 'mail' + 'to';

		$('#emaillink').attr( 'href', t + ':' + o + '@' + b );
	}, 1000);



	//	Parallax home
	var $home = $('#home-intro'),
		$next = $('#features');

	if ( $home.length )
	{
		$wndw
			.on( 'scroll.parallax',
				function()
				{
					var pad = Math.ceil( $wndw.scrollTop() / 3 );
					if ( pad <= 300 )
					{
						$home.css( 'padding-bottom', 700 - pad );
						$next.css( 'margin-top', 0 - pad );
					}
				}
			)
			.trigger( 'scroll.parallax' );
	}



	//	Collapse tablerows
	$('.table-collapsed')
		.find( '.sub-start' )
		.each(
			function()
			{
				var $parent = $(this).prev().find( 'td' ).eq( 1 ).addClass( 'toggle' ),
					$args = $parent.find( 'span' ),
					$subs = $(this);
	
				var searching = true;
				$(this).nextAll().each(
					function()
					{
						if ( searching )
						{
							$subs = $subs.add( this );
							if ( !$(this).is( '.sub' ) )
							{
								searching = false;
							}
						}
					}
				);
				$subs.hide();
				$parent.click(
					function()
					{
						$args.toggle();
						$subs.toggle();
					}
				);
			}
		);
	


	//	Open menu in examples
	var $phones = $('.phone');
	if ( $phones.length )
	{
		var offsets = {};
		
		$phones
			.each(
				function()
				{
					var offset = $(this).offset().top - 150;
					if ( offset < 0 )
					{
						offset = 0;
					}

					if ( !offsets[ offset ] )
					{
						offsets[ offset ] = $();
					}
					offsets[ offset ] = offsets[ offset ].add( this );
				}
			);

		$wndw
			.on( 'scroll.phones',
				function()
				{
					var offset = $wndw.scrollTop();
					for ( var o in offsets )
					{
						if ( offset > o )
						{
							offsets[ o ]
								.each(
									function( i )
									{
										var iframe = $(this).find( 'iframe' )[ 0 ].contentWindow;
										var interv = setInterval(
											function()
											{
												if ( iframe.$ )
												{	
													var API = iframe.$('#menu').data( 'mmenu' );
													if ( API )
													{
														if ( API.open )
														{
															API.open();
														}
														clearInterval( interv );
													}
												}
											}, 250 + ( i * 250 )
										);
									}
								);
							
							delete offsets[ o ];
						}
					}

					for ( var o in offsets )
					{
						return;
					}
					$(this).off( 'scroll.phones' );
				}
			);

		setTimeout(
			function()
			{
				$wndw.trigger( 'scroll.phones' );
			}, 2500
		);
	}

	setServerTime(baseUrl + "/getServerTime.f", "#serverTimeDiv");

	initLeftResources(1);

	//initRightResources();
	getResourceItem();

	$('.mm-search input').keyup(function(event){
		var $input = $(this);
		if(event.keyCode == 13)
			initLeftResources(1);
	});
});

function showContent(){
	$('.slideBox').hide().empty();
	$('.slideBox').html($('#hideContent').html()).show();
	jQuery(".slideBox").slide({
		mainCell: ".bd ul",
		trigger: "click",
		effect: "leftLoop",
		pnLoop: true,
		mouseOverStop: false,
		autoPlay: true,
		delayTime: 2000,
		interTime: 3000,
		triggerTime: 3000
	});

	$(".time").each(function () {
		var timevalue = $(this).attr("value");
		if (timevalue != null && timevalue != '') {
			var idvalue = $(this).attr("sign");
			$(this).html(setTimeString(timevalue));
			var funObj = setInterval(refreshTime, 1000, this);
			if (idvalue) {
				_functionMap[idvalue] = funObj;
			}
		}
	});
}

var globalPageNum = 1;
function initLeftResources(pageNum){
	globalPageNum = pageNum;
	$.ajax({
		url: baseUrl + '/trans/view-resource/view_left',
		type: 'post',
		data: {
			title: $('.mm-search input').val(),
			index: pageNum
		},
		success: function(responseHtml){
			$('ul.listview-icons.mm-listview li').html($(responseHtml).find('table').parent().html());
			$('#pageNav').html($(responseHtml).find('table').parent().next().children().html().replace(/SubmitPageForm\(\'frmSearch\',/g,'initLeftResources('));
		}
	});
}

function initRightResources(){
	$.ajax({
		url: baseUrl + '/trans/view-resource/view_right',
		type: 'post',
		data: {

		},
		success: function(responseHtml){
			var $ul = $('#hideContent div.bd ul');
			$('#hideContent div.hd ul').html($(responseHtml).find('index').html());
			$ul.html($(responseHtml).find('content').html());

			showContent();
			//浏览器窗口大小发生变化时，重新展现网页内容
			$(window).resize(function(){
				showContent();
			});
		}
	});
}

function updateDisplayStatus(resourceId,displayStatus){
	$.ajax({
		url: baseUrl + '/trans/index/status',
		type: 'post',
		data: {
			resourceId: resourceId,
			displayStatus: displayStatus
		},
		success: function(){
			initLeftResources(globalPageNum);

			getResourceItem();
		}
	});
}

var dikuai;
function getResourceItem() {
	$.get(baseUrl + '/trans/resource', function (data) {
		dikuai = data;
		show();

		showContent();
		//浏览器窗口大小发生变化时，重新展现网页内容
		$(window).resize(function(){
			showContent();
		});
	});
}

function show() {
	$('#hideContent div.bd ul').empty();
	var liHtml = "";
	$(dikuai.dikuai).each(function(index,el){
		setAttr(el);

		liHtml += '<li>'+(index + 1)+'</li>';
	});

	$('#hideContent div.hd ul').empty().append(liHtml);
}

function setAttr(obj) {
	formatResourceInfo(obj);
	var html = template('test', obj);

	var $insertHtml = $(html);
	switch (obj.resource.resourceStatus) {
		case 1://正在竞价
			$insertHtml.find('div[sign="headTitle"]').attr("class", "head");
			$insertHtml.find('.currentInfo').show();
			break;
		case 10://挂牌
			$insertHtml.find('div[sign="headTitle"]').attr("class", "head");
			$insertHtml.find('.currentInfo').show();
			break;
		case 20://公告
			$insertHtml.find('div[sign="headTitle"]').attr("class", "head");
			$insertHtml.find('.currentInfo').show();
			break;
		case 30://已经完成
			$insertHtml.find('div[sign="headTitle"]').attr("class", "head tradedhead");
			$insertHtml.find(".orderEnd").show();
			break;
		case 31://流标
			$insertHtml.find('div[sign="headTitle"]').attr("class", "head");
			$insertHtml.find(".noOrder").show();
			break;
	}

	var $ul = $('#hideContent div.bd ul');
	$ul.append($insertHtml);
}

function formatResourceInfo(value){
	if(value.resource.maxOffer==0){
		value.resource.maxOffer='无';
	}
	if(value.resource.gpBeginTime!=null){

		var dateObj= new XDate(value.resource.gpBeginTime);

		value.resource.gpBeginTime=dateObj.toString("yyyy年MM月dd日 HH:mm:ss");

	}

	if(value.resource.gpEndTime!=null){
		var dateObj= new XDate(value.resource.gpEndTime);
		value.resource.gpEndTime=dateObj.toString("yyyy年MM月dd日 HH:mm:ss");
	}
	for(var i =0;i<value.offerList.length;i++){
		var dateObj= new XDate(value.offerList[i].offerTime);
		value.offerList[i].offerTime=dateObj.toString("MM月dd日 HH:mm:ss");
	}
}

var _dayLength=24*60*60*1000;
var _hourLength=60*60*1000;
var _minLength=60*1000;
var _functionMap=new Object();

function setTimeString(timevalue){
	var days=(timevalue-_serverTime)/_dayLength;
	var hours=((timevalue-_serverTime)% _dayLength)/_hourLength;
	var min=(((timevalue-_serverTime)% _dayLength) % _hourLength)/_minLength;
	var sec=(((timevalue-_serverTime)% _dayLength) % _hourLength)%_minLength /1000;
	if (days>1)
		return  buildTimeStr(days) + "<em>天</em>" +buildTimeStr(hours) + "<em>时</em>" +  buildTimeStr(min) + "<em>分</em>" +  buildTimeStr(sec) + "<em>秒</em>";
	else if(hours>1)
		return buildTimeStr(hours) + "<em>时</em>" +  buildTimeStr(Math.floor(min)) + "<em>分</em>" +  buildTimeStr(Math.floor(sec)) + "<em>秒</em>";
	else if(min>1)
		return buildTimeStr(min) + "<em>分</em>" +  buildTimeStr(Math.floor(sec)) + "<em>秒</em>";
	else if(sec>0)
		return buildTimeStr(sec) + "<em>秒</em>";
}

function buildTimeStr(value){
	value=value*1.0;
	if (value>10){
		return "<var>" + Math.floor(value) + "</var>";
	}else{
		return "<var>0" + Math.floor(value) + "</var>";
	}
}

function refreshTime(obj){
	var timevalue=$(obj).attr("value");
	if ((timevalue-_serverTime)<0) {
		try{
			var idvalue=$(obj).attr("sign");
			if(idvalue && _functionMap[idvalue])
				clearInterval(_functionMap[idvalue]);
		}catch(ex){

		}
	}else{
		$(obj).html(setTimeString(timevalue));
	}
}