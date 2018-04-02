$(document).ready(function () {
	
	$.ajax({
		type: "GET",
		contentType: "application/json",
		url: "/getAllTeams",
		dataType: 'json',
		cache: false,
		success: function (data) {
			var options = "";
			jQuery.each(data, function(index, item) {
				options += "<option value="+item.id+">"+item.teamName+"</option>";
				$("#teamNames").html(options);
			});        	
		},
		error: function (e) {
			alert("Error");
		}
	});

    $("#create-employee-form").submit(function (event) {
        event.preventDefault();
        createEmployee();
    });
    
    
    $("#imageURL").change(function(event){
    	$("#imageURLThumbnail").attr("src",$("#imageURL").val());
    });
    
    $('#genRandImg').click(function(){
		var rand = Math.floor(Math.random() * (1 - 99) + 99);
		var image = "https://spaceholder.cc/100x100?a="+rand;
		$("#imageURL").val(image);
		$("#imageURL").trigger("change");
    });

});

function createEmployee() {

    var search = {}
    search["name"] = $("#empName").val();
    search["imageURL"] = $("#imageURL").val();
    search["teams"] = $("#teamNames").val();
    search["currentStatus"] = "away"; //defaulting the status as Away

    $("#btn-create-employee").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/createEmployee",
        data: JSON.stringify(search),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
        	alert("Employee Created Successfully");
        	window.location.reload();
        },
        error: function (e) {

        	alert("Employee Creation Failed");
        	window.location.reload();

        }
    });

}