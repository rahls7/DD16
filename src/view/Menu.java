package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * Created by rahls7 on 18/02/17.
 *
 *
 *
 * Not for Build 1, please ignore.
 */


public class Menu extends JFrame{

    private About about_panel;
    private Map map_panel;
    private Item item_panel;



    public Menu() {
        add(new Panel());
        setJMenuBar(createMenuBar("Main Menu"));
        pack();
        setVisible(true);

    }

    public JMenuBar createMenuBar(String name) {
        JMenuBar bar = new JMenuBar();
        bar.add(createMenu(name));
        return bar;
    }

    private JMenu createMenu(String name) {
        JMenu menu = new JMenu(name);

        JMenuItem item_play = new JMenuItem("Play");
        item_play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        JMenuItem item_map = new JMenuItem("Map Editor");
        item_map.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map_panel = new Map();
                menuAction(map_panel);
            }
        });

        JMenuItem item_compaign = new JMenuItem("Compaign Editor");
        item_compaign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        JMenuItem item_character = new JMenuItem("Character Editor");
        item_character.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        JMenuItem item_item = new JMenuItem("Item Editor");
        item_item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                item_panel = new Item();
                menuAction(item_panel);
            }
        });

        JMenuItem item_about = new JMenuItem("About");
        item_about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                about_panel = new About();
                menuAction(about_panel);
            }
        });

        JMenuItem item_close = new JMenuItem("Close");
        item_close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //getContentPane().removeAll();
                System.exit(0);
            }
        });

        menu.add(item_play);
        menu.add(item_map);
        menu.add(item_compaign);
        menu.add(item_character);
        menu.add(item_item);
        menu.add(item_about);
        menu.add(item_close);

        return menu;
    }



    private void menuAction(JPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().doLayout();
        repaint();
        validate();
    }
}


class Panel extends JPanel {


    public Panel() {
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public Dimension getPreferredSize() {
        return new Dimension(960, 600);
    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw Image
        try {
            BufferedImage image = ImageIO.read(new File("src/images/MenuBackground.jpg"));
            g.drawImage(image,0,0,getWidth(),getHeight(),this);

        }catch (IOException e) {
            e.printStackTrace();
        }

        addJLabels();


    }

    private void addJLabels() {

    }
}





/*import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;

import javax.swing.*;

public class Menu extends Application {

    private Map map_panel;

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(860, 600);

        try (InputStream is = Files.newInputStream(Paths.get("src/images/MenuBackground.jpg"))) {
            ImageView img = new ImageView(new Image(is));
            img.setFitWidth(860);
            img.setFitHeight(600);
            root.getChildren().add(img);
        }
        catch (IOException e) {
            System.out.println("Couldn't load image");
        }

        Title title = new Title("D R A G O N & D U N G E O N S");
        title.setTranslateX(75);
        title.setTranslateY(200);

        MenuItem itemExit = new MenuItem("EXIT");
        itemExit.setOnMouseClicked(event -> System.exit(0));

        MenuItem mapItem = new MenuItem("MAP EDITOR");
        mapItem.setOnMouseClicked(event -> {
            map_panel = new Map();

        });

        MenuBox menu = new MenuBox(
                new MenuItem("PLAY"),
                //new MenuItem("MAP EDITOR"),
                mapItem,
                new MenuItem("CAMPAIGN EDITOR"),
                new MenuItem("ITEM EDITOR"),
                itemExit);
        menu.setTranslateX(100);
        menu.setTranslateY(300);

        root.getChildren().addAll(title, menu);
        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Dragon and Dungeons V16");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void menuAction(JPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().doLayout();
        repaint();
        validate();
    }

    /* private static class Title extends StackPane {
        public Title(String name) {
            Rectangle bg = new Rectangle(750, 60);
            bg.setStroke(Color.WHITE);
            bg.setStrokeWidth(2);
            bg.setFill(null);

            Text text = new Text(name);
            text.setFill(Color.DARKBLUE);
            text.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 50));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);
        }
    } */

   /* private static class MenuBox extends VBox {
        public MenuBox(MenuItem... items) {
            getChildren().add(createSeparator());

            for (MenuItem item : items) {
                getChildren().addAll(item, createSeparator());
            }
        }

        private Line createSeparator() {
            Line sep = new Line();
            sep.setEndX(200);
            sep.setStroke(Color.DARKGREY);
            return sep;
        }
    }

    private static class MenuItem extends StackPane {
        public MenuItem(String name) {
            LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[] {
                    new Stop(0, Color.DARKVIOLET),
                    new Stop(0.1, Color.BLACK),
                    new Stop(0.9, Color.BLACK),
                    new Stop(1, Color.DARKVIOLET)
            });

            Rectangle bg = new Rectangle(200, 30);
            bg.setOpacity(0.4);

            Text text = new Text(name);
            text.setFill(Color.DARKGREY);
            text.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 22));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);

            setOnMouseEntered(event -> {
                bg.setFill(gradient);
                text.setFill(Color.WHITE);
            });


            setOnMouseExited(event -> {
                bg.setFill(Color.BLACK);
                text.setFill(Color.DARKGREY);
            });

            setOnMousePressed(event -> {
                bg.setFill(Color.DARKVIOLET);
            });

            setOnMouseReleased(event -> {
                bg.setFill(gradient);
            });
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}*/