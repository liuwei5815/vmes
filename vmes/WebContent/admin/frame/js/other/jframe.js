// jFrame
// $Revision: 1.144 $
// Author: Frederic de Zorzi
// Contact: fredz@_nospam_pimentech.net
// Revision: $Revision: 1.144 $
// Date: $Date: 2010-06-16 12:12:54 $
// Copyright: 2007-2009 PimenTech SARL
// Tags: ajax javascript pimentech english jquery


jQuery.fn.waitingJFrame = function () {
    // Overload this function in your code to place a waiting event
    // message, like :  $(this).html("<b>loading...</b>");
};

function _jsattr(elem, key) {
    var res = jQuery(elem).attr(key);
    if (res == undefined) {
        return function() {};
    }
    if (typeof res == 'string') {
        return function() { eval(res); };
    }
    return res;
}

function jFrameSubmitInput(input) {
    var target = jQuery(input).getJFrameTarget();
    if (target.length) {
        var form = input.form;
        if (form.onsubmit && form.onsubmit() == false
            || target.preloadJFrame() == false) {
            return false;
        }
		var submit_events = jQuery(form).data("events").submit;
		if (submit_events) {
			jQuery.each(submit_events, function(i, submit) { 
							submit.handler(); 
						});
		}
        jQuery(form).ajaxSubmit({
                                    target: target,
                                    beforeSubmit: function(formArray) {
                                        formArray.push({ name:"submit", value: jQuery(input).attr("value") });
                                    },
                                    success: function() {
                                        target.attr("src", jQuery(form).attr("action"));
                                        _jsattr(target, "afterload")();
                                        target.activateJFrame();
                                    }
                                });
        return false;
    }
    return true;
}

jQuery.fn.preloadJFrame = function(initial) {
    if (!initial && _jsattr(this, "beforeload")() == false) {
        return false;
    }
    jQuery(this).waitingJFrame();
    return true;
};

jQuery.fn.getJFrameTarget = function() {
    var target = jQuery(this).attr("target");
    if (target) {
        return jQuery("#" + target);
    }
    // Returns first parent jframe element, if exists
    return jQuery(jQuery(this).parents("div[src]").get(0));
};

jQuery.fn.loadJFrame = function(url, callback, initial) {
    // like ajax.load, for jFrame. the onload->afterload attribute is supported
    var this_callback = _jsattr(this, "afterload");
    callback = callback || function(){ };
    url = url || jQuery(this).attr("src");
    if (url && url != "#") {
        if (jQuery(this).preloadJFrame(initial) == false) {
            return false;
        }
        jQuery(this).load(url,
                          function(response,status,xhr) {
                              jQuery(this).attr("src", url);
                              jQuery(this).activateJFrame();
                              jQuery(this).find("div[src]").each(function(i) {
                                                                     jQuery(this).loadJFrame();
                                                                 } );
                              this_callback();
                              callback(response,status,xhr);
                          });
    }
    else {
        jQuery(this).activateJFrame();
    }
    return true;
};

jQuery.fn.activateLink = function() {
    this.unbind("click");
    this.each(function () {
                  var oc = this.onclick;
                  this.onclick = null;
                  jQuery(this).bind("click", function (event) { 
                                   if (oc) {
                                       if (!oc()) {
                                           event.stopImmediatePropagation();
                                           return false;
                                       } 
                                   }
                                   return true;
                               });

              });
    this.click(function() {
                   var target = jQuery(this).getJFrameTarget();
                   if (target.length) {
                       var href = jQuery(this).attr("href");
                       var toggle = jQuery(this).attr("toggle");
                       if (href && href.indexOf('javascript:') != 0) {
                           if (toggle == "on") {
                               target.hide();
                               jQuery(this).attr("toggle", "off");
                               return false;
                           }
                           if (toggle == "off") {
                               target.show();
                               jQuery(this).attr("toggle", "on");
                           }
                           target.loadJFrame(href);
                           return false;
                       }
                   }
                   return true;
               } )
        .attr("jframe","activated");
};

jQuery.fn.activateSubmitButton = function() {
    this.unbind("click")
        .click(function() {
                   return jFrameSubmitInput(this);
               } )
        .attr("jframe","activated");
};

jQuery.fn.activateJFrame = function() {
    // Add an onclick event on all <a> and <input type="submit"> tags
    jQuery(this).find("a")
        .not("[jframe='no']")
        .not("[jframe='activated']")
        .activateLink();

    jQuery(":image,:submit,:button", this)
        .not("[jframe='no']")
        .not("[jframe='activated']")
        .activateSubmitButton();


	if ($.browser.msie && $.browser.version.substr(0,1)<7) {
		// Only for IE6 : enter key invokes submit event
		jQuery(this).find("form")
			.unbind("submit")
			.submit(function() {
                    return jFrameSubmitInput(jQuery(":image,:submit,:button", this).get(0));
                } );
	}
};

jQuery(document).ready(
    function() {
        jQuery(document).find("div[src]").each(
            function(i) {
                jQuery(this).loadJFrame(undefined, undefined, true);
            } );
        jQuery(document).find("a[jframe='activate']").activateLink();
        jQuery(":image,:submit,:button", document).find("[jframe='activate']").activateSubmitButton();
    } );
