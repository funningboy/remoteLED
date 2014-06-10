package com.javacodegeeks.android.androidsocketclient;

import com.javacodegeeks.android.androidsocketclient.ClientContext;
import com.javacodegeeks.android.androidsocketclient.JsonUtil;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Client extends Activity {

	static EditText et_addr, et_port, et_status;
	static Button bt_send, bt_led1, bt_led2;
	static String addr;
	static int    port; 
	static ClientContext table;
	static String str;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // build up default context table
        final ClientContext table = new ClientContext();
        ClientContext.LED1 led1 = table.new LED1();
        ClientContext.LED2 led2 = table.new LED2();
        table.setLed1(led1); 
        table.setLed2(led2);
        
        // trigger thread event when the send button clicked
        // addr text
        et_addr = (EditText) findViewById(R.id.EditTextAddr); 
        
        // port text
        et_port = (EditText) findViewById(R.id.EditTextPort);
        
        // led1 button update led1 status 
        bt_led1 = (Button) findViewById(R.id.ButtonLED1);
        bt_led1.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View arg0) {
        		try {
        			table.getLed1().setPower((table.getLed1().getPower() == 1)? 0:1);
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
        }});
        
        // led2 button update led2 status
        bt_led2 = (Button) findViewById(R.id.ButtonLED2);
        bt_led2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	try {
            		table.getLed2().setPower((table.getLed2().getPower() == 1)? 0:1);
            	} catch (Exception e) {
            		e.printStackTrace();
            	}
        }});
        
        // status text display
        et_status = (EditText) findViewById(R.id.EditTextStatus);
        
        // send button
        bt_send = (Button) findViewById(R.id.ButtonSend);
        bt_send.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View arg0) {
        		try {
        			// construct add, port and status update
      	    	  	addr = et_addr.getText().toString();
      	    	  	port = Integer.parseInt(et_port.getText().toString());
      	    	  	str  = JsonUtil.toJSon(table);
      	    	  	et_status.setText(str);
      	            // spawn as thread	  	
            		Thread t = new Thread(new ClientThread());
            		t.start();
        			t.sleep(500);
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	}});    
    }
    
    // thread run
    public class ClientThread implements Runnable {
    	@Override
	     public void run() {
	      try{
	    	  Socket socket =new Socket(addr, port);
	    	  PrintWriter out = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(socket.getOutputStream())),
						true);	
	    	  out.println(str);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    }
 
}
