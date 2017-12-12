<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加操作人员信息</title>
<link href="${ctx}/css/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/frame/js/jquery.autocomplete.js"></script>
<script type="text/javascript">

	jQuery(document).ready(function() {
		unload();
	}); 

	function unload(){
	    if($('#successflag').val() == "1"){//执行成功
			this.parent.frmright.doQuery();	
			top.Dialog.alert($("#message").val());
			top.Dialog.close();
		}else{
			if(document.forms[0].message.value !=""){
				top.Dialog.alert(document.forms[0].message.value);
		    	document.forms[0].message.value = "";	   
		    }
		}
	}
	
	function checkpasswd(){
	
      var pwd2 = document.getElementById("RePassword").value;
      var pwd1 = document.getElementById("password").value;
      if(pwd1!=pwd2){
    	  top.Dialog.alert("两次输入的密码不一样");
        return false;
      }
    }
</script>
</head>
<body>
<div class="static_box1">
<div class="box1_topcenter2"><div class="box1_topleft2"><div class="box1_topright2"></div></div></div>
<div class="box1_middlecenter"><div class="box1_middleleft2"><div class="box1_middleright2">
	<div style="padding:0 20px 0 20px;">
    <s:form action="admin/admin!add.action" method="post" theme="simple">
	<s:hidden name="message" id="message"/>
	<input type="hidden" name="successflag" id="successflag" value="${successflag}"/>
	  <table class="tableStyle" transmode="true">
	  	
		<tr>
			<td>账&nbsp;&nbsp;号：<span class="star">*</span></td><td><input type="text" id="name" name="admin.account" class="validate[required,length[0,100]]"  style="width:200px;"/></td>
		</tr>
		<tr>
			<td>密&nbsp;&nbsp;码：<span class="star">*</span></td><td><input type="password" id="password" name="admin.pwd" checkStrength="true" class="validate[required,length[3,15]]"  style="width:200px;"/></td>
		</tr>
		<tr>
			<td>确认密码：<span class="star">*</span></td><td><input type="password" id="RePassword" name="RePassword" checkStrength="true" class="validate[required,length[3,15],confirm[password]]"  style="width:200px;"/></td>
		</tr>
		<tr>
			<td nowrap="nowrap">角色类型：<span class="star">*</span></td>
          	<td>
          		<select id="sel" name="admin.roleId">
          		  <option value="">--请选择--</option>
          		  <s:iterator value="#request.rList" var="item">
          		  	<option value="${item.id}">${item.name}</option>
          		  </s:iterator>
          		</select>
          	</td>
		</tr>
		<tr>
			<td colspan="2"><p></p>
				<input type="submit" onclick="return checkpasswd()" value="添加"/>
				<input type="reset" value="重置"/>
			</td>
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




