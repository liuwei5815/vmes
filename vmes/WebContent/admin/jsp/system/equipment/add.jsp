<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="/admin/jsp/common/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=eYf9sA6yVTFHlh9ytU4a0EYY"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加设备</title>
<link rel="stylesheet" type="text/css" href="${ctxAdmin }/js/webuploader/webuploader.css"/>

<script type="text/javascript" src="${ctxAdmin }/js/webuploader/webuploader.min.js"></script>


<script type="text/javascript">
function doSubmit(){
	$("#frm").submit();	
}

function cancel(){
	top.Dialog.close();
}

function unload(){
	if(document.frm.successflag.value == "1"){//执行成功
    	top.Dialog.alert("保存成功",function(){
    		
    		cancel();
    	});
    	this.parent.frmright.window.location.href="${ctx}/admin/equipment!init.action";	
	}else{
		if(document.forms[0].message.value !=""){
			top.Dialog.alert(document.forms[0].message.value);
	    	document.forms[0].message.value = "";	
	    }
	}

}

$(document).ready(function(){ 
	unload();
	loadWebUploder();
});
//设备树
function chooseShip(tableId,showDomId,nameCn,gxId,pkId){
    var pkVal ="";
	if(gxId!=""){
		var dom = $("[targetId='"+gxId+"']");
		var pkVal = dom.val();
		if(pkVal==""){
			top.Dialog.alert("请先选择"+dom.attr("lable"));
			return false;
		}
	}
	var diag = new top.Dialog();
	diag.ID="choose";
	diag.Title = "选择"+nameCn;
	diag.URL = "shipAction!init.action?shipId="+tableId+"&pkVal="+pkVal+"&pkId="+pkId;
	diag.Height=400;
	diag.Width=300;
	diag.OKEvent=function(){
		var treenode= diag.innerFrame.contentWindow.getTreeNode();
		if(!treenode){
			top.Dialog.alert("请至少选择一项");	
			return false;
		}
		if(treenode.parent=='#'){
			top.Dialog.alert("请正确选择类型");
			return false;
		}
		$("#"+showDomId+"").val(treenode.id);
		$("#"+showDomId+"_lable").val(treenode.text);
		diag.close();
	};
	diag.show();
}
//部门树
function chooseDept(showDomId){
	var diag = new top.Dialog();
	diag.ID="choose";
	diag.Title = "选择部门";
	diag.URL = "${ctx}/admin/departmentAction!deptTree.action";
	diag.Height=500;
	diag.Width=300;
	diag.OKEvent=function(){
		var treenode= diag.innerFrame.contentWindow.getTreeNode();
		if(!treenode){
			top.Dialog.alert("请至少选择一项");	
			return false;
		}
		if(treenode.parent=='#'){
			top.Dialog.alert("请正确选择类型");
			return false;
		}
		$("#"+showDomId+"").val(treenode.id);
		$("#"+showDomId+"_lable").val(treenode.text);
		diag.close();
	};
	diag.show();
}

function loadWebUploder(){
	var fileDiv =$("#upfile");
	var uploader = WebUploader.create({

	    // 选完文件后，是否自动上传。
	    auto: true,
		
	    // swf文件路径
	    swf: '${ctxAdmin}/js/Uploader.swf',

	    // 文件接收服务端。
	    server: '${ctxAdmin}/uploadImage!uploadImage.action',

	    // 选择文件的按钮。可选。
	    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
	    pick: '#fileImg',
	    accept: {
	        title: 'Images',
	        extensions: 'jpg,jpeg,png',
	        mimeTypes: 'image/jpg,image/jpeg,image/png'
	     },
	     fileSingleSizeLimit:1024*1024*5

	  
	});
	uploader.on( 'beforeFileQueued', function( file ) { 
		uploader.reset();
	});

	uploader.on( 'error', function( file ) { 
		top.Dialog.alert("上传文件大小不能超过5m");
	});
	uploader.on( 'uploadProgress', function( file,percentage) { 
		$("#percentage").html("<div>上传中"+percentage * 100 +"%</div>");
	});
	uploader.on( 'uploadSuccess', function(file,response) {
		if(response.code==0){
		
			var $li = $(
			            '<div id="' + file.id + '" class="file-item thumbnail">' +
			                '<img>' +
			               
			            '</div>'
			            ),
			$img = $li.find('img');
			uploader.makeThumb( file, function( error, src) {
				 if ( error ) {
			          $img.replaceWith('<span>不能预览</span>');
			           return;
			     }
				 $img.attr( 'src', src );
			},100,100);
			fileDiv.html($li);
			$("#imgPath").val(response.body);
		}
		else{
			 top.Dialog.alert("上传失败:"+response.msg);
		}
	});

	$("#upload").click(function(){
		var files= uploader.getFiles();
		if(files.length==0){
			top.Dialog.alert("请选择一张图片");
			return false;
		}
		
		uploader.upload();  
	});
	$("#upfile").click(function(){
		
		$("#fileImg").click();
	})
	
}




