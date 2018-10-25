function getDateDay(beginStr,endStr){
	var beginDate = new Date(beginStr.replace(/-/g, "/"));  
	//��������  
	var endDate = new Date(endStr.replace(/-/g, "/"));  
	//���ڲ�ֵ,�����������ա�����Ϊ��λ�Ĺ�ʱ��86400000=1000*60*60*24.  
	var workDayVal = (endDate - beginDate)/86400000 + 1;  
	//��ʱ������  
	var remainder = workDayVal % 7; 
	//��ʱ����ȡ���ĳ���  
	var divisor = Math.floor(workDayVal / 7);  
	var weekendDay = 2 * divisor;  
  
	//��ʼ���ڵ����ڣ�����ȡֵ�У�1,2,3,4,5,6,0��  
	var nextDay = beginDate.getDay();  
	//����ʼ���ڵ����ڿ�ʼ ����remainder��  
	for(var tempDay = remainder; tempDay>=1; tempDay--) {  
		//��һ�첻�ü�1  
		if(tempDay == remainder) {  
			nextDay = nextDay + 0;  
		} else if(tempDay != remainder) {  
			nextDay = nextDay + 1;  
		}  
		//���գ����Ϊ0  
		if(nextDay == 7) {  
			nextDay = 0;  
		}  
  
		//������  
		if(nextDay == 0 || nextDay == 6) {  
			weekendDay = weekendDay + 1;  
		}  
	}  
	//ʵ�ʹ�ʱ���죩 = ��ֹ���ڲ� - ��������Ŀ��  
	workDayVal = workDayVal - weekendDay;

	return workDayVal
}