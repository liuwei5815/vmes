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
<input type="hidden" name="todoId" value="${todoId}"/>
<input type="hidden" name="planCode" value="${planCode}"/>
<input type="hidden" name="planId" value="${planId}"/>
<input type="hidden" name="orderId" value="${orderId}"/>
<div style="padding-top:10px;">
    <div class="step_password">
        <ul class="step_cont">
          <s:if test="#request.orderId!='' && #request.orderId!='undefined'">
          	<li>
	          <span style="cursor:pointer" class="yuan curent_yuan active_i " onClick="jumpToOrder(${orderId})">1</span>
	          <i class="active_i"></i>
	          <div class="number_box"></div>
	          <p class="p_color">销售订单</p>
	         </li>
          	 <li>
                <span style="cursor:pointer" class="yuan curent_yuan active_i" onClick="jumpToPlan(${planId})">2</span>
                <i class="active_i"></i>
                <div class="number_box"></div>
                <p class="p_color">生产计划</p>
            </li>
            <li>
                <span style="cursor:pointer" class="yuan curent_yuan active_i" onClick="javascript :history.back(-1);">3</span>
                <i class="active_i"></i>
                <div class="number_box"></div>
                <p class="p_color">派工单</p>
            </li>
            <li>
                <span class="yuan curent_yuan active_i">4</span>
                <i class="active_i"></i>
                <div class="number_box"></div>
                <p class="p_color">派工单详情</p>
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
                <span style="cursor:pointer" class="yuan curent_yuan active_i" onClick="jumpToPlan(${planId})">1</span>
                <i class="active_i"></i>
                <div class="number_box"></div>
                <p class="p_color">生产计划</p>
            </li>
            <li style="list-style:none; float:left; width:25%; text-align:center; position:relative;">
                <span style="cursor:pointer" class="yuan curent_yuan active_i" onClick="javascript :history.back(-1);">2</span>
                <i class="active_i"></i>
                <div class="number_box"></div>
                <p class="p_color">派工单</p>
            </li>
            <li style="list-style:none; float:left; width:25%; text-align:center; position:relative;">
                <span class="yuan curent_yuan active_i">3</span>
                <i class="active_i"></i>
                <div class="number_box"></div>
                <p class="p_color">派工单详情</p>
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
		<s:if test="#request.orderId!='' && #request.orderId!='undefined'">
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
				<th class="th" width="100">计划完成时间</th>
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
				</tr>
			</s:else>
		</table>
		</fieldset>
		<fieldset>
		<legend>派工单</legend>
			<table class="tableStyle" transMode="true" footer="normal">
				<tr>
					<td width="15%">派工单编号：</td>
					<td width="25%">${produceplanTodo.code }</td>
					<td width="15%">工序名称：</td>
					<td >${produceplanTodo.processName }</td>
					<td rowspan="3">
						<img src="/qrcode/${produceplanTodo.code}.png" width="100px" height="100px" alt="" />
					</td>
				</tr>
				<tr>
					<td width="20%">任务名称：</td>
					<td width="30%">${produceplanTodo.taskName }</td>
					<td width="20%">总生产计划数量：</td>
					<td width="30%"><fmt:formatNumber value="${produceplanTodo.planNum }" pattern=",###"/></td>
				</tr>
				<tr>
					<td width="20%">总合格数量：</td>
					<td width="30%"><fmt:formatNumber value="${produceplanTodo.actualNum }" pattern=",###"/></td>
					<td width="20%">总不合格数量：</td>
					<td width="30%"><fmt:formatNumber value="${produceplanTodo.disqualifiedNum }" pattern=",###"/></td>
				</tr>
			</table>
		</fieldset>
		<fieldset>
		<legend>派工详情</legend>
			<table class="tableStyle" useclick="true" useradio="false" usecheckbox="false">
				<tbody>
				<tr class="odd">
					<th class="th">员工</th>
					<th class="th">开始时间</th>
					<th class="th">计划数量</th>
					<th class="th">实际合格数</th>
					<th class="th">不合格数</th>
					<th class="th">报工时间</th>
					<th class="th">状态</th>
					<th width="6%">操作</th> 
				</tr>
				<s:if test="#request.todoClaims==null ||#request.todoClaims.size()==0">
					<tr>
						<td colspan="8" style="text-align: center;">暂无任何报工详情,无法向下追溯</td>
					</tr>
				</s:if>
				<s:iterator value="#request.todoClaims" var="item">
					<tr>
						<td>${item[1] }</td>
						<td><s:date name="#item[0].addDate" format="yyyy-MM-dd HH:mm:ss" /> </td>
						<td><fmt:formatNumber value="${item[0].plannum }" pattern=",###"/></td>
						<td><fmt:formatNumber value="${item[0].qualifiedNum }" pattern=",###"/></td>
						<td><fmt:formatNumber value="${item[0].disqualifiedNum }" pattern=",###"/></td>
						<td><s:date name="#item[0].finishTime" format="yyyy-MM-dd HH:mm:ss" /> </td>
						<td>
							<s:property value="@com.xy.cms.common.CacheFun@getCodeText('claim_state',#item[0].state)" />
						</td>
						<td class="todo" todoId='${item[0].id}'>
							<span class="hand underLine todoclaim">
								<button type="button" class="todoCode" style="cursor: pointer">
									<span class="icon_find">查询相关详情</span>
								</button>
							</span>
						</td>
					</tr>
				</s:iterator>
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
		$(".todoclaim").click(function(){
			var id = $(this).parents(".todo:first").attr("todoId");
			var planCode=$("input[name='planCode']").val();
			var planId=$("input[name='planId']").val();
			var todoId=$("input[name='todoId']").val();
			var orderId=$("input[name='orderId']").val();
			location.href ="${ctx}/admin/traceAction!claimRelation.action?claimId="+id+"&planCode="+planCode+"&planId="+planId+"&todoId="+todoId+"&orderId="+orderId;
		});	
	});
function jumpToPlan(planId){
	location.href = "${ctx}/admin/traceAction!queryPlan.action?planId="+planId+"&orderId="+$("input[name='orderId']").val(); 
}
function jumpToOrder(planId){
	location.href = "${ctx}/admin/traceAction!queryOrder.action?orderId="+planId; 
}
/* function jumpTotodo(){
	 var id=$("input[name='planCode']").val();
	 var planId=$("input[name='planId']").val();
	 location.href ="${ctx}/admin/traceAction!queryDis.action?planCode="+id+"&planId="+planId; 
} */
</script>
</html>