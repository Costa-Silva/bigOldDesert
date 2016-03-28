import java.util.HashMap;
import java.util.Map;

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
    private boolean gotObject;
    private char[] path;
    private Map<Character,Integer> objects;

    public DesertProblem(char[] path){
        objects = new HashMap<Character,Integer>();
        this.path=path;
        this.gotObject=false;
        this.cost=0;

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

                int balloonCost = getObjectCost(foundObstacle,balloonPos,BALLOON);
                int plankCost = getObjectCost(foundObstacle,plankPos,PLANK);
                int canoeCost = getObjectCost(foundObstacle,canoePos,CANOE);

                int artifactPos = 0;
                int artifactCost = 0;
                if(balloonCost<plankCost && balloonCost<canoeCost){
                    artifactPos=balloonPos;
                    artifactCost=balloonCost;
                }else if(plankCost<balloonCost &&plankCost<canoeCost){
                    artifactPos=plankPos;
                    artifactCost=plankCost;
                }else {
                    artifactPos=canoePos;
                    artifactCost=canoeCost;
                }
                currentSpot=artifactPos;
                cost+=artifactCost;
            }else {
                currentSpot = -1;
            }
        }

        return cost;
    }

    private int getObjectCost(int currentSpot, int objectPos,char objectChar) {
        int objectCost=0;
        if (objectPos>=0)
            if(currentSpot>objectPos){
                for(int i =currentSpot;i>=objectPos;i--){
                    if(i>=0) {
                        char terrain = path[i];
                        if (terrain == 'L' || terrain == 'P' || terrain == 'M') {
                            objectCost += crossingCost(objectChar, true, true, true);
                        } else if (terrain == objectChar) {
                            objectCost += crossingCost(terrain, false, false, true);
                        } else {
                            objectCost += crossingCost(terrain, false, true, true);
                        }
                    }
                }
                return objectCost;
            }

        return Integer.MAX_VALUE;
    }


    private int nearestObjectPosition(int currentPos, char objectChar){

        for(int i = currentPos;i>=0;i--){
            if(path[i]==objectChar){
                return i;
            }
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



    private int crossingCost(char object,boolean usingObject, boolean hadObject,boolean leavingWithObject){


        if (usingObject) {

            return objects.get(object);
        }

        int crossCost=DEFAULTCOST;

        if (hadObject)
            crossCost++;
        if (leavingWithObject)
            crossCost++;

        return crossCost;
    }



}
