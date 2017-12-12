<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>菜单管理列表</title>
<%@ include file="/admin/jsp/common/common.jsp"%>
<script type='text/javascript' src="${ctx }/admin/js/page.js"></script>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style>
td, th {
	text-align: center;
}
</style>
<script>
$(document).ready(function() {
	loadPage();
});

//一级菜单被点击时显示其下属的二级菜单
function action(parentId){
	window.parent.treeClick(parentId);
}

//ajax删除
function del(id){
	if(id == ""){
		top.Dialog.alert("参数错误");
		return false;
	}
	top.Dialog.confirm("确认要删除吗？",function(){
		$.ajax({
			type:"POST",
			url:"${ctx}/admin/agrEncy!del.action",
			cache: false,
			dataType:"json",
			data:{"id":id},
			success: function(data) {
				if(data.successflag=="1"){
					top.Dialog.alert("删除成功");
					window.parent.doQuery();
					window.parent.menutree.window.location.href="${ctx}/admin/agrEncy!encyTree.action";
				}
				if(data.successflag=="5"){
					top.Dialog.alert("删除失败，此一级菜单下包含二级菜单");
				}
		     }		 
			
		});
	});
}

//更新的操作
function openEditWin(id){
	if(id == ""){
		top.Dialog.alert("参数错误");
		return false;	
	}
	var diag = new top.Dialog();
	diag.Title = "修改菜单信息";
	diag.URL = "${ctx}/admin/agrEncy!preEdit.action?ency.id=" + id;
	diag.Height=300;
	diag.Width=800;
	diag.show();	
}

</script>
</head>
<body>
	<div id="scrollContent" >
	
	<form name="frm" method="post">
	<input type="hidden" name="totalCount" id="totalCount" value="${totalCount }" /> 
	<input type="hidden" name="totalPage" id="totalPage" value="${totalPage }" /> 
	<input type="hidden" name="currPage" id="currPage" value="${currPage }" />		
	<input type="hidden" name="successflag" id="successflag" value="${successflag}"/>	
		
		<table class="tableStyle" id="checkboxTable">
			<tr>
				<th>名称</th>
				<th>等级</th>
				<th>父级</th>
				<th>首页显示</th>
				<th>添加时间</th>
				<th>状态</th>
				<th width="100">操作</th>
			</tr>
			
			<c:if test="${fn:length(list) == 0}">
				<tr>
					<td colspan="9" align="center">没有找到符合条件的记录</td>
				</tr>
			</c:if>
			
			<s:iterator value="list" status="st" var="cell">
				<tr>
					<!-- 当此菜单为一级菜单时可以点击名称进入此一级菜单下的二级菜单列表 -->
					<td>
						<s:if test="#cell.level==1">
						<a onclick="action(${cell.id})">${cell.name }</a>
						</s:if>
						<s:else>
						${cell.name }
						</s:else>
					</td>
					
					<td>${cell.level }</td>
					
					<!-- 如果该菜单为一级菜单，则显示一级菜单，否则显示其父菜单的名称而不是ID -->
					<td><s:if test="#cell.level==1">
						一级菜单
						</s:if>
					
						<s:iterator value="listParent" status="st" var="encyParent">
						<s:if test="#encyParent.id==#cell.parentId">
							${encyParent.name }
						</s:if>
						</s:iterator>
					</td>
					
					<td>${cell.isHome }</td>
					
					<td>${cell.addDate }</td>
					
					<td>${cell.state }</td>
					
					<%-- <td>
						<s:if test="#cell.state==1">正常</s:if>						
						<s:if test="#cell.state==0">停用</s:if>
					</td>
					
					<td>
						<s:if test="#cell.isMenu==0">是</s:if>
						<s:if test="#cell.isMenu==1">否</s:if>
					</td> --%>
					<td>
      	<span class="img_edit hand" title="修改" onclick="openEditWin('<s:property value="id"/>');" ></span>&nbsp;&nbsp;    
	    <span class="img_delete hand" title="删除" onclick="del('<s:property value="id" />');"></span>
    				 </td>
				</tr>
			</s:iterator>
		</table>
	</form>
	</div>		
</body>
</html>