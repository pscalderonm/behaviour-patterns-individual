package oscarblancarte.ipd.observer.impl.observers;

import oscarblancarte.ipd.observer.impl.ConfigurationManager;
import oscarblancarte.ipd.observer.impl.IObserver;

public class CurrencyFormatObserver implements IObserver {
    @Override
    public void notifyObserver(String command, Object source) {
        if(command.equals("currencyFormat")){
            ConfigurationManager conf = (ConfigurationManager) source;
            System.out.println("Observer ==> CurrencyFormatObserver.curencyFormatChange > "
                    + conf.getCurrencyFormat());
        }
    }
}
