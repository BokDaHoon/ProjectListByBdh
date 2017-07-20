package kr.co.lotte.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.lotte.service.SurveyService;
import kr.co.lotte.vo.BodyInfo;

@Controller
@RequestMapping(value="/survey")
public class SurveyController {
	@Autowired
	SurveyService service;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView surveyItemList() {
		ModelAndView mv = new ModelAndView();
		
		List<Map<String, String>> itemList = service.selectBodyInfoItem();
		mv.addObject("itemList", itemList);
		mv.setViewName("survey");
		
		return mv;
	}
	
	@RequestMapping(value="/bodydata", method=RequestMethod.GET)
	public @ResponseBody List<Map<String, String>> bodyData() {
		return service.selectBodyInfo();
	}
	
	@RequestMapping(value="/surveyItem", method=RequestMethod.GET)
	public @ResponseBody List<Map<String, String>> surveyItem() {
		return service.selectPrdClass();
	}
	
	@RequestMapping(value="/putBodyData", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> putBodyData(@RequestBody BodyInfo[] param) {
		service.putBodyInfo(param);
		return null;
	}
	
	@RequestMapping(value="/putSurveyData", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> putSurveyData(@RequestBody String[] param) {
		service.putSurveyData(param);
		return null;
	}
	
	@RequestMapping(value="/checkSurveyItem", method=RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> checkSurveyItem() {
		return service.checkSurveyItem();
	}
}
