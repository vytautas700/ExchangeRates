/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Vytautas
 */
@FacesValidator("files.DateValidator")
public class DateValidator implements Validator {
    FacesMessage msg = new FacesMessage("Invalid date format!");
    FacesMessage msg2 = new FacesMessage("Invalid date range!");

   @Override
   public void validate(FacesContext facesContext,
        UIComponent component, Object value)
        throws ValidatorException {
        StringBuilder date = new StringBuilder();
        String dateValue = value.toString();

        if(dateValue.isEmpty() || "".equals(dateValue)) {
             throw new ValidatorException(msg);
        }

        if(dateValue.length() != 10) {
            throw new ValidatorException(msg);
        }
        for (int i = 0; i < dateValue.length(); i++) {
            if (Character.isDigit(dateValue.charAt(i)) && !(i == 4 || i == 7)) {
            }
            else if((i == 4 || i == 7) && (dateValue.charAt(i) == '-')) {
            }
            else {
                throw new ValidatorException(msg);
            }
        }

        if(Integer.parseInt(dateValue.replaceAll("-", "")) < 19930626 || 
                Integer.parseInt(dateValue.replaceAll("-", "")) > 20141231) {
            throw new ValidatorException(msg2);
        }
        
        
        String dateFormat = "yyyy-MM-dd";
        try {
            DateFormat df = new SimpleDateFormat(dateFormat);
            df.setLenient(false);
            df.parse(dateValue);
        } catch (ParseException ex) {
            throw new ValidatorException(msg2);
        }
   }
}
