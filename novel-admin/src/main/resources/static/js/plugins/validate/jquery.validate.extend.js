/*this is basic form validation using for validation person's basic information author:Clara Guo data:2017/07/20*/
$(document).ready(function(){
	$.validator.setDefaults({       
		  submitHandler: function(form) {    
		 		form.submit();    
		}       
	});  
	//Penggabungan reguler kartu ID verifikasi nomor ponsel: (^\d{15}$)|(^\d{17}([0-9]|X)$)
	jQuery.validator.addMethod("isPhone",function(value,element){
		var length = value.length;
		var phone=/^(6|14|15|17|18)\d{12}$/;
		return this.optional(element)||(length == 13 && phone.test(value));
	},"Harap isi 13 digit nomor ponsel yang benar");
	//Verifikasi nomor telepon
	jQuery.validator.addMethod("isTel",function(value,element){
		var tel = /^(0\d{2,3}-)?\d{7,13}$/g;//Kode area 3, 4 digit, angka 7, 8 digit
		return this.optional(element) || (tel.test(value));
	},"Harap isi nomor telepon rumah yang benar");
	// //Verifikasi nama
	jQuery.validator.addMethod("isName",function(value,element){
		var name=/^(?:[\u0000-\u007F]+|[\u0370-\u03FF]+)$/;
		return this.optional(element) || (name.test(value));
	},"Nama hanya boleh menggunakan karakter Cina, panjangnya 2-4 digit");
	//Verifikasi nama pengguna
	jQuery.validator.addMethod("isUserName",function(value,element){
		var userName=/^[a-zA-Z0-9]{2,13}$/;
		return this.optional(element) || (userName).test(value);
	},'Harap masukkan angka atau huruf, tidak termasuk karakter khusus');
	
	//Periksa ID
	jQuery.validator.addMethod("isIdentity",function(value,element){
		var id= /^(\d{16}$|^\d{18}$|^\d{17}(\d|X))$/;
		return this.optional(element) || (id.test(value));
	},"Harap masukkan 16 atau 18 digit nomor ID yang benar");
	//Periksa tanggal lahir
	jQuery.validator.addMethod("isBirth",function(value,element){
		var birth = /^(19|20)\d{2}-(1[0-2]|0?[1-9])-(0?[1-9]|[1-2][0-9]|3[0-1])$/;
		return this.optional(element) || (birth).test(value);
	},"Contoh format tanggal lahir 2000-01-01");
	//Verifikasi bahwa kata sandi lama dan baru sama
	jQuery.validator.addMethod("isdiff",function(){
		var p1=$("#pwdOld").val();
		var p2=$("#pwdNew").val();
		if(p1==p2){
			return false;
		}else{
			 return true;
		}
		});
	//Verifikasi bahwa kata sandi baru dan konfirmasi kata sandi adalah sama
	jQuery.validator.addMethod("issame",function(){
		var p3=$("#confirm_password").val();
		var p4=$("#pwdNew").val();
		if(p3==p4){
			return true;
		}else{
			 return false;
		}
		});
	//Verifikasi formulir informasi dasar
	$("#basicInfoForm").validate({
		errorElement:'span',
		errorClass:'help-block error-mes',
		rules:{
			name:{
				required:true,
				isName:true
			},
			sex:"required",
			birth:"required",
            mobile:{
				required:true,
				isPhone:true
			},
			email:{
				required:true,
				email:true
			}
		},
		messages:{
			name:{
				required:"Harap masukkan nama",
				isName:"Nama hanya boleh karakter Cina"
			},
			sex:{
				required:"Harap masukkan jenis kelamin"
			},
			birth:{
				required:"Harap masukkan tahun dan bulan lahir"
			},
            mobile:{
				required:"Harap masukkan nomor telepon",
				isPhone:"Harap isi 13 digit nomor ponsel yang benar"
			},
			email:{
				required:"silahkan masukan email anda",
				email:"Harap isi format email yang benar"
			}
		},
	
		errorPlacement:function(error,element){
			element.next().remove();
			element.closest('.gg-formGroup').append(error);
		},
		
		highlight:function(element){
			$(element).closest('.gg-formGroup').addClass('has-error has-feedback');
		},
		success:function(label){
			var el = label.closest('.gg-formGroup').find("input");
			el.next().remove();
			label.closest('.gg-formGroup').removeClass('has-error').addClass("has-feedback has-success");
			label.remove();
		},
		submitHandler:function(form){
			alert("保存成功!");
		}
	});
	
	//Verifikasi dan ubah formulir kata sandi
	$("#modifyPwd").validate({
		onfocusout: function(element) { $(element).valid()},
		 debug:false, //Menunjukkan apakah akan mengirimkan formulir langsung setelah verifikasi lulus
		 onkeyup:false, //Menunjukkan pemantauan dan verifikasi saat tombol dilepaskan
		rules:{
			pwdOld:{
				required:true,
				minlength:5
			},
            pwdNew:{
			   required:true,
			   minlength:5,
			   isdiff:true,
			   //issame:true,
		   },
			confirm_password:{
			  required:true,
			  minlength:5,
			  issame:true,
			}
		  
		   },
		messages:{
			 	pwdOld : {
					 required:'Wajib',
					 minlength:$.validator.format('Panjang kata sandi harus lebih dari 5')
				},
            	pwdNew:{
				   required:'Wajib',
				   minlength:$.validator.format('Panjang kata sandi harus lebih dari 5'),
				   isdiff:'Kata sandi asli dan kata sandi baru tidak dapat diulang',
				  
			   },
				confirm_password:{
				   required:'Wajib',
				   minlength:$.validator.format('Panjang kata sandi harus lebih dari 5'),
				   issame:'Kata sandi baru harus sama dengan kata sandi baru yang telah dikonfirmasi',
				}
		
		},
		errorElement:"mes",
		errorClass:"gg-star",
		errorPlacement: function(error, element) 
		{ 
			element.closest('.gg-formGroup').append(error);

		}
	});
});