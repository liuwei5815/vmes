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
<style>
td,th{
 text-align: center;
}
</style>
<script>
    function chooseinfo(e){
    	var id=$(e).find("td:eq(0) input:eq(0)").val();
    	var nameCn=$(e).find("td:eq(0) input:eq(1)").val();
    	var name=$(e).find("td:eq(0) input:eq(2)").val();
    	parent.frmright.setTableInfo(id,nameCn,name);
    	top.Dialog.close();
    }
</script>
</head>
<body>
<div id="scrollContent" class="border_gray">
<form method="post">
<table class="tableStyle" id="checkboxTable">
	<tr>
		<th width="30">序号</th>
		<th>表名(表中文名)</th>
		<th>使用状态</th>
		<th>创建时间</th>
		<th>修改时间</th>
	</tr>
	<c:if test="${fn:length(list) == 0}">
		<tr>
			<td colspan="5" align="center">没有找到符合条件的记录</td>
		</tr>
	</c:if>
	<s:iterator value="list" status="st" var="cell">
		<tr title="双击选择" ondblclick="chooseinfo(this)">
			<td>${st.index+1}
				<input type="hidden" value="${cell.id}"/>
				<input type="hidden" value="${cell.nameCn}"/>
				<input type="hidden" value="${cell.name}"/>
			</td>
			<td>${cell.name }(${cell.nameCn })</td>
			<td>
			    <s:if test="#cell.status==1">正常</s:if>
			    <s:else>停用</s:else>
			</td>
			<td><s:date name="addDate" format="yyyy-MM-dd HH:mm:ss" /></td>
			<td><s:date name="updateDate" format="yyyy-MM-dd HH:mm:ss" /></td>
		</tr>
	</s:iterator>
</table>
</form>
</div>
</body>
</html>
