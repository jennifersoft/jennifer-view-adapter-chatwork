# jennifer-view-adapter-chatwork
Simple adapter to push EVENT notifications to Chatwork room.

# Important Notice

Please use this branch on JENNIFER Server 5.2.3 and more.

# Getting started

The first step is to register the adapter:

1. In JENNIFER Client, open the management area and Navigate to Extension and Notice > Adapter and Plugin
1. Make sure the adapter tab is selected then click the [+Add] button.
1. Select [Event] from the classifications dropdown.
1. Enter `chatwork` as ID.
1. Enter the path to the adapter JAR file jennifer-view-adapter-chatwork.jar or upload the JAR file from you local machine.
1. Enter the adapter class name `adapter.jennifer.chatwork.ChatworkAdapter` and save the settings.

The second step is to register parameters for sending messages.
1. Open the management area and Navigate to Extension and Notice > Adapter and Plugin.
1. Make sure the adapter tab is selected then click the `chatwork adapter` that was registerd privious step.
1. Click [Option] button.
1. Click [+Add] button.
1. Enter `token` as Key and token vale for using chatwork API as Value. Then save the setting.
1. Click [+Add] button.
1. Enter `room_name` as Key and the room name to send message as Value. Then save the setting.


### Options

The following table shows the required options for this adapter

| Key |	Required |	Description	Example |
| --- | --- | --- |
| token |	YES |	Set Chatwork API token |
| room_name |	YES | Set target destination for message. | 


All step is cleared, restart JENNIFER View Server then you can check the JENNIFER's event messages on Chatwork.
