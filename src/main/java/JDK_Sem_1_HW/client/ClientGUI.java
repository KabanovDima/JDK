package JDK_Sem_1_HW.client;

import JDK_Sem_1_HW.server.ServerWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ClientGUI extends JFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final JTextArea log = new JTextArea();

    private final JPanel panTop = new JPanel(new GridLayout(2,3));
    private final JTextField tfIPAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8189");
    private final JLabel emptyField = new JLabel();
    private final JTextField tfLogin = new JTextField("Введите имя");
    private final JPasswordField tfPassword = new JPasswordField("1234");
    private final JButton btnLogin = new JButton("Login");

    private final JPanel panBottom = new JPanel(new BorderLayout());
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");
    private boolean isConnected;

    public boolean isConnected() {
        return isConnected;
    }

    public String getTfLogin() {
        return tfLogin.getText();
    }

    public ClientGUI(ServerWindow serverWindow){
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(serverWindow.isServerWorking()){
                    isConnected = true;
                    serverWindow.clientConnected(ClientGUI.this);
                    logText("Вы успешно подключились!");
                    logText(serverWindow.getChatHistory());
                }else {
                    logText("Не удалось подключиться");
                }
            }
        });

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH,HEIGHT);
        setTitle("Chat Client");

        panTop.add(tfIPAddress);
        panTop.add(tfPort);
        panTop.add(emptyField);
        panTop.add(tfLogin);
        panTop.add(tfPassword);
        panTop.add(btnLogin);

        add(panTop, BorderLayout.NORTH);

        panBottom.add(tfMessage, BorderLayout.CENTER);
        panBottom.add(btnSend, BorderLayout.SOUTH);

        add(panBottom, BorderLayout.SOUTH);

        log.setEditable(false);
        JScrollPane scrolLog = new JScrollPane(log);
        add(scrolLog, BorderLayout.CENTER);

        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isConnected) {
                    String message = tfMessage.getText();
                    if (!message.isEmpty()) {
                        serverWindow.sendMessage(tfLogin.getText(), message);
                        tfMessage.setText("");
                    }
                }
            }
        });
        tfMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isConnected) {
                    String message = tfMessage.getText();
                    if (!message.isEmpty()) {
                        serverWindow.sendMessage(tfLogin.getText(), message);
                        tfMessage.setText("");
                    }
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new ClientGUI(new ServerWindow());
    }

    private void logText (String message){
        log.append(message + "\n");
    }

    public void receiveMessage (String clientNmae, String message) {
        logText(clientNmae + ": " + message);
    }


}
