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
        int pathLenght = path.length;
        if (path[pathLenght-1]=='L' ||path[pathLenght-1]=='P'|| path[pathLenght-1]=='M') {
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
                        balloonPos=nearestBalloon(foundObstacle);
                        plankPos=nearestPlank(foundObstacle);
                        canoePos=nearestCanoe(foundObstacle);
                        break;
                    case 'P':
                        balloonPos=nearestBalloon(foundObstacle);
                        plankPos=nearestPlank(foundObstacle);
                        break;
                    case 'M':
                        balloonPos=nearestBalloon(foundObstacle);
                        break;
                }

                int balloonCost = getBalloonCost(foundObstacle,balloonPos);
                int plankCost = getPlankCost(foundObstacle,plankPos);
                int canoeCost = getCanoeCost(foundObstacle,canoePos);

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

    private int getCanoeCost(int currentSpot, int canoePos) {
        int canoeCost=0;
        if (canoePos>=0)
            if(currentSpot>canoePos){
                for(int i =currentSpot;i>=canoePos;i--){
                    if(i>=0) {
                        char terrain = path[i];
                        if (terrain == 'L' || terrain == 'P' || terrain == 'M') {
                            canoeCost += crossingCost('c', true, true, true);
                        } else if (terrain == 'c') {
                            canoeCost += crossingCost(terrain, false, false, true);
                        } else {
                            canoeCost += crossingCost(terrain, false, true, true);
                        }
                    }
                }
                return canoeCost;
            }

        return Integer.MAX_VALUE;
    }

    private int getPlankCost(int currentSpot, int plankPos) {

        int plankCost=0;
        if (plankPos>=0)
            if(currentSpot>plankPos){
                for(int i =currentSpot;i>=plankPos;i--){
                    if(i>=0) {
                        char terrain = path[i];
                        if (terrain == 'L' || terrain == 'P' || terrain == 'M') {
                            plankCost += crossingCost('p', true, true, true);
                        } else if (terrain == 'p') {
                            plankCost += crossingCost(terrain, false, false, true);
                        } else {
                            plankCost += crossingCost(terrain, false, true, true);
                        }
                    }
                }
                return plankCost;
            }

        return Integer.MAX_VALUE;

    }


    private int getBalloonCost(int currentSpot, int balloonPos) {
        int balloonCost=0;
        if (balloonPos>=0)
            if(currentSpot>balloonPos){
                for(int i =currentSpot;i>=balloonPos;i--){
                    if (i>=0) {
                        char terrain = path[i];
                        if (terrain == 'L' || terrain == 'P' || terrain == 'M') {
                            balloonCost += crossingCost('b', true, true, true);
                        } else if (terrain == 'b') {
                            balloonCost += crossingCost(terrain, false, false, true);
                        } else {
                            balloonCost += crossingCost(terrain, false, true, true);
                        }
                    }
                }
                return balloonCost;
            }

        return Integer.MAX_VALUE;
    }

    private int nearestCanoe(int currentPos){
        for(int i = currentPos;i>=0;i--){
            if(path[i]=='c'){
                return i;
            }
        }
        return -1;
    }

    private int nearestPlank(int currentPos){
        for(int i = currentPos;i>=0;i--){
            if(path[i]=='p'){
                return i;
            }
        }
        return -1;

    }

    private int nearestBalloon(int currentPos){
        for(int i = currentPos;i>=0;i--){
            if(path[i]=='b'){
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
