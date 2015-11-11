package FastA;

/**
 * Created by Robin on 10.11.2015.
 */
public class FastASequence {

    private String id;
    private String strain;
    private String sequence;

    public FastASequence(String line){
        id = getIdOfLine(line);
        strain = getStrainOfLine(line);
        sequence = "not set";
    }

    /**
     * Reads the ID of a single sequence header line, returns it as String
     * @param line
     * @return FastA identifier (String)
     */
    private static String getIdOfLine(String line){
        if (line.contains(".") && line.contains(">")) {
            String[] parts = line.split("[.]");
            parts = parts[0].split(">");
            return parts[parts.length-1];
        } else {
            throw new IllegalArgumentException("String " + line + " does not contain .");
        }
    }


    /**
     * Reads the strain of a single sequence header line, returns it as String
     * @param line
     * @return FastA strain of sequence (String)
     */
    private static String getStrainOfLine(String line){
        if (line.contains(";")) {
            String[] parts = line.split(";");
            return parts[parts.length-1];
        } else {
        throw new IllegalArgumentException("String " + line + " does not contain ;");
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStrain() {
        return strain;
    }

    public void setStrain(String strain) {
        this.strain = strain;
    }



}
