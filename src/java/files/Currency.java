/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import java.math.BigDecimal;

public class Currency {
   private String currency;
   private BigDecimal change;

   public Currency(String currency, BigDecimal change) {
        this.currency = currency;
        this.change = change;
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
