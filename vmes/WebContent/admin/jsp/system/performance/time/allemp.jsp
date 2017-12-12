<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/core" prefix="c"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=eYf9sA6yVTFHlh9ytU4a0EYY"></script>
<script type="text/javascript" charset="utf-8" src="${ctxAdmin}/js/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxAdmin}/js/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${ctxAdmin}/js/third-party/highcharts/highcharts.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>人员绩效</title>
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
	<input type="hidden" name="deptName" value="${deptName}"/>
	<input type="hidden" name="quatime" value="${quaData}"/>
	<input type="hidden" name="empName" value="${empName}"/>
	<table width="100%" class="tableStyle" transmode="true">
	 	<div id="container" style="width: 550px; height: 400px; margin: 0 auto"></div>
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
<script type="text/javascript">
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
	if ($("input[name='quatime']").val() == "") {
		top.Dialog.alert("找不到该部门在所选时间的工作情况", function() {
			top.Dialog.close();
		});
		return;
	}
	loadAllEmp();
});
/**
 * highCharts dept
 */
function loadAllEmp(){
	//后台数据处理
	 var deptName=$("input[name='deptName']").val();
	 var quatime=$("input[name='quatime']").val();
	 var empName=$("input[name='empName']").val();
	 quatime=quatime.substring(0,quatime.length-1);
	 var quatimeData = quatime.split(",");
	 var array=new Array();
	 for(var i = 0;i < quatimeData.length; i++) {
		 array.push(+quatimeData[i]);//字符串转数字
	 }
	 deptName=deptName.substring(0,deptName.length-1);
	 var deptNameData = deptName.split(",");
	 empName=empName.substring(0,empName.length-1);
	 var empNameData = empName.split(",");
	 var data=new Array();
	 for(var i = 0;i < deptNameData.length; i++) {
		 data.push(deptNameData[i]+"-"+empNameData[i]);
	 }
	//后台数据处理完毕
	//highchart展示数据
	 var chart = {
		      type: 'column'
		   };
		   var title = {
		      text: '有效时间占比'   
		   };
		   var subtitle = {
		      text: ''  
		   };
		   var xAxis = {
		      categories: data,
		      crosshair: true
		   };
		   var yAxis = {
		      min: 0,
		      title: {
		         text: '有效时间占比'         
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
		        name: '有效时间占比',
		            data: array
		        }];     
		      
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
		   $('#container').highcharts(json);
}
</script>
</body>
</html>




