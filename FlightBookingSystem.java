import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

class Passenger {
    private String fullName;
    private int age;

    public Passenger(String fullName, int age) {
        this.fullName = fullName;
        this.age = age;
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }
}

class Flight {
    private String airline;
    private String flightNumber;
    private String departureCity;
    private String destinationCity;
    private String departureTime;
    private Map<String, Integer> classPrices;
    private Map<String, Integer> availableSeats;

    public Flight(String airline, String flightNumber, String departureCity, String destinationCity, String departureTime) {
        this.airline = airline;
        this.flightNumber = flightNumber;
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        this.departureTime = departureTime;
        this.classPrices = new HashMap<>();
        this.availableSeats = new HashMap<>();
    }

    public String getAirline() { return airline; }
    public String getFlightNumber() { return flightNumber; }
    public String getDepartureCity() { return departureCity; }
    public String getDestinationCity() { return destinationCity; }
    public String getDepartureTime() { return departureTime; }
    public int getPrice(String ticketClass) { return classPrices.getOrDefault(ticketClass, 0); }
    public int getAvailableSeats(String ticketClass) { return availableSeats.getOrDefault(ticketClass, 0); }

    public void setClassPrice(String ticketClass, int price) {
        this.classPrices.put(ticketClass, price);
    }

    public void setAvailableSeats(String ticketClass, int seats) {
        this.availableSeats.put(ticketClass, seats);
    }

    public boolean areSeatsAvailable(String ticketClass, int numTickets) {
        return getAvailableSeats(ticketClass) >= numTickets;
    }

    public void bookSeats(String ticketClass, int numTickets) {
        if (areSeatsAvailable(ticketClass, numTickets)) {
            availableSeats.put(ticketClass, availableSeats.get(ticketClass) - numTickets);
        }
    }

    public String getFlightDetails() {
        return String.format("| %-14s | %-10s | Rs %-10d | Rs %-12d | Rs %-11d |",
                airline, flightNumber, getPrice("Economy"), getPrice("Business"), getPrice("First"));
    }
}

class Booking {
    private Passenger passenger;
    private Flight flight;
    private String ticketClass;
    private int numberOfTickets;
    private double totalCost;
    private String travelDate;

    public Booking(Passenger passenger, Flight flight, String ticketClass, int numberOfTickets, String travelDate) {
        this.passenger = passenger;
        this.flight = flight;
        this.ticketClass = ticketClass;
        this.numberOfTickets = numberOfTickets;
        this.travelDate = travelDate;
        this.totalCost = flight.getPrice(ticketClass) * numberOfTickets;
    }

    public String generateConfirmation() {
        return "--- Flight Ticket Confirmation ---\n" +
                "Passenger Name: " + passenger.getFullName() + "\n" +
                "Age: " + passenger.getAge() + "\n" +
                "Airline: " + flight.getAirline() + "\n" +
                "Flight: " + flight.getFlightNumber() + " - " + flight.getDepartureTime() + "\n" +
                "Class: " + ticketClass + "\n" +
                "Route: " + flight.getDepartureCity() + " -> " + flight.getDestinationCity() + "\n" +
                "Travel Date: " + travelDate + "\n" +
                "Number of Tickets: " + numberOfTickets + "\n" +
                "Total Cost: Rs " + String.format("%.2f", totalCost) + "\n" +
                "------------------------------------";
    }

    public void saveToFile() {
        String fileName = "Booking-" + passenger.getFullName().replaceAll("\\s+", "") + "-" + flight.getFlightNumber() + ".txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(generateConfirmation());
            System.out.println("\n✅ Ticket successfully saved to file: " + fileName);
        } catch (IOException e) {
            System.err.println("Error: Could not save ticket to file. " + e.getMessage());
        }
    }
}

