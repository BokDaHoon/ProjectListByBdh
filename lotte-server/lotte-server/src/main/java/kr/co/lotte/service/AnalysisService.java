package kr.co.lotte.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.lotte.mapper.AnalysisMapper;

@Service
public class AnalysisService {
	
	@Autowired
	AnalysisMapper mapper;

	public List<Map<String, Object>> selectOrderCountData() {
		Map<String, String> param = new HashMap<String, String>();
		param.put("ID", "potter7050");
		return mapper.selectOrderCountData(param);
	}

}
