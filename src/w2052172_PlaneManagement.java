import java.util.Scanner;
import java.util.InputMismatchException;

public class w2052172_PlaneManagement {

    private static Ticket[] soldTickets = new Ticket[100]; // Considering there are no more than 100 tickets
    private static boolean[][] bookedSeats = new boolean[4][14];
    // int[] seatsPerRow = {14, 12, 12, 14};

    private static int numberOfSoldTickets = 0;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        while (true) {
            displayMenu();
            int option = readOption(input);

            switch (option) {
                case 1:
                    buy_seat();
                    break;
                case 2:
                    cancel_seat();
                    break;
                case 3:
                    find_first_available();
                    break;
                case 4:
                    show_seating_plan();
                    break;
                case 5:
                    print_ticket_info();
                    break;
                case 6:
                    search_ticket();
                    break;
                case 0:
                    System.out.println("Thank you for using the Plane Management application. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please enter a valid option");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("**************************************************");
        System.out.println("*               MENU OPTIONS                     *");
        System.out.println("**************************************************");
        System.out.println("      1) Buy a seat");
        System.out.println("      2) Cancel a seat");
        System.out.println("      3) Find first available seat");
        System.out.println("      4) Show seating plan");
        System.out.println("      5) Print ticket information and total sales");
        System.out.println("      6) Search ticket");
        System.out.println("      0) Quit");
        System.out.println("**************************************************");
        System.out.println("Please select an option:");
    }

    private static int readOption(Scanner scanner) {
        while (true) {
            try {
                int option = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                if (option >= 0 && option <= 6) {
                    return option;
                } else {
                    System.out.println("Please enter a valid option (an integer between 0 and 6).");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid option (an integer between 0 and 6).");
                scanner.nextLine(); // Clear the input buffer
            }
        }
    }


    private static void buy_seat() {
        Scanner input = new Scanner(System.in);

        // Considering four rows (A, B, C, D) and (14,12, 12, 14) seats.
        char[] rows = {'A', 'B', 'C', 'D'};
        int[] seatsPerRow = {14, 12, 12, 14};
        int[][] bookedSeats = new int[rows.length][];

        for (int i = 0; i < rows.length; i++) {
            bookedSeats[i] = new int[seatsPerRow[i]];
        }
        char rowLetter;
        int seatNumber;

        do {
            System.out.println("Enter the row letter (A, B, C, D):");
            rowLetter = input.next().charAt(0);

            if (!isValidRow(rowLetter, rows)) {
                System.out.println("Invalid row letter. Please enter A, B, C, or D.");
            }
        } while (!isValidRow(rowLetter, rows));

        // Validation part for input seat number
        do {
            int maxSeatsInRow = seatsPerRow[rowLetter - 'A'];
            System.out.println("Enter the seat number (1-" + maxSeatsInRow + "):");

            try {
                seatNumber = input.nextInt();

                if (seatNumber < 1 || seatNumber > maxSeatsInRow) {
                    System.out.println("Invalid seat number. Please enter a number between 1 and " + maxSeatsInRow + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number for the seat.");
                input.next(); // To stop an endless loop, consume the invalid input.
                seatNumber = -1; //To keep the cycle going, set an invalid seat number.

            }
        } while (seatNumber < 1);

        if (!isSeatBooked(rowLetter, seatNumber)) {
            // A seat is available; establish Person and Ticket instances.
            Person passenger = createPerson();
            double ticketPrice = calculateTicketPrice(rowLetter, seatNumber);

            Ticket ticket = new Ticket(rowLetter, seatNumber, passenger, ticketPrice);
            //Ticket ticket = new Ticket((rowLetter - 'A') * seatsPerRow[rowLetter - 'A'] + seatNumber, passenger, ticketPrice);

            // Make any necessary updates to the seating arrangement.
            markSeatAsBooked(rowLetter, seatNumber);

            // Include the ticket in the collection of tickets that have been sold.
            storeTicketDetails(ticket);

            System.out.println("A seat has been successfully reserved. Details of the ticket:\n" + ticket);
        } else {
            System.out.println("The seat is not available. Please select a different seat.");
        }
    }
    private static void storeTicketDetails(Ticket ticket) {
        if (numberOfSoldTickets < soldTickets.length) {
            soldTickets[numberOfSoldTickets++] = ticket;
        } else {
            System.out.println("Maximum ticket limit reached. Unable to store additional tickets.");
        }
    }


    private static boolean isValidRow(char rowLetter, char[] rows) {
        for (char validRow : rows) {
            if (rowLetter == validRow) {
                return true;
            }
        }
        return false;
    }

    private static boolean isSeatAvailable(char rowLetter, int seatNumber, int[][] bookedSeats) {
        int rowIdx = rowLetter - 'A';
        int seatIdx = seatNumber - 1; // Seat number conversion to array index

        // Verify that the seat is available and has not already been reserved.
        if (rowIdx >= 0 && rowIdx < bookedSeats.length && seatIdx >= 0 && seatIdx < bookedSeats[rowIdx].length) {
            return bookedSeats[rowIdx][seatIdx] == 0;
        }

        return false;
    }

    private static void markSeatAsBooked(char rowLetter, int seatNumber, int[][] bookedSeats) {
        int rowIdx = rowLetter - 'A';
        int seatIdx = seatNumber - 1; //Seat number conversion to array index

        // Mark the designated seat as reserved.
        bookedSeats[rowIdx][seatIdx] = 1;
    }

    private static Person createPerson() {
        Scanner input = new Scanner(System.in);

        //System.out.println("Enter passenger name:");
        //String name = input.nextLine();
        String name = "";
        boolean validName = false;
        while (!validName) {
            System.out.println("Enter passenger name:");
            name = input.nextLine();
            if (isValidName(name)) {
                validName = true;
            } else {
                System.out.println("Invalid name format. Please enter a valid name.");
            }
        }

        //System.out.println("Enter passenger surname:");
        //String surname = input.nextLine();
        String surname = "";
        boolean validSurname = false;
        while (!validSurname) {
            System.out.println("Enter passenger surname:");
            surname = input.nextLine();
            if (isValidSurname(surname)) {
                validSurname = true;
            } else {
                System.out.println("Invalid surname format. Please enter a valid surname.");
            }
        }

        String email = "";
        boolean validEmail = false;
        while (!validEmail) {
            System.out.println("Enter passenger email:");
            email = input.nextLine();
            // Here is the logic for validating the email.
            if (isValidEmail(email)) {
                validEmail = true;
            } else {
                System.out.println("Invalid email format. Please try again.");
            }
        }

        return new Person(name, surname, email);
    }

    private static boolean isValidName(String name) {
        //  name validation logic
        return !name.isEmpty() && name.matches("[a-zA-Z]+");
    }

    private static boolean isValidSurname(String surname) {
        //  surname validation logic
        return !surname.isEmpty() && surname.matches("[a-zA-Z]+");
    }
    private static boolean isValidEmail(String email) {
        //  apply  email validation logic.
        return email.contains("@") && email.contains(".");
    }



    private static double calculateTicketPrice(char rowLetter, int seatNumber) {
        int lowerBound = 1;
        int upperBound;

        if (rowLetter == 'A' || rowLetter == 'D') {
            upperBound = 14;
        } else {
            upperBound = 12;
        }

        if (seatNumber >= 1 && seatNumber <= 5) {
            return 200.0;
        } else if (seatNumber >= 6 && seatNumber <= 9) {
            return 150.0;
        } else if (seatNumber >= 10 && seatNumber <= upperBound) {
            if ((rowLetter == 'B' || rowLetter == 'C') && seatNumber >= 10 && seatNumber <= 12) {
                return 180.0;
            } else {
                return 150.0; // default cost if no other requirements are met
            }
        } else {
            return 150.0; // If the seat number is not within the range, the default pricing will be used.
        }
    }


    private static void markSeatAsBooked(char rowLetter, int seatNumber) {
        int rowIdx = rowLetter - 'A';
        int seatIdx = seatNumber - 1; // Convert seat number to array index

        if (rowIdx >= 0 && rowIdx < bookedSeats.length && seatIdx >= 0 && seatIdx < bookedSeats[rowIdx].length) {
            bookedSeats[rowIdx][seatIdx] = true;
            System.out.println("Seat marked as booked: " + rowLetter + seatNumber);
        } else {
            System.out.println("Invalid seat. Unable to mark as booked: " + rowLetter + seatNumber);
        }
    }

    private static void cancel_seat() {
        Scanner input = new Scanner(System.in);

        // Assuming 4 rows and seat counts
        char[] rows = {'A', 'B', 'C', 'D'};
        int[] seatsPerRow = {14, 12, 12, 14};


        System.out.println("Enter the row letter (A, B, C, D):");
        char rowLetter = input.next().charAt(0);

        // Verify the input row letter.
        if (!isValidRow(rowLetter, rows)) {
            System.out.println("Invalid row letter. Please enter A, B, C, or D.");
            return;
        }

        System.out.println("Enter the seat number (1-" + seatsPerRow[rowLetter - 'A'] + "):");
        int seatNumber = input.nextInt();

        // Verify the input seat number
        if (seatNumber < 1 || seatNumber > seatsPerRow[rowLetter - 'A']) {
            System.out.println("Invalid seat number. Please enter a number between 1 and " + seatsPerRow[rowLetter - 'A'] + ".");
            return;
        }

        // implementing the seating plan logic
        if (isSeatBooked(rowLetter, seatNumber)) {
            // seat reserved; please cancel

            unmarkSeatAsBooked(rowLetter, seatNumber);

            System.out.println("Seat " + rowLetter + seatNumber + " canceled successfully.");
        } else {
            System.out.println("Seat " + rowLetter + seatNumber + " is not booked. No cancellation needed.");
        }
    }

    private static boolean isSeatBooked(char rowLetter, int seatNumber) {
        int rowIdx = rowLetter - 'A';
        int seatIdx = seatNumber - 1; // Convert seat number to array index

        if (rowIdx >= 0 && rowIdx < bookedSeats.length && seatIdx >= 0 && seatIdx < bookedSeats[rowIdx].length) {
            return bookedSeats[rowIdx][seatIdx];
        } else {
            System.out.println("Invalid seat. Unable to check booked status: " + rowLetter + seatNumber);
            return false;
        }
    }

    private static void unmarkSeatAsBooked(char rowLetter, int seatNumber) {
        int rowIdx = rowLetter - 'A';
        int seatIdx = seatNumber - 1; // Convert seat number to array index

        if (rowIdx >= 0 && rowIdx < bookedSeats.length && seatIdx >= 0 && seatIdx < bookedSeats[rowIdx].length) {
            bookedSeats[rowIdx][seatIdx] = false;
            System.out.println("Seat marked as unbooked: " + rowLetter + seatNumber);
        } else {
            System.out.println("Invalid seat. Unable to mark as unbooked: " + rowLetter + seatNumber);
        }
    }


    private static void find_first_available() {
        // Assuming 4 rows and corresponding seats counts
        char[] rows = {'A', 'B', 'C', 'D'};
        int[] seatsPerRow = {14, 12, 12, 14};

        for (char rowLetter : rows) {
            int maxSeatsInRow = seatsPerRow[rowLetter - 'A'];
            int availableSeat = getFirstAvailableSeat(rowLetter, maxSeatsInRow);

            if (availableSeat != -1) {
                System.out.println("First available seat in row " + rowLetter + ": " + availableSeat);
            } else {
                System.out.println("No available seats in row " + rowLetter);
            }
        }
    }


    private static int getFirstAvailableSeat(char rowLetter, int maxSeatsPerRow) {
        int rowIdx = rowLetter - 'A';

        if (rowIdx >= 0 && rowIdx < bookedSeats.length) {
            for (int seatNumber = 1; seatNumber <= maxSeatsPerRow; seatNumber++) {
                if (!isSeatBooked(rowLetter, seatNumber)) {
                    return seatNumber;
                }
            }
        } else {
            System.out.println("Invalid row. Unable to find available seat in row: " + rowLetter);
        }

        return -1;
    }
// Additional functions (createPerson, calculateTicketPrice, unmarkSeatAsBooked, isSeatBooked, etc.) as required


    private static void show_seating_plan() {
        // Assuming 4 rows (A, B, C, D) and seats per row (14, 12, 12, 14)
        char[] rows = {'A', 'B', 'C', 'D'};
        int[] seatsPerRow = {14, 12, 12, 14};

        // Show the header
        System.out.print("  ");
        for (int seatNumber = 1; seatNumber <= seatsPerRow[0]; seatNumber++) {
            System.out.print(seatNumber + " ");
        }
        System.out.println();

        // Show the seating arrangement
        for (int i = 0; i < rows.length; i++) {
            System.out.print(rows[i] + " ");
            for (int seatNumber = 1; seatNumber <= seatsPerRow[i]; seatNumber++) {
                char status = isSeatBooked(rows[i], seatNumber) ? 'X' : 'O';
                System.out.print(status + " ");
            }
            System.out.println();
        }
    }

// The is SeatBooked() function and any pertinent functions as required

    private static void print_ticket_info() {
        System.out.println("Printing ticket information and total sales");

        // Show the details of the tickets that were sold.
        for (int i = 0; i < numberOfSoldTickets; i++) {
            System.out.println("Ticket " + (i + 1) + ": " + soldTickets[i]);
        }

        // Compute and show the total amount of sales.
        double totalSales = calculateTotalSales();
        System.out.println("Total Sales: $" + totalSales);
    }

    private static double calculateTotalSales() {
        double totalSales = 0.0;

        for (int i = 0; i < numberOfSoldTickets; i++) {
            totalSales += soldTickets[i].getTicketPrice();
        }

        return totalSales;
    }

    // Additional ways to purchase a ticket, modify a reservation, etc.


    private static void search_ticket() {
        Scanner input = new Scanner(System.in);

        // Assuming 4 rows (A, B, C, D) and corresponding seats counts
        char[] rows = {'A', 'B', 'C', 'D'};
        int[] seatsPerRow = {14, 12, 12, 14};

        char rowLetter;
        int seatNumber;

        // Continue until a valid input is received.
        do {
            System.out.println("Enter the row letter (A, B, C, D):");
            rowLetter = input.next().charAt(0);

            if (!isValidRow(rowLetter, rows)) {
                System.out.println("Invalid row letter. Please enter A, B, C, or D.");
            }
        } while (!isValidRow(rowLetter, rows));


        // Verify the seat number that was entered.
        do {
            int maxSeatsInRow = seatsPerRow[rowLetter - 'A'];

            try {
                System.out.println("Enter the seat number (1-" + maxSeatsInRow + "):");
                seatNumber = input.nextInt();

                if (seatNumber < 1 || seatNumber > maxSeatsInRow) {
                    System.out.println("Invalid seat number. Please enter a number between 1 and " + maxSeatsInRow + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number for the seat.");
                input.next(); // In order to stop an infinite loop, consume the faulty input.
                seatNumber = -1; // Set an invalid seat number to make the cycle continue.
            }
        } while (seatNumber < 1);

        // Look for the ticket within the sold-outA variety of tickets
        boolean found = false;
        for (int i = 0; i < numberOfSoldTickets; i++) {
            Ticket ticket = soldTickets[i];
            if (ticket.getSeatNumber() == (rowLetter - 'A') * seatsPerRow[rowLetter - 'A'] + seatNumber) {
                // located the corresponding ticket
                System.out.println("Ticket information for seat " + rowLetter + seatNumber + ":\n" + ticket.getPassenger());
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Seat " + rowLetter + seatNumber + " is not booked.");
        }
    }
        //} catch (InputMismatchException e) {
          //  System.out.println("Invalid input. Please enter a valid number for the seat.");
            //input.next(); // Consume the invalid input to prevent an infinite loop
        //}

}
