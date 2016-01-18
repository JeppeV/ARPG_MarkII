package generator.generators;

import generator.standard.Constants;
import generator.standard.Coordinates;
import generator.standard.Dungeon;
import generator.standard.Region;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Jeppe Vinberg on 06-01-2016.
 */
public class ConnectionGenerator {

    private ArrayList<Region> regions;
    private HashMap<Region, Boolean> merged;
    private Random random;


    public ConnectionGenerator(){
        this.regions = new ArrayList<>();
        this.random = new Random();
        this.merged = new HashMap<>();
    }

    private void init(Dungeon dungeon){
        // Get regions from dungeon
        for(Region r : dungeon.getRegions()){
            merged.put(r, false);
        }
    }

    public Dungeon generateConnections(Dungeon dungeon){
        init(dungeon);
        //all connectors and the regions they connect
        HashMap<Coordinates, ArrayList<Region>> allConnectors = findAllConnectors(dungeon);
        //connectors currently being considered and corresponding unmerged regions
        HashMap<Coordinates, Region> currentConnectors = new HashMap<>();
        //connectors to be removed
        ArrayList<Coordinates> obsoleteConnectors = new ArrayList<>();
        Region currentRegion, otherRegion;

        //select random starting region:
        ArrayList<Region> value = allConnectors.get(getRandomKey(allConnectors));
        currentRegion = value.get(random.nextInt(value.size()));

        //while there are still connectors to consider
        while(allConnectors.size() != 0){
            //current node has been merged:
            merged.put(currentRegion, true);
            //find all connectors connected to current region
            //here we check that one of the regions on either side of the connector is the current region,
            //and that the other region has no already been merged. this indicates a new connector
            //then we store the coordinates of the connector, and the region that has not been merged
            for(Coordinates c : allConnectors.keySet()){
                Region region1 = allConnectors.get(c).get(0);
                Region region2 = allConnectors.get(c).get(1);
                if(region1.equals(currentRegion) && !merged.get(region2)){
                    currentConnectors.put(c, region2);
                }else if(region2.equals(currentRegion) && !merged.get(region1)){
                    currentConnectors.put(c, region1);
                }
            }

            //choose random connector and open it up
            Coordinates randomC = getRandomKey(currentConnectors);
            otherRegion = currentConnectors.get(randomC);
            dungeon.setTile(randomC.getX(), randomC.getY(), Constants.FLOOR);
            dungeon.setVisited(randomC.getX(), randomC.getY(), true);

            //remove connectors between the two rooms from the connector sets
            obsoleteConnectors.clear();

            for(Coordinates c : currentConnectors.keySet()){
                if(currentConnectors.get(c).equals(otherRegion)){
                    obsoleteConnectors.add(c);
                }
            }
            for(Coordinates c : obsoleteConnectors){
                allConnectors.remove(c);
                currentConnectors.remove(c);
            }
            currentRegion = otherRegion;
        }

        return dungeon;
    }

    private HashMap<Coordinates,ArrayList<Region>> findAllConnectors(Dungeon dungeon){
        HashMap<Coordinates,ArrayList<Region>> result = new HashMap<>();
        ArrayList<Region> temp;
        Coordinates c;
        Region north, east, south, west;
        for(int x = 0; x < dungeon.getWidthInTiles(); x++){
            for(int y = 0; y < dungeon.getHeightInTiles(); y++){
                if(dungeon.getVisited(x, y)) continue;
                c = new Coordinates(x, y);
                temp = new ArrayList<>();
                north = dungeon.getRegionAt(new Coordinates(x, y-1));
                south = dungeon.getRegionAt(new Coordinates(x, y+1));
                west = dungeon.getRegionAt(new Coordinates(x-1, y));
                east = dungeon.getRegionAt(new Coordinates(x+1, y));

                if(regionsAreDifferent(north, south)){
                    temp.add(north);
                    temp.add(south);
                    result.put(c, temp);
                }else if(regionsAreDifferent(west, east)){
                    temp.add(west);
                    temp.add(east);
                    result.put(c, temp);
                }
            }
        }
        return result;
    }

    private boolean regionsAreDifferent(Region r1, Region r2){
        if(r1 != null && r2 != null){
            return !r1.equals(r2);
        }
        return false;
    }

    private Coordinates getRandomKey(HashMap<Coordinates, ?> map){
        List<Coordinates> keys = new ArrayList<>(map.keySet());
        Coordinates randomKey = keys.get( random.nextInt(keys.size()) );
        return randomKey;
    }





}
