$(document).ready(function() {
	$('#example').DataTable();

});

$('#confirm-delete').on('show.bs.modal', function(e) {
//$('button.btnDelete').on('show.bs.modal', function(e) {
	esseyId = e.relatedTarget.id;
});
//
$('#confirm-delete-btn').click(function(e) {
	 e.stopImmediatePropagation();

	 $.get(esseyId);
	
	 $('table').on('click',function(){
		 $(this).parents('tr').remove();
	 });
	 
	 location.reload();
});



