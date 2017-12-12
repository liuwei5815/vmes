<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<script type="text/javascript">
function upload(id){
		var dom =$("#upload_"+id+"");
		if(dom.val()==""){
			top.Dialog.alert("请选择文件");
			return false;
		}
		var size = $("#size_"+id).html();
		dom.parent().html(dom.clone());
		$("#fileContent").html(dom);
		$("#size").html(size);
		$("#fileId").val(id);
		dom.attr("id","");
		dom.attr("name","image");
		$("#uploadForm").submit();
}
function uploadSuccess(imagePath){
	top.Dialog.alert("上传成功");
	$("#imgPath").val(imagePath);
	var fileId = $("#fileId").val();
	$("#"+fileId+"").val(imagePath);
	if(imagePath!=''){
		var index = imagePath.lastIndexOf("/");
		var begin = imagePath.substring(0, index+1);
		var end = "s_"+imagePath.substring(index+1, imagePath.length);
		imagePath = begin+end;
	}
	$("#"+fileId+"_view").html($("#uploadDiv .image").clone().attr("src","<s:property value='@com.xy.cms.common.CommonFunction@getStrValue(\"picUrl\")'/>"+imagePath));
}
function uploadError(msg){
	top.Dialog.alert(msg);
}
</script>
<div style="display: none;" id="uploadDiv">
	<image  class="image"/>
<input type="hidden" id="fileId" />
<form id="uploadForm" action="${ctxAdmin }/uploadImage!uploadImage.action" method="post" enctype="multipart/form-data" target="hiddenIframe" style="width:0px;height:0px;">
		<span id="fileContent"></span>
		<span id="size"></span>
</form>
<iframe name="hiddenIframe" frameborder="0" border="0" style="display:none;width:0px;height:0px;"></iframe>
</div>

