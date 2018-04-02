$(document).ready(function () {

    $("#create-team-form").submit(function (event) {
        event.preventDefault();
        createTeam();
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

function createTeam() {

    var search = {}
    search["id"] = 0;//replaced by Spring JPA on create
    search["teamName"] = $("#teamName").val();
    search["imageURL"] = $("#imageURL").val();

    $("#btn-create-team").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/createTeam",
        data: JSON.stringify(search),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
        	alert("Team Created Successfully");
        	window.location.reload();
        },
        error: function (e) {

        	alert("Team Creation Failed");
        	window.location.reload();

        }
    });

}