/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lpgo.pepenet.server;

public class ClientMessageListing
{
	// region Variables
	private int id;
	private String msg;
	// endregion
	
	public ClientMessageListing ( int id, String msg )
	{
		this.id = id;
		this.msg = msg;
	}
	
	// region GettersNSetters
	public int    getId  (            ) { return id;      }
	public void   setId  ( int id     ) { this.id = id;   }
	public String getMsg (            ) { return msg;     }
	public void   setMsg ( String msg ) { this.msg = msg; }
	// endregion
}