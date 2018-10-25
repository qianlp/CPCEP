function getDateDay(beginStr,endStr){
	var beginDate = new Date(beginStr.replace(/-/g, "/"));  
	//结束日期  
	var endDate = new Date(endStr.replace(/-/g, "/"));  
	//日期差值,即包含周六日、以天为单位的工时，86400000=1000*60*60*24.  
	var workDayVal = (endDate - beginDate)/86400000 + 1;  
	//工时的余数  
	var remainder = workDayVal % 7; 
	//工时向下取整的除数  
	var divisor = Math.floor(workDayVal / 7);  
	var weekendDay = 2 * divisor;  
  
	//起始日期的星期，星期取值有（1,2,3,4,5,6,0）  
	var nextDay = beginDate.getDay();  
	//从起始日期的星期开始 遍历remainder天  
	for(var tempDay = remainder; tempDay>=1; tempDay--) {  
		//第一天不用加1  
		if(tempDay == remainder) {  
			nextDay = nextDay + 0;  
		} else if(tempDay != remainder) {  
			nextDay = nextDay + 1;  
		}  
		//周日，变更为0  
		if(nextDay == 7) {  
			nextDay = 0;  
		}  
  
		//周六日  
		if(nextDay == 0 || nextDay == 6) {  
			weekendDay = weekendDay + 1;  
		}  
	}  
	//实际工时（天） = 起止日期差 - 周六日数目。  
	workDayVal = workDayVal - weekendDay;

	return workDayVal
}