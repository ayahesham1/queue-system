public class QueueSystem {
    private int clock; //indicates time of system
    private int totalWaitingTime; //total waiting time of clients
    private Client[] clientsWorld; //clients ready to enter system
    private int totalClientsInSystem; //stores num of clients in system
    private int waitingLineSize; //size of waiting lime
    private Client[] waitingLine; //clients in waitimg line
    private boolean tvInWaitingArea; 
    private boolean coffeeInWaitingArea;
    private Queue[] queues;

    public QueueSystem (int waitingLineSize, boolean tvInWaitingArea, boolean coffeeInWaitingArea){
        this.clock = 0;
        this.tvInWaitingArea = tvInWaitingArea;
        this.coffeeInWaitingArea = coffeeInWaitingArea;
        this.clientsWorld = new Client[waitingLineSize];
        this.waitingLineSize = waitingLineSize;
        this.waitingLine = new Client[waitingLineSize];
        this.queues = new Queue[waitingLineSize];

        for (int i = 0; i < queues.length; i++){
            queues[i] = new Queue("Queue" + i, i);
        
        }
        
        if (tvInWaitingArea == true) {
        	for (Client client : clientsWorld) {
        		if (client != null) {
            		client.setPatience(client.getPatience() + 20);
        		}
    		}
    		if (coffeeInWaitingArea == true) {
        		for (Client client : clientsWorld) {
            		if (client != null) {
                		client.setPatience(client.getPatience() + 15);
                	}
                }
            }
        }
    }
    
    public int getClock() {
        return clock;
    }

    public void setClock(int clock) {
        this.clock = clock;
    }

    public int getTotalWaitingTime() {
        return totalWaitingTime;
    }

    public void setTotalWaitingTime(int totalWaitingTime) {
        this.totalWaitingTime = totalWaitingTime;
    }

    public Client[] getClientsWorld() {
        return clientsWorld;
    }

    public void setClientsWorld(Client[] clientsWorld) {
        this.clientsWorld = clientsWorld;
    }

    public int getTotalClientsInSystem() {
        return totalClientsInSystem;
    }

    public void setTotalClientsInSystem(int totalClientsInSystem) {
        this.totalClientsInSystem = totalClientsInSystem;
    }

    public int getWaitingLineSize() {
        return waitingLineSize;
    }

    public void setWaitingLineSize(int waitingLineSize) {
        this.waitingLineSize = waitingLineSize;
    }

    public Client[] getWaitingLine() {
        return waitingLine;
    }

    public void setWaitingLine(Client[] waitingLine) {
        this.waitingLine = waitingLine;
    }

    public boolean isTvInWaitingArea() {
        return tvInWaitingArea;
    }

    public void setTvInWaitingArea(boolean tvInWaitingArea) {
        this.tvInWaitingArea = tvInWaitingArea;
    }

    public boolean isCoffeeInWaitingArea() {
        return coffeeInWaitingArea;
    }

    public void setCoffeeInWaitingArea(boolean coffeeInWaitingArea) {
        this.coffeeInWaitingArea = coffeeInWaitingArea;
    }

    public Queue[] getQueues() {
        return queues;
    }

    public void setQueues(Queue[] queues) {
        this.queues = queues;
    }


    public void increaseTime() {
        // Step 1: Increase the clock
        clock++;
    
        // Step 2: Servers process requests from clients being served
        for (Queue queue : queues) {
            Client clientBeingServed = queue.getClientBeingServed();
            if (clientBeingServed != null) {
                for (Request request : clientBeingServed.getRequests()) {
                    // Check if the request is in progress, and update its state if necessary
                    if (request.getStatus() == Status.IN_PROGRESS) {
                        // Implement logic to update request status here
                        request.setStatus(Status.PROCESSED);
                    }
                }
    
                // Check if the client being served has no more requests to process
                if (clientBeingServed.getRequests().length == 0) {
                    // Remove this client from the system
                    clientBeingServed.setDepartureTime(clock);
                    clientBeingServed.setServiceTime(clock - clientBeingServed.getArrivalTime());
                    Client[] clientsHistory = queue.getClientsHistory();
                    queue.setClientsHistory(clientsHistory);
                    queue.setClientBeingServed(null);
                }
            }
        }
    
        // Step 3: Move clients from the queue to the server (FIFO policy)
        for (Queue queue : queues) {
            if (queue.getClientBeingServed() == null && queue.getClientsInQueue().length != 0) {
                Client[] clientsInQueue = queue.getClientsInQueue();
                if (clientsInQueue.length > 0) {
                    Client firstClientInQueue = clientsInQueue[0];
                    queue.setClientBeingServed(firstClientInQueue);
    
                    // Remove the first client from the array by creating a new array
                    Client[] newClientsInQueue = new Client[clientsInQueue.length - 1];
                    queue.setClientsInQueue(newClientsInQueue);
                }
            }
        }
    
        // Step 4: Check the patience value of all other clients (not being served)
        for (Queue queue : queues) {
            for (int i = 0; i < queue.getClientsInQueue().length; i++) {
                Client clientInQueue = queue.getClientsInQueue()[i];
                if (clientInQueue != null && clientInQueue.getPatience() <= 0) {
                    // Remove this client from the system
                    clientInQueue.setDepartureTime(clock);
                    clientInQueue.setServiceTime(clock - clientInQueue.getArrivalTime());
    
                    // Add the client to the clientsHistory of the specific queue
                    Client[] queueClientsHistory = queue.getClientsHistory();
                    for (int j = 0; j < queueClientsHistory.length; j++) {
                        if (queueClientsHistory[j] == null) {
                            queueClientsHistory[j] = clientInQueue;
                            break;
                        }
                    }
    
                    // Remove the client from the queue's clientsInQueue array
                    for (int j = i; j < queue.getClientsInQueue().length - 1; j++) {
                        queue.getClientsInQueue()[j] = queue.getClientsInQueue()[j + 1];
                    }
                    queue.getClientsInQueue()[queue.getClientsInQueue().length - 1] = null;
                    i--; // Decrement i to recheck the current index, as the array shifted left
                } 
                else if (clientInQueue != null) {
                    // Deduce patience value by 1
                    clientInQueue.setPatience(clientInQueue.getPatience() - 1);
                }
            }
        }
    
        // Step 5: From the waitingLine, clients enter a server queue if there is a spot available
        for (Client client : clientsWorld) {
            if (client != null && client.getArrivalTime() == clock) {
                Queue targetQueue = null;
                int targetPriority = -1;
                int targetMemberSince = Integer.MAX_VALUE;
        
                for (Queue q : queues) {
                    // Check if the client is a VIPClient and the queue is intended for VIP clients
                    if (client instanceof VIPClient && q instanceof VIPQueue) {
                        VIPQueue vipQueue = (VIPQueue) q;
                        VIPClient vipClient = (VIPClient) client;
                        int maxQueueSize = 10;
                        if (vipQueue.getClientsInQueue().length < maxQueueSize) {
                            if (vipClient.getPriority() > targetPriority || 
                                (vipClient.getPriority() == targetPriority && vipClient.getMemberSince() < targetMemberSince)) {
                                targetQueue = q;
                                targetPriority = vipClient.getPriority();
                                targetMemberSince = vipClient.getMemberSince();
                            }
                        }
                    }
                        
                        
                    else {
                        if (!(q instanceof VIPQueue)) {
                            int maxQueueSize = 10;
                            if (q.getClientsInQueue().length < maxQueueSize) {
                                int targetProcessingTime = client.getRequests()[0].calculateProcessingTime(); // Assuming the first request in the array
                                if (client.getArrivalTime() < targetPriority ||
                                    (client.getArrivalTime() == targetPriority &&
                                        (client.getYearOfBirth() < targetMemberSince ||
                                         (client.getYearOfBirth() == targetMemberSince &&
                                          client.getRequests()[0].calculateProcessingTime() < targetProcessingTime)))) {
                                    targetQueue = q;
                                    targetPriority = client.getArrivalTime();
                                    targetMemberSince = client.getYearOfBirth();
                                }
                            }
                        }
                    }
    
                    if (targetQueue != null) {
                        Client[] clientsInQueue = targetQueue.getClientsInQueue();
                        Client[] newClientsInQueue = new Client[clientsInQueue.length + 1];
                        
                        for (int i = 0; i < clientsInQueue.length; i++) {
                            newClientsInQueue[i] = clientsInQueue[i];
                        }
                    
                        newClientsInQueue[clientsInQueue.length] = client;
                        targetQueue.setClientsInQueue(newClientsInQueue);
                    }
            }
            }

        }
    }
    
        
    
    
    
    

    public void increaseTime(int time){
        for (int i = 0; i < time; i++){
            increaseTime();
        }
    }

    public Client[] getClientsBeingServed(){
        return waitingLine;
    }

    public String toString() {
        String returnedString = null;
    
        returnedString += "[WaitingLine]-";
        for (Client client : waitingLine) {
            int processingTime = 0;
            for (Request request : client.getRequests()) {
                processingTime += request.calculateProcessingTime();
            }
            returnedString += "[" + String.format("%02d", processingTime) + "]";
        }
        returnedString += "\n---\n";
    
    
        for (int i = 0; i < queues.length; i++) {
            Queue queue = queues[i];
            returnedString += "[Queue:" + (i + 1) + "]";
            Client clientBeingServed = queue.getClientBeingServed();
            if (clientBeingServed != null) {
                int remainingTime = clientBeingServed.getDepartureTime() - clock;
                returnedString += "[" + String.format("%02d", remainingTime) + "]";
                returnedString += "-----";
            } else {
                returnedString += "-----";
            }
    
            Client[] clientsInQueue = queue.getClientsInQueue();
            for (Client client : clientsInQueue) {
                if (client != null) {
                    Request clientRequest = queue.getRequestInProgress();
                    if (clientRequest != null) {
                        int estimatedTime = clientRequest.calculateProcessingTime();
                        returnedString += "[" + String.format("%02d", estimatedTime) + "]";
                    }
                }
            }
    
            returnedString += "\n";
        }

        /*
         * [WaitingLine]-[11][21][10][08][03][11][21][10][08][03]
         * ---
         * [Queue:1][09]-----[13][21][12][08][03]
         * [Queue:2][03]-----[22][11][10][18][13]
         * [Queue:3][19]-----[21][22][11][08][03]
         * [Queue:4][12]-----[13][31][10][28][13]
         * [Queue:5][03]-----[41][24][14][08][05]
         * [Queue:6][05]-----[13][21][10][38][04]
         */
        return returnedString;
        

    }

    public String toString(boolean showID) {
        String returnedBooleanID = null;

        if (showID == true) {
            returnedBooleanID += "[WaitingLine]-";
            for (Client client : waitingLine) {
                returnedBooleanID += "[" + String.format("%02d", client.getId()) + "]";
            }
            returnedBooleanID += "\n---\n";

            for (int i = 0; i < queues.length; i++) {
                Queue queue = queues[i];
                returnedBooleanID += "[Queue:" + (i + 1) + "]";
                Client clientBeingServed = queue.getClientBeingServed();
                if (clientBeingServed != null) {
                    int remainingTime = clientBeingServed.getDepartureTime() - clock;
                    returnedBooleanID += "[" + String.format("%02d", remainingTime) + "]";
                    returnedBooleanID += "-----";
                } else {
                    returnedBooleanID += "-----";
                }

                Client[] clientsInQueue = queue.getClientsInQueue();
                for (Client client : clientsInQueue) {
                    if (client != null) {
                        returnedBooleanID += "[" + String.format("%02d", client.getId()) + "]";
                    }
                }

                returnedBooleanID += "\n";
            }
        }

        return returnedBooleanID;
    }
    


        /*
         * [WaitingLine]-[37][38][39][40][41][42][43][44][45][46]
         * ---
         * [Queue:1][01]-----[07][13][19][25][31]
         * [Queue:2][02]-----[08][14][20][26][32]
         * [Queue:3][03]-----[09][15][21][27][33]
         * [Queue:4][04]-----[10][16][22][28][34]
         * [Queue:5][05]-----[11][17][23][29][35]
         * [Queue:6][06]-----[12][18][24][30][36]
         */


}