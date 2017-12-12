<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctxAdmin}/js/jquery.tmpl.min.js"></script>
<script type="application/javascript" src="${ctxAdmin}/js/jstree/jstree.js?v=4.0"></script>
<link rel="stylesheet" href="${ctxAdmin}/js/jstree/themes/default/style.min.css" />
<script type="application/javascript" src="${ctxAdmin}/js/jstree/overmenu/jstree.plugin.overmenu.min.js"></script>
<link rel="stylesheet" href="${ctxAdmin}/js/jstree/overmenu/overmenu.min.css" />
<title>设备产能利用率</title>
</head>
<body>
<div class="position">
	<div class="center">
		<div class="left">
			<div class="right"><span>当前位置：设备状态管理 >>设备产能利用率</span></div>
		</div>
	</div>
</div>
<div class="box2" panelTitle="设备基本信息" roller="false">
 <form method="post" target="querytable" action="admin/equipment!capaInfor_query.action" id="frm">
 	<input type="hidden" id="employeeId" value="" name="employeeId"/>
 	<input type="hidden" id="departmentId" value="0" name="departmentId"/>
 	<input type="hidden" id="orgtree" value='${orgtree }'/>
 	<input type="hidden" name="currPage" id="currPage" value="${currpage}" /> 
 	<input type="hidden" name="perPageRows" id="perPageRows" value="${perPageRows}" />
 	<input type="hidden" name="deptId" id="deptId" value="${deptId}" />
 	<input type="hidden" name="eqType" id="eqType" value="" />
	<table>
		<tr>
			<td>设备类型：</td>
			<td><input style="width:120px;" name="equipType" type="text" id="equipType" class="validate[length[0,15]]" onclick="return queryEquipType()"/></td>
			<td>设备名称：</td>
			<td><input style="width:120px;" name="eqName" type="text" id="eqName" class="validate[length[0,15]]"/></td>
			<td>系统设备编号：</td>
			<td><input style="width:120px;" name="eqCode" type="text" class="validate[length[0,15]]"/></td>
			<td>用户设备编号：</td>
			<td><input style="width:120px;" name="eqUserCode" type="text"  id="eqUserCode" class="validate[length[0,15]]"/></td>
		</tr>
	</table>
	<table>
		<tr>
			<td>时间段：</td>
			<td>
				<input class="date" name="beginDate" id="beginDate" type="text" value="${beginDate}" />
				至
				<input class="date" name="endDate" id="endDate" type="text" value="${endDate}" />
			</td>
			<td colspan="20%">
				<button type="button" onclick="return doSubmit();"><span class="icon_find">查询</span></button>&nbsp;&nbsp; 
			</td>
			<td>
				<button type="button" onclick="searchYX();"><span class="icon_find2">产能利用率(饼图)</span></button>
		     	<button type="button" onclick="searchZH();"><span class="icon_find2">产能利用率(趋势图)</span></button>
			</td>
		</tr>
	</table>
 </form>
</div>
<div id="scrollContent">
	<iframe scrolling="no" width="100%" frameBorder=0 id="querytable" name="querytable" 
	src="${ctx}/admin/equipment!capaInfor_query.action" onload="iframeHeight('querytable')" 
	allowTransparency="true">
	</iframe>
	<%-- <table style="width: 100%;">
		<tr>
			<td style="width: 20%" valign="top">
				<div style="width: 100%; overflow: auto; height: 100%;">
					<div id="jstree_div"></div>
				</div>
			</td>
			<td style="width: 80%;" valign="top">
				<div style="overflow: auto;height: 100%;">
				<iframe scrolling="no" width="100%" frameBorder=0 id="querytable"
						name="querytable"
						src="${ctx}/admin/equipment!capaInfor_query.action"
						onload="iframeHeight('querytable')" allowTransparency="true">
				</iframe>
				</div>
			</td>
		</tr>
	</table> --%>
</div>
<pt:page action="${ctx}/admin/equipment!capaInfor_query.action" target="querytable"></pt:page>
<script>
function doSubmit(){
	$("#frm").submit();	
}

function qk(){
	$("#departmentId").val("");
	$("#eqName").val("");
	$("#deptName").val("");
	$("#eqUserCode").val("");
	$("#eqCode").val("");
	
}

/* function searchYX(){
	 document.querytable.yxSearch();
	
}

function searchZH(){
	document.querytable.yxSearchZH();
}
 */
