package com.acuo.common.model.results;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;


@Data
@EqualsAndHashCode(callSuper = true)
public class ReutersError extends Error {
    private Set<ReutersMessage> reutersMessages = new HashSet<>();
    public Set<ReutersMessage> getReutersMessages() { return reutersMessages; }
    public void addReutersMessage(ReutersMessage reutersMessage) { this.reutersMessages.add(reutersMessage); }
    public void removeAllReutersMessages() {reutersMessages.clear(); }
}

