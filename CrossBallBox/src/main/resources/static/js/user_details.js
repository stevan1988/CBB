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
		 if(e1 !== null){
			 e1.style.display = 'block';
		 }
		 if(e2 !== null){
			 e2.style.display = 'block';
		 }
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

//User Progress table
$('#add_row').click(function() {
	add();
});

$('#delete_row').click(function() {
	alert('test');
	remove();
});

function print(el){
	alert('print');
	var printContents = document.getElementById(divName).innerHTML;
    var originalContents = document.body.innerHTML;

    document.body.innerHTML = printContents;

    window.print();

    document.body.innerHTML = originalContents;

}

function add() {
	var table = document.getElementById("user_progress");
    var i = table.rows.length-1;
    var row = table.insertRow(table.rows.length);
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
    var cell3 = row.insertCell(2);
    var cell4 = row.insertCell(3);
    var cell5 = row.insertCell(4);
    var cell6 = row.insertCell(5);
    var cell7 = row.insertCell(6);
    var cell8 = row.insertCell(7);
    var cell9 = row.insertCell(8);
    var cell10 = row.insertCell(9);
    cell1.innerHTML =  id=''+i;
    cell2.innerHTML = "<input name='userProgressList["+i+"].date' type='date' placeholder='Date' class='form-control input-md' type='text' class='form-control' id='datetimepicker4' /> ";
    cell3.innerHTML = "<input name='userProgressList["+i+"].hight' type='text' placeholder='Body Height' class='form-control input-md' id='' /> ";
    cell4.innerHTML = "<input name='userProgressList["+i+"].weigth' type='text' placeholder='Body Weight' class='form-control input-md'  /> ";
    cell5.innerHTML = "<input name='userProgressList["+i+"].BMI' type='text' placeholder='BMI' class='form-control input-md'  /> ";
    cell6.innerHTML = "<input name='userProgressList["+i+"].fatPercentage' type='text' placeholder='total fat' class='form-control input-md'  /> ";
    cell7.innerHTML = "<input name='userProgressList["+i+"].viscelar' type='text' placeholder='Viscelar fat' class='form-control input-md'  /> ";
    cell8.innerHTML = "<input name='userProgressList["+i+"].waist' type='text' placeholder='Waist size' class='form-control input-md'  /> ";
    cell9.innerHTML = "<input name='userProgressList["+i+"].thigh' type='text' placeholder='Thigh size' class='form-control input-md'  /> ";
    cell10.innerHTML = "<a id='delete_row' class='btn-sm btn-danger btn-lg'><span class='glyphicon glyphicon-remove'></span></a>"
}

function remove() {
    var table = document.getElementById("user_progress");
    if(table.rows.length > 2){
    	var row = table.deleteRow(table.rows.length-1);
    }
}