</script>

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
	
	<s:form action="admin/equipment!add.action" method="post" theme="simple" id="frm">
	<input type="hidden" name="message" id="message" value="${message}" /> 
	<input type="hidden" name="successflag" id="successflag" value="${successflag}" /> 
	<input type="hidden" name="department.pid" value="${pid }" />
	<table width="100%" class="tableStyle" transmode="true">
	<tr class="validate">
		<td>用户设备编号：</td>
		<td>
			<input type="text" name="eqiupment.userEquipmentCode" class="validate[length[0,15]]" />
		</td>
	</tr>
	<tr class="validate">
		<td>设备名称：</td>
		<td>
			<input type="text" name="eqiupment.equipmentName"  class="validate[required,length[0,15]]" />
			<span class="star"> *</span>
		</td>
	</tr>
	<tr class="validate">
		<td>规格/型号：</td>
		<td>
			<input type="text" name="eqiupment.equipmentModel" class="validate[required,length[0,15]]" />
			<span class="star"> *</span>
		</td>
	</tr>
	<tr class="validate" style="border: medium none; background-color: transparent;" yzid="5">
		<td style="text-align: right; padding-top: 3px; padding-bottom: 3px; border: medium none;">设备类型：</td>
		<td style="padding-top: 3px; padding-bottom: 3px; border: medium none;">
		<div style="float: left;">
		<input class="validate[required]" value="" id="Type_1_lable" readonly="readonly" type="text">
		<input id="Type_1" targetid="85" name="eqiupment.equipmentType" value="" not-null="1" lable="设备类型" datatype="1" type="hidden"></div>
		<span class="icon_find" onclick="chooseShip('30','Type_1','设备类型','','')">&nbsp; </span>
		</td>
	</tr>
	<tr class="validate" style="border: medium none; background-color: transparent;" yzid="5">
			<td style="text-align: right; padding-top: 3px; padding-bottom: 3px; border: medium none;">所属部门：</td>
			<td style="padding-top: 3px; padding-bottom: 3px; border: medium none;">
			<div style="float: left;">
			<input class="textinput simple" value="" id="deptType_lable" readonly="readonly" type="text">
			<input id="deptType" targetid="85" name="eqiupment.dept" value="" not-null="1" lable="所属部门" datatype="1" type="hidden"></div>
			<span class="icon_find" onclick="chooseDept('deptType')">&nbsp; </span>
		</td>
	</tr>
	<tr class="validate">
		<td>日标准工作时长：</td>
		<td>
			<select name="eqiupment.equipmentWorkTime" class="validate">
			   <option value="8">8小时</option>
		       <option value="1">1小时</option>
		       <option value="2">2小时</option>
		       <option value="3">3小时</option>
		       <option value="4">4小时</option>
		       <option value="5">5小时</option>
		       <option value="6">6小时</option>
		       <option value="7">7小时</option>
		      <!--  <option value="8">8小时</option> -->
		       <option value="9">9小时</option>
		       <option value="10">10小时</option>
		       <option value="11">11小时</option>
		       <option value="12">12小时</option>
		       <option value="13">13小时</option>
		       <option value="14">14小时</option>
		       <option value="15">15小时</option>
		       <option value="16">16小时</option>
		        <option value="17">17小时</option>
		       <option value="18">18小时</option>
		       <option value="19">19小时</option>
		       <option value="20">20小时</option>
		       <option value="21">21小时</option>
		       <option value="22">22小时</option>
		       <option value="23">23小时</option>
		       <option value="24">24小时</option>
	          </select>
		</td>
	</tr>
	<tr class="validate">
		<td>采购日期：</td>
		<td>
			<!-- <input type="text" name="eqiupment.buyDate" type="text" data-prompt-position="centerRight:-20" /> -->
			<input class="date validate[required]"  type="text" name="eqiupment.buyDate"/><span class="star"> *</span>
		</td>
	</tr>
	<tr class="validate">
		<td>设备图片：</td>
		<td>
			<div style="position: relative;height: 110px;width: 110px;">
				<div id="upfile" style="position:absolute;top: 0;z-index:100;margin: 0;width: 100px;height: 100px">
					<img src="${ctxAdmin }/image/xztp.png" width="100px" height="100px" />
					<div id="percentage"></div>
				</div>
				<div id="fileImg" style="position:absolute;z-index:999;top:0;margin;0; width: 100px;height: 100px;filter:alpha(Opacity=0);-moz-opacity:0;opacity: 0;">
						<img src="${ctxAdmin }/image/xztp.png" width="100px" height="100px" />
				</div>
			
			</div>	
			<span>上传图片大小请勿超过5m</span>
			<input type="hidden" id="imgPath" name="eqiupment.equipmentImg"/>
		</td>
	</tr>
	<tr>
		<td colspan="2"></td>
	</tr>
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
<%@include file="/admin/jsp/system/equipment/eqImgupload.jsp"%>

<table width="100%" class="tableStyle" transmode="true">
	<tr>
		<td colspan="2">
		  <p>
		    <button onclick="return doSubmit();" type="button" id="preserve"><span class="icon_save">保 存</span> </button>
            <button onclick="top.Dialog.close();" type="button"><span class="icon_no">关 闭</span> </button>
			</p>
		</td>
	</tr>
</table> 
</body>
</html>




