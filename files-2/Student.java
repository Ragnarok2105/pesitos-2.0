import java.util.ArrayList;

/**
 * Student class — represents one user of Pesitos and tracks ALL their progress.
 *
 * Now it owns three kinds of data:
 *   1. progress[][]  — the most recent attempt for every question
 *                      (0 = unanswered, 1 = correct, -1 = wrong)
 *   2. bestCorrect[] — the best score the student has ever gotten per lesson
 *   3. achievements  — badges the student has unlocked
 *
 * Uses only AP CSA concepts:
 *   - Encapsulation (private fields + getters)
 *   - A constructor
 *   - Arrays, 2D (jagged) arrays, and a boolean[]
 *   - An ArrayList<String>
 *   - Loops, conditionals, Math, and casting
 *
 * No inheritance, no abstract classes, no polymorphism.
 */
public class Student {

    private String name;

    // Most recent attempt for every question, lesson by lesson.
    // progress[i] is one row = lesson i. progress[i][j] = question j.
    // 0 = unanswered, 1 = correct, -1 = wrong.
    private int[][] progress;

    // One slot per lesson.
    private int[] bestCorrect;     // best number of correct answers so far
    private int[] lessonTotals;    // how many questions that lesson has
    private boolean[] completed;   // has the student finished this lesson once?

    // Badges the student has unlocked (no duplicates).
    private ArrayList<String> achievements;

    // Constructor.
    // questionsPerLesson[i] = how many questions lesson i has.
    // We use it to build the jagged 2D progress array at the right size.
    public Student(String name, int[] questionsPerLesson) {
        this.name = name;
        int n = questionsPerLesson.length;

        this.bestCorrect = new int[n];
        this.lessonTotals = new int[n];
        this.completed = new boolean[n];
        this.achievements = new ArrayList<String>();

        // Build the 2D progress array. Each row can be a different length.
        this.progress = new int[n][];
        for (int i = 0; i < n; i++) {
            this.progress[i] = new int[questionsPerLesson[i]];  // all start at 0
            this.lessonTotals[i] = questionsPerLesson[i];
        }
    }

    public String getName() {
        return name;
    }

    // ----- PROGRESS (per question) -----

    // Clears the most-recent-attempt record for a lesson.
    // Called right before replaying a lesson.
    public void resetLessonProgress(int lessonIndex) {
        for (int j = 0; j < progress[lessonIndex].length; j++) {
            progress[lessonIndex][j] = 0;
        }
    }

    // Records the result of a single question (1 if correct, -1 if wrong).
    public void recordAnswer(int lessonIndex, int questionIndex, boolean correct) {
        if (correct) {
            progress[lessonIndex][questionIndex] = 1;
        } else {
            progress[lessonIndex][questionIndex] = -1;
        }
    }

    // 0, 1, or -1 for one specific question.
    public int getQuestionResult(int lessonIndex, int questionIndex) {
        return progress[lessonIndex][questionIndex];
    }

    public int getLessonQuestionCount(int lessonIndex) {
        return progress[lessonIndex].length;
    }

    // How many questions in this lesson were correct on the most recent attempt.
    public int getCorrectInLesson(int lessonIndex) {
        int count = 0;
        for (int j = 0; j < progress[lessonIndex].length; j++) {
            if (progress[lessonIndex][j] == 1) {
                count++;
            }
        }
        return count;
    }

    // How many questions in this lesson have been answered (right or wrong).
    public int getAnsweredInLesson(int lessonIndex) {
        int count = 0;
        for (int j = 0; j < progress[lessonIndex].length; j++) {
            if (progress[lessonIndex][j] != 0) {
                count++;
            }
        }
        return count;
    }

    // ----- BEST SCORES + COMPLETION -----

    // Called after a lesson finishes. Keeps the BEST score if replayed.
    public void recordResult(int lessonIndex, int correct, int total) {
        lessonTotals[lessonIndex] = total;
        completed[lessonIndex] = true;
        if (correct > bestCorrect[lessonIndex]) {
            bestCorrect[lessonIndex] = correct;
        }
        checkAchievements();
    }

    public int getBestCorrect(int lessonIndex) {
        return bestCorrect[lessonIndex];
    }

    public int getLessonTotal(int lessonIndex) {
        return lessonTotals[lessonIndex];
    }

    public boolean hasCompleted(int lessonIndex) {
        return completed[lessonIndex];
    }

    public int getLessonsCompleted() {
        int count = 0;
        for (int i = 0; i < completed.length; i++) {
            if (completed[i]) {
                count++;
            }
        }
        return count;
    }

    public int getTotalLessons() {
        return completed.length;
    }

    // Sum of best correct answers across every lesson played.
    public int getTotalCorrect() {
        int sum = 0;
        for (int i = 0; i < bestCorrect.length; i++) {
            sum += bestCorrect[i];
        }
        return sum;
    }

    // Sum of questions across every lesson played.
    public int getTotalQuestions() {
        int sum = 0;
        for (int i = 0; i < lessonTotals.length; i++) {
            sum += lessonTotals[i];
        }
        return sum;
    }

    // Overall accuracy as a whole-number percentage (0 if nothing played yet).
    public int getOverallPercentage() {
        int totalQ = getTotalQuestions();
        if (totalQ == 0) {
            return 0;
        }
        return (int) Math.round((double) getTotalCorrect() / totalQ * 100.0);
    }

    // ----- ACHIEVEMENTS -----

    public ArrayList<String> getAchievements() {
        return achievements;
    }

    // Adds a badge only if the student doesn't already have it.
    private void unlock(String badge) {
        if (!achievements.contains(badge)) {
            achievements.add(badge);
        }
    }

    // Decides which badges the student has earned. Called after each lesson.
    private void checkAchievements() {
        if (getLessonsCompleted() >= 1) {
            unlock("\uD83C\uDF31 Primer paso: completaste tu primera leccion");
        }

        for (int i = 0; i < bestCorrect.length; i++) {
            if (completed[i] && lessonTotals[i] > 0 && bestCorrect[i] == lessonTotals[i]) {
                unlock("\uD83C\uDF1F Puntaje perfecto: 100% en una leccion");
                break;
            }
        }

        if (getLessonsCompleted() >= getTotalLessons() / 2) {
            unlock("\uD83D\uDCDA A mitad de camino: completaste la mitad de las lecciones");
        }

        if (getLessonsCompleted() >= getTotalLessons()) {
            unlock("\uD83C\uDFC6 Maestro del dinero: completaste todas las lecciones");
        }
    }
}
