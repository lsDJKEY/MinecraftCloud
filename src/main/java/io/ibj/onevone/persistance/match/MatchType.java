package io.ibj.onevone.persistance.match;

/**
 * Represents all different type of Pvp matches the server can host
 */
public enum MatchType {

    /**
     * Represents a potion based Pvp match
     */
    POTION("potion"),
    /**
     * Represents a soup based Pvp match
     */
    SOUP("soup");

    MatchType(String commonname){
        this.common = commonname;
    }

    private String common;

    /**
     *
     * @return Returns the DB/User friendly version of what type of match this is
     */
    public String getName(){
        return common;
    }

    public static MatchType fromName(String name){
        for(MatchType type : MatchType.values()){
            if(type.getName().equals(name)){
                return type;
            }
        }
        return null;
    }
}
