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
			<div class="right"><span>当前位置：人员绩效管理 >>人员生产合格率</span></div>
		</div>
	</div>
</div>
<div class="box2" panelTitle="人员生产合格率" roller="false">
 <form action="${ctx}/admin/qualityAction!queryEmployee.action" method="post" target="querytable" id="frm">
 	<input type="hidden" id="employeeId" value="" name="employeeId"/>
 	<input type="hidden" id="departmentId" value="0" name="departmentId"/>
 	<input type="hidden" id="orgtree" value='${orgtree }'/>
 	<input type="hidden" name="currPage" id="currPage" value="${currpage}" /> 
 	<input type="hidden" name="perPageRows" id="perPageRows" value="${perPageRows}" />
 	<input type="hidden" name="Parentid" id="Parentid" value="${Parentid}" />
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
					
					<button type="button" onclick="return searchEmp()"><span class="icon_find">生成任务合格率（饼图）</span></button>
					<button type="button" onclick="return histogramEmp()"><span class="icon_find">生产任务合格率（趋势图）</span></button>
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
						src="${ctx}/admin/qualityAction!queryEmployee.action"
						onload="iframeHeight('querytable')" allowTransparency="true">
				</iframe>
				</div>
			</td>
		</tr>
	</table>
</div>
<pt:page action="${ctx}/admin/qualityAction!queryEmployee.action" target="querytable"></pt:page>
</body>
<script >
	/**
	 * 上移或下移部门
	 * 
	 * @param t  up/down
	 */
	function upOrDownDept(id, t) {
		$.ajax({
		     type: "POST",
		     url: "${ctx}/admin/departmentAction!upOrDownDepartment.action",
		     cache: false,
		     dataType:"json",
		     data : {
					"t" : t,
					"id" : id
			},
		     success: function(data){
	     		if(data.successflag=="1"){
	     			window.location.reload();
		     	}
	     		else{
	     			top.Dialog.alert(data.msg);
	     		}
		     }
		});	
	}
/**
 * 定义右键菜单,根据不同的节点类型有选择性的去掉部分菜单
 */
var ctxmenus = function(o, cb) {
	var items = {
		"rename" : null,
		"ccp" : null,
		"up" : {
			"separator_before" : false,
			"separator_after" : false,
			"label" : "上移",
			"action" : function(data) {
				upOrDownDept(o.id, "up");
			}
		},
		"down" : {
			"separator_before" : true,
			"separator_after" : false,
			"label" : "下移",
			"action" : function(data) {
				upOrDownDept(o.id, "down");
			}
		} 
	};
	if (this.get_parent(o) == "#") {// root节点
		delete items.up;
		delete items.down;
	} 
	return items;
};

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
		"plugins" : [ "wholerow", "themes", "overmenu" ],
		"contextmenu" : {
			"items" : ctxmenus
		}
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
		return;
	}
	var diag = new top.Dialog();
	diag.Title = "部门生产合格率";
	diag.URL = "qualityAction!queryDeptTask.action?deptId="+departmentId+"&claimStart="+$("input[name='claimStart']").val()+"&claimEnd="+$("input[name='claimEnd']").val();
	diag.Height=800;
	diag.Width=950;
	diag.show();
	return false;
}
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
/*
function queryEmpTask(){
	var departmentId = $("#departmentId").val();
	if(departmentId==0){
		top.Dialog.alert("请选择部门");
		return false;
	} 
	var empId =window.frames["querytable"].document.getElementById("empId").value;
	console.log("empId:"+empId);
	if(empId==null||empId==" "){
		top.Dialog.alert("请选择员工");
		return false;
	}
	var diag = new top.Dialog();
	diag.Title = "人员生产合格率";
	diag.URL = "qualityAction!queryEmpTask.action?empId="+empId;
	diag.Height=800;
	diag.Width=950;
	diag.show();
	return false;
}
*/
</script>
</html>





