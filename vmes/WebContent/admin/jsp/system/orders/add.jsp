<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="/admin/jsp/common/common.jsp"%>
<!-- chosen -->
<script src="${ctx}/admin/js/third-party/chosen/chosen.jquery.js" type="text/javascript"></script>
<link  type='text/css' rel='stylesheet'  href="${ctx}/admin/js/third-party/chosen/chosen.css" />
<title>新增菜单</title>
<style>
.tableStyle tr th,td input{
 height:24px;
 text-align: center;
 white-space: nowrap;
 word-wrap: break-word;
 overflow: hidden;
}
</style>
<script type="text/javascript">
$(function(){
	//chosen-start
	var config = {
			'.chosen-select'           : {},
			'.chosen-select-deselect'  : { allow_single_deselect: true },
			'.chosen-select-no-single' : { disable_search_threshold: 10 },
			'.chosen-select-no-results': { no_results_text: '未找到相关信息!' },
			'.chosen-select-rtl'       : { rtl: true },
			'.chosen-select-width'     : { width: '100%' },
	}
	for (var selector in config) {
		$(selector).chosen(config[selector]);
	}
	unload();
	//添加一行
	$("#addDefRow").click(function(){
		if($("input[name='ordersDetailList[0].deliveryDate']").val().length==0){
			$("input[name='ordersDetailList[0].deliveryDate']").val($("input[name='orders.deliveryDate']").val());
		}
		
		var no = $("#checkboxTable").find("tr").size()-2;
		console.log("currentNo:"+no);
		
		if($("input[name='ordersDetailList["+no+"].deliveryDate']").val()!=" "){
			$("input[name='ordersDetailList["+no+"].deliveryDate']").val($("input[name='ordersDetailList["+no+"].deliveryDate']").val());
		}else{
			$("input[name='ordersDetailList["+no+"].deliveryDate']").val($("input[name='orders.deliveryDate']").val());
		}
		$("#checkboxTable").append($("#addTr").html().replace(/\{no\}/g,(no+1)));
	    var nc=no+1;
	    $("input[name='ordersDetailList["+nc+"].deliveryDate']").val($("input[name='orders.deliveryDate']").val());//默认交付日期 */
	    $("input[name='selText"+nc+"']").val("")
		$("input[name='selType"+nc+"']").val("");
		
		//解决name不同但通过添加出来的插件不能弹出
		var dateInput = $(":input[name=\"ordersDetailList["+no+"].deliveryDate\"]");
		$("#checkboxTable").on("focus","input[name=\"ordersDetailList["+(no+1)+"].deliveryDate\"]",function(){
			var that = $(this);
			 WdatePicker({
	                    skin: themeColor,
	                    isShowClear: true,
	                    onpicked: function(r) {
	                        that.blur()
	                    }
	                })
		});
		//加载chosen插件
		var config = {
				'.chosen-select'           : {},
				'.chosen-select-deselect'  : { allow_single_deselect: true },
				'.chosen-select-no-single' : { disable_search_threshold: 10 },
				'.chosen-select-no-results': { no_results_text: '未找到相关信息!' },
				'.chosen-select-rtl'       : { rtl: true },
				'.chosen-select-width'     : { width: '100%' },
		}
		for (var selector in config) {
			$(selector).chosen(config[selector]);
		}
	})
})
function remove(that){
	 if($("#checkboxTable tr").length-1==1){
		 top.Dialog.alert("请至少添加一条明细");
		 return;
	 }
	//获取所有当前点击的元素的父元素，获取第一个class为.reqTr的元素
	var trNode=$(that).parents(".reqTr:eq(0)");
	 //获取当前点击的元素的索引
	var index = trNode.index("#checkboxTable .reqTr");
	 
	console.log(index)
	$("#checkboxTable .reqTr:gt("+index+")").each(function(){
		var nIndex=$(this).index("#checkboxTable .reqTr");
		var i = parseInt(nIndex);
		console.log(i)
		console.log($(this).find("input:eq(0)").attr("name"))
		$(this).find("input:eq(0)").attr("name","ordersDetailList["+i+"].productId");
		$(this).find("input:eq(2)").attr("name","ordersDetailList["+i+"].modelNumber");
		$(this).find("input:eq(3)").attr("name","ordersDetailList["+i+"].unit");
		$(this).find("input:eq(4)").attr("name","ordersDetailList["+i+"].num");
		$(this).find("input:eq(5)").attr("name","ordersDetailList["+i+"].finishTime");
		$(this).find("input:eq(6)").attr("name","ordersDetailList["+i+"].remarks");
	}) 
	trNode.remove();
}
function doSubmit(){
	//验证时间
	var startDate= new Date($("input[name='orders.orderDate']").val());
	var endDate=new Date($("input[name='orders.deliveryDate']").val());
	if(startDate.getTime()>endDate.getTime()){
		top.Dialog.alert("下单时间不能早于交货时间");
		return;
	}
	var flag = false;
	 $("#checkboxTable select").each(function(){
         var value = $(this).val();
         if( $("#checkboxTable select option[value='"+value+"']:selected").size()>1){
             flag = true;
         } 
      });
	 if(flag){
		top.Dialog.alert("请勿选择重复的产品");
    	return;
     }
	$("#frm").submit();	
}

