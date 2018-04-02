$(document).ready(function () {

	loadTeams();

	$("#createTeamLink").click(function(){
		$(".container").load("/team");
	});
	
	$("#createEmployeeLink").click(function(){
		$(".container").load("/employee");
	});

	$("#viewTeams").click(function(){
		window.location.reload();
	});
	
	$("#editEmployeeLink").click(function(){
		$(".container").load("/editEmployee");
	});
	
	$("#editTeamLink").click(function(){
		$(".container").load("/editTeam");
	});

});

function loadTeams() {

	$.ajax({
		type: "GET",
		contentType: "application/json",
		url: "/getAllTeams",
		dataType: 'json',
		cache: false,
		success: function (data) {
			jQuery.each(data, function(index, item) {
				var content="<div class='col-sm-6 col-md-4'><div class='thumbnail'>" +
				"<img style='cursor:pointer' onclick='loadAwayBoard("+item.id+")' width=250 height=250 src='"+item.imageURL+"' alt='Team'>" +
				"<div style='cursor:pointer' onclick='loadAwayBoard("+item.id+")' class='caption'><h3>"+item.teamName+"</h3></div></div></div>";
				$('.teams').append(content);
			});        	
		},
		error: function (e) {
			alert("Error");
		}
	});

}

function loadAwayBoard(id){
	$(".container").load("/awayboard?id="+id);
}