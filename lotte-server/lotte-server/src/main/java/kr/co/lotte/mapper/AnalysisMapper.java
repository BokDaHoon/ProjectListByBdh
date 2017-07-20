package kr.co.lotte.mapper;

import java.util.List;
import java.util.Map;

public interface AnalysisMapper {

	List<Map<String, Object>> selectOrderCountData(Map<String, String> param);

}
