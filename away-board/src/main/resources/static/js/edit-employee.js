$(document).ready( function () {
	 
	  var table = $('#employeesTable').DataTable( {
	        "ajax":{"url":"/getAllEmployees","dataSrc":""},
	        "columns": [
	        	{ "data": "name" },
				  { "data": "currentStatus" },
				  { "data": "imageURL" },
				  { "data": "teams" },
				  {
				      "data": null,
				      "defaultContent": '<button type="button" class="btn btn-primary edit">Edit</button>&nbsp;<button type="button" class="btn btn-danger delete">Delete</button>',
				      "targets": -1
				    }
	        ],
	        "createdRow": function ( row, data, index ) {
	                $('td', row).eq(1).addClass(data["currentStatus"]);
	        }
	    });
	  
	  table.on('click', 'button.edit', function () {
	        var closestRow = $(this).closest('tr');
	        var data = table.row(closestRow).data();
	        var name = data["name"];
	        var currentStatus = data["currentStatus"];
	        var imageURL = data["imageURL"];
	        var teams = data["teams"];
	        
	        $('#editEmployeeModal').modal();
	        
	        var employeeTeamNames = new Array();
	        var employeeTeamIds = new Array();
	        var teamIds = new Array();
	        
	        var temp = teams.toString().split(",");
	        temp.forEach(function(team) {
	        	employeeTeamNames.push(team);
			});
	        
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
	    				teamIds.push(item.id);
	    				employeeTeamNames.forEach(function(employeeTeamName) {
	    					if(item.teamName == employeeTeamName){
	    			        	employeeTeamIds.push(item.id);
	    					}
	    				});
	    			});        	
	    	        $('#teamNames').val(employeeTeamIds);
	    		},
	    		error: function (e) {
	    			alert("Error");
	    		}
	    	});
	        
	        $('#name').val(name);
	        $('#status').val(currentStatus);
	        $('#imageURL').val(imageURL);
	        $("#imageURLThumbnail").attr("src",imageURL);
	        
	    });
	  
	  table.on('click', 'button.delete', function () {
	        var closestRow = $(this).closest('tr');
	        var data = table.row(closestRow).data();
	        var employee = {}
	        employee["name"] = data["name"];

	        $.ajax({
	            type: "POST",
	            contentType: "application/json",
	            url: "/deleteEmployee",
	            data: JSON.stringify(employee),
	            cache: false,
	            success: function (data) {
	            	alert("Employee Deleted Successfully");
	            	table.ajax.reload();
	            },
	            error: function (e) {
	            	alert("Employee Deletion Failed");

	            }
	        });
	    });
	  
	  $("#imageURL").change(function(event){
	    	$("#imageURLThumbnail").attr("src",$("#imageURL").val());
	    });
	  
	  $("#update-employee-form").submit(function (event) {
	        event.preventDefault();
	        
	        var employee = {}
	        employee["name"] = $("#name").val();
	        employee["imageURL"] = $("#imageURL").val();
	        employee["teams"] = $("#teamNames").val();
	        employee["currentStatus"] = $("#status").val();

	        $.ajax({
	            type: "POST",
	            contentType: "application/json",
	            url: "/updateEmployee",
	            data: JSON.stringify(employee),
	            cache: false,
	            success: function (data) {
	            	alert("Employee Updated Successfully");
	            	 $('#editEmployeeModal').modal('toggle');
	            	 table.ajax.reload();
	            },
	            error: function (e) {
	            	alert("Employee Update Failed");
	            	 $('#editEmployeeModal').modal('toggle');
	            }
	        });
	    });
});