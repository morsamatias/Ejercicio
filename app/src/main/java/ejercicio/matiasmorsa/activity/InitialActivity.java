/**
 *  Created by Matias Morsa.
 */

package ejercicio.matiasmorsa.activity;


import android.widget.Button;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import ejercicio.matiasmorsa.R;
import ejercicio.matiasmorsa.controller.ControllerInitial;


public class InitialActivity extends AppCompatActivity {

	public static Intent newIntent(Context context) {
		return new Intent(context, InitialActivity.class);
	}

	private Button searchButton;
	private Button faqButton;
	private ConstraintLayout gradientConstraintLayout;
	private ConstraintLayout searchConstraintLayout;
	private ControllerInitial controllerInitial = ControllerInitial.getInstance();
	private EditText searchedItemEditText;
	private TextView sloganTextView;
	private Toolbar toolbar;

	@Override
	public void onCreate(Bundle _savedInstanceState) {

		super.onCreate(_savedInstanceState);
		this.setContentView(R.layout.initial_activity);
		this.init();
		controllerInitial.initialize(this);
	}

	private void init() {

		// Configuro NavigationBar
		toolbar = this.findViewById(R.id.toolbar);

		// Configuro Login
		gradientConstraintLayout = this.findViewById(R.id.gradient_constraint_layout);

		// Configuro Slogan
		sloganTextView = this.findViewById(R.id.slogan_text_view);

		// Configuro Searchfield
		searchConstraintLayout = this.findViewById(R.id.search_constraint_layout);

		// Configuro SearchItem
		searchedItemEditText = this.findViewById(R.id.searched_item_edit_text);

		// Configuro SearchButton
		searchButton = this.findViewById(R.id.search_button);
		searchButton.setOnClickListener((view) -> {
			controllerInitial.loadItems(String.valueOf(this.searchedItemEditText.getText()));
		});

		// Configuro FAQ
		faqButton = this.findViewById(R.id.faq_button);
		faqButton.setOnClickListener((view) -> {
			controllerInitial.onFaq();
		});

		this.setupToolbar();
	}

	public void setupToolbar() {
		setSupportActionBar(toolbar);
	}

}
