package com.rpeactual.couchdb;

import java.io.IOException;

public class CouchDBException extends IOException
{
	public CouchDBException(Exception e)
	{
		super(e);
	}

	public CouchDBException(String message)
	{
		super( message);
	}

	private static final long serialVersionUID = 5717843099996306852L;
}
