package JDK_Sem_1_HW.client;

import JDK_Sem_1_HW.server.Server;

public class Client {
    private View view;
    private String name;
    private Server server;
    private boolean isCnnected;

    public String getName() {
        return name;
    }

    public Client(View view, Server server) {
        this.view = view;
        this.server = server;
    }

    public boolean connectToServer(String name){
        this.name = name;
        if(server.connectUser(this) && server.isServerWorking() == true){
            showOnWindow("Вы успешно подключились! \n");
            isCnnected = true;
            String log = server.getHistory();
            if (log != null){
                showOnWindow(log);
            }
            return true;
        }else {
            showOnWindow("Подключение не удалось");
            return false;
        }
    }

    public void showOnWindow(String text){
        view.sendMessage(text + "\n");
    }

    public void answerFromServer(String messageFromServer){
        showOnWindow(messageFromServer);
    }

    public void sendMessage(String message){
        if(isCnnected && server.isServerWorking()){
            if(!message.isEmpty()){
                server.sendMessage(name + ": " + message);
            }
        }else {
            showOnWindow("Нет подключения к серверу");
        }
    }


}
