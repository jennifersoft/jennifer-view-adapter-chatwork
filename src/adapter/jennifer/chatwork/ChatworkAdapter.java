package adapter.jennifer.chatwork;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.jennifersoft.view.adapter.JenniferAdapter;
import com.jennifersoft.view.adapter.JenniferModel;
import com.jennifersoft.view.adapter.model.JenniferEvent;
import com.jennifersoft.view.adapter.util.LogUtil;
import adapter.jennifer.chatwork.entity.ChatworkProp;
import adapter.jennifer.chatwork.util.ChatworkClient;
import adapter.jennifer.chatwork.util.ConfUtil;


public class ChatworkAdapter implements JenniferAdapter
{

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	@Override
	public void on(JenniferModel[] jennfierModel) {

	    try {
	    	ChatworkProp chatworkProp = ConfUtil.getChatworkProperties();
	    	
	    	ChatworkClient chatworkClient = null;
	    	
	    	try {
	    		chatworkClient = new ChatworkClient(chatworkProp.getToken(),chatworkProp.getRoomName());    		
	    	} catch (Exception e) {
	    		LogUtil.error("Fail to push message to Chatwork.");
				System.exit(0);
	    	}
	    	
	    	for (JenniferModel model:jennfierModel)
			{
				JenniferEvent eventModel = (JenniferEvent)model;
			  	
				String chatworkMessage = jenniferEventToString(eventModel);
				LogUtil.info(chatworkMessage);
				
				// Send message
				String result = chatworkClient.push(chatworkMessage);
				if (result.equals("200"))
					LogUtil.info("Success to push a Message : " + result);
				else
					LogUtil.info("Fail to push a Message : " + result);
			}
			
		} catch (Exception e) {
			LogUtil.error(e.toString());
		}
	}

	private String jenniferEventToString(JenniferEvent eventModel)
	{
		StringBuilder messageBody = new StringBuilder();
		messageBody.append("[info]");
            messageBody.append("[" + eventModel.getErrorLevel() + "]");
	    messageBody.append("The following event " + eventModel.getErrorType() + " was caught by JENNIFER\n");
	    messageBody.append("[hr]");
	    messageBody.append("Affected Domain [ID:NAME]: " + eventModel.getDomainId() + ":" + eventModel.getDomainName() + "\n");
	    messageBody.append("Affected Instance [ID:NAME]: " + eventModel.getInstanceId() + ":" + eventModel.getInstanceName() + "\n");
	    String message = eventModel.getMessage().length() == 0 ? "None" : eventModel.getMessage();
	    messageBody.append("Message : " + message + "\n");
	    String detailedMessage = eventModel.getDetailMessage().length() == 0 ? "None" : eventModel.getDetailMessage();
	    messageBody.append("Detailed Message : " + detailedMessage + "\n");
	    String serviceName = eventModel.getServiceName().length() == 0 ? "Not available" : eventModel.getServiceName();
	    messageBody.append("Service Name : " + serviceName + "\n");
	    String transactionId = eventModel.getTxId() + "";
	    messageBody.append("Transaction Id: " + transactionId + "\n");
	    String metricsName = eventModel.getMetricsName().length() == 0 ? "Not available" : eventModel.getMetricsName();
	    messageBody.append("Metrics Name : " + metricsName + "\n");
	    Date d = new Date(eventModel.getTime());
	    messageBody.append("Event Time [Raw:HumanRedable]: " + eventModel.getTime() + ":" + this.sdf.format(d) + "\n");
	    messageBody.append("\nThis message was created automatically by JENNIFER Adapter");
	    messageBody.append("[/info]");
	    
	    return messageBody.toString();
	}

}