function cancel(){
	top.Dialog.close();
}

function unload(){
    if(document.frm.successflag.value == "1"){//执行成功
    	top.Dialog.alert("保存成功",function(){
    		this.parent.frmright.doSubmit();
    		cancel();
    	});
    	/* this.parent.frmright.window.location.href="${ctx}/admin/orders!init.action";	 */
	}else{
		if(document.forms[0].message.value !=""){
			top.Dialog.alert(document.forms[0].message.value);
	    	document.forms[0].message.value = "";	   
	    }
	}
}
/*
 * 自动变换客户名称
 */
function cselFunc(){
	$("#selName").val($("#csel").val());
	$("#cusId").val($("#csel option:selected").attr("cusId"));
}

/*
 * 自动变换产品名称
 */
function selFunc(currentRow){
	if(currentRow>=1){
	    $("input[name='selText"+currentRow+"']").val($("select[name='sel"+currentRow+"']").val());
		$("input[name='selType"+currentRow+"']").val($("select[name='sel"+currentRow+"'] option:selected").attr("productTypespec"));
		$("input[name='selUnit"+currentRow+"']").val($("select[name='sel"+currentRow+"'] option:selected").attr("productUnit"));
		$("input[name='ordersDetailList["+currentRow+"].productId']").val($("select[name='sel"+currentRow+"'] option:selected").attr("productId"));//ID
		$("input[name='ordersDetailList["+currentRow+"].productCode']").val($("select[name='sel"+currentRow+"'] option:selected").attr("productCode"));//CODE
		//隐藏域赋值
		$("input[name='ordersDetailList["+currentRow+"].productName']").val($("select[name='sel"+currentRow+"']").val());
		$("input[name='ordersDetailList["+currentRow+"].productTypespec']").val($("select[name='sel"+currentRow+"'] option:selected").attr("productTypespec"));
		$("input[name='ordersDetailList["+currentRow+"].productUnit']").val($("select[name='sel"+currentRow+"'] option:selected").attr("productUnit"));
	}else{
		$("input[name='selText']").val($("select[name='sel']").val());
		$("input[name='selType']").val($("select[name='sel'] option:selected").attr("productTypespec"));
		$("input[name='selUnit']").val($("select[name='sel'] option:selected").attr("productUnit"));
		$("input[name='ordersDetailList[0].productId']").val($("select[name='sel'] option:selected").attr("productId"));//ID
		$("input[name='ordersDetailList[0].productCode']").val($("select[name='sel'] option:selected").attr("productCode"));//CODE
		//隐藏域赋值
		$("input[name='ordersDetailList[0].productName']").val($("select[name='sel']").val());
		$("input[name='ordersDetailList[0].productTypespec']").val($("select[name='sel'] option:selected").attr("productTypespec"));
		$("input[name='ordersDetailList[0].productUnit']").val($("select[name='sel'] option:selected").attr("productUnit"));
	}
}

//单位树
function chooseUnit(showDomId,no){
	var diag = new top.Dialog();
	diag.ID="choose";
	diag.Title = "选择产品单位";
	diag.URL = "${ctx}/admin/orders!queryUint.action";
	diag.Height=500;
	diag.Width=300;
	diag.OKEvent=function(){
		var treenode= diag.innerFrame.contentWindow.getTreeNode();
		if(!treenode){
			top.Dialog.alert("请至少选择一项");	
			return false;
		}
		if(treenode.parent=='#'){
			top.Dialog.alert("请选择单位");
			return false;
		}
		/* var no=no>0?no:"";
		$("#"+showDomId+no+"").val(treenode.id);
		$("#"+showDomId+"_lable"+no).val(treenode.text); */
		if(no>0){
			$("#"+showDomId+no+"").val(treenode.id);
			$("#"+showDomId+"_lable"+no).val(treenode.text);
		}else{
			$("#"+showDomId+"").val(treenode.id);
			$("#"+showDomId+"_lable").val(treenode.text);
		}
		diag.close();
	}
	diag.show();
}

function openAddWin(){
	var diag = new top.Dialog();
	diag.Title = "新增客户信息";
	diag.URL = "admin/orders!preAddCustomer.action";
	diag.Height=350;
	diag.show();
	return false;
	
}

