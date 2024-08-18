import java.util.List;

public class VIPClient extends Client implements Prioritizable {

    private int memberSince;
    private int priority;

    // Constructor chaining to reduce redundancy
    public VIPClient(String firstName, String lastName, int birthYear, Gender gender, int arrivalTime, int patience, List<Request> requests, int memberSince, int priority) {
        super(firstName, lastName, birthYear, gender, arrivalTime, patience, requests);
        this.memberSince = memberSince;
        this.priority = Math.max(0, priority); // Ensure priority is non-negative
    }

    public VIPClient(String firstName, String lastName, int birthYear, Gender gender, int patience, int memberSince, int priority) {
        this(firstName, lastName, birthYear, gender, 0, patience, null, memberSince, priority);
    }

    // Getters and setters
    public int getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(int memberSince) {
        this.memberSince = memberSince;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = Math.max(0, priority); // Ensure priority is non-negative
    }

    public void adjustPriorityBasedOnTenure(int currentYear) {
        int yearsOfMembership = currentYear - memberSince;
        if (yearsOfMembership > 10) {
            priority += 2;
        } else if (yearsOfMembership > 5) {
            priority += 1;
        }
    }

    @Override
    public String toString() {
        StringBuilder finalResponse = new StringBuilder(super.toString());
        finalResponse.append("** VIP since: ").append(memberSince).append("\n");
        finalResponse.append("** Priority: ").append(priority).append("\n");
        return finalResponse.toString();
    }

    @Override
    public void increasePriority(int value) {
        priority = Math.max(priority + value, 0); // Ensure priority stays non-negative
    }

    @Override
    public void decreasePriority(int value) {
        priority = Math.max(priority - value, 0);
    }
}
