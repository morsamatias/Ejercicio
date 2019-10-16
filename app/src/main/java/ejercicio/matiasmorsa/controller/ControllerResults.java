package ejercicio.matiasmorsa.controller;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import ejercicio.matiasmorsa.activity.InitialActivity;
import ejercicio.matiasmorsa.activity.ItemSpecsActivity;
import ejercicio.matiasmorsa.activity.ResultsActivity;
import ejercicio.matiasmorsa.Item.Item;
import ejercicio.matiasmorsa.repository.ItemRepo;

public class ControllerResults implements Controller {


    private ControllerResults(){}  //private constructor

    public static ControllerResults getInstance(){
        if (sSoleInstance == null){ //if there is no instance available... create new one
            sSoleInstance = new ControllerResults();
        }

        return sSoleInstance;
    }

    private static ControllerResults sSoleInstance;
    private static ItemRepo itemRepo = ItemRepo.getInstance();
    private ControllerInitial controllerInitial = ControllerInitial.getInstance();
    private int interval = 0;
    private Item item;
    private ResultsActivity resultsActivity;
    private String query;

    public void initialize(String query, ResultsActivity resultsAct){
        this.query = query;
        this.resultsActivity = resultsAct;
    }

    @Override
    public void loadResultsActivity() {

    }

    // GETTERS
    public Item getItem(){return item;}
    public String getQuery() {
        return query;
    }
    public ResultsActivity getResultActivity(){
        return this.resultsActivity;
    }


    // BOTONES

    public void startItemSpecsActivity(Item item){
        itemRepo.clean();
        this.item = item;
        resultsActivity.startActivity(ItemSpecsActivity.newIntent(resultsActivity));
        Intent i = new Intent(resultsActivity, ItemSpecsActivity.class);
        resultsActivity.startActivity(i);
    }

    public void onBackPressed() {
        resultsActivity.startActivity(InitialActivity.newIntent(resultsActivity));
        Intent i = new Intent(resultsActivity, InitialActivity.class);
        resultsActivity.startActivity(i);
    }

    public void onMore(String _newQuery) {
        if(_newQuery.isEmpty()) {
            Toast.makeText(resultsActivity, "Por favor ingrese un texto para buscar", Toast.LENGTH_SHORT).show();
        }else{
            controllerInitial.loadItems(_newQuery);
        }
    }

    public void onNewQuery(String newQuery) {

        if(!newQuery.isEmpty()) {
            Log.i("NEW QUERY =", newQuery);
            controllerInitial.loadItems(newQuery);
        }else{
            Toast.makeText(resultsActivity, "Por favor ingrese un texto para buscar", Toast.LENGTH_SHORT).show();
        }

    }

    // PAGINACION DE LOS RESULTADOS

    public void addInterval() {
        interval += 4;
        Log.i("Interval = ", String.valueOf(interval));
        controllerInitial.loadItems(query, interval);
    }

    public void decreaseInterval(){
        interval -= 4;
        if (interval >= 0){
            controllerInitial.loadItems(query, interval);
        }else{
            interval = 0;
        }
    }
}
