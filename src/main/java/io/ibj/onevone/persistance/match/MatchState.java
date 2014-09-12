package io.ibj.onevone.persistance.match;

/**
 * Represents what state a match is in.
 */
public enum MatchState {
    /**
     * Represents when a match is being first created and loaded with initial details. The players have not been teleported.
     * Due to the nature of how a PVP is started, this is a very temporary state, and should only last a few milliseconds.
     */
    FORMATION(0),
    /**
     * Represents when a Pvp match is about to begin. The players have been moved to their servers.
     */
    INITIATION(1),
    /**
     * Represents when a Pvp match is in progress, and the players have started fighting.
     */
    INPROGRESS(2),
    /**
     * Represents when a pvp match is finished, and the match result is locked. Hosting server should have fallen into FREE
     */
    ENDED(3);

    MatchState(int level){
        this.level = level;
    }

    private int level;

    public int getLevel(){
        return level;
    }

    public static MatchState fromLevel(int level){
        for(MatchState t : MatchState.values()){
            if(t.getLevel()==level){
                return t;
            }
        }
        return null;
    }

}
