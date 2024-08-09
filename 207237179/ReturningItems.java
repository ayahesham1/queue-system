public class ReturningItems extends Request{
    private String[] itemsToReturn;

    public ReturningItems (String description, int priority, int difficulty, String[] itemsToReturn) {
        super();
        setDescription(description);
        setPriority(priority);
        setDifficulty(difficulty);
        setFactor(3);
        setStatus(Status.NEW);
        this.itemsToReturn = itemsToReturn;
        
    }

    @Override
    public int calculateProcessingTime() {
        return (getDifficulty() * getFactor() * itemsToReturn.length);
    }

    public String[] getItemsToReturn() {
        return itemsToReturn;
    }

    public void setItemsToReturn(String[] itemStrings){
        this.itemsToReturn = itemsToReturn;
    }


}