package com.rpeactual.couchdb;


public class CouchDBConnectionInfo
{
	private String url = null;
	private String user = null;
	private String password = null;
	private String database = null;

	public CouchDBConnectionInfo(String url, String user, String password, String database)
	{
		this.url = url;
		this.user = user;
		this.password = password;
		this.database = database;
	}

	public String getDatabase()
	{
		return database;
	}

	public void setDatabase(String database)
	{
		this.database = database;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getUser()
	{
		return user;
	}

	public void setUser(String user)
	{
		this.user = user;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
}