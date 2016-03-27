import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        try {
            int numberOfProb = Integer.parseInt(in.readLine());

            for(int i = 0; i<numberOfProb;i++){
                String problem = in.readLine();
                DesertProblem desertProblem = new DesertProblem(problem.toCharArray());
                System.out.println(desertProblem.getMinimumCost());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
