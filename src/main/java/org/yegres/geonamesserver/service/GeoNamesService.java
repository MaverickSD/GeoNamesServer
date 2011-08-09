package org.yegres.geonamesserver.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yegres.geonamesserver.controller.HomeController;

public class GeoNamesService implements ExternalService{

	private static final Logger logger = LoggerFactory.getLogger(GeoNamesService.class);
	private Map<String, String> namesCache = new HashMap<String, String>();
	
	private String baseURLString = null;
		

	public String getBaseURLString() {
		return baseURLString;
	}

	public void setBaseURLString(String baseURLString) {
		this.baseURLString = baseURLString;
	}

	public String getJSONData( String serviceName,boolean fromXML, String... params ) throws IOException {
		if( !fromXML ){
			String fullUrl = baseURLString+serviceName+"?"+array2ParamString( params );
			URL url = new URL( fullUrl );
			return IOUtils.toString( url.openStream() ); 
		}
		String xml = getXMLData( serviceName, false, params );
		XMLSerializer xmlSerializer = new XMLSerializer(); 
        JSON json = xmlSerializer.read( xml );  
        return json.toString(2);		
	}

	public String getXMLData( String serviceName, boolean fromJSON, String... params ) throws IOException {
		String fullUrl = baseURLString+serviceName+"?"+array2ParamString( params );
		if( !namesCache.containsKey( fullUrl ) ){
			URL url = new URL( fullUrl );
			String result = IOUtils.toString( url.openStream() );
			namesCache.put(fullUrl, result );
		}
		return namesCache.get( fullUrl );
	}
	
	private String array2ParamString( String... arrStr ){
		StringBuilder sb = new StringBuilder();
		for( String str : arrStr ){
			if( sb.length() > 0 )
				sb.append( "&" );
			sb.append( str );
		}
		System.out.println( sb.toString() );
		return sb.toString();	
	}

}
