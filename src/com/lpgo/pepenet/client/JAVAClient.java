/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lpgo.pepenet.client;

import java.util.List;

import com.lpgo.pepenet.cme.MessageEventSource;


/**
 * Main Desktop/Android/HTML5 class
 * 
 * @author JOSE LUIS CEBRIAN MARQUEZ
 *
 */

// TBE: The Big Explanation of: Pepe's Networking Stuff.
//
// First, understand that as far as the networking stuff goes, the whole Kit 'N' Kaboodle starts right HERE, in this class.
// JAVAClient creates a ClientMSG, which creates a WSClient (WebSocketClient), which in turn is based on the ComClient interface.
// Implementing this chain of events is done by calling the JAVAClient constructor during the creation of the game, and accessing
// the JAVAClient in successive screens through the Game class. From the moment Pepe's code is added to a LibGDX game in this
// fashion, the JAVAClient object and the Game object should be considered Siamese twins, ie. joined at the hip. This is why
// JAVAClient is created in the Game class.
//
// Further, usage of this setup means that when you need to send a message to the server, you do so like this:
//
// 		<Insert either "this" or the Game object name here>.<Insert JAVAClient object name here>.clientMSG.sendMessage
// 		(
//			"Put your unbelievably important message here or substitute a String variable for this String literal."
//		);
//
// Actually readable example:
//
//		game.jClient.clientMSG.sendMessage ( message );
//
// This second example assumes a Game object called game, a JAVAClient object called jClient, and a String called message.

public class JAVAClient
{
	// region Variables
	private List<String> messageFromServerList; 
	private MessageEventSource source;
	private ClientMSG clientMSG;
	private String ip;
	private int myID;
	private int port;
	boolean usingPNCME;
	public static enum platformCode { DESKTOP, ANDROID, HTML5 };
	// endregion
	
	// Note that there are TWO constructors.
	// Use THIS constructor if you want to poll for messages rather than using our sparkly new
	// PepeNet CustomMessageEvent ( PNCME ) system.
	public JAVAClient( platformCode pc, List<String> messageFromServerList, String ip, int port )
	{
		super (  );
		this.ip = ip;
		this.port = port;
		this.usingPNCME = false;
		//Here we must create the client connection to the server
//		clientMSG = new ClientMSG ( "127.0.0.1", 5080, this, pc ); //Change here the IP and Port for your Server IP and Port
		clientMSG = new ClientMSG ( ip, port, this, pc );
		myID = clientMSG.getId (  );
	}

	// Use THIS constructor to use the PNCME system.
	public JAVAClient( platformCode pc, String ip, int port, MessageEventSource source )
	{
		super (  );
		this.ip = ip;
		this.port = port;
		this.source = source;
		this.usingPNCME = true;
		
		//Here we must create the client connection to the server
//		clientMSG = new ClientMSG ( "127.0.0.1", 5080, this, pc ); //Change here the IP and Port for your Server IP and Port
		clientMSG = new ClientMSG ( ip, port, this, pc );
		myID = clientMSG.getId (  );
	}
	
	// Unlike the server, we don't need to deal with a clientID here, since the server is the
	// only connection we should be getting messages from.
	// So, we don't need to have two getMessage methods here, as a String will do nicely for either
	// an event or a list.
	public void getMessage ( String message )
	{
		// need to do something with the message; if we are using PNCME, fire the message event.
		if ( usingPNCME && source != null )
		{
			source.fireMessageReceived ( message );
			return; // make sure we exit.
		}
		else if ( !usingPNCME && messageFromServerList != null )
		{
			messageFromServerList.add ( message ); // You'll have to poll this list regularly, somehow, maybe in the main render loop? 
		}
	}
	
	// One sendMessage to rule them all, and in the network, bind them...
	public void sendMessage ( String message )
	{
		clientMSG.sendMessage ( message );
	}
	
	// Going to leave this in, so peeps can do the right thing and close the client out nicely.
	public void dispose (  )
	{
		clientMSG.close (  );
	}

	// region GettersNSetters
	public String             getIp                    (           ) { return ip;                    }
	public void               setIp                    ( String ip ) { this.ip = ip;                 }
	public int                getMyID                  (           ) { return myID;                  }
	public void               setMyID                  ( int myID  ) { this.myID = myID;             }
	public int                getPort                  (           ) { return port;                  }
	public void               setPort                  ( int port  ) { this.port = port;             }
	public ClientMSG          getClientMSG             (           ) { return clientMSG;             }
	public List<String>       getMessageFromServerList (           ) { return messageFromServerList; }
	public MessageEventSource getSource                (           ) { return source;                }
	
	// endregion
}