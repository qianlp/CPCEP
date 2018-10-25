/*
 * ��ʽ������
 */
Date.prototype.format = function(format){
	var o = {
		"M+" : this.getMonth() + 1, //month
		"d+" : this.getDate(), //day
		"h+" : this.getHours(), //hour
		"m+" : this.getMinutes(), //minute
		"s+" : this.getSeconds(), //cond
		"q+" : Math.floor((this.getMonth() + 3) / 3), //quarter
		"S" : this.getMilliseconds() //millisecond
	}
	if(/(y+)/.test(format)){
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	for(var k in o){
		if(new RegExp("(" + k + ")").test(format)){
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}

//�жϴ����ֵ�Ƿ�Ϊnull
function objectIsNull(o) {
	if(o == null || o == "undefined") {
		return null;
	} else {
		return o;
	}
}


//�жϴ����ֵ�Ƿ�Ϊnull

function stringIsNull(o) {
	if(o == "undefined") {
		return "";
	} else {
		return o;
	}
}

//�жϴ����ֵ�Ƿ�Ϊundefined
function integerIsUndefined(o) {
	if(o == "undefined") {
		return 0;
	} else {
		return o;
	}
}