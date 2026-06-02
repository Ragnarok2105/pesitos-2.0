import java.util.ArrayList;
public class Lesson {
    private String title;
    private String intro;
    private ArrayList<Question> questions;
    public Lesson(String title, String intro) {
        this.title = title;
        this.intro = intro;
        this.questions = new ArrayList<Question>();
    }
    public void addQuestion(Question q) { questions.add(q); }
    public String getTitle() { return title; }
    public String getIntro() { return intro; }
    public ArrayList<Question> getQuestions() { return questions; }
    public int getNumberOfQuestions() { return questions.size(); }
    public Question getQuestion(int index) { return questions.get(index); }
}
