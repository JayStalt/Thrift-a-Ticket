package com.example.java_backend.backend;

import java.awt.FlowLayout;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.springframework.stereotype.Service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// For SeatGeek API:
// Your app secret is
// fab7dbffd504ffa3c4b334e9fc0c24b0957ebec644fb21557f73f2b7e9c05c43 - copy now
// as it cannot be retrieved later.
@Service
public class ticketAPIMain {
	private static final HttpResponse<String> NULL = null;

	public Iterable<UserTickets> callAPI(String user_event_name, String user_state_initials) {
		Scanner kb = new Scanner(System.in);
		{
			// get event name and state initials from user
			// System.out.print("Search for your event's name: ");
			// String user_event_name = kb.nextLine();
			user_event_name = user_event_name.replaceAll(" ", ",");
			// System.out.print("Enter initials of the state you want to see the concert in:
			// ");
			// String user_state_initials = kb.nextLine();

			// concatenate user input with get command URL
			String get_req_url = "https://app.ticketmaster.com/discovery/v2/events.json?keyword=" + user_event_name
					+ "&countryCode=US&stateCode="
					+ user_state_initials + "&apikey=kp12R01fgG1JtbhuJmKX9tzmho9pYjDy";

			// Sending HTTP GET request to get the top 20 chart rankings from the online
			// movie database
			HttpResponse<String> response = NULL;
			try {
				response = Unirest.get(get_req_url)
						.header("X-RapidAPI-Key", "b6f3fb89aemsh3d591bae31cc233p190d09jsnc75897a96de4")
						.header("X-RapidAPI-Host", "seatgeek-seatgeekcom.p.rapidapi.com")
						.asString();
			} catch (UnirestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Getting the response body
			String responseString = response.getBody();
			//System.out.println("response: " + responseString);
			List<UserTickets> ticketArray = new ArrayList<>();

			int substringIndex = 0;
			String toDisplay = responseString.substring(substringIndex);
			//while (substringIndex < responseString.length()) {
				// Parsing the response for relevant data
				// getting event name
				try {
					toDisplay = toDisplay.substring(substringIndex);
					int event_name_loc = toDisplay.indexOf("name\":\"");
					event_name_loc += 7;
					String event_name_remaining = toDisplay.substring(event_name_loc);
					int quote_loc = event_name_remaining.indexOf("\"");
					String event_name_string = toDisplay.substring(event_name_loc, event_name_loc + quote_loc);

					// error handling if user's desired concert isn't available
					if (event_name_string.equals("ks")) {
						System.out.println("Error finding concert tickets for your event.");
						System.exit(0); // to be changed once integrated with rest of program
					}

					// System.out.println("\nEVENT NAME: " + event_name_string);

					// getting event ticket purchase site URL
					int url_loc = toDisplay.indexOf("url\":\"");
					url_loc += 6;
					String url_remaining = toDisplay.substring(url_loc);
					quote_loc = url_remaining.indexOf("\"");
					String url_string = toDisplay.substring(url_loc, url_loc + quote_loc);
					// System.out.println("TICKET PURCHASE URL: " + url_string);

					// getting image of artist
					int img_loc = toDisplay.indexOf("ratio\":\"16_9\",\"url\":\"");
					img_loc += 21;
					String img_remaining = toDisplay.substring(img_loc);
					quote_loc = img_remaining.indexOf("\"");
					String img_string = toDisplay.substring(img_loc, img_loc + quote_loc);
					// System.out.println("URL OF ARTIST IMG: " + img_string);

					// getting sale start date of public tickets
					int sale_start_loc = toDisplay.indexOf("startDateTime\":\"");
					sale_start_loc += 16;
					String sale_start_remaining = toDisplay.substring(sale_start_loc);
					quote_loc = sale_start_remaining.indexOf("\"");
					String sale_start_string = toDisplay.substring(sale_start_loc, sale_start_loc + quote_loc);
					String sale_start_date = sale_start_string.substring(0, 10);
					String sale_start_time = sale_start_string.substring(11, 16);
					// System.out.println("PUBLIC TICKET SALE START DATE AND TIME: " +
					// sale_start_date + " " + sale_start_time);

					// getting sale end date of public tickets
					int sale_end_loc = toDisplay.indexOf("endDateTime\":\"");
					sale_end_loc += 14;
					String sale_end_remaining = toDisplay.substring(sale_end_loc);
					quote_loc = sale_end_remaining.indexOf("\"");
					String sale_end_string = toDisplay.substring(sale_end_loc, sale_end_loc + quote_loc);
					String sale_end_date = sale_end_string.substring(0, 10);
					String sale_end_time = sale_end_string.substring(11, 16);
					// System.out.println("PUBLIC TICKET SALE END DATE AND TIME: " + sale_end_date +
					// " " + sale_end_time);

					// getting the time zone
					int timezone_loc = toDisplay.indexOf("timezone\":\"");
					timezone_loc += 11;
					String timezone_remaining = toDisplay.substring(timezone_loc);
					quote_loc = timezone_remaining.indexOf("\"");
					String timezone_string = toDisplay.substring(timezone_loc, timezone_loc + quote_loc);
					// System.out.println("EVENT TIMEZONE: " + timezone_string);

					// getting the price ranges for the event
					// getting minimum price of a ticket
					int min_price_loc = toDisplay.indexOf("min\":");
					min_price_loc += 5;
					String min_price_remaining = toDisplay.substring(min_price_loc);
					int comma_loc = min_price_remaining.indexOf(",");
					String min_price_string = toDisplay.substring(min_price_loc, min_price_loc + comma_loc);
					// System.out.println("EVENT TICKET MINIMUM PRICE: $" + min_price_string);

					// getting maximum price of a ticket
					int max_price_loc = toDisplay.indexOf("max\":");
					max_price_loc += 5;
					String max_price_remaining = toDisplay.substring(max_price_loc);
					int curly_brace_loc = max_price_remaining.indexOf("}");
					String max_price_string = toDisplay.substring(max_price_loc, max_price_loc + curly_brace_loc);
					// System.out.println("EVENT TICKET MAX PRICE: $" + max_price_string);

					// getting the event date and time
					// getting the event date
					int event_date_loc = toDisplay.indexOf("localDate\":\"");
					event_date_loc += 12;
					String event_date_remaining = toDisplay.substring(event_date_loc);
					quote_loc = event_date_remaining.indexOf("\"");
					String event_date_string = toDisplay.substring(event_date_loc, event_date_loc + quote_loc);
					// System.out.println("DATE OF EVENT: " + event_date_string);

					// getting the event time
					int event_time_loc = toDisplay.indexOf("localTime\":\"");
					event_time_loc += 12;
					String event_time_remaining = toDisplay.substring(event_time_loc);
					quote_loc = event_time_remaining.indexOf("\"");
					String event_time_string = toDisplay.substring(event_time_loc, event_time_loc + quote_loc);
					event_time_string = event_time_string.substring(0, 5);
					// System.out.println("TIME OF EVENT: " + event_time_string);

					// getting the event's current status (onsale, offsale, canceled, postponed, or
					// rescheduled)
					int status_loc = toDisplay.indexOf("status\":{\"code\":\"");
					status_loc += 17;
					String status_remaining = toDisplay.substring(status_loc);
					quote_loc = status_remaining.indexOf("\"");
					String status_string = toDisplay.substring(status_loc, status_loc + quote_loc);
					substringIndex = status_loc + quote_loc;
					// System.out.println("EVENT'S CURRENT STATUS: " + status_string);
					UserTickets temp = new UserTickets(null, event_name_string, event_name_string, event_date_string,
							event_time_string, min_price_string, url_string, img_string);
					ticketArray.add(temp);
					//System.out.println(substringIndex);
				} catch (StringIndexOutOfBoundsException e) {
					// Handle the exception by returning the current ticketArray
					return ticketArray;
				}
			//}
			return ticketArray;
		}
	}
}
