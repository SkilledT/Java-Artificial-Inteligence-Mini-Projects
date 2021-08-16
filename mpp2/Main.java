import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception{

        double probability;

        List<String> keys = new ArrayList<>(Perceptron.loadData("iris_test.txt").keySet());
        List<Perceptron> perceptrons = new ArrayList<>();
        for (String key : keys)
            perceptrons.add(new Perceptron(key));
        perceptrons.forEach(e -> {
            System.out.println("Name: " + e.name);
            System.out.println("Weights: " + e.weights);
            System.out.println("Theta: " + e.theta);
            System.out.println();
        });

        double attempts = 0;
        double corret = 0;
        Map<String, List<List<Double>>> data = Perceptron.loadData("iris_test.txt");
        for (String key : keys){
            for (List<Double> vec : data.get(key)){
                List<String> qualified = new ArrayList<>();
                perceptrons.forEach(e -> {
                    if (e.checkIfBelongToThisClass(vec))
                        qualified.add(e.name);
                });
                if (qualified.size() == 0){
                    qualified.add(keys.get((int)(Math.random()*3)));
                }
                if (qualified.get(0).equals(key))
                    corret++;
                attempts++;
            }
        }
        probability = corret/attempts;
        System.out.println("Probability: " + probability);

        while (true){
            System.out.println("Enter vectors");
            List<Double> vec = new ArrayList<>();
            // VC 6.9	3.1	5.4	2.1
            // VC 4.9	2.4	3.3	1.0
            // S 4.6	3.4	1.4	0.3
            // VG 7.3	2.9	6.3	1.8
            Scanner scanner = new Scanner(System.in);
            String[] vectors = scanner.nextLine().split("\\s+");
            for (String v : vectors)
                vec.add(Double.parseDouble(v));

            List<String> qualified = new ArrayList<>();
            perceptrons.forEach(e -> {
                if (e.checkIfBelongToThisClass(vec))
                    qualified.add(e.name);
            });
            if (qualified.size() == 0){
                qualified.add(keys.get((int)(Math.random()*3)));
            }
            System.out.println("Predicted class: " + qualified.get(0));
        }
    }
}
