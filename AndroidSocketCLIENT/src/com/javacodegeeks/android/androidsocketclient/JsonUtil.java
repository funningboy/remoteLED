package com.javacodegeeks.android.androidsocketclient;


import org.json.JSONObject;
import org.json.JSONException;
import com.javacodegeeks.android.androidsocketclient.ClientContext;

// pack context table as json stream
public class JsonUtil {
	public static String toJSon(ClientContext context) {
		try {
			JSONObject jsonLED1 = new JSONObject();
			JSONObject jsonLED2 = new JSONObject();
			jsonLED1.put("power", context.getLed1().getPower());
			jsonLED1.put("gpio",  context.getLed1().getGpio());
			jsonLED2.put("power", context.getLed2().getPower());
			jsonLED2.put("gpio",  context.getLed2().getGpio());
			JSONObject jsonTable = new JSONObject();
			jsonTable.put("LED1", jsonLED1);
			jsonTable.put("LED2", jsonLED2);
			return jsonTable.toString();
		}
		catch(JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}