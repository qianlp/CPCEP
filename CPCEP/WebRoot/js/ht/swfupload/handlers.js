/*
基于uploadfiy改造
htUpload v1.0.0
*/
var __CreateJSPath;
if (__CreateJSPath == undefined) {
	__CreateJSPath = function (js) {
		var scripts = document.getElementsByTagName("script");
		var path = "";
		for (var i = 0, l = scripts.length; i < l; i++) {
			var src = scripts[i].src;
			if (src.indexOf(js) != -1) {
				var ss = src.split(js);
				path = ss[0];
				break;
			}
		}
		return path;
	}
};

var gUploadPATH = __CreateJSPath("handlers.js");
(function($) {
	// These methods can be called by adding them as the first argument in the uploadify plugin call
	var methods = {
		init : function(options, swfUploadOptions) {
			return this.each(function() {
				// Setup the default options
				var settings = $.extend({
					// Required Settings
					id       		: $(this).attr('id'), // The ID of the DOM object
					uploader 		: options.uploader,  // 上传路径
					queueID			: '',				  // 附件显示区域
					// Options
					buttonCursor    : 'hand',             // The cursor to use with the browse button
					buttonImage     : 'upload',           // (String or null) The path to an image to use for the Flash browse button if not using CSS to style the button
					buttonText      : '上传',     		  // The text to use for the browse button
					buttonTextStyle : null,
					height          : 25,                 // The height of the browse button
					width           : 70,                // The width of the browse button
					
					debug           : false,              // Turn on swfUpload debugging mode
					fileSizeLimit   : "10MB",                  // The maximum size of an uploadable file in KB (Accepts units B KB MB GB if string, 0 for no limit)
					fileTypeDesc    : '所有文件',        // The description for file types in the browse dialog
					fileTypeExts    : '*.*',              // Allowed extensions in the browse dialog (server-side validation should also be used)

					multi           : true,               // Allow multiple file selection in the browse dialog
					formData        : {},                 // An object with additional data to send to the server-side upload script with every file upload
					queueSizeLimit  : 100,                // The maximum number of files that can be in the queue at one time
					uploadLimit     : 0,                  // The maximum number of files you can upload
					imageZipWeb		: false,			  //仅支持客户端进行图片的压缩。
					imageZipServer	: false,			  //仅支持服务器端进行图片的压缩。
					imageWidth		: 800,
					imageHeight		: 800,
					imageQuality	: 100
				}, options);
				settings.formData["ID"]=settings.queueID;//主键ID
				if(settings.imageZipServer){
					//设置高度与宽度,用于服务端压缩					
					settings.formData["Width"]=settings.imageWidth;
					settings.formData["Height"]=settings.imageHeight;
					settings.formData["Zip"]="1";
					//服务端压缩时,客户端不允许再压缩
					settings.imageZipWeb=false;
				}
				if(settings.imageZipWeb){
					settings.fileTypeDesc="仅图片";
					settings.fileTypeExts="*.jpg;*.jpeg;*.gif;*.png;*.bmp";
				}
				// Prepare settings for SWFUpload
				var swfUploadSettings = {
					//-**********************************************************
					// Button Settings
					button_placeholder_id    : settings.id,
					button_width             : settings.width,
					button_height            : settings.height,
					button_image_url         : null,
					button_text              : null,
					button_text_style        : null,
					button_text_top_padding  : 0,
					button_text_left_padding : 0,
					button_action            : (settings.multi ? SWFUpload.BUTTON_ACTION.SELECT_FILES : SWFUpload.BUTTON_ACTION.SELECT_FILE),
					button_disabled          : false,
					button_cursor            : (settings.buttonCursor == 'arrow' ? SWFUpload.CURSOR.ARROW : SWFUpload.CURSOR.HAND),
					button_window_mode       : SWFUpload.WINDOW_MODE.TRANSPARENT,					
					
					// Backend Settings
					upload_url: settings.uploader,
					post_params: settings.formData,

					// File Upload Settings
					file_size_limit : settings.fileSizeLimit,	// 10M
					file_types : settings.fileTypeExts,
					file_types_description : settings.fileTypeDesc,
					file_upload_limit : settings.uploadLimit,
					file_queue_limit : settings.queueSizeLimit,

					// Event Handler Settings (all my handlers are in the Handler.js file)
					swfupload_preload_handler : handlers.preLoad,
					swfupload_load_failed_handler : handlers.loadFailed,
					file_dialog_start_handler : handlers.onDialogOpen,
					file_queued_handler : handlers.onSelect,
					file_queue_error_handler : handlers.onSelectError,
					file_dialog_complete_handler : handlers.onDialogClose,
					upload_start_handler : handlers.onUploadStart,
					upload_progress_handler : handlers.onUploadProgress,
					upload_error_handler : handlers.onUploadError,
					upload_success_handler : handlers.onUploadSuccess,
					upload_complete_handler : handlers.onUploadComplete,

					// Flash Settings
					flash_url : gUploadPATH+"swfupload.swf",
					flash9_url : gUploadPATH+"swfupload_fp9.swf",

					custom_settings : {
						imageZipWeb 		: settings.imageZipWeb,
						imageWidth			: settings.imageWidth,
						imageHeight			: settings.imageHeight,
						imageQuality		: settings.imageQuality,
						processID 			: settings.id+"-queue",
						goWinAttacheDestroy : function(){
							try{
								var objPanel = mini.get("mini-window-attache");
								if(objPanel){
									objPanel.destroy();
									//刷新下页面显示
									initComplefj();
								}
							}catch(e){
							}
						}
					},

					// Debug Settings
					debug : settings.debug					
				};

				// Merge the user-defined options with the defaults
				if (swfUploadOptions) {
					swfUploadSettings = $.extend(swfUploadSettings, swfUploadOptions);
				}
				// Add the user-defined settings to the swfupload object
				swfUploadSettings = $.extend(swfUploadSettings, settings);
				
				// Detect if Flash is available
				var playerVersion  = swfobject.getFlashPlayerVersion();
				var flashInstalled = (playerVersion.major >= 9);

				if (flashInstalled) {
					// Create the swfUpload instance
					window['uploadify_' + settings.id] = new SWFUpload(swfUploadSettings);
					//*
					var swfuploadify = window['uploadify_' + settings.id];
					
					// Wrap the instance
					var $wrapper = $('<div />', {
						'id'    : settings.id,
						'css'   : {
									'height'   : settings.height + 'px',
									'width'    : settings.width + 'px',
									'display'  : 'inline-block'
								  }
					});
					$('#' + swfuploadify.movieName).wrap($wrapper);
					// Recreate the reference to wrapper
					$wrapper = $('#' + settings.id);

					// Create the button
					var $button = $('<div />', {
						'id'    : settings.id + '-button'
					});
				//	$button.html('<a class="mini-button" iconCls="icon-'+settings.buttonImage+'" plain="false">' + settings.buttonText + '</a>');
					$button.html('<a class="mini-button" href="javascript:void(0);"><span class="mini-button-text  mini-button-icon icon-upload">' + settings.buttonText + '</span></a>');
					// Append the button to the wrapper
					$wrapper.append($button);

					// Adjust the styles of the movie
					$('#' + swfuploadify.movieName).css({
						'position' : 'absolute',
						'z-index'  : 1
					});
				}
			});

		}
	};

	// These functions handle all the events that occur with the file uploader
	var handlers = {
		preLoad:function() {
			if (!this.support.loading) {
				alert("You need the Flash Player 9.028 or above to use SWFUpload.");
				return false;
			}
		},
		loadFailed:function() {
			alert("Something went wrong while loading SWFUpload. If this were a real application we'd clean up and then give you an alternative");
		},
		// Triggered when the file dialog is opened
		onDialogOpen : function() {
			
		},

		// Triggered when the browse dialog is closed
		onDialogClose :  function(numFilesSelected, numFilesQueued) {
				this.customSettings.imageZipWeb=this.customSettings.imageZipWeb||false;
				var objPanel = mini.get("mini-window-attache");
				if(objPanel){

				}else{
					objPanel= new mini.Window();
					objPanel.setTitle("附件列表");
					objPanel.setId("mini-window-attache");
					objPanel.setWidth(410);
					objPanel.setHeight(300);
					objPanel.setShowCloseButton(false);
				}
				objPanel.setBody('<div id="'+this.customSettings.processID+'"></div>');
				objPanel.showAtPos("center", "middle");
				try {
					/* I want auto start and I can do that here */
					if (numFilesQueued > 0) {
						if(this.customSettings.imageZipWeb){
							this.startResizedUpload(this.getFile(0).ID, this.customSettings.imageWidth, this.customSettings.imageHeight, SWFUpload.RESIZE_ENCODING.JPEG, this.customSettings.imageQuality, false);
						}else{
							this.startUpload();
						}
					}else{
						this.customSettings.goWinAttacheDestroy();
					}
				} catch (ex)  {
					this.customSettings.goWinAttacheDestroy();
					this.debug(ex);
				}
				//this.startUpload();
		},

		// Triggered once for each file added to the queue
		onSelect : function(file) {
			try {
				// You might include code here that prevents the form from being submitted while the upload is in
				// progress.  Then you'll want to put code in the Queue Complete handler to "unblock" the form
				var progress = new FileProgress(file, this.customSettings.processID);
				progress.setStatus("等待上传...");
				progress.toggleCancel(true, this);

			} catch (ex) {
				this.debug(ex);
			}
		},

		// Triggered when a file is not added to the queue
		onSelectError : function(file, errorCode, message) {
			try {
				if (errorCode === SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED) {
					alert("You have attempted to queue too many files.\n" + (message === 0 ? "You have reached the upload limit." : "You may select " + (message > 1 ? "up to " + message + " files." : "one file.")));
					return;
				}

				var progress = new FileProgress(file, this.customSettings.processID);
				progress.setError();
				progress.toggleCancel(false);

				switch (errorCode) {
				case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
					progress.setStatus("File is too big.");
					this.debug("Error Code: File too big, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
					break;
				case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
					progress.setStatus("Cannot upload Zero Byte files.");
					this.debug("Error Code: Zero byte file, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
					break;
				case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
					progress.setStatus("Invalid File Type.");
					this.debug("Error Code: Invalid File Type, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
					break;
				case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
					alert("You have selected too many files.  " +  (message > 1 ? "You may only add " +  message + " more files" : "You cannot add any more files."));
					break;
				default:
					if (file !== null) {
						progress.setStatus("Unhandled Error");
					}
					this.debug("Error Code: " + errorCode + ", File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
					break;
				}
			} catch (ex) {
				this.debug(ex);
			}
		},

		// Triggered when a file upload successfully completes
		onUploadComplete : function(file) {
			try {
				
				/*  I want the next upload to continue automatically so I'll call startUpload here */
				if(this.customSettings.imageZipWeb){
					if (this.getStats().files_queued > 0) {
						this.startResizedUpload(this.getFile(0).ID, this.customSettings.imageWidth, this.customSettings.imageHeight, SWFUpload.RESIZE_ENCODING.JPEG, this.customSettings.imageQuality, false);
					} else {
						var progress = new FileProgress(file,  this.customSettings.processID);
						progress.setComplete();
						progress.setStatus("上传完成.");
						progress.toggleCancel(false);
						progress.setTimer(null);
						loadAttachGrid(true);
						this.customSettings.goWinAttacheDestroy();
					}
				}else{
					if (this.getStats().files_queued > 0) {
						this.startUpload();
					}else{
						var progress = new FileProgress(file,  this.customSettings.processID);
						progress.setComplete();
						progress.setStatus("上传完成.");
						progress.setTimer(null);
						loadAttachGrid(true)
						this.customSettings.goWinAttacheDestroy();
					}
				}
			} catch (ex) {
				this.debug(ex);
				this.customSettings.goWinAttacheDestroy();
			}
		},

		// Triggered when a file upload returns an error
		onUploadError : function(file, errorCode, message) {
			try {
				var progress = new FileProgress(file, this.customSettings.processID);
				progress.setError();
				progress.toggleCancel(false);

				switch (errorCode) {
				case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
					progress.setStatus("Upload Error: " + message);
					this.debug("Error Code: HTTP Error, File name: " + file.name + ", Message: " + message);
					break;
				case SWFUpload.UPLOAD_ERROR.MISSING_UPLOAD_URL:
					progress.setStatus("Configuration Error");
					this.debug("Error Code: No backend file, File name: " + file.name + ", Message: " + message);
					break;
				case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
					progress.setStatus("Upload Failed.");
					this.debug("Error Code: Upload Failed, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
					break;
				case SWFUpload.UPLOAD_ERROR.IO_ERROR:
					progress.setStatus("Server (IO) Error");
					this.debug("Error Code: IO Error, File name: " + file.name + ", Message: " + message);
					break;
				case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
					progress.setStatus("Security Error");
					this.debug("Error Code: Security Error, File name: " + file.name + ", Message: " + message);
					break;
				case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
					progress.setStatus("Upload limit exceeded.");
					this.debug("Error Code: Upload Limit Exceeded, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
					break;
				case SWFUpload.UPLOAD_ERROR.SPECIFIED_FILE_ID_NOT_FOUND:
					progress.setStatus("File not found.");
					this.debug("Error Code: The file was not found, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
					break;
				case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
					progress.setStatus("Failed Validation.  Upload skipped.");
					this.debug("Error Code: File Validation Failed, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
					break;
				case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
					progress.setStatus("Cancelled");
					progress.setCancelled();
					break;
				case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
					progress.setStatus("Stopped");
					break;
				default:
					progress.setStatus("Unhandled Error: " + error_code);
					this.debug("Error Code: " + errorCode + ", File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
					break;
				}
			} catch (ex) {
				this.debug(ex);
			}
		},

		// Triggered periodically during a file upload
		onUploadProgress : function(file, bytesLoaded, bytesTotal) {
			try {
				var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);

				var progress = new FileProgress(file, this.customSettings.processID);
				progress.setProgress(percent);
				progress.setStatus("正在上传...");
			} catch (ex) {
				this.debug(ex);
			}
		},

		// Triggered right before a file is uploaded
		onUploadStart : function(file) {
			try {
				/* I don't want to do any file validation or anything,  I'll just update the UI and return true to indicate that the upload should start */
				var progress = new FileProgress(file, this.customSettings.processID);
				progress.setStatus("等待上传...");
				progress.toggleCancel(true, this);
			}
			catch (ex) {
			}
			
			return true;
		},

		// Triggered when a file upload returns a successful code
		onUploadSuccess : function(file, serverData) {
			try {
				var progress = new FileProgress(file, this.customSettings.processID);
				progress.setComplete();
				progress.setStatus("上传完成.");
				progress.toggleCancel(false);
			} catch (ex) {
				this.debug(ex);
			}
		}
	};

	$.fn.htUpload = function(method) {
		if (typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('The method ' + method + ' does not exist in $.uploadify');
		}

	}
})($);