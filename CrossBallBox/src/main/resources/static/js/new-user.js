(function() {
	var dialog = document.getElementById('window');
	document.getElementById('show').onclick = function() {
		if (dialog !== null) {
			dialog.show();
		}
	};
	document.getElementById('exit').onclick = function() {
		if (dialog !== null) {
			dialog.close();
		}
	};
});

$(function() {
	$('#datetimepicker1').datetimepicker({
		language : 'pt-BR'
	});
});

