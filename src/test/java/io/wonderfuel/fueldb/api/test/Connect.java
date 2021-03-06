package io.wonderfuel.fueldb.api.test;

import io.wonderfuel.fueldb.api.FuelDB;
import io.wonderfuel.fueldb.api.core.FuelDBHandler;
import io.wonderfuel.fueldb.api.endpoint.ClientEndpointEnum;
import io.wonderfuel.fueldb.api.listener.DataListener;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * @author Joris Basiglio
 *
 */
public class Connect {

	public static void main(String[] args) {
		FuelDB fueldb = FuelDBHandler.get(ClientEndpointEnum.WEBSOCKET, "wonderfuel.io", 8102, true, "admin", "admin");
		try {
			fueldb.connect();
			
			fueldb.subscribe("fueldb.cpu.load", new DataListener() {
				@Override
				public void handle(JSONObject data) {
					System.out.println("Subs: " + data.get("value").toString());

				}
			});
			
			fueldb.read("fueldb.iops", new DataListener() {
				@Override
				public void handle(JSONObject data) {
					System.out.println("Read value: "+data.get("value"));
				}
			});
			
			fueldb.browse("", new DataListener() {
				@Override
				public void handle(JSONObject data) {
					System.out.println("Values: "+data.get("value")+" Size: "+((JSONArray)data.get("value")).size());
				}
			});
			JSONObject obj = fueldb.readSync("your.data.point");
			System.out.println("Read Sync: " + obj.toJSONString());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
