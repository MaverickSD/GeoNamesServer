package org.yegres.geonamesserver.service;

public interface ExternalService {
	String getJSONData( String serviceName, boolean fromXML, String... params ) throws Exception;
	String getXMLData( String serviceName, boolean fromJSON, String... params ) throws Exception;
}
