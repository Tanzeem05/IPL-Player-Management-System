package network.server;

import data.CentralDatabase;
import data.Player;
import network.util.NetworkUtil;
import java.io.IOException;
import network.dto.*;

public class ReadThreadServer implements Runnable {
    private Thread thr;
    private NetworkUtil networkUtil;
    WriteThreadServer writeThreadServer;

    public ReadThreadServer(NetworkUtil networkUtil, WriteThreadServer writeThreadServer) {
        this.networkUtil = networkUtil;
        this.writeThreadServer = writeThreadServer;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = networkUtil.read();
                System.out.println(o);

                if (o instanceof LoginRequest) {
                    new Thread(() -> {writeThreadServer.login((LoginRequest) o, networkUtil);
                    }).start();
                }

                if (o instanceof SignUpRequest) {
                    new Thread(() -> writeThreadServer.signUp((SignUpRequest) o, networkUtil)).start();
                }

                if (o instanceof SellRequest) {
                    new Thread(() -> writeThreadServer.sell((SellRequest) o)).start();
                }

                if (o instanceof BuyRequest) {
                    new Thread(() -> writeThreadServer.buy((BuyRequest) o)).start();
                }

                if(o instanceof PlayerEditInfo){
                    new Thread(() -> writeThreadServer.editPlayer((PlayerEditInfo) o)).start();
                }

                if (o instanceof LogoutRequest) {
                    new Thread(() -> writeThreadServer.logout(networkUtil)).start();
                }

                if (o instanceof Player){
                    new Thread(()-> writeThreadServer.addPlayer((Player) o)).start();
                }

                if(o instanceof CloseGUI){
                    new Thread(() -> writeThreadServer.logout(networkUtil)).start();
                    networkUtil.write(o);
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                CentralDatabase.getInstance().writeToInputFile();
                networkUtil.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
