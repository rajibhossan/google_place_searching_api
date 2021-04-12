/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.opencsv.CSVWriter;
import domain.Place;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Md. Rajib Hossain
 */
public class ExportCSVService {

    public static void saveCSVFile(List<Place> list, File file) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(file.getAbsoluteFile()));

            //Writting Header Values
            String line1[] = {"Name", "Address", "City", "State", "Zip code"};
            writer.writeNext(line1);
            for (Place p : list) {
                String data[] = {p.getName(), p.getAddress(), p.getCity(), p.getState(), p.getZipCode()};
                writer.writeNext(data);
            }
            writer.flush();
            
            
            
        } catch (IOException ex) {
            System.out.println("CSV saving problems!");
        }
    }
}
