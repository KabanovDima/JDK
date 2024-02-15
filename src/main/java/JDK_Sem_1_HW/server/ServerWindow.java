package JDK_Sem_1_HW.server;

import JDK_Sem_1_HW.client.Client;
import JDK_Sem_1_HW.client.ClientGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;



public class ServerWindow extends JFrame implements Server{
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final JButton btnStart = new JButton("Start");
    private final JButton btnStop = new JButton("Stop");
    private final JTextArea log = new JTextArea();

    private boolean isServerWorking = false;


    private File logFile;

    public static void main(String[] args) {
        new ServerWindow();
    }

    private List<Client> connectedClients = new ArrayList<>();

    public ServerWindow(){
        isServerWorking = false;
        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isServerWorking){
                    logText("Сервер не запущен");
                } else {
                    isServerWorking = false;
                    logText("Сервер остановлен");
                }
            }
        });
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isServerWorking){
                    logText("Сервер работает");
                } else {
                    isServerWorking = true;
                    logText("Сервер запущен");
                }
            }
        });


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat Server");
        setAlwaysOnTop(true);

        JPanel panButtom = new JPanel(new GridLayout(1,2));
        panButtom.add(btnStart);
        panButtom.add(btnStop);

        JPanel panMain = new JPanel(new BorderLayout());
        panMain.add(panButtom, BorderLayout.SOUTH);
        panMain.add(new JScrollPane(log), BorderLayout.CENTER);
        add(panMain);

        setVisible(true);

        logFile = new File("C:\\Users\\Dmitry\\Desktop\\JDK\\src\\main\\java\\JDK_Sem_1_HW\\server\\log.txt");
        try {
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    private void logText (String message){
        log.append(message + "\n");
    }

    @Override
    public boolean connectUser(Client client) {
        if(isServerWorking) {
            connectedClients.add(client);
            logText(client.getName() + " подключился к беседе");
            String history = getHistory();
            if (!history.isEmpty()) {
                client.answerFromServer(history);
            }
        }
        return true;
    }

    @Override
    public void sendMessage(String message) {
        if(isServerWorking) {
            logText(message);
            for (Client client : connectedClients) {
                client.answerFromServer(message);
            }
            logMessage(message);
        }
    }

    @Override
    public String getHistory() {
        StringBuilder history = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new FileReader(logFile))){
            String line;
            while ((line = reader.readLine()) != null){
                history.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return history.toString();
    }

    @Override
    public boolean isServerWorking() {
        return isServerWorking;
    }

    public void logMessage(String message){
        try(FileWriter writer = new FileWriter(logFile, true)){
            writer.write("+ message \n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
