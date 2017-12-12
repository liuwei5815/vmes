<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新闻信息列表</title>
<%@ include file="/admin/jsp/common/common.jsp"%>
<script type='text/javascript' src="${ctx }/admin/js/page.js"></script>
<link rel="stylesheet" href="${ctxAdmin}/css/import_basic.css?v=1.1" />
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style>
.tableStyle tr th,td {
	height: 24px;
	text-align: center;
	white-space: nowrap;
	word-wrap: break-word;
	overflow: hidden;
}
.todoCode:hover{
	color: #cccccc;

}
.exceptionPlan td{
	color: red;
}

</style>
</head>
<body>
<input type="hidden" name="orderId" value="${orderId}"/>
<input type="hidden" name="planId" value="${planId}"/>
<div style="padding-top:10px;">
    <div class="step_password">
        <ul class="step_cont">
        	 <li>
                <span style="cursor:pointer" class="yuan curent_yuan active_i " onClick="jumpTo()">1</span>
                <i class="active_i"></i>
                <div class="number_box"></div>
                <p class="p_color">销售订单</p>
            </li>
           <li>
                <span class="yuan curent_yuan ">2</span>
                <i></i>
                <div class="number_box"></div>
                <p>生产计划</p>
            </li>
            <li>
                <span class="yuan curent_yuan ">3</span>
                <i ></i>
                <div class="number_box"></div>
                <p >派工单</p>
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
            <div class="clearfix"></div>
        </ul>
    </div>
</div>
	<div id="scrollContent" class="border_gray">
		<input type="hidden" name="totalCount" id="totalCount"
			value="${totalCount }" /> <input type="hidden" name="totalPage"
			id="totalPage" value="${totalPage }" /> <input type="hidden"
			name="currPage" id="currPage" value="${currPage }" />
		<table class="tableStyle cusTreeTable" usecolor="false"
			usehover="false" ohterClose="false" useclick="false" useradio="false"
			usecheckbox="false">
			<tr>
				<th class="th">销售订单编号</th>
				<th class="th" width="100">产品编号</th>
				<th class="th" width="100">产品名称</th>
				<th class="th" width="100">规格/型号</th>
				<th class="th" width="100">单位</th>
				<th class="th" width="100">订单数量</th>
				<th class="th" width="100">客户名称</th>
				<th class="th" width="100">交付期</th>
				<th class="th">操作</th>
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
					<td class="todo" todoId='${planId}'>
						<span class="hand underLine todoclaim">
							<button type="button" class="plans" style="cursor: pointer">
								<span class="icon_find">查询生产计划</span>
					        </button>
				        </span>
					 </td>
				</tr>
			</s:else>
		</table>
	</div>
</body>
<script>
$(document).ready(function() {
		$(".img_add2").click(function() {
			var height = $(document).height();
			window.parent.iframeHeight('querytable');
		})	
		 $(".plans").click(function(){
			 var id = $("input[name='planId']").val();
			 var orderId=$("input[name='orderId']").val();
		    location.href = "${ctx}/admin/traceAction!queryPlan.action?planId="+id+"&orderId="+orderId; 
				  
	});	
});
function jumpTo(id){
	location.href ="${ctx}/admin/traceAction!init.action ";  
}
</script>
</html>