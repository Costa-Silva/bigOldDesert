import java.util.HashMap;
import java.util.Map;

/**
 * Created by Antonio on 26/03/16.
 */
public class DesertProblem {

    private boolean gotObject;
    private char[] path;

    private static final int BALLOONTIME = 6;
    private static final int PLANKTIME = 5;
    private static final int CANOETIME =4;

    private CrossingCost crossingCost;


    private int cost;

    public DesertProblem(char[] path){
        this.path=path;
        this.gotObject=false;
        this.cost=0;
        crossingCost = new CrossingCost();
    }

    public void inutilReferences(){

        Map<String,Integer> terrain;

        Map<String,Integer> objects;
        terrain = new HashMap<>();
        objects = new HashMap<>();


        objects.put("c",4);
        objects.put("p",5);
        objects.put("b",6);

        terrain.put("_",1);
        terrain.put("L",2);
        terrain.put("P",3);
        terrain.put("M",4);
    }

    public int getMinimumCost(){

        int currentSpot;

        for(currentSpot=path.length-1; currentSpot>=0;currentSpot--) {

            int foundObstacle = searchNextObstacle(currentSpot);

            int balloonPos =-1;
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
        if (canoePos>0)
        if(currentSpot>canoePos){
            for(int i =currentSpot;i>=canoePos;i--){
               if(i>0) {
                   char terrain = path[i];
                   if (terrain == 'L' || terrain == 'P' || terrain == 'M') {
                       canoeCost += crossingCost.crossingCost('c', true, true, true);
                   } else if (terrain == 'p') {
                       canoeCost += crossingCost.crossingCost(terrain, false, false, true);
                   } else {
                       canoeCost += crossingCost.crossingCost(terrain, false, true, true);
                   }
               }
            }
            return canoeCost;
        }

        return Integer.MAX_VALUE;
    }

    private int getPlankCost(int currentSpot, int plankPos) {

        int plankCost=0;
        if (plankPos>0)
        if(currentSpot>plankPos){
            for(int i =currentSpot;i>=plankPos;i--){
                if(i>0) {
                    char terrain = path[i];
                    if (terrain == 'L' || terrain == 'P' || terrain == 'M') {
                        plankCost += crossingCost.crossingCost('p', true, true, true);
                    } else if (terrain == 'p') {
                        plankCost += crossingCost.crossingCost(terrain, false, false, true);
                    } else {
                        plankCost += crossingCost.crossingCost(terrain, false, true, true);
                    }
                }
            }
            return plankCost;
        }

        return Integer.MAX_VALUE;

    }


    private int getBalloonCost(int currentSpot, int balloonPos) {
        int balloonCost=0;
        if (balloonPos>0)
        if(currentSpot>balloonPos){
            for(int i =currentSpot;i>=balloonPos;i--){
               if (i>0) {
                   char terrain = path[i];
                   if (terrain == 'L' || terrain == 'P' || terrain == 'M') {
                       balloonCost += crossingCost.crossingCost('b', true, true, true);
                   } else if (terrain == 'b') {
                       balloonCost += crossingCost.crossingCost(terrain, false, false, true);
                   } else {
                       balloonCost += crossingCost.crossingCost(terrain, false, true, true);
                   }
               }
            }
            return balloonCost;
        }

        return Integer.MAX_VALUE;
    }

    private int nearestCanoe(int currentPos){
        for(int i = currentPos;i>0;i--){
            if(path[i]=='c'){
                return i;
            }
        }
        return -1;
    }

    private int nearestPlank(int currentPos){
        for(int i = currentPos;i>0;i--){
            if(path[i]=='p'){
                return i;
            }
        }
        return -1;

    }

    private int nearestBalloon(int currentPos){
        for(int i = currentPos;i>0;i--){
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


}
