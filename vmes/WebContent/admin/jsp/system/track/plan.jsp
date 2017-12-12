<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<link rel="stylesheet" href="${ctxAdmin}/css/import_basic.css?v=1.1" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<title>报工单详情</title>
<style>
.tableStyle tr th,td {
	height: 24px;
	text-align: center;
	white-space: nowrap;
	word-wrap: break-word;
	overflow: hidden;
}
.exceptionPlan td{
	color: red;
}

</style>
<script type="text/javascript">
	
</script>
</head>
<body>
<input type="hidden" name="planId" value="${planId}"/>
<input type="text" name="orderId" value="${orderId}"/>
<div style="padding-top:10px;">
    <div class="step_password">
        <ul class="step_cont">
         <s:if test="#request.orderId!='' && #request.orderId!='undefined' && #request.orderId!=null">
          	<li>
	          <span style="cursor:pointer" class="yuan curent_yuan active_i " onClick="jumpToOrder(${orderId})">1</span>
	          <i class="active_i"></i>
	          <div class="number_box"></div>
	          <p class="p_color">销售订单</p>
	         </li>
          	 <li>
                <span class="yuan curent_yuan active_i" onClick="jumpToPlan(${planId})">2</span>
                <i class="active_i"></i>
                <div class="number_box"></div>
                <p class="p_color">生产计划</p>
            </li>
            <li>
                <span class="yuan curent_yuan " onClick="javascript :history.back(-1);">3</span>
                <i></i>
                <div class="number_box"></div>
                <p>派工单</p>
            </li>
            <li>
                <span class="yuan curent_yuan">4</span>
                <i></i>
                <div class="number_box"></div>
                <p>派工单详情</p>
            </li>
            <li>
                <span class="yuan curent_yuan">5</span>
                <i></i>
                <div class="number_box"></div>
                <p>相关详情</p>
            </li>
          </s:if>
          <s:else>
          	 <li style="list-style:none; float:left; width:25%; text-align:center; position:relative;">
               <span style="cursor:pointer" class="yuan curent_yuan active_i " onClick="jumpTo(${planId})">1</span>
                <i class="active_i"></i>
                <div class="number_box"></div>
                <p  class="p_color">生产计划</p>
            </li>
            <li style="list-style:none; float:left; width:25%; text-align:center; position:relative;">
                <span class="yuan curent_yuan" onClick="javascript :history.back(-1);">2</span>
                <i></i>
                <div class="number_box"></div>
                <p>派工单</p>
            </li>
            <li style="list-style:none; float:left; width:25%; text-align:center; position:relative;">
                <span class="yuan curent_yuan">3</span>
                <i></i>
                <div class="number_box"></div>
                <p>派工单详情</p>
            </li>
            <li style="list-style:none; float:left; width:25%; text-align:center; position:relative;">
                <span class="yuan curent_yuan">4</span>
                <i></i>
                <div class="number_box"></div>
                <p>相关详情</p>
            </li>
          </s:else>
            <div class="clearfix"></div>
        </ul>
    </div>
