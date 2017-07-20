package kr.co.lotte.mapper;

import java.util.List;
import java.util.Map;

public interface SurveyMapper {
	
	public List<Map<String, String>> selectBodyInfo(Map<String, String> param);
	
	public List<Map<String, String>> selectBodyInfoItem();
	
	public List<Map<String, String>> selectPrdClass();
	
	public void initBodyInfo(Map<String, String> param);

	public void initSurveyData(Map<String, String> param);
	
	public void putBodyInfo(Map<String, String> param);
	
	public void putSurveyData(Map<String, String> param);

	public List<Map<String, Object>> checkSurveyItem(Map<String, String> param);
}
