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

function preEdit(id){
	if(id == ""){
		top.Dialog.alert("id not null");
		return false;	
	}
	window.parent.location.href="${ctx}/admin/view!preEditView.action?id="+id;
}

function del(id){
	if(id == ""){
		top.Dialog.alert("id not null");
		return false;
	}
	top.Dialog.confirm("您确定要删除选中的信息",function(){
		$.ajax({
			url:"${ctx}/admin/view!delView.action?id=" + id,
		    dataType:"json",
		    type:"post",
		    success:function(){
		    	top.Dialog.alert("删除成功",function(){
		    		top.Dialog.close();
		    	});
		    	window.parent.location.href="${ctx}/admin/view!init.action";
		    }
		});
		
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
      <th width="30" align="center">序号</th>
      <th align="center">视图名称</th>
      <th align="center">状态</th>
      <th align="center">创建时间</th>
      <th width="100" align="center">操作</th>
    </tr>
    <c:if test="${fn:length(list) == 0}">
    <tr><td colspan="5" align="center">暂无任何信息</td></tr>
    </c:if>
    <s:iterator value="list"  status="st" var="cell">
    <tr>
      <td>
      <%-- <input type="checkbox" value='<s:property value="id"/>' name="checks"/> --%>
        ${st.index+1 }
      </td>
      <td align="center"><s:property value="name"/></td>
      <td align="center">
          <s:if test="#cell.state==0">停用</s:if>
          <s:if test="#cell.state==1">正常</s:if>
      </td>      
      <td align="center"><s:date name="createTime" format="yyyy-MM-dd hh:mm:ss"/></td>
      <td align="center">
      	 <%-- <span class="img_edit hand" title="修改" onclick="preEdit('<s:property value="id"/>');"></span>
	    &nbsp;&nbsp; --%>
	    <span class="img_delete hand" title="删除" onclick="del('<s:property value="id" />');"></span>
     </td>
    </tr>
    </s:iterator>
  </table>
  </form>
</div>
</body>
</html>
