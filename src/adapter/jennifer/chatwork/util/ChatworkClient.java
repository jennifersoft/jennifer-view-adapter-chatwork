package adapter.jennifer.chatwork.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

public class ChatworkClient {

	private final String CHATWORK_API_URL_ROOT = "https://api.chatwork.com/v2/rooms";
	private final String CHATWORK_API_TOKEN_HEADER = "X-ChatWorkToken";
	private String token = null;
	private String roomName = null;
	private int roomId = -1;
	private int connection_timeout = 1000;
	
	private HttpURLConnection connection = null;
	
	public ChatworkClient(String token, String room_name)
	{
		this.token = token;
		this.roomName = room_name;
		this.roomId = getRoomId(this.roomName);
	}
	
	private int getRoomId(String roomName)
	{
		try
	    {
			URL url = new URL(this.CHATWORK_API_URL_ROOT);
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(connection_timeout);
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty(CHATWORK_API_TOKEN_HEADER, token);
			connection.connect();
	      
			InputStream in = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line + "\n");
			}
			reader.close();

			if (connection.getResponseCode() == 200)
			{
				JSONArray roomArray = new JSONArray(response.toString());
				JSONObject room = null;
				for (int index = 0; index < roomArray.length(); index++)
				{
					room = roomArray.getJSONObject(index);
					if ( room.get("name").equals(roomName) )
					{
						System.out.println("3:" + room.getInt("room_id"));
						return room.getInt("room_id");
					}
				}
			}	
	    }
	    catch (Exception ex)
	    {
	    	System.out.println("Exception in ChatworkClient : " + ex.toString());
	    }
	    finally
	    {
	      if (connection != null) {
	        connection.disconnect();
	      }
	    }
		return -1;
	}
	
/*
	public static void main(String args[])
	{
		ChatworkClient client = new ChatworkClient("e53f2bb5f0a36c74fe96c6ae8d806a53","AdapterTest");
		String rtn = client.push("Test Message!");
		System.out.println("Result is " + rtn);
	}
*/
	public String push(String message)
	{
	    try
	    {
	      URL url = new URL(this.CHATWORK_API_URL_ROOT+"/"+this.roomId+"/messages");
	      connection = (HttpURLConnection)url.openConnection();
	      connection.setRequestMethod("POST");
	      connection.setConnectTimeout(connection_timeout);
	      connection.setUseCaches(false);
	      connection.setDoInput(true);
	      connection.setDoOutput(true);
	      connection.setRequestProperty("Accept", "application/json");
	      connection.setRequestProperty(CHATWORK_API_TOKEN_HEADER, token);
	      connection.connect();
	      
	      // Request
	      String contents = "body=" + URLEncoder.encode(message, "UTF-8");
	      DataOutputStream out = new DataOutputStream(connection.getOutputStream());
	      out.writeBytes(contents);
	      out.flush();
	      out.close();

	      // Response
	      InputStream in = connection.getInputStream();
	      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	      StringBuilder response = new StringBuilder();
	      String line;
	      while ((line = reader.readLine()) != null) {
	        response.append(line + "\n");
	      }
	      reader.close();
	      
	      	int responseCode = connection.getResponseCode();
	      	return String.valueOf(responseCode);
	    }
	    catch (Exception ex)
	    {
	    	System.out.println("Exception in ChatworkClient : " + ex.toString());
	    	return null;
	    }
	    finally
	    {
	      if (connection != null) {
	        connection.disconnect();
	      }
	    }
	}
}
