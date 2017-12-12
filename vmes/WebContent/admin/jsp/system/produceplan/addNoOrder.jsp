<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="${ctx}/admin/js/third-party/chosen/chosen.jquery.js?v=1.2" type="text/javascript"></script>
<link  type='text/css' rel='stylesheet'  href="${ctx}/admin/js/third-party/chosen/chosen.css" />


<title>新增生产计划</title>
<style>
</style>
<script>
function cancel(){
	top.Dialog.close();
}

function unload(){
    if(document.frm.successflag.value == "1"){//执行成功
    	top.Dialog.alert("保存成功",function(){
    		this.parent.frmright.doSubmit();
    		cancel();
    	});
	}else{
		if(document.forms[0].message.value !=""){
			top.Dialog.alert(document.forms[0].message.value);
	    	document.forms[0].message.value = "";	   
	    }
	}
    
}

$(document).ready(function(){ 
	var config = {
		'.chosen-select'           : {},
		'.chosen-select-deselect'  : { allow_single_deselect: true },
		'.chosen-select-no-single' : { disable_search_threshold: 10 },
		'.chosen-select-no-results': { no_results_text: '未找到相关产品!' },
		'.chosen-select-rtl'       : { rtl: true },
		'.chosen-select-width'     : { width: '100%' },
	}
	for (var selector in config) {
		$(selector).chosen(config[selector]);
	}

	unload();
});

//产品
function queryProduct(hiddenSignId,SignId){
	var diag = new top.Dialog();
	diag.ID="choose";
	diag.Title = "选择产品";
	diag.URL = "produceplanAction!queryProductInit.action?";
	diag.Height=400;
	diag.Width=900;
	diag.OKEvent=function(){
		var winContent= diag.innerFrame.contentWindow.document;
		var checked = $(winContent).find("#querytable").contents().find("[name=checks]:checked");
		if(checked.length==0){
			top.Dialog.alert("请至少选择一项");
			return false;
		}
		var type = $(winContent).find("#type").val();
			if(checked.size()>1){
				top.Dialog.alert("只能选择一项");
				return false;
			}else{
				$("#"+hiddenSignId+"").val(checked.val());
				$("#"+SignId+"").val(checked.attr("productCode"));
				//产品名称
				$("#productName").val(checked.attr("lable"));
				//规格型号
				$("#productTypespec").val(checked.attr("typespec"));
				diag.close();
			}
		};
	diag.show();
}

function doSubmit(){
	//验证时间
	var startDate= new Date($("input[name='produceplan.startDate']").val());
	var endDate=new Date($("input[name='produceplan.endDate']").val());
	if(startDate.getTime()>endDate.getTime()){
		top.Dialog.alert("计划开始时间不能早于计划结束时间");
		return;
	}
	var productCode= $("#productName option:selected").val();
	if(productCode==""){
		top.Dialog.alert("请选择产品");
		return false;
	}
	$("#frm").submit();	
}

function changeOrderCode(){
	var productCode= $("#productName option:selected").attr("productCode");
	var typespec= $("#productName option:selected").attr("typespec");
	var val= $("#productName option:selected").val();
	$("#produceplanProductId").val(productCode);
	$("#productTypespec").val(typespec);
	$("#productId").val(val);
	
} 

</script>
</head>
<body>
<div class="box1">
		<s:form action="admin/produceplanAction!addNoOrder.action" method="post" theme="simple" id="frm">
		<input type="hidden" name="message" id="message" value="${message}" /> 
		<input type="hidden" name="successflag" id="successflag" value="${successflag}" /> 
      		<fieldset>
				<legend>生产计划基本信息</legend>
    			<table width="100%" class="tableStyle" transmode="true">
					<tr >
						<td width="40%">生产计划编号：</td>
						<td>
							<input type="text" name="produceplan.manufactureCode" id="produceplanManufactureCode" class="validate[length[15,30],custom[noSpecialCaracters]]" value="" class="textinput" placeholder="如果不填系统自动生成"></input>
						</td>
					</tr>
					<tr>
						<td>产品编号：</td>
						<td>
							<input type="text" name="produceplan.productId" id="produceplanProductId"  value="" style="width: 118px; height: 100%" disabled></input>
							<input type="hidden" name="produceplan.productId" id="productId" value=""/>
							<!-- <div style="float: left;">
								<input type="text" name="produceplan_productId" id="produceplan_productId" class="validate[required,maxSize[30]]" value="" class="textinput default hid" ></input>
								<input type="hidden" name="produceplan.productId" id="produceplanProductId" value=""/>
							</div>
							<span class="star" style="float: left;">*</span>
							<span class="icon_find" onclick="queryProduct('produceplanProductId','produceplan_productId')">&nbsp</span> -->
						</td>
					</tr>
					<tr>
						<td>产品检索：</td>
						<td>
							<!-- <input type="text" name="productName" id="productName" class="validate[required,maxSize[30]]" value="" class="textinput default hid"  disabled></input> -->
							 <select  class="chosen-select-deselect" onchange='changeOrderCode()' name="productName" id="productName" style="width: 122px;font-size: 12px;background: url(form/textinput_bg.jpg) repeat-x scroll left top #ffffff;border-color: #cccccc;border-style: solid;border-width: 1px;color: #336699;height: 24px;line-height: 20px;">
								<option  value="" >请选择产品</option>
								<s:iterator value="#request.productList " var="cell" status="item">
					    			<option  value="${cell.id}" productCode="${cell.productCode }" typespec="${cell.typespec }">${cell.name}(${cell.typespec})</option>
					    		</s:iterator>
							</select>
							<span class="star"> *</span> 
						</td>
					</tr>
					<tr >
						<td>规格/型号：</td>
						<td>
							<input type="text" name="productTypespec" id="productTypespec" value="" style="width: 118px; height: 100%" disabled></input>
						</td>
					</tr>
					<tr>
						<td>生产计划数：</td>
						<td>
							<input type="text" name="produceplan.num" id="produceplanNum" class="validate[required,custom[onlyNumber],length[0,6]] " value="" class="textinput default hid"  ></input>
							<span class="star"> *</span>
						</td>
					</tr>
					<tr>
						<td>计划开始日期：</td>
						<td>
							<input type="text" name="produceplan.startDate" id="produceplanStartDate" value="" class="date hid validate[required]"   datefmt="yyyy-MM-dd"/>
							<span class="star"> *</span>
						</td>
					</tr>
					<tr>
						<td>计划结束日期：</td>
						<td>
							<input type="text" name="produceplan.endDate" id="produceplanEndDate" value="" class="date hid validate[required]"  datefmt="yyyy-MM-dd"/>
							<span class="star"> *</span>
						</td>
					</tr>
					<tr>
						<td colspan="2"></td>
					</tr>
					<!-- <tr>
						<td colspan="2">
					    	<p>
					            <button onclick="return doSubmit();" type="button" id="add">保 存 </button>
					            <button onclick="top.Dialog.close();" type="button">关 闭 </button>
					    	</p>
						</td>
					</tr> -->
			</table>
		</fieldset>
	</s:form>
</div>
<table width="100%" class="tableStyle" transmode="true">
				<tr>
			       <td colspan="2">
			       <p>
			       		<button id="save" onclick="return doSubmit();" type="button"><span class="icon_save">保 存</span></button>
			            <button onclick="top.Dialog.close();" type="button"><span class="icon_no">关 闭</span> </button>
			       </p>
			       </td>
			    </tr>
		    </table> 
</body>
</html>





