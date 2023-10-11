package com.realnet.Dbconfig.Entity;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.couchbase.client.core.error.CouchbaseException;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.ClusterOptions;
import com.couchbase.client.java.env.ClusterEnvironment;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.orientechnologies.orient.core.exception.OStorageException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;


@Service
public class DatabaseConnectionServices {

	public Connection Connection(String database_type, String username, String password, String db_url)
			throws SQLException {

		if (database_type.equalsIgnoreCase("mysql")) {
			Connection connection = mysqlconnection(username, password, db_url);
			return connection;

		} else if (database_type.equalsIgnoreCase("postgresql")) {
			Connection connection = postgreconnection(username, password, db_url);
			return connection;

		} else if (database_type.equalsIgnoreCase("mysql_lite")) {
			Connection connection = mysql_liteconnection(username, password, db_url);
			return connection;

		} else if (database_type.equalsIgnoreCase("ms_sql")) {
			Connection connection = ms_sqlconnection(username, password, db_url);
			return connection;

		} else if (database_type.equalsIgnoreCase("oracle")) {
			Connection connection = oracleconnection(username, password, db_url);
			return connection;

		} else if (database_type.equalsIgnoreCase("maria_db")) {
			Connection connection = maria_dbconnection(username, password, db_url);
			return connection;

		}

		else if (database_type.equalsIgnoreCase("ibm DB2")) {

			Connection connection = ibmdb2connection(username, password, db_url);
			return connection;

		} else if (database_type.equalsIgnoreCase("FireBird")) {
			
			
			Connection connection = firebird_dbconnection(username, password, db_url);
			return connection;

		} else if (database_type.equalsIgnoreCase("Neo4j")) {

			Connection connection = neo4jconnection(username, password, db_url);
			return connection;

		}

		else if (database_type.equalsIgnoreCase("OrientDB")) {
			String dbUrl = "jdbc:orient:remote:13.233.70.96:2424/newsetu";

			Properties userInfo = new Properties();
			userInfo.put("admin", username); // Use the correct username
			userInfo.put("admin", password); // Use the correct password

			Connection connection = null;
			try {
				Class.forName("com.orientechnologies.orient.jdbc.OrientJdbcDriver");
				connection = DriverManager.getConnection(dbUrl, userInfo);

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				// Handle ClassNotFoundException appropriately
			} catch (SQLException e) {
				e.printStackTrace();
				// Handle SQLException appropriately
			} catch (OStorageException e) {
				// Handle the exception here
				e.printStackTrace(); // You can print the stack trace for debugging purposes
				System.out.println(e);

			}
			return connection;
//	            return null; // Return appropriate value on failure
		}

		else {
			return null;
		}
	}

//  MYSQL CONNECTION
	public Connection mysqlconnection(String username, String password, String db_url) throws SQLException {

		Connection con = DriverManager.getConnection(db_url, username, password);
		return con;

	}

//	public Connection firebird_dbconnection(String username, String password, String db_url) throws SQLException {
//
//		db_url = "jdbc:firebirdsql://13.233.70.96:3050/firebird/data/demo.fdb?user=SYSDBA&password=b52546e64f714b303fc3&encoding=UTF8";
//		System.out.println(db_url);
//		Connection connection = firebird_dbconnection("SYSDBA", "b52546e64f714b303fc3", db_url);
//
//		return connection;
//
//	}

	
	
