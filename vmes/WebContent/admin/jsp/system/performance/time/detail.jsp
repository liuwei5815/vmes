<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
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
</head>
<body>
<div class="static_box1">
<div class="box1_topcenter2">
<div class="box1_topleft2">
<div class="box1_topright2"></div>
</div>
</div>
<div class="box1_middlecenter">
<div class="box1_middleleft2">
<div class="box1_middleright2">
<div style="padding: 0 20px 0 20px;">
<table class="tableStyle" id="checkboxTable">
	<tr>
		<th>派工单编号</th>
		<th>员工</th>
		<th>开始时间</th>
		<th>计划数量</th>
		<th>实际合格数</th>
		<th>不合格数</th>
		<th>报工时间</th>
		<th>状态</th>
	</tr>
	<c:if test="${fn:length(list) == 0}">
		<tr>
			<td colspan="12" align="center">没有找到符合条件的记录</td>
		</tr>
	</c:if>
	<s:iterator value="list" status="st" var="cell">
		 <tr>
			 <td><span class="hand underLine todoCode" onclick="return viewtodoClaim('${cell.todoId}');">${cell.detailCode}</span></td>
			 <td>${cell.name}</td>
			 <td><s:date name="#cell.startTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			 <td>${cell.planNum}</td>
			 <td>${cell.qualifiedNum}</td>
			 <td>${cell.disqualifiedNum}</td>
			 <td><s:date name="#cell.finishTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			 <td><s:property value="@com.xy.cms.common.CacheFun@getCodeText('claim_state',#cell.state)" /></td>
		</tr>
	</s:iterator>
</table>
</div>
</div>
</div>
</div>
<div class="box1_bottomcenter2">
<div class="box1_bottomleft2">
<div class="box1_bottomright2"></div>
</div>
</div>
</div>
<script>
function viewtodoClaim(id) {
	var diag = new top.Dialog();
	diag.Title = "派工单详情";
	diag.URL = "${ctx}/admin/dispatch!claim.action?todoId="+id;
	diag.Height = 400;
	diag.Width = 600;
	diag.show();
}
</script>
</body>
</html>