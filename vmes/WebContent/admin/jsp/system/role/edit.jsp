<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<title>新增用户</title>
<%@ include file="/admin/jsp/common/common.jsp"%>
<script type="text/javascript">
	function doSubmit() {
		/*
		var name = $('#name');
		if($.trim(name.val()) == '') {
			top.Dialog.alert("请输入角色名称");
			name.focus();
			return false;
		} else if($.trim(name.val()).length > 15) {
			top.Dialog.alert("角色名称不能大于15个字符");
			return false;
		}
		*/
	
		//document.forms[0].submit(); 
		$("#frm").submit();
	}

	function cancel(){
		top.Dialog.close();
	}

	function unload(){
		if(document.frm.successflag.value == "1"){//执行成功
	    	top.Dialog.alert("保存成功",function(){
	    		cancel();
	    	});
	    	this.parent.frmright.window.location.href="${ctx}/admin/power!init.action";
		}else{
			if(document.forms[0].message.value !=""){
				top.Dialog.alert(document.forms[0].message.value);
		    	document.forms[0].message.value = "";	   
		    }
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
<s:form id="frm" name="frm" action="role!edit.action" method="post" theme="simple" >
	<s:hidden name="message" id="message"/>
	<s:hidden name="role.id" id="id"/>
	<input name="successflag" id="successflag" value="${successflag}" type="hidden"/>
		<table width="100%" class="tableStyle" transmode="true">
		  <tr>
            <td>角色名称：</td>
            <td><s:textfield id="name" maxlength="30" name="role.name" class="validate[required,length[0,15]]"/><span class="star">*</span></td>
          </tr>
     
          <%-- <tr>
            <td>备注：</td>
            <td><s:textarea name="role.memo" id="memo"/></td>
          </tr> --%>
          <tr>
            <td colspan="2"><p>
              <button onclick="return doSubmit(this);" type="button"><span class="icon_save">保 存</span></button>
              <button onclick="top.Dialog.close();" type="button"><span class="icon_no">关 闭</span></button>
          </p></td>
          </tr>
        </table>
        
</s:form>
	</div>
</div></div></div>
<div class="box1_bottomcenter2">
<div class="box1_bottomleft2"><div class="box1_bottomright2"></div></div></div>
</div>	
</body>
</html>
