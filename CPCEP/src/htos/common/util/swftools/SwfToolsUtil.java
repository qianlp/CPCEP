package htos.common.util.swftools;

import htos.common.util.FileUtilBetas;


/**
 * 文件转换调用接口
 * 讲文件转换成swf文件方便预览
 */
public class SwfToolsUtil {
	
	public static void convert2SWF(String inputFile) {
		
		String extend=FileUtilBetas.getExtend(inputFile);
		PDFConverter pdfConverter = new OpenOfficePDFConverter();
		SWFConverter swfConverter = new SWFToolsSWFConverter();
		if(extend.equals("pdf"))
		{
			swfConverter.convert2SWF(inputFile,extend);
		}
		if(extend.equals("doc") || extend.equals("docx") || extend.equals("xls") || extend.equals("pptx") || extend.equals("xlsx") || extend.equals("ppt") || extend.equals("txt") || extend.equals("odt"))
		{
			DocConverter converter = new DocConverter(pdfConverter,swfConverter);
			converter.convert(inputFile,extend);
		}
		
	}
}
