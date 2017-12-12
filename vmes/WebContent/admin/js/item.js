
function isObj(str) {
	if(str==null||typeof(str)=='undefined')
		return false;
	return true;
}

function isInt(str){
	var reg = /^(-|\+)?\d+$/ ;
	return reg.test(str);
}

function isFloatOrNull(str){
	if(!isObj(str))//判 断对象是否存在
	return 'undefined';
	if(isInt(str))
	return true;
	return isNull(str)||isFloat(str);
}

function isFloat(str){
	if(isInt(str))
	return true;
	var reg = /^(-|\+)?\d+\.\d*$/;
	return reg.test(str);
}