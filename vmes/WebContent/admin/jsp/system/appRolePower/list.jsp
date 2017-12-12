<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新闻信息列表</title>
<%@ include file="/admin/jsp/common/common.jsp"%>
<script type='text/javascript' src="${ctx }/admin/js/page.js"></script>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
.tableStyle tr th,td{
 height:24px;
 text-align: center;
 white-space: nowrap;
 word-wrap: break-word;
 overflow: hidden;
}
</style>
<script>
function openEditWin(id, roleName){
	var diag = new top.Dialog();
	diag.ID="appRolePower";
	var title = "编辑 " + roleName;
	console.log(roleName);
	diag.Title = title;	
	diag.URL = "${ctx}/admin/appRolePower!preEdit.action?appRole.id=" + id;
	diag.Height = 150;
	diag.show();
	return false;
}

function openPowerWin(id) {
	var diag = new top.Dialog();
	var title = "编辑权限";
	diag.Title = title;	
	diag.URL = "${ctx}/admin/appRolePower!initPower.action?appRole.id=" + id;
	diag.show();
	return false;
}


function del(id){
	if(id == ""){
		top.Dialog.alert("参数错误");
		return false;
	}
	top.Dialog.confirm("确认要删除吗？",function(){  		
		document.forms[0].action = "${ctx}/admin/appRolePower!del.action?appRole.id=" + id;
		document.forms[0].submit();
	});
}

$(document).ready(function() {
	loadPage();
});
</script>
</head>
<body>
<div id="scrollContent" class="border_gray">
<input type="hidden" name="totalCount" id="totalCount" value="${totalCount }" />
<input type="hidden" name="totalPage" id="totalPage" value="${totalPage }" />
<input type="hidden" name="currPage" id="currPage" value="${currPage }" />
<input type="hidden" name="appRoleId" id="appRoleId" value="${appRole.id }" />
<input type="hidden" name="appRoleName" id="appRoleName" value="${appRole.name }" />
<form name="form1" method="post" target="frmright">
<table class="tableStyle" id="checkboxTable">
	<tr>
		<th width="10%">序号</th>
      	<th>角色名称</th>
      	<th width="10%">操作</th>
	</tr>
	<c:if test="${fn:length(list) == 0}">
		<tr>
			<td colspan="<s:property value="3"/>" align="center">没有找到符合条件的记录</td>
		</tr>
	</c:if>
	<s:iterator value="list" status="st" var="cell">
		 <tr>
			<td>${st.index+1 }</td>
			<td><s:property value="name"/></td>
			<td>
				<span class="img_guard hand" title="权限" onclick="openPowerWin('<s:property value="id"/>');"></span>
				 <span class="img_edit hand" title="编辑" onclick="openEditWin('<s:property value="id"/>', '<s:property value="name"/>');"></span>
				 <span class="img_delete hand" title="删除" onclick="del('<s:property value="id"/>');"></span>
			</td>
		</tr>
	</s:iterator>
</table>
</form>
</div>
</body>
</html>