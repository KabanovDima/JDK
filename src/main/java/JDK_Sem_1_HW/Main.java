package JDK_Sem_1_HW;

import JDK_Sem_1_HW.client.ClientGUI;
import JDK_Sem_1_HW.server.ServerWindow;

public class Main {
    public static void main(String[] args) {
        ServerWindow serverWindow = new ServerWindow();
        new ClientGUI(serverWindow);
        new ClientGUI(serverWindow);
    }
}
