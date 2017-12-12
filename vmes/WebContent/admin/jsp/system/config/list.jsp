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
<style>
td,th{
 text-align: center;
}
</style>
<script>


function openEditWin(id){
	if(id == ""){
		top.Dialog.alert("参数错误");
		return false;	
	}
	var diag = new top.Dialog();
	diag.Title = "修改";
	diag.URL = "${ctx}/admin/config!preEdit.action?id=" + id;
	diag.Width=1200;
	diag.Height=900;
	diag.ShowButtonRow=true;
	diag.OKEvent=function(){
		 diag.innerFrame.contentWindow.doSubmit();
	};
	diag.show();
}

function del(id){
	if(id == ""){
		top.Dialog.alert("参数错误");
		return false;
	}
	top.Dialog.confirm("确定要删除这条数据吗？",function(){
		$.ajax({
		     type: "POST",
		     url: "${ctx}/admin/config!delete.action",
		     cache: false,
		     dataType:"json",
		     data:{"id":id},
		     success: function(data){
	     		if(data.code=="1"){
	     			top.Dialog.alert("删除成功");
	     			parent.doQueryTab();	
		     	}
	     		else{
	     			top.Dialog.alert("删除失败");}
		     }
		});	
	});
}

function delAll(){
	var chk_value =[];    
	$('input[name="checks"]:checked').each(function(){    
		chk_value.push($(this).val());    
	});
	if (chk_value.length == 0) {
		top.Dialog.alert('您至少要选择一条新闻信息进行删除!');
		return false;
	}
	top.Dialog.confirm("确定要删除这条记录吗？",function(){
		$.ajax({
		     type: "POST",
		     url: "${ctx}/admin/sys_tables!del.action",
		     cache: false,
		     dataType:"json",
		     data:"id="+chk_value,
		     success: function(data){
	     		if(data.successflag=="1"){
	     			parent.frmright.doQueryTab();
		     	}else{
		     		top.Dialog.alert("删除失败");
		     	}
		     }
		});	
	});
	return false;
}

function createMenu(e){
	
 	var id=$(e).attr("id");
 	if(id == ""){
 		top.Dialog.alert("参数异常");
 		return;
 	}
 	
 	if($("#"+id)[0].checked){//新建菜单
 		var dlg = new top.Dialog();
 		dlg.Title = "创建菜单";
 		dlg.URL = "${ctx}/admin/sys_tables!menuCreate.action?tableId=" + id;
 		//dlg.URL = "${ctx}/admin/jsp/system/table/create.jsp?id="+id;
 		dlg.Height = 100;
 		dlg.Width = 300;
 		dlg.ShowButtonRow = false;
 		dlg.CancelButtonText = "关 闭 ";
 		dlg.OKEvent=function(){
 			dlg.innerFrame.contentWindow.doSubmit();
 		};
 		dlg.CancelEvent=function(){
 			var chk = document.getElementById(id);
 			chk.checked = false;
 			dlg.close();
 		};
 		
 		dlg.show();
 	}else{//删除菜单
 		$.ajax({
			url:"${ctx}/admin/sys_tables!delMenu.action",
			type:"post",
			dataType:"json",
			data:{
				"tableId":id
			},
			success:function(data){
				if(data.code==0){
					top.Dialog.alert("菜单取消成功!");
					parent.doQueryTab();
				}else{
					top.Dialog.alert(data.msg);
			    }
			}
		});
 	}
 	
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
		<th width="30">选择</th>
		<th>配置名</th>
		<th>配置值</th>
		<th>说明</th>
		<th width="100">操作</th>
	</tr>
	<c:if test="${fn:length(list) == 0}">
		<tr>
			<td colspan="9" align="center">没有找到符合条件的记录</td>
		</tr>
	</c:if>
	<s:iterator value="list" status="st" var="cell">
		<tr>
			<td><input type="checkbox" value='<s:property value="id"/>' name="checks" /></td>
			<td><s:property value="#cell.confname" /></td>
			<td>
			    <s:property value="#cell.confvalue" />
			</td>
			<td>
				<s:property value="#cell.des" />
			</td>
			<td width="100">
			 <%--<span class="img_view hand" title="查看" onclick="openViewWin('<s:property value="id"/>');"></span>--%>
			 <span class="img_edit hand" title="编辑" onclick="openEditWin('${cell.id}');"></span>
			 <span class="img_delete hand" title="删除" onclick="del('<s:property value="id"/>');"></span>
			</td>
		</tr>
	</s:iterator>
</table>
</form>
</div>
</body>
</html>
