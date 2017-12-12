<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息列表</title>
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
	$(".img_add2").click(function(){
		
		var height = $(document).height();
		window.parent.iframeHeight('querytable');
	}) ;
});
</script>
</head>
<body>
<!-- <div id="scrollContent" class="border_gray"> -->
<form action="${ctx}/admin/equipment!queryRecordList.action" method="post" target="querytable" id="frm">
<input type="hidden" name="totalCount" id="totalCount" value="${totalCount }" />
<input type="hidden" name="totalPage" id="totalPage" value="${totalPage }" />
<input type="hidden" name="currPage" id="currPage" value="${currPage }" />
<table class="tableStyle"  style="text-align: center">
		<tbody>
		<tr>
			<th class="th">上机时间</th>
			<th class="th">状态</th>
			<th class="th">累计加工数</th>
			<th class="th">工作分钟</th>
			<th class="th">工作小时</th>
			<th class="th">设备上电时间</th>
		</tr>
		<c:if test="${fn:length(list) == 0}">
			<tr>
				<td  colspan="6"  align="center">没有找到符合条件的记录</td>
			</tr>
		</c:if>
		<s:iterator value="list" status="st" var="cell">
		<tr>
			<td>${cell.receive_time }</td>
			<td>
				<c:if test="${cell.bootTime>0 }">
					开机
				</c:if>
				<c:if test="${cell.bootTime=='0.0' }">
					关机
				</c:if>
			</td>
			<td>${cell.number }</td>
			<td>${cell.workMinute }</td>
			<td>${cell.workHour}</td>
			<td>${cell.bootTime}</td>
		</tr>
		</s:iterator>
	</tbody> 
</table>
</form>
</body>
</html>