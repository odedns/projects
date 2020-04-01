package test;

import java.io.File;
import java.util.List;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class S3Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String bucket = "elasticbeanstalk-us-west-2-958281113299";

        AmazonS3 s3Client = new AmazonS3Client(new ProfileCredentialsProvider());

        List<Bucket> buckets = s3Client.listBuckets();
        for(Bucket b : buckets) {
        	
        	System.out.println("b= " + b.getName());
        }
        // list objects in bucket.
        ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucket);
        ListObjectsV2Result result;
        do {               
           result = s3Client.listObjectsV2(req);
           
           for (S3ObjectSummary objectSummary : 
               result.getObjectSummaries()) {
               System.out.println(" - " + objectSummary.getKey() + "  " +
                       "(size = " + objectSummary.getSize() + 
                       ")");
           }
           System.out.println("Next Continuation Token : " + result.getNextContinuationToken());
           req.setContinuationToken(result.getNextContinuationToken());
        } while(result.isTruncated() == true );
        
        // write object to bucket
        // in order to write a string use ByteArrayInputStream
        String fileName = "c:/tmp/6945.doc";
        File f = new File(fileName);
        String keyName = "6945";
        s3Client.putObject(new PutObjectRequest(bucket, keyName, f));
        System.out.println("done putObject....");
        
        
	}

}
