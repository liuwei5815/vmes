<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<script type="text/javascript" src="${ctx }/admin/js/common/common.js?v=5.0"></script>
<title>调整工序计划</title>
<style>
.errorInput{
	border: 1px solid red;
}

</style>
<script type="text/javascript">
	$(function(){
		$(".todoEdit").click(function(){
			var view = $(this).parents(".view");
			view.hide();
			view.next(".edit").show();
		
		})
		$(".todoQx").click(function(){
			var edit = $(this).parents(".edit");
			edit.prev(".view").show();
			edit.hide();
		})
	});
	
	function testPlanNum(plannum){
	 	return /^\d{1,11}$/.test(plannum);
	}
	function submit(){
		
		$("#frmDiv").html("");
		$(".edit:visible").each(function(i){
			var state=$(this).find("[name=state]").val();
			var planNumInput = $(this).find("[name=plannum]");
			if(state!=2){
				if(!testPlanNum(planNumInput.val())){
					planNumInput.attr("title","计划数量格式错误，支持6位及以下的正整数");
					planNumInput.addClass("errorInput");
					throw "";
				}
			}
			var numInput = planNumInput.clone().attr("name","planTodos["+i+"].planNum");
			var stateSelect = $(this).find("[name=state]");
			var stateInput = $("<input type='text' name='planTodos["+i+"].state'/>");
			stateInput.val(stateSelect.val());
			var idInput = $(this).find("[name=id]").clone().attr("name","planTodos["+i+"].id");
			$("#frmDiv").append(numInput).append(stateInput).append(idInput);
		});
		$.xyAjax({
			url:'${ctx }/admin/dispatch!doChangeWorkPlan.action',
		   type:'post',
		   data:$("#frm").serialize(),
		    suc:function(){
				top.Dialog.alert("调整成功",function(){
					top.Dialog.close();
			  		$(window.parent.frmright.document).find("#frm").submit();
				});
			}
		});
	
		
	}
	
</script>
</head>
<body>
	<div >
		<form id="frm" >
				<input type="hidden" name="planId" value="${plan[0].id }"></input>
				<div id="frmDiv">
				
				</div>
		</form>
	</div>
	


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
							<legend>生产计划</legend>
							<table class="tableStyle" transMode="true" footer="normal">
								<tr>
			
									<td width="30%">生产计划编号：</td>
									<td width="10%">
									${(plan[0].manufactureCode!=null && plan[0].manufactureCode!='') ? plan[0].manufactureCode
						: plan[0].planCode } 
									</td>
									<td width="10%">产品编号：</td>
									<td width="15%">${plan[1].productCode }</td>
									<td width="10%">产品名称:</td>
									<td>${plan[1].name }</td>
								</tr>
								<tr>
			
									<td width="30%">规格/型号：</td>
									<td width="10%">${plan[1].typespec }</td>
									<td width="10%">计划开始时间：</td>
									<td width="15%"><s:date name="#request.plan[0].startDate" format="yyyy-MM-dd" /> </td>
									<td width="10%">计划结束时间:</td>
									<td><s:date name="#request.plan[0].endDate" format="yyyy-MM-dd" /></td>
								</tr>
								
								<tr>
			
									<td>生产计划数：</td>
									<td colspan="4"   >${plan[0].num }</td>
									
								</tr>

							</table>
						</fieldset>
						<fieldset>
							<legend>工序计划详情</legend>
							<table class="tableStyle" usehover="false" useclick="true" useradio="false"
								usecheckbox="false" style="text-align:center">
								<tbody>
									<tr class="odd">
										<th class="th">派工单号</th>
										<th class="th">工序名称</th>
										<th class="th">任务名称</th>
										<th class="th" width="50">计划数量</th>
										<th class="th" width="50">建议调整值</th>
										<th class="th" width="50">实际合格数</th>
										<th class="th" width="40">未合格数</th>
										<th class="th">状态</th>
										<th class="th">调整</th>
									</tr>
								
									<s:iterator value="#request.todos" var="item">
										<tr class="view">
											<td>${item.code }</td>
											<td>${item.processName } </td>
											<td>${item.taskName}</td>
											<td style="width: 100px">
											<fmt:formatNumber value="${item.planNum }" pattern=",###"></fmt:formatNumber>
											
											</td>
											<td style="width: 100px">
												<c:set var="advise" value="${item.planNum }"></c:set>
												<s:if test="#request.exceptionHandle!=null">
													<c:set var="advise" value="${item.planNum/exceptionHandle.oldPlanNum*exceptionHandle.planNum }"></c:set>
												</s:if>
												<fmt:formatNumber value="${advise }" maxFractionDigits="0" ></fmt:formatNumber>
												
												
											
											</td>
											<td>${item.actualNum }</td>
											<td>${item.disqualifiedNum }</td>
											<td width="100px"><s:property value="@com.xy.cms.common.CacheFun@getCodeText('todo_state',#item.state)" />  </td>
											<td><span class="icon_edit todoEdit">修改</span> </td>
										</tr>
										
										<tr class="edit" style="display: none">
											<td><input type="hidden" name="id" value="${item.id }"/>  ${item.code }</td>
											<td>${item.processName } </td>
											<td>${item.taskName}</td>
											<td><fmt:formatNumber value="${item.planNum }" pattern=",###"></fmt:formatNumber></td>
											<td style="width: 100px"><input type="text" class="validate[required,custom[onlyNumber]]" name="plannum"  style="width:90px"  value="<fmt:formatNumber pattern="#" value="${advise }" maxFractionDigits="0" ></fmt:formatNumber>"></input></td>
											<td>${item.actualNum }</td>
											<td>${item.disqualifiedNum }</td>
											<td style="width: 100px">
											<select name="state" autocomplete="off" class="simple" style="width: 80px">
												<s:iterator  value="@com.xy.cms.common.CacheFun@getCodeList('todo_state')" var="st">
													<option value="${key }"  <s:if test="#st.key==#item.state">selected="selected"</s:if> >${value }</option>
												</s:iterator>
												
											</select>
											
											</td>
											<td><span class="icon_no todoQx" >取消</span></td>
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
<table width="100%" class="tableStyle" transmode="true">
	<tr>
		<td colspan="2">
			<p>
			  <button id="save" onclick="return submit();" type="button"><span class="icon_save">保 存</span></button>
			  <button onclick="top.Dialog.close();" type="button"><span class="icon_no">取消</span></button>
			</p>
		</td>
	</tr>
</table>
</body>
</html>