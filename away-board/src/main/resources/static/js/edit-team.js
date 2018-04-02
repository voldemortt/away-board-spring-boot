$(document).ready( function () {
	 
	  var table = $('#teamsTable').DataTable( {
	        "ajax":{"url":"/getAllTeams","dataSrc":""},
	        "columns": [
	        	{ "data": "id" },
	        	{ "data": "teamName" },
				  { "data": "imageURL" },
				  {
				      "data": null,
				      "defaultContent": '<button type="button" class="btn btn-primary edit">Edit</button>&nbsp;<button type="button" class="btn btn-danger delete">Delete</button>',
				      "targets": -1
				    }
	        ]
	    });
	  
	  table.on('click', 'button.edit', function () {
	        var closestRow = $(this).closest('tr');
	        var data = table.row(closestRow).data();
	        var id = data["id"];
	        var teamName = data["teamName"];
	        var imageURL = data["imageURL"];
	        
	        $('#editTeamModal').modal();
	        
	        $('#id').val(id);
	        $('#teamName').val(teamName);
	        $('#imageURL').val(imageURL);
	        $("#imageURLThumbnail").attr("src",imageURL);
	        
	    });
	  
	  table.on('click', 'button.delete', function () {
	        var closestRow = $(this).closest('tr');
	        var data = table.row(closestRow).data();
	        var team = {}
	        team["id"] = data["id"];
	        team["teamName"] = data["teamName"];

	        $.ajax({
	            type: "POST",
	            contentType: "application/json",
	            url: "/deleteTeam",
	            data: JSON.stringify(team),
	            cache: false,
	            success: function (data) {
	            	alert("Team Deleted Successfully");
	            	table.ajax.reload();
	            },
	            error: function (e) {
	            	alert("Team Deletion Failed");

	            }
	        });
	    });
	  
	  $("#imageURL").change(function(event){
	    	$("#imageURLThumbnail").attr("src",$("#imageURL").val());
	    });
	  
	  $("#update-team-form").submit(function (event) {
	        event.preventDefault();
	        
	        var team = {}
	        team["id"] = $("#id").val();
	        team["teamName"] = $("#teamName").val();
	        team["imageURL"] = $("#imageURL").val();

	        $.ajax({
	            type: "POST",
	            contentType: "application/json",
	            url: "/updateTeam",
	            data: JSON.stringify(team),
	            cache: false,
	            success: function (data) {
	            	alert("Team Updated Successfully");
	            	 $('#editTeamModal').modal('toggle');
	            	 table.ajax.reload();
	            },
	            error: function (e) {
	            	alert("Team Update Failed");
	            	 $('#editTeamModal').modal('toggle');
	            }
	        });
	    });
});