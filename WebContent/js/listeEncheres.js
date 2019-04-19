$(function() {
    function log( message ) {
      $( "<div>" ).text( message ).prependTo( "#log" );
      $( "#log" ).scrollTop( 0 );
    }

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
      select: function( event, ui ) {
        log( ui.item ?
          "Selected: " + ui.item.label :
          "Nothing selected, input was " + this.value);
      },
      open: function() {
        $( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
      },
      close: function() {
        $( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
      }
    });
    
    
 });

function gestionCheckBox(){
	var rad = document.getElementsByName("grpBtnRad");
	for (var i = 0; i < rad.length; i++) {
	    rad[i].addEventListener('change', function() {
	    	if(this.value == "achats"){
	    		$('#checkOuvertes').prop('disabled', false);
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
	    		$('#checkNonDebuter').prop('disabled', false);
	    		$('#checkTerminer').prop('disabled', false);
	    	}
	    });
	 }
}
