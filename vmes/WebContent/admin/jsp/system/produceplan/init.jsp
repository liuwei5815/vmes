<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>生产计划</title>

<script>
//添加无销售订单详情的生产计划
function addNoOrder(){
	var diag = new top.Dialog();
	diag.ID="model";
	diag.Title = "新增生产计划";
	diag.URL = "${ctx}/admin/produceplanAction!preAddNoOrder.action";
	diag.Width=500;
	diag.Height=450;
	diag.show();
	return false;
}


function doSubmit(){
	$("#frm").submit();	
}
</script>
</head>
<body>
	<div class="position">
		<div class="center">
		<div class="left">
		<div class="right">
			<span>当前位置：生产计划管理 >>生产计划生成 </span>	</div>	
		</div>	
		</div>
	</div>	
	<div class="box2" panelTitle="生产计划生成" roller="false">
    <s:form action="admin/produceplanAction!query.action" name="initfrm" method="post" target="querytable" theme="simple" id="frm">
  	<input type="hidden" name="totalCount" id="totalCount" value="${totalCount}"/>
  	<input type="hidden" name="totalPage" id="totalPage" value="${totalPage}"/>
  	<input type="hidden" name="currPage" id="currPage" value="${currPage}"/>
	<input type="hidden" name="perPageRows" id="perPageRows" value="${perPageRows}"/>
	<table>
    	<tr>
        	
			<td>产品名称：</td>
			<td><input type="text" name="productName" class="textinput default hid validate[length[0,15]]"/></td>
			<td>产品编号：</td>
			<td><input type="text" name="productCode" class="textinput default hid validate[length[0,15]]"/></td>
       		<td>规格/型号：</td>
			<td><input type="text" name="productTypespec" class="textinput default hid validate[length[0,15]]"/></td>
        </tr>
        <tr>
        	<td>订单编号：</td>
			<td><input type="text" name="orderCode" class="textinput default hid validate[length[0,15]]"/></td>
        	<td>生产计划编号：</td>
			<td><input type="text" name="manufactureCode" class="textinput default hid validate[length[0,15]]"/></td>
        	<td>生产计划状态：</td>
			<td>
				<select name="produceplanState" id="sel3" class="validate[required]">
					<option value="">全部</option>
              		<option value="0">待派工</option>
              		<option value="1">待生产</option>
              		<option value="2">生产中</option>
              		<option value="3">已完成</option>
              		<option value="-1">已取消</option>
          		</select>
			</td>
			
			<td><button onclick='return doSubmit()' type="button"><span class="icon_find">查询</span></button></td>
       		<td>
				<button type="button" onclick='return addNoOrder()'><span class="icon_add">新增生产计划(填报)-无销售订单</span></button>
					<!-- <div class="simpleMenu_con" style="width: 120px;">
						<a href="javascript:;" onclick='return addNoOrder()' ><span>添加无订单生产计划</span></a>
						<a href="javascript:;"onclick='return addYesOrder()'><span>添加有订单生产计划</span></a>
					</div> -->
			</td>
        </tr>
    </table>
    </s:form>
    </div>
      
	<div id="scrollContent">
		<iframe scrolling="no" width="100%"  frameBorder=0 id="querytable" name="querytable" src="${ctx}/admin/produceplanAction!query.action" onload="iframeHeight('querytable')"  allowTransparency="true"></iframe>
	</div>
	<pt:page action="${ctx}/admin/produceplanAction!query.action" target="querytable"></pt:page>
</body>
</html>





