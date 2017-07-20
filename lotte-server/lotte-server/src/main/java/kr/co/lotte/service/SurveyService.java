package kr.co.lotte.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.lotte.mapper.SurveyMapper;
import kr.co.lotte.vo.BodyInfo;

@Service
public class SurveyService {
	@Autowired
	SurveyMapper mapper;
	
	public List<Map<String, String>> selectBodyInfo() {
		Map<String, String> param = new HashMap<String, String>();
		param.put("ID", "potter7050");
		
		return mapper.selectBodyInfo(param);
	}
	
	public List<Map<String, String>> selectBodyInfoItem() {
		return mapper.selectBodyInfoItem();
	}
	
	public List<Map<String, String>> selectPrdClass() {
		return mapper.selectPrdClass();
	}
	
	public void putBodyInfo(BodyInfo[] bodyInfos) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("ID", "potter7050");
		
		mapper.initBodyInfo(param);
		
		for (BodyInfo body : bodyInfos) {
			param.put("CODE_VALUE", body.getCodeValue());
			param.put("VALUE", body.getValue());
			
			mapper.putBodyInfo(param);
		}
	}

	public void putSurveyData(String[] requestData) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("ID", "potter7050");
		
		mapper.initSurveyData(param);
		
		for (String temp : requestData) {
			String[] splitData = temp.split("_");
			param.put("LCLS_ID", splitData[0]);
			param.put("MCLS_ID", splitData[1]);
			
			mapper.putSurveyData(param);
		}
	}

	public List<Map<String, Object>> checkSurveyItem() {
		Map<String, String> param = new HashMap<String, String>();
		param.put("ID", "potter7050");
		
		return mapper.checkSurveyItem(param);
	}
}
