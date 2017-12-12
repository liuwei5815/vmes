/*******************************************************************************
 * layer util
 * 
 * @param msg
 * @returns
 */ 



/*******************************************************************************
 * cookie util
 * 
 * @param msg
 * @returns
 */ 
(function(w) {
	var l = {};
	w.cookieUtil = l;
	l.clearCookie=function() {
		var keys = document.cookie.match(/[^ =;]+(?=\=)/g);
		if (keys) {
			for (var i = keys.length; i--;)
				document.cookie = keys[i] + '=0;expires='
						+ new Date(0).toUTCString();
		}
	}
	l.getCookie=function(name) {
		var nameEQ = name + "=";
		var ca = document.cookie.split(';'); //把cookie分割成组    
		for (var i = 0; i < ca.length; i++) {
			var c = ca[i]; //取得字符串    
			while (c.charAt(0) == ' ') { //判断一下字符串有没有前导空格    
				c = c.substring(1, c.length); //有的话，从第二位开始取    
			}
			if (c.indexOf(nameEQ) == 0) { //如果含有我们要的name    
				return unescape(c.substring(nameEQ.length, c.length)); //解码并截取我们要值    
			}
		}
		return false;
	}
	l.setCookie=function(name, value, seconds) {
		seconds = seconds || 0; //seconds有值就直接赋值，没有为0，这个根php不一样。    
		var expires = "";
		if (seconds != 0) { //设置cookie生存时间    
			var date = new Date();
			date.setTime(date.getTime() + (seconds * 1000));
			expires = "; expires=" + date.toGMTString();
		}
		document.cookie = name + "=" + escape(value) + expires + "; path=/"; //转码并赋值    
	}
	


})(window);

(function($) {
	$.xyAjax = function(options) {
		var defaults = {
			url : '',
			data : {},
			dataType : 'json',
			type : 'get',
			'suc' : function(data) {
			},
			'err' : function(msg, code) {
				top.Dialog.alert(msg);
			},
			"success" : function(data) {
				if (data.code == '0') {
					this.suc(data.body);
				} else{
					this.err(data.msg, data.code);
				}
			},
			error : function() {
				this.err("与服务器连接失败或超时",500);
			}
		}
		var opts = $.extend(defaults, options);
		$.ajax(opts);
	}
	$.xyAjaxFileUpload = function(options) {
		
		var defaults = {
			url : '',
			dataType : 'json',
			'suc' : function(data) {
			},
			'err' : function(msg, code) {
				layerUtil.error(msg);
			},
			"success" : function(data) {
				if (data.code == '0') {
					this.suc(data.body)
				} else {
					this.err(data.msg, data.code)
				}
			},
			error : function(msg,code) {
				console.log(msg);
				console.log(code);
				this.err("与服务器连接失败或超时", 500);
			}
		}
		var opts = $.extend(defaults, options);
		$.ajaxFileUpload(opts);
	}
	$.xyLoad = function(param) {
		var defaults = {
			"suc" : function(msg) {
				if($.trim(msg) != ""){
					top.Dialog.alert(msg);
				}
			},
			"err" : function(msg, code) {
				top.Dialog.alert(msg);
			},
			"showMsg" : function(msg) {
				top.Dialog.alert(msg);
			}
		};
		var options = $.extend({}, defaults, param)
		var code = $("#code").val();
		var msg = $("#message").val();
		if (code !== "") {
			if (code === "0") {
				options.suc(msg);
			} else {
				options.err(msg, code);
			}
		} 
	}
})(jQuery);
