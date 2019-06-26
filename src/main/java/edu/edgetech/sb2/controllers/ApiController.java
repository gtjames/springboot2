package edu.edgetech.sb2.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.edgetech.sb2.models.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class ApiController {

    Pet     pet;

    @RequestMapping("/pet")
    @ResponseBody
    public String api() {
        String  str = "";
        ObjectMapper mapper = new ObjectMapper();

        //  petfinder Authentication URL
        String petsURL = "https://api.petfinder.com/v2/oauth2/token";
        try
        {
            // create a new URL and open a connection
            //  We'll be doing a POST
            URL url = new URL(petsURL);
            HttpURLConnection  con = (HttpURLConnection)url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");

            //  write the parameters to the output stream
            PrintStream ps = new PrintStream( con.getOutputStream() );
            ps.print("grant_type=client_credentials&client_id=5gMD6cxk9KsN9LfDpFHll7wWIYd0quDmmvLgsiCHy6IMGy9Zgu&client_secret=3mJ4y1UTi03ZqMg1flqHXYClKSC0b4YdkXjErLqm");
            ps.close();

            //  wrap the connection input stream with BufferedReader to read plain text
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder content = new StringBuilder();
            while ( (str = br.readLine()) != null ) {
                content.append(str);
            }
            br.close();

            str = new String(content);
            pet =  mapper.readValue(str, Pet.class);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return pet.getAccess_token();
    }

    @RequestMapping("/pet/search")
    @ResponseBody
    public Animals searchPets() {
        Animals animals = null;
        String str = "";
        ObjectMapper mapper = new ObjectMapper();

        //  petfinder Authentication URL
        String petsURL = "https://api.petfinder.com/v2/animals?type=dog&breed=poodle";

        try {
            URL url = new URL(petsURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer " + pet.getAccess_token());

            //  wrap the connection input stream with BufferedReader to read plain text
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder content = new StringBuilder();
            while ((str = br.readLine()) != null) {
                content.append(str);
            }
            br.close();
            str = new String(content);
            System.out.println(str);
            animals =  mapper.readValue(str, Animals.class);
            System.out.println("animals = " + animals);
            System.out.println("yo");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return animals;
    }

    @RequestMapping("/weather/{city}")
    @ResponseBody
    public String weather(@PathVariable String city) {
        Weather weather = null;
        ObjectMapper mapper = new ObjectMapper();

        // creating the URL
        String weatherUrl = "https://api.openweathermap.org/data/2.5/weather?appid=a099a51a6362902523bbf6495a0818aa&q=" + city;

        try
        {
            /* create a new URL and open a connection */
            weather =  mapper.readValue(new URL(weatherUrl), Weather.class);
        }
        catch ( Exception e ) { System.out.println(e); }

        return weather.toString();
    }

    /**
     * 		TODO	The rest of these endpoints are for educational purposes
     * 				All of these endpoints are annotated with @ResponseBody. This means what ever we return
     * 				with the return statement is exactly and only what will be displayed in the browser.
     * 				This makes for some great experimentation	.
     *
     * 				/api/string/{any text we want}
     * 					assigns the text following /product/string/{XXXXXX} to the line parameter
     * 					Then we can do whatever is required with that data
     *
     *				/api/query?id={some text}&string={some other text}&number={an optional NUMBER}
     *					This endpoint has three possible parameters passed to it.
     *					The first two are required and number is optional
     *
     *				/api/vowels/{some text}
     *					we are going to count the vowels in the string passed into the endpoint
     *					along the way we will also save the consonants that are obviously not vowels
     *
     *				/api/tree/{how many limbs}
     *					let's practice for that job interview question
     *
     */
    @RequestMapping(value = "/string/{line}")
    @ResponseBody
    public String result(@PathVariable String line) {
        return line;
    }

    @RequestMapping(value = "/query")
    @ResponseBody
    public String query(@RequestParam String id,                            //	'id' param is required
                        @RequestParam("string") String input,                //	'string' param is required. the variable used is input
                        @RequestParam("number") Optional<Integer> number) {	//	'number' param is optional
        return "These are the Query params. id: " + id + " string: " + input + " and number: " + (number.isPresent() ? number.get() : "not passed in");
    }

    @RequestMapping(value="/vowels/{line}", method= RequestMethod.GET)
    @ResponseBody
    public String vowels(@PathVariable String line) {
        int vowels = 0;
        String list = "";
        for (int i = 0; i < line.length(); i++) {
            if ( "aeiou".contains(""+line.charAt(i))) {
                vowels++;
            }
            else {
                list += line.charAt(i);
            }
        }
        return "'" + line + "' has " + vowels + " vowels. Here are the remaining consonants " + list;
    }

    @RequestMapping("/tree/{levels}")
    @ResponseBody
    public String tree(@PathVariable Integer levels) {
        String strTree = "";
        String row = new String(new char[levels-1]).replace  ("\0", " ") +
                new String(new char[levels*2-1]).replace("\0", "*");
        for ( int i = 1; i <= levels; i++ ) {
            strTree += row.substring(i-1, levels-1+2*i-1) + "<br>";
        }
        return "<pre>" + strTree + "</pre>";
    }
}
