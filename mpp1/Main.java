import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String pathTraining = "C:\\Users\\48536\\Desktop\\iris_training.txt";
        String pathTest = "C:\\Users\\48536\\Desktop\\iris_test.txt";


        try {
            Integer k = new Scanner(System.in).nextInt();
            KNN knn = new KNN(k);
            knn.calculateProbability(pathTraining, pathTest);
            while (true){
                String data[] = new Scanner(System.in).nextLine().split("\\t");
                knn.generateDecisionAttribute(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
