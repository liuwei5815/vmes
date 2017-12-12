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
<title>数据表</title>
</head>
<body>
<div class="position">
	<div class="center">
		<div class="left">
			<div class="right"><span>当前位置：设备状态管理 >>设备其他数据表</span></div>
		</div>
	</div>
</div>
<div class="box2" panelTitle="设备基本信息" roller="false">
 <form method="post" target="querytable" action="admin/equipment!otherInfor_query.action" id="frm">
 	<input type="hidden" id="employeeId" value="" name="employeeId"/>
 	<input type="hidden" id="departmentId" value="0" name="departmentId"/>
 	<input type="hidden" id="orgtree" value='${orgtree }'/>
 	<input type="hidden" name="currPage" id="currPage" value="${currpage}" /> 
 	<input type="hidden" name="perPageRows" id="perPageRows" value="${perPageRows}" />
 	<input type="hidden" name="Parentid" id="Parentid" value="${Parentid}" />
 	<input type="hidden" name="deptId" id="deptId" value="${deptId}" />
 	<table>
		<tr>
			<td>设备类型：<input style="width:120px;" name="equipType" type="text" id="equipType" class="validate[length[0,15]]" onclick="return queryEquipType()"/></td>
			<input type="hidden" name="eqType" id="eqType" value="" />
			<td>设备名称：<input style="width:100px;" name="eqName" type="text" id="eqName" class="validate[length[0,15]]"/></td>
			<td>系统设备编号：<input style="width:100px;" name="eqCode" type="text" id="eqCode" class="validate[length[0,15]]"/></td>
			<td>用户设备编号：<input style="width:100px;" name="eqUserCode" type="text"  id="eqUserCode" class="validate[length[0,15]]"/></td>
	</table>
	<table>		
			<td>
				时间段：
				<input style="width:100px;" class="date" name="beginDate"  type="text" value="${beginDate}"/>
					至
				<input style="width:100px;" class="date" name="endDate"  type="text" value="${endDate}"/>
			</td>
			<td >
				<button type="button" onclick="return doSubmit();"><span class="icon_find">查询</span></button>&nbsp;&nbsp;
				<!-- <button type="button" onclick="qk();"><span class="icon_reload">清空</span></button> -->
			</td>
		</tr>
	</table>
 </form>
</div>
<div id="scrollContent">
	<table style="width: 100%;">
		<tr>
			<!-- <td style="width: 20%" valign="top">
				<div style="width: 100%; overflow: auto; height: 100%;">
					<div id="jstree_div"></div>
				</div>
			</td> -->
			<td style="width: 80%;" valign="top">
				<div id="scrollContent">
		        <iframe scrolling="no" width="100%" frameBorder=0 id="querytable"  name="querytable" src="${ctx}/admin/equipment!otherInfor_query.action"
			       onload="iframeHeight('querytable')" allowTransparency="true"></iframe>
	            </div>
			</td>
		</tr>
	</table>
</div>
<pt:page action="${ctx}/admin/equipment!otherInfor_query.action" target="querytable"></pt:page>
</body>
<script >
var emp = {
	loadEmps : function() {
		$("#frm").submit();
	}
};

function doSubmit(){
	$("#frm").submit();	
}
function queryEquipType(){
	var diag = new top.Dialog();
	diag.ID="choose";
	diag.Title = "选择设备类型";
	diag.URL = "${ctx}/admin/equipment!queryEquipmentType.action";
	diag.Height=500;
	diag.Width=300;
	diag.OKEvent=function(){
		var treenode= diag.innerFrame.contentWindow.getTreeNode();
		if(!treenode){
			top.Dialog.alert("请至少选择一项");	
			return false;
		}

	
			$("#eqType").val(treenode.text);
			$("#equipType").val(treenode.text);
			diag.close();
		};
	diag.show();
}
</script>
</html>





