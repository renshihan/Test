package com.check.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
public class GoController implements EnvironmentAware{
	private final Logger LOGGER=LoggerFactory.getLogger(GoController.class);
	@RequestMapping(value={"/"},method={RequestMethod.HEAD})
	public String head(){
		return "go.jsp";
	}
	//处理GET类型的"/index"和"/"请求
	@RequestMapping()
	public String index(Model model) throws Exception{
		LOGGER.info("进入index=============");
		//返回msg参数
		model.addAttribute("msg","GO GOG GO");
		return "go.jsp";
	}
	private Environment environment;
	@Override
	public void setEnvironment(Environment environment) {
		this.environment=environment;
	}
}
