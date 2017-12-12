<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Insert title here</title>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type='text/javascript' src="${ctx }/admin/js/page.js"></script>
<script>

function openEditWin(id){
	if(id == ""){
		top.Dialog.alert("参数错误");
		return false;	
	}
	var diag = new top.Dialog();
	diag.Title = "修改角色信息";
	diag.URL = "${ctx}/admin/role!preEdit.action?role.id=" + id;
	diag.Height=150;
	diag.show();
}

function del(id){
	if(id == ""){
		top.Dialog.alert("参数错误");
		return false;
	}
	top.Dialog.confirm("确认要删除吗？",function(){  		
		document.forms[0].action = "${ctx}/admin/role!del.action?role.id=" + id;
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

<form method="post">
<input type="hidden" name="totalCount" id="totalCount" value="${totalCount }" /> 
<input type="hidden" name="totalPage" id="totalPage" value="${totalPage }" /> 
<input type="hidden" name="currPage" id="currPage" value="${currPage }" />
<table class="tableStyle" id="checkboxTable">
	<tr>
		<th width="30">序号</th>
		<th>角色名称</th>
		<th width="70">操作</th>
	</tr>
	<c:if test="${fn:length(list) == 0}">
		<tr>
			<td colspan="4" align="center">没有找到符合条件的记录</td>
		</tr>
	</c:if>

	<s:iterator value="list" status="st" var="cell">
		<tr>
			<td>${st.index+1 }</td>
			<td><s:property value="name" /></td>
			<td width="70">
				<span class="img_edit hand" title="编辑"	onclick="openEditWin('<s:property value="id"/>');"></span>
				
				<span class="img_delete hand" title="删除" onclick="del('<s:property value="id"/>');"></span>
			</td>
		</tr>
	</s:iterator>
</table>
</form>
</div>

</body>
</html>
