import java.util.ArrayList;
import java.util.List;

public class QueueSystem {
    private int clock;
    private int totalWaitingTime;
    private List<Client> clientsWorld;
    private int totalClientsInSystem;
    private List<Client> waitingLine;
    private boolean tvInWaitingArea;
    private boolean coffeeInWaitingArea;
    private List<Queue> queues;

    public QueueSystem(int waitingLineSize, boolean tvInWaitingArea, boolean coffeeInWaitingArea) {
        this.clock = 0;
        this.tvInWaitingArea = tvInWaitingArea;
        this.coffeeInWaitingArea = coffeeInWaitingArea;
        this.clientsWorld = new ArrayList<>();
        this.waitingLine = new ArrayList<>();
        this.queues = new ArrayList<>();

        // Initialize queues with a flexible list
        for (int i = 0; i < waitingLineSize; i++) {
            queues.add(new Queue("Queue" + i, i));
        }

        // Adjust client patience based on waiting area amenities
        applyWaitingAreaBenefits();
    }

    private void applyWaitingAreaBenefits() {
        if (tvInWaitingArea || coffeeInWaitingArea) {
            for (Client client : clientsWorld) {
                if (client != null) {
                    int patienceBoost = 0;
                    if (tvInWaitingArea) patienceBoost += 20;
                    if (coffeeInWaitingArea) patienceBoost += 15;
                    client.setPatience(client.getPatience() + patienceBoost);
                }
            }
        }
    }

    public void increaseTime() {
        clock++;

        // Process requests for clients being served
        processClientRequests();

        // Move clients from the queue to the server
        moveClientsToServer();

        // Check the patience of waiting clients
        checkPatienceOfWaitingClients();

        // Add new clients from the world to the queue
        addNewClientsToQueue();
    }

    private void processClientRequests() {
        for (Queue queue : queues) {
            Client clientBeingServed = queue.getClientBeingServed();
            if (clientBeingServed != null) {
                for (Request request : clientBeingServed.getRequests()) {
                    if (request.getStatus() == Status.IN_PROGRESS) {
                        request.setStatus(Status.PROCESSED);
                    }
                }

                if (clientBeingServed.getRequests().length == 0) {
                    clientBeingServed.setDepartureTime(clock);
                    clientBeingServed.setServiceTime(clock - clientBeingServed.getArrivalTime());
                    queue.addClientToHistory(clientBeingServed);
                    queue.setClientBeingServed(null);
                }
            }
        }
    }

    private void moveClientsToServer() {
        for (Queue queue : queues) {
            if (queue.getClientBeingServed() == null && !queue.getClientsInQueue().isEmpty()) {
                Client firstClientInQueue = queue.getClientsInQueue().remove(0);
                queue.setClientBeingServed(firstClientInQueue);
            }
        }
    }

    private void checkPatienceOfWaitingClients() {
        for (Queue queue : queues) {
            List<Client> clientsInQueue = queue.getClientsInQueue();
            for (int i = 0; i < clientsInQueue.size(); i++) {
                Client client = clientsInQueue.get(i);
                if (client.getPatience() <= 0) {
                    // Remove impatient client
                    client.setDepartureTime(clock);
                    client.setServiceTime(clock - client.getArrivalTime());
                    queue.addClientToHistory(client);
                    clientsInQueue.remove(i);
                    i--; // Adjust index due to removal
                } else {
                    client.setPatience(client.getPatience() - 1);
                }
            }
        }
    }

    private void addNewClientsToQueue() {
        for (Client client : clientsWorld) {
            if (client != null && client.getArrivalTime() == clock) {
                Queue targetQueue = findTargetQueue(client);
                if (targetQueue != null) {
                    targetQueue.getClientsInQueue().add(client);
                }
            }
        }
    }

    private Queue findTargetQueue(Client client) {
        Queue targetQueue = null;
        if (client instanceof VIPClient) {
            targetQueue = findVIPQueue((VIPClient) client);
        } else {
            targetQueue = findStandardQueue(client);
        }
        return targetQueue;
    }

    private Queue findVIPQueue(VIPClient client) {
        VIPQueue bestQueue = null;
        for (Queue queue : queues) {
            if (queue instanceof VIPQueue && queue.getClientsInQueue().size() < 10) {
                VIPQueue vipQueue = (VIPQueue) queue;
                if (bestQueue == null || client.getPriority() > ((VIPClient) bestQueue).getPriority()) {
                    bestQueue = vipQueue;
                }
            }
        }
        return bestQueue;
    }

    private Queue findStandardQueue(Client client) {
        Queue bestQueue = null;
        for (Queue queue : queues) {
            if (!(queue instanceof VIPQueue) && queue.getClientsInQueue().size() < 10) {
                if (bestQueue == null || queue.getClientsInQueue().size() < bestQueue.getClientsInQueue().size()) {
                    bestQueue = queue;
                }
            }
        }
        return bestQueue;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[WaitingLine]-");
        for (Client client : waitingLine) {
            int totalProcessingTime = client.getRequests().stream().mapToInt(Request::calculateProcessingTime).sum();
            sb.append("[").append(String.format("%02d", totalProcessingTime)).append("]");
        }
        sb.append("\n---\n");

        for (int i = 0; i < queues.size(); i++) {
            Queue queue = queues.get(i);
            sb.append("[Queue:").append(i + 1).append("]");
            Client clientBeingServed = queue.getClientBeingServed();
            if (clientBeingServed != null) {
                int remainingTime = clientBeingServed.getDepartureTime() - clock;
                sb.append("[").append(String.format("%02d", remainingTime)).append("]-----");
            } else {
                sb.append("-----");
            }

            for (Client client : queue.getClientsInQueue()) {
                sb.append("[").append(String.format("%02d", client.getId())).append("]");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String toString(boolean showID) {
        if (showID) {
            return toString();
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("[WaitingLine]-");
            for (Client client : waitingLine) {
                sb.append("[").append(String.format("%02d", client.getPatience())).append("]");
            }
            sb.append("\n---\n");

            for (int i = 0; i < queues.size(); i++) {
                Queue queue = queues.get(i);
                sb.append("[Queue:").append(i + 1).append("]-----");

                for (Client client : queue.getClientsInQueue()) {
                    sb.append("[").append(String.format("%02d", client.getPatience())).append("]");
                }
                sb.append("\n");
            }
            return sb.toString();
        }
    }
}
