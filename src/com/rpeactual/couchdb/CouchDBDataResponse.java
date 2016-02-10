package com.rpeactual.couchdb;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CouchDBDataResponse
{
	private String _id = null;
	private String _rev = null;
	private String data = null;
	
	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	public String get_id()
	{
		return _id;
	}

	public void set_id(String _id)
	{
		this._id = _id;
	}

	public String get_rev()
	{
		return _rev;
	}

	public void set_rev(String _rev)
	{
		this._rev = _rev;
	}

	@Override
	public String toString()
	{
		return  "id: " + get_id() + " rev: " + get_rev() + " data: " + getData();
	}
}