	public Connection firebird_dbconnection(String username, String password, String db_url) throws SQLException {
	    try {
	        Class.forName("org.firebirdsql.jdbc.FBDriver");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    db_url = "jdbc:firebirdsql://13.233.70.96:3050/demo.fdb?user=SYSDBA&password=a0e97a0c4beaa259bfba&encoding=UTF8";
		System.out.println(db_url);

	    Connection con = DriverManager.getConnection(db_url, username, password);

	    return con;
	}

	public Connection neo4jconnection(String username, String password, String db_url) throws SQLException {
		Connection con = DriverManager.getConnection(db_url, username, password);
		return con;
	}

	public MongoClient MongoConnection(String database_type, String username, String password, String db_url)
			throws SQLException {
		if (database_type.equalsIgnoreCase("MongoDB")) {
			String uriString = "mongodb://root:root1234@13.233.70.96:27017/?authMechanism=SCRAM-SHA-1"; // Replace with
																										// your MongoDB
																										// URI
			return createMongoClient(uriString);
		} else {
			return null;
		}

	}

	public MongoClient createMongoClient(String uriString) {
		ConnectionString connectionString = new ConnectionString(uriString);
		return MongoClients.create(connectionString);
	}

	public Object ConnectionforNosql(String dbhostname, String portnumber) throws SQLException {

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

//    private static Cluster clusterInstance;

//	public static synchronized Cluster getClusterInstance(String databaseType, String username, String password, String dbUrl) {
//		
//		dbUrl="couchbase://13.233.70.96";
//		username="Administrator";
//		password="password";
//		
//        if (clusterInstance == null) {
//            ClusterEnvironment clusterEnvironment = ClusterEnvironment.builder().build();
//            clusterInstance = Cluster.connect(dbUrl, ClusterOptions.clusterOptions(username, password).environment(clusterEnvironment));
//            return clusterInstance;
//        }
//        
//        else {
//        	return null;
//        }
//    }

//	private static Cluster clusterInstance;
//	public static synchronized Cluster getClusterInstance(String databaseType, String username, String password,String dbUrl) {
//	    if (clusterInstance == null) {
//	        ClusterEnvironment clusterEnvironment = ClusterEnvironment.builder().build();
//	        clusterInstance = Cluster.connect(dbUrl, ClusterOptions.clusterOptions(username, password).environment(clusterEnvironment));
//	    }
//	    
//	    return clusterInstance;
//	}

	private static Cluster clusterInstance;

	public static synchronized Cluster getClusterInstance(String databaseType, String username, String password,
			String dbUrl) {
		if (clusterInstance == null) {
			try {
				ClusterEnvironment clusterEnvironment = ClusterEnvironment.builder().build();
				clusterInstance = Cluster.connect(dbUrl,
						ClusterOptions.clusterOptions(username, password).environment(clusterEnvironment));
			} catch (CouchbaseException e) {
				// Handle connection exceptions
				e.printStackTrace(); // You can log the exception for debugging
			}
		}

		return clusterInstance;
	}

//    public  Bucket getBucket(String databaseType, String username, String password, String dbUrl) throws SQLException {
//        if (databaseType.equalsIgnoreCase("couchbase")) {
//            return getClusterInstance(databaseType, username, password, dbUrl).bucket("suresetu");
//        }
//        return null;
//    }

//	public Cluster getCouchbaseCluster(String dbUrl, String username, String password) {
//	    ClusterOptions options = ClusterOptions
//	            .clusterOptions(username, password)
//	            .build();
//	    
//	    return Cluster.connect(dbUrl, options);
//	}

	public Connection ibmdb2connection(String username, String password, String db_url) throws SQLException {
		// Load the Db2 JDBC driver
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");
		} catch (ClassNotFoundException e) {
			throw new SQLException("Db2 JDBC driver not found. Make sure to include it in your classpath.");
		}

		Connection con = DriverManager.getConnection(db_url, username, password);
		return con;
	}

//  POSTGRESQL CONNECTION
	public Connection postgreconnection(String username, String password, String db_url) throws SQLException {

		Connection con = DriverManager.getConnection(db_url, username, password);
		return con;

	}

//  MYSQL_LITE CONNECTION
	public Connection mysql_liteconnection(String username, String password, String db_url) throws SQLException {

		Connection con = DriverManager.getConnection(db_url, username, password);
		return con;

	}

//  MS_SQL CONNECTION
	public Connection ms_sqlconnection(String username, String password, String db_url) throws SQLException {

		Connection con = DriverManager.getConnection(db_url, username, password);
		return con;

	}

//  ORACLE CONNECTION
	public Connection oracleconnection(String username, String password, String db_url) throws SQLException {

		Connection con = DriverManager.getConnection(db_url, username, password);
		return con;

	}

//  MARIADB CONNECTION
	public Connection maria_dbconnection(String username, String password, String db_url) throws SQLException {

		Connection con = DriverManager.getConnection(db_url, username, password);
		return con;

	}

	public Connection firebirdConnection(String username, String password, String db_url) throws SQLException {
		// Load the Firebird JDBC driver
		try {
			Class.forName("org.firebirdsql.jdbc.FBDriver");
		} catch (ClassNotFoundException e) {
			throw new SQLException("Firebird JDBC driver not found. Make sure to include it in your classpath.");
		}

		Connection con = DriverManager.getConnection(db_url, username, password);
		return con;
	}

	public List<String> getMongoCollectionNames(String uriString, String databaseName) {
		List<String> collectionNames = new ArrayList<>();
		try {
			MongoClientSettings settings = MongoClientSettings.builder()
					.applyConnectionString(new ConnectionString(uriString)).build();

			try (MongoClient mongoClient = MongoClients.create(settings)) {
				MongoDatabase database = mongoClient.getDatabase(databaseName);

				for (String collectionName : database.listCollectionNames()) {
					collectionNames.add(collectionName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return collectionNames;
	}

	public Set<String> getCollectionFieldNames(String uriString, String databaseName, String collectionName) {
		Set<String> fieldNames = new HashSet<>();
		try {
			MongoClientSettings settings = MongoClientSettings.builder()
					.applyConnectionString(new ConnectionString(uriString)).build();

			try (MongoClient mongoClient = MongoClients.create(settings)) {
				MongoDatabase database = mongoClient.getDatabase(databaseName);
				MongoCollection<Document> collection = database.getCollection(collectionName);

				// Find all documents in the collection
				try (MongoCursor<Document> cursor = collection.find().iterator()) {
					while (cursor.hasNext()) {
						Document document = cursor.next();
						for (String fieldName : document.keySet()) {
							fieldNames.add(fieldName);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fieldNames;
	}

	public List<String> getRedisKeys(String hostName, String portNumber) {
		List<String> redisKeys = new ArrayList<>();

		try {
			Jedis jedis = new Jedis(hostName, Integer.parseInt(portNumber));
			jedis.connect();

			if (jedis.isConnected()) {
				redisKeys = new ArrayList<>(jedis.keys("*")); // Fetch all keys (equivalent to tables in Redis)
			}

			jedis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return redisKeys;
	}

	public DynamoDbClient createDynamoDbClient(String accessKeyId, String secretAccessKey, String url, String region) {

//		 accessKeyId="AKIAQMXNGR7W5BGSO2KD";
//     	secretAccessKey="y8Pq2bdiZafmEyvxDilqkmqAfQQSLf3kcMJrhong";
//     	url="http://13.233.70.96:8002";
		return DynamoDbClient.builder().region(Region.AP_SOUTH_1) // Replace with your desired region
				.credentialsProvider(
						StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretAccessKey)))
				.endpointOverride(URI.create(url)) // Your DynamoDB endpoint URL
				.build();

	}

}
