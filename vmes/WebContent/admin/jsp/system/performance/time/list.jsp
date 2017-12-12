<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新闻信息列表</title>
<%@ include file="/admin/jsp/common/common.jsp"%>
<script type='text/javascript' src="${ctx }/admin/js/page.js"></script>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
.tableStyle tr th,td{
 height:24px;
 text-align: center;
 white-space: nowrap;
 word-wrap: break-word;
 overflow: hidden;
}
</style>
</head>
<body>
<div id="scrollContent" class="border_gray">
 <form action="${ctx}/admin/performanceAction!queryEmployeeTime.action" method="post" target="querytable" id="frm">
<input type="hidden" name="totalCount" id="totalCount" value="${totalCount }" />
<input type="hidden" name="totalPage" id="totalPage" value="${totalPage }" />
<input type="hidden" name="currPage" id="currPage" value="${currPage }" />
<input type="hidden" name="departmentId" id="departmentId" value="${departmentId }" />
<input type="hidden" name="empId" id="empId" value="${empId}" />
<table class="tableStyle" id="checkboxTable">
	<tr>
		<th width="30"></th>
		<th>部门名称</th>
		<th>员工号</th>
		<th>员工姓名</th>
		<th>日期</th>
		<th>当日有效工作时间</th>
	<!-- 	<th width="30">报工时间</th>  -->
		<th>有效时间占比</th>
		<th>详情</th>
	</tr>
	<c:if test="${fn:length(list) == 0}">
		<tr>
			<td colspan="8" align="center">没有找到符合条件的记录</td>
		</tr>
	</c:if>
	<s:iterator value="list" status="st" var="cell">
		 <tr>
		 <td><input id="pId" type="checkbox" value="${cell.empCode }"/></td>
			 <td>${cell.deptName}</td>
			 <td>${cell.empCode}</td>
			 <td>${cell.empName}</td>
			 <td>${cell.finishtime}</td>
			 <td>
			 	<fmt:formatNumber type="number" value="${cell.workTime/60}" maxFractionDigits="0"/>小时
			 	<fmt:formatNumber type="number" value="${cell.workTime%60}" maxFractionDigits="0"/>分钟 
			 </td>
			 <td>${cell.quatime}%</td>
			 <td width="7%" ><button onclick="return detail('${cell.empCode }','${cell.finishtime }');"><span class="icon_find">详情</span></button></td>
		</tr>
	</s:iterator>
</table>
</form>
</div>
<script>
function unload(){
    if(document.frm.successflag.value == "1"){//执行成功
    	top.Dialog.alert("保存成功",function(){
    		cancel();
    	});
    	this.parent.frmright.window.location.href="${ctx}/admin/departmentAction!init.action";	
	}else{
		if(document.forms[0].message.value !=""){
			top.Dialog.alert(document.forms[0].message.value);
	    	document.forms[0].message.value = "";	   
	    }
	}
}
$(document).ready(function(){
	loadPage();
	unload();
});

/*
 * 单个或多个人员统计
 */
function empSearch(){
	//验证是否选中
	var sl=$("input[type='checkbox']:checked").size();
    
    if(sl == 0){
	    top.Dialog.alert("请先选择一个人员");
	    return false;
    } else if (sl > 10) {
    	top.Dialog.alert("最多选择十个人员");
    	return false;
    }
    var idSet = new Set();
    $("#checkboxTable").find("input[type='checkbox']:checked").each(function(i){
	   idSet.add($(this).val());
	});
    console.log("idSet = " + idSet);
    var idArray = Array.from(idSet);
    console.log("idArray = " + idArray);
    var workTime = $("#workTime", parent.document).val();
    var claimStart = $("#claimStart", parent.document).val();
    var claimEnd = $("#claimEnd", parent.document).val();
    var diag = new top.Dialog();
	diag.Title = "人员有效工作时间占比";
	diag.URL = "performanceAction!queryEmpTime.action?empCode="+idArray+"&workTime="+workTime+"&claimStart="+claimStart+"&claimEnd="+claimEnd;
	diag.Height = 600;
	diag.Width = 800;
	diag.show();
	return false;
}

function detail(empCode, finishtime) {
	var diag = new top.Dialog();
	diag.Title = "派工单详情";
	diag.URL = "performanceAction!queryOrderDetail.action?empCode="+empCode+"&finishtime="+finishtime;
	diag.Height = 600;
	diag.Width = 800;
	diag.show();
	return false;
}


/* function empsSearch() {
	//验证是否选中
	var sl=$("input[type='checkbox']:checked").size();
    if(sl==0){
	    top.Dialog.alert("请先选择人员");
	    return false;
    }
    var  id=[];
    $("#checkboxTable").find("input[type='checkbox']:checked").each(function(i){
	   id.push($(this).val());
	});
    var workTime = $("#workTime", parent.document).val();
	var allTime=$("#allTime", parent.document).val();
	var diag = new top.Dialog();
	diag.Title = "多个人员有效工作时间占比";
	diag.URL = "performanceAction!queryAllEmpTime.action?empIds="+id+"&workTime="+workTime+"&allTime="+allTime;
	diag.Height=500;
	diag.Width=950;
	diag.show();
	return false;
} */
</script>
</body>
</html>