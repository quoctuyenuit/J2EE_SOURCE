function increaseQuantity(productId, responseKey, priceKey) {
	var quantity = $("#" + responseKey).val();
	var price = $("#" + responseKey).data("price");
	debugger
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "/my-cart/increase-quantity",
		data : {
			quantity : quantity,
			price : price,
			productId : productId
		},
		dataType : 'json',
		timeout : 100000,
		success : function(data) {
			debugger
			$("#" + responseKey).val(data.quantity);
			$("#" + priceKey).html(data.totalPrice);
			$("#subtotal").html(data.subtotal);
			$("#total-list").html(data.totalListPrice);
		},
		error : function(e) {
			debugger
			console.log("ERROR: ", e);
		}
	});
}

function descreaseQuantity(productId, responseKey, priceKey) {
	var quantity = $("#" + responseKey).val();
	var price = $("#" + responseKey).data("price");
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "/my-cart/descrease-quantity",
		data : {
			quantity : quantity,
			price : price,
			productId : productId
		},
		dataType : 'json',
		timeout : 100000,
		success : function(data) {
			debugger
			$("#" + responseKey).val(data.quantity);
			$("#" + priceKey).html(data.totalPrice);
			$("#subtotal").html(data.subtotal);
			$("#total-list").html(data.totalListPrice);
		},
		error : function(e) {
			debugger
			console.log("ERROR: ", e);
		}
	});
}

function deleteCart() {
	return confirm("Bạn chắc chắn muốn xoá món đồ này?")
}