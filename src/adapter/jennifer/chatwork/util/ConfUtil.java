package adapter.jennifer.chatwork.util;

import com.jennifersoft.view.extension.util.PropertyUtil;

import adapter.jennifer.chatwork.entity.ChatworkProp;

public class ConfUtil {

	  private static final String ADAPTER_ID = "chatwork";
	  
	  public static String getValue(String key)
	  {
	    return PropertyUtil.getValue(ADAPTER_ID, key);
	  }
	  
	  private static final ChatworkProp chatworkProperties = new ChatworkProp();
	  
	  public static ChatworkProp getChatworkProperties()
	  {
		  chatworkProperties.setToken(getValue("token"));
		  chatworkProperties.setRoomName(getValue("room_name"));
	    
		  return chatworkProperties;
	  }
}
