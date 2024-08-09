public class BuyingProducts extends Request {
    private String[] itemsToBuy;

    public BuyingProducts (String description, int priority, int difficulty, String[] itemsToBuy) {
        super();
        setDescription(description);
        setPriority(priority);
        setDifficulty(difficulty);
        setFactor(2); 
        setStatus(Status.NEW);
        this.itemsToBuy = itemsToBuy;


    }

    @Override 
    public int calculateProcessingTime() {
        return (getDifficulty() * getFactor() * itemsToBuy.length);
    }

    public String[] getItemsToBuy(){
        return itemsToBuy;
    }

    public void setItemsToBuy(String[] itemsToBuy){
        this.itemsToBuy = itemsToBuy;
    }



}