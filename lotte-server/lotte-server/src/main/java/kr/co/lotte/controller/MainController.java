package kr.co.lotte.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.lotte.service.MainService;

@Controller
public class MainController {
	
	@Autowired
	MainService service;
	
	@RequestMapping(value="/main")
	public ModelAndView hello() {
		ModelAndView mv = new ModelAndView();
		List<Map<String, String>> returnParam = service.selectProductList();
		
		mv.setViewName("main");
		mv.addObject("productList" , returnParam);
		
		return mv;
	}
}
