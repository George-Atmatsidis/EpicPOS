/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posexample;

import java.text.DateFormat;
import java.util.*;

/**
 *
 * @author KwabenaEpic
 */
public class PosExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Collection pLUs = new TreeSet();
        
        PLU plu = new PLU();
        
        pLUs.add(plu);
        PLU pl = new PLU();
        pLUs.add(pl);
        
        pLUs.add(plu);
        pLUs.add(plu);
        
        plu = new PLU();
        pLUs.add(plu);
        
        plu = new PLU();
        pLUs.add(plu);
//        
        plu = new PLU();
        pLUs.add(plu);
        
////        for(Iterator i = pLUs.iterator(); i.hasNext();){
//////            plu = (PLU)i.next();
////            System.out.println((PLU)i.next());
////        }
//        
         Iterator i = pLUs.iterator(); 
         while (i.hasNext()){
            Object p = (PLU)i.next();
            System.out.println(p);
        }
   ////        for(int j =0; j< pLUs.size(); j++){
////            pLUs.get(j);
////            System.out.println((pLUs.get(j)));
////        }
//        System.out.println(pLUs.size());
        
//        Collection prices = new TreeSet();
//        
//        Price p = new Price();
//        System.out.println(p);
//        prices.add(p);
//        
//        DateFormat df = DateFormat.getDateInstance();
//        Date d = new Date();
//        try {
//            d = df.parse("19-Oct-98");
//        }
//        catch (java.text.ParseException e){};
//        p = new Price(500,d);
//        System.out.println(p);
//        prices.add(p);
//        Item anItem = new Item();
//        Collection theItems = new TreeSet();
//        
//        Collection thePlUs = new TreeSet();
//        
//        PLU plu = new PLU();
//        
//        thePlUs.add(plu);
//        plu = new PLU();
//        thePlUs.add(plu);
//        
//        thePlUs.add(plu);
//        thePlUs.add(plu);
//        
//        plu = new PLU();
//        thePlUs.add(plu);
//        
//        plu = new PLU();
//        thePlUs.add(plu);
//        
//        plu = new PLU();
//        thePlUs.add(plu);
//        anItem.setPLUs(thePlUs);
//        
//        Price price = new Price();
//        anItem.addPrice(price);
//        
//        DateFormat df = DateFormat.getDateInstance();
//        Date d = new Date();
//        
//        price = new Price(300,d);
//        anItem.addPrice(price);
//        try{
//                d = df.parse("19-Oct-98");
//        }
//        catch (java.text.ParseException e){};
//                price = new Price(300,d);
//                        try{
//                d = df.parse("02-Oct-98");
//        }
//        catch (java.text.ParseException e){};
//        anItem.addPrice(price);
//        price = new Price(370,d);
//        anItem.addPrice(price);
//        price = new Price(37,d);
//        anItem.addPrice(price);
//        
//        System.out.println(anItem);
//        theItems.add(anItem);
//        
//        thePlUs = anItem.getPLUs();
//        
//        Collection thePrices = anItem.getPrices();
//        
//        for(Iterator i = thePlUs.iterator(); i.hasNext();){
//            System.out.println("A" + i.next());
//        }   
//            System.out.println();
//            System.out.println(thePlUs.toString());
//            System.out.println(thePrices.toString());
//            
//         
//        for(Iterator i = thePrices.iterator(); i.hasNext();){
//            System.out.println("A" + i.next());
//        }     
       
    }
    
}
