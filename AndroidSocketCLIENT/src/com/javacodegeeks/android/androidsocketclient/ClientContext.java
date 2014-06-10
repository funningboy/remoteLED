package com.javacodegeeks.android.androidsocketclient;

// context table
public class ClientContext {
	private LED1 led1;
	private LED2 led2;
	
	public class LED {
		private int power;
		private int gpio;
		
		public LED() {
			power = 0;
			gpio  = 0;
		}
		
		public void setPower(int it) { power = it; }
		public void setGpio(int it)  { gpio  = it; }
		public int getPower() { return power; }
		public int getGpio()  { return gpio;  }
	}
	
	public class LED1 extends LED {
		public LED1() {
			super.power = 0;  // on/off 1/0
			super.gpio  = 16; // pi gpio pin 16
		}
	}
	
	public class LED2 extends LED {
		public LED2() {
			super.power = 0;  // on/off 1/0
			super.gpio  = 18; // pi gpio pin 18
		}
	}

	public void setLed1(LED1 it) { led1 = it; }
	public void setLed2(LED2 it) { led2 = it; }
	public LED1 getLed1() { return led1; }
	public LED2 getLed2() { return led2; }
}