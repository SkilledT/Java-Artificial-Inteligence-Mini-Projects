import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Huffman {
    //letter, code
    static Map<Character, String> mapOfCodesOfLetters = new HashMap<>();
    public static void displayHuffmanCodeAndAddCodesToMap(HuffmanNode root, String s) {
        if (root.left == null && root.right == null) {
            mapOfCodesOfLetters.put(root.c.charAt(0), s);
            System.out.println(root.c + "   |  " + s);

            return;
        }
        displayHuffmanCodeAndAddCodesToMap(root.left, s + "0");
        displayHuffmanCodeAndAddCodesToMap(root.right, s + "1");
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        var listOfLetters = loadData(input);
        int n = listOfLetters.size();

        PriorityQueue<HuffmanNode> q = new PriorityQueue<>(n, (o1, o2) -> {
            if (o1.item - o2.item == 0)
                return (int) o1.c.charAt(0) - (int) o2.c.charAt(0);
            return (int) (o1.item - o2.item);
        });

        //Dodanie wszystkich elementów do kolejki
        for (int i = 0; i < n; i++) {
            HuffmanNode hn = new HuffmanNode();

            hn.c = String.valueOf(listOfLetters.get(i).c);
            hn.item = listOfLetters.get(i).weight;

            hn.left = null;
            hn.right = null;

            q.add(hn);
        }

        HuffmanNode root = null;

        //Tworzenie nowych węzłów i łączenie ich
        while (q.size() > 1) {
            //Pobieramy 2 pierwsze elementy
            HuffmanNode x = q.poll();
            HuffmanNode y = q.poll();

            //Tworzymy nowy węzeł
            HuffmanNode f = new HuffmanNode();

            f.item = x.item + y.item;
            f.c = "" + x.c + y.c;
            f.left = x;
            f.right = y;
            root = f;
            q.add(f);
        }
        System.out.println(" Znak | kod ");
        System.out.println("--------------------");
        displayHuffmanCodeAndAddCodesToMap(root, "");
        countHuffmanCost(listOfLetters);
        countKodStalejDlugosci(listOfLetters);
        getHuffmanCode(input);
    }

    public static void getHuffmanCode(String input){
        System.out.println("Kod Huffmana");
        for (var s : input.toCharArray())
            System.out.print(mapOfCodesOfLetters.get(s) + " ");
    }

    public static void countHuffmanCost(List<Letter> letters)
    {
        int cost = 0;
        for (var letter : letters){
            cost += mapOfCodesOfLetters.get(letter.c).length() * letter.weight;
        }
        System.out.println("Huffman | Potrzebna liczba bitów: " + cost);
    }

    public static void countKodStalejDlugosci(List<Letter> letters){
        int numberOfLetters = letters.size();
        long numberOfOccurrences = letters.stream().mapToInt(e -> (int) e.weight).sum();
        double neededBits = Math.ceil(Math.log(numberOfLetters)/Math.log(2));
        double result = neededBits * numberOfOccurrences;
        System.out.println("Kod stalej dlugosci | Potrzebna ilosc bitow: " + result);
    }

    public static List<Letter> loadData(String input){
        List<String> words = new ArrayList<>();
        words.add(input);
        Map<Character, Long> charFrequency = words.stream()
                .flatMap(a -> a.chars().mapToObj(c -> (char) c))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        List<Letter> result = new ArrayList<>();
        for (var key : charFrequency.keySet()){
            Letter letter = new Letter();
            letter.c = key;
            letter.weight = charFrequency.get(key);
            result.add(letter);
        }

        return result;

    }

}

