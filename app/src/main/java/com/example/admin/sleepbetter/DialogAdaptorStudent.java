package com.example.admin.sleepbetter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class DialogAdaptorStudent extends BaseAdapter {
    Activity activity;

    private Activity context;
    private ArrayList<Dialogpojo> alCustom;
    private String sturl;


    public DialogAdaptorStudent(Activity context, ArrayList<Dialogpojo> alCustom) {
        this.context = context;
        this.alCustom = alCustom;

    }

    @Override
    public int getCount() {
        return alCustom.size();

    }

    @Override
    public Object getItem(int i) {
        return alCustom.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.row_addapt, null, true);

        TextView tvExperiment=(TextView)listViewItem.findViewById(R.id.tv_experiment);
        TextView tvOverall=(TextView)listViewItem.findViewById(R.id.tv_overall);
        TextView tvDuedate=(TextView)listViewItem.findViewById(R.id.tv_desc);
        TextView tvProof=(TextView)listViewItem.findViewById(R.id.tv_proof);
        TextView tvComment=(TextView)listViewItem.findViewById(R.id.tv_comment);

        tvExperiment.setText("Experiment : "+alCustom.get(position).getExperiments());
        tvOverall.setText("Overall Mood : "+alCustom.get(position).getOveralls() + "/5");
        tvDuedate.setText("Due Date : "+alCustom.get(position).getDuedates());
        tvProof.setText("Proof : "+alCustom.get(position).getProofs());
        tvComment.setText("Comment : "+alCustom.get(position).getComments());

        return  listViewItem;
    }

}

