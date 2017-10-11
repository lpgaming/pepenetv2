/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lpgo.pepenet.client;

public class ClientMSG
{
	// region Variables
	ComClient comclient;
	JAVAClient client;      	//For Bidirectional Communication mode
	// endregion
	
	public ClientMSG ( String ip, int port, JAVAClient client, JAVAClient.platformCode pc )
	{
		if ( pc == JAVAClient.platformCode.HTML5 )
		{
			//Only available on the HTML project - ClientMSG class
			comclient = new GWTClient(ip, port, this);
		}
		else
		{
			//Only available on the JAVA-ANDROID project
			comclient = new WSClient ( ip, port, this, pc );
		}
		this.client = client; //To call the methods of the the upper level class
	}


	public void onMessage ( String message )
	{
		client.getMessage ( message );
	}

	public boolean sendMessage ( String message )
	{		
		if ( comclient != null && comclient.isConnected (  ) )
		{
			return ( comclient.sendMsg ( message ) );
		}	
		else
		{
			return false;
		}
	}
	
	public int getId (  )
	{
		return ( comclient.getId (  ) );
	}
	
	public void close (  )
	{
		comclient.close (  );
	}
}