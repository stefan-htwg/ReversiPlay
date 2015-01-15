function basicDialog($dialog,callback){
	var callback = callback || function(){};
	$("#overlay").show();
	$dialog.show();
	$dialog.addClass("active").find("button").unbind().mouseup(function()
	{
		$(this).unbind();
		$('#overlay').hide();
		$dialog.removeClass("active");
		callback($(this).data("action"));
	});		
}


function l(s){
	console.log(s);
}

function errorMessage(msg){
	l(msg);
}

function AjaxService() {
	return {get : function(url, data, success, error) {
		$.getJSON(url, data).done(success).fail(error);
	},post : function(url, data, success, error) {
		$.ajax( {
			url : url,
			type : 'POST',
			contentType : 'application/json',
			data : JSON.stringify(data),
			// processData: false, // this is optional
			dataType : 'json'
		}).done(success).fail(error);
	}};
}

function menuHandler(act){
	if(act=="restart"){
		AjaxService().post(getUrl("game/restart"),"",function(data){ 
			AjaxService().get(getUrl("game/load"),"",function(data){ 
				drawBoad(data);			
			},errorMessage);	
		},errorMessage);
	}
}

var server = "http://localhost:9000/" 
	
$(function(){
	/**
	 * Config
	 */
	
	$("#overlay").show();
	$("#dialogStart").addClass("active").find("button").unbind().mouseup(function()
			{
				$(this).unbind();
				$('#overlay').hide();
				$("#dialogStart").removeClass("active");
				menuHandler($(this).data("action"));
			});	
	
	AjaxService().get(getUrl("game/load"),"",function(data){ 
		drawBoad(data);			
	},errorMessage);
	
	
	$("#optionclick").click(function(){
		basicDialog($("#dialogBreak"),menuHandler);
	});
	
	//load game options
	
	$("#iconHome").addClass("w");
	$("#iconGuest").addClass("b");
	
	$("#nextmove").attr("class","starter blue");
	$("#nextmove").attr("class","starter red");	
});


function getUrl(path){
	return server+path;
}

function clearBoard(){
	for(var i=1;i<=8;i++){
		for(var k=1;k<=8;k++){
			$("#sq"+k+"x"+i).attr("class","starter");			
		}
	}
}

function drawBoad(resp){
	var list= $.parseJSON(resp.board);
	
	l(resp.status);
	if(resp.status=="GameOver"){
		$("#finHome").addClass("w");
		$("#finGuest").addClass("b");
		basicDialog($("#dialogOver"),menuHandler);
	}
	
	if(resp.next==1){
		$("#nextmove").attr("class","starter blue");
	}else
		$("#nextmove").attr("class","starter red");
	
	$("#blacksc").html(resp.sp1);
	$("#whitesc").html(resp.sp2);
	
	clearBoard();
	jQuery.each(list, function(i,o){
		if(o.v==1)
			$("#sq"+o.c+"x"+o.r).addClass("blue");
		else if(o.v==2)
			$("#sq"+o.c+"x"+o.r).addClass("red");						
	});
}



function clickat(x,y){
 l("x/y: "+x+"/"+y);
 AjaxService().post(getUrl("game/move"), { r: y, c: x }, function(resp){
	  drawBoad(resp)
 }, errorMessage);
}