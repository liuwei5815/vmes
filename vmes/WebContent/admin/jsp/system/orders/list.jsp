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
function editOrders(id){
	var diag = new top.Dialog();
	diag.Title = "编辑销售订单";
	diag.URL = "orders!preEditOrders.action?id="+id;
	diag.Height=400;
	diag.Width=650;
	diag.show();
	return false;
}
function editDetail(id){
	var diag = new top.Dialog();
	diag.Title = "编辑销售订单详情";
	diag.URL = "orders!preEditDetail.action?id="+id;
	diag.Height=400;
	diag.Width=650;
	diag.show();
	return false;
}
function editDetailNum(orderId,detailId){
	var diag = new top.Dialog();
	diag.Title = "调整销售订单详情数量";
	diag.URL = "orders!preEditDetailNum.action?id="+detailId+"&oId="+orderId;
	diag.Height=400;
	diag.Width=900;
	diag.show();
	return false;
}
function addOrderDetail(id){
	var diag = new top.Dialog();
	diag.Title = "新增销售订单明细";
	diag.URL = "orders!preAddDetail.action?id="+id;
	diag.Height=400;
	diag.Width=650;
	diag.show();
	return false;
}
function del(obj,id){
	if(id == ""){
		top.Dialog.alert("参数错误");
		return false;
	}
	top.Dialog.confirm("确定要删除这条记录吗？",function(){
		$.ajax({
		     type: "POST",
		     url: "${ctx}/admin/dispatch!del.action",
		     cache: false,
		     dataType:"json",
		     data:{"currentId":id},
		     success: function(data){
	     		if(data.successflag=="1"){
	     			var tr=obj.parentNode.parentNode;  
	     		    var tbody=tr.parentNode;  
	     		    tbody.removeChild(tr);
		     	}
	     		else{
	     			top.Dialog.alert("删除失败");}
		     }
		});	
	});
}
//取消订单
function cancleOrders(id){
	if(id == ""){
		top.Dialog.alert("id not null");
		return false;
	}
	top.Dialog.confirm("您确定要取消选中的订单吗",function(){
		$.ajax({
		     type: "POST",
		     url: "${ctx}/admin/orders!cancleOrders.action",
		     cache: false,
		     dataType:"json",
		     data:{"id":id},
		     success: function(data){
		    	 if(data.successflag=="1"){
		    		 	$("#frm").submit();	
			   			top.Dialog.alert("取消成功",function(){
			   	    		cancel();
			   	    	});
			     	}
			   		else{
			   			top.Dialog.alert("取消失败");}
			     }
		});	
	});
}
//取消订单详情
function cancleDetail(id){
	if(id == ""){
		top.Dialog.alert("id not null");
		return false;
	}
	top.Dialog.confirm("您确定要取消选中的订单吗",function(){
		$.ajax({
		     type: "POST",
		     url: "${ctx}/admin/orders!cancleOrdersDetail.action",
		     cache: false,
		     dataType:"json",
		     data:{"id":id},
		     success: function(data){
		   		if(data.successflag=="1"){
		   			$("#frm").submit();	
		   			top.Dialog.alert("取消成功",function(){
		   	    		cancel();
		   	    	});
		     	}
		   		else{
		   			top.Dialog.alert("取消失败");}
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
</head>
<body>
<!-- <div id="scrollContent" class="border_gray"> -->
<form action="${ctx}/admin/orders!queryOrder.action" method="post" target="querytable" id="frm">
<input type="hidden" name="totalCount" id="totalCount" value="${totalCount }" />
<input type="hidden" name="totalPage" id="totalPage" value="${totalPage }" />
<input type="hidden" name="currPage" id="currPage" value="${currPage }" />
<table class="tableStyle cusTreeTable" usecolor="false" usehover="false" useclick="false" useradio="false" usecheckbox="false">
		<tbody>
		<tr>
			<th class="th" width="30"></th>
			<th class="th">订单编号</th>
			<th class="th">下单日期</th>
			<th class="th">交付日期</th>
			<th class="th">公司经手人</th>
			<th class="th">客户名称</th>
			<th class="th">订单状态</th>
			<th class="th">操作</th>
		</tr>
		<c:if test="${fn:length(list) == 0}">
			<tr>
				<td  colspan="8"  align="center">没有找到符合条件的记录</td>
			</tr>
		</c:if>
		<s:iterator value="list" var="cell">
		<tr>
			<td><span class="hand img_add2"></span></td>
			<td>${cell[0].orderCodeAuto}<br><s:if test="#cell[0].orderCode!=null && #cell[0].orderCode!='' ">(${cell[0].orderCode })</s:if></td>
			<td><fmt:formatDate value="${cell[0].orderDate}" pattern="yyyy-MM-dd"/></td>
			<td><fmt:formatDate value="${cell[0].deliveryDate}" pattern="yyyy-MM-dd"/></td>
			<td><s:if test="#cell[0].empId==#cell[2].id">${cell[2].name}</s:if></td>
			<td>${cell[1].name}</td>
			<td 
				<s:if test="#request.cell[0].state==@com.xy.cms.common.CacheFun@getCodeValue('order_state','待排产')">style="background:red;color:white;"</s:if>
				<s:elseif test="#request.cell[0].state==@com.xy.cms.common.CacheFun@getCodeValue('order_state','生产中')">style="background:yellow;"</s:elseif>
				<s:elseif test="#request.cell[0].state==@com.xy.cms.common.CacheFun@getCodeValue('order_state','生产已完成')">style="background:green;color:white;"</s:elseif>
				<s:else>style="background: #D4D4D4;"</s:else> 
			>
	          	<s:if test="#request.list!=null">
	          		<s:property value="@com.xy.cms.common.CacheFun@getCodeText('order_state',#cell[0].state)" />
	          	</s:if>
          	</td> 
          	<s:if test="#cell[0].state==@com.xy.cms.common.CacheFun@getCodeValue('order_state','已取消')|| #cell[0].state==@com.xy.cms.common.CacheFun@getCodeValue('order_state','已完成')">
				<td>
		      		<%-- <span class="img_add hand" title="新增明细" onclick="addOrderDetail(${cell[0].id})"></span>
			   		 <span class="img_edit hand" title="编辑" onclick="editOrders(${cell[0].id})"></span> --%>
	    		 </td>
    		 </s:if>
    		 <s:else>
	    		 <td>
		      		<span class="img_add hand" title="新增明细" onclick="addOrderDetail(${cell[0].id})"></span>
			   		 <span class="img_edit hand" title="编辑" onclick="editOrders(${cell[0].id})"></span>
			   		 <span class="img_no hand" title="取消" onclick="cancleOrders(${cell[0].id})"></span>
	    		 </td>
    		 </s:else>
		</tr>
		<tr style="display: none;">
			<td></td>
			<td colspan="8">
				<table class="tableStyle" style="margin-top:8px;margin-bottom:8px;" useclick="true" useradio="false" usecheckbox="false">
					<tbody>
					<tr class="odd">
						<th class="th">产品编号</th>
						<th class="th">产品名称</th>
						<th class="th">规格/型号</th>
						<th class="th">单位</th>
						<th class="th">数量</th>
						<th class="th">交货期</th>
						<th class="th">明细状态</th>
						<th class="th">备注</th>
						<th class="th">操作</th>
					</tr>
					<s:if test="#request.ordersDetailMap[#cell[0].id]==null">
						<tr>
							<td colspan="8">暂无产品详情</td>
						</tr>
					</s:if>
					<s:iterator value="#request.ordersDetailMap[#cell[0].id]" var="detail">
					<tr>
						<td>${detail.productCode}</td>
						<td>${detail.productName}</td>
						<td>${detail.productTypespec}</td>
						<td>${detail.productUnit}</td>
						<td><fmt:formatNumber value="${detail.num}" pattern=",###"/></td>
						<td><fmt:formatDate value="${detail.deliveryDate}" pattern="yyyy-MM-dd"/></td>
						<td
							<s:if test="#request.detail.state==@com.xy.cms.common.CacheFun@getCodeValue('orderdetail_state','待排产')">style="background:red;color:white;"</s:if>
							<s:elseif test="#request.detail.state==@com.xy.cms.common.CacheFun@getCodeValue('orderdetail_state','生产中')">style="background:yellow;"</s:elseif>
							<s:elseif test="#request.detail.state==@com.xy.cms.common.CacheFun@getCodeValue('orderdetail_state','生产已完成')">style="background:green;color:white;"</s:elseif>
							<s:else>style="background: #D4D4D4;"</s:else> 
						>
				          	<s:if test="#request.ordersDetailMap[#cell[0].id]!=null">
				          		<s:property value="@com.xy.cms.common.CacheFun@getCodeText('orderdetail_state',#detail.state)" />
				          	</s:if>
          				</td> 
						<td>${detail.remarks}</td>
						<s:if test="#detail.state==@com.xy.cms.common.CacheFun@getCodeValue('order_state','已取消')|| #detail.state==@com.xy.cms.common.CacheFun@getCodeValue('order_state','已完成')">
							<td>
							<%-- 	<span class="img_mark hand" title="调整" onclick="editDetailNum(${cell[0].id},${detail.id})"></span>
				   		 <span class="img_edit hand" title="编辑" onclick="editDetail(${detail.id})"></span> --%>
				    		 </td>
    					 </s:if>
    					 <s:else>
				    		 <td>
				    		 	<span class="img_mark hand" title="调整" onclick="editDetailNum(${cell[0].id},${detail.id})"></span>
						   		 <span class="img_edit hand" title="编辑" onclick="editDetail(${detail.id})"></span>
						   		 <span class="img_no hand" title="取消" onclick="cancleDetail(${detail.id})"></span>
				    		 </td>
    		 			</s:else>
					</tr>
					</s:iterator>
				</tbody>
			 </table>
			</td>
		</tr>
		</s:iterator>
	</tbody>
</table>
</div>
<!-- </div> -->
</form>
</body>
</html>