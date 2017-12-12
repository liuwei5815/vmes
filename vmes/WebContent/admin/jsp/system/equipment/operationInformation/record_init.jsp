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
<title>设备24小时运行数据</title>
<style>

</style>
<script >
function noWarning(){
	$("#type").val("1");
	$("#frm").submit();
}

function warning(){
	$("#type").val("2");
	$("#frm").submit();
}
</script>
</head>
<body>
<div class="simpleTab" iframeMode="true" style="height:380px;">
<form method="post" target="querytable" action="${ctx}/admin/equipment!queryRecordList.action" id="frm">
	<input type="hidden" id="type" name="type" value="${type }"  />		
	<input type="hidden" name="totalCount" id="totalCount" value="${totalCount }" />
	<input type="hidden" name="totalPage" id="totalPage" value="${totalPage }" />
	<input type="hidden" name="currPage" id="currPage" value="${currPage }" />
	<ul class="simpleTab_top">
		<li class="current" ><a onclick="noWarning()" href="javascript:void(0)" target="querytable"><span>24小时数据</span></a></li>
		<li ><a onclick="warning()" href="javascript:void(0)" target="querytable"><span>警报数据</span></a></li>
	</ul>
	<div class="scrollContent">
		<iframe scrolling="no" width="100%" frameBorder=0 id="querytable"
		name="querytable" src="${ctx}/admin/equipment!queryRecordList.action"
		onload="iframeHeight('querytable')" allowTransparency="true"></iframe>
	</div>
</form>
</div>
<%-- <div class="scrollContent">
		<form method="post" target="querytable" action="${ctx}/admin/equipment!queryRecordList.action" id="frm">
		<input type="hidden" id="equipmentId" name="equipmentId" value="${equipmentId }"  />
		<input type="hidden" id="type" name="type" value="${type }"  />		
		<input type="hidden" name="currPage" id="currPage" value="${currpage}" /> 
 		<input type="hidden" name="perPageRows" id="perPageRows" value="${perPageRows}" />
			<table>
				<tr>
					<td>
						<button type="button" onclick="noWarning()" value="24小时数据"><span class="icon_find">24小时数据</span></button>
					</td>
					<td>
						<button type="button" onclick="warning()" value="警报数据"><span class="icon_find">警报数据</span></button>
					</td>
				</tr>
			</table>
		</form>
</div>
<div id="scrollContent">
	<iframe scrolling="no" width="100%" frameBorder=0 id="querytable"
		name="querytable" src="${ctx}/admin/equipment!queryRecordList.action"
		onload="iframeHeight('querytable')" allowTransparency="true"></iframe>
</div> --%>
<pt:page action="${ctx}/admin/equipment!queryRecordList.action" target="querytable"></pt:page>
</body>


</html>





