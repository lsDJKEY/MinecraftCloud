package io.ibj.onevone.generation;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

/**
 * Created by Joe on 8/22/2014.
 */
public class SchematicChunkGenerator extends ChunkGenerator {

    public SchematicChunkGenerator(CuboidClipboard clip){
        if(clip.getHeight() > 255){
            throw new IllegalArgumentException("The clip passed is too high to generate a world out of.");
        }
        this.clip = clip;
        int height = clip.getHeight();
        lowBound = 128-(height/2);
        highBound = lowBound + height;
    }

    public boolean canSpawn(World w, int x, int z){
        return true;
    }

    int lowBound;
    int highBound;

    CuboidClipboard clip;


    public byte[][] generateBlockSections(World w, Random r, int x, int z, BiomeGrid b){
        //256 world height
        byte[][] ret = new byte[16][]; //Initial Generation Array
        if((x*16 > clip.getSize().getX() && z*16 > clip.getSize().getZ()) //Too far ouside of the clip
                || x < 0 || z < 0){ //x or z are in the negative
            return ret; //If this chunk is completely outside of the clip range
        }
        for(int i = 0; i<16; i++){  //For each chunk slice
            if((i*16)<lowBound || (i*16)+15 > highBound){   //Make sure this slice isn't out of range (above or below)
                continue;   //Skip this slice
            }
            //We now need to go through the 16x16x16 slice
            for(int y = i*16; y< ((i+1)*16); y++){  //For each y altitude within the slice
                for(int xb = x*16; xb<(x+1)*16; xb++){  //For each x block within the altitude in the chunk
                    for(int zb = z*16; zb<(z+1)*16; zb++){  //For each z block within the altitude in the chunk
                        BaseBlock block = clip.getBlock(new Vector(xb,y+lowBound,zb));   //Retrieve the block at this location, with corrected Y
                        int id;
                        if(block == null){  //If no block was found at this location
                            id = 0; //Make it air
                        }
                        else
                        {
                            id = block.getId(); //Otherwise copy over the ID
                        }
                        ret[y >> 4][((y & 0xF) << 8) | (zb << 4) | xb] = (byte)id;  //Assign the ID of the block to the resultant array
                    }
                }
            }
        }
        return ret;
    }
}
