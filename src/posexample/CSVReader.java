/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posexample;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author KwabenaEpic
 */
public class CSVReader {
   
    
    private static class Item{
        private String plu;
        private String description;
        private String price;
        
        public Item(String plu, String description, String price){
            this.plu = plu;
            this.description = description;
            this.price = price;
        }
        
        public String plu(){
            return plu;
        }
        public String description(){
            return description;
        }
        public String price(){
            return price;
        }
        
        public String toString(){
            return "Item [plu" + plu +", description =" + description + ", price=" + price + "]";
        }
        
    } 
    
    public static List readCSV() throws FileNotFoundException,IOException{
        List items = new ArrayList();
    
    BufferedReader br = new BufferedReader(new FileReader("SampleCSVFile.csv"));
    
    String line = br.readLine();
    
    while ((line = br.readLine()) != null && !line.isEmpty()){
            String[] fields = line.split(",");
            String plu = fields[0];
            String description = fields[1];
            String price = fields[2];
            Item item = new Item (plu, description, price);
            items.add(item);
            
    }
            br.close();
            return items;
    }
  
    public static void print(List items){
        for (Object item : items){
            System.out.print(item);
        }
    }
}
