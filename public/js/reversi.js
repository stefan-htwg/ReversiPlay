$(function(){
	console.log("ready");
	
	var url= "http://localhost:9000/game/load";
	
	
	$.ajax(url).success(
		function(data){ 
//			var v =  data; 
			var v =  $.parseJSON(data); // nur wenn als string Ÿbermittelt
			//v.length
			l(v.length);
		}
	);/**/
	
});


function l(s){
	console.log(s);
}

function clickat(x,y){
 l("x/y: "+x+"/"+y);
 $("#sq"+x+"x"+y).addClass("blue");
}