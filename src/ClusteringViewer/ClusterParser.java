package ClusteringViewer;

import FastA.FastAParser;
import javafx.scene.control.TreeItem;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by Robin on 08.11.2015.
 */
public class ClusterParser {

    private ArrayList<TreeItem<Cluster>> clusters;

    /**
     * @param cluster
     * @param fastA
     * @throws IOException
     */
    public ClusterParser(File cluster, File fastA) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(cluster));

        FastAParser fastAInfo = new FastAParser(fastA);

        String clusterIdentifier = ">Cluster \\d+";
        String clusterRepresentative = ".*\\*";

        ArrayList<TreeItem<Cluster>> r = new ArrayList<>();
        ArrayList<TreeItem<Cluster>> clusterSequences = new ArrayList<>();
        String line;
        boolean firstCluster = true;
        while((line = br.readLine()) != null){
            if(Pattern.matches(clusterIdentifier, line)){
                // if new cluster is found, complete old cluster.
                if(!firstCluster) {
                    r.get(r.size()-1).getChildren().addAll(clusterSequences);
                    r.get(r.size()-1).setExpanded(true);
                }
                clusterSequences = new ArrayList<>();
            }else if(Pattern.matches(clusterRepresentative, line)) {
                Cluster c = new Cluster();
                c = c.ClusterHelper(line, fastAInfo);
                r.add(new TreeItem<>(c));
            }else{
                Cluster c = new Cluster();
                c = c.ClusterHelper(line, fastAInfo);
                clusterSequences.add(new TreeItem<>(c));
                }
            }
        // complete last cluster
        r.get(r.size()-1).getChildren().addAll(clusterSequences);
        this.clusters = r;
        }

    public ArrayList<TreeItem<Cluster>> getClusters() {
        return clusters;
    }

    public void setClusters(ArrayList<TreeItem<Cluster>> clusters) {
        this.clusters = clusters;
    }
}

