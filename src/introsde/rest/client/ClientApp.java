package introsde.rest.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.glassfish.jersey.client.ClientConfig;
import org.json.JSONArray;
import org.json.JSONObject;


public class ClientApp {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		Scanner input = new Scanner(System.in);
		String name = null;
		int operation = -1;
		
		System.out.println("Welcome to LIFECOACH APP!");
		System.out.println(" ");
		System.out.println("This application will help you to control your everyday physical activity, sleep and water consumption. ");
		System.out.println(" \n -- Compare the amount of steps you took");
		System.out.println(" -- Note the hours of sleep you took  ");
		System.out.println(" -- Note the amount of water you consumed ");
		System.out.println("\nIt is recommended to make at least 3000 steps every day, sleep 8 hours per day and drink at least 3 litres of water.");
		System.out.println("\nYou can however change the goals.");
		 System.out.println("\nContinue(Y/N)");
		 Scanner s= new Scanner(System.in);
		 char c = s.next().charAt(0);
	       
	        if (c=='Y'|| c=='y') {
		 
		System.out.println("\nPlease choose the operation to proceed\n");


		while (operation != 0) {
			System.out.println("\n MAIN MENU'\n");
			System.out.println("1 - Print your current information");
			System.out.println("2 - Key in the amount of steps you made today and check goal");
			System.out.println("3 - Key in the hours of sleep you get last night and check goal");
			System.out.println("4 - Key in the the amount of water you consume today (in litres) and check goal");
			System.out.println("5 - Set the goal - number of steps you should make, at least 3000 recommended");
			System.out.println("6 - Set the goal - hours of sleep you should get, at least 8 hours recommended");
			System.out.println("7 - Set the goal - how many litres of water you should drink, at least 3 litres recommended");
			System.out.println("8 - Print all the current goals ");
			System.out.println("0 - Exit");

			System.out.print("\nEnter operaton  number to proceed\n");
			operation = Integer.parseInt(input.nextLine());

			if (operation < 0 || operation > 6) {
				System.out.print("\nOperation not allowed, try again!\n\n");
			}

			switch (operation) {
				case 0:
					System.out.println("\nThank you for using LIFECOACH APP! Remember to stay healthy!");
					break;
				case 1:
					// String ENDPOINT = "http://10.218.204.124:5500/introsde/businessLogic/getPersonDetails";
					 String ENDPOINT = "https://sdebusinesslogichisyam.herokuapp.com/introsde/businessLogic/getPersonDetails";

					 DefaultHttpClient client = new DefaultHttpClient();
					 HttpGet request = new HttpGet(ENDPOINT);
					 HttpResponse response = client.execute(request);

					 BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

					 StringBuffer result = new StringBuffer();
					 String line = "";
					while ((line = rd.readLine()) != null) {
						result.append(line);
					}

					JSONObject o = new JSONObject(result.toString());

					if (response.getStatusLine().getStatusCode() == 200) {

						System.out.println("###############################################");
						System.out.println("YOUR INFORMATION");
						System.out.println("###############################################");

						System.out.println("Name: " + o.getString("name"));
						System.out.println("Surname: " + o.getString("lastname"));
						System.out.println("id: " + o.getInt("idPerson"));
						System.out.println("Birthdate: " + o.getString("birthdate"));

						 System.out.println("###############################################");
						 System.out.println("YOUR CURRENT HEALTH PROFILE");
						 System.out.println("###############################################");

						 for(int i = 0; i < o.getJSONArray("lifeStatus").length(); i++){
						 	System.out.println("Measure: "+o.getJSONArray("lifeStatus").getJSONObject(i).getString("measure"));
						 	System.out.println("Value: "+o.getJSONArray("lifeStatus").getJSONObject(i).get("value"));
						 System.out.println();
					}

			}
			 break;
   				
   				case 2:
			  			int value = -1;
			  			while(value < 0){
			  				System.out.println("Health profile - steps: ");
			  				System.out.println("Insert new value: ");
			  				value = Integer.parseInt(input.nextLine());
			  				
			  				if(value < 0){
			  					System.out.println("Value not allowed! Please, try again!");
			  				}
			  			}

			 	//Edit measure STEPS in Life Status
			        //ENDPOINT = "http://10.218.204.124:5901/introsde/processCentric/updateHP";
			        ENDPOINT = "https://sdeprocesscentrichisyam.herokuapp.com/introsde/processCentric/updateHP";
			        ClientConfig clientConfig9 = new ClientConfig();
				 	   Client client9 = ClientBuilder.newClient(clientConfig9);
	                    client = new DefaultHttpClient();
	        
	  
	                   HttpGet request9 = new HttpGet(ENDPOINT);
	                   HttpResponse response9 = client.execute(request9);

				 		WebTarget service9 = client9.target(ENDPOINT);

				    	Response res9 = null;
				 		String putResp9 = null;

				    	String updateLifeStatus9 ="{" + "\"value\": " + value + "," + "\"measureName\": \"" + "steps" + "\"" + "}";

				    	res9 = service9.request(MediaType.APPLICATION_JSON_TYPE).put(Entity.json(updateLifeStatus9));
				    	putResp9 = res9.readEntity(String.class);
				    	
				    	//get quote motivation from foresmatic
				    	
				    	//String ENDPOINT3 = "http://10.218.204.124:5901/introsde/processCentric/getQuote2";
				    	String ENDPOINT3 = "https://sdeprocesscentrichisyam.herokuapp.com/introsde/processCentric/getQuote2";

				    	DefaultHttpClient client3 = new DefaultHttpClient();
				    	HttpGet request3 = new HttpGet(ENDPOINT3);
				    	HttpResponse response3 = client3.execute(request3);
				   	
				    	BufferedReader rd3 = new BufferedReader(new InputStreamReader(response3.getEntity().getContent()));

				    	StringBuffer result3 = new StringBuffer();
				    	String line3 = "";
				    	while ((line3= rd3.readLine()) != null) {
				    	    result3.append(line3);
				    	}
				   	
				    	JSONObject testing = new JSONObject(result3.toString());
				    	 String quoteText = testing.getString("quoteText");
				    	    String quoteAuthor = testing.getString("quoteAuthor");
				    	    String quoteLink = testing.getString("quoteLink");
				    	    
				    	  //get quote motivation from quotedesignapi
				    	    
				    	    //String ENDPOINT4 = "http://10.218.204.124:5901/introsde/processCentric/getQuote";
				    	    String ENDPOINT4 = "https://sdeprocesscentrichisyam.herokuapp.com/introsde/processCentric/getQuote";
				        	DefaultHttpClient newclient4 = new DefaultHttpClient();
				        	HttpGet newrequest4 = new HttpGet(ENDPOINT4);
				        	HttpResponse newresponse4 = newclient4.execute(newrequest4);
				       	
				        	BufferedReader newrd4 = new BufferedReader(new InputStreamReader(newresponse4.getEntity().getContent()));

				        	StringBuffer newresult4 = new StringBuffer();
				        	String newline4 = "";
				        	while ((newline4 = newrd4.readLine()) != null) {
				        		newresult4.append(newline4);
				        	}
				        	
				        	JSONObject newObject4 = new JSONObject(newresult4.toString());
				        	 String quote = newObject4.getString("quote");
					    	 String author = newObject4.getString("author");
					    	   String permalink= newObject4.getString("permalink");
				    	// get value from DB  and compare with new value
			    		
			    		//String ENDPOINT2 = "http://10.218.204.124:5500/introsde/businessLogic/getGoals";
			    		String ENDPOINT2 = "https://sdebusinesslogichisyam.herokuapp.com/introsde/businessLogic/getGoals";
						 DefaultHttpClient client2 = new DefaultHttpClient();
						 HttpGet request2 = new HttpGet(ENDPOINT2);
						 HttpResponse response2 = client2.execute(request2);

						 BufferedReader rd2 = new BufferedReader(new InputStreamReader(response2.getEntity().getContent()));

						 StringBuffer result2 = new StringBuffer();
						 String line2 = "";
						while ((line2 = rd2.readLine()) != null) {
							result2.append(line2);
						}
					
						JSONArray newO = new JSONArray(result2.toString());
						
						
						
						if(res9.getStatus() == 200 ){
							int idGoal =1;
				    		//check if the goal is hit
				    		 for(int i = 0; i < newO.length(); i++){
				    			if ( newO.getJSONObject(i).getInt("idGoal")== idGoal){
				    				 if (value >= newO.getJSONObject(i).getInt("value")){
				    					 System.out.println("\nCongratulation on hitting your goal!");
				    					 System.out.println("\nYour steps goal : "+newO.getJSONObject(i).getInt("value"));
				    					 System.out.println("Your current steps :"+ value);
				    					 System.out.println("\nHere is a quote for you :"); 
				    					 System.out.println("Quote : "+ quoteText);
				    					 System.out.println("Author : "+ quoteAuthor);
				    					 System.out.println("Source : "+ quoteLink);
				    				 }
				    				 else if  (value < newO.getJSONObject(i).getInt("value")   ){
				    					 System.out.println("\nUnfortunately you  haven't reach your goal.");
				    					 System.out.println("\nYour steps goal : "+newO.getJSONObject(i).getInt("value"));
				    					 System.out.println("Your current steps :"+ value);;
				    					 System.out.println("\nYou need to walk more ! This is a motivational phrase for you"); 
				    					 System.out.println("Quote : "+ quote);
				    					 System.out.println("Author : "+ author);
				    					 System.out.println("Source : "+ permalink);
				    				 }
				    				 else {
				    					 System.out.println("ERROR during updating! Please, try again!");
				    				 }
				    			 }
				    		
				    			 else {
				    				 //System.out.println("\nDoesnt even check the s==steps");
						  	    		
				    			 }
						
				    		 }
				    
				    	}else{
				  	    		System.out.println("ERROR during updating! Please, try again!");
				  	    	}
            break;
            //newly added key in sleeping hours
            
   				case 3:
		  			int value2 = -1;
		  			while(value2 < 0){
		  				System.out.println("Health profile - sleep: ");
		  				System.out.println("Insert new value: ");
		  				value2 = Integer.parseInt(input.nextLine());
		  				if(value2 < 0){
		  					System.out.println("Value not allowed! Please, try again!");
		  				}
		  			}

		 	//Edit measure sleep in Life Status
		        //ENDPOINT = "http://10.218.204.124:5901/introsde/processCentric/updateHP";
		        ENDPOINT = "https://sdeprocesscentrichisyam.herokuapp.com/introsde/processCentric/updateHP";
		       ClientConfig clientConfig1 = new ClientConfig();
		 	   Client client1 = ClientBuilder.newClient(clientConfig1);
                client = new DefaultHttpClient();
    

               HttpGet request1 = new HttpGet(ENDPOINT);
               HttpResponse response1 = client.execute(request1);

		 		WebTarget service1 = client1.target(ENDPOINT);

		    	Response res1 = null;
		 		String putResp1 = null;

		    	String updateLifeStatus1 ="{" + "\"value\": " + value2 + "," + "\"measureName\": \"" + "sleep" + "\"" + "}";

		    	res1 = service1.request(MediaType.APPLICATION_JSON_TYPE).put(Entity.json(updateLifeStatus1));
		    	putResp1 = res1.readEntity(String.class);

		    	//get quote motivation from foresmatic
		    	
		    	//String ENDPOINT5 = "http://10.218.204.124:5901/introsde/processCentric/getQuote2";
		    	String ENDPOINT5 = "https://sdeprocesscentrichisyam.herokuapp.com/introsde/processCentric/getQuote2";
		    	DefaultHttpClient newclient3 = new DefaultHttpClient();
		    	HttpGet newrequest3 = new HttpGet(ENDPOINT5);
		    	HttpResponse newresponse3 = newclient3.execute(newrequest3);
		   	
		    	BufferedReader newrd3 = new BufferedReader(new InputStreamReader(newresponse3.getEntity().getContent()));

		    	StringBuffer newresult3 = new StringBuffer();
		    	String newline3 = "";
		    	while ((newline3= newrd3.readLine()) != null) {
		    		newresult3.append(newline3);
		    	}
		   	
		    	JSONObject foresmatic = new JSONObject(newresult3.toString());
		    	 String quoteText1 = foresmatic.getString("quoteText");
		    	    String quoteAuthor1 = foresmatic.getString("quoteAuthor");
		    	    String quoteLink1 = foresmatic.getString("quoteLink");
		    	    
		    	  //get quote motivation from quotedesignapi
		    	    
		    	   // String ENDPOINT6 = "http://10.218.204.124:5901/introsde/processCentric/getQuote";
		    	    String ENDPOINT6 = "https://sdeprocesscentrichisyam.herokuapp.com/introsde/processCentric/getQuote";
		        	DefaultHttpClient newclient6 = new DefaultHttpClient();
		        	HttpGet newrequest6 = new HttpGet(ENDPOINT6);
		        	HttpResponse newresponse6 = newclient6.execute(newrequest6);
		       	
		        	BufferedReader newrd6 = new BufferedReader(new InputStreamReader(newresponse6.getEntity().getContent()));

		        	StringBuffer newresult6 = new StringBuffer();
		        	String newline6 = "";
		        	while ((newline6 = newrd6.readLine()) != null) {
		        		newresult6.append(newline6);
		        	}
		        	
		        	JSONObject newObject6 = new JSONObject(newresult6.toString());
		        	 String quote1 = newObject6.getString("quote");
			    	 String author1 = newObject6.getString("author");
			    	   String permalink1= newObject6.getString("permalink");
		    	// get value from DB  and compare with new value
	    		
	    		//String ENDPOINT7 = "http://10.218.204.124:5500/introsde/businessLogic/getGoals";
	    		String ENDPOINT7 = "https://sdebusinesslogichisyam.herokuapp.com/introsde/businessLogic/getGoals";
				 DefaultHttpClient newclient2 = new DefaultHttpClient();
				 HttpGet newrequest2 = new HttpGet(ENDPOINT7);
				 HttpResponse newresponse2 = newclient2.execute(newrequest2);

				 BufferedReader newrd2 = new BufferedReader(new InputStreamReader(newresponse2.getEntity().getContent()));

				 StringBuffer newresult2 = new StringBuffer();
				 String newline2 = "";
				while ((newline2 = newrd2.readLine()) != null) {
					newresult2.append(newline2);
				}
			
				JSONArray sleepObjectJson = new JSONArray(newresult2.toString());
				
				
				
				if(res1.getStatus() == 200 ){
					int idGoal =3;
		    		//check if the goal is hit
		    		 for(int i = 0; i < sleepObjectJson.length(); i++){
		    			if ( sleepObjectJson.getJSONObject(i).getInt("idGoal")== idGoal){
		    				 if (value2 >= sleepObjectJson.getJSONObject(i).getInt("value")){
		    					 System.out.println("\nCongratulation on hitting your goal!");
		    					 System.out.println("\nYou need to sleep for at least : "+sleepObjectJson.getJSONObject(i).getInt("value")+ " hours");
		    					 System.out.println("And you sleep for  :"+ value2 + " hours");
		    					 System.out.println("\nHere is a quote for you :"); 
		    					 System.out.println("Quote : "+ quoteText1);
		    					 System.out.println("Author : "+ quoteAuthor1);
		    					 System.out.println("Source : "+ quoteLink1);
		    				 }
		    				 else if  (value2 < sleepObjectJson.getJSONObject(i).getInt("value")   ){
		    					 System.out.println("\nUnfortunately you  didn't sleep enough.");
		    					 System.out.println("\nYou need to sleep for at least : "+sleepObjectJson.getJSONObject(i).getInt("value")+ " hours");
		    					 System.out.println("And you only sleep for  :"+ value2 + " hours");
		    					 System.out.println("\nSleep more. It's important ! This is a motivational phrase for you"); 
		    					 System.out.println("Quote : "+ quote1);
		    					 System.out.println("Author : "+ author1);
		    					 System.out.println("Source : "+ permalink1);
		    				 }
		    				 else {
		    					 System.out.println("ERROR during updating! Please, try again!");
		    				 }
		    			 }
		    		
		    			 else {
		    				 //System.out.println("\nDoesnt even check the s==steps");
				  	    		
		    			 }
				
		    		 }
		    
		    	}else{
		  	    		System.out.println("ERROR during updating! Please, try again!");
		  	    	}
        break;
      
			  		case 4:
			  			int newvalue2 = -1;
			  			while(newvalue2 < 0){
			  				System.out.println("Health profile - water: ");
			  				System.out.println("Insert new value: ");
			  				newvalue2 = Integer.parseInt(input.nextLine());
			  				if(newvalue2 < 0){
			  					System.out.println("Value not allowed! Please, try again!");
			  				}
			  			}

			 	//Edit measure water in Life Status
			       // ENDPOINT = "http://10.218.204.124:5901/introsde/processCentric/updateHP";
			  			 ENDPOINT = "https://sdeprocesscentrichisyam.herokuapp.com/introsde/processCentric/updateHP";
			       ClientConfig newclientConfig1 = new ClientConfig();
			 	   Client newclient1 = ClientBuilder.newClient(newclientConfig1);
                    client = new DefaultHttpClient();
        
  
                   HttpGet newrequest1 = new HttpGet(ENDPOINT);
                   HttpResponse newresponse1 = client.execute(newrequest1);

			 		WebTarget newservice1 = newclient1.target(ENDPOINT);

			    	Response newres1 = null;
			 		String newputResp1 = null;

			    	String newupdateLifeStatus1 ="{" + "\"value\": " + newvalue2 + "," + "\"measureName\": \"" + "water" + "\"" + "}";

			    	newres1 = newservice1.request(MediaType.APPLICATION_JSON_TYPE).put(Entity.json(newupdateLifeStatus1));
			    	newputResp1 = newres1.readEntity(String.class);

			    	//get quote motivation from foresmatic
			    	
			    	//String ENDPOINT8 = "http://10.218.204.124:5901/introsde/processCentric/getQuote2";
			    	String ENDPOINT8 = "https://sdeprocesscentrichisyam.herokuapp.com/introsde/processCentric/getQuote2";
			    	DefaultHttpClient newclient8= new DefaultHttpClient();
			    	HttpGet newrequest8 = new HttpGet(ENDPOINT8);
			    	HttpResponse newresponse8= newclient8.execute(newrequest8);
			   	
			    	BufferedReader newrd8 = new BufferedReader(new InputStreamReader(newresponse8.getEntity().getContent()));

			    	StringBuffer newresult8 = new StringBuffer();
			    	String newline8 = "";
			    	while ((newline8= newrd8.readLine()) != null) {
			    		newresult8.append(newline8);
			    	}
			   	
			    	JSONObject foresmatic2 = new JSONObject(newresult8.toString());
			    	 String quoteText2 = foresmatic2.getString("quoteText");
			    	    String quoteAuthor2 = foresmatic2.getString("quoteAuthor");
			    	    String quoteLink2 = foresmatic2.getString("quoteLink");
			    	    
			    	  //get quote motivation from quotedesignapi
			    	    
			    	    //String ENDPOINT9 = "http://10.218.204.124:5901/introsde/processCentric/getQuote";
			    	    String ENDPOINT9 = "https://sdeprocesscentrichisyam.herokuapp.com/introsde/processCentric/getQuote";
			        	DefaultHttpClient newclient9 = new DefaultHttpClient();
			        	HttpGet newrequest9 = new HttpGet(ENDPOINT9);
			        	HttpResponse newresponse9 = newclient9.execute(newrequest9);
			       	
			        	BufferedReader newrd9 = new BufferedReader(new InputStreamReader(newresponse9.getEntity().getContent()));

			        	StringBuffer newresult9 = new StringBuffer();
			        	String newline9 = "";
			        	while ((newline9 = newrd9.readLine()) != null) {
			        		newresult9.append(newline9);
			        	}
			        	
			        	JSONObject newObject9 = new JSONObject(newresult9.toString());
			        	 String quote2 = newObject9.getString("quote");
				    	 String author2 = newObject9.getString("author");
				    	   String permalink2= newObject9.getString("permalink");
			    	// get value from DB  and compare with new value
		    		
		    		//String ENDPOINT10 = "http://10.218.204.124:5500/introsde/businessLogic/getGoals";
		    		String ENDPOINT10 = "https://sdebusinesslogichisyam.herokuapp.com/introsde/businessLogic/getGoals";

					 DefaultHttpClient newclient10 = new DefaultHttpClient();
					 HttpGet newrequest10 = new HttpGet(ENDPOINT10);
					 HttpResponse newresponse10 = newclient10.execute(newrequest10);

					 BufferedReader newrd10 = new BufferedReader(new InputStreamReader(newresponse10.getEntity().getContent()));

					 StringBuffer newresult10 = new StringBuffer();
					 String newline10 = "";
					while ((newline10 = newrd10.readLine()) != null) {
						newresult10.append(newline10);
					}
				
					JSONArray waterObjectJson = new JSONArray(newresult10.toString());
					
					
					
					if(newres1.getStatus() == 200 ){
						int idGoal =2;
			    		//check if the goal is hit
			    		 for(int i = 0; i < waterObjectJson.length(); i++){
			    			if ( waterObjectJson.getJSONObject(i).getInt("idGoal")== idGoal){
			    				 if (newvalue2 >= waterObjectJson.getJSONObject(i).getInt("value")){
			    					 System.out.println("\nCongratulation on hitting your goal!");
			    					 System.out.println("\nYou need to drink at least : "+waterObjectJson.getJSONObject(i).getInt("value")+ " litre of water");
			    					 System.out.println("And you consume  :"+ newvalue2 + " litre of water");
			    					 System.out.println("\nHere is a quote for you :"); 
			    					 System.out.println("Quote : "+ quoteText2);
			    					 System.out.println("Author : "+ quoteAuthor2);
			    					 System.out.println("Source : "+ quoteLink2);
			    				 }
			    				 else if  (newvalue2 < waterObjectJson.getJSONObject(i).getInt("value")   ){
			    					 System.out.println("\nUnfortunately you did not hydate yourself enough.");
			    					 System.out.println("\nYou need to drink at least : "+waterObjectJson.getJSONObject(i).getInt("value")+ " litre of water");
			    					 System.out.println("And you consume  :"+ newvalue2 + " litre of water");
			    					 System.out.println("\nDrink more. Remember to stay hydrate ! This is a motivational phrase for you"); 
			    					 System.out.println("Quote : "+ quote2);
			    					 System.out.println("Author : "+ author2);
			    					 System.out.println("Source : "+ permalink2);
			    				 }
			    				 else {
			    					 System.out.println("ERROR during updating! Please, try again!");
			    				 }
			    			 }
			    		
			    			 else {
			    				 //System.out.println("\nDoesnt even check the s==steps");
					  	    		
			    			 }
					
			    		 }
			    
			    	}else{
			  	    		System.out.println("ERROR during updating! Please, try again!");
			  	    	}

            break;

			  		case 5:
			  			int value3 = -1;
			  			while(value3 < 0 || value3 > 1000000){
			  				System.out.println("Goal - steps: ");
			  				System.out.println("Insert new value: ");
			  				value3 = Integer.parseInt(input.nextLine());
			  				if(value3 < 0 || value3 > 1000000){
			  					System.out.println("Value not allowed! Please, try again!");
			  				}
			  			}

			 	//Change the goal STEPS
			    	//ENDPOINT = "http://10.218.204.124:5901/introsde/processCentric/updateGoal";
			    	ENDPOINT = "https://sdeprocesscentrichisyam.herokuapp.com/introsde/processCentric/updateGoal";
			    	ClientConfig clientConfig4 = new ClientConfig();
			 	Client client4 = ClientBuilder.newClient(clientConfig4);

			 	WebTarget service4 = client4.target(ENDPOINT);

			    	Response res4 = null;
			 	String putResp4 = null;

			    	String updateGoal ="{" + "\"value\": " + value3 + "," + "\"type\": \"" + "steps" + "\"" + ","
                            + "\"idGoal\": \"" + "1" + "\""
                            + "}";


			    	res4 = service4.request(MediaType.APPLICATION_JSON_TYPE).put(Entity.json(updateGoal));
			    	putResp4 = res4.readEntity(String.class);

			    	if(res4.getStatus() != 200 ){
			    		System.out.println("ERROR while updating! Please, try again!");
			    	}else{
			    		System.out.println("Goal has been updated!");
			    	}

			 break;
			 //newly added set goal hours of sleep

			  		case 6:
			  			int value4 = -1;
			  			while(value4 < 0){
			  				System.out.println("Goal - sleep: ");
			  				System.out.println("Insert new value: ");
			  				value4 = Integer.parseInt(input.nextLine());
			  				if(value4 < 0 || value4 > 10000){
			  					System.out.println("Value not allowed! Please, try again!");
			  				}
			  			}

			 	//Change the goal SLEEP
			    	//ENDPOINT = "http://10.218.204.124:5901/introsde/processCentric/updateGoal";
			    	ENDPOINT = "https://sdeprocesscentrichisyam.herokuapp.com/introsde/processCentric/updateGoal";
			    	ClientConfig clientConfig5 = new ClientConfig();
			 	Client client5 = ClientBuilder.newClient(clientConfig5);

			 	WebTarget service5 = client5.target(ENDPOINT);

			    	Response res5 = null;
			 	String putResp5 = null;

			    	String updateGoal5 ="{" + "\"value\": " + value4 + "," + "\"type\": \"" + "sleep" + "\"" + ","
                            + "\"idGoal\": \"" + "3" + "\""
                            + "}";

			    	res5 = service5.request(MediaType.APPLICATION_JSON_TYPE).put(Entity.json(updateGoal5));
			    	putResp5 = res5.readEntity(String.class);

			    	if(res5.getStatus() != 200){
			    		System.out.println("ERROR while updating! Please, try again!");
			    	}else{
			    		System.out.println("Goal has been updated!");
			    	}
			    	
			   	 break;	
			  		case 7:
			  			int newvalue4 = -1;
			  			while(newvalue4 < 0){
			  				System.out.println("Goal - water: ");
			  				System.out.println("Insert new value: ");
			  				newvalue4 = Integer.parseInt(input.nextLine());
			  				if(newvalue4 < 0 || newvalue4 > 10000){
			  					System.out.println("Value not allowed! Please, try again!");
			  				}
			  			}
			  		
			 	//Change the goal WATER
			  			 
			  			//ENDPOINT = "http://10.218.204.124:5901/introsde/processCentric/updateGoal";
			  			ENDPOINT = "https://sdeprocesscentrichisyam.herokuapp.com/introsde/processCentric/updateGoal";
			    	ClientConfig newclientConfig5 = new ClientConfig();
			 	Client newclient5 = ClientBuilder.newClient(newclientConfig5);

			 	WebTarget newservice5 = newclient5.target(ENDPOINT);

			    	Response newres5 = null;
			 	String newputResp5 = null;

			    	String newupdateGoal5 ="{" + "\"value\": " + newvalue4 + "," + "\"type\": \"" + "water" + "\"" + ","
                            + "\"idGoal\": \"" + "2" + "\""
                            + "}";

			    	newres5 = newservice5.request(MediaType.APPLICATION_JSON_TYPE).put(Entity.json(newupdateGoal5));
			    	newputResp5 = newres5.readEntity(String.class);

			    	if(newres5.getStatus() != 200){
			    		System.out.println("ERROR while updating! Please, try again!");
			    	}else{
			    		System.out.println("Goal has been updated!");
			    	}
			 break;

                case 8:
                   // ENDPOINT = "http://10.218.204.124:5500/introsde/businessLogic/getGoals";
                    ENDPOINT = "https://sdebusinesslogichisyam.herokuapp.com/introsde/businessLogic/getGoals";
                    DefaultHttpClient client6 = new DefaultHttpClient();
                    HttpGet request6 = new HttpGet(ENDPOINT);
                    HttpResponse response6 = client6.execute(request6);

                    BufferedReader rd6 = new BufferedReader(new InputStreamReader(response6.getEntity().getContent()));

                    StringBuffer result6 = new StringBuffer();
                    String line6 = "";
                    while ((line6 = rd6.readLine()) != null) {
                        result6.append(line6);
                    }

                    JSONArray o6 = new JSONArray(result6.toString());

                    if (response6.getStatusLine().getStatusCode() == 200) {

                        System.out.println("***********************************************");
                        System.out.println("YOUR'S GOALS");
                        System.out.println("***********************************************");

                        for(int i = 0; i < o6.length(); i++){
                            System.out.println("Goal: "+o6.getJSONObject(i).getString("type"));
                            System.out.println("Value: "+o6.getJSONObject(i).getInt("value"));
                            System.out.println("idGoal: "+o6.getJSONObject(i).getInt("idGoal"));
                            System.out.println("");
                        }

                    }
                    break;
			     		}
			    	}
	        }
	        else {
	        	System.out.println("\nThank you for using LIFECOACH APP! Remember to stay healthy!");
	        }
			 }
		}

