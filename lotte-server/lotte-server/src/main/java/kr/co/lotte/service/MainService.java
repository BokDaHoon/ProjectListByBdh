package kr.co.lotte.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.lotte.mapper.MainMapper;

@Service
public class MainService {
	@Autowired
	MainMapper mainMapper;
	
	public List<Map<String, String>> selectProductList() {
		Map<String, String> param = new HashMap<String, String>();
		param.put("ID", "potter7050");
		return mainMapper.selectProductsList(param);
	}
}
