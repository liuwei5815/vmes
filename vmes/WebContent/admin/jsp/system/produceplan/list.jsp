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
//编辑无销售订单详情的生产计划
function editNoOrder(id){
	if(id == ""){
		top.Dialog.alert("id not null");
		return false;	
	}
	var diag = new top.Dialog();
	diag.ID="model";
	diag.Title = "编辑生产计划";
	diag.URL = "${ctx}/admin/produceplanAction!preEditNoOrder.action?id="+id;
	diag.Width=500;
	diag.Height=450;
	diag.show();
	return false;
}

//调整无销售订单详情的生产计划
function editNoOrderNum(id,pehId){
	if(id == ""){
		top.Dialog.alert("参数错误");
		return false;	
	}
	$.ajax({
	     type: "POST",
	     url: "${ctx}/admin/produceplanAction!prompt.action",
	     cache: false,
	     dataType:"json",
	     data:{"id":id},
	     success: function(data){
	    	 console.log(data)
	   		if(data.prompt=="1"){
	   			top.Dialog.confirm("上次调整以后，派工单还未进行相应的调整，您确定要再次调整生产计划?",function(){
	   				var diag = new top.Dialog();
		   			diag.ID="model";
		   			diag.Title = "调整生产计划";
		   			diag.URL = "${ctx}/admin/produceplanAction!preEditNoOrderNum.action?id="+id+"&pehId="+pehId;
		   			diag.Width=400;
		   			diag.Height=300;
		   			diag.show();
		   			return false;
	   			});
	     	}else{
	   			var diag = new top.Dialog();
	   			diag.ID="model";
	   			diag.Title = "调整生产计划";
	   			diag.URL = "${ctx}/admin/produceplanAction!preEditNoOrderNum.action?id="+id+"&pehId="+pehId;
	   			diag.Width=400;
	   			diag.Height=300;
	   			diag.show();
	   			return false;
	     	} 
	     }
	});	
}

//取消生产计划
function cancelProduceplan(id){
	if(id == ""){
		top.Dialog.alert("id not null");
		return false;
	}
	top.Dialog.confirm("您确定要取消选中的生产计划",function(){
		$.ajax({
		     type: "POST",
		     url: "${ctx}/admin/produceplanAction!cancelProduceplan.action",
		     cache: false,
		     dataType:"json",
		     data:{"id":id},
		     success: function(data){
		   		if(data.successflag=="1"){
		   			
		   			$("#frm").submit();	
		     	}
		   		else{
		   			top.Dialog.alert("删除失败");
		   		}
		     }
		});	
	});
}

//添加有订单的生产计划
function addYesOrderOne(id){
	if(id == ""){
		top.Dialog.alert("id not null");
		return false;	
	}
	var diag = new top.Dialog();
	diag.ID="model";
	diag.Title = "新增生产计划";
	diag.URL = "${ctx}/admin/produceplanAction!preAddYesOrderOne.action?id="+id;
	diag.Width=800;
	diag.Height=450;
	diag.show();
	return false;
}

//编辑有订单的生产计划
function editYesOrderOne(id){
	if(id == ""){
		top.Dialog.alert("id not null");
		return false;	
	}
	var diag = new top.Dialog();
	diag.ID="model";
	diag.Title = "编辑生产计划";
	diag.URL = "${ctx}/admin/produceplanAction!preEditYesOrderOne.action?id="+id;
	diag.Width=800;
	diag.Height=450;
	diag.show();
	return false;
}

