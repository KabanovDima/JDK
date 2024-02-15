package JDK_Sem_1_HW.server;

import JDK_Sem_1_HW.client.Client;

public interface Server {
    boolean connectUser (Client client);
    void sendMessage (String message);
    String getHistory();
    boolean isServerWorking();
}
