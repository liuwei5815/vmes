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
	<div id="scrollContent" class="border_gray">
		<input type="hidden" name="totalCount" id="totalCount"
			value="${totalCount }" /> <input type="hidden" name="totalPage"
			id="totalPage" value="${totalPage }" /> <input type="hidden"
			name="currPage" id="currPage" value="${currPage }" />

		<table class="tableStyle cusTreeTable" usecolor="false"
			usehover="false" ohterClose="false" useclick="false" useradio="false"
			usecheckbox="false">
			<tr>
				<th width="40"></th>
				<th class="th">生产计划编号</th>
				<th class="th" width="100">产品编号</th>
				<th class="th" width="100">产品名称</th>
				<th class="th" width="100">规格/型号</th>
				<th class="th" width="100">总生产数量</th>
				<th class="th" width="100">计划开始时间</th>
				<th class="th">计划完成时间</th>
				<th class="th">生产计划状态</th>
				<th class="th"width="100">生成派工单</th>
			</tr>
			<s:if test="list==null || list.size()==0">
				<tr>
					<td colspan="10">暂无待派工的生产计划</td>
				</tr>
			</s:if>
			
			
			<s:iterator value="list" var="cell" status="st">
				
				<tr class="plan <s:if test="#request.exceptionHandle.contains(#cell[0].id) && #request.planTodoMap[#cell[0].id]!=null">exceptionPlan</s:if>">
					<td>
					<s:if test="#request.planTodoMap[#cell[0].id]!=null">
						<span class="hand img_add2"></span>
					</s:if>
					</td>
					<td> ${cell[0].planCode}<br><s:if test = "#cell[0].manufactureCode!=null && #cell[0].manufactureCode!=''">(${cell[0].manufactureCode })</s:if> 
						 </td>
					<td>${cell[1].productCode }</td>
					<td>${cell[1].name }</td>
					<td>${cell[1].typespec }</td>
					<td><fmt:formatNumber type="number" value="${cell[0].num }" pattern=",###"></fmt:formatNumber>	</td>
					<td><s:date name="#cell[0].startDate" format="yyyy-MM-dd" /></td>
					<td><s:date name="#cell[0].endDate" format="yyyy-MM-dd" /></td>
					<td
						<s:if test="#request.cell[0].state==@com.xy.cms.common.CacheFun@getCodeValue('plan_state','待派工')">style="background:red;color:white;"</s:if>
		          		<s:elseif test="#request.cell[0].state==@com.xy.cms.common.CacheFun@getCodeValue('plan_state','待生产')">style="background:orange;"</s:elseif>
		          		<s:elseif test="#request.cell[0].state==@com.xy.cms.common.CacheFun@getCodeValue('plan_state','生产中')">style="background:yellow;"</s:elseif>
		          		<s:elseif test="#request.cell[0].state==@com.xy.cms.common.CacheFun@getCodeValue('plan_state','已完成')">style="background:green;color:white;"</s:elseif>
		          		<s:elseif test="#request.cell[0].state==@com.xy.cms.common.CacheFun@getCodeValue('plan_state','已取消')">style="background:gray;"</s:elseif>
					><s:property
							value="@com.xy.cms.common.CacheFun@getCodeText('plan_state',#cell[0].state)" />
					</td>
					<td>
						<s:if test="#request.cell[0].state!=-1 && #request.cell[0].state!=3">
							<span class="img_add hand" title="生成派工单" onclick="addDisPath('${cell[0].id}')"></span>
							<span class="img_edit hand" title="调整工序计划" onclick="changeWorkPlan('${cell[0].id}')"></span>
						</s:if>
					</td>
				</tr>
				<tr>
					<td></td>
					<td colspan="9">
						<table class="tableStyle" style="" useclick="false"
							useradio="false" usecheckbox="false" >
							<tbody class="ck_${st.index+1}">
								<tr class="odd">
									<th class="th"><input type="checkbox" title="全选" onclick="swapCheck(${st.index+1})"/></th>
									<th class="th">派工单号</th>
									<th class="th">工序名称</th>
									<th class="th">任务名称</th>
									<th class="th">总派工生产数量</th>
									<th class="th">实际合格数</th>
									<th class="th">未合格数</th>
									<th class="th">状态</th>
								</tr>
								<s:if test="#request.planTodoMap[#cell[0].id]==null">
									<tr>
										<td colspan="8">暂无派工单，请重新生成派工单</td>
									</tr>
								</s:if>

								<s:iterator value="#request.planTodoMap[#cell[0].id]" var="todo">
									<tr class="todo" todoId='${todo.id }'>
										<td width="30"><input type="checkbox" name="pId" value="${todo.id }"  /></td>
										<td><span class="hand underLine todoCode">${todo.code }</span>  </td>
										<td>${todo.processName }</td>
										<td>${todo.taskName }</td>
										<td><fmt:formatNumber type="number" value="${todo.planNum }" pattern=",###"></fmt:formatNumber></td>
										<td><fmt:formatNumber type="number" value="${todo.actualNum }" pattern=",###"></fmt:formatNumber></td>
										<td><fmt:formatNumber type="number" value="${todo.disqualifiedNum }" pattern=",###"></fmt:formatNumber></td>
										<td 
											<s:if test="#request.todo.state==@com.xy.cms.common.CacheFun@getCodeValue('todo_state','待执行')">style="background:red;color:white;"</s:if>
										    <s:elseif test="#request.todo.state==@com.xy.cms.common.CacheFun@getCodeValue('todo_state','进行中')">style="background:yellow;"</s:elseif>
											<s:elseif test="#request.todo.state==@com.xy.cms.common.CacheFun@getCodeValue('todo_state','已完成')">style="background:green;color:white;"</s:elseif>
											<s:else>style="background: #D4D4D4;"</s:else>
										>
											<s:property value="@com.xy.cms.common.CacheFun@getCodeText('todo_state',#todo.state)" />
											
										</td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</td>
				</tr>
			</s:iterator>
		</table>

	</div>
