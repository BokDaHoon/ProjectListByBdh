package kr.co.lotte.mapper;

import java.util.List;
import java.util.Map;

public interface MainMapper {
	public List<Map<String, String>> selectProductsList(Map<String, String> param);
}
