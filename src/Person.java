public class Person {
    // Fields
    private String name; // Name of the person
    private String surname; // Surname of the person
    private String email; // Email of the person

    // Constructor
    public Person(String name, String surname, String email) {
        // Initialize the person with their name, surname, and email using the constructor.
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    // Getters and Setters for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getters and Setters for surname
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    // Getters and Setters for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Override toString() method to provide a string representation of the Person object
    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
