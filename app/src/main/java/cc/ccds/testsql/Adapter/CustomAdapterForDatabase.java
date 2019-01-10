package cc.ccds.testsql.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cc.ccds.testsql.R;

public class CustomAdapterForDatabase extends ArrayAdapter<String> {
    private Context context;
    private int resource;
    private List<String> databaseList;
    private Typeface myTypeFace;
    public CustomAdapterForDatabase(@NonNull Context context, int resource,@NonNull List<String> databaseList) {
        super(context, resource,databaseList);
        this.context=context;
        this.resource=resource;
        this.databaseList=databaseList;
        myTypeFace=Typeface.createFromAsset(context.getAssets(), "font/cutecartoon.ttf");
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView viewholder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.list_view_database,parent,false);
            viewholder=(TextView)convertView.findViewById(R.id.tv_databasename);
            convertView.setTag(viewholder);
        }
        else {
            viewholder=(TextView) convertView.getTag();
        }
        String databasename=databaseList.get(position);
        viewholder.setText(databasename);
        viewholder.setTypeface(myTypeFace);
        viewholder.setTextColor(Color.parseColor("#0c0c0c"));
        return convertView;
    }
}
