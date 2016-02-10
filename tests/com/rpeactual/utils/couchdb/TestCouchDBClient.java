package com.rpeactual.utils.couchdb;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.rpeactual.couchdb.CouchDBClient;
import com.rpeactual.couchdb.CouchDBConnectionInfo;
import com.rpeactual.couchdb.CouchDBDataResponse;
import com.rpeactual.couchdb.CouchDBException;
import com.rpeactual.couchdb.CouchDBResponse;

public class TestCouchDBClient
{
	private static final String CLOUDANT_URL = System.getenv("CLOUDANT_URL");
	private static final String CLOUDANT_DBNAME = System.getenv("CLOUDANT_DBNAME");
	private static final String CLOUDANT_PASSWORD = System.getenv("CLOUDANT_PASSWORD");
	private static final String CLOUDANT_USERNAME = System.getenv("CLOUDANT_USERNAME");

	private static final String DOC_ID = "7e9ab3e882c7b6b1066760cc8d078760";
	
	@XmlRootElement
	public static final class Data
	{
		public String name = "";
		public String nickname = "";
		
		public Data(){}
		
		public Data( String name, String nickname)
		{
			this.name = name;
			this.nickname = nickname;
		}

		@Override
		public String toString()
		{
			return "name : " + this.name + " nickname: " + this.nickname;
		}
	}
	
	@Test
	public void testCreateDB() throws CouchDBException
	{
		CouchDBConnectionInfo connectionInfo = new CouchDBConnectionInfo( CLOUDANT_URL, CLOUDANT_USERNAME, CLOUDANT_PASSWORD, null);
		CouchDBClient client = new CouchDBClient(connectionInfo);

		CouchDBResponse response = client.createDB("gamma");
		
		Assert.assertNotNull( response);
		
		System.out.println( response.toString());
	}
	

	@Test
	public void testStore() throws CouchDBException
	{
		CouchDBConnectionInfo connectionInfo = new CouchDBConnectionInfo( CLOUDANT_URL, CLOUDANT_USERNAME, CLOUDANT_PASSWORD, CLOUDANT_DBNAME);
		CouchDBClient client = new CouchDBClient(connectionInfo);
		
		Gson gson = new Gson();
		Data data = new Data("dragos", "spurlos");
		CouchDBResponse response = client.store( null, null, gson.toJson(data));
		
		Assert.assertNotNull( response);
		
		System.out.println( response.toString());
	}
	
	@Test
	public void testGet() throws CouchDBException
	{
		CouchDBConnectionInfo connectionInfo = new CouchDBConnectionInfo( CLOUDANT_URL, CLOUDANT_USERNAME, CLOUDANT_PASSWORD, CLOUDANT_DBNAME);
		CouchDBClient client = new CouchDBClient(connectionInfo);
		
		CouchDBDataResponse response = client.read( DOC_ID);
		Assert.assertNotNull( response);
		System.out.println( response.toString());
		
		Data data = new Gson().fromJson(response.getData(), Data.class);
		System.out.println( data.toString());
	}
	
	@Test
	public void testUpdate() throws CouchDBException
	{
		CouchDBConnectionInfo connectionInfo = new CouchDBConnectionInfo( CLOUDANT_URL, CLOUDANT_USERNAME, CLOUDANT_PASSWORD, CLOUDANT_DBNAME);
		CouchDBClient client = new CouchDBClient(connectionInfo);
		Gson gson = new Gson();
		
		CouchDBDataResponse getResponse = client.read( DOC_ID);
		Assert.assertNotNull( getResponse);
		System.out.println( getResponse.toString());
		
		// generate some new data
		Data data = new Data("dragos" + UUID.randomUUID(), "spurlos" + UUID.randomUUID());
		
		CouchDBResponse updateResponse = client.store( getResponse.get_id(), getResponse.get_rev(), gson.toJson(data));
		Assert.assertNotNull( updateResponse);
		System.out.println( updateResponse.toString());
	}
	
}
