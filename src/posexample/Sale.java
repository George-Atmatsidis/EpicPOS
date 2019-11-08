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
public class Sale extends Observable implements Serializable {
    
    private static Session unknownSession = new Session();
    
    private static Store unknownStore = new Store();
    
    private Session session;
    
    private Store store;
    
    private Date date;
    
    private Collection saleLineItems;
    
    public Sale(){
        this(unknownSession, unknownStore);
    }
    
    public Sale(Session session, Store store){
        this.session = session;
        this.store = store;
        date = new Date();
        saleLineItems = new ArrayList();
    }
    
    public void addSaleLineItem(Item item, int quantity){
        SaleLineItem lineitem = new SaleLineItem(item, quantity);
        saleLineItems.add(lineitem);
    }//method addSelectionlineItem
    
    public Collection getSaleLineItems(){
        return saleLineItems;
    }
    
    public int calcSubTotal(){
        SaleLineItem saleItem;
        int subTotal = 0;
        for (Iterator i = saleLineItems.iterator(); i.hasNext();){
            saleItem = (SaleLineItem)i.next();
            subTotal += saleItem.calcSubTotal();
        }//for
        return subTotal;
    }
    
    public void display(){
       
    }
    
    public String toString(){
        return "On " + date + " in " + session + " at " + store;
    }//method toStrign
}
