public class Ticket {
    // Domains
    private char rowLetter;
    private int seatNumber;// The ticket's seat number
    private Person passenger;// The individual in charge of the ticket
    private double ticketPrice;// The cost of the ticket
    private static int[] seatsPerRow = {14, 12, 12, 14}; //Seats per row in a static field

    // Builders
    public Ticket() {
        // The default constructor
    }

    public Ticket(char rowLetter,int seatNumber, Person passenger, double ticketPrice) {
        // Seat number, passenger, and ticket price are set using a parameterized constructor.
        this.rowLetter = rowLetter;
        this.seatNumber = seatNumber;
        this.passenger = passenger;
        this.ticketPrice = ticketPrice;
    }

    // Getter and Setter methods

    public char getRowLetter(){
        return rowLetter;
    }

    // Getter for seat number
    public int getSeatNumber() {
        return seatNumber;
    }

    // Setter for seat number
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    // Getter for traveler
    public Person getPassenger() {
        return passenger;
    }

    // Setter for traveler
    public void setPassenger(Person passenger) {
        this.passenger = passenger;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    // Additional techniques if required


    // To provide a string representation of the Ticket object, override the toString() function.
    @Override
    public String toString() {
        return "Ticket{" +
                "rowLetter=" + rowLetter +
                ",seatNumber=" + seatNumber +
                ", passenger=" + passenger +
                ", ticketPrice=" + ticketPrice +
                '}';
    }

    // Getter for each row's seats
    public static int[] getSeatsPerRow() {
        return seatsPerRow;
    }
}
