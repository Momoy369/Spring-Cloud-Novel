// 以下为官方示例
$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		console.log('Mengirim perubahan');
		update();
	}
});
function update() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/common/generator/update",
		data : $('#signupForm').serialize(),// formID Anda
		async : false,
		error : function(request) {
			parent.layer.alert("Waktu koneksi jaringan habis");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg(data.msg);

			} else {
				parent.layer.msg(data.msg);
			}

		}
	});

}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			author : {
				required : true
			},
			email : {
				required : true,
			},
			package : {
				required : true,
			},
			
		},
		messages : {

			author : {
				required : icon + "Harap masukkan penulis"
			},
			email : {
				required : icon + "Silakan masukkan email",
			},
			package : {
				required : icon + "Harap masukkan nama paket",
			},
		}
	})
}
