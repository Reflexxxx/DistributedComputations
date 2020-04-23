package task_b;

import java.util.LinkedList;

public class MainClass {
    public static void main(String[] args) {
        Armchair armchair = new Armchair();
        Queue queue = new Queue();
        Hairdresser hairdresser = new Hairdresser(armchair, queue);
        int numberOfClients = 5;

        LinkedList<Client> clients = new LinkedList<>();
        for (int i = 0; i < numberOfClients; i++) {
            clients.add(new Client(armchair, queue));
        }

        for (int i = 0; i < numberOfClients; i++) {
            try {
                clients.get(i).thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        hairdresser.thread.interrupt();

        System.out.println("\n\nEND OF MAIN\n\n");



//        try {
//            for (Client client : clients) {
//                client.thread.join();
//            }
////            synchronized (armchair) {
////                armchair.notifyAll();
////                hairdresser.thread.interrupt();
////            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }
}
