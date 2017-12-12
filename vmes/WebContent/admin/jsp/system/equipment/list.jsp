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




</head>
<body>
<!-- <div id="scrollContent" class="border_gray"> -->
<form action="${ctx}/admin/equipment!query.action" method="post" target="querytable" id="frm">
<input type="hidden" name="totalCount" id="totalCount" value="${totalCount }" />
<input type="hidden" name="totalPage" id="totalPage" value="${totalPage }" />
<input type="hidden" name="currPage" id="currPage" value="${currPage }" />
<table class="tableStyle" id="checkboxTable" style="text-align: center">
		<tr>
 			<th class="th"></th> 
			<th class="th">系统设备编号</th>
			<th class="th">用户设备编号</th>
			<th class="th">设备名称</th>
			<th class="th">规格/型号</th>
			<th class="th">设备类型</th>
			<th class="th">设备所属部门</th>
			<th class="th">采购日期</th>
			<!-- <th class="th">设备图片</th> -->
			<th class="th">日标准工作时长</th>
			<th class="th">操作</th>
		</tr>
		<c:if test="${fn:length(list) == 0}">
			<tr>
				<td  colspan="11"  align="center">没有找到符合条件的记录</td>
			</tr>
		</c:if>
		<s:iterator value="list" status="st" var="cell">
		<tr>
			<td><input name="pId" type="checkbox" value="${cell[0].id}"></td>
			<td>${cell[0].equipmentCode }</td>
			<td>${cell[0].userEquipmentCode }</td>
			<td>${cell[0].equipmentName }</td>
			<td>${cell[0].equipmentModel }</td>
			<td>${cell[1].type}</td>
			<td>${cell[2].name}</td>
			<%-- <td>${cell[0].buyDate }</td> --%>
			<td><fmt:formatDate value="${cell[0].buyDate }" pattern="yyyy-MM-dd"/></td>
		<%-- 	<td>
				<s:if test="#request.cell[0].equipmentImg!=null && #request.cell[0].equipmentImg!=''">
					<img src="<s:property value='@com.xy.cms.common.CommonFunction@getStrValue(\"picUrl\")'/>${cell[0].equipmentImg }" alt="" />
				</s:if>
			</td> --%>
			<s:if test="#cell.equipmentWorkTime==null">
				<td></td>
			</s:if>
			<s:else>
				<td>${cell[0].equipmentWorkTime }小时</td>
			</s:else>
	    	<td>
		      	<span class="img_edit hand" onclick="editEquipment(${cell[0].id});"></span>
				 <span class="img_delete hand" onclick="del(${cell[0].id});"></span>
	    	</td>
		</tr>
		</s:iterator>
</table>
</div>
<!-- </div> -->
</form>

</body>
 <script>
function editEquipment(id){
	var diag = new top.Dialog();
	diag.Title = "编辑设备信息";
	diag.URL = "equipment!preEdit.action?id="+id;
	diag.Height=500;
	diag.Width=650;
	diag.show();
	return false;
}
function del(id){
	if(id == ""){
		top.Dialog.alert("参数错误");
		return false;
	}
	top.Dialog.confirm("确定要删除该设备信息吗？",function(){
		$.ajax({
		     type: "POST",
		     url: "${ctx}/admin/equipment!delEquipment.action",
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
	     				top.Dialog.alert("删除失败");
	     			}
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
</html>