package com.example.planzy;

import java.util.ArrayList;
import java.util.List;

public class PollModel {
    public PollModel(String name, Integer totalVotes) {
        Name = name;
        this.totalVotes = totalVotes;
    }

    String Name;
    Integer totalVotes; //Number of participants that need to cast vote.
    Integer votesCommitted; //Number of participants that already casted the vote.
    ArrayList<String> hasVoted;
    //This is an ArrayList and the 'participants' are in List so it needs to be casted or converted/
    String result;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(Integer totalVotes) {
        this.totalVotes = totalVotes;
    }

    public Integer getVotesCommitted() {
        return votesCommitted;
    }

    public void setVotesCommitted(Integer votesCommitted) {
        this.votesCommitted = votesCommitted;
    }

    public List<String> getHasVoted() {
        return hasVoted;
    }

    public void setHasVoted(ArrayList<String> hasVoted) {
        this.hasVoted = hasVoted;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
