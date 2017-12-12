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
function openEdit(id){
	if(id == ""){
		top.Dialog.alert("参数错误");
		return false;	
	}
	var diag = new top.Dialog();
	diag.Title = "编辑角色信息";
	diag.URL = "${ctx}/admin/role!preEdit.action?role.id=" + id;
	diag.Height=150;
	diag.show();
}
function openEditWin(id){
	if(id == ""){
		top.Dialog.alert("参数错误");
		return false;	
	}
	form1.action="${ctx}/admin/power!rolepower.action?role.id=" + id;
	form1.submit();
}

function del(id){
	if(id == ""){
		top.Dialog.alert("id not null");
		return false;
	}
	top.Dialog.confirm("确定要删除该角色信息吗",function(){
		window.location = "${ctx}/admin/power!del.action?id=" + id;
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
  <form name="form1" method="post" target="frmright">
    <table class="tableStyle" id="checkboxTable">
    <tr>
      	<th width="10%">序号</th>
      	<th>角色名称</th>
      	<th width="10%">操作</th>
    </tr>
    <c:if test="${fn:length(roleList) == 0}">
    <tr><td colspan="13">暂无任何信息</td></tr>
    </c:if>
    <s:iterator value="roleList"  status="st" var="cell">
    <tr>
      <td>${st.index+1 }</td>
      <td><s:property value="name"/></td>      
      <td>
      	<span class="img_guard hand" title="权限" onclick="openEditWin('<s:property value="id"/>');"></span>
      	<span class="img_edit hand" title="编辑"	onclick="openEdit('<s:property value="id"/>');"></span>
	    <span class="img_delete hand" title="删除" onclick="del('<s:property value="id" />');"></span>
     </td>
    </tr>
    </s:iterator>
  </table>
  </form>
</div>
</body>
</html>
