package com.example.planningpokeradmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null)
        {
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.mainactivity, new CreateGroupFragment()).addToBackStack(null).commit();

        }
    }

    public void addGroup(View v)
    {
        EditText groupname=findViewById(R.id.groupname);

        if(groupname.getText().toString().matches(""))
        {
           Toast.makeText(this,"You should write a group name!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("Groups").child(groupname.getText().toString()).setValue(groupname.getText().toString());

            Toast.makeText(this,"Group added!", Toast.LENGTH_SHORT).show();
            groupname.setText("");
        }
    }

    public void viewGroups(View v)
    {
        Fragment viewgroups = new AllGroupsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.mainactivity, viewgroups);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void addQuestion(View v)
    {
        TextView groupname=findViewById(R.id.questionsgroupname);
        Bundle groupnametoaddquestions=new Bundle();
        groupnametoaddquestions.putString("groupname",groupname.getText().toString());

        Fragment addquestion=new AddQuestionFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();

        addquestion.setArguments(groupnametoaddquestions);

        transaction.replace(R.id.mainactivity,addquestion);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void uploadQuestion(View v)
    {
        EditText question=findViewById(R.id.questionname);
        TextView groupname=findViewById(R.id.groupnameaddquestion);

        if(question.getText().toString().matches(""))
        {
            Toast.makeText(this,"You should write a question name!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Question questioninstance=new Question();

            questioninstance.setQuestionname(question.getText().toString());
            questioninstance.setStatus("Inactive");

            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("Groups").child(groupname.getText().toString()).child("Questions").child(question.getText().toString()).setValue(questioninstance.getStatus());

            question.setText("");

            Toast.makeText(this,"Question uploaded!", Toast.LENGTH_SHORT).show();
        }
    }
}
