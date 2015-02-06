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
});