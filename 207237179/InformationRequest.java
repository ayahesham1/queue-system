public class InformationRequest extends Request {
    private String[] questions;

    public InformationRequest (String description, int priority, int difficulty, String[] questions) {
        super();
        setDescription(description);
        setPriority(priority);
        setDifficulty(difficulty);
        setFactor(1);
        setStatus(Status.NEW);
        this.questions = questions;


        ///Information request: Proessing time = Request difficulty x factor x numberOfQuestions
    }


    @Override
    public int calculateProcessingTime() {
        return (getDifficulty() * getFactor() * questions.length);
    }

    public String[] getQuestions() {
        return questions;
    }

    public void setQuestions(String[] questions){
        this.questions = questions;
    }
}