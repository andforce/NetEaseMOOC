package org.zarroboogs.wangyiyunclass.net;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCookieStore;

public class BroserContent {

	private CookieStore mCookieStore = new BasicCookieStore();
	private HttpClient mHttpClient = HttpClientFactory.creteHttpClient(mCookieStore);
	
	public CookieStore getCookieStore(){
		return mCookieStore;
	}
	
	public HttpClient getHttpClient(){
		return mHttpClient;
	}
	
}
