Wen Yin 20179082
Jimmy Yassin Hassanaly 20190749

Lien vers le répertoire git: https://github.com/Ywen27/TP1_IFT3913

Voici les méthodes d'utilisations des 4 fichiers Jar:

En général, il faut ouvrir l'invité de commandes dans le dossier ou se trouve le fichier exécutable et s'utilise comme suit:
>java -jar <nomExecutable.jar> <arguments>

Pour tloc: 
Comme tloc calcule la métrique de taille TLOC, il prend un fichier java en entrée et renvoie TLOC.
Exemple d'utilisation:
>java -jar tloc.jar jfreechart/src/test/java/org/jfree/chart/title/TitleTest.java
et renvoie:
39

Pour tassert:
tassert prend entrée un fichier java et renvoie le nombres d'assertions JUnit.
Exemple d'utilisation:
>java -jar tassert.jar jfreechart/src/test/java/org/jfree/chart/title/TitleTest.java
et renvoie:
11

Pour tls: 
Il prend en entrée un chemin d'accès d'un dossier qui contient du code et produit en sortie en format CSV les colonnes 
précisés dans l'énoncé (partie 3): 
Exemple d'utilisation: 
>java -jar tls.jar jfreechart/src/test/java/org/jfree/chart/title
et renvoie: 
.\CompositeTitleTest.java, org.jfree.chart.title, CompositeTitleTest, 83, 20, 4,15
.\DateTitleTest.java, org.jfree.chart.title, DateTitleTest, 55, 15, 3,67
.\ImageTitleTest.java, org.jfree.chart.title, ImageTitleTest, 6, 16, 0,38
.\ShortTextTitleTest.java, org.jfree.chart.title, ShortTextTitleTest, 40, 9, 4,44
.\TextTitleTest.java, org.jfree.chart.title, TextTitleTest, 81, 25, 3,24
.\TitleTest.java, org.jfree.chart.title, TitleTest, 39, 11, 3,55
Ou si on veut conserver le résultat dans un fichier csv, on a :
java -jar tls.jar -o <chemin-à-la-sortie.csv> <chemin-de-l'entrée>

Pour tropcomp: 
Il prend un chemin en entrée  et un seuil, et retourne en format csv sous le format tls les lignes de classes suspectes.
Exemple d'utilisation:
>java -jar tropcomp.jar jfreechart/src/test 10
et retourne :
.\java\org\jfree\chart\renderer\category\BoxAndWhiskerRendererTest.java, java.org.jfree.chart.renderer.category, BoxAndWhiskerRendererTest, 307, 40, 7.68
.\java\org\jfree\chart\swing\ChartPanelTest.java, java.org.jfree.chart.swing, ChartPanelTest, 216, 23, 9.39
.\java\org\jfree\data\time\YearTest.java, java.org.jfree.data.time, YearTest, 328, 47, 6.98
Ou si on veut conserver le résultat dans un fichier csv, on a :
java -jar tropcomp.jar -o <chemin-à-la-sortie.csv> <chemin-de-l'entrée> <seuil>


