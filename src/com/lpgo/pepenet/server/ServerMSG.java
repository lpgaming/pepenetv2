/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lpgo.pepenet.server;

public class ServerMSG
{
	WSServer wsserver;
	JAVAServer server; //For Bidirectional Communication mode
	
	public ServerMSG ( int port, JAVAServer server, JAVAServer.platformCode pC )
	{
		wsserver = new WSServer ( port, this, pC );
		this.server = server;
	}
	
	public void onMessage ( int clientID, String message )
	{
		// System.out.println ( "Receiving message from Client to Server: " + message );

		if ( server.usingPNCME )
		{
			server.getMessage ( clientID, message );
		}
		else
		{
			ClientMessageListing listing = new ClientMessageListing ( clientID, message );
			server.getMessage ( listing );			
		}
	}
	
	public boolean sendMessageToClient ( int clientID, String message )
	{
		System.out.println ( "Sending message from Server to Client: " + message );
		return ( wsserver.sendToClient ( clientID, message ) );
	}
	
	public void sendMessageToAll ( String message )
	{
		System.out.println ( "Sending message from Server to ALL CLIENTS: " + message );
		wsserver.sendToAll ( message );
	}
	
	public void close (  )
	{
		wsserver.stop (  );
	}
}