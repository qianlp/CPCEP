/**
 * 这里面写一些名称等转换
 */
function CommonFlowStatus(cell){
		var strColor='0f0',strStatus=cell.value;
		switch(parseInt(cell.value,10)){
			case 0:
				strStatus='草稿';
				strColor='000';
				break;
			case 1:
				var arrData=grid.getRow(cell.rowIndex)['curUser'].split(',');
				if($.inArray(gCurUser,arrData)>-1){
					strColor='f00';
					strStatus='待审批';
				}else{
					strColor='00f';
					strStatus='进行中';
				}
				break;
			case 2:
				strColor='000';
				strStatus='已审批';
				break;
			case 3:
				strColor='22DD48';
				strStatus='已更新';
				break;
			case 4:
				strColor='2BD5D5';
				strStatus='已发送';
				break;
			default:
		}
		return "<span style='color:#"+strColor+"'>" + strStatus + "</span>";
	}

