package com.acuo.common.model.results;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Data
public class ReutersError extends Error {
    private Set<ReutersMessage> reutersMessages = new HashSet<>();
    public Set<ReutersMessage> getReutersMessages() { return reutersMessages; }
    public void addReutersMessage(ReutersMessage reutersMessage) { this.reutersMessages.add(reutersMessage); }
    public void removeAllReutersMessages() {reutersMessages.clear(); }
}

@Data
public class ReutersMessage {
    private String errorType;
    private String errorMessage;
}