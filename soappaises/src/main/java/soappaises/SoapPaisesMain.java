package soappaises;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;

public class SoapPaisesMain {
	
	public static final String ENDPOINT = "http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		mostrarInfo("AR");
		mostrarInfo("ES");
		mostrarInfo("CU");
		mostrarInfo("BR");
		
	}
	
	
	public static void mostrarInfo(String pais) {
		
		System.out.println("COUNTRY NAME DEL PAIS " + pais +  " :" + getInfoPais(pais, "CountryName"));
		System.out.println("COUNTRY CURRENCY DEL PAIS " + pais +  " :" + getInfoPais(pais, "CountryCurrency"));
		System.out.println("COUNTRY FULL INFO DEL PAIS " + pais +  " :" + getInfoPais(pais, "FullCountryInfo"));
		System.out.println("LANGUAGE NAME DEL PAIS " + pais +  " :" + getInfoPais(pais, "LanguageName"));
		
	}
	
	
	
	
	
	
	public static String getInfoPais(String pais, String info) {
		
		
		
		
		String result = "";
		try {
			//conexion
			SOAPConnection soapConnection = SOAPConnectionFactory.newInstance().createConnection();
			
			
			//Creamos el mensaje SOAP
			MessageFactory messageFactory = MessageFactory.newInstance();
			SOAPMessage soapMessage = messageFactory.createMessage();
			
			//Crear el cuerpo del mensaje SOAP
			SOAPBody soapBody = soapMessage.getSOAPPart().getEnvelope().getBody();
			
			/* MENSAJES SEGÚN DOCUMENTACIÓN
			 * 
			  
				 *   <CountryName xmlns="http://www.oorsprong.org/websamples.countryinfo">
				         <sCountryISOCode>string</sCountryISOCode>
				     </CountryName>
				     
				 *   <CountryCurrency xmlns="http://www.oorsprong.org/websamples.countryinfo">
				         <sCountryISOCode>string</sCountryISOCode>
				     </CountryCurrency>
				     
				 *   <FullCountryInfo xmlns="http://www.oorsprong.org/websamples.countryinfo">
				         <sCountryISOCode>string</sCountryISOCode>
				     </FullCountryInfo>
				     
			   	 *	 <LanguageName xmlns="http://www.oorsprong.org/websamples.countryinfo">
				         <sISOCode>string</sISOCode>
				     </LanguageName>
				    
			 * 
			 * */
			
			//Construimos el mensaje a enviar
			if(info.equals("LanguageName")) {
				
				soapBody.addChildElement(info, "", "http://www.oorsprong.org/websamples.countryinfo")
				.addChildElement("sISOCode")
				.addTextNode(pais);
				
			} else {
				
				soapBody.addChildElement(info, "", "http://www.oorsprong.org/websamples.countryinfo")
				.addChildElement("sCountryISOCode")
				.addTextNode(pais);
			}
			
			
			
			//Enviar el mensaje SOAP y recibir la respuesta
			SOAPMessage soapResponse = soapConnection.call(soapMessage, ENDPOINT);
			
			
			//Procesamos la respuesta.
			SOAPBody responseBody = soapResponse.getSOAPBody();
			result = responseBody.getTextContent();
			soapConnection.close();
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	

}
