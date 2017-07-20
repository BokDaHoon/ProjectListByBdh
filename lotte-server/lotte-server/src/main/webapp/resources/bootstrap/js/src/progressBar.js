var progress = function() {
	$.ajaxSetup({
		beforeSend : function() {
			$('#loading').fadeIn();
		},
		complete : function() {
			$('#loading').fadeOut();
		},
		success : function() {
			$('#loading').fadeOut();
		}
	});
}