</div>
	<div id="scrollContent" class="border_gray">
		<div class="box1_topcenter2">
			<div class="box1_topleft2">
				<div class="box1_topright2"></div>
			</div>
		</div>
		<div class="box1_middlecenter">
			<div class="box1_middleleft2">
				<div class="box1_middleright2">
					<div style="padding: 0 20px 0 20px;">
		<!-- 销售订单 -->
		<s:if test="#request.orderId!='' && #request.orderId!='undefined' && #request.orderId!=null">
		<fieldset> 
		<legend>销售订单</legend> 
		<table class="tableStyle cusTreeTable" usecolor="false"
			usehover="false" ohterClose="false" useclick="false" useradio="false"
			usecheckbox="false">
			<tr>
				<th class="th" width="100">销售订单编号</th>
				<th class="th" width="100">产品编号</th>
				<th class="th" width="100">产品名称</th>
				<th class="th" width="100">规格/型号</th>
				<th class="th" width="100">单位</th>
				<th class="th" width="100">订单数量</th>
				<th class="th" width="100">客户名称</th>
				<th class="th" width="100">交付期</th>
			</tr>
			<s:if test="#request.detail==null">
				<tr>
					<td colspan="9" style="text-align: center;">暂无生产计划,无法向下追溯</td>
				</tr>
			</s:if>
			<s:else>
				<tr class="plan">
					<td> ${orderCode}</td>
					<td>${detail.productCode}</td>
					<td>${detail.productName}</td>
					<td>${detail.productTypespec }</td>
					<td>${detail.productUnit }</td>
					<td><fmt:formatNumber type="number" value="${detail.num }" pattern=",###"></fmt:formatNumber>	</td>
					<td> ${cusName}</td>
					<td><s:date name="#request.detail.deliveryDate" format="yyyy-MM-dd" /></td>
				</tr>
			</s:else>
		</table>
		</fieldset>
		</s:if>
		<!-- 生产计划 -->
		<fieldset> 
		<legend>生产计划</legend> 
		<table class="tableStyle cusTreeTable" usecolor="false"
			usehover="false" ohterClose="false" useclick="false" useradio="false"
			usecheckbox="false">
			<tr>
				<th class="th">生产计划编号</th>
				<th class="th" width="100">产品编号</th>
				<th class="th" width="100">产品名称</th>
				<th class="th" width="100">规格/型号</th>
				<th class="th" width="100">单位</th>
				<th class="th" width="100">总生产数量</th>
				<th class="th" width="100">计划开始时间</th>
				<th class="th">计划完成时间</th>
				<th class="th">操作</th>
			</tr>
			<s:if test="#request.plan==null">
				<tr>
					<td colspan="9" style="text-align: center;">暂无生产计划,无法向下追溯</td>
				</tr>
			</s:if>
			<s:else>
				<tr class="plan <s:if test="#request.exceptionHandle.contains(#plan.id) && #request.planTodoMap[#plan.id]!=null">exceptionPlan</s:if>">
					<td> ${plan.planCode}<br><s:if test = "#plan.manufactureCode!=null && #plan.manufactureCode!=''">(${plan.manufactureCode })</s:if> 
						 </td>
					<td>${plan.productCode }</td>
					<td>${plan.productName }</td>
					<td>${plan.productTypespec }</td>
					<td>${plan.productUnit }</td>
					<td><fmt:formatNumber type="number" value="${plan.num }" pattern=",###"></fmt:formatNumber>	</td>
					<td><s:date name="#request.plan.startDate" format="yyyy-MM-dd" /></td>
					<td><s:date name="#request.plan.endDate" format="yyyy-MM-dd" /></td>
					<td class="todo" todoId='${plan.planCode}'>
						<span class="hand underLine todoclaim">
							<button type="button" class="plans" style="cursor: pointer">
								<span class="icon_find">查询派工单</span>
					        </button>
				        </span>
					 </td>
				</tr>
			</s:else>
		</table>
		</fieldset>
		</div>
		</div>
		</div>
		</div>
		<div class="box1_bottomcenter2">
			<div class="box1_bottomleft2">
				<div class="box1_bottomright2"></div>
			</div>
		</div>
	</div>
</body>
<script>
$(document).ready(function() {
		$(".img_add2").click(function() {
			var height = $(document).height();
			window.parent.iframeHeight('querytable');
		})	
		 $(".plans").click(function(){
		 var id = $(this).parents(".todo:first").attr("todoId");
		 var planId=$("input[name='planId']").val();
		 var orderId=$("input[name='orderId']").val();
		 location.href ="${ctx}/admin/traceAction!queryDis.action?planCode="+id+"&planId="+planId+"&orderId="+orderId; 
	});	
});
function jumpTo(id){
	location.href ="${ctx}/admin/traceAction!init.action ";  
}
function jumpToOrder(planId){
	location.href = "${ctx}/admin/traceAction!queryOrder.action?orderId="+planId; 
}
</script>
</html>