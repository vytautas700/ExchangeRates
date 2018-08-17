/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Vytautas
 */
public class Functions {
    /** Get the date of the day before selected date */
    public static String getPreviousDay(String selectedDate) {
        String dayBefore = selectedDate;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(selectedDate);
            long milliseconds = date.getTime() - (1000 * 60 * 60 * 24);
            Date newDate = new Date(milliseconds);
            dayBefore = sdf.format(newDate);
        } catch (ParseException ex) {
        }
        
        return dayBefore;
    }
    
    /** Get exchange rates from server */
    public static String getXmlTextFromServer(String date) {
        String result = "";
        try
        {
            // Declare URL and create connection
            URL url = new URL("http://old.lb.lt/webservices/ExchangeRates/ExchangeRates.asmx/getExchangeRatesByDate");
            URLConnection connection = url.openConnection();
            
            // Specify that we will send output and accept input
            connection.setDoInput(true);
            connection.setDoOutput(true);
            
            // Declare timeout
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setUseCaches(false);
            connection.setDefaultUseCaches(false);
            
            // HTTP POST info
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", "15");
            
            // Indicate the date
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write("Date=" + date);
            writer.flush();
            writer.close();
            
            // Raad the response
            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            StringBuilder buffer = new StringBuilder();
            char[] charBuffer = new char[2048];
            int num;
            while (-1 != (num = reader.read(charBuffer))) {
                buffer.append(charBuffer, 0, num);
            }
            result = buffer.toString();
        } catch(IOException e) {
        }
        
        return result;
    }
    
    /** Get document from XML text */
    public static Document getDocumentFromXmlText(String text) {
        Document doc = null;
        try {
            InputStream stream = new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(stream);
            doc.getDocumentElement().normalize();
        } catch (ParserConfigurationException | SAXException | IOException ex) {
        }
        
        return doc;
    }
}
