package funkey.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CommandsDao {
	private static final String TABLE_NAME = "commands";
	@Autowired
	private DynamoDB dynamoDB;
	private static final Logger log = Logger.getLogger(CommandsDao.class);
	
	
	public CommandsDao()
	{
		
	}
	
	
	public void addCommand(Command cmd) throws JsonProcessingException
	{
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(cmd);
		log.debug("addCommand json = " + json);
		Table cmdTable = dynamoDB.getTable(TABLE_NAME);
		Item item = Item.fromJSON(json);  
		cmdTable.putItem(item);
	}
	
	public void deleteCommand(long cmdId)
	{
		Table cmdTable = dynamoDB.getTable(TABLE_NAME);
		cmdTable.deleteItem("commandId", cmdId);
	}
	
	public void updateCommand(long cmdId, Command cmd)
	{
		
	}
	
	public Command findById(long id)
	{
		return(null);
	}
	
	
	public List<Command> findAll()
	{
		return(null);
	}


	public DynamoDB getDynamoDB() {
		return dynamoDB;
	}


	public void setDynamoDB(DynamoDB dynamoDB) {
		this.dynamoDB = dynamoDB;
	}

}
