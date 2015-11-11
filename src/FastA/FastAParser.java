package FastA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by Robin on 10.11.2015.
 */
public class FastAParser {

    private ArrayList<FastASequence> sequencesArray;

    public FastAParser(File fastAFile) throws IOException {
        ArrayList<FastASequence> seqAr = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fastAFile));
        String line;
        while((line = br.readLine()) != null){
            if(Pattern.matches(">.*", line)){
                seqAr.add(new FastASequence(line));
            }
        }
        this.sequencesArray = seqAr;
    }

    public FastASequence getFastASequenceByID(String id){
        for(FastASequence s : this.getSequencesArray()){
            if(s.getId().equals(id))return s;
        }
            throw new IllegalArgumentException("there is a missing organism in the FastA file");
        }


    public ArrayList<FastASequence> getSequencesArray() {
        return sequencesArray;
    }

    public void setSequencesArray(ArrayList<FastASequence> sequencesArray) {
        this.sequencesArray = sequencesArray;
    }


}
