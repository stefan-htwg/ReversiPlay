$(function(){
	console.log("ready");
	
	var url= "http://localhost:9000/game/load";
	
	
	$.ajax(url).success(
		function(data){ 
//			var v =  data; 
			var list =  $.parseJSON(data); // nur wenn als string Ÿbermittelt
			//v.length
			l(list.length);

			drawBoad(list);
			
		}
	);/**/
	
	
	
	
});


function clearBoard(){
	for(var i=1;i<=8;i++){
		for(var k=1;k<=8;k++){
			$("#sq"+k+"x"+i).attr("class","starter");			
		}
	}
}

function drawBoad(list){
	clearBoard();
	jQuery.each(list, function(i,o){
		if(o.v==1)
			$("#sq"+o.c+"x"+o.r).addClass("blue");
		else if(o.v==2)
			$("#sq"+o.c+"x"+o.r).addClass("red");						
	});
}

function l(s){
	console.log(s);
}

function clickat(x,y){
 l("x/y: "+x+"/"+y);
 $("#sq"+x+"x"+y).addClass("blue");
 
 var data = { r: y, c: x };
	
 $.ajax({
	  type: "POST",
	  url: "http://localhost:9000/game/move",
	  data: data,
	  success: function(list){
		  drawBoad(list)
	  },
	  dataType: "json"
	}).fail(function(e) {
	    l(e);
	  });
}