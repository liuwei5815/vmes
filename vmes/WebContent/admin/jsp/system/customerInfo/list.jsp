<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Insert title here</title>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style>
.checkbox {
  position: relative;
  display: inline-block;
}
.checkbox:after, .checkbox:before {
  font-family: FontAwesome;
  -webkit-font-feature-settings: normal;
     -moz-font-feature-settings: normal;
          font-feature-settings: normal;
  -webkit-font-kerning: auto;
     -moz-font-kerning: auto;
          font-kerning: auto;
  -webkit-font-language-override: normal;
     -moz-font-language-override: normal;
          font-language-override: normal;
  font-stretch: normal;
  font-style: normal;
  font-synthesis: weight style;
  font-variant: normal;
  font-weight: normal;
  text-rendering: auto;
}
.checkbox label { 
  width: 55px;
  height: 25px;
  background: #ccc;
  position: relative;
  display: inline-block;
  border-radius: 46px;
  -webkit-transition: 0.4s;
  transition: 0.4s;
}
.checkbox label:after {
  content: '';
  position: absolute;
  width: 25px;
  height: 25px;
  border-radius: 100%;
  left: 0;
  top: -1px;
  z-index: 2;
  background: #fff;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
  -webkit-transition: 0.4s;
  transition: 0.4s;
}
.checkbox input {
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  z-index: 5;
  opacity: 0;
  cursor: pointer;
}   

.checkbox input:hover + label:after {
  box-shadow: 0 2px 15px 0 rgba(0, 0, 0, 0.2), 0 3px 8px 0 rgba(0, 0, 0, 0.15);
}
.checkbox input:checked + label:after {
  left: 30px;
}
 
.model-1 .checkbox input:checked + label {
  background: green;
}
</style>
<script type='text/javascript' src="${ctx }/admin/js/page.js"></script>
<script>

function openEditWin(id){
	if(id == ""){
		top.Dialog.alert("参数错误");
		return false;	
	}
	var diag = new top.Dialog();
	diag.Title = "编辑客户信息";
	diag.URL = "${ctx}/admin/customerInfo!preEdit.action?id=" + id;
	diag.Height=300;
	diag.Width=650;
	diag.show();
}
$(document).ready(function() {
	loadPage();
});
function doSubmit(){
	$("#frm").submit();	
}
function del(id){
	if(id == ""){
		top.Dialog.alert("参数错误");
		return false;
	}
	top.Dialog.confirm("确定要删除该客户信息吗？",function(){
		$.ajax({
		     type: "POST",
		     url: "${ctx}/admin/customerInfo!del.action",
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
//客户激活或者取消激活按钮
function customerActive() {
	//var btn_active = document.getElementById("btn_active");
	if($('#btn_active').is(':checked')) {
		alert("激活");
		//点击激活后后台业务逻辑程序
	} else{
		alert("取消激活");
		//点击取消激活后业务逻辑程序
	}
	
}
</script>
</head>
<body>
<div id="scrollContent" class="border_gray">

<form method="post" id="frm" action="${ctx}/admin/customerInfo!query.action">
<input type="hidden" name="totalCount" id="totalCount" value="${totalCount }" /> 
<input type="hidden" name="totalPage" id="totalPage" value="${totalPage }" /> 
<input type="hidden" name="currPage" id="currPage" value="${currPage }" />
<table class="tableStyle" id="checkboxTable" style="text-align: center">
	<tr>
		<th width="10%">系统客户号</th>
		<th width="15%">用户客户号</th>
		<th width="35%">客户名称</th>
		<th width="20%">客户类型</th>
		<th width="15%">激活状态</th>
		<th width="5%">操作</th>
	</tr>
	<c:if test="${fn:length(list) == 0}">
		<tr>
			<td colspan="4" align="center">没有找到符合条件的记录</td>
		</tr>
	</c:if>

	<s:iterator value="list" status="st" var="cell">
		<tr>
			<td>C0001</td>
			<%-- <td>${cell.customerCode }</td> --%>
			<td>111</td>
			<td>浙江顶智</td>
			<td>化工</td>
			<td>
				<section class="model-1">
				  <div class="checkbox">
				    <input id="btn_active" type="checkbox" onclick="customerActive();"/>
				    <label></label>
				  </div>
				</section>
			</td>
			<td width="70" style = "text-align: center">
				<span class="img_edit hand" title="编辑"	onclick="openEditWin('${cell.id}');"></span>
				<span class="img_delete hand" title="删除" onclick="del('${cell.id}');"></span>
			</td>
		</tr>
	</s:iterator>
</table>
</form>
</div>

</body>
</html>
