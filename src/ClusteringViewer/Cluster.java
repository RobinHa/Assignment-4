package ClusteringViewer;

import FastA.FastAParser;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TreeItem;

import java.util.regex.Pattern;

/**
 * Created by Robin on 10.11.2015.
 */
public class Cluster extends TreeItem<Cluster> {

    private SimpleStringProperty seqID;
    private SimpleStringProperty seqStrain;
    private SimpleIntegerProperty seqLength;
    private double seqSimilarity;
    private boolean seqRepresentative;

    public Cluster(){

    }

    public Cluster(String id, String strain, int length, double similarity, boolean representive){
        seqID = new SimpleStringProperty(id);
        seqStrain = new SimpleStringProperty(strain);
        seqLength = new SimpleIntegerProperty(length);
        seqSimilarity = similarity;
        seqRepresentative = representive;
    }

    /**
     * Constructor with just the line of a
     * @param line
     */
    public Cluster ClusterHelper(String line, FastAParser fastA){
        String id = getSeqIDByLine(line);
        String strain = fastA.getFastASequenceByID(id).getStrain();
        int length = getLengthOfSequenceByLine(line);
        boolean representative = Pattern.matches(".*\\*", line);
        double similarity = (double)100;
        if(!representative){
            similarity = getSimilarityByLine(line);
        }
        return new Cluster(id, strain, length, similarity, representative);
       }

    private static String getSeqIDByLine(String line){
        if(line.contains(">") && line.contains(".")) {
            String[] parts = line.split(">");
            parts = parts[1].split("[.]");
            return parts[0];
        }else{
            throw new IllegalArgumentException("String " + line + " does not match expectd format");
        }
    }

    private static int getLengthOfSequenceByLine(String line){
        if(line.contains("\t") && line.contains("nt,")){
            String[] parts = line.split("nt,");
            parts = parts[0].split("\\t");
            if(parts[1].contains(".")){
                parts = parts[1].split(".");
                return Integer.parseInt(parts[0]);
            }
            return Integer.parseInt(parts[1]);
        }else{
            throw new IllegalArgumentException("String " + line + " does not match expectd format");
        }
    }

    private static double getSimilarityByLine(String line){
        if(line.contains("at +/") && line.contains("%")){
            String[] parts = line.split("%");
            parts = parts[0].split("at \\+/");
            return Double.parseDouble(parts[1]);
        }else{
            throw new IllegalArgumentException("String " + line + " does not match expectd format");
        }
    }

    public String getSeqID() {
        return seqID.get();
    }

    public void setSeqID(String seqID) {
        this.seqID.set(seqID);
    }

    public String getSeqStrain() {
        return seqStrain.get();
    }

    public void setSeqStrain(String seqStrain) {
        this.seqStrain.set(seqStrain);
    }

    public int getSeqLength() {
        return seqLength.get();
    }

    public void setSeqLength(int seqLength) {
        this.seqLength.set(seqLength);
    }

    public double getSeqSimilarity() {return seqSimilarity;}

    public void setSeqSimilarity(double seqSimilarity) {this.seqSimilarity = seqSimilarity;}

    public boolean isSeqRepresentative() {
        return seqRepresentative;
    }

    public void setSeqRepresentative(boolean seqRepresentative) {
        this.seqRepresentative = seqRepresentative;
    }
}
