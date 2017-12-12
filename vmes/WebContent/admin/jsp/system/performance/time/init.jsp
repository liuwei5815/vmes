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
			<div class="right"><span>当前位置：人员绩效管理 >>人员有效工作时间占比</span></div>
		</div>
	</div>
</div>
<div class="box2" panelTitle="人员有效工作时间占比" roller="false">
 <form action="${ctx}/admin/performanceAction!queryEmployeeTime.action" method="post" target="querytable" id="frm">
 	<input type="hidden" id="employeeId" value="" name="employeeId"/>
 	<input type="hidden" id="departmentId" value="0" name="departmentId"/>
 	<input type="hidden" id="orgtree" value='${orgtree }'/>
 	<input type="hidden" name="currPage" id="currPage" value="${currpage}" /> 
 	<input type="hidden" name="perPageRows" id="perPageRows" value="${perPageRows}" />
 	<input type="hidden" name="Parentid" id="Parentid" value="${Parentid}" />
 	<table>
			<tr>
				<!-- <td>员工号：</td>
				<td><input type="text" name="empCode" /></td> -->
				<td>标准工作时间：</td>
				<td>
					<select id="workTime" name="workTime" style="width: 122px;font-size: 12px;border-color: #cccccc;border-style: solid;border-width: 1px;color: #336699;height: 24px;line-height: 20px;">
						 <option value="8">8小时</option>
					       <option value="1">1小时</option>
					       <option value="2">2小时</option>
					       <option value="3">3小时</option>
					       <option value="4">4小时</option>
					       <option value="5">5小时</option>
					       <option value="6">6小时</option>
					       <option value="7">7小时</option>
					       <option value="9">9小时</option>
					       <option value="10">10小时</option>
					       <option value="11">11小时</option>
					       <option value="12">12小时</option>
					       <option value="13">13小时</option>
					       <option value="14">14小时</option>
					       <option value="15">15小时</option>
					       <option value="16">16小时</option>
					        <option value="17">17小时</option>
					       <option value="18">18小时</option>
					       <option value="19">19小时</option>
					       <option value="20">20小时</option>
					       <option value="21">21小时</option>
					       <option value="22">22小时</option>
					       <option value="23">23小时</option>
					       <option value="24">24小时</option>
					</select>
				</td>
				<td>员工号：</td>
				<td><input style="width:120px;" name="empCode" type="text" id="empCode" class="validate[length[0,15]]"/></td>
				<td>员工姓名：</td>
				<td><input style="width:120px;" name="empName" type="text" id="empName" class="validate[length[0,15]]"/></td>
				<td>时间段：</td>
				<td>
					<input class="date" id="claimStart" name="claimStart"  type="text" value="${startTime}"/>
						至
					<input class="date" id="claimEnd" name="claimEnd"  type="text" value="${endTime}"/>
				</td>
				<td>
					<button onclick="return doSubmit()"><span class="icon_find">查询</span></button>
					<button type="button" onclick="return queryEmpTime()"><span class="icon_find">趋势图</span></button>
				</td>
				<%-- <td colspan="4">
					<button type="submit" ><span class="icon_find">查询</span></button>&nbsp;&nbsp;
					<button type="button" onclick="return queryEmpTask()"><span class="icon_find">单个人员统计</span></button>
				</td>
				<td>固定时间：</td>
				<td><input type="text" id="allTime" name="allTime" class="date" value="${allTime }"/></td>
				<td><button type="button" onclick="return queryAllEmpTask()"><span class="icon_find">多个人员统计</span></button></td> --%>
			</tr>
		</table>
 </form>
</div>
<div id="scrollContent"><table style="width: 100%;">
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
						src="${ctx}/admin/performanceAction!queryEmployeeTime.action"
						onload="iframeHeight('querytable')" allowTransparency="true">
				</iframe>
				</div>
			</td>
		</tr>
	</table>
</div>
<pt:page action="${ctx}/admin/performanceAction!queryEmployeeTime.action" target="querytable"></pt:page>
<script>
/**
 * 定义右键菜单,根据不同的节点类型有选择性的去掉部分菜单
 */
var ctxmenus = function(o, cb) {
	var items = {
		"rename" : null,
		"ccp" : null,
	};
	if (this.get_parent(o) == "#") {// root节点
		delete items.up;
		delete items.down;
	} 
	return items;
};

$(function() {
	$("#departmentId").val(0);
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

function doSubmit(){
	var claimStart = $('#claimStart').val();
    var claimEnd = $('#claimEnd').val();
    if (moment(claimEnd).isBefore(claimStart)) {
        top.Dialog.alert("开始时间不能早于结束时间!");
        return false;
	}
    var newStart = moment(claimStart).add(1, 'months');
    var newEnd = moment(claimEnd);
    if (newStart < newEnd) {
    	top.Dialog.alert("起止时间不能超过一个月!");
    	return false;
    }
	$("#frm").submit();	
}

function queryEmpTime() {
	document.querytable.empSearch();
}

$(function() {
	doSubmit();
});

/* function queryEmpTask(){
	document.querytable.empSearch();
}

function queryAllEmpTask(){
	document.querytable.empsSearch();	
} */
</script>
</body>
</html>





