$(function() {

	var teamId = $('#teamId').val();
	// initial get
	$.get('/getEmployeesByTeam?teamId='+teamId, function(employees) {
		if (employees.length > 0) {
			employees.forEach(function(employee) {
				addTile(employee.name, employee.imageURL, employee.currentStatus); 
			});
		}
	});
	
	//publishing employee's status globally across all boards on updating the status from board.s
	 var socket = new SockJS('/awayboard-ws');
	 stompClient = Stomp.over(socket);
	 stompClient.connect({}, function (frame) {
         console.log('Connected: ' + frame);
	        stompClient.subscribe('/topic/awayboard/getEmployeesByTeam', function (res) {
	        	var employees = JSON.parse(res.body);
	        	if (employees.length > 0) {
	        		removeAllTiles();
	    			employees.forEach(function(employee) {
	    				addTile(employee.name, employee.imageURL, employee.currentStatus);
	    			});
	    		}
	        });
	    });   

	//init drag and drop
	$( ".column" ).sortable({
		connectWith: ".column",
		handle: "[data-draggable]",
		placeholder: "tile__placeholder",
		start: function (event, ui) {
			ui.item.css("transform", "")
			ui.item.addClass('-dragged');
		},
		stop: function (event, ui) {
			ui.item.removeClass('-dragged');
			postData(ui.item,'update');
		},
		update: function (event, ui) {
			ui.item.removeClass('-dragged');
			var rand = Math.random() * (-3 - 3) + 3;
			ui.item.css("transform", "rotate(" + rand + "deg)")
		}
	});


	//new button handler   
	$(document).on('click', '.js-new', function(e){
		e.preventDefault();
		var rand = Math.floor(Math.random() * (1 - 99) + 99);
		var name = sanitizeString($('.js-name').val()) || rand;
		var image = sanitizeString($('.js-image').val()) || "https://spaceholder.cc/100x100?a="+rand;
		postData($(this),'add');
		addTile(name, image, 'away'); //defaulting to away
		$('.js-name').val('');
		$('.js-image').val(''); 
	});


	//remove button handler
	$(document).on('click', '.js-remove', function(e){
		e.preventDefault();
		postData($(this).closest('[data-tile]'),'remove');
		removeTile($(this));
	});


	// add a new tile
	function addTile(name, image, loc ){
		var rand = Math.random() * (-3 - 3) + 3;
		var tile = 
			`<article class="tile" data-tile data-name="${name}" data-image="${image}" style="transform: rotate(${rand}deg)">
			<div class="tile__content" data-draggable>
			<img class="tile__image" src="${image}">
			</div>
			<header class="tile__header" data-draggable>${name}</header>
			<button class="tile__remove js-remove"> ╳ </button>
			</article>`;

		$(`[data-col="${loc}"]`).append(tile);
	}


	// delete an existing tile
	function removeTile(el){
		el.closest('[data-tile]').remove();
	}
	
	function removeAllTiles(){
		$('[data-tile]').remove();
	}


	// post current state to rest api
	function postData(el,mode){
		if(mode=='add'){
			var rand = Math.floor(Math.random() * (1 - 99) + 99);
			var name = sanitizeString($('.js-name').val()) || rand;
			var image = sanitizeString($('.js-image').val()) || "https://spaceholder.cc/100x100?a="+rand;	      
			var employee = {};
			var teams = new Array();
			teams.push(teamId);
			employee["name"] = name;
			employee["imageURL"] = image;
			employee["teams"] = teams;
			employee["currentStatus"] = "away"; //defaulting the status as Away

			$.ajax({
				type: "POST",
				contentType: "application/json",
				url: "/createEmployeeFromBoard",
				data: JSON.stringify(employee),
				dataType: 'json',
				cache: false,
				success: function (data) {
					console.log("Employee Created Successfully");
				},
				error: function (e) {
					console.log("Employee Creation Failed");
				}
			});
		}
		else if(mode=='update'){
			var employee = {};
			var teams = new Array();
			teams.push(teamId);
			employee["name"] = el.data('name');
			employee["imageURL"] = el.data('image');
			employee["teams"] = teams;
			employee["currentStatus"] = el.closest('[data-col]').data('col');

			$.ajax({
				type: "POST",
				contentType: "application/json",
				url: "/updateEmployeeFromBoard",
				data: JSON.stringify(employee),
				cache: false,
				success: function (data) {
					console.log(data);
				},
				error: function (e) {
					console.log("Update failed");
				}
			});
		}
		else if(mode=='remove'){
			var teams = new Array();
			teams.push(teamId);
			var employee = {};
			employee["name"] = el.data('name');
			employee["imageURL"] = el.data('image');
			employee["currentStatus"] = el.closest('[data-col]').data('col');
			employee["teams"] = teams;
			$.ajax({
				type: "POST",
				contentType: "application/json",
				url: "/deleteEmployeeFromBoard",
				data: JSON.stringify(employee),
				cache: false,
				success: function (data) {
					console.log(data);
				},
				error: function (e) {
					console.log("Update failed");
				}
			});
		}
		/*$.post('/updateEmployee', {employees: employees}).done(function( resp ) {
      console.log( resp );
    });*/
	}

	function sanitizeString(str){
		str = str.replace(/[^a-z0-9áéíóúñü\/:\&\=\?_-\s\.,]/gim,"");
		return str.trim();
	}

})
