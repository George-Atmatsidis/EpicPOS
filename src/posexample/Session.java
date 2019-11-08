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
public class Session extends Observable implements Comparable, Serializable {
    
    private static String defaultName = "default session";
    
    private String name = "default session";
    
    private Date startDate = new Date();
    
    public Session(){
        this(defaultName);
    }
    
    public Session(Cashier c, Register r){
        this(defaultName);
    }
    
    public Session(String name){
        this(name, new Date());
    }
    
    public Session(String name, Date d){
        this.name = name;
        this.startDate = d;
    }
    
    public String getName(){
        return name;
    }//getName
    
    public String toString(){
        return name + "" + startDate;
    }//method toString
    
    public Date getStartDate(){
        return startDate;
    }//getName
    

    @Override
    public int compareTo(Object obj) {
        
        if ((obj != null ) && (obj instanceof Session)){
            Session s = (Session)obj;
            Date d = getStartDate();
            Date d2 = s.getStartDate();
            if (d.compareTo(d2) != 0){
                return d.compareTo(d2);
            }
            else {
                return getName().compareTo(s.getName());
            }
        }
        else {
            throw new ClassCastException("" + obj + " is not a Session");
        }
        
    }
    
    public boolean equals (Object obj){
        if ((obj !=null ) && (obj instanceof Session)){
            Session s = (Session)obj;
            Date d = getStartDate();
            Date d2 = s.getStartDate();
            return d.equals(d2) && getName().equals(s.getName());
            
        }
        return false;
    }
    
}
