import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Period;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Classifier {

    private int K;
    private String path;
    private Map<List<Double>, List<Flower>> centroids = new HashMap<>();

    public Classifier(int k, String path) {
        K = k;
        this.path = path;

        try {
            initializeCentroids();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeCentroids() throws IOException {
        List<Flower> flowers = loadVectors(path);
        for (int i = 0; i < K; i++){
            List<Flower> tmp = new ArrayList();
            tmp.add(flowers.get(i));
            centroids.put(flowers.get(i).getVector(), tmp);
        }
        List<List<Double>> keys = new ArrayList<>(centroids.keySet());
        for (int i = K; i < flowers.size(); i++){
            centroids.get(keys.get((int)(Math.random()*K))).add(flowers.get(i));
        }
        matchPoints();
    }

    private void matchPoints() throws IOException {
        var oldKeys = new ArrayList<>(centroids.keySet());
        var newKeys = new ArrayList<>(countNewCentroids(oldKeys));
        var vectors = loadVectors(path);
        int iteration = 0;
        System.out.println("Iteration: " + iteration++);
        printSumOfSquaresOfAllClasses(centroids);
        printNumberOfGroups(centroids);
        while (!Arrays.equals(newKeys.toArray(), oldKeys.toArray())){
            Map<List<Double>, List<Flower>> groups = new HashMap<>();
            for (var vec : vectors){
                var group = chooseCorrectClass(newKeys, vec);
                if (!groups.containsKey(group)){
                    List<Flower> list = new ArrayList<>();
                    list.add(vec);
                    groups.put(group,list);
                } else {
                    groups.get(group).add(vec);
                }
            }

            for (int i = 0; i < newKeys.size(); i++){
                if (!groups.containsKey(newKeys.get(i))){
                    List<Flower> flowers = new ArrayList<>();
                    var x = centroids.get(oldKeys.get(i)).get(0);
                    for (var keyInn : groups.keySet()){
                        if (groups.get(keyInn).contains(x))
                            groups.get(keyInn).remove(x);
                    }
                    groups.put(newKeys.get(i), flowers);
                    groups.get(newKeys.get(i)).add(x);
                }
            }
            centroids = groups;
            oldKeys = new ArrayList<>(centroids.keySet());
            newKeys = new ArrayList<>(countNewCentroids(oldKeys));
            System.out.println("Iteration: " + iteration++);
            printSumOfSquaresOfAllClasses(centroids);
            printNumberOfGroups(centroids);
        }
    }

    private void printNumberOfGroups(Map<List<Double>, List<Flower>> cen){
        try {
            for (var key : cen.keySet()){
                Map<String, Long> groups = cen.get(key).stream().map(Flower::getName).collect(
                        Collectors.groupingBy(
                                Function.identity(), Collectors.counting()
                        )
                );
                Map<String, Long> fileGroups = loadVectors(path).stream().collect(
                        Collectors.groupingBy(e ->
                                e.getName(), Collectors.counting()
                        )
                );
                double finalEntropy = 0.0;
                double mianownik = 0.0;
                for (var innerKey : fileGroups.keySet()){
                    if (!groups.containsKey(innerKey))
                        continue;
                    mianownik += (double) groups.get(innerKey);
                }

                for (var innerKey : fileGroups.keySet()){
                    if (!groups.containsKey(innerKey))
                        continue;
                    //double liczebnoscWPliku = (double) fileGroups.get(innerKey);
                    double liczebnoscWZbiorze = (double) groups.get(innerKey);
                    double x = liczebnoscWZbiorze/mianownik;
                    double result = (-1)*(x * (Math.log(x) / Math.log(2)));
                    finalEntropy += result;
                }
                System.out.println("Centroid: " + key + ", liczebność grupy łącznie: " + cen.get(key).size() + ", z rodzieleniem na grupy: " + groups + " , Entropia: " + finalEntropy);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private List<Double> chooseCorrectClass(List<List<Double>> keys, Flower point){
        List<Double> correctKey = keys.get(0);
        Double distance = computeDistanceTwoPoints(keys.get(0), point.getVector());
        for (List<Double> key : keys){
            if (computeDistanceTwoPoints(key, point.getVector()) < distance){
                distance = computeDistanceTwoPoints(key, point.getVector());
                correctKey = key;
            }
        }
        return correctKey;
    }

    private void printSumOfSquaresForEveryClass(Map<List<Double>, List<Flower>> cen){
        for (List<Double> key : cen.keySet()){
            double distance = 0.0;
            for (Flower vec : cen.get(key))
                distance += computeDistanceTwoPoints(key, vec.getVector());
            System.out.println("Centroid: " + key + ", suma odległości punktów: " + distance);
        }
    }

    private void printSumOfSquaresOfAllClasses(Map<List<Double>, List<Flower>> cen){
        double distance = 0.0;
        for (List<Double> key : cen.keySet()){
            for (Flower vec : cen.get(key))
                distance += computeDistanceTwoPoints(key, vec.getVector());
        }
        System.out.println("suma odległości punktów: " + distance);
    }

    private Double computeDistanceTwoPoints(List<Double> centroid, List<Double> point){
        // sqrt(sum((xi-yi)^2))
        Double distance = 0.0;
        for (int i = 0; i < centroid.size(); i++){
            distance += Math.pow((centroid.get(i) - point.get(i)), 2);
        }
        Double sqrtOfDistance = Math.sqrt(distance);
        return sqrtOfDistance;
    }

    private List<List<Double>> countNewCentroids(List<List<Double>> keys){
        List<List<Double>> newKeys = new ArrayList<>();
        for (List<Double> key : keys){
            List<Flower> centroidPoints = centroids.get(key);
            List<Double> newCentroid = AverageValueOfVectors(centroidPoints.stream().map(Flower::getVector).collect(Collectors.toList()));
            newKeys.add(newCentroid);
        }
        return newKeys;
    }

    private List<Double> AverageValueOfVectors(List<List<Double>> vectors){
        List<Double> centroid = new ArrayList<>();
        for (int i = 0; i < vectors.size(); i++){
            for (int j = 0; j < vectors.get(i).size(); j++){
                if (i == 0){
                    centroid.add(vectors.get(i).get(j)/vectors.size());
                } else {
                    centroid.set(j, centroid.get(j) + vectors.get(i).get(j)/vectors.size());
                }
            }
        }
        return centroid;
    }

    public List<Flower> loadVectors(String path) throws IOException {
        List<String> data = Files.lines(Path.of(path)).collect(Collectors.toList());
        Collections.shuffle(data);
        return data.stream().map(e -> {
                    String[] items = e.split("\\s+");
                    String name = items[items.length -1];
                    List<Double> vectors = new ArrayList<>();
                    for (int i = 0; i < items.length -1; i++)
                        vectors.add(Double.parseDouble(items[i]));
                    Flower flower = new Flower(vectors, name);
                    return flower;
        }).collect(Collectors.toList());
    }


}
