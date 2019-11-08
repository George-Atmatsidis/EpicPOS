/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posexample;

import java.text.DateFormat;
import java.util.*;
import java.io.*;
import javax.swing.*;

/**
 *
 * @author KwabenaEpic
 */
public class Price extends Observable implements Comparable, Serializable{
 
    
    private Date effectiveDate = new Date();
    
    private int price = 0;
    
    public Price(){
        this(500);
    }
    
    public Price(int price){
        this(price, new Date());
    }
    
    public Price(int price, Date d){
        this.effectiveDate = d;
        this.price = price;
    }
    
    public String toString(){
        DateFormat df = DateFormat.getDateInstance();
        return df.format(getEffectiveDate()) + '\t' + 
        Integer.toString(price);
    }
    
    public Date getEffectiveDate(){
        return effectiveDate;
    }
    
    public void setEffectiveDate(Date d){
        this.effectiveDate = d;
    }
    
    public int getPrice(){
        return price;
    }
    public void setPrice(int p){
        this.price = p;
    }
    
    public int compareTo(Object obj){
        if ((obj !=null) && (obj instanceof Price)) {
            Price p = (Price)obj;
            Date d = getEffectiveDate();
            Date d2 = p.getEffectiveDate();
            if (d.compareTo(d2) != 0){
                return -(d.compareTo(d2));
            }
            else {
                return getPrice() - p.getPrice();
            }
        }
        else {
            throw new ClassCastException ("" + obj + " is not a Price");
        }
    }
    
    public boolean equals(Object obj){
        if ((obj != null) && (obj instanceof Price)){
            Date d = getEffectiveDate();
            Date d2 = ((Price)obj).getEffectiveDate();
            return d.equals(d2) && (getPrice() == ((Price)obj).getPrice());
    }
        return false;
    }
}
