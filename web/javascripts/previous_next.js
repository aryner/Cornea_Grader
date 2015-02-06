/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function() {
	$(":submit[value=Previous]").click(function(e) {
		var prev = $("input[type=hidden][name=previous]").val();
		$("input[type=hidden][name=nextFile]").val(prev);
	});

	$(":submit[value=Next]").click(function(e) {
		var next = $("input[type=hidden][name=next]").val();
		$("input[type=hidden][name=nextFile]").val(next);
	});

	$(document).bind('keypress', function(e) {
		e = e || window.event;
		var unicode = e.keyCode || e.which;

		switch(unicode) {
			case 97 : //a
				$('input[type=radio][value=left]').prop('checked',true);
				break;
			case 115 : //s
				$('input[type=radio][value=delete]').prop('checked',true);
				break;
			case 100 : //d
				$('input[type=radio][value=right]').prop('checked',true);
				break;
			case 102 : //f
				break;
			case 37 : //left arrow
				e.preventDefault();
				$(':submit[value=Previous]').trigger('click');
				break;
			case 39 : //right arrow
				e.preventDefault();
				$(':submit[value=Next]').trigger('click');
				break;
		}
	});
});