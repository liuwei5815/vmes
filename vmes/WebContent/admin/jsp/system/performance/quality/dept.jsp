<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/core" prefix="c"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@include file="/admin/jsp/common/image_upload.jsp"%>
<%-- <%@include file="/admin/jsp/system/model/common/model.jsp" %> --%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=eYf9sA6yVTFHlh9ytU4a0EYY"></script>
<script type="text/javascript" charset="utf-8" src="${ctxAdmin}/js/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxAdmin}/js/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${ctxAdmin}/js/third-party/highcharts/highcharts.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${deptWcl.name}</title>
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
	
	<s:form action="" method="post" theme="simple" id="frm">
	<input type="hidden" name="message" id="message" value="${message}" /> 
	<input type="hidden" name="successflag" id="successflag" value="${successflag}" />
	<input type="hidden" name="deptId" value="${deptId}"/>
	<input name="deptName" type="hidden" value="${deptWcl.name}"/>
	<input name="deptWcl" type="hidden" value="${deptWcl.wcl}"/>
	<input name="stafList" type="hidden" value="${stafList}"/>
	<input name="empName" type="hidden" value="${nameStr}"/>
	<input name="empQua" type="hidden" value="${quaStr}"/>
	<table width="100%" class="tableStyle" transmode="true">
		<td>
			<select id="changeData" onchange="getData();">
				<option value="desc" selected>前十名</option>
				<option value="asc">后十名</option>
			</select>
		</td>
	 	<div id="containerEmp" style="width: 550px; height: 400px; margin: 0 auto"></div>
	 	<div id="containerA" style="width: 550px; height: 400px; margin: 0 auto;"></div>
	</table>
	</s:form>
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

<script type="text/javascript">
/**
 * 表单提交
 */
function doSubmit(){
	$("#frm").submit();	
}
/**
 * 关闭窗口
 */
function cancel(){
	top.Dialog.close();
}
/**
 * 页面加载
 */
$(document).ready(function(){ 
	loadHighCharts();
	loadEmp();
	loadEmpA();	
});
function getData() {
	var departmentId=$("input[name='deptId']").val();
	var type=$("#changeData").val();
	var claimStart=$("input[name='claimStart']").val();
	var claimEnd=$("input[name='claimEnd']").val();
	$.ajax({
	    type: "POST",
	    url: "${ctx}/admin/qualityAction!queryDeptTaskByAjax.action",
	    cache: false,
	    dataType:"json",
	    data : {
	   	 	"deptId": departmentId,
			"type" : type,
			"claimStart":claimStart,
			"claimEnd":claimEnd
		},
	    success: function(data){
	   		if(data.successflag=="1"){
	   			$("input[name='deptId']").val(data.deptId);
	   			$("input[name='empName']").val(data.nameStr);
	   			$("input[name='empQua']").val(data.quaStr);
	   			loadHighCharts();
	   			loadEmp();
	   			loadEmpA();
	    	}
	   		else{
	   			top.Dialog.alert(data.msg);
	   		}
	    }
	});
}
/**
 * highCharts dept
 */
function loadHighCharts(){
	 
}
function loadEmp(){
	var name=$("input[name='empName']").val();
	var wcl=$("input[name='empQua']").val();
	name=name.substring(0,name.length-1);
	var nameData = name.split(",");
	wcl=wcl.substring(0,wcl.length-1);
	var wclData=wcl.split(",");
	var wclA=new Array();
	for(var i = 0;i < wclData.length; i++) {
		wclA.push(parseInt(wclData[i]));
	}
	console.log("loadEmp wclA = " + wclA);
	var chart = {
    	type: 'column'
   	};
	var title = {
	   text: '部门人员生产合格率'
	};
	var subtitle = {
	   text: ''
	};
	var xAxis = {
	   categories: nameData,
	   crosshair: true
	};
	var yAxis = {
	   min: 0,
	   title: {
	      text: '生产合格率 (%)'
	   }
	};
	var tooltip = {
	   headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	   pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	      '<td style="padding:0"><b>{point.y:.1f} %</b></td></tr>',
	   footerFormat: '</table>',
	   shared: true,
	   useHTML: true
	};
	var plotOptions = {
	   column: {
	      pointPadding: 0.2,
	      borderWidth: 0
	   }
	};  
	var credits = {
	   enabled: false
	};
	
	var series= [{
		name: '合格',
	    data: wclA
	      }  
	     ];     
	   
	var json = {};   
	json.chart = chart; 
	json.title = title;   
	json.subtitle = subtitle; 
	json.tooltip = tooltip;
	json.xAxis = xAxis;
	json.yAxis = yAxis;  
	json.series = series;
	json.plotOptions = plotOptions;  
	json.credits = credits;
	$('#containerEmp').highcharts(json);
}
function loadEmpA(){
	var name=$("input[name='empName']").val();
	var wcl=$("input[name='empQua']").val();
	name=name.substring(0,name.length-1);
	var nameData = name.split(",");
	wcl=wcl.substring(0,wcl.length-1);
	var wclData=wcl.split(",");
	var wclA=new Array();
	for(var i = 0;i < wclData.length; i++) {
		wclA.push(parseInt(wclData[i]));
	}
	console.log("loadEmpA wclA = " + wclA);
	var title = {
	     text: '部门人员生产合格率'   
	};
	var subtitle = {
	   text: ''
	};
	var xAxis = {
		categories: nameData,
	};
	var yAxis = {
	   title: {
	      text: '生产合格率 (%)'
	   },
	   plotLines: [{
	      value: 0,
	      width: 1,
	      color: '#808080'
	   }]
	};   
	
	var tooltip = {
		valueSuffix: '%'
	}
	
	var legend = {
		layout: 'vertical',
		align: 'right',
		verticalAlign: 'middle',
		borderWidth: 0
	};
	
	var series =  [
	{
	   name: '合格',
	   data: wclA
	  }  
	];
	
	var json = {};
	
	json.title = title;
	json.subtitle = subtitle;
	json.xAxis = xAxis;
	json.yAxis = yAxis;
	json.tooltip = tooltip;
	json.legend = legend;
	json.series = series;
	
	$('#containerA').highcharts(json);
}
</script>
</html>




