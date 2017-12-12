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
<script src="${ctxAdmin}/js/third-party/highcharts/highcharts.js"></script>
<title>数据表</title>
<script >

</script>

</head>
<body>
<input type="hidden" name="histogram" id="histogram" value="${wclHistogram}"/>
<div id="container" style="width: 550px; height: 450px; margin: 0 auto">
 
</div>
<script language="JavaScript">
$(document).ready(function() { 
	var histogram = $("#histogram").val().split("-");
	console.log(histogram)
	var record = new Array();
	var data = new Array();
	for(var i=0;i<histogram.length;i++){
		data[i] = parseFloat(histogram[i]);
	}
	var obj1={"type": 'column',"name": "1","data": data};
	var obj2={"type": 'spline',"name": "2","data": data};
	record[0]=obj1;
	record[1]=obj2;
	console.log(record)
	var dateCategories = new Array();
	for(var i=0;i<histogram.length;i++){
		dateCategories[i]= parseInt(i)+1;
	}
	var credits = {  
		enabled:false  
	};
	var title = {
   		text: '任务合格率'
	};
	 var yAxis = {
       title: {
           text: '任务合格率占比（%）'
       },
       labels: {
           formatter: function () {
               return this.value + '%';
           }
       }
   };
	var xAxis = {
		categories: dateCategories
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
	var series= record;     
		      
		   var json = {};   
		   json.credits = credits;
		   json.title = title;   
		   json.xAxis = xAxis;
		   json.yAxis = yAxis;
		   json.labels = labels;  
		   json.series = series;
		   $('#container').highcharts(json); 
	/* var histogram = $("#histogram").val().split("-");
	console.log(histogram)
	var record = new Array();
	var sun = histogram.length;
	for(var i=0;i<histogram.length;i++){
		var data = new Array();
		data[i] = histogram[i];
	}
	var obj1={"type": 'column',"name": "1","data": data};
	var obj2={"type": 'spline',"name": "1","data": data};
	record[0]=obj1;
	record[1]=obj2;
	var dateCategories = new Array();
	for(var i=0;i<histogram.length;i++){
		dateCategories[i]= i+"";
	}
	var credits = {  
	           enabled:false  
	};
   var title = {
      text: '任务完成率'
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
           text: '任务完成率'
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
   var colors=['#8B2323','#A2CD5A'];
  var series= [{
	  type: 'column',
      name: '小张',
      data: [3, 2, 1, 3, 4]
  	}, 
  	{
        type: 'spline',
        name: '平均值',
        data: [3, 2.67, 3, 6.33, 3.33],
        marker: {
            lineWidth: 2,
            lineColor: Highcharts.getOptions().colors[3],
            fillColor: 'white'
        }
  }];      
      
   var json = {}; 
   json.credits = credits;
   json.title = title;   
   json.xAxis = xAxis;
   json.yAxis = yAxis;
   json.labels = labels;  
   json.series = series;
   json.colors=colors;
   $('#container').highcharts(json);   */
});
</script>
</body>


</html>





