<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>图片裁剪</title>

<script src="${ctx }/admin/js/jcrop/jquery.Jcrop.js"></script>
<script src="${ctx }/admin/js/serializeJson.js"></script>
<script type="text/javascript">
  jQuery(function($){
    var jcrop_api,
        boundx,
        boundy,

        // Grab some information about the preview pane
        $preview = $('#preview-pane'),
        $pcnt = $('#preview-pane .preview-container'),
        $pimg = $('#preview-pane .preview-container img'),

        xsize =$pcnt.width(),
        ysize = $pcnt.height();
    
    //console.log('init',[xsize,ysize]);

    $('#target').Jcrop({
      allowSelect:false,
      allowResize:false,
      onChange: updatePreview,
      onSelect: updatePreview,
      setSelect: [0,0,'${width}','${height}'],
      boxWidth:500
     
    },function(){
      // Use the API to get the real image size
      var bounds = this.getBounds();
      boundx = bounds[0];
      boundy = bounds[1];
      // Store the API in the jcrop_api variable
      jcrop_api = this;
		
      // Move the preview into the jcrop container for css positioning
      $preview.appendTo(jcrop_api.ui.holder);

  		var c = jcrop_api.getBounds();
		$("#imgWidth").html(c[0]);
		$("#imgHeight").html(c[1]);
        
    });

    function updatePreview(c)
    {
      if (parseInt(c.w) > 0)
      {
        var rx = xsize / c.w;
        var ry = ysize / c.h;

        $pimg.css({
          width: Math.round(rx * boundx) + 'px',
          height: Math.round(ry * boundy) + 'px',
          marginLeft: '-' + Math.round(rx * c.x) + 'px',
          marginTop: '-' + Math.round(ry * c.y) + 'px'
        });
        $("#x").val(c.x);
        $("#y").val(c.y);
        $("#height").val(c.h);
        $("#width").val(c.w);
            
      }
    };
    $("#cut").click(function(){
        //console.log(JSON.stringify($("#cutForm").serializeJson()));
        $.ajax({
			url:'${ctxAdmin}/jcrop!jcrop.action',
		    type:'post',
		    dataType:'json',
		    data:$("#cutForm").serializeJson(),
	        success:function(data){
				if(data.code==0){
					if(successCallBack){
						successCallBack(data.body)
					};
				}
				else{
					top.Dialog.alert("裁剪失败");
				}
			}	  
         });
	 });
     $("#releaseSelect").click(function(){
         var height = $("#cjHeight").val();
         var width  = $("#cjWidth").val();
         if(isNaN(height)){
        	 top.Dialog.alert("选框高度必须为数字");
        	 return false;
         }
         if(isNaN(width)){
        	 top.Dialog.alert("选框宽度必须为数字");
        	 return false;
            }
    	 jcrop_api.setSelect([0,0,width,height]);
       })


  });
  var successCallBack="";
  function setSuccessCallBack(callBackStr){
	  successCallBack=callBackStr;
  }
  function evalSuccessCallBack(img){
	  if(successCallBack!=""){
   			eval(successCallBack.replace(/\\$picPath\\$/g,img));
	   }
  }

</script>
<link rel="stylesheet"
	href="${ctx }/admin/jsp/common/jcrop/css/main.css" type="text/css" />
<link rel="stylesheet"
	href="${ctx }/admin/jsp/common/jcrop/css/demos.css" type="text/css" />
<link rel="stylesheet"
	href="${ctx }/admin/jsp/common/jcrop/css/jquery.Jcrop.css"
	type="text/css" />
<style type="text/css">
/* Apply these styles only when #preview-pane has
   been placed within the Jcrop widget */
.jcrop-holder #preview-pane {
	display: block;
	position: absolute;
	z-index: 2000;
	top: 10px;
	right: -280px;
	padding: 6px;
	border: 1px rgba(0, 0, 0, .4) solid;
	background-color: white;
	-webkit-border-radius: 6px;
	-moz-border-radius: 6px;
	border-radius: 6px;
	-webkit-box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.2);
	-moz-box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.2);
	box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.2);
}

/* The Javascript code will set the aspect ratio of the crop
   area based on the size of the thumbnail preview,
   specified here */
#preview-pane .preview-container {
	width: 250px;
	height: 170px;
	overflow: hidden;
}
</style>

</head>
<body>
<div id="cutParam" >
		<form id="cutForm">
		<input type="hidden" id="x" value="0"  name="x"/>
		<input type="hidden" id="y" value="0" name="y"/>
		<input type="hidden" id="width" value="${width }" name="width"/>
		<input type="hidden" id="height" value="${height }" name="height"/>
		<input type="hidden" id="swidth" value="${swidth }" name="swidth"/>
		<input type="hidden" id="sheight" value="${sheight }" name="sheight"/>
		<input type="hidden" name="imagePath" value="${imagePath }"/> 
		</form>
</div>
<div class="container"> 	
<div class="row">
<div class="span12">
<div class="jc-demo-box">
<img src="${ctx}/<s:property  value='@com.xy.cms.common.CommonFunction@getImageValue(\"picUrl\",imagePath,\"bg\")' />" id="target" alt="[Jcrop Example]" />
<div id="preview-pane">
	<div class="preview-container">
 		<img src="${ctx }/<s:property  value='@com.xy.cms.common.CommonFunction@getImageValue(\"picUrl\",imagePath,\"bg\")' />" class="jcrop-preview" alt="Preview" />
	</div>
	<div>原图大小:宽:<span id="imgWidth"></span> 高:<span id="imgHeight"></span> </div>
	<div>裁剪区大小:宽:<input type="text" id="cjWidth" style="width: 40px;" value="${width }"></input>高:<input type="text" style="width: 40px;" id="cjHeight" value="${height}"></input>   </div>
	
	<div><button id="cut">裁剪</button><button id="releaseSelect">重置选框</button> </div>
</div>




</div>
</div>
</div>
</div>

</body>
</html>




