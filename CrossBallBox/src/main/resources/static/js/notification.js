$(document).ready(function() {

	// ANIMATEDLY DISPLAY THE NOTIFICATION COUNTER.
	$('#noti_Counter').css({
		opacity : 0
	})
	// .text('3') // ADD DYNAMIC VALUE (YOU CAN EXTRACT DATA FROM DATABASE OR
	// XML).
	.css({
		top : '-10px'
	}).animate({
		top : '-2px',
		opacity : 1
	}, 500);

	if ($('#noti_Counter').text() == "") {
		$('#noti_Counter').css({
			background : 0
		})
	}

	$('#noti_Button').click(function() {

		// TOGGLE (SHOW OR HIDE) NOTIFICATION WINDOW.
		$('#notifications').fadeToggle('fast', 'linear', function() {
			if ($('#notifications').is(':hidden')) {
				$('#noti_Button').css('background-color', '#ffffff');
			} else
				$('#noti_Button').css('background-color', '#FFF'); // CHANGE
																	// BACKGROUND
																	// COLOR OF
																	// THE
																	// BUTTON.
		});

		$('#noti_Counter').fadeOut('slow'); // HIDE THE COUNTER.

		return false;
	});

	// HIDE NOTIFICATIONS WHEN CLICKED ANYWHERE ON THE PAGE.
	$(document).click(function() {
		$('#notifications').hide();

		// CHECK IF NOTIFICATION COUNTER IS HIDDEN.
		if ($('#noti_Counter').is(':hidden')) {
			// CHANGE BACKGROUND COLOR OF THE BUTTON.
			$('#noti_Button').css('background-color', '#ffffff');
		}

	});

	// $('#notifications').click(function () {
	// return false; // DO NOTHING WHEN CONTAINER IS CLICKED.
	// });

	// za dodavanje texta - da pozovem iz bvackenda =
	// $('<p>Text</p>').appendTo('#notification_area');
});