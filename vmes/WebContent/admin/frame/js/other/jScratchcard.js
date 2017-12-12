/*
 * jScratchcard jQuery plugin - Rel. 0.0
 *
 * Copyright (c) 2010 Giovanni Casassa (senamion.com - senamion.it)
 *
 * Dual licensed under the MIT (MIT-LICENSE.txt)
 * and GPL (GPL-LICENSE.txt) licenses.
 *
 * http://www.senamion.com
 *
 */


(function($)
{
	jQuery.fn.jScratchcard = function(o) {

		o = jQuery.extend({
			opacity: 0.2,
			color: '#666666',
			stepx: 10,
			stepy: 10,
			mousedown: true
		}, o);

		return this.each(function(i) {
			var el = $(this);
			var h = el.height();
			var	w = el.width();
			var	position = el.position();
			var x = position.left;
			var y = position.top;
			var	uuid = (el.attr('name') || el.attr('id') || el.attr('class') || 'internalName') + '__sc';

			over = "";
			for (i=0;i<w;i+=o.stepx) {
				for (j=0;j<h;j+=o.stepy) {
					over += "<div class='"+uuid+"' style='height:" + o.stepy + "px; width:" + o.stepx + "px; background-color:" + o.color + "; position: absolute;border: 0;overflow: hidden;top:" + (y + j) + "px; left:" + (x + i) + "px;' />";
				}
			}
			el.after(over);
			$('.'+uuid)
//				.css({"height": o.stepy+'px', "width": o.stepx+'px', "background-color": o.color})
				.mouseover(function() {
					if (o.mousedown == false || el.data('okMouse')) {
						op = $(this).css('opacity') - o.opacity;
						if (op <= 0)
							$(this).remove();
						else
							$(this).css({'opacity': op});
					}
				})
			if (o.mousedown)
				$('.'+uuid)
					.mousedown(function() {
						el.data('okMouse', true);
						return false;
					})
					.mouseup(function() {
						el.data('okMouse', false);
						return false;
					});
		});
	};
})(jQuery);