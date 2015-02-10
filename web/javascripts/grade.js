/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function() {
	$(':submit[value=Submit]').click(function(e) {
		var grade = $('input[type=radio][name=grade][value=0]').prop('checked')
			 || $('input[type=radio][name=grade][value=1]').prop('checked');

		var quality = $('input[type=radio][name=quality][value=0]').prop('checked')
			   || $('input[type=radio][name=quality][value=1]').prop('checked')
			   || $('input[type=radio][name=quality][value=2]').prop('checked');

		if(!(grade && quality)) {
			e.preventDefault();
			alert("You must select both a grade and quality");
		}
	});

	$(document).bind('keypress', function(e) {
		e = e || window.event;
		var unicode = e.keyCode || e.which;

		console.log(unicode);
		switch(unicode) {
			case 97 : //a
				$('input[type=radio][name=grade][value=1]').prop('checked',true);
				break;
			case 102 : //f
				$('input[type=radio][name=grade][value=0]').prop('checked',true);
				break;
			case 106 : //j
				$('input[type=radio][name=quality][value=2]').prop('checked',true);
				break;
			case 107 : //k
				$('input[type=radio][name=quality][value=1]').prop('checked',true);
				break;
			case 108 : //l
				$('input[type=radio][name=quality][value=0]').prop('checked',true);
				break;
			case 13 : //return/enter
				$(':submit[value=Submit]').trigger('click');
				break;
		}
	});
});