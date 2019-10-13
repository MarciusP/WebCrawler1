public class Flight {

    final int day;
    final double price;
    final String departureAirport, departureTime, arrivalAirport, arrivalTime;

    public Flight(int day, double price, String departureAirport, String departureTime, String arrivalAirport, String arrivalTime)
    {
        this.day = day;
        this.price = price;
        this.departureAirport = departureAirport;
        this.departureTime = departureTime;
        this.arrivalAirport = arrivalAirport;
        this.arrivalTime = arrivalTime;
    }

    public String toString(){
        String fullFlight = "Departure day: " + day + "\n" + "Price: " + price + " departure airport: " + departureAirport
                + " departure time: " + departureTime + " arrival airport: " + arrivalAirport + " arrival time: "
                + arrivalTime + "\n";

        return fullFlight;
    }
}
