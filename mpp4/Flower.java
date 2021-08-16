import java.util.ArrayList;
import java.util.List;

public class Flower {
    private List<Double> vector = new ArrayList<>();
    private String name;

    public Flower(List<Double> vector, String name) {
        this.vector = vector;
        this.name = name;
    }

    public List<Double> getVector() {
        return vector;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Flower{" +
                "vector=" + vector +
                ", name='" + name + '\'' +
                '}';
    }
}