function customer(){
	$.ajax({
	     type: "POST",
	     url: "${ctx}/admin/orders!customer.action",
	     cache: false,
	     dataType:"json",
	     success: function(data){
	    	var customerList = data.customerList;
	    	 var customer=customerList[parseInt(customerList.length-1)];
	    	
	    	 $("<option cusId="+customer.id+" value="+customer.customerCode+" >"+customer.name+"("+customer.customerCode+")</option>").appendTo(".chosen-select");
	     	 $("#csel").append( "<option cusId="+customer.id+" value="+customer.customerCode+" >"+customer.name+"("+customer.customerCode+")</option>" );
	    	 $("#csel").trigger("liszt:updated");
	    	 $("#csel").trigger("chosen:updated");
	    	 $(".chosen-select").trigger("chosen:updated");
	     }
	});	
}
</script>
</head>
<body>
<div class="static_box1">
<div class="box1_topcenter2">
<div class="box1_topleft2">
<div class="box1_topright2">
</div>
</div>
</div>
<div class="box1_middlecenter">
<div class="box1_middleleft2">
<div class="box1_middleright2">
<div style="padding:0 20px 0 20px;">
<s:form name="frm" action="orders!add.action" method="post" theme="simple" id="frm">
	<input type="hidden" name="message" id="message" value="${message}" /> 
	<input name="successflag" id="successflag" value="${successflag}" type="hidden"/>
	<fieldset>
		<legend>销售订单基本信息</legend>
		<table width="100%" class="tableStyle" footer="normal" transMode="true" border="0">
	        <tr>
	          <td>订单编号:</td>
	          <td><input class="textinput validate[custom[noSpecialCaracters],length[0,30]]" type="text" name="orders.orderCode" placeholder="如果不填系统自动生成"/></td>
	          <td>下单日期:</td>
	          <td><input class="date validate[required]"  type="text" name="orders.orderDate"/><span class="star"> *</span></td>
	          <td>交付日期:</td>
	          <td><input class="date validate[required]" type="text" name="orders.deliveryDate"/><span class="star"> *</span></td>
	           
	        </tr>
	        
	   
	        <tr>
	          <td>客户名称:</td>
	          <td>
	          	<span style="width:124px;display: block;float: left;">
				 <select  class="chosen-select-deselect" onchange='cselFunc()' id="csel" class="textinput" style="height:24px;width:124px;" >
				   	<s:iterator  value="#request.cusList " var="cell" status="item">
						 <option cusId="${cell[0]}" value="${cell[2]}" >${cell[1]}(${cell[2]})</option>
					</s:iterator>
 				 </select>
 				 </span>
 				 <span onclick='return openAddWin()' width="20px" class="icon_add" style="margin-left:5px;margin-top: 2px; ">&nbsp</span>
 				 <input id="cusId" type="hidden" name="orders.customerId"  value="${cusList[0][0]}"/>
 				 
	          </td>
	          
	          <td>
	          	客户编号:
	          </td>
	          <td>
	           	<input id="selName" textinput style="width: 118px;  height: 18px;"  class="textinput" disabled type="text" value="${cusList[0][2]}" name="customerName"/> 
	          </td>
	          <td>公司经手人:</td>
	          <td>
	          
	          	<select class="chosen-select-deselect" style="height:24px;width:124px;" name="orders.empId" >
				   	<s:iterator  value="#request.empList" var="cell" status="item">
						 <option value="${cell.id}" >${cell.name}(${cell.serialNo})</option>
					</s:iterator>
 				 </select>
 				</td>
	     	</tr>
	      </table>
	</fieldset>
	<fieldset>
	<legend>销售订单详细信息</legend>
	  <table class="tableStyle" id="checkboxTable">
        <tr>
          <th>产品检索(名称或型号)</th>
          <th>产品名称</th>
          <th>规格/型号</th>
          <th>单位</th>
          <th>数量</th>
          <th>交付期</th>
          <th>备注</th>
          <th><input value="添加" id="addDefRow" currentId= class="button" type="button"></input></th>
        </tr>
        <tr>
          <td style="width: 130px;">
			 <select class="chosen-select-deselect" onchange='selFunc()' name="sel" style="width:95%;">
			   	<s:iterator  value="#request.proList " var="cell" status="item">
					 <option productId="${cell.id}" productCode="${cell.productCode}" productTypespec="${cell.typespec}" productUnit="${cell.unit}"  value="${cell.name}" >${cell.name}(${cell.typespec})</option>
				</s:iterator>
 			 </select>
 			  <input value="${proList[0].id}" type="hidden" name="ordersDetailList[0].productId"/>
 			   <input value="${proList[0].productCode}" type="hidden" name="ordersDetailList[0].productCode"/>
         </td>
          <td>
          	<input name="selText" style="width: 95%; height: 95%" disabled type="text" value="${proList[0].name}"/>
          	<input style="width: 100%; height: 100%"  type="hidden" value="${proList[0].name}" name="ordersDetailList[0].productName"/>
          </td>
          <td>
	          <input name="selType" style="width: 95%; height: 95%" disabled type="text" value="${proList[0].typespec}"/>
	          <input style="width: 100%; height: 100%"  type="hidden" value="${proList[0].typespec}" name="ordersDetailList[0].productTypespec"/>
          </td>
           <td style="width: 90px;">
          	<input style="width: 60px;float:left;" class="textinput simple validate[required]" value="" name="ordersDetailList[0].productUnit" id="Type_1_lable" readonly="readonly" type="text"/>
          	<input id="Type_1" targetid="85" value="" type="hidden"/>
          	<span  width="20px;" class="icon_find" onclick="chooseUnit('Type_1',0)">&nbsp</span>
          </td> 
          <td><input class="validate[required,custom[onlyNumber],length[0,6]]"  style="width: 95%; height: 95%"  type="text" name="ordersDetailList[0].num"/></td>
          <td><input class="date validate[required]" style="width: 95%; height: 95%"  type="text" name="ordersDetailList[0].deliveryDate"/></td>
          <td><input  style="width: 95%; height: 95%"  type="text" name="ordersDetailList[0].remarks"/></td>
          <td><input value="删除"  class="button" type="button" onclick="remove(this)"></input></td>
        </tr>
      </table>
      </fieldset>
