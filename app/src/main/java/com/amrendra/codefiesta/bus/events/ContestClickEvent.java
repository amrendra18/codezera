package com.amrendra.codefiesta.bus.events;

/**
 * Created by Amrendra Kumar on 07/05/16.
 */
public final class ContestClickEvent {
    int contestId;
    String title;

    public ContestClickEvent(int contestId, String title) {
        this.contestId = contestId;
        this.title = title;
    }

    public int getContestId() {
        return contestId;
    }

    public String getTitle() {
        return title;
    }
}
