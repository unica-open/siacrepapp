/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
$(document).ready(function() 
{
	$("#ord-sel-all").click(function() {
		  $(".ord-sel > input").prop('checked', this.checked);
	});
	
	$(".azione-file").click(function() {
		  $(".anchor-azione > span").text($(this).parents("tr").data("index"));
	});
		
	$(".conferma-azione").click(function() {
		var anchor = $("A", $(this).parent());
		anchor[0].confirm = true;
		anchor.attr('href', anchor.attr('href') + '?fi=' + $("span", anchor).text());
	});

	$(".modal-azione").on('hidden', function () {
		var anchor = $(".anchor-azione", $(this));

		if (anchor[0].confirm) {
			anchor[0].confirm = false;
			location.href = anchor.attr('href');
		}
	});
	
	
	$('[name="selectedXbrl"]').prop('checked', false);
	$('[name="__checkbox_selectedXbrl"]').remove();
	
});