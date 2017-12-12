<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息列表</title>
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
.round{
	width:30px;
	height:30px;
	display: inline-block;
	font-size:30px;
	text-align:center;
	color:#c0c0c0;
	text-decoration:none
}
.rounds{
	width:30px;
	height:30px;
	display: inline-block;
	font-size:30px;
	text-align:center;
	color:#32CD32;
	text-decoration:none
}
</style>
</head>
<body>
<!-- <div id="scrollContent" class="border_gray"> -->
<form action="${ctx}/admin/equipment!query.action" method="post" target="querytable" id="frm">
<input type="hidden" name="totalCount" id="totalCount" value="${totalCount }" />
<input type="hidden" name="totalPage" id="totalPage" value="${totalPage }" />
<input type="hidden" name="currPage" id="currPage" value="${currPage }" />
 <table class="tableStyle" id="checkboxTable">
		<tbody>
		<tr>
			<th width="30"></th> 
 			<!-- <th class="th">序号</th> -->
			<th class="th">系统设备编号</th>
			<th class="th">用户设备编号</th>
			<th class="th">设备名称</th>
			<th class="th">规格/型号</th>
			<th class="th">设备类型</th>
			<th class="th">所属部门</th>
			<th class="th">状态</th>
			<!-- <th class="th">采购日期</th>
			<th class="th">设备图片</th>
			<th class="th">日标准工作时长</th> -->
		</tr>
		<c:if test="${fn:length(list) == 0}">
			<tr>
				<td  colspan="11"  align="center">没有找到符合条件的记录</td>
			</tr>
		</c:if>
		<s:iterator value="list" status="st" var="cell">
		<tr>
			<td><input id="pId" name="checkbox" type="checkbox" value="${cell.id}"/></td>
			<%-- <td>${st.index+1}</td> --%>
			<td>${cell.equipment_code }</td>
			<td>${cell.usereq_code }</td>
			<td>${cell.name }</td>
			<td>${cell.model }</td>
			<td>${cell.type}</td>
			<td>${cell.deName}
	
			</td>
			<%-- <s:if test="#request.state[''.concat(#cell.id)] == 2"><td style="background:#30B2C2">在线</td></s:if>
			<s:else><td style="background:#c0c0c0">离线</td></s:else> --%>
			<s:if test="#request.state[''+#cell.id] == 3">
			<td width="30px"><span class="round">●</span></td>
			</s:if>
			<s:else>
				<td width="30px"><span class="rounds">●</span></td>
			</s:else>
			<%-- <td><fmt:formatDate value="${cell.buy_date }" pattern="yyyy-MM-dd"/></td>
			<td>
				<s:if test="#request.cell[0].equipmentImg!=null && #request.cell[0].equipmentImg!=''">
					<img src="<s:property value='@com.xy.cms.common.CommonFunction@getStrValue(\"picUrl\")'/>${cell.qrcode_img }" alt="" />
				</s:if>
			</td>
			<td>${cell.worktime }小时</td> --%>
		</tr>
		</s:iterator>
	</tbody>
</table>
</form>
<script>
function editEquipment(id){
	var diag = new top.Dialog();
	diag.Title = "编辑设备信息";
	diag.URL = "equipment!preEdit.action?id="+id;
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
	}) ;
});

function qxuan(){ 
	   var flag= $("#qx").attr("checked");
	   if(flag=="checked"){
	    
		   $("input[type='checkbox']").each(function(i){
		   
			  	  	$(this).attr("checked",true);
			    });
	   }else{
	   
	        $("input[type='checkbox']").each(function(i){
		   
			  	  	$(this).attr("checked",false);
			    });
	   }
	}
	
</script>
</body>
</html>