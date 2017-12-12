<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Insert title here</title>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type='text/javascript' src="${ctx }/admin/js/page.js"></script>
<style>
.tableStyle tr th,td{
 height:24px;
 text-align: center;
 white-space: nowrap;
 word-wrap: break-word;
 overflow: hidden;
 text-overflow:ellipsis;
}
</style>

</head>
<body>
<div id="scrollContent" class="border_gray">

<form method="post" id="frm" action="${ctx}/admin/accout_setting!query.action">
<input type="hidden" name="totalCount" id="totalCount" value="${totalCount }" /> 
<input type="hidden" name="totalPage" id="totalPage" value="${totalPage }" /> 
<input type="hidden" name="currPage" id="currPage" value="${currPage }" />
<input type="hidden" name="type" id="type" />
<table class="tableStyle" id="checkboxTable" style="text-align: center">
	<tr>
		<th width="10%">序号</th>
      	<th>账号</th>
      	<th>员工</th>
      	<th width="10%">操作</th>
		<%--
		<th width="30">序号</th>
		<th>账号</th>
		<th>创建时间</th>
		<th width="70">操作</th>
		--%>
	</tr>
	<c:if test="${fn:length(list) == 0}">
		<tr>
			<td colspan="4" align="center">没有找到符合条件的记录</td>
		</tr>
	</c:if>
	<s:iterator value="list" status="st" var="cell">
		<tr>
			<td>${st.index+1 }</td>
			<%-- <td>${cell.customerCode }</td> --%>
			<td>${cell[1]}</td>
			<%-- <td><fmt:formatDate value="${cell[2]}" pattern="yyyy-MM-dd HH:mm:ss"/></td> --%>
			<td>${cell[3] }</td>
			<td width="70">
				<span class="img_edit hand" title="编辑" onclick="openEditWin('${cell[0]}','${cell[3] }');"></span>
				<span class="img_delete hand" title="删除" onclick="del('${cell[0]}');"></span>
			</td>
		</tr>
	</s:iterator>
</table>
</form>
</div>
</body>
<script>
$(document).ready(function() {
	loadPage();
});
function doSubmit(){
	$("#frm").submit();	
}
function openEditWin(id, name) {
	var type = $(window.parent.document).find("#type").val();
	$("#type").val(type);
	var diag = new top.Dialog();
	diag.Title = "修改密码";
	diag.URL = "${ctx}/admin/accout_setting!preEdit.action?id="+id+"&type="+type;
	diag.Height = 230;
	diag.show();
}
function del(id){
	//var type=$("#type").val();
	var type = $(window.parent.document).find("#type").val();
	$("#type").val(type);
	console.log(type);
	if(id == ""){
		top.Dialog.alert("参数错误");
		return false;
	}
	top.Dialog.confirm("确定要删除该信息吗？",function(){
		$.ajax({
		     type: "POST",
		     url: "${ctx}/admin/accout_setting!del.action",
		     cache: false,
		     dataType:"json",
		     data:{"id":id,"type":type},
		     success: function(data){
	     		if(data.successflag=="1"){
	     			top.Dialog.alert("删除成功",function(){
	     				 window.parent.location.reload();	
	     				// $("#frm").submit();
	    	    	});
		     	}
	     		else{
	     			top.Dialog.alert("删除失败");}
		     }
		});	
	});
}
</script>
</html>
