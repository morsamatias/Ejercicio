package ejercicio.matiasmorsa.repository;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import ejercicio.matiasmorsa.Item.Item;
import ejercicio.matiasmorsa.controller.Controller;
import ejercicio.matiasmorsa.adapter.MercadoLibre;


public class ItemRepo {

    private ItemRepo(){} //private constructor

    public static ItemRepo getInstance(){
        if (sSoleInstance == null){ //if there is no instance available... create new one
            sSoleInstance = new ItemRepo();
        }
        return sSoleInstance;
    }


    private static ItemRepo sSoleInstance;
    private MercadoLibre mercadoLibre;
    private Controller controller;
    private List<Item> itemList = new ArrayList<Item>();



    public void addArticulo(Item _item){
        if(itemList == null){
            itemList = new ArrayList<Item>();
        }
        if(itemList.size()>4){
            itemList.remove(0);
        }
            itemList.add(_item);}


    public Item pushArticulo(){
        if(itemList.size()>0) {
            Item art = itemList.get(itemList.size()-1);
            Log.i("//// itemList.get(0)",art.getTitle());
            Log.i("//// itemList.size()", String.valueOf(itemList.size()));
            itemList.remove(art);
            return art;
        }else{
            Log.w("Error Size  itemList.size() = 0 ", "No puede entregar mas articulos");
                    return null;
        }
    }

    public void loadArticulos(String _query, int _interval, Context _context, Controller _controller){
        controller = _controller;
        mercadoLibre = MercadoLibre.getInstance();
        mercadoLibre.getArticulos(_query,_interval,_context);
    }

    public void loadReady(){
        controller.loadResultsActivity();
    }
    public  void clean(){ itemList = null;}


}
