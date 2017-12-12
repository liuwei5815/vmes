
function isPic(filePath){
	
	var start=filePath.lastIndexOf(".");
	var ext=filePath.substring(start,filePath.length).toUpperCase();
	
	var arr = new Array(".BMP",".GIF",".JPG",".PNG",".JPEG");
	for(var i=0,k=arr.length;i<k;i++){ 
		if(ext==arr[i]){ 
		return true; 
		}
	}
	return false;
	
}