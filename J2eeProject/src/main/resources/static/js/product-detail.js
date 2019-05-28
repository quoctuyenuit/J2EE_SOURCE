function increaseQuantity() {
	var quantity = $("#ajax-quantity").val();
	debugger
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "/detail/increase-quantity",
		data : {
			quantity : quantity
		},
		dataType : 'json',
		timeout : 100000,
		success : function(data) {
			debugger
			$("#ajax-quantity").val(data);
		},
		error : function(e) {
			debugger
			console.log("ERROR: ", e);
		}
	});
}

function descreaseQuantity() {
	var quantity = $("#ajax-quantity").val();
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "/detail/descrease-quantity",
		data : {
			quantity : quantity
		},
		dataType : 'json',
		timeout : 100000,
		success : function(data) {
			debugger
			$("#ajax-quantity").val(data);
		},
		error : function(e) {
			debugger
			console.log("ERROR: ", e);
		}
	});
}