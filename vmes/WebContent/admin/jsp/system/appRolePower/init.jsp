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
function openAddWin(){
	var diag = new top.Dialog();
	diag.Title = "添加角色信息";
	diag.URL = "appRolePower!preAdd.action";
	diag.Height=350;
	diag.show();
	return false;
}

function doQuery() {
	$("#frm").submit();
}

$(document).ready(function() {
	document.frm.action = "${ctx}/admin/appRolePower!query.action";
});

</script>
</head>
<body>
<div class="position">
<div class="center">
<div class="left">
<div class="right"><span>当前位置：系统信息管理 >> 终端角色管理 </span></div>
</div>
</div>
</div>
<div class="box2" panelTitle="终端角色管理" roller="false">
<form id="frm" name="frm" method="post" target="querytable" action="admin/appRolePower!query.action">
	<input type="hidden" name="currPage" id="currPage" value="${currpage}" /> 
	<input type="hidden" name="perPageRows" id="perPageRows" value="${perPageRows}" />
	<table>
	<tr>
		<td nowrap="nowrap">角色名称: </td>
       	<td><input  type="text"  id="name" name="appRole.name" class="validate[length[0,15]]"/></td>
       	<td><button onclick='return doQuery()' type="button"><span class="icon_find">查询</span></button></td>
       	<td>
			<button onclick='return openAddWin();' type="button">
				<span class="icon_add">新增</span>
			</button>
		</td>
	</tr>
</table>
</form>
</div>

<div id="scrollContent"><iframe scrolling="no" width="100%"
	frameBorder=0 id="querytable" name="querytable"
	src="${ctx}/admin/appRolePower!query.action"
	onload="iframeHeight('querytable')" allowTransparency="true"></iframe>
</div>
<pt:page action="${ctx}/admin/appRolePower!query.action" target="querytable"></pt:page>
</body>
</html>





