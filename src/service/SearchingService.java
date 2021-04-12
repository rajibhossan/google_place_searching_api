package service;

import model.Place;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Md. Rajib Hossain
 */
public class SearchingService {

    //KEY
    static final String KEY = "";// add your key
    //PLACE AUTO COMPLETE URL
    static final String BASE_URL_AUTO_COMPLETE = "https://maps.googleapis.com/maps/api/place/autocomplete/json?";
    //PLACE DETAILS URL
    static final String BASE_URL_PLACE_DETAILS = "https://maps.googleapis.com/maps/api/place/details/json?";

    private URL url;
    private URLConnection urlcon;
    private InputStream stream;    
    private ParsingService ps;

    /**
     *
     * @param String query
     * @return List<String> of place_id
     */
    public List<String> executePlaceAutoQuery(String query) {

        List<String> placelist = null;

        try {
            url = new URL(placeAutoComplete(query));
            urlcon = url.openConnection();
            stream = urlcon.getInputStream();

            String list = readResponse(stream);
            ps = new ParsingService();
            placelist = ps.getPlaceId(list);

        } catch (Exception e) {
            System.out.println(e);           
        }

        return placelist;
    }

    /**
     *
     * @param String query
     * @return List<Place> of Place
     */
    public List<Place> getplaces(String query) {

        List<String> placeId = executePlaceAutoQuery(query);
        ps = new ParsingService();
        List<Place> placelist = new ArrayList();

        try {

            String json = "";

            for (String id : placeId) {
                url = new URL(placeDetails(id));
                urlcon = url.openConnection();
                stream = urlcon.getInputStream();

                json = readResponse(stream);
                ps = new ParsingService();
                placelist.add(ps.getPlace(json));
            }

        } catch (Exception e) {
            System.out.println(e);           
        }

        return placelist;
    }

    private static String placeAutoComplete(String args) {

        StringBuilder sb = new StringBuilder(BASE_URL_AUTO_COMPLETE);
        sb.append("input=".concat(args));
        sb.append("&");
        sb.append("language=en");
        sb.append("&");
        //sb.append("fields=place_id");
        sb.append("&");
        sb.append("key=".concat(KEY));
        //System.out.println(sb.toString());

        return sb.toString();
    }

    private static String placeDetails(String placeId) {
        
        StringBuilder sb = new StringBuilder(BASE_URL_PLACE_DETAILS);
        sb.append("place_id=".concat(placeId));
        sb.append("&");
        sb.append("fields=name,address_components,vicinity");
        sb.append("&");
        sb.append("key=".concat(KEY));
        //System.out.println(sb.toString());

        return sb.toString();
    }

    /**
     *
     * @param InputStream
     * @return String
     */
    public String readResponse(InputStream stream) {
        
        String s = "";
        
        try {            
            int i;            
            while ((i = stream.read()) != -1) {
                s += (char) i;
            }
        } 
        catch (IOException e) {
            System.out.println(e);
        }
        
        return s;
    }
}
