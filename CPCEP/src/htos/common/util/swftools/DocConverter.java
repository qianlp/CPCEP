package htos.common.util.swftools;

import htos.common.util.FileUtilBetas;

public class DocConverter {

	private PDFConverter pdfConverter;
	private SWFConverter swfConverter;

	public DocConverter(PDFConverter pdfConverter, SWFConverter swfConverter) {
		super();
		this.pdfConverter = pdfConverter;
		this.swfConverter = swfConverter;
	}

	public void convert(String inputFile, String swfFile, String extend) {
		this.pdfConverter.convert2PDF(inputFile, extend);
		String pdfFile = FileUtilBetas.getFilePrefix(inputFile) + ".pdf";
		this.swfConverter.convert2SWF(pdfFile, swfFile);
	}

	public void convert(String inputFile, String extend) {
		this.pdfConverter.convert2PDF(inputFile, extend);
		String pdfFile = FileUtilBetas.getFilePrefix2(inputFile) + ".pdf";
		extend = FileUtilBetas.getExtend(pdfFile);
		this.swfConverter.convert2SWF(pdfFile, extend);

	}

}
