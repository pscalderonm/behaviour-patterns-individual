package oscarblancarte.ipd.observer;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Observable;
import oscarblancarte.ipd.observer.impl.ConfigurationManager;
import oscarblancarte.ipd.observer.impl.observers.CurrencyFormatObserver;
import oscarblancarte.ipd.observer.impl.observers.DateFormatObserver;
import oscarblancarte.ipd.observer.impl.observers.MoneyFormatObserver;

/**
 * @author Oscar Javier Blancarte Iturralde
 * @see http://www.oscarblancarteblog.com
 */
public class ObserverMain {

    public static void main(String[] args) {
        ConfigurationManager conf = ConfigurationManager.getInstance();
        
        //Se establecen los valores por default.
        conf.setDefaultDateFormat(new SimpleDateFormat("yyyy/MM/dd"));
        conf.setMoneyFormat(new DecimalFormat("##.00"));
        System.out.println("Established configuration");
        
        //Se dan de alta lo observers
        DateFormatObserver dateFormatObserver = new DateFormatObserver();
        MoneyFormatObserver moneyFormatObserver = new MoneyFormatObserver();
        CurrencyFormatObserver currencyFormatObserver = new CurrencyFormatObserver();
        conf.addObserver(dateFormatObserver);
        conf.addObserver(moneyFormatObserver);
        conf.addObserver(currencyFormatObserver);
        System.out.println("");
        
        //Se cambia la fonfiguratión
        conf.setDefaultDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
        conf.setMoneyFormat(new DecimalFormat("###,#00.00"));
        conf.setCurrencyFormat("$");
        System.out.println("");
        
        //Se realiza otro cambio en la configuración.
        conf.setDefaultDateFormat(new SimpleDateFormat("MM/yyyy/dd"));
        conf.setMoneyFormat(new DecimalFormat("###,#00"));
        conf.setCurrencyFormat("€");

        
        conf.removeObserver(dateFormatObserver);
        conf.removeObserver(moneyFormatObserver);
        conf.removeObserver(currencyFormatObserver);
        System.out.println("");
        
        //Se realiza otro cambio en la configuración.
        conf.setDefaultDateFormat(new SimpleDateFormat("MM/yyyy"));
        conf.setMoneyFormat(new DecimalFormat("###,##0.00"));
        conf.setCurrencyFormat("xx");
    }
}