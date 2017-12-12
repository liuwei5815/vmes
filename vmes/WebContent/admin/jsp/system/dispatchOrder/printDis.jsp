<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/tags/core" prefix="c"%>
<%@page import="com.xy.cms.common.CacheFun"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"
	scope="request" />
<%@ taglib uri="/tags/PageTag" prefix="pt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>打印派工单</title>
<script type="application/javascript"
	src="${ctx}/admin/frame/js/jquery-1.8.0.js?v=4.0"></script>
<script type='text/javascript'
	src="${ctx}/admin/js/third-party/printJs/jquery.jqprint-0.3.js"></script>
<!-- 
如果您使用的是高版本jQuery调用下面jQuery迁移辅助插件即可
<script src="http://www.jq22.com/jquery/jquery-migrate-1.2.1.min.js"></script>
-->
<link type='text/css' rel='stylesheet'
	href="${ctx}/admin/css/print/todoprint.css?v=4.8" />

<script language="javascript">
	function printPlan() {
		$(".print").jqprint({
			debug : false, //如果是true则可以显示iframe查看效果（iframe默认高和宽都很小，可以再源码中调大），默认是false
			importCSS : true, //true表示引进原来的页面的css，默认是true。（如果是true，先会找$("link[media=print]")，若没有会去找$("link")中的css文件）
			printContainer : true, //表示如果原来选择的对象必须被纳入打印（注意：设置为false可能会打破你的CSS规则）。
			operaSupport : true
		//表示如果插件也必须支持歌opera浏览器，在这种情况下，它提供了建立一个临时的打印选项卡。默认是true
		});
	}
</script>
<style type="text/css">
td {
      text-align:center; /*设置水平居中*/
      vertical-align:middle;/*设置垂直居中*/
}
</style>
</head>

<body>
	
	<button class="button" onclick="printPlan()" style="margin-top: 10px";width: 66px; padding-left: 5px;">
		<span class="icon_print" style="cursor: default;">打印</span>
	</button>
	<div class="print">
		<s:iterator value="#request.planObjs" var="cell" step="st">
			<div class="printP"
				style="page-break-after: always; margin-top: 20px">
				<div class="title">
					<h2>生产计划</h2>
				</div>
				<div class="content">
					<table>
						<tr>
							<td width="20%">生产计划编号</td>
							<td width="45%">
							${cell[1].planCode}<br><s:if test = "#cell[1].manufactureCode!=null && #cell[1].manufactureCode!=''">(${cell[1].manufactureCode })</s:if> 
						 	</td>
							<td width="20%">生产计划数</td>
							<td width="15%"><fmt:formatNumber value="${cell[1].num }" pattern=",###"/></td>
						</tr>
						<tr>
							<td>产品名称</td>
							<td>${cell[1].productName }</td>
							<td>规格/型号</td>
							<td>${cell[1].productTypespec }</td>
						</tr>
						<tr>
							<td>计划开始时间</td>
							<td><s:date name="#cell[1].startDate" format="yyyy-MM-dd" />
							</td>
							<td>计划完成时间</td>
							<td><s:date name="#cell[1].endDate" format="yyyy-MM-dd" /></td>
						</tr>
					</table>
				</div>
				<div class="title">
					<h2>派工单</h2>
				</div>
				<div class="content">
					<table>
						<tr>
							<td width="18%">派工单号</td>
							<td width="20%">${cell[0].code }</td>
							<td width="18%">工序名称</td>
							<td>${cell[0].processName}</td>
							<td width="130px" valign="top" style="border-bottom: 0;padding: 0" rowspan="2">
								
								<img  class="qrcode" src="/qrcode/${cell[0].code}.png?<s:property value="@com.xy.cms.common.RandomUtil@randomStr(6)" />" width="100%" height="100%" style="margin-top: -35px;margin-left: -65px;" alt="" />
								
							</td>
						</tr>
						<tr>
							<td>生产任务名称</td>
							<td>${cell[0].taskName }</td>
							<td>总派工生产数量</td>
							<td><fmt:formatNumber value="${cell[0].planNum }" pattern=",###"/></td>
						</tr>

					</table>
				</div>
				<div class="title">
					<h2>派工信息</h2>
				</div>
				<div class="content">
					<table>
						<tr>
							<td width="12%">员工</td>
							<td width="20%">开始时间</td>
							<td width="12%">计划数量</td>
							<td width="12%">实际合格数</td>
							<td width="12%">不合格数</td>
							<td width="20%">报工时间</td>
							<td>状态</td>
						</tr>
						<s:set var="plannum" value="0"></s:set>
						<s:if
							test="#request.todoClaimsMap!=null && #request.todoClaimsMap[#cell[0].id]!=null">
							<s:set var="plannum"
								value="#request.todoClaimsMap[#cell[0].id].size()"></s:set>
							<s:iterator value="#request.todoClaimsMap[#cell[0].id]"
								var="claim">
								<tr>
									<td>${claim[1] }</td>
									<td><s:date name="#claim[0].addDate" format="yyyy-MM-dd HH:mm:ss" />
									</td>
									<td><fmt:formatNumber value="${claim[0].plannum }" pattern=",###"/></td>
									<td><fmt:formatNumber value="${claim[0].qualifiedNum }" pattern=",###"/></td>
									<td><fmt:formatNumber value="${claim[0].disqualifiedNum }" pattern=",###"/></td>
									<td><s:date name="#claim[0].finishTime" format="yyyy-MM-dd HH:mm:ss" /></td>
									<td><s:property value="@com.xy.cms.common.CacheFun@getCodeText('claim_state',#claim[0].state)" /></td>
								</tr>

							</s:iterator>
						</s:if>
						
						<s:iterator begin="1" step="1" end="15-#plannum">
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</s:iterator>

					</table>
				</div>

			</div>
		</s:iterator>
	</div>




</body>
</html>
