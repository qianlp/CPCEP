/**
 * @license Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights
 *          reserved. For licensing, see LICENSE.md or
 *          http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function(config) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';

	config.toolbar = 'MXICToolbar';
	config.toolbar_MXICToolbar = [
			[ 'SpellChecker' ],
			[ 'PasteFromWord', '-', 'Undo', 'Redo' ],
			[ '-', 'Find', 'Replace', '-', 'SelectAll' ],
			[ 'Bold', 'Italic', 'Underline', 'Strike', '-', 'RemoveFormat' ],
			[ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-',
					'JustifyLeft', 'JustifyCenter', 'JustifyRight',
					'JustifyBlock' ], [ 'Link', 'Unlink', 'Anchor' ],
			[ 'Image', 'Table', 'HorizontalRule', 'SpecialChar', 'PageBreak' ],
			[ 'Styles', 'Format', 'Font', 'FontSize' ],
			[ 'TextColor', 'BGColor' ], [ 'Maximize', 'ShowBlocks' ],
			[ 'Source' ] ];

	config.height = '500px';
	config.removeDialogTabs = 'image:advanced;link:advanced';//隐藏上传
	config.filebrowserImageBrowseUrl = gDir + '/information/imgUpload.jsp?parentDocId='+ $("[name=curDocId]").val() +'&area=att_img';
	config.baseFloatZIndex=900;
	// config.filebrowserUploadUrl= gDir+"/profile/findAllAdenexaJson.action";
	// config.filebrowserFlashBrowseUrl =
	// '../editor/ckfinder/ckfinder.html?Type=Flash';
	// config.filebrowserUploadUrl =
	// '../editor/ckfinder/core/connector/aspx/connector.aspx?command=QuickUpload&type=Files';
	// config.filebrowserImageUploadUrl = '/profile/findAllAdenexaJson.action';
	// config.filebrowserFlashUploadUrl =
	// '../editor/ckfinder/core/connector/aspx/connector.aspx?command=QuickUpload&type=Flash';
	config.filebrowserWindowWidth = '800'; // “浏览服务器”弹出框的size设置
	config.filebrowserWindowHeight = '500';
};
