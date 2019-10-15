package ejercicio.matiasmorsa.controller;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import ejercicio.matiasmorsa.activity.InitialActivity;
import ejercicio.matiasmorsa.activity.ResultsActivity;
import ejercicio.matiasmorsa.repository.ItemRepo;

public class ControllerInitial implements Controller {

    //Constructor Singleton
    private ControllerInitial(){}

    public static ControllerInitial getInstance(){
        if (sSoleInstance == null){
            sSoleInstance = new ControllerInitial();
        }

        return sSoleInstance;
    }

    private static ControllerInitial sSoleInstance;
    private static ItemRepo itemRepo = ItemRepo.getInstance();
    private InitialActivity initialActivity;
    private String query;


    public void initialize(InitialActivity _initialActivity){
        initialActivity = _initialActivity;
    }


    // Recibe notificacion de que ya se cargaron los resultados
    public void loadReady() {
        initialActivity.startActivity(ResultsActivity.newIntent(initialActivity));
        Intent i = new Intent(initialActivity, ResultsActivity.class);

        // Envio la query ingresada por el usuario
        i.putExtra("query",query);
        initialActivity.startActivity(i);
    }

    //Solicita al repo que cargue los articulos
    public void loadResultsActivity(String _query,int _offset) {
        if(!_query.isEmpty()){

            RequestQueue requestQueue = Volley.newRequestQueue(initialActivity.getBaseContext());
            requestQueue.cancelAll(initialActivity.getBaseContext());
            this.query = _query;
            itemRepo.loadArticulos(query, _offset, initialActivity.getBaseContext(), this);
        }else {
            Toast.makeText(initialActivity, "Por favor ingrese un Item para buscar", Toast.LENGTH_SHORT).show();

           }

    }

    // Interval DEFAULT = 0 (pagina 0)
    public void loadResultsActivity(String _query) {
            this.loadResultsActivity(_query,0);
     }


    public void onFaq() {
        String urlString = "https://portal-cbt.mercadolibre.com/faqs";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");
        try {
            initialActivity.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            // Chrome browser presumably not installed so allow user to choose instead
            //
            intent.setPackage(null);
            initialActivity.startActivity(intent);
        }
    }
}
