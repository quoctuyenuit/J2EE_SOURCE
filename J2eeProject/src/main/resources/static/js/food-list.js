function addToCart(productId) {

	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "/home/products/add-product-to-cart",
		data : {
			productId : productId
		},
		dataType : 'json',
		timeout : 100000,
		success : function(data) {
			console.log("SUCCESS: ", data);
			$("#ajax-quantity-cart").html(data);
		},
		error : function(e) {
			console.log("ERROR: ", e);
		}
	});
}
