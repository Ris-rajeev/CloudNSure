package com.realnet.Dbconfig.Controllers;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.realnet.Dbconfig.Entity.DatabaseConnectionServices;
import com.realnet.Dbconfig.Entity.ResponseMessage;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

@RestController
@RequestMapping("/suredata/test")
public class TestController {

	@Autowired
	DatabaseConnectionServices connectionServices;

	@GetMapping("/testconnection")
	public ResponseEntity<?> getcolomnListFromSource(@RequestParam String database_type,
			@RequestParam(required = false) String username, @RequestParam(required = false) String password,
			@RequestParam String portnumber, @RequestParam String dbhostname,
			@RequestParam(required = false) String database_name) throws SQLException {

		String url = null;
		if (database_type.equalsIgnoreCase("ibm DB2")) {
			url = dbhostname + ":" + portnumber + "/" + database_name;
		} else if (database_type.equalsIgnoreCase("FireBird") || database_type.equalsIgnoreCase("OrientDB")) {
			url = dbhostname + ":" + portnumber + "/" + database_name;

		}

		else if (database_type.equalsIgnoreCase("MongoDB")) {
			url = dbhostname + ":" + portnumber + "/";

		} else if (database_type.equalsIgnoreCase("dynamodb")) {
			url = dbhostname + ":" + portnumber;

		} else if (database_type.equalsIgnoreCase("couchbase")) {
			url = dbhostname;

		} else {
			url = dbhostname + ":" + portnumber + "/";

		}

		if (database_type.equalsIgnoreCase("redis")) {
			Jedis jedis = new Jedis(dbhostname, Integer.parseInt(portnumber));

			try {
				jedis.connect(); // Try to establish the connection

				if (jedis.isConnected()) {
					return new ResponseEntity<>(new ResponseMessage("Connection made"), HttpStatus.OK);
				} else {
					return new ResponseEntity<>(new ResponseMessage("Connection not made"), HttpStatus.BAD_REQUEST);
				}
			} catch (JedisConnectionException e) {
				return new ResponseEntity<>(new ResponseMessage("Connection error: " + e.getMessage()),
						HttpStatus.BAD_REQUEST);
			}
		}

//		else if (database_type.equalsIgnoreCase("couchbase")) {
//	        try {
//	            Bucket bucket = connectionServices.getClusterInstance(database_type, store_username, store_password, url);
//
//	            if (bucket != null) {
//	                // Successfully connected to the Couchbase bucket
//	                return new ResponseEntity<>(new ResponseMessage("Connection made"), HttpStatus.OK);
//	            } else {
//	                // Connection to Couchbase bucket failed
//	                return new ResponseEntity<>(new ResponseMessage("Connection not made"), HttpStatus.BAD_REQUEST);
//	            }
//	        } catch (Exception e) {
//	            return new ResponseEntity<>(new ResponseMessage("Connection error: " + e.getMessage()), HttpStatus.BAD_REQUEST);
//	        }
//	    }

		if (database_type.equalsIgnoreCase("couchbase")) {
			try {
				Cluster cluster = connectionServices.getClusterInstance(database_type, username, password, url);

				if (cluster != null) {
					// Successfully connected to the Couchbase cluster
					return new ResponseEntity<>(new ResponseMessage("Connection made"), HttpStatus.OK);
				} else {
					// Connection to Couchbase cluster failed
					return new ResponseEntity<>(new ResponseMessage("Connection not made"), HttpStatus.BAD_REQUEST);
				}
			} catch (IllegalStateException e) {
				return new ResponseEntity<>(new ResponseMessage("Connection error: " + e.getMessage()),
						HttpStatus.BAD_REQUEST);
			}
		}

//		else if (database_type.equalsIgnoreCase("dynamodb")) {
//			
//	            DynamoDbClient dynamoDbClient = connectionServices.createDynamoDbClient(store_username, store_password, url, database_name);
//
//	            if (isDynamoDBConnectionSuccessful(dynamoDbClient)) {
//	                return new ResponseEntity<>(new ResponseMessage("connection made"), HttpStatus.OK);
//	            } else {
//	                return new ResponseEntity<>(new ResponseMessage("connection not made"), HttpStatus.BAD_REQUEST);
//	            }
//	        }

		else if (database_type.equalsIgnoreCase("dynamodb")) {

			DynamoDbClient dynamoDbClient = createDynamoDbClient(username, password, url, database_name);

			try {
				dynamoDbClient.listTables();
				return new ResponseEntity<>(new ResponseMessage("connection made"), HttpStatus.OK);
			} catch (DynamoDbException e) {
				return new ResponseEntity<>(new ResponseMessage("connection not made"), HttpStatus.BAD_REQUEST);
			}
		}

		else {

			Connection con = connectionServices.Connection(database_type, username, password, url);

			if (isConnectionSuccessful(database_type, con)) {
				return new ResponseEntity<>(new ResponseMessage("connection made"), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new ResponseMessage("connection not made"), HttpStatus.BAD_REQUEST);
			}
		}

	}

	public DynamoDbClient createDynamoDbClient(String accessKeyId, String secretAccessKey, String url, String region) {
		return DynamoDbClient.builder().region(Region.AP_SOUTH_1) // Replace with your desired region
				.credentialsProvider(
						StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretAccessKey)))
				.endpointOverride(URI.create(url)) // Your DynamoDB endpoint URL
				.build();
	}

	public boolean isConnectionSuccessful(String database_type, Connection connection) {
		if (database_type.equalsIgnoreCase("MongoDB")) {
			return connection != null; // MongoDB connection check
		} else {
			try {
				return connection.getMetaData() != null;
			} catch (SQLException e) {
				return false;
			}
		}
	}

	public boolean isDynamoDBConnectionSuccessful(DynamoDbClient dynamoDbClient) {
		return dynamoDbClient != null;
	}
}
