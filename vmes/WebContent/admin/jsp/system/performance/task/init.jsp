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
			<div class="right"><span>当前位置：人员绩效管理 >>人员生产任务完成率</span></div>
		</div>
	</div>
</div>
<div class="box2" panelTitle="人员生产任务完成率" roller="false">
 <form action="${ctx}/admin/taskAction!queryEmployee.action" method="post" target="querytable" id="frm">
 	<input type="hidden" id="empId" value="" name="empId"/>
 	<input type="hidden" id="departmentId" value="0" name="departmentId"/>
 	<input type="hidden" id="orgtree" value='${orgtree }'/>
 	<input type="hidden" name="totalCount" id="totalCount" value="${totalCount }" />
	<input type="hidden" name="totalPage" id="totalPage" value="${totalPage }" />
	<input type="hidden" name="currPage" id="currPage" value="${currPage }" />
 	<table>
			<tr>
				<td>员工号：</td>
				<td><input type="text" name="empCode" class="validate[length[0,15]]"/></td>
				<td>姓名：</td>
				<td><input type="text" name="employeeName" class="validate[length[0,15]]"/></td>
				<td>产品名称：</td>
				<td><input type="text" name="productName" class="validate[length[0,15]]"/></td>
				<td>工序：</td>
				<td><input type="text" name="process" class="validate[length[0,15]]"/></td>
			</tr>
		</table>
		<table>
			<tr>
				<td>时间段：</td>
				<td>
					<input class="date" name="claimStart"  type="text" value="${startTime}"/>
						至
					<input class="date" name="claimEnd"  type="text" value="${endTime}"/>
				</td>
				<td colspan="4">
					<button type="submit" ><span class="icon_find">查询</span></button>
					<button type="button" onclick="return searchEmp()"><span class="icon_find">生成任务完成率（饼图）</span></button>
					<button type="button" onclick="return histogramEmp()"><span class="icon_find">生产任务完成率（趋势图）</span></button>
					<button type="button" onclick="return queryDept()" ><span class="icon_find">按部门统计</span></button>
				</td>
			</tr>
		</table>
 </form>
</div>
<div id="scrollContent">
	<table style="width: 100%;">
		<tr>
			<td style="width: 20%" valign="top">
				<div style="width: 100%; overflow: auto; height: 100%;">
					<div id="jstree_div"></div>
				</div>
			</td>
			<td style="width: 80%;" valign="top">
				<div style="overflow: auto;height: 100%;">
				<iframe scrolling="no" width="100%" frameBorder=0 id="querytable"
						name="querytable"
						src="${ctx}/admin/taskAction!queryEmployee.action"
						onload="iframeHeight('querytable')" allowTransparency="true">
				</iframe>
				</div>
			</td>
		</tr>
	</table>
</div>
<pt:page action="${ctx}/admin/taskAction!queryEmployee.action" target="querytable"></pt:page>
</body>
<script >
$(function() {
	$("#departmentId").val(0);
	/* emp.loadEmps(); */

	$('#jstree_div').jstree({
		"core" : {
			"data" : eval($("#orgtree").val()),
			"themes" : {
				"variant" : "large",// 加大
				"ellipsis" : true
			// 文字多时省略
			},
			"check_callback" : true
		},
		"plugins" : [ "wholerow", "themes"],
	}).on('select_node.jstree', function(event, data) {
		$("#departmentId").val(data.node.id);
		$("#currDeptName").text(data.node.text);
		emp.loadEmps();
	});

});

var emp = {
	loadEmps : function() {
		$("#frm").submit();
	}
};
function searchEmp(){
	var claimStart=new Date($("input[name='claimStart']").val());
	var claimEnd=new Date($("input[name='claimEnd']").val());
	if(claimStart.getTime()>claimEnd.getTime()){
		top.Dialog.alert("开始时间不能早于结束时间");
		return;
	}
	 document.querytable.empSearch($("input[name='claimStart']").val(),$("input[name='claimEnd']").val());
}
function histogramEmp(){
	 document.querytable.histogramEmpS();
}
function doSubmit(){
	$("#frm").submit();	
}
function queryDept(){
	var claimStart=new Date($("input[name='claimStart']").val());
	var claimEnd=new Date($("input[name='claimEnd']").val());
	if(claimStart.getTime()>claimEnd.getTime()){
		top.Dialog.alert("开始时间不能早于结束时间");
		return;
	}
	var departmentId = $("#departmentId").val();
	if(departmentId==0){
		top.Dialog.alert("请选择部门");
		return false;
	}
	console.log(claimStart)
	console.log(claimEnd)
	console.log($("input[name='claimStart']").val())
	console.log($("input[name='claimEnd']").val())
	var diag = new top.Dialog();
	diag.Title = "部门任务完成率";
	diag.URL = "taskAction!queryDeptTask.action?deptId="+departmentId+"&claimStart="+$("input[name='claimStart']").val()+"&claimEnd="+$("input[name='claimEnd']").val();
	diag.Height=800;
	diag.Width=950;
	diag.show();
	return false;
}


/* function queryEmpTask(){
	var empId =window.frames["querytable"].document.getElementById("empId").value;
	console.log("empId:"+empId);
	if(empId==null||empId==" "){
		top.Dialog.alert("请选择员工");
		return false;
	}
	var diag = new top.Dialog();
	diag.Title = "人员任务完成率";
	diag.URL = "taskAction!queryEmpTask.action?deptId="+departmentId+"&empId="+empId;
	diag.Height=500;
	diag.Width=950;
	diag.show();
	return false;
} */
</script>
</html>





