package JDK_Sem_1_HW.client;

import JDK_Sem_1_HW.server.ServerWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class ClientGUI extends JFrame implements View{
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private Client client;

    JTextArea log;
    JTextField tfIPAddress,tfPort,tfLogin, tfMessage;
    JPasswordField tfPassword;
    JButton btnLogin,btnSend;
    JPanel panTop, panBottom;
    JLabel emptyField;

    public ClientGUI(ServerWindow serverWindow){
        settings(serverWindow);
        createPanel();

        setVisible(true);
    }

    private void settings(ServerWindow serverWindow){
        setSize(WIDTH,HEIGHT);
        setTitle("Chat Client");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        client = new Client(this, serverWindow);
    }

    public void sendMessage(){
        client.sendMessage(tfMessage.getText());
        tfMessage.setText("");
    }

    public void createPanel(){
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createLog());
        add(createFooter(), BorderLayout.SOUTH);
    }

    private Component createHeaderPanel(){
        panTop = new JPanel(new GridLayout(2,3));
        tfIPAddress = new JTextField("127.0.0.1");
        tfPort = new JTextField("8189");
        emptyField = new JLabel();
        tfLogin = new JTextField("Введите имя");
        tfPassword = new JPasswordField("1234");
        btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.connectToServer(tfLogin.getText());
            }
        });
        panTop.add(tfIPAddress);
        panTop.add(tfPort);
        panTop.add(emptyField);
        panTop.add(tfLogin);
        panTop.add(tfPassword);
        panTop.add(btnLogin);

        return panTop;
    }

    private Component createLog(){
        log = new JTextArea();
        log.setEditable(false);
        return new JScrollPane(log);
    }

    private Component createFooter(){
        panBottom = new JPanel(new BorderLayout());
        tfMessage = new JTextField();
        tfMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n'){
                    sendMessage();
                }
            }
        });
        btnSend = new JButton("Send");
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        panBottom.add(tfMessage);
        panBottom.add(btnSend, BorderLayout.EAST);

        return panBottom;
    }

    @Override
    public void sendMessage(String message) {
        log.append(message);
    }

    @Override
    public void connectedToServer() {

    }
}
