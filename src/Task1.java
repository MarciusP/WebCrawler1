import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class Task1 {


    private static String appendString(int day, double price, String departAir, String departTime, String arriveAir, String arriveTime, String toPrint)
    {
        toPrint += "Departure day: " + day + "\n" + "Price: " + price + " departure airport: " + departAir
                + " departure time: " + departTime + " arrival airport: " + arriveAir + " arrival time: "
                + arriveTime + "\n";
        return toPrint;
    }

    private static String addLowest(int day, double price, String departAir, String departTime, String arriveAir, String arriveTime, String toPrint)
    {
        toPrint += "Departure day of cheapest flight: " + day + "\n" + "Price: " + price + " departure airport: " + departAir
                + " departure time: " + departTime + " arrival airport: " + arriveAir + " arrival time: "
                + arriveTime + "\n";
        return toPrint;
    }

    private static void printToFile(String toPrint)
    {
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("AllFlights.txt"));
            writer.write(toPrint);

            writer.close();
        }
        catch (Exception e) {
            System.out.println("Exception in writing thread: " + e);
        }
    }

    public static void main(String[] args) {
        final String urlFirstPart =
                "https://www.norwegian.com/uk/ipc/availability/avaday?D_City=OSL&A_City=RIX&TripType=1&D_Day=";
        final String urlSecondPart =
                "&D_Month=201911&D_SelectedDay=01&R_Day=03&R_Month=201911&R_SelectedDay=03&IncludeTransit=false&AgreementCodeFK=-1&CurrencyCode=EUR&rnd=61083&processid=76875&mode=ab";
        String toPrint = "";
        int lowestPriceDay =-1;
        double lowestPrice = -1;
        String lowDepartAir ="", lowDepartTime ="", lowArrAir ="", lowArrTime ="";

        try{
            Document document;
            for(int i = 1; i<=30; i++)
            {
                document = Jsoup.connect(urlFirstPart + i + urlSecondPart).get();

                System.out.println("Prices of day: " + i);
                double price = -2;
                String departure = "", arrival = "", depAir, arrAir;
                String placeHoldPri, placeHoldDep;

                for (Element row : document.select("table.avadaytable tr"))
                {
                    placeHoldPri = row.select(".standardlowfare.fareselect").text();
                    placeHoldDep =row.select(".depdest").text();

                    if(!placeHoldPri.equals("")){
                       // System.out.println("placeholderis: " + placeHoldPri);
                        arrival =row.select(".arrdest").text();
                        price = Double.parseDouble(placeHoldPri.replace(",", ""));
                        departure = placeHoldDep;
                        System.out.println("Price: " + price + " departure: " + departure + " arrival: " + arrival);
                    }
                    else if (!placeHoldDep.equals("") && price != -2){
                        depAir = placeHoldDep;
                        arrAir = row.select(".arrdest").text();
                        System.out.println("Departure airport: " + depAir + " arrival airport: " + arrAir);
                        if (lowestPrice == -1) {
                            lowestPrice = price;
                            lowestPriceDay = i;
                            lowArrAir = arrAir;
                            lowArrTime =arrival;
                            lowDepartAir =depAir;
                            lowDepartTime =departure;
                        }
                        if (price<lowestPrice) {
                            lowestPrice = price;
                            lowestPriceDay = i;
                            lowArrAir = arrAir;
                            lowArrTime =arrival;
                            lowDepartAir =depAir;
                            lowDepartTime =departure;
                        }
                        toPrint = appendString(i, price, depAir, departure, arrAir, arrival, toPrint);
                    } //else if

                }// for each

            }// for all days

            toPrint = addLowest(lowestPriceDay, lowestPrice, lowDepartAir, lowDepartTime, lowArrAir, lowArrTime, toPrint);
        }
        catch (Exception e)
        {
            System.out.println("Exception was thrown in main method: " + e);
        }

        printToFile(toPrint);
    }
}
