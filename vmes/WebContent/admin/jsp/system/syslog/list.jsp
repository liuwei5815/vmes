<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>日志列表</title>
<%@ include file="/admin/jsp/common/common.jsp"%>
<script type='text/javascript' src="${ctx }/admin/js/page.js"></script>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style>
td, th {
	text-align: center;
}
</style>
<script>
	$(document).ready(function() {
		loadPage();
	});
</script>
</head>
<body>
	<div id="scrollContent" class="border_gray">
		<input type="hidden" name="totalCount" id="totalCount"
			value="${totalCount }" /> <input type="hidden" name="totalPage"
			id="totalPage" value="${totalPage }" /> <input type="hidden"
			name="currPage" id="currPage" value="${currPage }" />
		<form method="post">
		<table class="tableStyle" id="checkboxTable">
			<tr>
				<!-- <th>员工账号</th>
				<th>用户ID</th>
				<th>操作描述</th>
				<th>操作时间</th>
				<th>操作人IP</th>
				<th>请求URL</th> -->
				<th>员工账号</th>
				<th>员工号</th>
				<th>员工姓名</th>
				<th>操作时间</th>
				<th>操作人IP</th>
				<th>请求URL</th>
				<th>操作描述</th>
			</tr>
			<c:if test="${fn:length(list) == 0}">
				<tr>
					<td colspan="9" align="center">没有找到符合条件的记录</td>
				</tr>
			</c:if>
			<s:iterator value="list" status="st" var="cell">
				<tr>
					<td>${cell.userAccount }</td>
					<td>${cell.adminId }</td>
					<td>${cell.userAccount }</td>
					<td>${cell.time }</td>
					<td>${cell.ip }</td>
					<td>${cell.url }</td>
					<td>${cell.content }</td>
				</tr>
			</s:iterator>
		</table>
		</form>
		</div>
</body>
</html>