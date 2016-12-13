/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utrgvmail;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Alex
 */
public class UTRGVmail extends Application {
    
    EmailSender send1 = new EmailSender();
    
    @Override
    public void start(Stage primaryStage) {
        try {
            String current = new java.io.File( "." ).getCanonicalPath();
            System.out.println("\n\n\n\n\n==========Current dir:" + current + "\n\n\n\n\n");
        } catch (Exception e) {            
        }
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Text scenetitle = new Text("UTRGV Email");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        //userTextField.setText("Adolfo.Sierra01@utrgv.edu");
        userTextField.setText("alejandro.c.rios01@utrgv.edu");
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);
        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);
        
        Label lbMailTo = new Label("Mail To:");
        grid.add(lbMailTo, 0, 3);
        TextField tfMailTo = new TextField();
        tfMailTo.setText("alex.c.rios93@gmail.com");
        grid.add(tfMailTo, 1, 3);
        
        Label lbSubject = new Label("Subject: ");
        grid.add(lbSubject, 0, 4);
        TextField tfSubject = new TextField();
        tfSubject.setText("TRANSCRIPT FOR YOU!!!!");
        grid.add(tfSubject, 1, 4);        
        
        Label lbTexArea = new Label("Message: ");
        grid.add(lbTexArea, 0, 5);
        TextArea tfTexArea = new TextArea();
        tfTexArea.setText("Dear Student, ");
        grid.add(tfTexArea, 1, 5);        
        
        Label lbOutpUt = new Label("");
        lbOutpUt.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(lbOutpUt, 1, 8);        
        
        Button btn = new Button();
        btn.setText("Send Email");
        
        grid.add(btn, 1, 7);
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                String attachment = "MYPDF.pdf"; // This is where the pdf file goes
                
                send1.EmailSender(userTextField.getText(),
                        userTextField.getText(),
                        pwBox.getText(),
                        tfMailTo.getText(),
                        tfSubject.getText(),
                        tfTexArea.getText(),
                        attachment,
                        lbOutpUt);
            }
        });        
                
        StackPane root = new StackPane();
        root.getChildren().add(grid);
        
        Scene scene = new Scene(root, 600, 500);
        
        primaryStage.setTitle("UTRGV Email Client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
