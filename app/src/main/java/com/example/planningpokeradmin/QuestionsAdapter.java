package com.example.planningpokeradmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder>
{
    Context context;
    ArrayList<Question> questions;
    private DatabaseReference mDatabase;

    public static class QuestionsViewHolder extends RecyclerView.ViewHolder
    {
        public TextView questionname;
        public TextView status;
        public Button startstop;
        public TextView groupname;

        public QuestionsViewHolder(View v)
        {
            super(v);

            questionname=v.findViewById(R.id.questiontext);
            status=v.findViewById(R.id.statusstr);
            startstop=v.findViewById(R.id.startbutton);
            groupname=v.findViewById(R.id.questionsgroupname);
        }
    }

    public QuestionsAdapter(Context context,ArrayList<Question> dataset)
    {
        this.context=context;
        this.questions=dataset;
    }

    @NonNull
    @Override
    public  QuestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.listofquestions,parent,false);

        return new QuestionsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final QuestionsViewHolder holder, final int position)
    {
        holder.questionname.setText(questions.get(position).getQuestionname());
        holder.status.setText(questions.get(position).getStatus());

        if(holder.status.getText().toString().matches("Inactive"))
        {
            holder.startstop.setText("Start");
        }
        else
        {
            holder.startstop.setText("Stop");
        }

        holder.startstop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(holder.startstop.getText().toString().matches("Start"))
                {
                    if(QuestionsFragment.getActiveStatusCount()==0)
                    {
                        holder.status.setText("Active");
                        holder.startstop.setText("Stop");

                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        mDatabase.child("Groups").child(QuestionsFragment.getGroupName()).child("Questions").child(holder.questionname.getText().toString()).setValue("Active");

                        QuestionsFragment.updateQuestions(position,"Active");
                    }
                    else
                    {
                        Toast.makeText(context,"At a moment only one question can be active!",Toast.LENGTH_SHORT).show();
                    }
                }
                else if(holder.startstop.getText().toString().matches("Stop"))
                {
                    holder.status.setText("Inactive");
                    holder.startstop.setText("Start");

                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("Groups").child(QuestionsFragment.getGroupName()).child("Questions").child(holder.questionname.getText().toString()).setValue("Inactive");

                    QuestionsFragment.updateQuestions(position,"Inactive");
                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return questions.size();
    }
}
