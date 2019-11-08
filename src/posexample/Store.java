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
public class Store extends Observable implements Serializable{
    
    private Collection cashiers;
    private Collection items;
    private Collection sales;
    private String nameOfStore;
    
    private static Cashier unkownCashier = new Cashier("unkknown Cashier", "no password");
    
    private static Item unknownItem = new Item("Unknown Item");
    
    public Store(){
        cashiers = new TreeSet();
         items = new TreeSet();
         sales = new ArrayList();
         nameOfStore = "The Out of Twon Department Store";
    }//constructor
    
    public Collection getCashiers(){
        return cashiers;
    }
    
    public Collection getItems(){
        return items;
    }
    
    public Collection getSales(){
        return sales;
    }
    
    public Cashier getCashierForNumber(int n){
        Cashier c;
        for (Iterator i = cashiers.iterator(); i.hasNext();){
            c = (Cashier)i.next();
            if (c.getNumber() == n){
                return c;
            }//if
        }//for
        return unkownCashier;
    }
    
    public void addChashier(Cashier c){
        cashiers.add(c);
    }
    
    public Item getItemForPLU(int n){
        Item item;
        for (Iterator i = items.iterator(); i.hasNext();){
            item = (Item)i.next();
            if (item.hasPLU(n)){
                return item;
            }//if
        }//for
        return unknownItem;
    }
    
    public void addSale(Sale s){
        sales.add(s);
    }//addSale
    
    public void addItem(Item item){
        items.add(items);
    }//addItem
    
    public String toString(){
        return nameOfStore;
    }
    
    
   
    
}
