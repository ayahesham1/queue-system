import java.util.ArrayList;
import java.util.List;

public class Client {

    private int id;
    private String firstName;
    private String lastName;
    private int yearOfBirth;
    private Gender gender;
    private List<Request> requests;
    private int arrivalTime;
    private int waitingTime;
    private int timeInQueue;
    private int serviceTime;
    private int departureTime;
    private int patience;
    private static int lastClientId = 0;
    private String serverName;

    // Constructors
    public Client(String firstName, String lastName, int yearOfBirth, Gender gender, int arrivalTime, int patience, List<Request> requests) {
        this.id = ++lastClientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
        this.gender = gender;
        this.arrivalTime = Math.max(arrivalTime, 0); // Ensure arrival time is non-negative
        this.patience = patience;
        this.requests = requests != null ? requests : new ArrayList<>();
        this.waitingTime = 0;
        this.timeInQueue = 0;
        this.serviceTime = 0;
        this.departureTime = 0;
        this.serverName = "Unknown";  // Default value
    }

    // Overloaded constructors for flexibility
    public Client(String firstName, String lastName, int yearOfBirth, Gender gender, int patience, List<Request> requests) {
        this(firstName, lastName, yearOfBirth, gender, 0, patience, requests);
    }

    public Client(String firstName, String lastName, int yearOfBirth, Gender gender, int patience) {
        this(firstName, lastName, yearOfBirth, gender, 0, patience, new ArrayList<>());
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests != null ? requests : new ArrayList<>();
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        if (arrivalTime >= 0) {
            this.arrivalTime = arrivalTime;
        }
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        if (waitingTime >= 0) {
            this.waitingTime = waitingTime;
        }
    }

    public int getTimeInQueue() {
        return timeInQueue;
    }

    public void setTimeInQueue(int timeInQueue) {
        if (timeInQueue >= 0) {
            this.timeInQueue = timeInQueue;
        }
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(int departureTime) {
        if (departureTime >= arrivalTime + waitingTime + timeInQueue) {
            this.departureTime = departureTime;
        }
    }

    public int getPatience() {
        return patience;
    }

    public void setPatience(int patience) {
        this.patience = patience;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    // Method to estimate service level based on waiting time and queue time
    public int estimateServiceLevel() {
        int serviceLevel = 10;
        int[] thresholds = {4, 6, 8, 10, 12};

        for (int threshold : thresholds) {
            if (waitingTime > threshold) {
                serviceLevel--;
            }
            if (timeInQueue > threshold) {
                serviceLevel--;
            }
        }
        return Math.max(serviceLevel, 0);
    }

    // toString method for displaying client information
    @Override
    public String toString() {
        return String.format(
            "Client %s, %s\n** Arrival Time: %d\n** Waiting Time: %d\n** Time in Queue: %d\n" +
            "** Service Time: %d\n** Departure Time: %d\n** Served By Server: %s\n** Service Level: %d\n",
            lastName, firstName, arrivalTime, waitingTime, timeInQueue, serviceTime, departureTime, serverName, estimateServiceLevel()
        );
    }
}
