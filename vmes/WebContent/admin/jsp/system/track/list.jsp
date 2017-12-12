<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Insert title here</title>
<%@ include file="/admin/jsp/common/common.jsp"%>
<script type='text/javascript' src="${ctx }/admin/js/page.js"></script>
<link rel="stylesheet" href="${ctxAdmin}/css/import_basic.css?v=1.1" />
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
    	<tr>
    		
      		<!-- 订单 -->
    		<th width="12%">订单编号</th>
    		<th width="6%">产品编号</th>
    		<th width="7%">产品名称</th>
    		<th width="9%">规格型号</th>
    		<th width="4%">单位 </th>
    		<th width="5%">订单数量 </th>
    		<th width="5%">客户名称</th>
    		<th width="7%">交货期 </th>
    		<!-- 生产计划 -->
    		<th width="11%">生产计划编号</th>
      	    <th width="6%">操作</th> 
      		
    	</tr>
    	<c:if test="${fn:length(list) == 0}">
			<tr>
				<td colspan="14" align="center">没有找到符合条件的记录</td>
			</tr>
		</c:if>
		<s:iterator value="list" status="st" var="cell">
			<tr>
				<!-- 订单 -->
        	<td>${cell.order_code_auto }<br>
        		<s:if test="#request.cell.order_code!='' && #request.cell.order_code!=null">(${cell.order_code })</s:if>
        	</td>
        	<td>${cell.product_code }</td>
        	<td>${cell.product_name }</td>
        	<td>${cell.product_typespec }</td>
        	<td>${cell.product_unit}</td>
        	<td><fmt:formatNumber value="${cell.detailNum }" pattern=",###"/></td>
        	<td>${cell.cusName}</td>
        	<td>${cell.deliveryd_date }</td>      	
        	<!-- 生产计划 -->
        	<!-- 计划编号 -->
			<td>${cell.plan_code }<br>
			<!--版本1-->
          	 <%-- <td class="todo" todoId='${cell.planId}'>
				<span class="hand underLine todoclaim">
					<button type="button" class="plan" style="cursor: pointer">
						<span class="icon_find" >追溯产品质量</span>
			        </button>
		        </span>
			 </td> --%>
			 <!--版本2-->
			 <td class="todo" orderId='${cell.detailId }' todoId='${cell.planId}'>
				<span class="hand underLine todoclaim">
					<button type="button" class="plan" style="cursor: pointer">
						<span class="icon_find" >追溯产品质量</span>
			        </button>
		        </span>
			 </td>
        </tr>
		</s:iterator>
		</table>
</form>
</div>
</body>
<script>
$(document).ready(function(){ 
	loadPage();+
	loadMsg();
	//版本1
	 /* $(".plan").click(function(){
		 var id = $(this).parents(".todo:first").attr("todoId");
		 parent.location.href = "${ctx}/admin/traceAction!queryPlan.action?planId="+id; 
	});	 */
	//版本2
	$(".plan").click(function(){
		 var orderId = $(this).parents(".todo:first").attr("orderId");
		 if(orderId!=""){
			 parent.location.href = "${ctx}/admin/traceAction!queryOrder.action?orderId="+orderId;
			 return ;
		 }
		 var id = $(this).parents(".todo:first").attr("todoId");
		 parent.location.href = "${ctx}/admin/traceAction!queryPlan.action?planId="+id; 
		  
	});
	
});

function loadMsg(){
	var message = $("#message").val();
	if(message != ""){
		top.Dialog.alert(message);
		$("#message").val("");
	}
}
</script>
</html>
