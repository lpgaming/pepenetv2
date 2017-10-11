/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lpgo.pepenet.cme;

import java.util.ArrayList;
import java.util.List;

public class MessageEventSource
{
	// region Variables
	private List<PepenetMessageEventListener> listeners = new ArrayList<PepenetMessageEventListener> (  );
	// endregion

	public synchronized void addListener ( PepenetMessageEventListener listener )
	{
		listeners.add ( listener );
	}
	
	public synchronized void removeListener ( PepenetMessageEventListener listener )
	{
		listeners.remove ( listener );
	}
	
	public void fireMessageReceived ( int clientID, String message ) // This version is for the server
	{
		if ( listeners != null && !listeners.isEmpty (  ) )
		{
			PepenetMessageEvent messageEvent = new PepenetMessageEvent ( this, clientID, message );
			
			synchronized ( listeners )
			{
				for ( PepenetMessageEventListener pmel : listeners )
				{
					pmel.handleEvent ( messageEvent );
				}
			}
		}
	}

	public void fireMessageReceived ( String message ) // This version is for the client
	{
		if ( listeners != null && !listeners.isEmpty (  ) )
		{
			PepenetMessageEvent messageEvent = new PepenetMessageEvent ( this, message );
			
			synchronized ( listeners )
			{
				for ( PepenetMessageEventListener pmel : listeners )
				{
					pmel.handleEvent ( messageEvent );
				}
			}
		}
	}

	// region GettersNSetters
	public List<PepenetMessageEventListener> getListeners (  ) { return listeners; }

	// endregion
}