package com.tcs.soapClient.Controller;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tcs.soapClient.Request.Request;
import com.tcs.soapClient.Service.SoapClientService;

@Controller
public class SoapClientController {
	
	@Autowired 
	SoapClientService soapClientService;
	
	@RequestMapping(value = "/soapClient", method = RequestMethod.POST)
	public @ResponseBody String getClients(@RequestBody Request request){
		
		return soapClientService.clientResponse(request);
		
	}
}
