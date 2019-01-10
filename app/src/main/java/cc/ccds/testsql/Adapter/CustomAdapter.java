package cc.ccds.testsql.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cc.ccds.testsql.Quiz.Question;
import cc.ccds.testsql.R;

public class CustomAdapter extends ArrayAdapter<Question> {

    private Context context;
    private int resource;
    private List<Question> questionList;
    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<Question> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.questionList=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.list_view_question,parent,false);// goi thuoc tinh tu list_view_question de dua vao trong test view,Khi attachToRoot được bật, view sẽ được gắn vào cây sau khi thực hiện inflate.
            viewHolder=new ViewHolder();
            viewHolder.setTvID((TextView)convertView.findViewById(R.id.tv_id));
            viewHolder.setTvQuestion((TextView)convertView.findViewById(R.id.tv_question));
            viewHolder.setTvTrueAswer((TextView)convertView.findViewById(R.id.tv_trueAnswer));
            viewHolder.setTvWrongAswer1((TextView)convertView.findViewById(R.id.tv_wrongAnswer1));
            viewHolder.setTvWrongAswer2((TextView)convertView.findViewById(R.id.tv_wrongAnswer2));
            viewHolder.setTvWrongAswer3((TextView)convertView.findViewById(R.id.tv_wrongAnswer3));


            convertView.setTag(viewHolder);
        }
        else {
            viewHolder =(ViewHolder) convertView.getTag();
        }
        Question question =questionList.get(position);
        viewHolder.getTvID().setText(String.valueOf(question.getiD()));
        viewHolder.getTvQuestion().setText(String.valueOf(question.getQuestion()));
        viewHolder.getTvTrueAswer().setText(String.valueOf(question.getTrueAnswer()));
        viewHolder.getTvWrongAswer1().setText(String.valueOf(question.getWrongAnswer1()));
        viewHolder.getTvWrongAswer2().setText(String.valueOf(question.getWrongAnswer2()));
        viewHolder.getTvWrongAswer3().setText(String.valueOf(question.getWrongAnswer3()));
        return convertView;
    }


}
