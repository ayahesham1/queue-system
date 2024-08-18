import java.util.ArrayList;
import java.util.List;

public class Queue {
    private String serverName;
    private int queueSize;
    private Client clientBeingServed;
    private Request requestInProgress;
    private int processingStartTime;
    private List<Client> clientsHistory;
    private List<Client> clientsInQueue;

    public Queue(String serverName, int queueSize) {
        this.serverName = serverName;
        this.queueSize = queueSize;
        this.clientsHistory = new ArrayList<>();
        this.clientsInQueue = new ArrayList<>();
    }

    // Getter and Setter for serverName
    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    // Getter and Setter for queueSize
    public int getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    // Getter and Setter for clientBeingServed
    public Client getClientBeingServed() {
        return clientBeingServed;
    }

    public void setClientBeingServed(Client clientBeingServed) {
        this.clientBeingServed = clientBeingServed;
    }

    // Getter and Setter for requestInProgress
    public Request getRequestInProgress() {
        return requestInProgress;
    }

    public void setRequestInProgress(Request requestInProgress) {
        this.requestInProgress = requestInProgress;
    }

    // Getter and Setter for processingStartTime
    public int getProcessingStartTime() {
        return processingStartTime;
    }

    public void setProcessingStartTime(int processingStartTime) {
        this.processingStartTime = processingStartTime;
    }

    // Methods for managing clients in queue
    public void addClientToQueue(Client client) {
        if (clientsInQueue.size() < queueSize) {
            clientsInQueue.add(client);
        } else {
            System.out.println("Queue is full!");
        }
    }

    public void removeClientFromQueue(Client client) {
        clientsInQueue.remove(client);
    }

    public List<Client> getClientsInQueue() {
        return clientsInQueue;
    }

    // Methods for managing clients history
    public void addClientToHistory(Client client) {
        clientsHistory.add(client);
    }

    public List<Client> getClientsHistory() {
        return clientsHistory;
    }

    // toString method for general queue display
    @Override
    public String toString() {
        StringBuilder queueRepString = new StringBuilder("[Queue:" + serverName + "]");
        if (clientBeingServed != null) {
            queueRepString.append("[").append(clientBeingServed.getId()).append("]");
        } else {
            queueRepString.append("[None]");
        }
        queueRepString.append("-----");

        for (Client client : clientsInQueue) {
            queueRepString.append("[").append(client.getId()).append("]");
        }
        return queueRepString.toString();
    }

    // toString with optional showing of client IDs or remaining times
    public String toString(boolean showID) {
        StringBuilder queueRepString = new StringBuilder("[Queue:" + serverName + "]");
        if (showID) {
            queueRepString.append("-----");
            for (Client client : clientsInQueue) {
                queueRepString.append("[").append(client.getId()).append("]");
            }
        } else {
            queueRepString.append("-----");
            if (clientBeingServed != null) {
                int remainingTime = calculateRemainingTime(clientBeingServed);
                queueRepString.append("[").append(String.format("%02d", remainingTime)).append("]");
            }

            for (Client client : clientsInQueue) {
                if (client != null) {
                    int estimatedTime = requestInProgress != null
                            ? requestInProgress.calculateProcessingTime()
                            : 0;
                    queueRepString.append("[").append(String.format("%02d", estimatedTime)).append("]");
                }
            }
        }
        return queueRepString.toString();
    }

    private int calculateRemainingTime(Client client) {
        if (client.getDepartureTime() > 0) {
            return client.getDepartureTime() - client.getArrivalTime();
        }
        return 0;
    }
}
