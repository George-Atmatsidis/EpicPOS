/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posexample;

import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author KwabenaEpic
 */
public class PLU implements Comparable, Serializable {
    
    private static int nextPLU = 0;
    
    public static int valueOfPLU = 0;
    
    public PLU(){
        this(++nextPLU);
    }
    
    public PLU(int plu){
        this.valueOfPLU = plu;
    }
    
    public int getPLU(){
        return valueOfPLU;
    }
    
    public int compareTo(Object obj){
        if ((obj !=null) && (obj instanceof PLU)){
            return valueOfPLU - ((PLU)obj).getPLU();
    }
        else {
            throw new ClassCastException ("" + obj + "is not a PLU");
        }
    }
    
    public boolean equals (Object obj){
        if ((obj != null) && obj instanceof PLU) {
            return valueOfPLU == ((PLU)obj).getPLU();
        }
        return false;
    }
    
    public String toString(){
        return "" + getPLU();
    }
    
}
