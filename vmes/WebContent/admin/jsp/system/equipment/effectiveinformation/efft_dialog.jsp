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
	
	<s:form action="" method="post" theme="simple" id="frm">
	<input type="hidden" name="message" id="message" value="${message}" />
	<input type="hidden" name="efcData" value="<c:if test="${not empty efcData }">${efcData.efc }</c:if><c:if test="${ empty efcData }">0</c:if>"/>
	<input type="hidden" name="disEfcData" value="<c:if test="${not empty disEfcData}">${disEfcData.disEfc }</c:if><c:if test="${ empty disEfcData}">0</c:if>"/>

	<table width="100%" class="tableStyle" transmode="true">
	 	<div id="container" style="width: 750px; height: 550px; margin: 0 auto"></div>
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
$(document).ready(function() { 
	   var efcData=$("input[name='efcData']").val();
	   var disEfcData=$("input[name='disEfcData']").val();
	
	   var credits = {  
	           enabled:false  
	  };
	   var chart = {
	       plotBackgroundColor: null,
	       plotBorderWidth: null,
	       plotShadow: false
	   };
	   var title = {
	      text: '设备有效利用率'   
	   };      
	   var tooltip = {
	      pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	   };
	   var plotOptions = {
	      pie: {
	         allowPointSelect: true,
	         cursor: 'pointer',
	         dataLabels: {
	            enabled: true,
	            format: '<b>{point.name}%</b>: {point.percentage:.1f} %',
	            style: {
	               color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
	            }
	         }
	      }
	   };
	   var colors=['#A4D3EE','#B22222','#BCEE68'];
	   var series= [{
	      type: 'pie',
	      name: '占比',
	      data: [
	         ['有效利用率',  parseFloat(efcData)],
	         ['故障率',      parseFloat(disEfcData)],
	         ['其他',   parseFloat(100)-parseFloat(efcData)-parseFloat(disEfcData)]
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
	   $('#container').highcharts(json);  
	});
</script>
</html>




