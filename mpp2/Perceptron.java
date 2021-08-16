import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Perceptron {

    double theta; // Generate theta from -1 to 1
    double alpha = 0.1;
    List<Double> weights = new ArrayList<>();
    String name;
    Map<String, List<List<Double>>> trainingData;
    Map<String, List<List<Double>>> testData;
    final String test = "iris_test.txt";
    final String training = "iris_training.txt";
    double probability;

    public Perceptron(String name) throws Exception {
        this.trainingData = loadData(training);
        this.testData = loadData(test);
        this.name = name;
        for (int i = 0; i < trainingData.get(name).get(0).size(); i++)
            this.weights.add((Math.random()*3)-1); // Generate weight from -1 to 1
        theta = (Math.random()*3)-1;
        train();
    }

    private void train(){
        int learningRate = 5000;
        for (int i = 0; i < learningRate; i++){
            List<String> keysOfTrainingData = new ArrayList<>(trainingData.keySet());
            for (String key : keysOfTrainingData){
                List<List<Double>> values = trainingData.get(key);
                for (List<Double> vec : values){
                    if (key.equals(name))
                        modifyWeights(vec, 1);
                    else
                        modifyWeights(vec, 0);
                }
            }
        }
    }

    public void calculateProbability() {
        double attempts = 0.0;
        double predicted = 0.0;
        List<String> keysOfTestData = new ArrayList<>(testData.keySet());
        for (String key : keysOfTestData){
            List<List<Double>> values = testData.get(key);
            for (List<Double> vec : values){
                if (checkIfBelongToThisClass(vec) == key.equals(name)) {
                    predicted++;
                    //System.out.println("Wektor: " + vec + " , należy ("+ checkIfBelongToThisClass(vec) +") do: " + name + " , klucz: " + key);
                } else {
                    //System.out.println("Wektor: " + vec + " , NIE należy do: " + name + " , klucz: " + key);
                }
                attempts++;
            }
        }
        System.out.println(weights);
        System.out.println(theta);
        probability = predicted/attempts;
    }

    public boolean checkIfBelongToThisClass(List<Double> vec){
        double net = 0.0;
        for (int i = 0; i < weights.size(); i++){
            net += (vec.get(i) * weights.get(i));
        }
        return net - this.theta > 0;
    }

    // net = W^(T)*X - theta >= 0
    public void modifyWeights(List<Double> vectors, int correctAnswer){
        int decision = 0;
        do {
            double net = 0.0;
            for (int i = 0; i < weights.size(); i++){
                net += (vectors.get(i) * weights.get(i));
            }
            decision = net - this.theta >= 0 ? 1 : 0;
            // W' = W + (correctAnswer - decision)*alpha*X
            for (int i = 0; i < weights.size(); i++){
                double neWeight = weights.get(i) + ((correctAnswer - decision) * alpha * vectors.get(i));
                weights.set(i, neWeight);
            }
            //this.theta = this.theta + (correctAnswer - decision) * alpha * -1;
        } while (decision != correctAnswer);
    }

    public static Map<String, List<List<Double>>> loadData(String path) throws Exception{
        List<String> data = Files.lines(Path.of(path)).collect(Collectors.toList());
        Map<String, List<List<Double>>> result =
                data.stream().collect(
                        Collectors.groupingBy(e -> {
                            String[] items = e.split("\\s+");
                            return items[items.length-1];
                        }, Collectors.mapping(e -> {
                            String[] items = e.split("\\s+");
                            List<Double> vectors = new ArrayList<>();
                            for (int i = 0; i < items.length -1; i++)
                                vectors.add(Double.parseDouble(items[i]));
                            return vectors;
                        }, Collectors.toList()))
                );
        return result;
    }
}
