public class Queue {
    private String serverName;
    private int queueSize;
    private Client clientBeingServed;
    private Request requestInProgress;
    private int processingStartTime;
    private Client[] clientsHistory;
    private Client[] clientsInQueue;

    public Queue (String serverName, int queueSize) {
        this.serverName = serverName;
        this.queueSize = queueSize;
        this.clientsHistory = new Client[queueSize];
        this.clientsInQueue = new Client[queueSize];
    }

    public void setServerName(String serverName){
        this.serverName = serverName;
    }

    public String getServerName(){
        return serverName;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    public int getQueueSize(){
        return queueSize;
    }

    public void setClientBeingServed(Client clientBeingServed){
        this.clientBeingServed = clientBeingServed;
    }

    public Client getClientBeingServed() {
        return clientBeingServed;
    }

    public void setRequestInProgress( Request requestInProgress) {
        this.requestInProgress = requestInProgress;
    }

    public Request getRequestInProgress(){
        return requestInProgress;
    }

    public void setProcessingStartTime(int processingStartTime) {
        this.processingStartTime = processingStartTime;
    }

    public int getProcessingStartTime(){
        return processingStartTime;
    }

    public void setClientsHistory(Client[] clientsHistory){
        this.clientsHistory = clientsHistory;
    }

    public Client[] getClientsHistory(){
        return clientsHistory;
    }

    public void setClientsInQueue(Client[] clientsInQueue) {
        this.clientsInQueue = clientsInQueue;
    }

    public Client[] getClientsInQueue(){
        return clientsInQueue;
    }

    public String toString(){
        ///[Queue:1][09]-----[13][21][12][08][03]
        String queueRepString = "[Queue:" + serverName + "]";
        queueRepString += "[" + clientBeingServed.getId() + "]";
        queueRepString += "-----";
        
        for (Client client : clientsInQueue) {
        	if (client != null) {
        		queueRepString += "[" + client.getId() + "]";
        	}
        }
        
        return queueRepString;
    }

    public String toString(boolean showID) {
    	String showIdStr = "[Queue:" + serverName + "]";
    	if (showID == true) {
    		showIdStr += "-----";
    		for (Client client : clientsInQueue) {
    			if (client != null) {
    				showIdStr += "[" + client.getId() + "]";
    			}
    		}	
    	}
    	else {
            showIdStr += "-----";
            if (clientBeingServed != null) {
                int remainingTime = 0;
        
                if (clientBeingServed.getDepartureTime() > 0) {
                    remainingTime = clientBeingServed.getArrivalTime() - clientBeingServed.getDepartureTime();
                }
                showIdStr += "[" + String.format("%02d", remainingTime) + "]";
            }
            for (Client client : clientsInQueue) {
                if (client != null) {
                    Request clientRequest = getRequestInProgress(); 
                    if (clientRequest != null) {
                        int estimatedTime = clientRequest.calculateProcessingTime();
                        showIdStr += "[" + String.format("%02d", estimatedTime) + "]";
                    }
                }
            }
        }
        
        ///[Queue:1][01]-----[07][13][19][25][31]
        return showIdStr;
    }


}