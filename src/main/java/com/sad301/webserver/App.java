package com.sad301.webserver;

import javax.print.event.PrintJobAttributeListener;
import javax.swing.*;
import spark.*;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.GroupPrincipal;

/**
* Hello world!
*
*/
public class App extends JFrame {

  private Service service;

  public static void main( String[] args ) {
    try {
      String laf = UIManager.getSystemLookAndFeelClassName();
      UIManager.setLookAndFeel(laf);
    }
    catch(Exception exc) {
      exc.printStackTrace();
    }
    finally {
      SwingUtilities.invokeLater(() -> {
        App app = new App();
        app.setVisible(true);
      });
    }
  }

  public App() {
    createGUI();
  }

  private void createGUI() {
    JLabel lAddress = new JLabel("Address :");
    JTextField tfAddress = new JTextField("127.0.0.1");

    JLabel lPort = new JLabel("Port :");
    JTextField tfPort = new JTextField("8000");

    JLabel lRoot = new JLabel("Directory :");
    JTextField tfRoot = new JTextField();
    tfRoot.addFocusListener(new FocusListener() {
      boolean showing = false;
      @Override
      public void focusGained(FocusEvent e) {
        System.out.println("gained");
        if(!showing) {
          showing = true;
          JFileChooser fc=  new JFileChooser();
          fc.showOpenDialog(null);
        }
      }
      @Override
      public void focusLost(FocusEvent e) {
        System.out.println("lost");
        showing = false;
      }
    });

    JButton btnExec = new JButton("Start");

    GroupLayout gl = new GroupLayout(getContentPane());
    gl.setAutoCreateGaps(true);
    gl.setAutoCreateContainerGaps(true);
    gl.setHorizontalGroup(gl.createSequentialGroup()
            .addGroup(gl.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addGroup(gl.createSequentialGroup()
                            .addGroup(gl.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(lAddress)
                                    .addComponent(lPort)
                                    .addComponent(lRoot)
                            )
                            .addGroup(gl.createParallelGroup()
                                    .addComponent(tfAddress)
                                    .addComponent(tfPort)
                                    .addComponent(tfRoot)
                            )
                    )
                    .addComponent(btnExec)
            )
    );
    gl.setVerticalGroup(gl.createSequentialGroup()
            .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lAddress)
                    .addComponent(tfAddress)
            )
            .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lPort)
                    .addComponent(tfPort)
            )
            .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lRoot)
                    .addComponent(tfRoot)
            )
            .addComponent(btnExec)
    );

    getContentPane().setLayout(gl);

    pack();
    setSize(250, getHeight());
    setResizable(false);

    setTitle("Simple Web Server");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
  }

}
