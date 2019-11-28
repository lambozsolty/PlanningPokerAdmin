package com.example.planningpokeradmin;

public class Question
{
    private String questionname;
    private String status;

    public Question(String qn)
    {
        this.questionname=qn;
    }

    public Question(){}

    public String getQuestionname()
    {
        return questionname;
    }

    public void setQuestionname(String questionname)
    {
        this.questionname = questionname;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
}
