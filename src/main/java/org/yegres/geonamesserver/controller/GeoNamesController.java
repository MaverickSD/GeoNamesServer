package org.yegres.geonamesserver.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yegres.geonamesserver.service.ExternalService;

@Controller
public class GeoNamesController {
	
	@Autowired
	private ExternalService externalService = null;
		
	@RequestMapping( value="/getCountryInfo", method=RequestMethod.GET )
	public @ResponseBody String getCountryInfo( @RequestParam( value="alt", required=false) String alt, 
										 @RequestParam( value="srvname", required=true) String serviceName,									 
										 @RequestParam( value="username", required=true) String uname, 
										 @RequestParam( value="country", required=true) String country){
		try{
			if( alt == null || alt.isEmpty() ){
				alt = "xml";
			}
			String[] params = new String[]{ "username="+uname, "country="+country};
			if( "xml".equals( alt.toLowerCase()) ){
				return externalService.getXMLData( serviceName, false, params );
			}else if( "json".equals( alt.toLowerCase()) ){
				return externalService.getJSONData( serviceName,true, params );
			}
		}catch( Exception ioe ){
			ioe.printStackTrace();
		}
		return "There is error during requst performing! alt is "+alt;
	}
	
	@RequestMapping( value="/getWeatherInfo", method=RequestMethod.GET )
	public @ResponseBody String getWeatherInfo( @RequestParam( value="srvname", required=true) String serviceName,									 
										 @RequestParam( value="username", required=true) String uname, 
										 @RequestParam( value="north", required=true) String north,
										 @RequestParam( value="south", required=true) String south,
										 @RequestParam( value="east", required=true) String east,
										 @RequestParam( value="west", required=true) String west ){
		try{
			String[] params = new String[]{ "username="+uname, "north="+north,"south="+south,"east="+east,"west="+west};
			return externalService.getJSONData( serviceName,false, params );			
		}catch( Exception ioe ){
			ioe.printStackTrace();
		}
		return "There is error during requst performing!";
	}

}