</s:form>

</div>
</div>
</div>
</div>
<div class="box1_bottomcenter2">
<div class="box1_bottomleft2">
<div class="box1_bottomright2">
</div>
</div>
</div>
</div>
<%-- 	<table >
        <tr>
          <td><button id="save" onclick="return doSubmit();"><span class="icon_save">保存</span></button></td>
          <td><button onclick="return cancel();"><span class="icon_no">关闭</span></button></td>
        </tr>
      </table> --%>	
      <table width="100%" class="tableStyle" transmode="true">
		<tr>
			<td colspan="2">
			  <p>
			    <button onclick="return doSubmit();" type="button" id="save"><span class="icon_save">保 存</span> </button>
	            <button onclick="return cancel();" type="button"><span class="icon_no">关 闭</span> </button>
				</p>
			</td>
		</tr>
	 </table>
</body>
<script type="text/x-jquery-tmpl" id="addTr">
 <tr class="reqTr">
          <td style="width: 130px;">
			 <select class="chosen-select-deselect" onchange='selFunc({no})' name="sel{no}" style="width:95%;">
			   	<option>请选择产品</option>
				<s:iterator  value="#request.proList " var="cell" status="item">
					 <option productId="${cell.id}" productCode="${cell.productCode}" productTypespec="${cell.typespec}" productUnit="${cell.unit}"  value="${cell.name}" >${cell.name}(${cell.typespec})</option>
				</s:iterator>
 			 </select>
 			  <input value="${proList[0].id}" type="hidden" name="ordersDetailList[{no}].productId"/>
 			  <input value="${proList[0].productCode}" type="hidden" name="ordersDetailList[{no}].productCode"/>
         </td>
          <td>
			<input name="selText{no}" style="width: 95%; height: 95%" disabled type="text" value="${proList[0].name}" name="ordersDetailList[{no}].productName"/>
			<input style="width: 100%; height: 100%"  type="hidden" value="${proList[0].name}" name="ordersDetailList[{no}].productName"/>
		  </td>
          <td>
			<input name="selType{no}" style="width: 95%; height: 95%" disabled type="text" value="${proList[0].typespec}"/>
		 	 <input style="width: 100%; height: 100%"  type="hidden" value="${proList[0].typespec}" name="ordersDetailList[{no}].productTypespec"/>
         </td>
		<td style="width: 90px;">
          	<input style="width: 60px;float:left;" class="textinput simple validate[required]" value="" name="ordersDetailList[{no}].productUnit" id="Type_1_lable{no}" readonly="readonly" type="text"/>
          	<input id="Type_1{no}" targetid="85" value="" type="hidden"/>
          	<span  width="20px;" class="icon_find" onclick="chooseUnit('Type_1',{no})">&nbsp</span>
          </td> 
          <td><input class="validate[required,custom[onlyNumber],length[0,6]]"  style="width: 95%; height: 95%"  type="text" name="ordersDetailList[{no}].num"/></td>
         <td><input class="date validate[required]" style="width: 95%; height: 95%" value=""  type="text" name="ordersDetailList[{no}].deliveryDate"/></td>
          <td><input  style="width: 95%; height: 95%"  type="text" name="ordersDetailList[{no}].remarks"/></td>
           <td><input value="删除"  class="button" type="button" onclick="remove(this)"></input></td>
        </tr>
</script>
</html>