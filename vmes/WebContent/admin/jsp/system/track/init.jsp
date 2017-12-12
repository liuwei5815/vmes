<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>生产计划</title>

<script>
function doSubmit(){
	var startDate= new Date($("#orderStartDate").val());
	var endDate=new Date($("#orderEndDate").val());
	if(startDate.getTime()>endDate.getTime()){
		top.Dialog.alert("开始时间不能早于结束时间");
		return;
	}
	var planStartDate= new Date($("#planStartDate").val());
	var planEndDate=new Date($("#planEndDate").val());
	if(planStartDate.getTime()>planEndDate.getTime()){
		top.Dialog.alert("开始时间不能早于结束时间");
		return;
	}
	$("#frm").submit();	
}
</script>
<style>
.cusWidth {
	width:120px;;
}
</style>
</head>
<body>
	<div class="position">
		<div class="center">
		<div class="left">
		<div class="right">
			<span>当前位置：产品质量追溯 >>产品质量追溯 </span>	</div>	
		</div>	
		</div>
	</div>
	<div class="box2" panelTitle="产品质量追溯" roller="false">
    <s:form action="admin/traceAction!query.action" name="initfrm" method="post" target="querytable" theme="simple" id="frm">
  	<input type="hidden" name="totalCount" id="totalCount" value="${totalCount}"/>
  	<input type="hidden" name="totalPage" id="totalPage" value="${totalPage}"/>
  	<input type="hidden" name="currPage" id="currPage" value="${currPage}"/>
	<input type="hidden" name="perPageRows" id="perPageRows" value="${perPageRows}"/>
	<table>
		<tr>
			<td>客户名称：</td>
			<td><input type="text" name="customerName" class="cusWidth textinput default hid validate[length[0,15]]"/></td>
			<td> 产品名称：</td>
			<td><input type="text" name="productName" class="cusWidth textinput default hid validate[length[0,15]]"/></td>
			<td> 规格/型号：</td>
			<td><input type="text" name="productTypespec" class="cusWidth textinput default hid validate[length[0,15]]"/></td>
			<td><button onclick='return doSubmit()' type="button" style="cursor: pointer"><span class="icon_find">查询</span></button></td>
        </tr>
   	</table>
   	<table>
   		<tr>
   			<td> 销售订单日期:</td>
       		<td>
				<input class="date cusWidth" name="orderStartDate" id="orderStartDate" type="text" value="${orderStartDate}" />
				至
				<input class="date cusWidth" name="orderEndDate" id="orderEndDate" type="text" value="${orderEndDate}" />
			</td>
			<td> 生产日期:</td>
       		<td>
				<input class="date cusWidth" name="planStartDate" id="planStartDate" type="text" value="${planStartDate}" />
				至
				<input class="date cusWidth" name="planEndDate" id="planEndDate" type="text" value="${planEndDate}" />
			</td>
   		</tr>
   	</table>
	<!-- <table>
    	<tr>
        	第五次需求变更
        	<td>生产计划编号：</td>
			<td><input type="text" name="planCode" class="textinput default hid validate[length[0,15]]"/></td>
			<td>产品名称：</td>
			<td><input type="text" name="productName" class="textinput default hid validate[length[0,15]]"/></td>
			<td>产品编号：</td>
			<td><input type="text" name="productCode" class="textinput default hid validate[length[0,15]]"/></td>
       		<td>规格/型号：</td>
			<td><input type="text" name="productTypespec" class="textinput default hid validate[length[0,15]]"/></td>
			<td><button onclick='return doSubmit()' type="button" style="cursor: pointer"><span class="icon_find">查询</span></button></td>
       		
			</tr>
		</table> -->
		
    </s:form>
    </div>
      
	<div id="scrollContent">
		<iframe scrolling="no" width="100%"  frameBorder=0 id="querytable" name="querytable" src="${ctx}/admin/traceAction!query.action" onload="iframeHeight('querytable')"  allowTransparency="true"></iframe>
	</div>
	<pt:page action="${ctx}/admin/traceAction!query.action" target="querytable"></pt:page>
</body>
</html>





