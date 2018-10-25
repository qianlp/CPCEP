<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
#showWFIdea tr{height:35px}
#showWFIdea .tdTecheName,#showWFIdea .tdIdea,#showWFIdea .tdIdeaUser,#showWFIdea .tdIdeaTime{
	text-align:center;letter-spacing:2px;
}
#showWFIdea .tdTecheName{
	width:150px
}
#showWFIdea .tdIdea>div{
	text-align:left;width:280px;margin:0 auto
}
#showWFIdea .tdIdeaUser{
	width:150px;letter-spacing:4px;
}
#showWFIdea .tdIdeaTime{
	width:250px;letter-spacing:0px;
}
#SignMode .ideaDiv{
	width:100%;border:1px solid #253D5F;border-radius:4px;
}
#SignMode .nodeDiv{
	text-align:left;padding:5px;padding-left:10px;
}
#SignMode .textDiv{
	text-align:left;padding:5px 35px;
}
#SignMode .signDiv{
	width:100%;text-align:right;padding:5px;padding-right:10px;
}
</style>
<div class="col-md-7" name="FormMode" id="SignMode"
	style="width:100%;margin:20px auto;float:none;">
	<div class="widget-container fluid-height col-md-7-k"
		style="height:auto;border-radius:4px;padding-bottom:3px;">
		<div class="mbox-header">
			<span class="form-title" style="height:100%;line-height:45px;">
				签字 </span>
		</div>
		<div class="mbox-body" style="padding:5px;">
			<div style="width:100%;" id="showWFIdea">
			</div>
			<table cellspacing="1" cellpadding="1"
				style="width:99%;border:0;margin:0 auto">
				<tr style="border-top:0">
					<td colspan="4" style="border-top:0;text-align:center;"
						id="ideaList"><textarea name="ID_ideaArea"
							style="width:85%;margin:auto;margin-top:10px;margin-bottom:10px;"
							class="mini-textarea" emptyText="请填写意见"></textarea>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
