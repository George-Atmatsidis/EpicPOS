/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posexample;

import java.util.*;
import java.io.*;
/**
 *
 * @author KwabenaEpic
 */
public class Item extends Observable implements Comparable, Serializable{
    
    private static int nextItemNumber  = 0;
    
    private String description = "description";
    
    private int number = 0;
    
    private Collection pLUs = new TreeSet();
    
    private Collection prices = new TreeSet();
    
    private static Price unknownPrice = new Price(0);
    
    private static PLU unknownPLU = new PLU(0);
    
    public Item(){
    this("the item's description");
    }
    
    public Item(String description){
        this(description, unknownPrice);
    }
    
    public Item(String description, Price price){
        this(description, unknownPLU, price);
    }
    
    public Item(String description,PLU plu,Price price){
        this.description = description;
        addPLU(plu);
        addPrice(price);
        this.number = ++nextItemNumber;
    }
    
    public String toString(){
        char newLine = (char)Character.LINE_SEPARATOR;
        return number + " " + description;
    }
    
    public String getDescription(){
        return description;
    }
    
    public int getNumber(){
        return number;
    }
    
    public boolean hasPLU(int n){
        PLU plu = new PLU(n);
        return hasPLU(plu);
    }
    
    public Collection getPLUs(){
        return pLUs;
    }
    
    public void setPLUs(Collection p){
        pLUs = p;
    }
    
    public boolean hasPLU( PLU plu){
            return pLUs.contains(plu);
    }
    
    public void addPLU(PLU u){
        pLUs.add(u);
    }
    
    public Collection getPrices(){
        return prices;
    }
    
    public void addPrice(Price p){
        prices.add(p);
    }
    
    public Price getPriceForDate(Date d){
        Price price;
        Collection thePrices = getPrices();
        for (Iterator i = thePrices.iterator(); i.hasNext();){
            price = (Price)i.next();
            System.out.println("" + price + "" + price.getEffectiveDate());
            if (d.after(price.getEffectiveDate())){
                return price;
            }
        }
        return unknownPrice;
       }
    
    public int howMuchForQty(int qty, Date d){
        Price price = this.getPriceForDate(d);
        int forQty = price.getPrice()*qty;
        return forQty;
    }
    
    

    @Override
    public int compareTo(Object obj) {
        if ((obj !=null) && (obj instanceof Item)){
            Item i = (Item)obj;
            if (getDescription().compareTo(i.getDescription()) != 0 ){
            return getDescription().compareTo(i.getDescription());
        }
            else {
                    return getNumber() - i.getNumber();
                    }
        }
        else {
            throw new ClassCastException("" + obj + "is not a Item");
        }
    }
    
}