public class FlightBookingSystem {
    private static final List<Flight> allFlights = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);

    static {
        Flight f1 = new Flight("Air India", "AI101", "Delhi", "Mumbai", "10:00 AM");
        f1.setClassPrice("Economy", 2000); f1.setAvailableSeats("Economy", 50);
        f1.setClassPrice("Business", 5000); f1.setAvailableSeats("Business", 20);
        f1.setClassPrice("First", 8000); f1.setAvailableSeats("First", 10);
        allFlights.add(f1);

        Flight f2 = new Flight("IndiGo", "6E123", "Delhi", "Mumbai", "12:30 PM");
        f2.setClassPrice("Economy", 1800); f2.setAvailableSeats("Economy", 75);
        f2.setClassPrice("Business", 4500); f2.setAvailableSeats("Business", 15);
        f2.setClassPrice("First", 7500); f2.setAvailableSeats("First", 8);
        allFlights.add(f2);

        Flight f3 = new Flight("SpiceJet", "SG456", "Mumbai", "Bangalore", "3:00 PM");
        f3.setClassPrice("Economy", 1700); f3.setAvailableSeats("Economy", 40);
        f3.setClassPrice("Business", 4000); f3.setAvailableSeats("Business", 10);
        f3.setClassPrice("First", 7000); f3.setAvailableSeats("First", 5);
        allFlights.add(f3);

        Flight f4 = new Flight("Vistara", "UK789", "Delhi", "Bangalore", "6:00 PM");
        f4.setClassPrice("Economy", 2200); f4.setAvailableSeats("Economy", 60);
        f4.setClassPrice("Business", 5500); f4.setAvailableSeats("Business", 18);
        f4.setClassPrice("First", 8500); f4.setAvailableSeats("First", 9);
        allFlights.add(f4);
    }

    public static void main(String[] args) {
        System.out.println("✈️  --------------- WELCOME TO FLIGHT BOOKING SYSTEM --------------- ✈️");

        System.out.print("Enter Full Name: ");
        String fullName = sc.nextLine();
        int age = getIntInput("Enter age: ");
        Passenger passenger = new Passenger(fullName, age);

        System.out.print("Enter Departure City: ");
        String departureCity = sc.nextLine().trim();
        System.out.print("Enter Destination City: ");
        String destinationCity = sc.nextLine().trim();
        String travelDate = getValidDate();

        List<Flight> availableFlights = findFlights(departureCity, destinationCity);
        if (availableFlights.isEmpty()) {
            System.out.println("\n❌ No flights found for the route: " + departureCity + " -> " + destinationCity);
            return;
        }
        displayFlights(availableFlights);

        int flightChoice = getIntInput("Choose a flight (Enter number 1-" + availableFlights.size() + "): ", 1, availableFlights.size()) - 1;
        Flight selectedFlight = availableFlights.get(flightChoice);

        String ticketClass = chooseClass();
        int numTickets = getIntInput("Enter Number of Tickets: ");

        if (!selectedFlight.areSeatsAvailable(ticketClass, numTickets)) {
            System.out.println("\n❌ Booking Failed: Not enough seats available in " + ticketClass + " class.");
            System.out.println("Available seats: " + selectedFlight.getAvailableSeats(ticketClass));
            return;
        }

        selectedFlight.bookSeats(ticketClass, numTickets);
        Booking booking = new Booking(passenger, selectedFlight, ticketClass, numTickets, travelDate);

        System.out.println("\n" + booking.generateConfirmation());
        booking.saveToFile();

        System.out.println("\n✅ Thank you for booking with us! Have a safe journey! 👋");
    }

    private static int getIntInput(String prompt, int... range) {
        int value;
        while (true) {
            try {
                System.out.print(prompt);
                value = Integer.parseInt(sc.nextLine().trim());
                if (range.length == 2 && (value < range[0] || value > range[1])) {
                    System.out.println("Error: Please enter a number between " + range[0] + " and " + range[1] + ".");
                } else if (value <= 0 && range.length == 0) {
                    System.out.println("Error: Please enter a positive number.");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid input. Please enter a whole number.");
            }
        }
    }

    private static String getValidDate() {
        while (true) {
            System.out.print("Enter Date of Travel (YYYY-MM-DD): ");
            String dateStr = sc.nextLine().trim();
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate travelDate = LocalDate.parse(dateStr, formatter);
                LocalDate minDate = LocalDate.now();
                if (travelDate.isBefore(minDate)) {
                    System.out.println("Error: Date cannot be in the past. Please enter a future date.");
                } else {
                    return dateStr;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Error: Invalid date format. Please use YYYY-MM-DD.");
            }
        }
    }

    private static List<Flight> findFlights(String departure, String destination) {
        return allFlights.stream()
                .filter(f -> f.getDepartureCity().equalsIgnoreCase(departure) &&
                        f.getDestinationCity().equalsIgnoreCase(destination))
                .collect(Collectors.toList());
    }

    private static void displayFlights(List<Flight> flights) {
        System.out.println("\nDetails of Available Flights:");
        System.out.println("+----+----------------+------------+---------------+----------------+---------------+");
        System.out.println("| No | Airline        | Flight No  | Economy Class | Business Class | First Class   |");
        System.out.println("+----+----------------+------------+---------------+----------------+---------------+");
        for (int i = 0; i < flights.size(); i++) {
            System.out.printf("| %-2d %s\n", (i + 1), flights.get(i).getFlightDetails());
        }
        System.out.println("+----+----------------+------------+---------------+----------------+---------------+");
    }

    private static String chooseClass() {
        System.out.println("\nChoose Class:");
        System.out.println("1. Economy");
        System.out.println("2. Business");
        System.out.println("3. First");
        int classChoice = getIntInput("Enter your choice (1-3): ", 1, 3);
        switch (classChoice) {
            case 1: return "Economy";
            case 2: return "Business";
            case 3: return "First";
            default: return "";
        }
    }
}
