import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Task1 {

    private static List<Flight> flights;
    private static List<Flight> cheapestFlights;

    private static void printToFile()
    {
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("AllFlights.txt"));

            if(!flights.isEmpty()) {
                writer.write("All the flights:\n");

                for (Flight flight : flights) {
                    writer.write(flight.toString());
                }

                writer.write("All the cheapest flights and their info:\n");

                for (Flight flight : cheapestFlights) {
                    writer.write(flight.toString());
                }

            }
            else
                writer.write("No flights were found\n");

            writer.close();
        }
        catch (Exception e) {
            System.out.println("Exception in writing function: " + e);
        }
    }

    public static void main(String[] args) {
        final String urlFirstPart =
                "https://www.norwegian.com/uk/ipc/availability/avaday?D_City=OSL&A_City=RIX&TripType=1&D_Day=";
        final String urlSecondPart =
                "&D_Month=201911&D_SelectedDay=01&R_Day=03&R_Month=201911&R_SelectedDay=03&IncludeTransit=false&AgreementCodeFK=-1&CurrencyCode=EUR&rnd=61083&processid=76875&mode=ab";
        double lowestPrice = -1;
        flights = new ArrayList<>();
        cheapestFlights = new ArrayList<>();

        try{
            Document document;
            for(int i = 1; i<=30; i++)
            {
                document = Jsoup.connect(urlFirstPart + i + urlSecondPart).get();

                double price = -2;
                String departure = "", arrival = "", depAir, arrAir;
                String placeHoldPri, placeHoldDep;

                for (Element row : document.select("table.avadaytable tr"))
                {
                    placeHoldPri = row.select(".standardlowfare.fareselect").text();
                    placeHoldDep =row.select(".depdest").text();

                    if(!placeHoldPri.equals("")){
                        arrival =row.select(".arrdest").text();
                        price = Double.parseDouble(placeHoldPri.replace(",", ""));
                        departure = placeHoldDep;
                    }
                    else if (!placeHoldDep.equals("") && price != -2){
                        depAir = placeHoldDep;
                        arrAir = row.select(".arrdest").text();
                        if (lowestPrice == -1) {
                            cheapestFlights = new ArrayList<>();
                            cheapestFlights.add(new Flight(i, price, depAir, departure, arrAir, arrival));
                            lowestPrice = price;
                        }
                        if (price<lowestPrice) {
                            lowestPrice = price;
                            cheapestFlights = new ArrayList<>();
                            cheapestFlights.add(new Flight(i, price, depAir, departure, arrAir, arrival));
                        }
                        else if (price==lowestPrice)
                            cheapestFlights.add(new Flight(i, price, depAir, departure, arrAir, arrival));

                        flights.add(new Flight(i, price, depAir, departure, arrAir, arrival));
                    } //else if

                }// for each

            }// for all days

        }
        catch (Exception e)
        {
            System.out.println("Exception was thrown in main method: " + e);
        }

        printToFile();
    }
}
