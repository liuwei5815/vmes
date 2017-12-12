<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="/admin/jsp/common/common.jsp"%>
<title>新增菜单</title>
<script type="text/javascript">
function doSubmit(){
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
    	this.parent.frmright.window.location.href="${ctx}/admin/dispatch!init.action";	
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
<div class="box1_topcenter2">
<div class="box1_topleft2">
<div class="box1_topright2">
</div>
</div>
</div>
<div class="box1_middlecenter">
<div class="box1_middleleft2">
<div class="box1_middleright2">
<div style="padding:0 20px 0 20px;">
<s:form name="frm" action="dispatch!edit.action" method="post" theme="simple" id="frm">
	<s:hidden name="message" id="message"/>
	<input name="id" value="${planTodo.id}" type="hidden"/>
	<input name="successflag" id="successflag" value="${successflag}" type="hidden"/>
	<table width="100%" class="tableStyle" transmode="true">
		<tr>
            <td>派工单编号:</td>
            <td>
            <input type="text" disabled="disabled" class="validate[required]"  value="${planTodo.code}"/>
		</tr>
		 <tr>
            <td>生产计划:</td>
            <td>
            <input type="text" disabled="disabled" class="validate[required]"  value="${productPlanCode}"/>
		</tr>
		<tr>
            <td>计划数量:</td>
            <td>
            <input type="text" name="planNum" class="validate[required,custom[onlyNumber]]" value="${planTodo.planNum}"/>
			<span class="star"> *</span></td>
		</tr>
		<tr>
            <td>实际完成数量:</td>
            <td>
            <input type="text" name="actualNum" class="validate[required,custom[onlyNumber]]"  value="${planTodo.actualNum}"/>
			<span class="star"> *</span></td>
		</tr>
		<tr>
            <td>生产工序名称:</td>
            <td>
            <input type="text" name="processName" class="validate[required]"  value="${planTodo.processName}"/>
			<span class="star"> *</span></td>
		</tr>
		<tr>
            <td>分配给:</td>
            <td>
            <input type="text" disabled="disabled" class="validate[required]"  value="${assginto}"/>
		</tr>
		<%-- <tr>
            <td>派工单状态:</td>
            <td>
            <input type="text" name="planNum" class="validate[required,custom[onlyNumber]]" value="${planTodo.state}"/>
			<select>
				<option value="1">完成</option>
				<option value="2">未完成</option>
				<option value="3">删除</option>
			</select>
			<span class="star"> *</span></td>
		</tr> --%>
		<%-- <tr>
            <td>派工单数量：</td>
            <td><s:textfield id="pcount" class="validate[required,funcCall[validateP]]" name="count"/><span class="star"> *</span></td>
		</tr> --%>
		<tr>
            <td colspan="2">
            <p>
            <button onclick="return doSubmit();" type="button"><span class="icon_save">保 存</span> </button>
            <button onclick="top.Dialog.close();" type="button"><span class="icon_no">关 闭</span> </button>
          	</p>
          	</td>
        </tr>
        
	</table>        
</s:form>
</div>
</div>
</div>
</div>
<div class="box1_bottomcenter2">
<div class="box1_bottomleft2">
<div class="box1_bottomright2">
</div>
</div>
</div>
</div>
</body>
</html>