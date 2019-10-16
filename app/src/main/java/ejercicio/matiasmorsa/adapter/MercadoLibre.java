package ejercicio.matiasmorsa.adapter;

import android.content.Context;
import android.util.Log;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ejercicio.matiasmorsa.Item.Item;
import ejercicio.matiasmorsa.repository.ItemRepo;


public class MercadoLibre {

    // Singleton
    private MercadoLibre(){}  //private constructor.

    public static MercadoLibre getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new MercadoLibre();
        }

        return sSoleInstance;
    }

    private static MercadoLibre sSoleInstance;



    // Obtengo la respuesta para la query del usuario, la parsea y
    // la carga directamente al repositorio
    public void getArticulos(String query, int offset, Context context) {


        ItemRepo itemRepo = ItemRepo.getInstance();
        itemRepo.clean();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.cancelAll(context);

        String url ="https://api.mercadolibre.com/sites/MLA/search?q="+	query+ "&offset=" + offset + "&limit=4";

        Log.i("/// URL = ",url);

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject c = jsonArray.getJSONObject(i);

                        Item item = parseArticulo(c);
                        itemRepo.addArticulo(item);

                    }
                    itemRepo.loadReady();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    //Parseo la respuesta del JSON para convertirla en un Item
    public Item parseArticulo(JSONObject c) throws JSONException {
        Item item = new Item();

        item.setTitle(c.getString("title"));
        item.setId(c.getString("id"));
        item.setPrice(String.valueOf(c.getInt("price")));
        item.setImageURL(c.getString("thumbnail"));
        item.setPermalink(c.getString("permalink"));
        item.setCondition(c.getString("condition"));
        item.setQuantity(c.getString("available_quantity"));
        item.setAccepts_mercadopago(c.getString("accepts_mercadopago"));
        item.setCity(c.getJSONObject("address").getString("city_name"));
        item.setShipping(c.getJSONObject("shipping").getString("free_shipping"));
        JSONArray  jsonAtributes =  c.getJSONArray("attributes");
        JSONObject brand = jsonAtributes.getJSONObject(0);
        item.setBrand(brand.getString("value_name"));

        return item;
    }

}
