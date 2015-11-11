package ClusteringViewer;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Clustering Viewer");

        final Scene scene = new Scene(new Group(), 700, 600);
        Group sceneRoot = (Group)scene.getRoot();

        FlowPane flowPane = new FlowPane(6,6);
        sceneRoot.getChildren().add(flowPane);

        // Input of the Files,
        TextField clusterPath = new TextField("select the cluster file");
        clusterPath.setPrefWidth(400);
        TextField fastAPath = new TextField();
        fastAPath.setPrefWidth(400);
        fastAPath.setDisable(true);

        Button chooseClsr = new Button("Choose ClusterParser File");
        chooseClsr.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            File clusterFile = fc.showOpenDialog(primaryStage);
            File fastAFile = checkForFastAFile(clusterFile);
            clusterPath.setText(clusterFile == null ? ".clsr file no longer exist": clusterFile.getAbsolutePath());
            fastAPath.setText(fastAFile == null ? "missing similar FastA file" :fastAFile.getAbsolutePath());
        });


        //Columns of the Table
        TreeTableColumn<Cluster, String> represCol = new TreeTableColumn<>("SequenceID");
        represCol.setPrefWidth(120);
        represCol.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Cluster, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getSeqID())
        );
        TreeTableColumn<Cluster, String> strainCol = new TreeTableColumn<>("Strain");
        strainCol.setPrefWidth(250);
        strainCol.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Cluster, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getSeqStrain())
        );
        TreeTableColumn<Cluster, String> lengthCol = new TreeTableColumn<>("Length");
        lengthCol.setPrefWidth(80);
        lengthCol.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Cluster, String> param) ->
                        new ReadOnlyStringWrapper(Integer.toString(param.getValue().getValue().getSeqLength()))
        );
        TreeTableColumn<Cluster, String> similCol = new TreeTableColumn<>("Similarity");
        similCol.setPrefWidth(80);
        similCol.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Cluster, String> param) ->
                        new ReadOnlyStringWrapper(Double.toString(param.getValue().getValue().getSeqSimilarity()))
        );

        TreeItem<Cluster> root = new TreeItem<>(new Cluster("Clusters", "", 0, 0, true));
        TreeTableView<Cluster> treeTableView = new TreeTableView<>(root);
        treeTableView.setShowRoot(false);
        treeTableView.getColumns().addAll(represCol, strainCol, lengthCol, similCol);

        Button viewClustering = new Button("View Clustering/Validate");
        viewClustering.setOnAction(e -> {
            try {
                File clusterFile = new File(clusterPath.getText());
                File fastAFile = new File(fastAPath.getText());
                ClusterParser clusters = new ClusterParser(clusterFile, fastAFile);
                root.getChildren().addAll(clusters.getClusters());
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        });

        flowPane.getChildren().addAll(clusterPath, chooseClsr, fastAPath, viewClustering, treeTableView);
        Insets insets = new Insets(4,9,4,9);
        flowPane.getChildren().forEach(node -> flowPane.setMargin(node, insets));

        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private File checkForFastAFile(File clusterFile) {
        String clsrPath = clusterFile.getAbsolutePath();
        String fastAPath;
        if(Pattern.matches(".*_\\d{1,3}\\.clsr", clsrPath)){
            fastAPath = clsrPath.split("_\\d{1,3}\\.clsr")[0]
                        + ".fasta";
        }else{
            return null;
        }

        File fastAFile = new File(fastAPath);

        if(!fastAFile.exists() || !clusterFile.exists())
            return null;
        return fastAFile;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
