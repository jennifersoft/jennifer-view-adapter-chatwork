package adapter.jennifer.chatwork.entity;

public class ChatworkProp {

	private String token;
	private String roomName;
	
	public void setToken(String token)
	{
		this.token = token;
	}
	
	public void setRoomName(String name)
	{
		this.roomName = name;
	}
	
	public String getToken()
	{
		return this.token;
	}
	
	public String getRoomName()
	{
		return this.roomName;
	}
}
