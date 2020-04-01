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

public class DynamoTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.setConnectionTimeout(50000);
		//clientConfig.se
		System.out.println("max connections =" + clientConfig.getMaxConnections());
		AmazonDynamoDBClient client = new AmazonDynamoDBClient(new ProfileCredentialsProvider());
		client.setRegion(Region.getRegion(Regions.US_WEST_2));
		  DynamoDB dynamoDB = new DynamoDB(client);

		
		System.out.println("connected...");
		Table table = dynamoDB.getTable("imei");
		try {

            System.out.println("Adding data to test" );
            List<String> groups = Arrays.asList("g1","g2");
            
            Item item = new Item()
                .withPrimaryKey("imei_id", "abcd111")
                .withList("groups",groups);
            
            
                
                
            table.putItem(item);
            System.out.println("done ...");
            
           
		} catch(Exception e) {
        	  e.printStackTrace();
          }
		
		
	}

}
