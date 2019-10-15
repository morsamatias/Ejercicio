/**
 *  Created by Matias Morsa.
 */

package ejercicio.matiasmorsa.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import java.io.IOException;
import ejercicio.matiasmorsa.R;
import ejercicio.matiasmorsa.adapter.ResultsActivityRecycler;
import ejercicio.matiasmorsa.controller.ControllerResults;
import ejercicio.matiasmorsa.adapter.MercadoLibre;
import ejercicio.matiasmorsa.repository.ItemRepo;
import io.supernova.uitoolkit.drawable.LinearGradientDrawable;

public class ResultsActivity extends AppCompatActivity {


	public static Intent newIntent(Context context) {

		// Fill the created intent with the data you want to be passed to this Activity when it's opened.
		return new Intent(context, ResultsActivity.class);
	}


	public static final int NEXT = 1;
	public static final int PREVIOUS = 2;
	private AppCompatButton next;
	private AppCompatButton previous;
	private ConstraintLayout resultsConstraintLayout;
	private ControllerResults controllerResults = ControllerResults.getInstance();
	private EditText searchItemEditText;
	private ImageButton backButton;
	private ImageButton newQuery;
	private ItemRepo itemRepo = ItemRepo.getInstance();
	private MercadoLibre mercadoLibre;
	private RecyclerView resultsRecyclerView;
	private String query;

	@Override
	public void onCreate(Bundle _savedInstanceState) {
		Intent i = getIntent();
		query = i.getStringExtra("query");
		itemRepo.saveContext(this);
		controllerResults.initialize(query,this);
		super.onCreate(_savedInstanceState);
		this.setContentView(R.layout.results_activity);
		mercadoLibre = MercadoLibre.getInstance();

		// Para poder mostrar las imagenes del thumbnail
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		try {
			this.init();
		} catch (IOException e) {
			e.printStackTrace();
		}

		searchItemEditText.setHint(query);
	}

	private void init() throws IOException {


		// Configuro el Fondo
		resultsConstraintLayout = this.findViewById(R.id.gradient_constraint_layout);
		resultsConstraintLayout.setBackground(new LinearGradientDrawable.Builder(this, new PointF(0.5f, 0f), new PointF(0.5f, 1f)).addStop(0.37f, Color.argb(255, 255, 230, 0)).addStop(1f, Color.argb(255, 255, 255, 255)).build());

		// Configuro Results component
		resultsRecyclerView = this.findViewById(R.id.results_recycler_view);
		resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		resultsRecyclerView.setAdapter(new ResultsActivityRecycler());

		// Configuro Back
		backButton = this.findViewById(R.id.back_button);
		backButton.setOnClickListener((_view) -> {
			controllerResults.onBackPressed();
		});

		newQuery = this.findViewById(R.id.more_button);
		newQuery.setOnClickListener((_view)->{
			controllerResults.onNewQuery(this.searchItemEditText.getText().toString());
		});

		// Mostrar siguiente paguina
		next = this.findViewById(R.id.next_button);
		next.setOnClickListener((_view)->{
			controllerResults.addInterval();
		});

		// Mostrar pagina anterior
		previous = this.findViewById(R.id.previous_button);
		previous.setOnClickListener((_view)->{
			controllerResults.decreaseInterval();
		});


		searchItemEditText = this.findViewById(R.id.search_item_edit_text);
	}

	// BOTONES

	public void onNewQuery(View _view){
		controllerResults.onMore(String.valueOf(searchItemEditText));
	}

}
