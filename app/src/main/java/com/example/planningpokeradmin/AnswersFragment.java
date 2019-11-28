package com.example.planningpokeradmin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AnswersFragment extends Fragment
{
    String question;
    String groupname;

    ArrayList<Answer> answers=new ArrayList<>();
    private RecyclerView recyclerview;
    private RecyclerView.Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.fragment_answers, container, false);

        question=getArguments().getString("questionname");
        groupname=QuestionsFragment.getGroupName();

        loadData();

        recyclerview=v.findViewById(R.id.answersrecyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.addItemDecoration(new DividerItemDecoration(v.getContext(), DividerItemDecoration.VERTICAL));

        return v;
    }

    private void loadData()
    {
        answers.clear();

        FirebaseDatabase.getInstance().getReference().child("Answers").child(groupname).child(question).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Answer answer=new Answer();
                    answer.setName(snapshot.getKey());
                    answer.setAnswer(snapshot.getValue().toString());

                    answers.add(answer);
                }

                adapter=new AnswerAdapter(getActivity(),answers);
                recyclerview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}
