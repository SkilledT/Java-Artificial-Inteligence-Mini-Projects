import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception{

        double probability;

        List<String> keys = new ArrayList<>(Perceptron.loadData("C:\\Users\\48536\\Desktop\\NAI\\NAI_MPP_2\\training").keySet());
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
        double correct = 0;
        Map<String, List<List<Double>>> data = Perceptron.loadData("C:\\Users\\48536\\Desktop\\NAI\\NAI_MPP_2\\test");
        for (String name : keys) {
            double positiveQualified = 0.0;
            double negativeQualified = 0.0;
            double factPositiveQualified = 0.0;
            double factNegativeQualified = 0.0;
            for (String key : keys) {

                for (List<Double> vec : data.get(key)) {
                    List<String> qualified = new ArrayList<>();
                    perceptrons.forEach(e -> {
                        if (e.checkIfBelongToThisClass(vec))
                            qualified.add(e.name);
                    });
                    if (qualified.size() == 0) {
                        qualified.add(keys.get((int) (Math.random() * 3)));
                    }
                    if (qualified.get(0).equals(key)) {
                        correct++;
                    } else {
                        //System.out.println("Przewidziano: " + qualified.get(0) + ", Faktycznie: " + key);
                    }
                    attempts++;
                    if (qualified.get(0).equals(name) && data.get(name).contains(vec))
                        factPositiveQualified++;
                    if (!qualified.get(0).equals(name) && data.get(name).contains(vec))
                        negativeQualified++;
                    if (!qualified.get(0).equals(name) && !data.get(name).contains(vec))
                        factNegativeQualified++;
                    if (qualified.get(0).equals(name) && !data.get(name).contains(vec))
                        positiveQualified++;
                }
            }
            Perceptron.printConfusionMatrix(factPositiveQualified,
                    positiveQualified,
                    factNegativeQualified,
                    negativeQualified,
                    name);
        }
        probability = correct/attempts;
        System.out.println("Procent prawidlowo zaklasyfikowanych przypadkow: " + probability*100);

        while (true){
            System.out.println("Enter vectors");
            List<Double> vec;
            Scanner scanner = new Scanner(System.in);
            String vectors = JOptionPane.showInputDialog("wpisz tutaj tekst");
            vec = Perceptron.characterVector(vectors);

            List<String> qualified = new ArrayList<>();
            List<Double> finalVec = vec;
            perceptrons.forEach(e -> {
                if (e.checkIfBelongToThisClass(finalVec))
                    qualified.add(e.name);
            });
            if (qualified.size() == 0){
                qualified.add(keys.get((int)(Math.random()*3)));
            }
            System.out.println("Predicted class: " + qualified.get(0));
        }
    }
}
