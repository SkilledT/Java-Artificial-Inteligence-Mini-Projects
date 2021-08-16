## MPP 1
Dane wejściowe:

Dane treningowe – plik iris_training.txt

Dane testowe – plik iris_test.txt

 

Program musi wczytać dane z podanego pliku tekstowego. Zakładamy, ze:
Atrybut decyzyjny znajduje się w ostatniej kolumnie.
Wszystkie atrybuty poza decyzyjnym są numeryczne.
Program musi akceptować dowolną liczbę atrybutów warunkowych, tzn. nie może zakładać, że ich jest ustalona liczba.
Następnie program wczyta wartość parametru k od użytkownika, a potem zaklasyfikuje wszystkie przykłady wczytane z pliku tekstowego zawierającego zbiór testowy algorytmem k-NN.
Jako wynik ma wypisać liczbę prawidłowo zaklasyfikowanych przykładów oraz dokładność eksperymentu wyrażoną w procentach.
Program musi umożliwiać wielokrotne ręczne wpisanie wektora atrybutów i wypisać dla takiego wektora jego wynik klasyfikacji k-NN.
Opcjonalnie można dodać wykres (np. w Excelu) zależności uzyskanej dokładności od k oraz krótka dyskusję.
Nie można używać żadnych bibliotek ML, wszystko ma być zaimplementowane od zera w pętlach, if-ach, odległość trzeba liczyć za pomocą dzialań arytmetycznych, etc.

## MPP 2

 

Zbudować jednowarstwową sieć perceptronową dyskretną rozpoznającą irysy.

 
Dane wejściowe:

Dane treningowe – plik iris_training.txt

Dane testowe – plik iris_test.txt


Program musi wczytać dane z podanego pliku tekstowego. Zakładamy, ze:

Atrybut decyzyjny znajduje się w ostatniej kolumnie.
Wszystkie atrybuty poza decycyjnym są numeryczne.

Algorytmem delty trenujemy pierwszy perceptron, który rozróżnia klasę Iris-setosa (wyjście 1) od dwóch pozostałych klas (wyjście 0).

Algorytmem delty trenujemy drugi perceptron, który rozróżnia klasę Iris-versicolor (1) od dwóch pozostałych klas (0).

Algorytmem delty trenujemy trzeci perceptron, który rozróżnia klasę Iris-virginica (1) od dwóch pozostałych klas (0).

Sieć będzie się składać z powyższych trzech perceptronów.

Sieć testujemy na danych z pliku testowego. Jako wynik wypisujemy:
Wagi i próg każdego perceptronu
Liczbę prawidłowo zaklasyfikowanych przykładów testowych oraz dokładność eksperymentu wyrażoną w procentach.

Potem wczytujemy nowe kwiatki do rozpoznawania. Program musi umożliwiać wielokrotne ręczne wpisanie wektora atrybutów i wypisać dla takiego wektora jego wynik klasyfikacji.

Nie można używać żadnych bibliotek ML, wszystko ma być zaimplementowane od zera w pętlach, if-ach, wyjście perceptronu trzeba liczyć za pomocą dzialań arytmetycznych, etc.

## MPP3

Dane wejściowe (należy samemu przygotować):

1. Utworzyć katalog na dane.

2. W tym katalogu tworzymy kilka (K>=3) podkatalogów nazwanych nazwami języków (np. czeski, słowacki ...)

3. W każdym z nich umieszczamy po 10 tekstów trenujących ściągniętych np. z wikipedii w odpowiednich językach (w alfabetach łacińskich). Minimalna długość – 2 akapity.

Oczywiście w momencie pisania programu nie powinno być wiadome ile i jakie będą języki.

4. W momencie uruchomienia sieć perceptronów będzie używała tych tekstów jako danych trenujących.

5. Utworzyć podobny katalog z tekstami testowymi.

Opis programu:

Użyjemy 1-warstwowej sieci neuronowej do klasyfikowania języków naturalnych tekstów.

Bierzemy dokument w dowolnym języku (w alfabecie łacińskim) z pliku ".txt", wyrzucamy wszystkie znaki poza literami alfabetu angielskiego (ascii) i przerabiamy na 26-elementowy wektor proporcji liter (czyli: jaka jest proporcja 'a', 'b', etc.).

Okazuje sie, ze taki wektor rozkładu znaków wystarcza do rozróżniania języka naturalnego dokumentu tekstowego, nawet dla tak podobnych języków jak np. czeski i słowacki.

Tworzymy więc jedną warstwę K perceptronów (gdzie K to liczba języków) i uczymy każdego perceptrona rozpoznawania "jego" język.


Uczenie/testowanie perceptronów – dwa warianty:

przeprowadzamy jak w poprzednim projekcie, tzn z dyskretną binarną (0-1) funkcją aktywacji, a podczas testowania wybieramy wyjście z odpowiedzią 1.
Odpowiedzi kodujemy na (-1 vs 1 zamiast 0-1), normalizujemy wektory wejść i wag perceptronów. Jako funkcję aktywacji użyjemy funkcji identycznościowej y=net, po każdej iteracji wektor wag ponownie normalizujemy, a uczenie przerywamy, kiedy błąd |d-y| spadnie poniżej wybranego progu (np. 0,01). Podczas testowania  zwycięża perceptron z najwyższą wartością wyjścia (maximum selektor).
Testujemy sieć na danych testowych, wydrukujemy miary ewaluacji klasyfikatora: macierz omyłek, dokładność, precyzję, pełność, F-miarę dla każdego języka.
Zapewnić okienko tekstowe do testowania: po nauczeniu wpiszemy lub wklejamy dowolny nowy tekst w danym języku i sprawdzamy, czy sieć prawidłowo go klasyfikuje.

Nie można używać żadnych bibliotek ML, wszystko ma być zaimplementowane od zera w pętlach, ifach, odleglość też sam ma liczyć używając dzialań arytmetycznych (do operacji na liczbach można używać java.lang.Math), etc. Można używać java.util.

## MPP 4

Przeprowadzić algorytm grupowania k-means dla irysów.

Podczas grupowania ignorujemy atrybut decyzyjny.

Wczytujemy od użytkownika liczbę grup k.

 
Program ma wyświetlać:

przy każdej iteracji: sumę kwadratów odległości kwiatków od "swoich" centroidów
na końcu: liczebność grup wraz z ich entropią ze względu na gatunki kwiatów.

 
Nie można używać żadnych bibliotek ML, wszystko ma być implementowane od zera w pętlach, if-ach, odległości trzeba liczyć za pomocą dzialań matematycznych, etc.

## MPP 5

Napisać program obliczający kod Huffmana dla dowolnego podanego na wejściu ciągu znaków.

 

Jako wynik program ma wypisać: ile bitów potrzebowałby kod stałej długości dla podanego ciągu, kody liter w porządku leksykograficznym kodów, kod Huffman ciągu oraz jego długość w bitach.

 
