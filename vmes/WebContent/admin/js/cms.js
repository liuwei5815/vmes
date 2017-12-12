/**
 * JS脚本·从身份证算出性别和年龄
 * @type
 */
function caculateSexAndAge(idCardObj,sexObj,ageObj){
	var str = idCardObj.value;// 身份证编码
	var len = str.length;// 身份证编码长度
	if (len < 15) {
		window.alert("不是有效身份证编码");
	} else{// 身份证编码大于15位
		if ((len != 15) && (len != 18)){// 判断编码位数等于15或18
			window.alert("不是有效身份证编码");
			return false;
		} else {
			if (len == 15){// 15位身份证
				var s = str.substr(len - 1, 1);
				if ((str.substr(len - 1, 1) % 2) != 0)// 判断顺序码是否是奇数
					sexObj.value = "男";
				else
					sexObj.value = "女";
				var date1 = new Date();// 取得当前日期
				var year1 = date1.getFullYear();// 取得当前年份
				var month1 = date1.getMonth();// 取得当前月份
				if (month1 > parseInt(str.substr(8, 2)))// 判断当前月分与编码中的月份大小
					ageObj.value = year1 - ("19" + str.substr(6, 2));
				else
					ageObj.value = year1 - ("19" + str.substr(6, 2)) - 1;
			}
			if (len == 18){// 18位身份证
				if ((str.substr(len - 2, 1) % 2) != 0)// 判断顺序码是否是奇数
					sexObj.value = "男";
				else
					sexObj.value = "女";
				var date1 = new Date();// 取得当前日期
				var year1 = date1.getFullYear();// 取得当前年份
				var month1 = date1.getMonth();// 取得当前月份
				if (month1 > parseInt(str.substr(10, 2)))// 判断当前月分与编码中的月份大小
					ageObj.value = year1 - str.substr(6, 4);
				else
					ageObj.value = year1 - str.substr(6, 4) - 1;
			}
		}
	}
}