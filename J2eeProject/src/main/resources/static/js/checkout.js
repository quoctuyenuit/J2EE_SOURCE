function checkout() {
	
	debugger
	var userId = $("#userId").val();
	var firstName = $("#firstName").val();
	var lastName = $("#lastName").val();
	var phoneNumber = $("#inputPhone").val();
	var address = $("#inputAddress").val();
	var wards = $("#inputWards").val();
	var district = $("#inputDistrict").val();
	var province = $("#inputProvince").val();
	var password = $("#password").val();
	var email = $("#email").val();
	var type_id = $("#typeId").val();
	var name = lastName + " " + firstName;
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "/checkout-action",
		data : {
			id: userId,
			firstName: firstName,
			lastName: lastName,
			phone: phoneNumber,
			address: address,
			wards: wards,
			name: name,
			district: district,
			province: province,
			password: password,
			email: email,
			type_id: type_id
		},
		dataType : 'json',
		timeout : 100000,
		success : function(data) {
			debugger
//			console.log("SUCCESS: ", data);
//			$("#ajax-quantity-cart").html(data);
		},
		error : function(e) {
			debugger
			console.log("ERROR: ", e);
		}
	});
}
