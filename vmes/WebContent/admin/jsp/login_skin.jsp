<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/tags/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!--框架必需start-->
<script type="text/javascript" src="${ctx}/frame/js/jquery-1.4.js"></script>
<script type="text/javascript" src="${ctx}/frame/js/framework.js"></script>
<link href="${ctx}/frame/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link  rel="stylesheet" type="text/css" id="skin"/>
<!--框架必需end-->
<script type="text/javascript" src="${ctx}/frame/js/pic/jomino.js"></script>
<script>
$(function(){
	$('.framegrid.nocaption').each(function(){
		$(".framegrid_cover", this).css({
			top:"128px",
			height:"0px"
		})
		$(".framegrid_title", this).hide()
		$(".framegrid_con", this).hide()
	})
	$('.framegrid.nocaption').hover(function(){
		$(".framegrid_cover", this).stop().animate({top:'58px',height:'70px'},{queue:false,duration:160});
		$(".framegrid_title", this).show()
		$(".framegrid_con", this).show()
	}, function() {
		$(".framegrid_cover", this).stop().animate({top:'125px',height:'0px'},{queue:false,duration:160});
		$(".framegrid_title", this).hide()
		$(".framegrid_con", this).hide()
	});
	$("button").each(function(){
		$(this).css({
			"paddingLeft":"5px",
			"width":"90px"
		})
		$(this).hover(function(){
			$(this).addClass("button_hover");
		},function(){
			$(this).removeClass("button_hover");
		})
	})
	
})
function showPreview(str){
	top.Dialog.open({InnerHtml:"<div style=\"text-align:center;\"><img width='900' height='544' src='images/selectTemp/"+str+"'/></div>",Title:"皮肤预览",Width:920,Height:550});
}
</script>
<style>
	.framegrid_con{
		padding:0 5px 0 0;
		text-align:right;
		height:20px;
		overflow:hidden;
		line-height:20px;
	}
	button{
		background:transparent url(include/btn_bg.jpg) repeat scroll 0 0;
		border:1px solid #83b1f2;
		font-size:12px;
		height:22px;
		line-height:20px;
		>margin-right:4px;
		width:80px;
	}
	button span{
		cursor:default;
	}
	.button_hover{
	background:transparent url(include/btn_bg_hover.jpg) repeat scroll 0 0!important;
}
</style>
<body>
	<div style="padding-left:10px;padding-bottom:10px;">
	<div class="clear"></div>
	<div class="framegrid nocaption">
		<div class="picItem3"><img src="../images/selectTemp/login_skin1_small.jpg"/></div>	
		<div class="framegrid_cover">
			<div class="framegrid_title">皮肤名称：skin1<br />主题风格：blue</div>
			<div class="framegrid_con">
				
			</div>
		</div>
		<div class="ali02"><button onclick="showPreview('login_skin1.jpg')"><span class="icon_find">点击预览</span></button></div>
	</div>
	
	<div class="framegrid nocaption">
		<div class="picItem3"><img src="../images/selectTemp/login_skin20_small.jpg"/></div>	
		<div class="framegrid_cover">
			<div class="framegrid_title">皮肤名称：skin20<br />主题风格：blue</div>
			<div class="framegrid_con">
				
			</div>
		</div>
		<div class="ali02"><button onclick="showPreview('login_skin20.jpg')"><span class="icon_find">点击预览</span></button></div>
	</div>
	
	<div class="framegrid nocaption">
		<div class="picItem3"><img src="../images/selectTemp/login_skin21_small.jpg"/></div>	
		<div class="framegrid_cover">
			<div class="framegrid_title">皮肤名称：skin21<br />主题风格：darkBlue</div>
			<div class="framegrid_con">
				
			</div>
		</div>
		<div class="ali02"><button onclick="showPreview('login_skin21.jpg')"><span class="icon_find">点击预览</span></button></div>
	</div>
	
	<div class="framegrid nocaption">
		<div class="picItem3"><img src="../images/selectTemp/login_skin22_small.jpg"/></div>	
		<div class="framegrid_cover">
			<div class="framegrid_title">皮肤名称：skin22<br />主题风格：green</div>
			<div class="framegrid_con">
				
			</div>
		</div>
		<div class="ali02"><button onclick="showPreview('login_skin22.jpg')"><span class="icon_find">点击预览</span></button></div>
	</div>
	
	<div class="framegrid nocaption">
		<div class="picItem3"><img src="../images/selectTemp/login_skin23_small.jpg"/></div>	
		<div class="framegrid_cover">
			<div class="framegrid_title">皮肤名称：skin23<br />主题风格：blue</div>
			<div class="framegrid_con">
				
			</div>
		</div>
		<div class="ali02"><button onclick="showPreview('login_skin23.jpg')"><span class="icon_find">点击预览</span></button></div>
	</div>
	
	<div class="framegrid nocaption">
		<div class="picItem3"><img src="../images/selectTemp/login_skin24_small.jpg"/></div>	
		<div class="framegrid_cover">
			<div class="framegrid_title">皮肤名称：skin24<br />主题风格：darkBlue</div>
			<div class="framegrid_con">
				
			</div>
		</div>
		<div class="ali02"><button onclick="showPreview('login_skin24.jpg')"><span class="icon_find">点击预览</span></button></div>
	</div>
	
	<div class="framegrid nocaption">
		<div class="picItem3"><img src="../images/selectTemp/login_skin25_small.jpg"/></div>	
		<div class="framegrid_cover">
			<div class="framegrid_title">皮肤名称：skin25<br />主题风格：darkBlue</div>
			<div class="framegrid_con">
				
			</div>
		</div>
		<div class="ali02"><button onclick="showPreview('login_skin25.jpg')"><span class="icon_find">点击预览</span></button></div>
	</div>
	
	<div class="framegrid nocaption">
		<div class="picItem3"><img src="../images/selectTemp/login_skin26_small.jpg"/></div>	
		<div class="framegrid_cover">
			<div class="framegrid_title">皮肤名称：skin26<br />主题风格：blue</div>
			<div class="framegrid_con">
				
			</div>
		</div>
		<div class="ali02"><button onclick="showPreview('login_skin26.jpg')"><span class="icon_find">点击预览</span></button></div>
	</div>
	
	<div class="framegrid nocaption">
		<div class="picItem3"><img src="../images/selectTemp/login_skin27_small.jpg"/></div>	
		<div class="framegrid_cover">
			<div class="framegrid_title">皮肤名称：skin27<br />主题风格：blue</div>
			<div class="framegrid_con">
				
			</div>
		</div>
		<div class="ali02"><button onclick="showPreview('login_skin27.jpg')"><span class="icon_find">点击预览</span></button></div>
	</div>
	
	<div class="framegrid nocaption">
		<div class="picItem3"><img src="../images/selectTemp/login_skin19_small.jpg"/></div>	
		<div class="framegrid_cover">
			<div class="framegrid_title">皮肤名称：skin19<br />主题风格：blue</div>
			<div class="framegrid_con">
				
			</div>
		</div>
		<div class="ali02"><button onclick="showPreview('login_skin19.jpg')"><span class="icon_find">点击预览</span></button></div>
	</div>
	
	
	<div class="framegrid nocaption">
		<div class="picItem3"><img src="../images/selectTemp/login_skin2_small.jpg"/></div>	
		<div class="framegrid_cover">
			<div class="framegrid_title">皮肤名称：skin2<br />主题风格：blue</div>
			<div class="framegrid_con">
				
			</div>
		</div>
		<div class="ali02"><button onclick="showPreview('login_skin2.jpg')"><span class="icon_find">点击预览</span></button></div>
	</div>
	
	<div class="framegrid nocaption">
		<div class="picItem3"><img src="../images/selectTemp/login_skin5_small.jpg"/></div>	
		<div class="framegrid_cover">
			<div class="framegrid_title">皮肤名称：skin5<br />主题风格：blue</div>
			<div class="framegrid_con">
				
			</div>
		</div>
		<div class="ali02"><button onclick="showPreview('login_skin5.jpg')"><span class="icon_find">点击预览</span></button></div>
	</div>
	
	<div class="framegrid nocaption">
		<div class="picItem3"><img src="../images/selectTemp/login_skin7_small.jpg"/></div>	
		<div class="framegrid_cover">
			<div class="framegrid_title">皮肤名称：skin7<br />主题风格：blue</div>
			<div class="framegrid_con">
				
			</div>
		</div>
		<div class="ali02"><button onclick="showPreview('login_skin7.jpg')"><span class="icon_find">点击预览</span></button></div>
	</div>
	
	<div class="framegrid nocaption">
		<div class="picItem3"><img src="../images/selectTemp/login_skin8_small.jpg"/></div>	
		<div class="framegrid_cover">
			<div class="framegrid_title">皮肤名称：skin8<br />主题风格：blue</div>
			<div class="framegrid_con">
				
			</div>
		</div>
		<div class="ali02"><button onclick="showPreview('login_skin8.jpg')"><span class="icon_find">点击预览</span></button></div>
	</div>
	
	<div class="framegrid nocaption">
		<div class="picItem3"><img src="../images/selectTemp/login_skin9_small.jpg"/></div>	
		<div class="framegrid_cover">
			<div class="framegrid_title">皮肤名称：skin9<br />主题风格：green</div>
			<div class="framegrid_con">
				
			</div>
		</div>
		<div class="ali02"><button onclick="showPreview('login_skin9.jpg')"><span class="icon_find">点击预览</span></button></div>
	</div>
	
	<div class="framegrid nocaption">
		<div class="picItem3"><img src="../images/selectTemp/login_skin11_small.jpg"/></div>	
		<div class="framegrid_cover">
			<div class="framegrid_title">皮肤名称：skin11<br />主题风格：darkBlue</div>
			<div class="framegrid_con">
				
			</div>
		</div>
		<div class="ali02"><button onclick="showPreview('login_skin11.jpg')"><span class="icon_find">点击预览</span></button></div>
	</div>
	
	<div class="framegrid nocaption">
		<div class="picItem3"><img src="../images/selectTemp/login_skin12_small.jpg"/></div>	
		<div class="framegrid_cover">
			<div class="framegrid_title">皮肤名称：skin12<br />主题风格：blue</div>
			<div class="framegrid_con">
				
			</div>
		</div>
		<div class="ali02"><button onclick="showPreview('login_skin12.jpg')"><span class="icon_find">点击预览</span></button></div>
	</div>
	
	<div class="framegrid nocaption">
		<div class="picItem3"><img src="../images/selectTemp/login_skin14_small.jpg"/></div>	
		<div class="framegrid_cover">
			<div class="framegrid_title">皮肤名称：skin14<br />主题风格：green</div>
			<div class="framegrid_con">
				
			</div>
		</div>
		<div class="ali02"><button onclick="showPreview('login_skin14.jpg')"><span class="icon_find">点击预览</span></button></div>
	</div>
	
	
	<div class="clear"></div>
	</div>
</body>
</html>