<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>数据表</title>
<script>
$(function(){
	winScrollContent("choose");
})
</script>
</head>
<body>
<div class="padding5">

	<input type="hidden" value='${ship.type }' id="type"></input>
	<form id="frm" method="post" target="querytable" action="admin/shipAction!query.action">
	<input type="hidden" name="currPage" id="currPage" value="${currpage}" /> 
	<input type="hidden" name="perPageRows" id="perPageRows" value="${perPageRows}" />
	<s:hidden  name="shipId"></s:hidden>
	<table>
		<tr>
			<td>${showColumn.nameCn }：</td>
			<td><input type="text" name="showName" /></td>
			<td><button><span class="icon_find">查询</span></button></td>
		</tr>
	</table>
	</form>
</div>
<div id="winScrollContent">
	<iframe scrolling="no" width="100%"
	frameBorder=0 id="querytable" name="querytable"
	src="${ctx}/admin/shipAction!query.action?shipId=${shipId}&pkId=${pkId}&pkVal=${pkVal}"
	onload="iframeHeight('querytable')" allowTransparency="true"></iframe>
</div>

<pt:page action="${ctx}/admin/shipAction!query.action" target="querytable"></pt:page>



</body>
</html>





