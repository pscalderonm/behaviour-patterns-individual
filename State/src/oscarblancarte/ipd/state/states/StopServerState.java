package oscarblancarte.ipd.state.states;

import oscarblancarte.ipd.state.Server;

public class StopServerState extends AbstractServerState {

    public StopServerState(final Server server){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{

                    while(server.getMessageProcess().countMessage() > 0){
                        Thread.sleep(1000);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

                server.getMessageProcess().stop();
            }
        });


    }
    
    @Override
    public void handleMessage(Server server, String message) {
        System.out.println("The server is stopped");
    }
}
