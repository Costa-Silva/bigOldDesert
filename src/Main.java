import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        try {
            int numberOfProb = Integer.parseInt(in.readLine());
            for(int i = 0; i<numberOfProb;i++){
                String problem = in.readLine();
                Desert desertProblem = new Desert(problem.toCharArray());

                System.out.println(desertProblem.problem());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
