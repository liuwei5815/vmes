<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择表</title>
<script type='text/javascript' src="${ctx }/admin/js/page.js"></script>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script>
$(function(){
	loadPage();
});
</script>
</head>
<body>
<input type="hidden" value="${message }">
<input type="hidden" name="totalCount" id="totalCount" value="${totalCount }" />
<input type="hidden" name="totalPage" id="totalPage" value="${totalPage }" />
<input type="hidden" name="currPage" id="currPage" value="${currPage }" />
</input>

<table class="tableStyle" id="checkboxTable">
	<tr>
		<th width="20"></th>
		<th width="30">序号</th>
		<th width="100">${showName }</th>
	</tr>
	<c:if test="${fn:length(list) == 0}">
		<tr>
			<td colspan="3" align="center">没有找到符合条件的记录</td>
		</tr>
	</c:if>
	<s:iterator value="list" status="st" var="cell">
		 <tr>
		 	<td><input type="checkbox"   lable="${cell.showName }"  value="${pkName }"  name="pkValue" /></td>
			<td>${st.index+1 }</td>
			
			<td>${cell.showName }</td>
		</tr>
	</s:iterator>
</table>

</body>
</html>





