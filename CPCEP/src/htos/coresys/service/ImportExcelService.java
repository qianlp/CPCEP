package htos.coresys.service;

import java.util.List;
import java.util.Map;


public interface ImportExcelService {

	Map<String, Object> saveImportExcel(List<Map<Integer, String>> list,String imptype);


}
