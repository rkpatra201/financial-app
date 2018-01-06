package com.spring.boot.config;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

public class FinancialJSONMapper {

	public static List<FinancialModel> toObject(ResponseEntity<String> response) throws JSONException
	{
		JSONObject jsonObject = new JSONObject(response.getBody());
		JSONArray jsonArray=jsonObject.getJSONArray("results");
		
		List<FinancialModel> list=new ArrayList<>();
		for(int i=0;i<jsonArray.length();i++)
		{
			String storeCode=jsonArray.getJSONObject(i).getJSONObject("fields").get("Store Code").toString();
			String faceBookWebAddress=jsonArray.getJSONObject(i).getJSONObject("fields").get("Facebook Web Address").toString();
			String googleId=jsonArray.getJSONObject(i).getJSONObject("apiIds").get("google").toString();
			
			FinancialModel model=new FinancialModel(storeCode,faceBookWebAddress,googleId);
			list.add(model);
		}
		
		return list;
	}
}
