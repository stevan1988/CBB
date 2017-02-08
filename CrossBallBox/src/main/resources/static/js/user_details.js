$(document).ready(function() {
	$("#upload-file-input").on("change", uploadFile);
});

function uploadFile() {
	var token = $('#upload-file-form').find(".csrfToken").val();
	var header = $('#upload-file-form').find(".csrfHeader").val();
	$.ajax({
		url : "/user/upload_image",
		type : "POST",
		data : new FormData($("#upload-file-form")[0]),
		enctype : 'multipart/form-data',
		processData : false,
		contentType : false,
		cache : false,
		dataType : "json",
		beforeSend : function(xhr) {
			xhr.setRequestHeader(header, token);
		},
		success : function(data) {
			console.log(data);
			window.location.reload();
			// $('#user_image').reload(true);
			// d = new Date();
			// $("#user_image").attr('src', data+d.getTime());
		},
		error : function(xhr, ajaxOptions, thrownError, data) {
			console.log(data);
			// console.log(xhr.status);
			// console.log(xhr);
			// console.log(thrownError);
			window.location.reload();
			// $('#user_image').reload(true);
			// d = new Date();
			// $("#user_image").attr('src', data+d.getTime());
		}
	});
}

$('#uploadBtn').click(function() {
	uploadFile
});

// <!-- end of upload -->

function bigImg(x) {
	x.style.height = "250px";
	x.style.width = "230px";
}

function normalImg(x) {
	x.style.height = "250px";
	x.style.width = "230px";
}

function chooseFile() {
	$("#imgInput").click();
}

function popup(mylink, windowname) {
	if (!window.focus)
		return true;
	var href;
	if (typeof (mylink) == 'string')
		href = mylink;
	else
		href = mylink.href;
	window.open(href, windowname, 'width=400,height=200,scrollbars=yes');
	return false;
}

$('.toggleModal').on('click', function (e) {
	  $('.modal').modal('show');
	});

$('.close').on('click', function (e) {
	$('.modal').modal('hide');
});

