<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/core" prefix="c"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=eYf9sA6yVTFHlh9ytU4a0EYY"></script>
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
	<!-- {deptName=IT部, wcl=10.02, empCode=123, empName=测试} -->
	<s:form action="" method="post" theme="simple" id="frm">
	<input type="hidden" name="message" id="message" value="${message}" />
	<input type="hidden" value="${empTask}" name="empTask"/>
	<input type="hidden" name="deptName" value="${empTask.deptName}"/>
	<input type="hidden" name="empName" value="${empTask.empName}"/>
	<input type="hidden" name="wcl" value="${empTask.wcl }"/>
	<table width="100%" class="tableStyle" transmode="true">
	 	<div id="containerDept" style="width: 550px; height: 400px; margin: 0 auto"></div>
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
<script type="text/javascript" charset="utf-8" src="${ctxAdmin}/js/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxAdmin}/js/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${ctxAdmin}/js/third-party/highcharts/highcharts.js"></script>
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
	loadHighCharts();
	loadEmp();
});
/**
 * highCharts dept
 */
 function loadHighCharts(){
	var empTask=$("input[name='empTask']").val();
	var deptName=$("input[name='deptName']").val();
	var empName=$("input[name='empName']").val();
	console.log(empTask);
	
	 var deptData = [];
	 var credits = {  
	           enabled:false  
	 		};
	 var chart = {
		       plotBackgroundColor: null,
		       plotBorderWidth: null,
		       plotShadow: false
		   };
		   var title = {
		      text: deptName+"-"+empName+'任务完成率'   
		   };      
		   var tooltip = {
		      pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		   };
		   var plotOptions = {
		      pie: {
		         allowPointSelect: true,
		         cursor: 'pointer',
		         dataLabels: {
		            enabled: false           
		         },
		         showInLegend: true
		      }
		   };
		   var colors=['#50B432','#ED561B'];
		   var series= [{
		      type: 'pie',
		      name:$("input[name='deptName']").val(),
		      data: [
		             [$("input[name='deptName']").val()+"-"+$("input[name='empName']").val()+'：完成',   parseInt($("input[name='wcl']").val())],
		             [$("input[name='deptName']").val()+"-"+$("input[name='empName']").val()+'：未完成', 100-parseInt($("input[name='wcl']").val())], 
		          ]
		   }];     
		   var json = {};   
		   json.credits = credits;
		   json.chart = chart; 
		   json.title = title;     
		   json.tooltip = tooltip;  
		   json.series = series;
		   json.plotOptions = plotOptions;
		   json.colors=colors;
		   $('#containerDept').highcharts(json);  
}
</script>
</html>




