package oscarblancarte.ipd.state.states;

import oscarblancarte.ipd.state.Server;

public class SafeStopServerState extends AbstractServerState {

    public SafeStopServerState(final Server server){

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Server is stopping...");
                try {
                    while (true) {
                        if (server.getMessageProcess().countMessage() <= 0) {
                            server.setState(new StopServerState(server));
                            break;
                        }
                        Thread.sleep(250);
                    }
                    System.out.println("Server stopped");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }).start();
    }

    @Override
    public void handleMessage(Server server, String message) {
        System.out.println("Server is stopping, it can not process any more message");
    }
}
