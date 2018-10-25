package htos.common.util.swftools;

import htos.common.util.FileUtilBetas;

import java.io.File;
import java.io.IOException;



public class SWFToolsSWFConverter implements SWFConverter {
	/** SWFTools pdf2swf.exe路径 */
	private static String PDF2SWF_PATH = ConStant.SWFTOOLS_PDF2SWF_PATH;

	public void convert2SWF(String inputFile, String swfFile, String extend) {
		File pdfFile = new File(inputFile);
		File outFile = new File(swfFile);
		
		if (!pdfFile.exists()) {
			// org.jeecgframework.core.util.LogUtil.info("PDF文件不存在！");
			return;
		}
	
		if (outFile.exists()) {
		//	 org.jeecgframework.core.util.LogUtil.info("SWF文件已存在！");
			return;
		}
		String command = ConStant.getSWFToolsPath(extend) + " \"" + inputFile
		//---update-for--文件预览无效------ scott@2013/05/22 ADD START
				+ "\" -o " +" \""+ swfFile +" \""+ " -s languagedir=D:\\xpdf-chinese-simplified -T 9 -f";
//				+ "\" -o " + swfFile + " -s languagedir=D:\\xpdf-chinese-simplified -T 9 -f";
//		---update-for--文件预览无效----- scott@2013/05/22 ADD END
		try {
			// 开始转换文档
			Process process = Runtime.getRuntime().exec(command);
			StreamGobbler errorGobbler = new StreamGobbler(
					process.getErrorStream(), "Error");
			StreamGobbler outputGobbler = new StreamGobbler(
					process.getInputStream(), "Output");
			errorGobbler.start();
			outputGobbler.start();
			try {
				process.waitFor();
			//	org.jeecgframework.core.util.LogUtil.info("时间-------"+process.waitFor());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void convert2SWF(String inputFile, String extend) {
		String swfFile = FileUtilBetas.getFilePrefix2(inputFile) + ".swf";
		convert2SWF(inputFile, swfFile, extend);
	}
}
