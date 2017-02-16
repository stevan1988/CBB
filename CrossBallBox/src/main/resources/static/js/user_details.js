$(document).ready(function() {
	
	show_suplement_info($('input[name="suplements"]:checked').val());
	show_training_activity_info($('input[name="training_activity"]:checked').val());

	var e = document.getElementById('progressList');
       e.style.display = 'none';
       
     e = document.getElementById('userInfoForm');
       e.style.display = 'none';
       
       addRemoveElement();
      
    
});

function addRemoveElement(){
	var next = 1;
    $(".add-more").click(function(e){
        e.preventDefault();
        var addto = "#field" + next;
        var addRemove = "#field" + (next);
        next = next + 1;
        var newIn = '<input autocomplete="off" class="input form-control" id="field' + next + '" name="field' + next + '" type="text">';
        var newInput = $(newIn);
        var removeBtn = '<button id="remove' + (next - 1) + '" class="btn btn-danger remove-me" >-</button></div><div id="field">';
        var removeButton = $(removeBtn);
        $(addto).after(newInput);
        $(addRemove).after(removeButton);
        $("#field" + next).attr('data-source',$(addto).attr('data-source'));
        $("#count").val(next);  
        
            $('.remove-me').click(function(e){
                e.preventDefault();
                var fieldNum = this.id.charAt(this.id.length-1);
                var fieldID = "#field" + fieldNum;
                $(this).remove();
                $(fieldID).remove();
            });
    });
}

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
		},
		error : function(xhr, ajaxOptions, thrownError, data) {
			console.log(data);
			window.location.reload();
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

$('.toggleModal').on('click', function (e) {
	  $('.modal').modal('show');
	});

$('.close').on('click', function (e) {
	$('.modal').modal('hide');
});

function toggle_visibility(id) {
    var e = document.getElementById(id);
    if(e.style.display == 'block')
       e.style.display = 'none';
    else
       e.style.display = 'block';
 }

function show_suplement_info(flag){
	var e1 = document.getElementById('suplement_type_text');
	var e2 = document.getElementById('suplements_type');
	if(flag==='no'){
		e1.style.display = 'none';
	    e2.style.display = 'none';
	 }
	 else{
	    e1.style.display = 'block';
	    e2.style.display = 'block';
	 }
}

function show_training_activity_info(flag){
	 var e1 = document.getElementById('training_activity_type');
	 var e2 = document.getElementById('training_type_text');
	 if(flag==='no'){
	    e1.style.display = 'none';
	    e2.style.display = 'none';
	 }
	 else{
	    e1.style.display = 'block';
	    e2.style.display = 'block';
	 }
}

//changing text in drop down
//$(function(){
//    $("#drop-down-btn1 li a").click(function(){
//      $(".btn:first-child").text($(this).text());
//      $(".btn:first-child").val($(this).text());
//
//   });
//});

function restoreDDElement(id) {
	var idDD = "#" + id;
	$(idDD).show();
}

function removeDDElement(id) {
	var idDD = "#" + id;
	console.log('id: ' + idDD);
	$(idDD).hide();
}

function addProgram(name) {

    var row = document.getElementById("choosenTraining");
    var x = row.insertCell(0);
    x.innerHTML = name;
}
