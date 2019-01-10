package cc.ccds.testsql.Adapter;

import android.widget.TextView;

public class ViewHolder {
    private TextView tvID;
    private TextView tvQuestion;
    private TextView tvTrueAswer;
    private TextView tvWrongAswer1;
    private TextView tvWrongAswer2;
    private TextView tvWrongAswer3;

    public TextView getTvID() {
        return tvID;
    }

    public TextView getTvQuestion() {
        return tvQuestion;
    }

    public TextView getTvTrueAswer() {
        return tvTrueAswer;
    }

    public TextView getTvWrongAswer1() {
        return tvWrongAswer1;
    }

    public TextView getTvWrongAswer2() {
        return tvWrongAswer2;
    }

    public TextView getTvWrongAswer3() {
        return tvWrongAswer3;
    }

    public void setTvID(TextView tvID) {
        this.tvID = tvID;
    }

    public void setTvQuestion(TextView tvQuestion) {
        this.tvQuestion = tvQuestion;
    }

    public void setTvTrueAswer(TextView tvTrueAswer) {
        this.tvTrueAswer = tvTrueAswer;
    }

    public void setTvWrongAswer1(TextView tvWrongAswer1) {
        this.tvWrongAswer1 = tvWrongAswer1;
    }

    public void setTvWrongAswer2(TextView tvWrongAswer2) {
        this.tvWrongAswer2 = tvWrongAswer2;
    }

    public void setTvWrongAswer3(TextView tvWrongAswer3) {
        this.tvWrongAswer3 = tvWrongAswer3;
    }
}
