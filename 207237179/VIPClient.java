public class VIPClient extends Client implements Prioritizable {
    private int memberSince;
    private int priority;

    public int getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(int memberSince) {
        this.memberSince = memberSince;
    }

    public int getPriority(){
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public VIPClient(String firstName, String lastName, int birthYear, Gender gender, int arrivalTime, int patience, Request[] requests, int memberSince, int priority) {
        super(firstName, lastName, birthYear, gender, arrivalTime, patience, requests);
        this.memberSince = memberSince;
        this.priority = priority;
    }

    @Override

    public String toString() {
        String finalResponse = super.toString();
        finalResponse += "** VIP since : " + memberSince + "\n";
        finalResponse += "** priority : " + priority + "\n";

        return finalResponse;
    }



}