</body>
<script>
	var addDisPath = function(produceplanId) {
		var diag = new top.Dialog();
		diag.Title = "生成派工单";
		diag.URL = "${ctx}/admin/dispatch!preAdd.action?produceplanId="+produceplanId;
		diag.Height = 200;
		diag.Width = 600;
		diag.show();
		return false;
	}
	$(document).ready(function() {
		loadPage();
		$(".img_add2").click(function() {

			var height = $(document).height();
			window.parent.iframeHeight('querytable');
		})	
		viewtodoClaim();
		
		
		
	});
	function viewtodoClaim(){
		$(".todoCode").click(function(){
			var id = $(this).parents(".todo:first").attr("todoId");
			var diag = new top.Dialog();
			diag.Title = "派工单详情";
			diag.URL = "${ctx}/admin/dispatch!claim.action?todoId="+id;
			diag.Height = 400;
			diag.Width = 600;
		
			diag.show();
		});	
	}
	//调整工序计划
	function changeWorkPlan(produceplanId){
	
		var diag = new top.Dialog();
		diag.Title = "调整工序计划";
		diag.URL = "${ctx}/admin/dispatch!changeWorkPlan.action?produceplanId="+produceplanId;
		diag.Height = 620;
		diag.Width = 800;
	    /* diag.OKEvent =function(){
			diag.innerFrame.contentWindow.submit();
		};
		diag.ShowButtonRow=true; */
		diag.show();
	}
	
	 //checkbox 全选/取消全选  
    var isCheckAll = false;  
    function swapCheck(num) {  
        if (isCheckAll) {  
            $(".ck_"+num+" input[type='checkbox']").each(function() {  
                this.checked = false;  
            });  
            isCheckAll = false; 
        } else {  
            $(".ck_"+num+" input[type='checkbox']").each(function() {  
                this.checked = true;  
            });  
            isCheckAll = true;  
        }  
    }  
</script>
</html>