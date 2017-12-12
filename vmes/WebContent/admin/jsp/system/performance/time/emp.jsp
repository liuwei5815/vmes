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
	<input type="hidden" name="dateList" id="dateList" value="${dateList} "/>
	<input type="hidden" name="listLength" id="listLength" value="${list.size()} "/>
	<c:forEach items="${list}" var="cell" varStatus="st">
		<c:forEach items="${cell}" var="map" varStatus="sta">
			<input type="hidden" equipment="${map.key }" name="fault_${st.index }" id="fault_${st.index }" value="${map.value } "/>
		</c:forEach>
	</c:forEach> 
	<div id="container" style="width: 750px; height: 550px; margin: 0 auto"></div>
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
$(document).ready(function() {
	var listLength = $("#listLength").val();
	var record = new Array();
	var sun = listLength;
	for(var i=0;i<listLength;i++){
		var value = $("#fault_"+i+"").val().split("-");
		var data = new Array();
		for(var j=0;j<value.length;j++){
			data[j]= parseFloat(value[j]);
		}
		var obj1={"type": 'column',"name": $("#fault_"+i+"").attr("equipment"),"data": data};
		var obj2={"type": 'spline',"name": $("#fault_"+i+"").attr("equipment"),"data": data};
		record[i]=obj1;
		record[parseInt(sun)]=obj2;
		sun++;
	}
	console.log("data = " + data);
	console.log("record = " + record);
	//时间
	var dateList = $("#dateList").val().split("##");
	var describe = dateList[0]+"至"+dateList[dateList.length-1]+"人员有效工作时间占比";
	var dateCategories = new Array();
	for(var i=0;i<dateList.length;i++){
		dateCategories[i]= dateList[i];
	}
	console.log("dataCategories = " + dateCategories);
	var credits = {  
	           enabled:false  
	};
   	var title = {
      	text: describe   
   	};
   	var xAxis = {
      	categories: dateCategories,
      	labels: {
          	rotation: -45,
          	style: {
            	fontSize: '13px',
          		fontFamily: 'Verdana, sans-serif'
        	}
     	}
   	};
   	var yAxis = {
       	title: {
           	text: '有效工作时间占比'
       	},
       	labels: {
           	formatter: function () {
               	return this.value + '%';
          	}
       }
   	};
   	var labels = {
      	items: [{
        	style: {
               	left: '50px',
               	top: '18px',
               	color: (Highcharts.theme && Highcharts.theme.textColor) || 'black'
            }
      	}]
   	};
   	var totalColor = ['#42f46b', '#00cdcd', '#ffdd8c', '#007380', '#efb7b7', '#b4eeb4', '#617976', '#8b3cf2', '#c1cdcd', '#4089ef'];
   	var colors = new Array(record.length);
   	for (var i = 0; i < listLength * 2; i++) {
   		colors[i] = totalColor[i % listLength];
   	}
  	var series= record;      
      
   	var json = {}; 
	json.credits = credits;
	json.title = title;   
	json.xAxis = xAxis;
	json.yAxis = yAxis;
	json.labels = labels;  
	json.series = series;
	json.colors=colors;
   	$('#container').highcharts(json);  
});

/* $(document).ready(function(){ 
	if ($("input[name='finishtime']").val() == "" || $("input[name='quatime']").val() == "") {
		top.Dialog.alert("找不到该员工在所选时间段的工作情况", function() {
			top.Dialog.close();
		});
		return;	
	}
	loadEmp();
});
function loadEmp(){
	 var finishtime=$("input[name='finishtime']").val();
	 var quatime=$("input[name='quatime']").val();
	 var empName=$("input[name='empName']").val();
	 finishtime=finishtime.substring(0,finishtime.length-1);
	 var finishtimeData = finishtime.split(",");
	 quatime=quatime.substring(0,quatime.length-1);
	 var quatimeData = quatime.split(",");
	 var arr=new Array();
	 for(var i = 0;i < quatimeData.length; i++) {
		 arr.push(+quatimeData[i]);//字符串转数字
	 }
	 var chart = {
		      type: 'column'
		   };
		   var title = {
		      text: empName + '有效工作时间占比' 
		   };
		   var subtitle = {
		      text: ''  
		   };
		   var xAxis = {
		      categories: finishtimeData,
		      crosshair: true
		   };
		   var yAxis = {
		      min: 0,
		      title: {
		         text: '人员有效工作时间占比 (%)'         
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
		         borderWidth: 0,
		      }
		   };  
		   var credits = {
		      enabled: false
		   };
		   
		   var series= [{
		        name: '有效工作时间占比',
		            data:arr
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
} */
</script>
</body>
</html>




