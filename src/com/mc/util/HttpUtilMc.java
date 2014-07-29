package com.mc.util;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * 
 * @author Administrator
 * @description �ǵ��޸Ĵ��룬���ͷ�������Ӧ��ʱ����Ҫ fall back ����
 */
public class HttpUtilMc {
	// ����URL
	// public static final String BASE_URL="http://10.0.2.2:8080/ShopServer/";
//	public static final String BASE_URL = "http://192.168.137.1:8080/TuoC/";
	 public static final String BASE_URL = "http://172.20.0.116:8080/xuptqueryscore/";
	// public static String SERVER_ADDRESS="192.168.1.103";
	/*
	 * public static String SERVER_ADDRESS="192.168.11.1"; public static int
	 * SERVER_PORT = 8080;
	 */

	public static String CONNECT_EXCEPTION = "�����쳣��";

	// ���Get�������request
	public static HttpGet getHttpGet(String url) {
		HttpGet request = new HttpGet(url);
		return request;
	}

	// ���Post�������request
	public static HttpPost getHttpPost(String url) {
		HttpPost request = new HttpPost(url);
		return request;
	}

	// ������������Ӧ����response
	public static HttpResponse getHttpResponse(HttpGet request)
			throws ClientProtocolException, IOException {
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}

	// ������������Ӧ����response
	public static HttpResponse getHttpResponse(HttpPost request)
			throws ClientProtocolException, IOException {
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}

	// ����Post���󣬻����Ӧ��ѯ���
	public static String queryStringForPost(String url) {
		// ����url���HttpPost����
		// for test remove , if run server ,need fall back
		HttpPost request = HttpUtilMc.getHttpPost(url);
		System.out.println("request==========" + request);
		String result = null;
		// for test remove , if run server ,need fall back
		try {
			// �����Ӧ����
			HttpResponse response = HttpUtilMc.getHttpResponse(request);

			System.out.println("response==========" + response);

			System.out
					.println("response.getStatusLine().getStatusCode()=========="
							+ response.getStatusLine().getStatusCode());
			// �ж��Ƿ�����ɹ�
			if (response.getStatusLine().getStatusCode() == 200) {
				System.out.println("��Ӧ�ɹ�");
				System.out.println("response.getEntity()=========="
						+ response.getEntity());

				// �����Ӧ
				result = EntityUtils.toString(response.getEntity(), "utf-8");// ��ֹ��������
				// result=new String(result.getBytes("ISO-8859-1"),"utf-8"); //
				System.out.println("result==========" + result);
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = HttpUtilMc.CONNECT_EXCEPTION;
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = HttpUtilMc.CONNECT_EXCEPTION;
			return result;
		}
		return null;

		// for test add , if run server ,need remove
		// return String.valueOf(1);
	}

	// �����Ӧ��ѯ���
	public static String queryStringForPost(HttpPost request) {
		String result = null;
		try {
			// �����Ӧ����
			HttpResponse response = HttpUtilMc.getHttpResponse(request);
			// �ж��Ƿ�����ɹ�
			if (response.getStatusLine().getStatusCode() == 200) {
				// �����Ӧ
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = HttpUtilMc.CONNECT_EXCEPTION;
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = HttpUtilMc.CONNECT_EXCEPTION;
			return result;
		}
		return null;
	}

	// ����Get���󣬻����Ӧ��ѯ���
	public static String queryStringForGet(String url) {
		// ���HttpGet����
		HttpGet request = HttpUtilMc.getHttpGet(url);
		String result = null;
		try {
			// �����Ӧ����
			HttpResponse response = HttpUtilMc.getHttpResponse(request);
			// �ж��Ƿ�����ɹ�
			if (response.getStatusLine().getStatusCode() == 200) {
				// �����Ӧ
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = HttpUtilMc.CONNECT_EXCEPTION;
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = HttpUtilMc.CONNECT_EXCEPTION;
			return result;
		}
		return null;
	}
}