/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author Vytautas
 */
@ManagedBean(name = "main", eager = true)
@SessionScoped
public class Main implements Serializable {
   private static final long serialVersionUID = 1L;
   private static ArrayList<String> currenciesArray;
   private static ArrayList<BigDecimal> ratesArray;
   private static String selectedDate;
   private static String previousDay;
   private String currency;
   private BigDecimal change;
   
   public String getDate() {
        return selectedDate;
   }
   
   public void setDate(String date) {
        Main.selectedDate = date;
        previousDay = Functions.getPreviousDay(selectedDate);
        
        String selectedDateInfo = Functions.getXmlTextFromServer(selectedDate);
        String previousDayInfo = Functions.getXmlTextFromServer(previousDay);
        
        Document selectedDateDoc = Functions.getDocumentFromXmlText(selectedDateInfo);
        Document previousDayDoc = Functions.getDocumentFromXmlText(previousDayInfo);
        
        NodeList currenciesList1 = selectedDateDoc.getElementsByTagName("currency");
        NodeList currenciesList0 = previousDayDoc.getElementsByTagName("currency");
        NodeList ratesList1 = selectedDateDoc.getElementsByTagName("rate");
        NodeList ratesList0 = previousDayDoc.getElementsByTagName("rate");
        currenciesArray = new ArrayList<>();
        ratesArray = new ArrayList<>();

        for (int i = 0; i < currenciesList1.getLength(); i++) {
            String currency1 = currenciesList1.item(i).getTextContent();
            for (int j = 0; j < currenciesList0.getLength(); j++) {
                String currency0 = currenciesList0.item(j).getTextContent();
                if (currency1.equals(currency0)) {
                    BigDecimal rate1 = new BigDecimal(ratesList1.item(i).getTextContent());
                    BigDecimal rate0 = new BigDecimal(ratesList0.item(j).getTextContent());
                    BigDecimal changeResult = rate1.subtract(rate0);
                    currenciesArray.add(currency1.concat("-LTL"));
                    ratesArray.add(changeResult);
                }    
            }
        }
   }  
   
    private static final ArrayList<Currency> currencies
        = new ArrayList<Currency>(Arrays.asList());	


    public ArrayList<Currency> getCurrencies() {
        currencies.clear();
        for (int i = 0; i < currenciesArray.size(); i++) {
            currencies.add(new Currency(currenciesArray.get(i), ratesArray.get(i)));
        }
        return currencies;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }
}
