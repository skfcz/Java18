package de.groygroy.java18;

/**
 * Beispiel Code Inline
 * {@snippet :
 * try (var out = new FileWriter("out.txt", StandardCharsets.ISO_8859_1)) { // @highlight substring="ISO_8859_1"
 *             out.write("äöüß°");
 * }
 * }
 * Beispiel Code aus Datei
 * {@snippet file="de/groygroy/java18/NewSwitch.java" region="beispiel1" }
 */
public class NewSwitch {

    public static void main(String ... args) throws Exception {
        // switch ohne falltrough und mit null als case (Java 17)
        // Vergleich wie mit instanceof mit Zuweisung (Java 18, JEP 420)
        Object[] testObjekts = new Object[] {Math.PI, null, "hallo", -1.0};
        for (Object testObjekt : testObjekts) {
            // @start region="beispiel1"
            var resultat = switch (testObjekt) {
                case null -> "ungültiger Wert 'null'";
                case String s -> "String " + s;
                case Double d && d < 0.0 -> "negativer Double " + d;
                case Double d -> "Double";
                default -> "Unbekannter Wert '" + testObjekt + "'";
            }; // @end
            System.out.println(testObjekt + "=>" + resultat);
        }

    }
}
