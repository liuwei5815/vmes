<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Insert title here</title>
<%@ include file="/admin/jsp/common/common.jsp"%>
<script type='text/javascript' src="${ctx }/admin/js/page.js"></script>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>
<script>

function openEditWin(id){
	if(id == ""){
		top.Dialog.alert("id not null");
		return false;	
	}
	var diag = new top.Dialog();
	diag.Title = "修改操作人员信息";
	diag.URL = "${ctx}/admin/admin!preEdit.action?admin.id=" + id;
	diag.Height=300;
	diag.show();
}

function del(id){
	if(id == ""){
		top.Dialog.alert("id not null");
		return false;
	}
	top.Dialog.confirm("您确定要删除选中的信息",function(){
		window.location = "${ctx}/admin/admin!del.action?id=" + id;
	});
}

$(document).ready(function(){ 
	loadPage();
	loadMsg();
});

function loadMsg(){
	var message = $("#message").val();
	if(message != ""){
		top.Dialog.alert(message);
		$("#message").val("");
	}
}
</script>
</head>
<body>
<div id="scrollContent" class="border_gray">
  <input type="hidden" name="totalCount" id="totalCount" value="${totalCount }"/>
  	<input type="hidden" name="totalPage" id="totalPage" value="${totalPage }"/>
  	<input type="hidden" name="currPage" id="currPage" value="${currPage }"/>
  	
	 <input type="hidden" name="perPageRows" id="perPageRows" value="${perPageRows}"/>
  	<s:hidden name="message" id="message"/>
  <form method="post">
    <table class="tableStyle" id="checkboxTable">
    <tr>
      <th width="30">select</th>
      <th>账号</th>
      <th>角色名称</th>
      <th>注册时间</th>
      <th width="100">操作</th>
    </tr>
    <c:if test="${fn:length(adminList) == 0}">
    <tr><td colspan="13">暂无任何信息</td></tr>
    </c:if>
    <s:iterator value="adminList"  status="st" var="cell">
    <tr>
      <td><input type="checkbox" value='<s:property value="id"/>' name="checks"/></td>
      <td><s:property value="account"/></td>
      <td><s:property value="roleName"/></td>      
      <td><s:date name="addDate" format="yyyy-MM-dd"/></td>
      <td>
      	<span class="img_edit hand" title="修改" onclick="openEditWin('<s:property value="id"/>');"></span>
	    &nbsp;&nbsp;
	    <span class="img_delete hand" title="删除" onclick="del('<s:property value="id" />');"></span>
     </td>
    </tr>
    </s:iterator>
  </table>
  </form>
</div>
</body>
</html>
