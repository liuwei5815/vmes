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
function openEditWin(id){
	var diag = new top.Dialog();
	diag.Title = "修改派工单";
	diag.URL = "dispatch!preEdit.action?id="+id;
	diag.Height=320;
	diag.Width=350;
	diag.show();
	return false;
}
function del(obj,id){
	if(id == ""){
		top.Dialog.alert("参数错误");
		return false;
	}
	top.Dialog.confirm("确定要删除这条记录吗？",function(){
		$.ajax({
		     type: "POST",
		     url: "${ctx}/admin/dispatch!del.action",
		     cache: false,
		     dataType:"json",
		     data:{"currentId":id},
		     success: function(data){
	     		if(data.successflag=="1"){
	     			var tr=obj.parentNode.parentNode;  
	     		    var tbody=tr.parentNode;  
	     		    tbody.removeChild(tr);
		     	}
	     		else{
	     			top.Dialog.alert("删除失败");}
		     }
		});	
	});
}

$(document).ready(function(){ 
	loadPage();
});
</script>
</head>
<body>
<div id="scrollContent" class="border_gray">
<input type="hidden" name="totalCount" id="totalCount" value="${totalCount }" />
<input type="hidden" name="totalPage" id="totalPage" value="${totalPage }" />
<input type="hidden" name="currPage" id="currPage" value="${currPage }" />
<form method="post">
<table class="tableStyle" id="checkboxTable">
	<tr>
		<th width="30"> </th>
		<th width="30">序号</th>
		<th width="30">派工单号</th>
		
		<th width="30">生产计划</th>
		
		<th width="30">计划数量</th>
		
		<th width="30">工序名称</th>
		<th width="30">分配给</th>
		<th width="60">二维码</th>
		<th width="30">派工单状态</th>
		<th width="10">操作</th>
	</tr>
	<c:if test="${fn:length(list) == 0}">
		<tr>
			<td  colspan="6"  align="center">没有找到符合条件的记录</td>
		</tr>
	</c:if>
	<s:iterator value="list" status="st" var="cell">
		 <tr>
			<td><input name="pId" type="checkbox" value="${cell[0].id}"></td>
			<td>${st.index+1 }</td>
			<td>${cell[0].code }</td>
			<td>${cell[1].planCode }</td>
			<td>${cell[0].planNum }</td>
			<td>${cell[0].processName }</td>
			<td>${cell[0].assignto }</td><!-- 分配给谁 -->
			<td><a href="/vmesfile/qrcode/${cell[0].code }.png" target="_blank"><img width="40" height="40" src="/vmesfile/qrcode/${cell[0].code }.png" alt="" /> </a></td>
			<td>${cell[0].processName }</td><!--工单状态-->
			<td width="10">
				<%--  <span class="img_view hand" title="查看" onclick="openViewWin('${cell[0]}');"></span> --%>
				 <span class="img_edit hand" title="编辑" onclick="openEditWin(${cell[0].id});"></span>
				 <span class="img_delete hand" title="删除" onclick="del(this,${cell[0].id});"></span>
			</td>
		</tr>
	</s:iterator>
</table>
</form>
</div>
</body>
</html>