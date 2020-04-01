package test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

import funkey.dao.Command;
import funkey.dao.CommandsDao;

public class DynamoTest2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.setConnectionTimeout(50000);
		AmazonDynamoDBClient client = new AmazonDynamoDBClient(new ProfileCredentialsProvider());
		client.setRegion(Region.getRegion(Regions.US_WEST_2));
		  DynamoDB dynamoDB = new DynamoDB(client);

		
		System.out.println("connected...");
		
		try {

            
            
            
            System.out.println("Now storing command");
            
            Command cmd = new Command();
            cmd.setCommandId(100);
            cmd.setRepeat(1);
            cmd.setType(1);
            List<String> groups = Arrays.asList("g1","g2");
            cmd.setGroups(groups);
            CommandsDao dao = new CommandsDao();
            dao.setDynamoDB(dynamoDB);
            dao.addCommand(cmd);
            System.out.println("done ....");
		} catch(Exception e) {
        	  e.printStackTrace();
          }
		
		
	}

}
