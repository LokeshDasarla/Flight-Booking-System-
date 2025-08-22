# ✈️ Flight Booking System 
A **console-based flight booking application** developed in **Java**.  
This system allows users to search for flights, select a ticket class, book seats, and generate a **booking confirmation file**

## ✅ Features
✔ **Search flights** by departure and destination city  
✔ **Choose class** (Economy, Business, First)  
✔ **Dynamic seat availability** (updates after booking)  
✔ **Price calculation** based on class and ticket count  
✔ **Date validation** (future date only)  
✔ **Save booking confirmation** to a text file  
✔ **Input validation** for age, date, and ticket count  

## 📂 **Project Structure**

**Classes:**
- `Passenger` → Stores passenger details (name, age)
- `Flight` → Stores flight details, class prices, and seat availability
- `Booking` → Generates confirmation and saves ticket to file
- `FlightBookingSystem` → Main class with booking logic

## 🛠 **Technologies Used**
- Java 8+ (Uses `LocalDate`, `DateTimeFormatter`, Streams)
- File I/O (`FileWriter` for saving booking confirmation)
## ▶️ **How to Run**
1. **Clone the project** or download the `.java` file.
2. Open a terminal and navigate to the project directory.
3. **Compile the Java file:**
   java FlightBookingSystem
Sample Output:
✈️  --------------- WELCOME TO FLIGHT BOOKING SYSTEM --------------- ✈️
Enter Full Name: Lokesh Dasarla
Enter age: 23
Enter Departure City: Delhi
Enter Destination City: Mumbai
Enter Date of Travel (YYYY-MM-DD): 2025-08-30

Details of Available Flights:
+----+----------------+------------+---------------+----------------+---------------+
| No | Airline        | Flight No  | Economy Class | Business Class | First Class   |
+----+----------------+------------+---------------+----------------+---------------+
| 1  | Air India      | AI101      | Rs 2000       | Rs 5000        | Rs 8000       |
| 2  | IndiGo         | 6E123      | Rs 1800       | Rs 4500        | Rs 7500       |
+----+----------------+------------+---------------+----------------+---------------+
Choose a flight (Enter number 1-2): 2

Choose Class:
1. Economy
2. Business
3. First
Enter your choice (1-3): 1
Enter Number of Tickets: 2

--- Flight Ticket Confirmation ---
Passenger Name: Lokesh Dasarla
Age: 23
Airline: IndiGo
Flight: 6E123 - 12:30 PM
Class: Economy
Route: Delhi -> Mumbai
Travel Date: 2025-08-30
Number of Tickets: 2
Total Cost: Rs 3600.00
------------------------------------

✅ Ticket successfully saved to file: Booking-LokeshDasarla-6E123.txt

   ```bash
   javac FlightBookingSystem.java
