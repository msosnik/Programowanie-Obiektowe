# Programowanie-Obiektowe

Projekt 1 - Generator Ewolucyjny

1. Symulacja napisana na podstawie poprzednich labolatoriów, zgodnie z specyfikacją dostępną na https://github.com/apohllo/obiektowe-lab/tree/master/proj1
2. Projekt należy pobrać, zbuować katalog oolab w środowisku javy za pomocą Gradle-a
3. Plik z konfiguracją symulacji należy umieścić w katalogu oolab, podać jako run parameter w klasie World i wystartować maina
4. Jak żaden plik nie zostanie podany, to konfiguracje zostaną pobrane z pliku config1.properties
5. Wyświetlone okienko symulacji ma 2 przyciski, 1 do startu i przerwania symulacji, 2 wykonuje jeden krok odpowiadający jednemu dniu
6. Wyniki zostają zapinane do pliku nazwanego jak ten z konfiguracjami + "-output.csv" w katalogu oolab

Warianty należy przekazać w pliku jako liczbę całkowitą:
Mapa:
  1 - kula ziemska
  każdy inny int - piekielny portal
Zachowanie zwierząt:
  1 - pełna predestynacja
  każdy inny int - nieco szaleństwa
Mutacje:
  1 - pełna losowoć
  każdy inny int - lekka korekta
