package cc.ccds.testsql.Score;

import java.util.Date;

public class score {
    private int id;
    private Date date;
    private int score;
    private int timePlay;

    public score(){
        this.date=null;
        this.score=0;
        this.timePlay=0;
    }
    public score(Date date,int score,int timePlay){
        this.date=date;
        this.score=score;
        this.timePlay = timePlay;
    }

    public Date getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setScore(int score){
        this.score=score;
    }
    public void setId(int id){
        this.id=id;
    }

    public int getTimePlay() {
        return timePlay;
    }

    public void setTimePlay(int timePlay) {
        this.timePlay = timePlay;
    }
}
