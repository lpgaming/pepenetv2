/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lpgo.pepenet.cme;

import java.util.EventListener;

public interface PepenetMessageEventListener extends EventListener
{
	public void handleEvent ( PepenetMessageEvent event ); // Called when a new message is received
}
