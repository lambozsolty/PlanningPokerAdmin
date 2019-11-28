package com.example.planningpokeradmin;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AddQuestionFragment extends Fragment
{
    TextView groupname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.fragment_add_question, container, false);

        groupname=v.findViewById(R.id.groupnameaddquestion);
        groupname.setText(getArguments().getString("groupname"));

        return v;
    }
}