function searchYX(){
	 var startDate= new Date($("#beginDate").val());
		if(startDate == "Invalid Date"){
			top.Dialog.alert("开始时间不能为空");
			return;
		}
		var endDate=new Date($("#endDate").val());
		if(endDate == "Invalid Date"){
			top.Dialog.alert("结束时间不能为空");
			return;
		}
		if(startDate.getTime()>endDate.getTime()){
			top.Dialog.alert("开始时间不能早于结束时间");
			return;
		}
		
		if(startDate.getTime()>endDate.getTime()){
			top.Dialog.alert("开始时间不能早于结束时间");
			return;
		}
		
		if(DateDiff($("#beginDate").val(),$("#endDate").val())>31){
			top.Dialog.alert("最多只能查看一个月的数据统计");
			return;
		}
	 
	var id =[];
	$(window.frames["querytable"].document).find("input[type=checkbox]:checked").each(function(i){
		var required = $(this).val();
		if(required){
			id.push(required);	
		}
	});
    if(id.length==0){
    top.Dialog.alert("请先选择至少一个设备再进行查看");
    return false;
    }
    var beginDate= $("#beginDate").val();
    var endDate= $("#endDate").val();
    console.log(id)
       var dia = new top.Dialog();
		dia.Title = "设备产能利用率";
		//饼状图
		dia.URL = "${ctx}/admin/equipment!capainforma_dialog.action?eqId="+id+"&beginDate="+beginDate+"&endDate="+endDate;
		dia.ID = "sbyxlyl";
		dia.CancelButtonText = "&nbsp;关 &nbsp;&nbsp;&nbsp;&nbsp;闭&nbsp;";
		dia.Height = 600;
		dia.Width = 800;
		dia.CancelEvent = function() {
			dia.close();
		};
		dia.show();
}


function searchZH(){
	var startDate= new Date($("#beginDate").val());
	if(startDate == "Invalid Date"){
		top.Dialog.alert("开始时间不能为空");
		return;
	}
	var endDate=new Date($("#endDate").val());
	if(endDate == "Invalid Date"){
		top.Dialog.alert("结束时间不能为空");
		return;
	}
	if(startDate.getTime()>endDate.getTime()){
		top.Dialog.alert("开始时间不能早于结束时间");
		return;
	}
	
	if(DateDiff($("#beginDate").val(),$("#endDate").val())>31){
		top.Dialog.alert("最多只能查看一个月的数据统计");
		return;
	}
	
	var id =[];
	$(window.frames["querytable"].document).find("input[type=checkbox]:checked").each(function(i){
		var required = $(this).val();
		if(required){
			id.push(required);	
		}
	});
    if(id.length==0){
    top.Dialog.alert("请先选择至少一个设备再进行查看");
    return false;
    }
    var beginDate= $("#beginDate").val();
    var endDate= $("#endDate").val();
       var dia = new top.Dialog();
		dia.Title = "设备产能利用率";
		//组合图
		dia.URL = "${ctx}/admin/equipment!capainforma_dialog2.action?eqId="+id+"&beginDate="+beginDate+"&endDate="+endDate;
		dia.ID = "sbyxlyl";
		dia.CancelButtonText = "&nbsp;关 &nbsp;&nbsp;&nbsp;&nbsp;闭&nbsp;";
		dia.Height = 600;
		dia.Width = 800;
		dia.CancelEvent = function() {
			dia.close();
		};
		dia.show();
}

function  DateDiff(sDate1,  sDate2){    //sDate1和sDate2是2002-12-18格式  
    var  aDate,  oDate1,  oDate2,  iDays  
    aDate  =  sDate1.split("-")  
    oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])    //转换为12-18-2002格式  
    aDate  =  sDate2.split("-")  
    oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])  
    iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24)    //把相差的毫秒数转换为天数  
    return  iDays  
}

function queryEquipType(){
	var diag = new top.Dialog();
	diag.ID="choose";
	diag.Title = "选择设备类型";
	diag.URL = "${ctx}/admin/equipment!queryEquipmentType.action";
	diag.Height=500;
	diag.Width=300;
	diag.OKEvent=function(){
		var treenode= diag.innerFrame.contentWindow.getTreeNode();
		if(!treenode){
			top.Dialog.alert("请至少选择一项");	
			return false;
		}
		$("#eqType").val(treenode.text);
		$("#equipType").val(treenode.text);
		diag.close();
	};
	diag.show();
}
</script>
</body>
</html>




