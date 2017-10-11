/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lpgo.pepenet.client;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

public class WSClient implements ComClient 
{
	private int port;
	private WebSocketClient wsclient; //Websocket
	private boolean connected;
	
	//For the Client side only
	private int myID;
	private ClientMSG clientmsg;     //For Bidirectional Communication mode
	public WSClient ( String ip, int port, ClientMSG clientmsg, JAVAClient.platformCode pC )
	{
		this.port = port;
		connected = false;
		this.connectClient ( ip );
		myID = -1;
		this.clientmsg = clientmsg; //To call the methods of the the upper level class
	}
	
	public void connectClient ( String ip )
	{
		if ( !ip.isEmpty (  ) )
		{
			//Websocket implementation
			URI url = null; //URI (url address of the server)
			try
			{
				url = new URI ( "ws://" + ip + ":" + port ); //We create the URI of the server. Use a port upper than 1024 on Android and Linux!
			}
			catch ( URISyntaxException e )
			{
				e.printStackTrace (  );
			} 

			//We select the standard implementation of WebSocket
			Draft standard = new Draft_6455 (  ); 

			wsclient = new WebSocketClient ( url, standard )
			{

				@Override
				public void onOpen ( ServerHandshake handshake )
				{
					connected = true;
					requestID (  );
				}
				
				@Override
				public void onMessage( String message )
				{
					System.out.println ( "Client receives:  " + message );
					//Low level control of Messages received from server
					//SERVER CLOSES MY WS CONNECTION.
					if ( message.equals ( "MSG_CLOSE_WS" ) ) 
					{
						this.close (  ); 
					}

					//SERVER SEND MY CLIENT ID.
					else if ( message.startsWith ( "MSG_SEND_ID" ) )
					{
						String [] values = message.split ( "\\*" ); //splitter with the " " separator
						myID = Integer.valueOf ( values[1] );
						clientmsg.client.setMyID ( myID );
						System.out.println ( "myID = " + myID );
					}
					//High level Message, send to the ClientMSG class
					else
					{
						clientmsg.onMessage ( message );
					}	
				}

				@Override
				public void onError( Exception ex )
				{
					System.out.println ( "WSClient Error: " + ex );
					ex.printStackTrace (  );
				}
				
				@Override
				public void onClose ( int code, String reason, boolean remote )
				{
					connected = false;
				}
			};
			wsclient.connect (  ); //And we create the connection between client and server
		}
	}

	private void requestID (  )
	{
		sendMsg ( "MSG_REQUEST_ID" );
	}
	
	public boolean sendMsg ( String msg )
	{
		if ( connected )
		{
			System.out.println ( "\nClient sends:  " + msg );
			wsclient.send ( msg );
			return true;
		}
		else return false;
	}

	public boolean isConnected (  )
	{
		return connected;
	}

	public int getId (  )
	{
		return myID;
	}
	
	public void close (  )
	{
		wsclient.close (  );
		connected = false;
	}
}