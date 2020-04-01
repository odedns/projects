package funkey.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import funkey.dao.Command;
import funkey.dao.CommandsDao;

@RestController
@RequestMapping("/rest")
public class DataService {
	private Logger log = Logger.getLogger(DataService.class);
	
	@Autowired
	private CommandsDao dao;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void addCommand(@RequestBody Command cmd) throws Exception
	{
		log.debug("in DataService.addCommand()");
		dao.addCommand(cmd);
	
	}
	
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test()
	{
		log.debug("in test");
		return("in test");
	}


	public CommandsDao getDao() {
		return dao;
	}


	public void setDao(CommandsDao dao) {
		this.dao = dao;
	}
}
