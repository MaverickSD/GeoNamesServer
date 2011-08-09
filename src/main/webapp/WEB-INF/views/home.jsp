<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.6.2.js"></script>
	<script type="text/javascript">
		$(document).ready( function(){
			$("#countrySelector").change( function(){
				getCountryInfo();
			});
			$("#altSelector").change( function(){
				getCountryInfo();
			}).change();						
		});
		
		var getCountryInfo = function(){
			var selCountry = $("#countrySelector :selected").val();
			var selAlt = $("#altSelector :selected").val();			
			if( selCountry && selAlt ){
				$.ajax({
					url:"getCountryInfo",
					type:"GET",
					data:"alt="+selAlt+"&srvname=countryInfo&username=demo&country="+selCountry,
					success: function( data ){
						$("#countryInfo").html( data );
						getWeatherInfo( data );
					},
					error:function( data ){
						$("#countryInfo").html( "error" );
					}
				});	
			}								
		};
		var getWeatherInfo = function( countryInfo ){
			
			var jsonInfo = $.parseJSON( countryInfo )[0];
			$.ajax({
				url:"getWeatherInfo",
				type:"GET",
				data:"&srvname=weatherJSON&username=demo"+
						"&north="+jsonInfo.bBoxNorth+"&south="+jsonInfo.bBoxSouth+
						"&east="+jsonInfo.bBoxEast+"&west="+jsonInfo.bBoxWest,
				success: function( data ){
					var jsonWeather = $.parseJSON( data ),
						wArray = jsonWeather["weatherObservations"],
						html="";
					for( var i=0, len=wArray.length; i<len; i++ ){
						html+="<br/>";
						var data = "";
						for( var v in wArray[i] )
							data +=v+":"+wArray[i][v]+"<br/>";
						html+= data;
					}
				
					$("#weatherInfo").html( html );										
				},
				error:function( data ){
					$("#weatherInfo").html( "error" );
				}
			});				
		};
	</script>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>

<select id="countrySelector">
	<option value="UA">UA</option>
	<option value="RU">RU</option>
	<option value="US">US</option>
</select>

<select id="altSelector">
	<option value="json">json</option>
	<option value="xml">xml</option>
</select>


<h2>Country info</h2>
<div id="countryInfo">

</div>

<h2>Weather info</h2>
<div id="weatherInfo">

</div>

</body>
</html>
