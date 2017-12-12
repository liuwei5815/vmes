<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%> 
<%@ taglib uri="/tags/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>创建菜单</title>
<script>
function doSubmit(){
	var supiorId=$("#supiorId").val();
	if(supiorId == ""){
		top.Dialog.alert("请选择菜单栏目");
		return;
	}
	var menuName=$("#menuName").val();
	if(menuName == ""){
		top.Dialog.alert("请输入菜单名称");
		return;
	}
	$.ajax({
	     type: "POST",
	     url: "${ctx}/admin/sys_tables!createMenu.action",
	     cache: false,
	     dataType:"json",
	     data:{"tableId":$("#tableId").val(),"menuName":menuName,"supiorId":supiorId},
	     success: function(data){
    		if(data.successflag=="1"){
    			top.Dialog.alert("菜单创建成功!",function(){
	    			top.Dialog.close();	
    			});
    			parent.frmright.doQueryTab();
	     	}
	     }
	});
	
}

</script>
</head>
<body>
<form id="frm" action="admin/sys_tables!createMenu.action" method="post">
	<input type="hidden" id="tableId" name="tableId" value="${tableId }"/>
	<table style="width: 90%;margin: 10px;">
		
		<tr>
			<td>
			菜单栏目：<select class="default;" id="supiorId" name="supiorId" style="width: 50%;">
				<s:iterator value="list" var="menu">
					<option value="${menu.id }">${menu.name }</option>
				</s:iterator>
			</select>
			</td>
		</tr>
		<tr>
			<td>
			菜单名称：<input type="text" id="menuName" name="menuName"/>
			</td>
		</tr>
	</table>
</form>
</body>
</html>





