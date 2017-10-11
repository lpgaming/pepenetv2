/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lpgo.pepenet.cme;

import java.util.EventObject;

public class PepenetMessageEvent extends EventObject
{
	// region Variables
	private static final long serialVersionUID = -6260838118807316376L;
	private int clientID;
	private String message;
	// endregion

	public PepenetMessageEvent ( MessageEventSource eventSource, int clientID, String message )
	{
		super ( eventSource );
		this.clientID = clientID;
		this.message = message;
	}

	public PepenetMessageEvent ( MessageEventSource eventSource, String message )
	{
		super ( eventSource );
		this.clientID = 0;
		this.message = message;
	}

	// region GettersNSetters
	public static long getSerialversionuid (  ) { return serialVersionUID; }
	public int         getClientID         (  ) { return clientID;         }
	public String      getMessage          (  ) { return message;          }
	// endregion
}