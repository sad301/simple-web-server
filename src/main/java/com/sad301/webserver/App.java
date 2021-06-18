package com.sad301.webserver;

import javax.swing.*;
import spark.*;

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
    SwingUtilities.invokeLater(() -> {
      App app = new App();
      app.setVisible(true);
    });
  }

  public App() {
    createGUI();
  }

  private void createGUI() {
    JLabel lRoot = new JLabel("Root Directory :");
    JTextField tfRoot = new JTextField();
    JButton btnExec = new JButton("Start");
    btnExec.addActionListener(e -> {
      switch(e.getActionCommand()) {
        case "Start" -> {
          btnExec.setActionCommand("Stop");
          String root = tfRoot.getText();
          service = Service.ignite()
            .externalStaticFileLocation(root)
            .port(8000);
          service.init();
        }
        case "Stop" -> {
          btnExec.setActionCommand("Start");
          service.stop();
        }
      }
      btnExec.setText(btnExec.getActionCommand());
    });
    JTextArea taLog = new JTextArea();
    JScrollPane spLog = new JScrollPane(taLog);

    GroupLayout gl = new GroupLayout(getContentPane());
    gl.setAutoCreateGaps(true);
    gl.setAutoCreateContainerGaps(true);
    gl.setHorizontalGroup(gl.createSequentialGroup()
      .addGroup(gl.createParallelGroup()
        .addGroup(gl.createSequentialGroup()
          .addComponent(lRoot)
          .addComponent(tfRoot)
          .addComponent(btnExec)
        )
        .addComponent(spLog)
      )
    );
    gl.setVerticalGroup(gl.createSequentialGroup()
      .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
        .addComponent(lRoot)
        .addComponent(tfRoot)
        .addComponent(btnExec)
      )
      .addComponent(spLog)
    );

    getContentPane().setLayout(gl);

    setTitle("Simple Web Server");
    setSize(400, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
  }

}
