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
	//if(id == ""){
	//	top.Dialog.alert("参数错误");
	//	return false;	
	//}
	var diag = new top.Dialog();
	diag.Title = "编辑客户信息";
	diag.URL = "${ctx}/admin/customerType!edit.action?id=" + id;
	diag.Height=200;
	diag.Width=650;
	diag.show();
}
$(document).ready(function() {
	loadPage();
});
function doSubmit(){
	$("#frm").submit();	
}
function del(id){
	if(id == ""){
		top.Dialog.alert("参数错误");
		return false;
	}
	top.Dialog.confirm("确定要删除该客户信息吗？",function(){
		$.ajax({
		     type: "POST",
		     url: "${ctx}/admin/customerType!del.action",
		     cache: false,
		     dataType:"json",
		     data:{"id":id},
		     success: function(data){
	     		if(data.successflag=="1"){
	     			top.Dialog.alert("删除成功",function(){
	    				$("#frm").submit();	
	    	    	});
		     	}
	     		else{
	     			top.Dialog.alert("删除失败");}
		     }
		});	
	});
}
</script>
</head>
<body>
<div id="scrollContent" class="border_gray">

<form method="post" id="frm" action="${ctx}/admin/customerType!query.action">
<input type="hidden" name="totalCount" id="totalCount" value="${totalCount }" /> 
<input type="hidden" name="totalPage" id="totalPage" value="${totalPage }" /> 
<input type="hidden" name="currPage" id="currPage" value="${currPage }" />
<table class="tableStyle" id="checkboxTable" style="text-align: center">
	<tr>
		<th width="25%">客户类型</th>
		<th width="50%">说明</th>
		<th width="25%">操作</th>
	</tr>
	<c:if test="${fn:length(list) == 0}">
		<tr>
			<td colspan="4" align="center">没有找到符合条件的记录</td>
		</tr>
	</c:if>

<%-- 	<s:iterator value="list" status="st" var="cell">
		<tr>
			<td>化工</td>
			<td>${cell.customerCode }</td>
			<td></td>
			<td width="70" style = "text-align: center">
				<span class="img_edit hand" title="编辑"	onclick="openEditWin('${cell.id}');"></span>
				<span class="img_delete hand" title="删除" onclick="del('${cell.id}');"></span>
			</td>
		</tr>
	</s:iterator> --%>
	<tr>
		<td>化工</td>
		<td></td>
		<td width="70" style = "text-align: center">
			<span class="img_edit hand" title="编辑"	onclick="openEditWin('${cell.id}');"></span>
			<span class="img_delete hand" title="删除" onclick="del('${cell.id}');"></span>
		</td>
	</tr>
</table>
</form>
</div>

</body>
</html>
