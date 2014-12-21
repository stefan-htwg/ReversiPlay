$(function(){
	console.log("ready");
	
	var url= "http://localhost:9000/game/load";
	
	$.ajax(url).success(
		function(data){ 
			console.log(data);
		}
	);
	
});