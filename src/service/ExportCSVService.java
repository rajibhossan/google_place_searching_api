package service;

import com.opencsv.CSVWriter;
import model.Place;
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
