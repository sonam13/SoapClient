package com.tcs.soapClient.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.messaging.URLEndpoint;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import org.springframework.stereotype.Service;

import com.tcs.soapClient.Request.Request;


@Service
public class SoapClientServiceImpl implements SoapClientService
{
	public String clientResponse(Request request) 
	{
		String responseXML = null;
		String serviceOperationName= request.getServiceOperationName();
		String serviceTargetNameSpace= request.getServiceTargetNameSpace();
		String ADD_SOAP_ACTION=trimLast(serviceTargetNameSpace)+"/"+serviceOperationName;
		String serviceUrl =request.getServiceUrl();
		
		
		String requestXML = getSoapRequestXML(request.getServiceInputParameters(),serviceOperationName,serviceTargetNameSpace);
		System.out.println(requestXML);
		responseXML = /*invokeService(requestXML, ADD_SOAP_ACTION, serviceUrl);*/"hiiiiiiii";
		return null;	
}


public String getSoapRequestXML(String requestBody,String OperationName,String TargetNameSpace) {
	StringBuilder req = new StringBuilder();
	req.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	req.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
	req.append(" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"");
	req.append(" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
	req.append("<soap:Body>");
	req.append(requestBody.replace("<"+OperationName+">", "<"+OperationName+" xmlns=\""+TargetNameSpace+"\">"));
	req.append("</soap:Body>"); 
	req.append("</soap:Envelope>");
	return req.toString();

}
public String invokeService(String soapRequest, String soapAction, String endPoint) {
	SOAPConnectionFactory myFct = null;
	SOAPConnection myCon = null;
	SOAPMessage message = null;
	try {
		myFct = SOAPConnectionFactory.newInstance();
		myCon = myFct.createConnection();
		MessageFactory myMsgFct = MessageFactory.newInstance();
		MimeHeaders headers = new MimeHeaders();
		ByteArrayInputStream is = new ByteArrayInputStream(soapRequest.getBytes());
		
		//headers.addHeader("SOAPAction", soapAction);
		message = myMsgFct.createMessage(headers, is);
		MimeHeaders headers1 = message.getMimeHeaders();
		
		headers1.addHeader("SOAPAction", soapAction);
		headers1.addHeader("Accept", "application/soap+xml, text/xml ");

		ByteArrayOutputStream requestOS = new ByteArrayOutputStream();
		message.saveChanges();
		message.writeTo(requestOS);
		
	} catch (UnsupportedOperationException e) {
		e.printStackTrace();
		
	} catch (SOAPException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	//commented for proxy
	URLEndpoint endPt = new URLEndpoint(endPoint);
	SOAPMessage reply = null;
	ByteArrayOutputStream responseOS = new ByteArrayOutputStream();
	
	try {
		reply = myCon.call(message, endPt);
		reply.writeTo(responseOS);

	} catch (SOAPException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		try {
			myCon.close();
		} catch (SOAPException e) {
		}
	}
	String soapResponse = new String(responseOS.toByteArray());
	return soapResponse;
}
public static String trimLast(String str) {
    if (str != null && str.length() > 0 && str.charAt(str.length()-1)=='/') {
      str = str.substring(0, str.length()-1);
    }
    return str;
}
		
}
