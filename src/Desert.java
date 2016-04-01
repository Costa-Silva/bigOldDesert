
/**
 * Created by Antonio Silva 43381 && Paulo Figueira 43268
 */
public class Desert {

    private static final int LEAVING = 1;
    private static final int ENTERING = 1;
    private static final int DEFAULTCOST = 1;
    private static final int BALLOONTIME = 6;
    private static final int PLANKTIME = 5;
    private static final int CANOETIME =4;
    private static final char BALLOON= 'b';
    private static final char CANOE = 'c';
    private static final char PLANK = 'p';
    private static final char PLAIN = '_';
    private static final char LAKE = 'L';
    private static final char PIT = 'P';
    private static final char MOUNTAIN = 'M';
    private static final int BALLONPOS = 1;
    private static final int CANOEPOS = 2;
    private static final int PLANKPOS = 3;
    private static final int PLANPOS = 0;
    private char[] path;
    private long matrixPath[][];
    private long lowestValue;
    private long previousValuePlain;
    private long previousValueBalloon;
    private long previousValueCanoe;
    private long previousValuePlank;

    public Desert(char[] path){
        this.path=path;
        lowestValue=0;
        previousValuePlain = Integer.MAX_VALUE;
        previousValueBalloon = Integer.MAX_VALUE;
        previousValueCanoe = Integer.MAX_VALUE;
        previousValuePlank = Integer.MAX_VALUE;
        matrixPath = new long[4][path.length];
    }

    public long problem(){
        fillFirst(path[0],0);
        for (int i =1; i<path.length;i++){
             previousValuePlain = matrixPath[0][i-1];
             previousValueBalloon = matrixPath[1][i-1];
             previousValueCanoe = matrixPath[2][i-1];
             previousValuePlank = matrixPath[3][i-1];
            lowestValue = Math.min(Math.min(Math.min(previousValueBalloon,previousValuePlank),previousValueCanoe),previousValuePlain);
            char terrain = path[i];
            switch (terrain){
                case PLAIN:
                    processPlain(true,PLANPOS,i,previousValuePlain);
                    processPlain(false,CANOEPOS,i,previousValueCanoe);
                    processPlain(false,BALLONPOS,i,previousValueBalloon);
                    processPlain(false,PLANKPOS,i,previousValuePlank);
                    break;
                case BALLOON:
                    processPlain(true,BALLONPOS,i,previousValueBalloon);
                    processObject(BALLONPOS,CANOEPOS,i,previousValueCanoe);
                    processObject(BALLONPOS,BALLONPOS,i,previousValueBalloon);
                    processObject(BALLONPOS,PLANKPOS,i,previousValuePlank);
                    break;
                case CANOE:
                    processPlain(true,CANOEPOS,i,previousValueCanoe);
                    processObject(CANOEPOS,CANOEPOS,i,previousValueCanoe);
                    processObject(CANOEPOS,BALLONPOS,i,previousValueBalloon);
                    processObject(CANOEPOS,PLANKPOS,i,previousValuePlank);
                    break;
                case PLANK:
                    processPlain(true,PLANKPOS,i,previousValuePlank);
                    processObject(PLANKPOS,CANOEPOS,i,previousValueCanoe);
                    processObject(PLANKPOS,BALLONPOS,i,previousValueBalloon);
                    processObject(PLANKPOS,PLANKPOS,i,previousValuePlank);
                    break;
                case MOUNTAIN:
                    matrixPath[PLANPOS][i] = Integer.MAX_VALUE;
                    matrixPath[CANOEPOS][i] = Integer.MAX_VALUE;
                    matrixPath[PLANKPOS][i] = Integer.MAX_VALUE;
                    crossObstacle(BALLONPOS,i,previousValueBalloon,BALLOONTIME);
                    break;
                case PIT:
                    matrixPath[PLANPOS][i] = Integer.MAX_VALUE;
                    matrixPath[CANOEPOS][i] = Integer.MAX_VALUE;
                    crossObstacle(BALLONPOS,i,previousValueBalloon,BALLOONTIME);
                    crossObstacle(PLANKPOS,i,previousValuePlank,PLANKTIME);
                    break;
                case LAKE:
                    matrixPath[PLANPOS][i] = Integer.MAX_VALUE;
                    crossObstacle(BALLONPOS,i,previousValueBalloon,BALLOONTIME);
                    crossObstacle(CANOEPOS,i,previousValueCanoe,CANOETIME);
                    crossObstacle(PLANKPOS,i,previousValuePlank,PLANKTIME);
                    break;
            }
        }
        return Math.min(Math.min(Math.min(matrixPath[BALLONPOS][path.length-1],matrixPath[CANOEPOS][path.length-1]),matrixPath[PLANKPOS][path.length-1]),matrixPath[PLANPOS][path.length-1]);
    }

