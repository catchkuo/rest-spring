package com.redhat.demo.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;


@RestController
@RequestMapping("/fn")
public class FunctionController {
	
//	static RestTemplate restTemplate = new RestTemplate();
	
	
	private RestTemplate restTemplate = new RestTemplate();

	
	 public FunctionController(MeterRegistry registry) {
		 
		 Supplier supplierNum = () -> { return 40; };
		    Gauge.builder("usercontroller.usercount",supplierNum).
		      tag("version","v1").
		      description("usercontroller descrip").
		      register(registry);
		  }


	
//	http://localhost:8080/fn/fib?lv=44
//	curl -d "lv=2" -X POST http://localhost:8080/fn/fib   @RequestHeader Map<String, String> headers
	@RequestMapping(value = "/fib", method = RequestMethod.GET,params = {"lv"})
	@Timed(value="user.get.time",description="time to retrieve users",percentiles={0.5,0.9})
	public String runFibLv(@RequestParam(value = "lv") int lv) throws UnknownHostException {

		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date date1 = new Date();
		
		
        
	
		System.out.println("GET--------------------------------------------------");
		
//		headers.forEach((key, value) -> {
//			 System.out.println(String.format("Header '%s' = %s", key, value));
//			 
//		
//		    });
		 
		System.out.println("Start time:"+format.format(date1));
//		System.out.println( "My jost Name is :" + InetAddress.getLocalHost().getHostName());
		System.out.println( "Lv:" +lv );
		
		System.out.println("--------------------------------------------------");
		

		fib(lv);
		
		Date date2 = new Date();
		System.out.println("End time:"+format.format(date2));
		Long difference = date2.getTime() - date1.getTime(); 
		
		return  difference.toString() + "ms";
	}
	
	@RequestMapping(value = "/fib", method = RequestMethod.POST,params = {"lv"})
	public String runPostFibLv(@RequestParam(value = "lv") int lv,@RequestHeader Map<String, String> headers) throws UnknownHostException {

		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date date1 = new Date();
		
		
		System.out.println("POST--------------------------------------------------");
		
		headers.forEach((key, value) -> {
			 System.out.println(String.format("Header '%s' = %s", key, value));
		    });
		 
		System.out.println("Start time:"+format.format(date1));
		System.out.println( "My jost Name is :" + InetAddress.getLocalHost().getHostName());
		System.out.println( "Lv:" +lv );
		
		System.out.println("--------------------------------------------------");
		
		
		String fooResourceUrl = "http://rest-backend:8080/fn/fib?lv=1";
		
		HttpHeaders reqheaders = new HttpHeaders();
		reqheaders.set("end-user", "vip");
		HttpEntity<String> entity = new HttpEntity<String>("parameters", reqheaders);
		
//		ResponseEntity<String> response =restTemplate.exchange(fooResourceUrl, HttpMethod.GET,entity,String.class);
		

        ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
  
    
		
		
		

		Date date2 = new Date();
		System.out.println("End time:"+format.format(date2));
		Long difference = date2.getTime() - date1.getTime(); 
		
		
		return  difference.toString() + "ms";
	}
	
	
	
	@RequestMapping(value = "/route", method = RequestMethod.GET,params = {"lv"})
	public String route(@RequestParam(value = "lv") int lv,@RequestHeader Map<String, String> headers) throws UnknownHostException {


		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date date1 = new Date();
		
		
		System.out.println("POST--------------------------------------------------");
		
		headers.forEach((key, value) -> {
			 System.out.println(String.format("Header '%s' = %s", key, value));
		    });
		 
		System.out.println("Start time:"+format.format(date1));
		System.out.println( "My jost Name is :" + InetAddress.getLocalHost().getHostName());
		System.out.println( "Lv:" +lv );
		
		System.out.println("--------------------------------------------------");
		
		 
		String fooResourceUrl = "http://localhost:8034/user/all";
		
		
	 
		HttpHeaders reqheaders = new HttpHeaders();
		reqheaders.set("end-user", "vip");
		HttpEntity<String> entity = new HttpEntity<String>("parameters", reqheaders);
		
		
		ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
		

		
		

	
		
		return diffTime(format,date1);
//		return  difference.toString() + "ms";
	}
	

	public String  diffTime(SimpleDateFormat format,Date date1) {
		
		Date date2 = new Date();
		System.out.println("End time:"+format.format(date2));
		Long difference = date2.getTime() - date1.getTime();
		
		return difference.toString() + "ms";
		
		
	}

	   



	
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String getError() throws SQLException {
	
		throw new SQLException("my fault");
	}


	public static long fib(int n) {
		if (n == 1)
			return 1;
		else if (n == 2)
			return 1;
		else
			return fib(n - 1) + fib(n - 2);
	}

}
