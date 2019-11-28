package com.example.planningpokeradmin;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class QuestionsAnsweredAdapter extends RecyclerView.Adapter<QuestionsAnsweredAdapter.QuestionsAnsweredViewHolder>
{
    Context context;
    ArrayList<String> questions;

    public static class QuestionsAnsweredViewHolder extends RecyclerView.ViewHolder
    {
        public TextView questionname;

        public QuestionsAnsweredViewHolder(View v)
        {
            super(v);

            questionname=v.findViewById(R.id.questionrv);
        }
    }

    public QuestionsAnsweredAdapter(Context context,ArrayList<String> dataset)
    {
        this.context=context;
        this.questions=dataset;
    }

    @NonNull
    @Override
    public QuestionsAnsweredAdapter.QuestionsAnsweredViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.questionsanswered,parent,false);

        return new QuestionsAnsweredAdapter.QuestionsAnsweredViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final QuestionsAnsweredAdapter.QuestionsAnsweredViewHolder holder, final int position)
    {
        holder.questionname.setText(questions.get(position));

        holder.questionname.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Bundle questionnametoanswers=new Bundle();
                questionnametoanswers.putString("questionname",questions.get(position));

                Fragment toanswers=new AnswersFragment();
                FragmentTransaction transaction=((FragmentActivity)context).getSupportFragmentManager().beginTransaction();

                toanswers.setArguments(questionnametoanswers);

                transaction.replace(R.id.mainactivity,toanswers);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
    }

    @Override
    public int getItemCount()
    {
        return questions.size();
    }
}
