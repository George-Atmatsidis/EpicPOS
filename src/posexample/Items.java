/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posexample;

/**
 *
 * @author KwabenaEpic
 */
public class Items {
    
    private String plu;
    private String description;
    private String price;

    public Items(String plu, String description, String price) {
        this.plu = plu;
        this.description = description;
        this.price = price;
    }

    @Override
    public String toString() {
        return getPlu() + " " + getDescription() + " " + getPrice() ;
    }

    /**
     * @return the plu
     */
    public String getPlu() {
        return plu;
    }

    /**
     * @param plu the plu to set
     */
    public void setPlu(String plu) {
        this.plu = plu;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(String price) {
        this.price = price;
    }
    
}
