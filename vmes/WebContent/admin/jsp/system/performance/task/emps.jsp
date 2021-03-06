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
	<!-- [{deptName=产品工程部, wcl=12.64, empCode=999, empName=王峥}, {deptName=后勤部, wcl=102.00, empCode=1234, empName=水电费}] -->
	<input type="hidden" name="message" id="message" value="${message}" />
	<input type="hidden" name="deptName" value="${deptStr}"/>
	<input type="hidden" name="empName" value="${empStr}"/>
	<input type="hidden" name="wcl" value="${wclStr}"/>
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
});
/**
 * highCharts dept
 */
 function loadHighCharts(){
	var deptName=$("input[name='deptName']").val();
	var empName=$("input[name='empName']").val();
	var wcl=$("input[name='wcl']").val();
	 
	 deptName=deptName.substring(0,deptName.length-1);
	 var deptNameData = deptName.split(",");
	 console.log("deptNameData:"+deptNameData);
	 empName=empName.substring(0,empName.length-1);
	 var empNameData = empName.split(",");
	 console.log("empNameData:"+empNameData);
/* 	 wcl=wcl.substring(0,empName.length-1); */
	 var wclData = wcl.split(",");
	 console.log("wclData:"+wclData);
	 var datas="";
	 for(var i = 0; i < deptNameData.length; i++) {
		 datas = datas + deptNameData[i] + "-" + empNameData[i] + " ";
	 }
	 console.log("data:"+datas);
	 var credits = {  
	           enabled:false  
	 		};
	 var chart = {
		       plotBackgroundColor: null,
		       plotBorderWidth: null,
		       plotShadow: false
		   };
		   var title = {
		      text: '任务完成率'   
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
		             //['产品工程部-王峥',100],
		             //['后勤部-水电费',102],
		             [datas + ": 完成率", parseInt($("input[name='wcl']").val())],
		             [datas + ": 未完成率", 100-parseInt($("input[name='wcl']").val())],
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




