<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Insert title here</title>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type='text/javascript' src="${ctx }/admin/js/page.js"></script>
<script>
function editProduct(id){
	var diag = new top.Dialog();
	diag.Title = "编辑产品信息";
	diag.URL = "product!preEdit.action?id="+id;
	diag.Height=400;
	diag.Width=650;
	diag.show();
	return false;
}
function del(id){
	if(id == ""){
		top.Dialog.alert("参数错误");
		return false;
	}
	top.Dialog.confirm("确定要删除该产品信息吗？",function(){
		$.ajax({
		     type: "POST",
		     url: "${ctx}/admin/product!del.action",
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
function doSubmit(){
	$("#frm").submit();	
}
$(document).ready(function(){ 
	loadPage();
	$(".img_add2").click(function(){
		
		var height = $(document).height();
		window.parent.iframeHeight('querytable');
	}) 
});
</script>
</head>
<body>
<!-- <div id="scrollContent" class="border_gray"> -->
<form action="${ctx}/admin/product!query.action" method="post" target="querytable" id="frm">
<input type="hidden" name="totalCount" id="totalCount" value="${totalCount }" />
<input type="hidden" name="totalPage" id="totalPage" value="${totalPage }" />
<input type="hidden" name="currPage" id="currPage" value="${currPage }" />
<table class="tableStyle" id="checkboxTable" style="text-align: center">
		<tr>
 			<th class="th">序号</th> 
			<th class="th">系统产品编号</th>
			<th class="th">用户产品编号</th>
			<th class="th">产品名称</th>
			<th class="th">规格/型号</th>
			<!-- <th class="th">产品分类</th> -->
			<th class="th">备注</th>
			<th class="th">操作</th>
		</tr>
		<c:if test="${fn:length(list) == 0}">
			<tr>
				<td  colspan="11"  align="center">没有找到符合条件的记录</td>
			</tr>
		</c:if>
		<s:iterator value="list" status="st" var="cell">
		<tr>
			<td>${st.index+1}</td>
			<td>${cell.productCode }</td>
			<td>${cell.userProductCode}</td>
			<td>${cell.name }</td>
			<td>${cell.typespec }</td>
		<%-- 	<td>${cell.type }</td> --%>
			<td>${cell.dsc }</td>
			<td>
		       <span class="img_edit hand" onclick="editProduct(${cell.id});"></span>
			   <span class="img_delete hand" onclick="del(${cell.id});"></span>
	        </td>
		</tr>
		</s:iterator>
</table>
</div>
<!-- </div> -->
</form>
</body>
</html>