<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<title>报工单详情</title>
<script type="text/javascript">
	
</script>
</head>
<body>
	<div class="static_box1">
		<div class="box1_topcenter2">
			<div class="box1_topleft2">
				<div class="box1_topright2"></div>
			</div>
		</div>
		<div class="box1_middlecenter">
			<div class="box1_middleleft2">
				<div class="box1_middleright2">
					<div style="padding: 0 20px 0 20px;">

						<fieldset>
							<legend>派工单基本信息</legend>
							<table class="tableStyle" transMode="true" footer="normal">
								<tr>

									<td width="15%">派工单编号：</td>
									<td width="25%">${produceplanTodo.code }</td>
									<td width="15%">工序名称：</td>
									<td >${produceplanTodo.processName }</td>
									<td rowspan="3">
									
										<img src="/qrcode/${produceplanTodo.code}.png?<s:property value="@com.xy.cms.common.RandomUtil@randomStr(6)" />" width="100px" height="100px" alt="" />
									
									</td>
									
								</tr>
								<tr>
									<td width="20%">任务名称：</td>
									<td width="30%">${produceplanTodo.taskName }</td>
									<td width="20%">总派工生产数量：</td>
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
							<legend>报工详情</legend>
							<table class="tableStyle" useclick="true" useradio="false"
								usecheckbox="false" style="text-align:center">
								<tbody>
									<tr class="odd">
										<th class="th">员工</th>
										<th class="th">开始时间</th>
										<th class="th">计划数量</th>
										<th class="th">实际合格数</th>
										<th class="th">不合格数</th>
										<th class="th">报工时间</th>
										<th class="th">状态</th>
									</tr>
									<s:if test="#request.todoClaims==null ||#request.todoClaims.size()==0">
										<tr>
											<td colspan="7">暂无任何员工报工</td>
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
											<td><s:property value="@com.xy.cms.common.CacheFun@getCodeText('claim_state',#item[0].state)" />  </td>
										</tr>
									</s:iterator>
									

								</tbody>
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
</html>