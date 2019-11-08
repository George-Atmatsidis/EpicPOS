/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posexample;

import java.io.*;
import java.util.*;

/**
 *
 * @author KwabenaEpic
 */
public class SaleLineItem extends Observable implements Comparable, Serializable{

    private int quantity = 0;
    
    private Item item = new Item("Unknown Item");
    
    private static Item unknownItem = new Item("Unknown Item");
    
    public SaleLineItem(){
        this(0);
    }
    
    public SaleLineItem(int quantity){
        this(unknownItem, quantity);
    }
    
    public SaleLineItem(Item item, int quantity){
        this.quantity = quantity;
        this.item = item;       
    }
    
    public int calcSubTotal(){
        return item.howMuchForQty(quantity, new Date());
    }
    
    public String toString(){
        StringBuffer buf = new StringBuffer();
        buf.append("[");
        buf.append(this.quantity);
        buf.append(", ");
        buf.append(this.item);
        buf.append("]");
        return buf.toString();
    }
    
    @Override
    public int compareTo(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
