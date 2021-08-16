import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Perceptron {

    double theta; // Generate theta from -1 to 1
    double alpha = 0.1;
    List<Double> weights = new ArrayList<>();
    String name;
    Map<String, List<List<Double>>> trainingData;
    Map<String, List<List<Double>>> testData;
    final String test = "C:\\Users\\48536\\Desktop\\NAI\\NAI_MPP_2\\test";
    final String training = "C:\\Users\\48536\\Desktop\\NAI\\NAI_MPP_2\\training";
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
                    System.out.println("Wektor: " + vec + " , NIE należy do: " + name + " , klucz: " + key);
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
        return net - this.theta >= 0;
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
        Map<String, List<List<Double>>> result = new HashMap<>();
        Files.walkFileTree(Path.of(path), new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
                try {
                    String content = Files.readString(file);
                    String language = file.getParent().getFileName().toString();
                    List<Double> vector = characterVector(content);
                    if (result.containsKey(language))
                        result.get(language).add(vector);
                    else {
                        List<List<Double>> languageVectors = new ArrayList<>();
                        languageVectors.add(vector);
                        result.put(language, languageVectors);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return result;
    }

    public static List<Double> characterVector(String text){
        var data = text.replaceAll("\\s+","")
                .toLowerCase();
        List<Double> resultVector = new ArrayList<>();
        for (int i = 'a'; i <= 'z'; i++){
            double occurrences = 0.0;
            for (int j = 0; j < data.length(); j++){
                if (data.charAt(j) == (char) i){
                    occurrences++;
                }
            }
            resultVector.add(occurrences);
        }
        return resultVector;
    }

    public static void printConfusionMatrix(double factPos, double wynPos, double factNeg, double wynNeg, String language){
        System.out.println(
                "Dla jezyka: " + language + '\n' +
                "Zaklasyfikowano jako -> | pozytywne | negatywne |" + '\n' +
                        "pozytywne\t\t|       " + factPos + "      | \t" + + wynNeg + '\n' +
                        "negatywne\t\t|       " + wynPos + "      | \t" + factNeg
        );
        double accuracy = (factPos + factNeg) / (factNeg + factPos + wynPos + wynNeg);
        double precision = factPos / (factPos + wynPos);
        double recall = factPos / (factPos + wynNeg);
        double fMeasure = 2 * precision * recall / (precision + recall);
        System.out.println(
                "Precyzja: " + precision + '\n' +
                        "Pelnosc: " + recall + '\n' +
                        "Dokladnosc: " + accuracy + '\n' +
                        "F-miara: " + fMeasure + '\n'
        );
    }
}
