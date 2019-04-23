$(document).ready(function() {
	$("#articlePhoto").change(function(event) {
		$("#articlePhotoLabel").text((event.target.files[0] != undefined) ? event.target.files[0].name : "Choisissez une photo");
	});
});