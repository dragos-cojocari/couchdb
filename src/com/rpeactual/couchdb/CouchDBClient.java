package com.rpeactual.couchdb;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import com.google.gson.Gson;
import com.rpeactual.utils.ConnectionUtils;


public class CouchDBClient
{
	private static final String DOCUMENT_REVISION = "rev";
	private CouchDBConnectionInfo connectionInfo = null;
	private WebTarget couchClient = null;
	
	public CouchDBClient(CouchDBConnectionInfo connectionInfo)
	{
		this.connectionInfo = connectionInfo;
		
		Client client = ConnectionUtils.createClient();
		
		HttpAuthenticationFeature auth = HttpAuthenticationFeature.basicBuilder().nonPreemptive().credentials( this.connectionInfo.getUser(), this.connectionInfo.getPassword()).build();
		client.register(auth);

		this.couchClient = client.target(UriBuilder.fromUri( this.connectionInfo.getUrl().toString()).build());
	}
	
	public CouchDBResponse createDB(String dbName) throws CouchDBException
	{
		Response response = null;
		
		if ( dbName == null || dbName.isEmpty())
		{
			throw new CouchDBException( "Null dbname"); //$NON-NLS-1$
		}
		
		response = couchClient.path( dbName).request(MediaType.APPLICATION_JSON_TYPE).put(Entity.text(""));
		
		if ( !response.getStatusInfo().getFamily().equals(Family.SUCCESSFUL))
		{
			throw new CouchDBException("Failed to create database. Status code: " + response.getStatus()  + " " + response.readEntity(String.class));   //$NON-NLS-1$//$NON-NLS-2$
		}
		
		CouchDBResponse couchResponse = null;
		try
		{
			String json = response.readEntity( String.class);
			
			Gson gson = new Gson();
			couchResponse = gson.fromJson(json, CouchDBResponse.class);
		}
		catch (Exception e)
		{
			throw new CouchDBException(e);
		}
		
		return couchResponse;
	}
	
	public CouchDBResponse store(String id, String rev, String data) throws CouchDBException
	{
		Response response = null;
		
		if ( id != null)
		{
			response = couchClient.path(connectionInfo.getDatabase()).path( id).queryParam(DOCUMENT_REVISION, rev).request(MediaType.APPLICATION_JSON_TYPE).put(Entity.entity( data,MediaType.APPLICATION_JSON_TYPE));
		}
		else
		{
			response = couchClient.path(connectionInfo.getDatabase()).request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity( data,MediaType.APPLICATION_JSON_TYPE));
		}
		
		if ( !response.getStatusInfo().getFamily().equals(Family.SUCCESSFUL))
		{
			throw new CouchDBException("Failed to store the entity. Status code: " + response.getStatus()  + " " + response.readEntity(String.class));   //$NON-NLS-1$//$NON-NLS-2$
		}
		
		CouchDBResponse couchResponse = null;
		try
		{
			String json = response.readEntity( String.class);
			
			Gson gson = new Gson();
			couchResponse = gson.fromJson(json, CouchDBResponse.class);
			
		}
		catch (Exception e)
		{
			throw new CouchDBException(e);
		}
		
		if ( !"true".equals( couchResponse.getOk())) //$NON-NLS-1$
		{
			throw new CouchDBException( "Job update failed"); //$NON-NLS-1$
		}
		
		return couchResponse;
	}

	public CouchDBDataResponse read(String id) throws CouchDBException
	{
		Response response = null;
		
		if ( id == null)
		{
			throw new CouchDBException( "null id"); //$NON-NLS-1$
		}
		
		response = couchClient.path(connectionInfo.getDatabase()).path( id).request(MediaType.APPLICATION_JSON_TYPE).get();
		
		if ( !response.getStatusInfo().getFamily().equals(Family.SUCCESSFUL))
		{
			throw new CouchDBException("Failed to read the entity. Status code: " + response.getStatus()  + " " + response.readEntity(String.class));   //$NON-NLS-1$//$NON-NLS-2$
		}
		
		CouchDBDataResponse couchResponse = null;
		try
		{
			String json = response.readEntity( String.class);
			
			Gson gson = new Gson();
			couchResponse = gson.fromJson(json, CouchDBDataResponse.class);
			couchResponse.setData(json);
			
		}
		catch (Exception e)
		{
			throw new CouchDBException(e);
		}
		
		return couchResponse; 
	}
	
}
