/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lpgo.pepenet.server;

import java.util.List;

import com.lpgo.pepenet.cme.MessageEventSource;

public class JAVAServer
{
	// region Variables
	public ServerMSG serverMSG;
	public int serverPort;
	private List<ClientMessageListing> messageFromClientList;
	MessageEventSource source;
	boolean usingPNCME;     // PepeNet CustomMessageEvent utilities
	// endregion

	public static enum platformCode { DESKTOP, ANDROID, HTML5 }; // Platform enum is necessary in order to use the right code
	
	// There are TWO constructors here, use the right one!
	// This constructor passes a list that you'll have to poll for new messages.
	public JAVAServer ( platformCode pC, List<ClientMessageListing> messageFromClientList, int serverPort )
	{
		super (  );
		this.serverPort = serverPort;
		this.source = null;
		this.usingPNCME = false;
		serverMSG = new ServerMSG ( serverPort, this, pC ); // Don't use a port lower than 1024 on Android and Linux!
		this.messageFromClientList = messageFromClientList;
	}

	// This constructor instead passes a source for the custom message events. The reason we don't need a list here
	// is because the firing of the event actually passes the clientID and message along, so you don't need to poll
	// for new messages.
	public JAVAServer ( platformCode pC, int serverPort, MessageEventSource source )
	{
		super (  );
		this.serverPort = serverPort;
		this.source = source;
		this.usingPNCME = true;
		serverMSG = new ServerMSG ( serverPort, this, pC ); // Don't use a port lower than 1024 on Android and Linux!
		this.messageFromClientList = null;
	}
	
	// When NOT using PNCME, this method is the choke point for all messages coming from clients to this server.
	public void getMessage ( ClientMessageListing listing )
	{
		if ( !usingPNCME && messageFromClientList != null )
		synchronized ( messageFromClientList )
		{
			messageFromClientList.add ( listing ); // You'll have to poll this list regularly, somehow, maybe in the main render loop? 
		}
	}
	
	// When *using* PNCME, _this_ method is the choke point for all messages coming from clients to this server.
	public void getMessage ( int clientID, String msg )
	{
		// custom event fires here
		if ( usingPNCME && source != null )
		{
			// We're using the included PepeNet Custom Message Event handler
			source.fireMessageReceived ( clientID, msg );
		}
	}
	
	// Send a message to one client
	public void messageSingleClientSend ( int id, String message )
	{
		serverMSG.sendMessageToClient( id, message );
	}
	
	// Send a message to multiple specific clients, possibly but not necessarily to all
	// This allows you to filter who you want to send messages to.
	// You might use this for sending messages to only those people in a particular clan or guild,
	// people who are in a specific area, or people on a specific chat channel.
	public void messageMultiClientSend ( MultiClientMessage multiMsg )
	{
		for ( int m : multiMsg.getClientIDs (  ) )
		{
			serverMSG.sendMessageToClient( m, multiMsg.getMsg (  ) );
		}
	}
	
	// send a message to ALL clients almost at once (obviously clients will get messages at
	// slightly different times, based on server lag, latency, etc.
	public void messageBroadcast ( String message )
	{
		serverMSG.sendMessageToAll ( message );
	}
	
	public void serverShutdown (  )
	{
		serverMSG.close (  );
	}

	// region GettersNSetters
	public List<ClientMessageListing> getMessageFromClientList (  ) { return messageFromClientList; }
	public void setMessageFromClientList ( List<ClientMessageListing> messageFromClientList ) { this.messageFromClientList = messageFromClientList; }
	public MessageEventSource getSource (  ) { return source; }
	public boolean isUsingPNCME (  ) { return usingPNCME; }
	
	// endregion
}