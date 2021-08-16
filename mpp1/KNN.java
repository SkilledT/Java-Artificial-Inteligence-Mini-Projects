import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class KNN {


    Integer K_CONSTANT = 3;
    static List<String[]> trainingSet = new ArrayList<>();
    List<String[]> testSet = new ArrayList<>();
    double testAttempts = 0;
    double correctAnswers = 0;
    static double probability;

    public KNN(Integer k_CONSTANT) {
        K_CONSTANT = k_CONSTANT;
    }

    private void loadTrainingData(String path) throws IOException{
        Files.lines(Path.of(path)).forEach(e -> {
            String[] data = e.split("\\t");
            trainingSet.add(data);
        });
    }

    private void loadTestData(String path) throws IOException{
        Files.lines(Path.of(path)).forEach(e -> {
            String[] data = e.split("\\t");
            testSet.add(data);
        });
    }

    public void calculateProbability(String pathTraining, String pathTest) throws IOException {
        loadTestData(pathTest);
        loadTrainingData(pathTraining);

        for (String[] test : testSet){
            List<KNNObject> knnObjectList = new ArrayList<>();
            for (String[] training : trainingSet){
                knnObjectList.add(createKNNObject(test, training));
            }
            trainingSet.add(test);
            String orginaldecision = test[test.length-1];
            String predictedDecision = findDecision_K_NearestNeighbour(knnObjectList, K_CONSTANT);
            System.out.println("Predicted: " + predictedDecision + " , orginal: " + orginaldecision);
            testAttempts++;
            if (orginaldecision.equals(predictedDecision)){
                correctAnswers++;
            }
        }
        probability = correctAnswers/testAttempts;
        System.out.println("Probability: " + probability*100 + "%.");
    }

    public void generateDecisionAttribute(String[] data){
        List<KNNObject> knnObjectList = new ArrayList<>();
        for (String[] training : trainingSet){
            knnObjectList.add(createKNNObject(data, training));
        }
        String predictedDecision = findDecision_K_NearestNeighbour(knnObjectList, K_CONSTANT);
        System.out.println("The flower is: " + predictedDecision + " with the " + (probability*100) + "% of probability");
    }

    private Double calculateEuklidesDistance(String[] data2, String[] data1){
        Double result = 0.0;
        for (int i = 0; i < data1.length-1; i++){
            result += Math.pow(
                    (Double.parseDouble(data1[i]) - Double.parseDouble(data2[i])),
                    2
            );
        }
        return Math.sqrt(result);
    }

    private String findDecision_K_NearestNeighbour(List<KNNObject> list, int k){
        List<KNNObject> list1 = new ArrayList<>();
        list.sort(Comparator.comparingDouble(KNNObject::getEuklidesDistance));
        for (int i = 0; i < k; i++)
            list1.add(list.get(i));
        Map<String, Long> result =
                list1.stream().collect(
                        Collectors.groupingBy(
                                KNNObject::getDecisionAttribjute, Collectors.counting()
                        )
                );
        return Collections.max(result.entrySet(), (e1, e2) -> (int) (e2.getValue() - e1.getValue())).getKey();
    }

    private KNNObject createKNNObject(String[] testData, String[] trainingData){
        return new KNNObject(trainingData[trainingData.length-1], calculateEuklidesDistance(testData, trainingData));
    }

}
