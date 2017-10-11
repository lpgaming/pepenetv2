/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lpgo.pepenet.client;

public interface ComClient
{
	public void connectClient ( String ip );
	public boolean sendMsg ( String msg );
	public boolean isConnected (  );
	public int getId (  );
	public void close (  );
}