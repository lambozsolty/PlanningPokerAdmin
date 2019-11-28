package com.example.planningpokeradmin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuestionsFragment extends Fragment
{
    private static TextView groupname;
    Button addquestion;
    String groupchild;
    Button answers;
    private static ArrayList<Question> questions=new ArrayList<>();
    private RecyclerView recyclerview;
    private RecyclerView.Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.fragment_questions, container, false);
        groupchild=getArguments().getString("groupname");
        groupname=v.findViewById(R.id.questionsgroupname);
        answers=v.findViewById(R.id.answers);

        groupname.setText(groupchild);
        loadData();

        addquestion=v.findViewById(R.id.addquestion);

        recyclerview=v.findViewById(R.id.questionsrecyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.addItemDecoration(new DividerItemDecoration(v.getContext(), DividerItemDecoration.VERTICAL));

        answers.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Fragment answeredquestions=new AnsweredQuestionsFragment();
                FragmentTransaction transaction= getActivity().getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.mainactivity,answeredquestions);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return v;
    }

    private void loadData()
    {
        questions.clear();

        FirebaseDatabase.getInstance().getReference().child("Groups").child(groupchild).child("Questions").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Question question=new Question();

                    question.setQuestionname(snapshot.getKey());
                    question.setStatus(snapshot.getValue().toString());

                    questions.add(question);
                }

                adapter=new QuestionsAdapter(getActivity(),questions);
                recyclerview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public static String getGroupName()
    {
        return groupname.getText().toString();
    }

    public static void updateQuestions(int index,String status)
    {
        questions.get(index).setStatus(status);
    }

    public static int getActiveStatusCount()
    {
        int counter=0;

        for(int i=0;i<questions.size();i++)
        {
            if(questions.get(i).getStatus().matches("Active"))
            {
                counter++;
            }
        }

        return counter;
    }
}
