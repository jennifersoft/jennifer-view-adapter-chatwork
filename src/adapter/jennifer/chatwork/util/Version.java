package adapter.jennifer.chatwork.util;

public class Version {

	  public String getVersion()
	  {
	    return "1.0";
	  }
	  
	  public static void main(String[] args)
	  {
	    System.out.format("Chatwork Adapter Version %s\n", new Object[] { new Version().getVersion() });
	  }
}
