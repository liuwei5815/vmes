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
 <form action="${ctx}/admin/performanceAction!queryEmployee.action" method="post" target="querytable" id="frm">
<input type="hidden" name="totalCount" id="totalCount" value="${totalCount }" />
<input type="hidden" name="totalPage" id="totalPage" value="${totalPage }" />
<input type="hidden" name="currPage" id="currPage" value="${currPage }" />
<input type="hidden" name="departmentId" id="departmentId" value="${departmentId }" />
<input type="hidden" name="empId" id="empId" value="${empId }" />
<table class="tableStyle" id="checkboxTable">
	<tr>
		<th width="20"></th>
		<th width="30">部门名称</th>
		<th width="30">员工号</th>
		<th width="30">员工姓名</th>
		<th width="30">产品名称</th>
		<th width="30">工序名称</th>
		<th width="30">任务名称</th> 
		<th width="30">开始时间</th>
		<th width="30">报工时间</th> 
		<th width="30">计划数量</th><!-- 2017/8/22 -->
		<th width="30">实际合格数</th><!-- 2017/8/22 -->
		<th width="30">生产合格率</th><!-- 2017/8/22 -->
	</tr>
	<c:if test="${fn:length(list) == 0}">
		<tr>
			<td colspan="12" align="center">没有找到符合条件的记录</td>
		</tr>
	</c:if>
	<s:iterator value="list" status="st" var="cell">
		 <tr>
		 	 <td><input id="pId" type="checkbox" name="checkbox" value="${cell.id}" hgl="${hgl }"/></td>
			 <td>${cell.deptName}</td>
			 <td>${cell.empCode}</td>
			 <td>${cell.empName}</td>
			 <td>${cell.productName}</td>
			 <td>${cell.process}</td>
			 <td>${cell.taskname}</td>
			 <td>${cell.add_date}</td>
			 <td>${cell.finishTime}</td>
			 <td>${cell.planSum}</td>
			 <td>${cell.quaSum}</td>
			 <td>${cell.hgl}（%）</td>
		</tr>
	</s:iterator>
</table>
</form>
</div>
</body>
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

//人员任务合格率饼图
function empSearch(claimStart,claimEnd){
	//验证是否选中
	var sl=$("input[type='checkbox']:checked").size(); 
    if(sl==0){
	    top.Dialog.alert("请先选择人员");
	    return false;
    }
    var id=[];
    var hgl=[];
    $("#checkboxTable").find("input[type='checkbox']:checked").each(function(i){
 	   id.push($(this).val());
 	   hgl.push($(this).attr("hgl"));
 	});
    var dia = new top.Dialog();
	dia.Title = "人员生产合格率饼图";
	dia.URL = "${ctx}/admin/qualityAction!queryEmpTask.action?&empId="+id+"&hgl="+hgl;
	dia.Height = 500;
	dia.Width = 600;
	dia.CancelEvent = function() {
		dia.close();
	};
	dia.show();
}


function histogramEmpS(){
	//验证是否选中
	var sl=$("input[type='checkbox']:checked").size();
    if(sl==0){
	    top.Dialog.alert("请先选择人员");
	    return false;
    }
    var  id=[];
    var hgl=[];
    $("#checkboxTable").find("input[type='checkbox']:checked").each(function(i){
    	hgl.push($(this).attr("hgl"));
	   id.push($(this).val());
	});
    var ids =  $.unique(id);
    if(ids.length>=2){
    	top.Dialog.alert("请选择同一个人进行查看");
    	return false;
    }
    console.log(ids)
    var dia = new top.Dialog();
	dia.Title = "人员任务合格率趋势图";
	dia.URL = "${ctx}/admin/qualityAction!queryEmpTaskHistogram.action?&hgl="+hgl;
	dia.Height = 500;
	dia.Width = 600;
	dia.CancelEvent = function() {
		dia.close();
	};
	dia.show();
}
</script>
</html>