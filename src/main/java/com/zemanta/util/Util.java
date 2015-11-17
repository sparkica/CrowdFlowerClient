package com.zemanta.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class Util {

	//encode values only, parameter names may include special chars like -> [ ]
	//is the order of parameters important? -> key???
		public static String buildQueryFromParameters(Map<String, String> parameters) {
			StringBuilder sb = new StringBuilder();
			
			try {
				for (Map.Entry<String, String> entry: parameters.entrySet()) {
					String key = entry.getKey();
					String value = URLEncoder.encode(entry.getValue(),"UTF-8");										
					sb.append(key).append("=").append(value).append("&");
				}	
			}
			catch(UnsupportedEncodingException  e)
			{
				e.printStackTrace();
			}

			//remove last &
			return sb.toString().substring(0, sb.length()-1);

		}
}
