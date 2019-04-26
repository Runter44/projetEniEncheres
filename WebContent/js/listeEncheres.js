$(function() {

    $( "#filtreNomArticle" ).autocomplete({
      source: function( request, response ) {
        $.ajax({
          url: "http://localhost:8080/projetEniEncheres/AutoCompleteNomArticle",
          dataType: "jsonp",
          data: {
            q: request.term
          },
          success: function( data ) {
            response( data );
          }
        });
      },
      minLength: 3,
      open: function() {
        $( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
      },
      close: function() {
        $( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
      }
    });
 });

function gestionBtnRadCheckBox(){
	var rad = document.getElementsByName("grpBtnRad");
	for (var i = 0; i < rad.length; i++) {
	    rad[i].addEventListener('change', function() {
	    	if(this.value == "achats"){
	    		$('#checkOuvertes').prop('disabled', false);
	    		$('#checkOuvertes').prop('checked', true);
	    		$('#checkMesEncheres').prop('disabled', false);
	    		$('#checkRemporter').prop('disabled', false);
	    		
	    		$('#checkEnCours').prop('disabled', true);
	    		$('#checkEnCours').prop('checked', false);
	    		$('#checkNonDebuter').prop('disabled', true);
	    		$('#checkNonDebuter').prop('checked', false);
	    		$('#checkTerminer').prop('disabled', true);
	    		$('#checkTerminer').prop('checked', false);
	    	}else{
	    		$('#checkOuvertes').prop('disabled', true);
	    		$('#checkOuvertes').prop('checked', false);
	    		$('#checkMesEncheres').prop('disabled', true);
	    		$('#checkMesEncheres').prop('checked', false);
	    		$('#checkRemporter').prop('disabled', true);
	    		$('#checkRemporter').prop('checked', false);
	    		
	    		$('#checkEnCours').prop('disabled', false);
	    		$('#checkEnCours').prop('checked', true);
	    		$('#checkNonDebuter').prop('disabled', false);
	    		$('#checkTerminer').prop('disabled', false);
	    	}
	    });
	 }	
}