//调整有订单的生产计划数量
function editYesOrderOneNum(id,pehId){
	if(id == ""){
		top.Dialog.alert("id not null");
		return false;	
	}
	var diag = new top.Dialog();
	diag.ID="model";
	diag.Title = "调整生产计划";
	diag.URL = "${ctx}/admin/produceplanAction!preEditYesOrderOneNum.action?id="+id+"&pehId="+pehId;
	diag.Width=800;
	diag.Height=450;
	diag.show();
	return false;
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
<form action="${ctx}/admin/produceplanAction!query.action" method="post" target="querytable" id="frm">
<input type="hidden" name="totalCount" id="totalCount" value="${totalCount }"/>
<input type="hidden" name="totalPage" id="totalPage" value="${totalPage }"/>
<input type="hidden" name="currPage" id="currPage" value="${currPage }"/>
<input type="hidden" name="perPageRows" id="perPageRows" value="${perPageRows}"/>
<s:hidden name="message" id="message"/> 
		<table class="tableStyle" id="checkboxTable">
		<tr style="text-align: center;">
			<th colspan="7">生产计划</th>
    		<th colspan="7">销售订单</th>
    	</tr>
    	<tr>
    		<!-- 生产计划 -->
    		<th width="11%">生产计划编号</th>
    		<th width="6%">生产计划数</th>
    		<th width="6%">实际完成数 </th>
      		<th width="7%">计划开始日期</th>
      		<th width="7%">计划结束日期</th>
      		<th width="7%">生产计划状态</th>
      		<th width="6%">操作</th>
      		<!-- 订单 -->
    		<th width="12%">订单编号</th>
    		<th width="6%">产品编号</th>
    		<th width="7%">产品名称</th>
    		<th width="9%">规格型号</th>
    		<th width="4%">单位 </th>
    		<th width="5%">订单数量 </th>
    		<th width="7%">交货期 </th>
    	</tr>
    	<c:if test="${fn:length(list) == 0}">
			<tr>
				<td colspan="14" align="center">没有找到符合条件的记录</td>
			</tr>
		</c:if> 
		<s:iterator value="list" status="st" var="cell">
			<tr <s:if test="#request.cell.pehState==1">style="color:red;"</s:if> >       	
        	<!-- 生产计划 -->
        	<td>${cell.plan_code }<br>
        		<s:if test="#request.cell.manufacture_code!='' && #request.cell.manufacture_code!=null">(${cell.manufacture_code })</s:if>
        	</td>
        	<td><fmt:formatNumber value="${cell.planNum }" pattern=",###"/></td>
        	<td><s:if test="#request.cell.planState==@com.xy.cms.common.CacheFun@getCodeValue('plan_state','已完成')">${cell.qualified_num }</s:if></td>
          	<td>${cell.planStartDate }</td>
          	<td>${cell.planEndDte }</td>
          	<td 
          		<s:if test="#request.cell.planState==@com.xy.cms.common.CacheFun@getCodeValue('plan_state','待派工')">style="background:red;color:white;"</s:if>
          		<s:elseif test="#request.cell.planState==@com.xy.cms.common.CacheFun@getCodeValue('plan_state','待生产')">style="background:orange;"</s:elseif>
          		<s:elseif test="#request.cell.planState==@com.xy.cms.common.CacheFun@getCodeValue('plan_state','生产中')">style="background:yellow;"</s:elseif>
          		<s:elseif test="#request.cell.planState==@com.xy.cms.common.CacheFun@getCodeValue('plan_state','已完成')">style="background:green;color:white;"</s:elseif>
          		<s:elseif test="#request.cell.planState==@com.xy.cms.common.CacheFun@getCodeValue('plan_state','已取消')">style="background: #D4D4D4;"</s:elseif>
          	>
          	<s:if test="#request.cell.planState!=null">
          		<s:property value="@com.xy.cms.common.CacheFun@getCodeText('plan_state',#cell.planState)" />
          	</s:if>
          	</td>
          	<td nowrap="nowrap">
          	<s:if test="#request.cell.planId==null">
        		<span class="img_add hand" title="新增" onclick="addYesOrderOne(${cell.detailId })"></span>
          	</s:if>
          	<s:else>
          		<s:if test="#request.cell.order_code_auto==null">
          			<s:if test="#request.cell.planState==@com.xy.cms.common.CacheFun@getCodeValue('plan_state','已取消')||#request.cell.planState==@com.xy.cms.common.CacheFun@getCodeValue('plan_state','已完成')">
          			</s:if>
          			<s:else>
          				<span class="img_mark hand" title="调整"  onclick="editNoOrderNum('${cell.planId}','${cell.pehId}');"></span>
      					<span class="img_edit hand" title="编辑" onclick="editNoOrder('${cell.planId }');"></span>
	    				<span class="img_no hand" title="取消" onclick="cancelProduceplan('${cell.planId }');"></span>
          			</s:else>
          		</s:if>
          		<s:else>
          			<s:if test="#request.cell.planState==@com.xy.cms.common.CacheFun@getCodeValue('plan_state','已取消')||#request.cell.planState==@com.xy.cms.common.CacheFun@getCodeValue('plan_state','已完成')">
          				<%-- <span class="img_mark hand" title="调整"  onclick="editYesOrderOneNum('${cell[0]}','${cell[15]}');"></span>
      					<span class="img_edit hand" title="编辑" onclick="editYesOrderOne('${cell[0] }');"></span> --%>
          			</s:if>
          			<s:else>
          				<span class="img_mark hand" title="调整"  onclick="editYesOrderOneNum('${cell.planId}','${cell.pehId}');"></span>
      					<span class="img_edit hand" title="编辑" onclick="editYesOrderOne('${cell.planId }');"></span>
	    				<span class="img_no hand" title="取消" onclick="cancelProduceplan('${cell.planId }');"></span>
          			</s:else>
          		</s:else>
          	</s:else>
          	</td>
          	<!-- 订单 -->
        	<td>${cell.order_code_auto }<br>
        		<s:if test="#request.cell.order_code!='' && #request.cell.order_code!=null">(${cell.order_code })</s:if>
        	</td>
        	<td>${cell.product_code }</td>
        	<td>${cell.product_name }</td>
        	<td>${cell.product_typespec }</td>
        	<td>${cell.product_unit }</td>
        	<td><fmt:formatNumber value="${cell.detailNum }" pattern=",###"/></td>
        	<td>${cell.deliveryd_date }</td>
          	
        </tr>
		</s:iterator>
		</table>
</form>
</div>
</body>
</html>
