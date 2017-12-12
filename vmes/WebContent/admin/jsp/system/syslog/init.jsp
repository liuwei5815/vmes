<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>日志</title>
<script>
function doQueryTab(){
	$("#frm").submit();
}

$(document).ready(function() {
	document.forms[0].action = "${ctx}/admin/sys_log!queryLog.action";
});
</script>
</head>
<body>
	<div class="position">
		<div class="center">
			<div class="left">
				<div class="right">
					<span>当前位置：系统信息管理 >>系统日志管理 </span>
				</div>
			</div>
		</div>
	</div>
	<div class="box2" panelTitle="日志查看" roller="false">
		<s:form id="frm" method="post" target="querytable" action="admin/sys_log!queryLog.action">
		
			<input type="hidden" name="currPage" id="currPage"
				value="${currpage}" />
			<input type="hidden" name="perPageRows" id="perPageRows"
				value="${perPageRows}" />
			<table>
		 			<td nowrap="nowrap">员工账号：</td>
        			<td><input id="name" name="sysLog.userAccount" value="${sysLog.userAccount }" class="validate[length[0,15]]"/></td>
					<td nowrap="nowrap">操作时间：</td>
					<td nowrap="nowrap"><input class="date" id="beginDate"
						name="beginDate" /></td>
					<td align="center">到</td>
					<td><input class="date" id="endDate" name="endDate" /></td>
					<td><button onclick='return doQueryTab();' type="button">
							<span class="icon_find">查询</span>
						</button></td>
				</tr>
			</table>
		</s:form>
	</div>
	<div id="scrollContent">
		<iframe scrolling="no" width="100%" frameBorder=0 id="querytable"
			name="querytable" src="${ctx}/admin/sys_log!queryLog.action"
			onload="iframeHeight('querytable')" allowTransparency="true"></iframe>
	</div>
	<pt:page action="${ctx}/admin/sys_log!queryLog.action"	target="querytable"></pt:page>
</body>
</html>