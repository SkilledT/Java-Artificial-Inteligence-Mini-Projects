import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String path = "C:\\Users\\48536\\Desktop\\k-means\\iris_training.txt";
        //Classifier cl = new Classifier(3, path);
        while(true){
            Scanner sc = new Scanner(System.in);
            System.out.println("Wprowadz K: (max 150)");
            int k = sc.nextInt();
            new Classifier(k, path);
        }
    }
}
