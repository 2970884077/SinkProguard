package com.Sink.ProGuard.bean;

public class Shop
{ 
	private String name;
	private String url;
	private String author;
	private String hash;

	public void setUrl(String url)
	{
		this.url=url;
	}

	public String getUrl()
	{
		return url;
	}

	public void setAuthor(String author)
	{
		this.author=author;
	}

	public String getAuthor()
	{
		return author;
	}

	public void setHash(String hash)
	{
		this.hash=hash;
	}

	public String getHash()
	{
		return hash;
	}
	
	public void setName(String name)
	{
		this.name=name;
	}
	public String getName()
	{
		return name;
	}

}
