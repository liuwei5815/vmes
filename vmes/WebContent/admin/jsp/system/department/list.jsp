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

function editEmployee(id){
	var diag = new top.Dialog();
	diag.ID="model";
	diag.Title = "编辑员工";
	diag.URL = "${ctx}/admin/departmentAction!preEditEmployee.action?id="+id;
	diag.Width=550;
	diag.Height=700;
	diag.show();
	return false;
}

function delEmployee(id){
	top.Dialog.confirm("确定要删除这个员工？",function(){
		$.ajax({
		     type: "POST",
		     url: "${ctx}/admin/departmentAction!delEmployee.action",
		     cache: false,
		     dataType:"json",
		     data:{"id":id},
		     success: function(data){
	     		if(data.successflag=="1"){
	     			top.Dialog.alert("删除成功",function(){
	    				$("#frm").submit();	
	    	    	});
		     	}
	     		else{
	     			top.Dialog.alert("删除失败");}
		     }
		});	
	});
}

$(document).ready(function(){ 
	loadPage();
	unload();
});

</script>
</head>
<body>
<div id="scrollContent" class="border_gray">
 <form action="${ctx}/admin/departmentAction!queryEmployee.action" method="post" target="querytable" id="frm">
<input type="hidden" name="totalCount" id="totalCount" value="${totalCount }" />
<input type="hidden" name="totalPage" id="totalPage" value="${totalPage }" />
<input type="hidden" name="currPage" id="currPage" value="${currPage }" />
<input type="hidden" name="departmentId" id="departmentId" value="${departmentId }" />
<table class="tableStyle" id="checkboxTable">
	<tr>
		<th width="20"></th>
		<th width="30">员工号</th>
		<th width="30">姓名</th>
		<th width="30">性别</th>
		<th width="30">出生年月</th>
		<th width="30">手机号码</th>
		<th width="30">职位</th>
		<th width="30">部门</th>
		<th width="30">身份证</th>
		<th width="30">最后修改时间</th><!-- 2017/8/22 -->
		<th width="30">操作</th>
	</tr>
	<c:if test="${fn:length(list) == 0}">
		<tr>
			<td colspan="12" align="center">没有找到符合条件的记录</td>
		</tr>
	</c:if>
	<s:iterator value="list" status="st" var="cell">
		 <tr>
		 	<td><input type="checkbox" value="${cell[0].id }" name="checks" /></td>
		 	<td>${cell[0].serialNo}</td>
			<td>${cell[0].name }</td>
			<td>
				<%-- <s:if test="#request.cell[0].gender=='1'">男</s:if>
				<s:else>女</s:else> --%>
				<s:if test="#request.cell[0].gender==1">
					男
				</s:if>
				<s:else>
					女
				</s:else>
			</td>
			<td>${cell[0].birthday}</td>
			<td>${cell[0].phoneNum}</td>
			<td>${cell[0].jobtitle}</td>
			<td>${cell[1].name}</td>
			<td>${cell[0].idcard}</td>
			<td><fmt:formatDate value="${cell[0].updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
			<td width="30">
				 <span class="img_edit hand" title="编辑" onclick="editEmployee('${cell[0].id}');"></span>
				 <span class="img_delete hand" title="删除" onclick="delEmployee('${cell[0].id}');"></span>
			</td>
		</tr>
	</s:iterator>
</table>
</form>
</div>
</body>
</html>