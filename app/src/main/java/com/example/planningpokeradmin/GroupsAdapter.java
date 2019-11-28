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
import java.util.ArrayList;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.GroupsViewHolder>
{
    Context context;
    ArrayList<String> groups;

    public static class GroupsViewHolder extends RecyclerView.ViewHolder
    {
        public TextView groupname;

        public GroupsViewHolder(View v)
        {
            super(v);

            groupname=itemView.findViewById(R.id.groupname);
        }
    }

    public GroupsAdapter(Context context,ArrayList<String> dataset)
    {
        this.context=context;
        this.groups=dataset;
    }

    @NonNull
    @Override
    public  GroupsViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType)
    {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.listofgroups,parent,false);

        return new GroupsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupsViewHolder holder, final int position)
    {
        holder.groupname.setText(groups.get(position));

        holder.groupname.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Bundle groupnametoquestions=new Bundle();
                groupnametoquestions.putString("groupname",groups.get(position));

                Fragment questions = new QuestionsFragment();
                questions.setArguments(groupnametoquestions);
                FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.mainactivity, questions,"QuestionsFragment");
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return groups.size();
    }
}
