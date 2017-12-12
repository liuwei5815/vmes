(function(jQuery){
jQuery.fn.vchecks = function() {

	object = jQuery(this);
	object.addClass("geogoer_vchecks");
	object.find("li:first").addClass("first");
	object.find("li:last").addClass("last");
	object.find("input[type=checkbox]").each(function() {
		$(this).hide();
	});
	object.find("li").each(function() {
		if ($(this).find("input[type=checkbox]").is(":checked")) {
			$(this).addClass("checked");
			$(this).append('<div class="check_div"></div>')
		} else {
			$(this).addClass("unchecked");
			$(this).append('<div class="check_div"></div>')
		}
	});
	object.find("li").find("span").click(function(a) {
		a.preventDefault();
		check_li = $(this).parent("li");
		checkbox = $(this).parent("li").find("input[type=checkbox]");
		if (checkbox.is(":checked")) {
			checkbox.attr("checked", false);
			check_li.removeClass("checked");
			check_li.addClass("unchecked")
		} else {
			checkbox.attr("checked", true);
			check_li.removeClass("unchecked");
			check_li.addClass("checked")
		}
	});
	object.find("li").not(":first").not(":last").find("span").bind("mouseover", function(a) {
		$(this).parent("li").addClass("hover")
	});
	object.find("li").not(":first").not(":last").find("span").bind("mouseout", function(a) {
		$(this).parent("li").removeClass("hover")
	});
	object.find("li").not(":first").not(":last").bind("mouseover", function(a) {
		$(this).parent("li").addClass("first_hover")
	});
	object.find("li").not(":first").not(":last").bind("mouseout", function(a) {
		$(this).parent("li").removeClass("first_hover")
	});
	object.find("li").not(":first").not(":last").bind("mouseover", function(a) {
		$(this).parent("li").addClass("last_hover")
	});
	object.find("li").not(":first").not(":last").bind("mouseout", function(a) {
		$(this).parent("li").removeClass("last_hover")
	})
};
})($)
$(function() {
	$(".checkButton").vchecks()
});