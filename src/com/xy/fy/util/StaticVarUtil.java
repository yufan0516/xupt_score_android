package com.xy.fy.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.xy.fy.singleton.Comment;
import com.xy.fy.singleton.Message;
import com.xy.fy.singleton.Student;
import com.xy.fy.view.ToolClass;

/**
 * �ͻ��˾�̬������
 * 
 * @author ����
 * 
 */
public class StaticVarUtil {

	/**
	 * listHerf��tittle
	 */
	public static final String QUERY_SCORE= "�ɼ���ѯ";
	/**
	 * handler�е�ֵ
	 */
	public static final int JUDGE_ACCOUNT_END = 1;
	public static final int JUDGE_NICKNAME_END = 2;
	public static final int JUDGE_EMAIL_END = 3;
	public static final int INTERNET_ERROR = 4;

	public static final int REGISTER_START = 5;
	public static final int REGISTER_END = 6;

	public static final int GET_PASSWORD_BACK1_START = 7;
	public static final int GET_PASSWORD_BACK1_END = 8;

	public static final int GET_PASSWORD_BACK2_START = 9;
	public static final int GET_PASSWORD_BACK2_END = 10;

	public static final int LOGIN_START = 11;
	public static final int LOGIN_END = 12;

	// һ�鳣�õ�handler����
	public static final int START = 13;
	public static final int END_SUCCESS = 14;
	public static final int END_FAIL = 15;

	public static final String ACCOUNT = "account";
	public static final String PASSWORD = "password";
	public static final String USER_INFO = "userinfo";
	public static final String IS_REMEMBER = "isRemember";

	public static ArrayList<Activity> activities = new ArrayList<Activity>();

	public static Student student = new Student();

	public static String PATH = null;// Ӧ�õ�Ŀ¼"/sdcard/FengYun"

	public static String response = null;// ���������ص�����

	public static String largePicPath = null;// ��ͼƬ·��

	public static String smallPicPath = null;// СͼƬ·��

	public static Message message = null;// �����˵˵

	public static ExecutorService executorService = Executors
			.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);// ����ϵͳ��Դ�����̳߳ش�С

	public static String lastMessageTime = null;// ���һ��˵˵�ķ���ʱ��
    public static String session = null;//������Ҫʹ�õĵ���session
	public static String fileName = null;// ������ļ���

	// ���е�herf
	public static List<HashMap<String, String>> listHerf = new ArrayList<HashMap<String, String>>();

	public static Student parseJsonToStudent(String strJson) {
		Student student = null;
		try {
			JSONObject jsonObject = new JSONObject(strJson);
			student = new Student();
			student.setAccount(jsonObject.optInt("account"));
			student.setPassword(jsonObject.optString("password"));
		} catch (JSONException e) {
			e.printStackTrace();
			student = null;
		}
		return student;
	}

	/**
	 * ����ջ��˳�������˳�
	 */
	public static void quit() {
		for (int i = StaticVarUtil.activities.size() - 1; i >= 0; i--) {
			if (StaticVarUtil.activities.get(i) != null) {
				StaticVarUtil.activities.get(i).finish();
			}
		}
	}

	/**
	 * �õ�һ��Message
	 * 
	 * @return Message
	 */
	public static Message getItemMessage(String jsonStr) {
		Message message = null;
		try {
			message = new Message();
			JSONObject json = new JSONObject(jsonStr);
			message.setAccount(json.optInt("account"));
			message.setCollegeId(json.optInt("collegeId"));
			message.setColNum(json.optInt("colNum"));
			message.setComNum(json.optInt("comNum"));
			message.setDate(json.optString("date"));
			message.setKind(json.optInt("kind"));
			message.setLarPic(json.optString("larPic"));
			message.setMsgId(json.optInt("msgId"));
			message.setPraNum(json.optInt("praNum"));
			message.setSmaPic(json.optString("smaPic"));
			message.setText(json.optString("text"));
			message.setTime(json.optString("time"));
			message.setNickname(json.optString("nickname"));
			message.setHeadPhoto(json.optString("headPhoto"));
		} catch (Exception e) {
			System.out.println("StaticVarUtil.getItemMessage()����");
			e.printStackTrace();
		}
		return message;
	}

	/**
	 * �õ����е���Ϣ����,���ұ������һ��˵˵��ʱ��
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static ArrayList<Message> getAllMessage(String jsonStr) {
		ArrayList<Message> allMessage = null;
		try {
			allMessage = new ArrayList<Message>();
			JSONArray allJson = new JSONArray(jsonStr);
			for (int i = 0; i < allJson.length(); i++) {
				allMessage.add(getItemMessage(allJson.getString(i)));
			}
			StaticVarUtil.lastMessageTime = allMessage.get(
					allMessage.size() - 1).getDate()
					+ " " + allMessage.get(allMessage.size() - 1).getTime();
		} catch (Exception e) {
			System.out.println("StaticVarUtil.getAllMessage()����");
			e.printStackTrace();
		}
		return allMessage;
	}

	/**
	 * �����ղ������Ϣ
	 * 
	 * @param messageId
	 */
	public static void collect(int messageId, Context context) {
		SharedPreferences share = context.getSharedPreferences(
				"messageIsCollect", Activity.MODE_PRIVATE);
		Editor editor = share.edit();
		editor.putBoolean(messageId + "", true);
		editor.commit();
	}

	/**
	 * �����Ƿ��ղ�
	 * 
	 * @param messageId
	 *            ��Ϣ����
	 * @return
	 */
	public static boolean isCollect(int messageId, Context context) {
		if (StaticVarUtil.student == null) {
			return false;
		}
		SharedPreferences share = context.getSharedPreferences(
				"messageIsCollect", Activity.MODE_PRIVATE);
		return share.getBoolean(messageId + "", false);
	}

	/**
	 * �Ƿ��޹��� ȡ�������ݿ⣬���ж�Ӧ��������ڣ���ô����true�� ������뱾�����ݿ⣬Ȼ�󷵻�false
	 * 
	 * @param position
	 *            λ��
	 * @return
	 */
	public static boolean isPraised(int messageId, Context context) {
		SharedPreferences share = context.getSharedPreferences(
				"messageIsPraise", Activity.MODE_PRIVATE);
		// ������ݿ����������ֵ��
		if (share.getBoolean(messageId + "", false) == true) {
			return true;
		}
		return false;
	}

	/**
	 * �����˵˵�ޣ����б���
	 * 
	 * @param messageId
	 */
	public static void praise(int messageId, Context context) {
		SharedPreferences share = context.getSharedPreferences(
				"messageIsPraise", Activity.MODE_PRIVATE);
		Editor editor = share.edit();
		editor.putBoolean(messageId + "", true);
		editor.commit();
	}

	/**
	 * �õ����е�Comment
	 * 
	 * @param jsonData
	 *            json����Դ
	 */
	public static ArrayList<Comment> getAllComments(String jsonData) {
		ArrayList<Comment> allComments = null;
		try {
			allComments = new ArrayList<Comment>();
			JSONArray jsonArray = new JSONArray(jsonData);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Comment comment = new Comment();
				comment.setContent(jsonObject.getString("content"));
				comment.setDate(jsonObject.getString("date"));
				comment.setName(jsonObject.getString("name"));
				comment.setTime(jsonObject.getString("time"));
				allComments.add(comment);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return allComments;
	}
}