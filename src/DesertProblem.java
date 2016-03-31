import java.util.*;

/**
 * Created by Antonio on 26/03/16.
 */
public class DesertProblem {

    private static final int DEFAULTCOST = 1;
    private static final int BALLOONTIME = 6;
    private static final int PLANKTIME = 5;
    private static final int CANOETIME =4;
    private static final char BALLOON= 'b';
    private static final char CANOE = 'c';
    private static final char PLANK = 'p';
    private int cost;
    private char currentObject;
    private boolean gotObject;
    private char[] path;
    private Map<Character,Integer> objects;
    private Map<Integer,SortedSet<Integer>> costValues;

    public DesertProblem(char[] path){
        objects = new HashMap<Character,Integer>();
        this.path=path;
        this.gotObject=false;
        currentObject=' ';
        this.cost=0;

        costValues= new TreeMap<Integer, SortedSet<Integer>>();

        objects.put('c',CANOETIME);
        objects.put('p',PLANKTIME);
        objects.put('b',BALLOONTIME);
    }


    public int getMinimumCost(){

        int currentSpot;
        int pathLength = path.length;
        if (path[pathLength-1]=='L' ||path[pathLength-1]=='P'|| path[pathLength-1]=='M') {
            gotObject = true;
        }

        for(currentSpot=path.length-1; currentSpot>=0;currentSpot--) {
        costValues.put(currentSpot,new TreeSet<Integer>());

            int foundObstacle = searchNextObstacle(currentSpot);
            int balloonPos = -1;
            int plankPos = -1;
            int canoePos = -1;

            if(foundObstacle>0){
                switch (path[foundObstacle]){

                    case 'L':
                        balloonPos=nearestObjectPosition(foundObstacle,BALLOON);
                        plankPos=nearestObjectPosition(foundObstacle,PLANK);
                        canoePos=nearestObjectPosition(foundObstacle,CANOE);
                        break;
                    case 'P':
                        balloonPos=nearestObjectPosition(foundObstacle,BALLOON);
                        plankPos=nearestObjectPosition(foundObstacle,PLANK);
                        break;
                    case 'M':
                        balloonPos=nearestObjectPosition(foundObstacle,BALLOON);
                        break;
                }

                getObjectCost(foundObstacle,balloonPos,BALLOON);
                getObjectCost(foundObstacle,plankPos,PLANK);
                getObjectCost(foundObstacle,canoePos,CANOE);

                     int artifactPos = 0;
                if(balloonPos>plankPos && balloonPos>canoePos){
                    artifactPos=balloonPos;
                }else if(plankPos>balloonPos &&plankPos>canoePos){
                    artifactPos=plankPos;
                }else {
                    artifactPos=canoePos;
                }

                currentSpot=artifactPos;
                cost= costValues.get(artifactPos).first();
            }else {
                currentSpot = -1;
            }
        }

        return cost;
    }

    private void getObjectCost(int currentSpot, int objectPos,char objectChar) {
        int objectCost=0;
        if (objectPos>=0) {
            if (currentSpot > objectPos) {
                for (int i = currentSpot; i >= objectPos; i--) {
                    if (i >= 0) {
                        char terrain = path[i];
                        if (terrain == 'L' || terrain == 'P' || terrain == 'M') {
                            if (canUse(objectChar,terrain))
                            objectCost += crossingCost(objectChar,terrain, true, true, true);
                            else {
                                objectCost=-1;
                            }
                            break;
                        } else if (terrain == objectChar) {
                            if (objectPos == 0)
                                objectCost += crossingCost(objectChar,terrain, false, false, true);
                            else {
                                char previousTerrain = path[i - 1];
                                if (previousTerrain == 'L' || previousTerrain == 'P' || previousTerrain == 'M')
                                    objectCost += crossingCost(objectChar,terrain, false, true, true);
                                else {
                                    objectCost += crossingCost(objectChar,terrain, false, false, true);
                                }
                            }

                        } else {
                            objectCost += crossingCost(objectChar,terrain, false, true, true);
                        }
                    }
                }
                int instantCost = 0;
                if (objectCost<0)
                    instantCost = Integer.MAX_VALUE;
                else instantCost = cost+objectCost;

                if (costValues.containsKey(objectPos)) {
                    if (costValues.get(objectPos) != null) {
                        costValues.get(objectPos).add(instantCost);
                    } else {
                        TreeSet<Integer> set = new TreeSet<Integer>();
                        set.add(instantCost);
                        costValues.put(objectPos, set);
                    }
                }else {
                    TreeSet<Integer> set = new TreeSet<Integer>();
                    set.add(instantCost);
                    costValues.put(objectPos,set);
                }

            }
        }
    }


    private int nearestObjectPosition(int currentPos, char objectChar){

        for(int i = currentPos;i>=0;i--){

            if (canUse(objectChar,path[i])){
            if(path[i]==objectChar)
                return i;
            }else break;
        }
        return -1;
    }




    private int searchNextObstacle(int currentPos) {

        for (int i = currentPos;i>=0;i--){
            char obstacle=path[i];
            if (obstacle=='L' ||obstacle=='P'||obstacle=='M'){
                if(!gotObject){
                    gotObject=true;
                    cost++;
                }
                return i;
            }
            gotObject=false;
            cost++;
        }
        return -1;
    }



    private int crossingCost(char object,char terrain,boolean usingObject, boolean hadObject,boolean leavingWithObject){


        if (usingObject) {
            if(canUse(object,terrain))
            return objects.get(object);
            else return -1;
        }

        int crossCost=DEFAULTCOST;

        if (hadObject)
            crossCost++;
        if (leavingWithObject)
            crossCost++;

        return crossCost;
    }

    private boolean canUse(char object, char terrain) {
        switch (object){
            case 'b':
                    return true;
            case 'p':
                if (terrain == 'P' || terrain == 'L')
                    return true;
                else if(terrain=='M') return false;
                break;
            case 'c':
                if (terrain=='L')
                    return true;
                else if (terrain=='M' || terrain == 'P')return false;
                break;
        }
        return false;
    }


}
