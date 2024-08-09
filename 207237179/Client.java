public class Client {
    
    private int id;
    private String firstName;
    private String lastName;
    private int yearOfBirth;
    private Gender gender;
    private Request[] requests;
    private int arrivalTime;
    private int waitingTime;
    private int timeInQueue;
    private int serviceTime;
    private int departureTime;
    private int patience;
    private static int lastClientId = 0;
    private String serverName; //additional field to access server name for string

    ///getters and setters
    
    public void setRequests(Request[] requests) {
    this.requests = requests;
    }
    
    public Request[] getRequests(){
    	return requests;
    }
    
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerName() {
        return serverName;
    }


    public int getPatience(){
        return patience;
    }

    public void setPatience(int patience){
        this.patience = patience;
    }

    public int getYearOfBirth(){
        return yearOfBirth;
    }

    public void setYearOfbirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public int getServiceTime(){
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }


    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



    public void setArrivalTime(int arrivalTime) {
        if (arrivalTime > 0) {
            this.arrivalTime = arrivalTime;
        }
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setDepartureTime(int departureTime) {
        if (departureTime == 0 || departureTime >= (arrivalTime+waitingTime+timeInQueue)){
           this.departureTime= departureTime;
        }
    }
    
    public int getDepartureTime(){
        return departureTime;
    }

    public void setWaitingTime(int waitingTime) {
        if (waitingTime >= 0) {
            this.waitingTime = waitingTime;
        }
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setTimeInQueue(int timeInQueue) {
        if (timeInQueue >= 0) {
            this.timeInQueue = timeInQueue;
        }
    }

    public int getTimeInQueue() {
        return timeInQueue;
    }



    public Client(String firstName, String lastName, int yearOfBirth, Gender gender,
        int arrivalTime, int patience, Request[] requests) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.yearOfBirth = yearOfBirth;
    this.gender = gender;
    this.arrivalTime = (arrivalTime > 0) ? arrivalTime : 1; 
    this.id = lastClientId++;
    this.waitingTime = 0;
    this.timeInQueue = 0;
    this.serviceTime = 0;
    this.departureTime = 0;
    this.requests = requests;
}

public Client(String firstName, String lastName, int yearOfBirth, Gender gender,
        int patience, Request[] requests) {
    this(firstName, lastName, yearOfBirth, gender, 0, patience, requests);
}

public Client(String firstName, String lastName, int yearOfBirth, Gender gender,
        int patience) {
    this(firstName, lastName, yearOfBirth, gender, 0, patience, new Request[0]); 
}


    public int estimateServiceLevel(){
        int serviceLevel = 10;

        
        if (waitingTime > 4) {
            serviceLevel -= 1;
        }
        if (waitingTime > 6) {
            serviceLevel -= 1;
        }
        if (waitingTime > 8) {
            serviceLevel -= 1;
        }
        if (waitingTime > 10) {
            serviceLevel -= 1;
        }
        if (waitingTime > 12) {
            serviceLevel -= 1;
        }
        if (timeInQueue > 4) {
            serviceLevel -= 1;
        }
        if (timeInQueue > 6) {
            serviceLevel -= 1;
        }
        if (timeInQueue > 8) {
            serviceLevel -= 1;
        }
        if (timeInQueue > 10) {
            serviceLevel -= 1;
        }
        if (timeInQueue > 12) {
            serviceLevel -= 1;
        }

        return serviceLevel;


    }

    public String toString() {
        String finalResponse = "";

        finalResponse += "Client " + lastName + ", " + firstName + "\n";
        finalResponse += "** Arrival Time : " + arrivalTime + "\n";
        finalResponse += "** Waiting Time : " + waitingTime + "\n";
        finalResponse += "** Time in Queue : " + timeInQueue + "\n";
        finalResponse += "** Service Time : " + serviceTime + "\n";
        finalResponse += "** Departure Time : " + departureTime + "\n";
        finalResponse += "** Served By Server : " + serverName + "\n";
        finalResponse += "** Service Level : " + estimateServiceLevel() + "\n";
        //come back and put in server name

        return finalResponse;
    }

}