    private void crossObstacle(int objectpos,int i,long previousValue,int crosstime){
        if (previousValue != Integer.MAX_VALUE) {
            matrixPath[objectpos][i]= previousValue+crosstime;
        }else{
            matrixPath[objectpos][i] = Integer.MAX_VALUE;
        }
    }
    private boolean isObstacle(char obs){
        return (obs == MOUNTAIN || obs == LAKE || obs == PIT);
    }

    //Quando encontra um objeto, calcula o minimo entre continuar com o objeto anterior ou apanhar este novo objeto
    private long getBestChoice(long lowestValue,long previousValue,int i){
        long value1 = previousValue + ENTERING+LEAVING+DEFAULTCOST;
        long value2 = lowestValue+DEFAULTCOST+LEAVING;
        if (isObstacle(path[i-1]))
           value2+=ENTERING;
        return Math.min(value1,value2);
    }

    private void processPlain(boolean exp,int planepos,int i,long previousValue){
        if(exp) {
            if (previousValuePlain != Integer.MAX_VALUE) {
                matrixPath[PLANPOS][i] = previousValuePlain + DEFAULTCOST;
            } else {
                matrixPath[PLANPOS][i] = lowestValue + DEFAULTCOST + LEAVING;
            }
        }else {
            if (previousValue != Integer.MAX_VALUE) {
                matrixPath[planepos][i] = previousValue + DEFAULTCOST + ENTERING + LEAVING;
            } else {
                matrixPath[planepos][i] = Integer.MAX_VALUE;
            }
        }
    }

    private void processObject(int plane,int planepos,int i,long previousValue){
        if (plane!=planepos) {
            if (previousValue != Integer.MAX_VALUE) {
                matrixPath[planepos][i] = previousValue + ENTERING + LEAVING + DEFAULTCOST;
            } else {
                matrixPath[planepos][i] = Integer.MAX_VALUE;
            }
        }else {
            if (previousValue!=Integer.MAX_VALUE){
                matrixPath[planepos][i] = getBestChoice(lowestValue,previousValue,i);
            }else{
                if (isObstacle(path[i-1]))
                    matrixPath[planepos][i] = lowestValue + ENTERING+DEFAULTCOST+LEAVING;
                else matrixPath[planepos][i] = lowestValue + DEFAULTCOST+LEAVING;
            }
        }
    }

    private void fillFirst(char c,int pos) {
        matrixPath[PLANPOS][pos] = DEFAULTCOST;
        switch (c){
            case PLAIN:
                matrixPath[BALLONPOS][pos]=Integer.MAX_VALUE;
                matrixPath[CANOEPOS][pos]=Integer.MAX_VALUE;
                matrixPath[PLANKPOS][pos]=Integer.MAX_VALUE;
                break;
            case BALLOON:
                matrixPath[BALLONPOS][pos] = DEFAULTCOST + LEAVING;
                matrixPath[CANOEPOS][pos]=Integer.MAX_VALUE;
                matrixPath[PLANKPOS][pos]=Integer.MAX_VALUE;
                break;
            case CANOE:
                matrixPath[BALLONPOS][pos]=Integer.MAX_VALUE;
                matrixPath[CANOEPOS][pos] = DEFAULTCOST + LEAVING;
                matrixPath[PLANKPOS][pos]=Integer.MAX_VALUE;
                break;
            case PLANK:
                matrixPath[BALLONPOS][pos]=Integer.MAX_VALUE;
                matrixPath[CANOEPOS][pos]=Integer.MAX_VALUE;
                matrixPath[PLANKPOS][pos] = DEFAULTCOST + LEAVING;
                break;
        }
    }
}
