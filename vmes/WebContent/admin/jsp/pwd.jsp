<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/admin/jsp/common/common.jsp"%>
<script type="text/javascript">
	function doSubmit(e) {
		var oldpwd = $('#oldpwd');
		if($.trim(oldpwd.val()) == '') {
			top.Dialog.alert("请输入旧密码");
			oldpwd.focus();
			return false;
		}

		var newpwd = $('#newpwd');
		if($.trim(newpwd.val()) == '') {
			top.Dialog.alert("请输入新密码");
			newpwd.focus();
			return false;
		}
		
		var newpwd2 = $('#newpwd2');
		if($.trim(newpwd.val()) != $.trim(newpwd2.val())) {
			top.Dialog.alert("密码验证错误");
			newpwd2.focus();
			return false;
		}
		document.forms[0].submit(); 

	}

	function unload(){
		if(document.forms[0].message.value !=""){
	    	top.Dialog.alert(document.forms[0].message.value);
	    	document.forms[0].message.value = "";	   
	    }
	}

	$(document).ready(function(){ 
		unload();
	});
</script>
</head>
<body>
<div class="static_box1">
<div class="box1_topcenter2"><div class="box1_topleft2"><div class="box1_topright2"></div></div></div>
<div class="box1_middlecenter"><div class="box1_middleleft2"><div class="box1_middleright2">
	<div style="padding:0 20px 0 20px;">
<form name="frm" action="${ctx }/admin/login!uptPwd.action" method="post">
	<input type="hidden" name="message" id="message" value="${message}"/>
	<table class="tableStyle" transMode="true">
        <tr>
          <td>原密码：</td>
          <td><input type="password" id="oldpwd" name="oldpwd"/></td>
        </tr>
        <tr>
          <td>新密码：</td>
          <td><input type="password" id="newpwd" name="newpwd" checkStrength="true"/></td>
        </tr>
        <tr>
          <td>确认密码：</td>
          <td><input type="password" id="newpwd2" name="verifypwd" checkStrength="true"/></td>
        </tr>
        <tr>
           <td colspan="2"><p>
             <button onclick="return doSubmit();" type="button"><span class="icon_save">保 存</span> </button>
             <button onclick="top.Dialog.close();" type="button"><span class="icon_no">关 闭</span> </button>
         </p></td>
         </tr>
      </table>
      </form>
    </div>
</div></div></div>
<div class="box1_bottomcenter2">
<div class="box1_bottomleft2"><div class="box1_bottomright2"></div></div></div>
</div>	
</body>
</html>