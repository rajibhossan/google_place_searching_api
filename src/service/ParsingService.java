package service;

import model.Place;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author Md. Rajib Hossain
 */
public class ParsingService {

    public List<String> getPlaceId(String jsonString) {

        List<String> placeIdList = new ArrayList<>();

        Object obj = JSONValue.parse(jsonString);
        JSONObject jsonObject = (JSONObject) obj;

        String status = (String) jsonObject.get("status");
        if (!status.equals("OK")) {
            return null;
        }

        JSONArray jsonObject1 = (JSONArray) jsonObject.get("predictions");

        jsonObject1.forEach(item -> {
            JSONObject placeObject = (JSONObject) item;
            String placeId = (String) placeObject.get("place_id");
            placeIdList.add(placeId);
            //System.out.println(placeId);
        });

        return placeIdList;

    }

    public Place getPlace(String jsonPlaceObject) {

        Object obj = JSONValue.parse(jsonPlaceObject);
        JSONObject jsonObject = (JSONObject) obj;

        String status = (String) jsonObject.get("status");

        if (!status.equals("OK")) {
            return null;
        }

        JSONObject ar1 = (JSONObject) jsonObject.get("result");

        Place place = new Place();
        place.setName((String) ar1.get("name"));
        place.setAddress((String) ar1.get("vicinity"));

        JSONArray jsonObject1 = (JSONArray) ar1.get("address_components");

        jsonObject1.forEach(item -> {

            JSONObject f = (JSONObject) item;
            JSONArray typeArray = (JSONArray) f.get("types");
            String type = (String) typeArray.get(0);

            switch (type) {
                case "postal_code": {//postal code
                    place.setZipCode((String) f.get("long_name"));
                    break;
                }
                case "locality": {//city or town
                    place.setCity((String) f.get("long_name"));
                    break;
                }
                case "administrative_area_level_1": {//state
                    place.setState((String) f.get("long_name"));
                    break;
                }
                default:
                    break;
            }

        });

        return place;
    }

}
