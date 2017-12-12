<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>

<script>
$(document).ready(function(){ 
	unload();
});

function doSubmit(){
	$("#frm").submit();	
}

function cancel(){
	top.Dialog.close();
}

function unload(){
    if($("#successflag").val() == "1"){//执行成功
    	top.Dialog.alert("保存成功",function(){
    		cancel();
    	});
    	this.parent.frmright.window.location.href="${ctx}/admin/sys_setting!init.action";	
	}else{
		if(document.forms[0].message.value !=""){
			top.Dialog.alert(document.forms[0].message.value);
	    	document.forms[0].message.value = "";	   
	    }
	}
}

function abc(){
	var appNum = $("#appNum").val();
	var webNum = $("#webNum").val();
	$("#num").val(parseInt(appNum)+parseInt(webNum));
	
}

function save(){
	$("#frm").submit();
}
</script>

<body>
	<div class="position">
		<div class="center">
		<div class="left">
			<div class="right">
				<span>当前位置：系统信息管理 >> 企业账号管理 </span>
			</div>	
		</div>	
		</div>
	</div>
    
    <div id="scrollContent">
		<div class="box2" panelTitle="企业账号管理" roller="false">
		<form id="frm" method="post" action="admin/sys_setting!save.action" id="frm">
		<input type="hidden" name="message" id="message" value="${message}" /> 
		<input type="hidden" name="successflag" id="successflag" value="${successflag}" />
		<table class="tableStyle" transMode="true">
		<tr>
			<td>软件有效期：</td>
			<td>
				<input id="date" name="license.expiredDate" class="date" style="width: 170px" value="${license.expiredDate }" required />
			</td>
		</tr>
		<tr>
			<td>Web端账号数量（个）：</td>
			<td>
				<input id="webNum" name="license.webNum" class="validate[required,custom[onlyNumber],length[0,6]]" style="width: 170px" value="<c:if test="${not empty license.webNum }">${license.webNum}</c:if><c:if test="${ empty license.webNum }">0</c:if>" onchange="abc()"/>
			</td>
		</tr>
		<tr>
			<td>终端账号数量（个）：</td>
			<td>
				<input id="appNum" name="license.appNum" class="validate[required,custom[onlyNumber],length[0,6]]" style="width: 170px" value="<c:if test="${not empty license.appNum }">${license.appNum}</c:if><c:if test="${ empty license.appNum }">0</c:if>" onchange="abc()"/>
			</td>
		</tr>
		<tr>
			<td>账号总数量（个）：</td>
			<td>
				<input style="width: 168px; height: 100%" disabled id="num"  value="${license.webNum+license.appNum }"/>
			</td>
		</tr>
		<!-- <tr>
			<td><input id="mobileClient" type="checkbox" /></td><td><label for="mobileClient">终端账号</label></td>
		</tr>
		<tr>
			<td><input id="webClient" type="checkbox"/></td><td><label for="webClient">Web端账号</label></td>
		</tr> -->
		<tr>
			<td colspan="2"><button type="button" onclick="save()"/><span class="icon_save">保 存</span> </td>
		</tr>
		</table>
		</form>
		</div> 
	</div>
      

</body>
</html>





