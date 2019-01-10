package cc.ccds.testsql.Quiz;

public class Question {
    private int iD;
    private String question;
    private String trueAnswer;
    private String wrongAnswer3;
    private String wrongAnswer1;
    private String wrongAnswer2;

    public Question(){
        this.question=null;
        this.trueAnswer=null;
        this.wrongAnswer3=null;
        this.wrongAnswer1=null;
        this.wrongAnswer2=null;
    }

    public Question(String question,String trueAnswer,String wrongAnswer1,String wrongAnswer2,String wrongAnswer3){
        this.question=question;
        this.trueAnswer=trueAnswer;
        this.wrongAnswer3=wrongAnswer3;
        this.wrongAnswer1=wrongAnswer1;
        this.wrongAnswer2=wrongAnswer2;
    }

    public Question(int iD,String question,String trueAnswer,String wrongAnswer1,String wrongAnswer2,String wrongAnswer3){
        this.iD=iD;
        this.question=question;
        this.trueAnswer=trueAnswer;
        this.wrongAnswer3=wrongAnswer3;
        this.wrongAnswer1=wrongAnswer1;
        this.wrongAnswer2=wrongAnswer2;
    }

    public int getiD() {
        return iD;
    }
    public void setiD(int iD) {
        this.iD = iD;
    }

    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTrueAnswer() {
        return trueAnswer;
    }
    public void setTrueAnswer(String trueAnswer) {
        this.trueAnswer = trueAnswer;
    }

    public void setWrongAnswer3(String wrongAnswer3) {
        this.wrongAnswer3 = wrongAnswer3;
    }
    public String getWrongAnswer3() {
        return wrongAnswer3;
    }

    public void setWrongAnswer1(String wrongAnswer1) {
        this.wrongAnswer1 = wrongAnswer1;
    }
    public String getWrongAnswer1() {
        return wrongAnswer1;
    }

    public void setWrongAnswer2(String wrongAnswer2) {
        this.wrongAnswer2 = wrongAnswer2;
    }
    public String getWrongAnswer2() {
        return wrongAnswer2;
    }
}
