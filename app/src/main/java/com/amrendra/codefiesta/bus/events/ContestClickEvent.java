package com.amrendra.codefiesta.bus.events;

import com.amrendra.codefiesta.model.Contest;

/**
 * Created by Amrendra Kumar on 07/05/16.
 */
public final class ContestClickEvent {
    Contest contest;

    public ContestClickEvent(Contest contest) {
        this.contest = contest;
    }

    public Contest getContest() {
        return contest;
    }
}
