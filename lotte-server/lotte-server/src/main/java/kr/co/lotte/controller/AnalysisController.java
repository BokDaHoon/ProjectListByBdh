package kr.co.lotte.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.lotte.service.AnalysisService;

@Controller
public class AnalysisController {
	
	@Autowired
	AnalysisService service;
	
	@RequestMapping(value="/analysis")
	public ModelAndView hello() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("analysis");
		
		return mv;
	}
	
	@RequestMapping(value="/analysis/selectOrderCountData", method=RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> selectOrderCountData() {
		return service.selectOrderCountData();
	}
}
