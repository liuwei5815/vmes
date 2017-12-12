<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctxAdmin}/js/jquery.tmpl.min.js"></script>
<script type="application/javascript" src="${ctxAdmin}/js/jstree/jstree.js?v=4.0"></script>
<link rel="stylesheet" href="${ctxAdmin}/js/jstree/themes/default/style.min.css" />
<script type="application/javascript" src="${ctxAdmin}/js/jstree/overmenu/jstree.plugin.overmenu.min.js"></script>
<link rel="stylesheet" href="${ctxAdmin}/js/jstree/overmenu/overmenu.min.css" />
<script src="${ctxAdmin}/js/third-party/highcharts/highcharts.js"></script>
<title>数据表</title>
<style>
#scrollContent {
	line-height: 100%;
	position: relative;
	z-index: 0;
}
</style>

</head>
<body>
<div class="padding5">
		<form method="post" target="querytable" action="${ctx}/admin/equipment!oper_dialog.action" id="frm">
		<input type="hidden" id="equmentId" value=${equmentId }  />	
			<table>
				<tr>
					<td>日期：</td>
					<td>
						<input class="date" name="date" id="date" value="${date }" type="text" />
					</td>
					<td>
						<button type="submit" ><span class="icon_find">查询</span></button>
					</td>
				</tr>
			</table>
		</form>
</div>
<div id="scrollContentS">
	<iframe scrolling="no" width="100%" frameBorder=0 id="querytable"
		name="querytable" src="${ctx}/admin/equipment!oper_dialog.action"
		onload="iframeHeight('querytable')" allowTransparency="true"></iframe>
</div>
</body>


</html>





