package com.rpeactual.couchdb;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CouchDBResponse
{
	private String ok = null;
	private String id = null;
	private String rev = null;
	
	public String getOk()
	{
		return ok;
	}

	public void setOk(String ok)
	{
		this.ok = ok;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getRev()
	{
		return rev;
	}

	public void setRev(String rev)
	{
		this.rev = rev;
	}

	@Override
	public String toString()
	{
		return  "ok: " + getOk() + " id: " + getId() + " rev: " + getRev();
	}
	
}