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
<title>设备运行数据报表</title>
<script >

$(function() {
	/* $("#departmentId").val(0); */
	/* emp.loadEmps(); */


});

var emp = {
	loadEmps : function() {
		$("#frm").submit();
	}
};




function doSubmit(){
	var startDate= new Date($("#beginDate").val());
	if(startDate == "Invalid Date"){
		top.Dialog.alert("开始时间不能为空");
		return;
	}
	/* var endDate=new Date($("#endDate").val());
	if(endDate == "Invalid Date"){
		top.Dialog.alert("结束时间不能为空");
		return;
	} */
	/* if(startDate.getTime()>endDate.getTime()){
		top.Dialog.alert("开始时间不能早于结束时间");
		return;
	} */
	$("#frm").submit();	
}

function qk(){
	$("#departmentId").val("");
	$("#eqName").val("");
	$("#deptName").val("");
	$("#eqUserCode").val("");
	$("#eqCode").val("");
	
}



function yxSearch(){
	/* var startDate= new Date($("#beginDate").val());
	var endDate=new Date($("#endDate").val());
	if(startDate.getTime()>endDate.getTime()){
		top.Dialog.alert("开始时间不能早于结束时间");
		return;
	} */
    var dia = new top.Dialog();
 	dia.Title = "设备运行数据";
	//饼状图
		dia.URL = "${ctx}/admin/jsp/system/equipment/operationInformation/oper_dialog.jsp";
		dia.ID = "sbyxlyl";
		dia.CancelButtonText = "&nbsp;关 &nbsp;&nbsp;&nbsp;&nbsp;闭&nbsp;";
		dia.Height = 500;
		dia.Width = 600;
		dia.CancelEvent = function() {
			dia.close();
		};
		dia.show();
}


function queryEquipType(){
	var diag = new top.Dialog();
	diag.ID="choose";
	diag.Title = "选择设备类型";
	diag.URL = "${ctx}/admin/equipment!queryEquipmentType.action";
	diag.Height=500;
	diag.Width=600;
	diag.OKEvent=function(){
		var treenode= diag.innerFrame.contentWindow.getTreeNode();
		if(!treenode){
			top.Dialog.alert("最多选择一项");	
			return false;
		}
		$("#eqType").val(treenode.id);
		$("#equipType").val(treenode.text);
		diag.close();
		};
	diag.show();
}

function queryDepartment(){
	var diag = new top.Dialog();
	diag.ID="choose";
	diag.Title = "选择部门";
	diag.URL = "${ctx}/admin/equipment!queryDepartment.action";
	diag.Height=500;
	diag.Width=600;
	diag.OKEvent=function(){
		var treenode= diag.innerFrame.contentWindow.getTreeNode();
		if(!treenode){
			top.Dialog.alert("最多选择一项");	
			return false;
		}
		if(treenode.parent=='#'){
			$("#deptId").val("0");
			$("#departmentId").val(treenode.text);
		}else{
			$("#deptId").val(treenode.id);
			$("#departmentId").val(treenode.text);
		}
		diag.close();
		};
	diag.show();
}
</script>

</head>
<body>
<div class="position">
	<div class="center">
		<div class="left">
			<div class="right"><span>当前位置：设备状态管理 >>设备运行数据报表</span></div>
		</div>
	</div>
</div>
<div class="box2" panelTitle="设备基本信息" roller="false">
 <form method="post" target="querytable" action="admin/equipment!operInfor_query.action" id="frm">
 	<input type="hidden" id="employeeId" value="" name="employeeId"/>
 	<!-- <input type="hidden" id="departmentId" value="0" name="departmentId"/> -->
 	<input type="hidden" name="currPage" id="currPage" value="${currpage}" /> 
 	<input type="hidden" name="perPageRows" id="perPageRows" value="${perPageRows}" />
 	<table>
		<tr>
			<td>设备类型：<input readonly="readonly"  style="width:120px;" name="equipType" type="text" id="equipType" class="validate[length[0,15]]" onclick="return queryEquipType()"/></td>
			<input type="hidden" name="eqType" id="eqType" value="" />
			<td>系统设备编号：<input style="width:120px;" name="eqCode" type="text" id="eqCode" class="validate[length[0,15]]" /></td>
			<td>所属部门：<input style="width:120px;" readonly="readonly" name="departmentId" type="text" id="departmentId" class="validate[length[0,15]]" onclick="return queryDepartment()"/></td>
			<input type="hidden" name="deptId" id="deptId" value="0" />
		</tr>
	</table>
	<table>
		<tr> 
			<td>设备名称：<input style="width:120px;" name="eqName" type="text" id="eqName" class="validate[length[0,15]]"/></td>
			<td>用户设备编号：<input style="width:120px;" name="eqUserCode" type="text"  id="eqUserCode" class="validate[length[0,15]]"/></td>
			
			<td>
				时间筛选：<input class="date" name="beginDate" id="beginDate" value="${beginDate }" type="text" />
				<%-- 至
				<input class="date" name="endDate" id="endDate" value="${endDate } " type="text" /> --%>
			</td>
			<td>
				<button style="margin-top: 5px;" type="button" onclick="return doSubmit();"><span class="icon_find">查询</span></button>&nbsp;&nbsp;
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
			<td style="width: 100%;" valign="top">
				<div style="overflow: auto;height: 100%;">
				<iframe scrolling="no" width="100%" frameBorder=0 id="querytable"
						name="querytable"
						src="${ctx}/admin/equipment!operInfor_query.action"
						onload="iframeHeight('querytable')" allowTransparency="true">
				</iframe>
				</div>
			</td>
		</tr>
	</table>
</div>
<pt:page action="${ctx}/admin/equipment!operInfor_query.action" target="querytable"></pt:page>
</body>
</html>





