package io.ibj.onevone.persistance.map;

import com.sk89q.worldedit.CuboidClipboard;
import io.ibj.onevone.persistance.DNamedEntity;
import io.ibj.onevone.persistance.player.DOfflinePlayer;

import java.io.InputStream;
import java.util.UUID;

/**
 * Represents an arena map. Stores the static ID of the map, its casual name, creator, raw map data location, and rating information.
 *
 * This map should always be referenced by its UUID inside of any DB or program. From a user, it should be accessed by its casual name.
 *
 */
public interface GameMap extends DNamedEntity {

    /**
     *
     * @return  Creator of the map
     */

    public DOfflinePlayer getCreator();

    /**
     *
     * @return Actual data stream of the .schematic containing the map
     */
    public InputStream getMapStream();

    /**
     *
     * @return WE Cuboid Clipboard representation of the map
     */
    public CuboidClipboard getClip();

    /**
     * Returns a rating from 0 to 1 representing the rating of the map by users
     * @return  Map rating
     */
    public Double getRating();

    /**
     * Votes positively for this map.
     * @param voter The voter who voted up this map
     * @throws AlreadyVotedException    Is thrown when the user passed has already voted for the map
     */
    public void voteUp(UUID voter) throws AlreadyVotedException;

    /**
     * Votes negatively for this map.
     * @param voter The voter who voted up this map
     * @throws AlreadyVotedException    Is thrown when the user passed has already voted for the map
     */
    public void voteDown(UUID voter) throws AlreadyVotedException;

    /**
     *
     * @param voter The voter
     * @return  Whether the voter has already voted for this map.
     */
    public boolean hasVoted(UUID voter);

}
