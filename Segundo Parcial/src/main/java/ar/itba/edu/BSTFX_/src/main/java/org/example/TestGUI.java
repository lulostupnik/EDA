package org.example;

import controller.GraphicsTree;
import core.BST;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TestGUI extends Application {

    public static void main(String[] args) {
        // GUI
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Drawing the BST");
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 1300, 700);

        BST<Person> myTree = createModel();
        GraphicsTree<Person> c = new GraphicsTree<>(myTree);
		/*BST<Person> myTree = createModel2();
		GraphicsTree<Person> c = new GraphicsTree<>(myTree);*/

        c.widthProperty().bind(scene.widthProperty());
        c.heightProperty().bind(scene.heightProperty());

        root.getChildren().add(c);
        stage.setScene(scene);
        stage.show();


    }


    private BST<Integer> createModel2() {
        BST<Integer> myTree = new BST<>();
        myTree = new BST<>();
        myTree.insert(50);
        myTree.insert(60);
        myTree.insert(80);
        myTree.insert(20);
        myTree.insert(70);
        myTree.insert(40);
        myTree.insert(44);
        myTree.insert(10);
        myTree.insert(40);
        myTree.inOrder();

        return myTree;
    }

    private BST<Person> createModel() {
        BST<Person> myTree = new BST<>();
        myTree = new BST<>();
        myTree.insert(new Person( 50, "Ana" ));
        myTree.insert(new Person( 60, "Juan") );
        myTree.insert(new Person( 80, "Sergio") );
        myTree.insert(new Person( 20, "Lila ") );
        myTree.insert(new Person( 77, "Ana") );
        myTree.inOrder();

        return myTree;
    }


}