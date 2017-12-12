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
$(document).ready(function(){ 
	loadPage();
});
</script>
</head>
<body>
<div id="scrollContent">
 <form action="${ctx}/accout_setting!queryEmployee.action" method="post" target="querytable" id="frm">
<input type="hidden" name="totalCount" id="totalCount" value="${totalCount }" />
<input type="hidden" name="totalPage" id="totalPage" value="${totalPage }" />
<input type="hidden" name="currPage" id="currPage" value="${currPage }" />
<table class="tableStyle" id="checkboxTable">
	<tr>
		<th width="20"></th>
		<th width="30">员工号</th>
		<th width="30">姓名</th>
		<th width="30">性别</th>
		<th width="30">出生年月</th>
		<th width="30">手机号码</th>
		<th width="30">职位</th>
		<th width="30">部门</th>
		<th width="30">身份证</th>
	</tr>
	<c:if test="${fn:length(list) == 0}">
		<tr>
			<td colspan="12" align="center">没有找到符合条件的记录</td>
		</tr>
	</c:if>
	<s:iterator value="list" status="st" var="cell">
		 <tr>
		 	<td><input type="checkbox" value="${cell.Id }" empName="${cell.name }" name="checks" /></td>
		 	<td>${cell.serial_no}</td>
			<td>${cell.name }</td>
			<td>
				<s:if test="#request.cell.gender==1">
					男
				</s:if>
				<s:else>
					女
				</s:else>
			</td>
			<td>${cell.birthday}</td>
			<td>${cell.phoneNum}</td>
			<td>${cell.jobtitle}</td>
			<td>${cell.depName}</td>
			<td>${cell.idcard}</td>
		</tr>
	</s:iterator>
</table>
</form>
</div>
</body>
</html>