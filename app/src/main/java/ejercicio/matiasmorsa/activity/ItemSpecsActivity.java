/**
 *  Created by Matias Morsa.
 */

package ejercicio.matiasmorsa.activity;

import android.view.View;
import android.widget.ImageView;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.content.Context;
import android.widget.ImageButton;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import ejercicio.matiasmorsa.R;
import ejercicio.matiasmorsa.Item.Item;
import ejercicio.matiasmorsa.controller.ControllerResults;
import ejercicio.matiasmorsa.controller.ControllerSpecs;


public class ItemSpecsActivity extends AppCompatActivity {

	public static Intent newIntent(Context context) {
		return new Intent(context, ItemSpecsActivity.class);
	}


	private ControllerSpecs controllerItemSpecsSingleton = ControllerSpecs.getInstance();
	private ControllerResults controllerResults = ControllerResults.getInstance();
	private EditText searchItemEditText;
	private ImageButton backButton;
	private ImageButton moreButton;
	private ImageView itemImageImageView;
	private Item item;
	private String query;
	private TextView titleTextView;
	private TextView stockTextView;
	private TextView desciptionTextView;
	private TextView conditionTextView;
	private TextView conditionLabelTextView;
	private TextView priceTextView;
	private TextView priceLabelTextView;
	private TextView locationTextView;
	private TextView quantityLabelTextView;
	private TextView ventasLabelTextView ;
	private TextView quantityTextView;
	private TextView shippingTextView;
	private TextView shippingLabelTextView;


	@Override
	public void onCreate(Bundle _savedInstanceState) {

		super.onCreate(_savedInstanceState);
		this.setContentView(R.layout.item_specs_activity);
		query = controllerResults.getQuery();
		init();
	}

	private void init() {

		// Inicializo el controller
		controllerItemSpecsSingleton.initialize(query,this);

		// Obtengo el item a mostrar
		item = controllerResults.getItem();

		// Configuro la imagen
		itemImageImageView = this.findViewById(R.id.item_image_image_view);
		itemImageImageView.setImageBitmap(controllerResults.getItem().getImageBitmapScaled());

		// Configuro el titulo
		titleTextView = this.findViewById(R.id.tittle_text_view);
		titleTextView.setText(item.getTitle());


		// Configuro Stock (quantity)
		stockTextView = this.findViewById(R.id.stock_text_view);
		stockTextView.setText(item.getBrand());

		// Configuro etiqueta "Caracteristicas"
		desciptionTextView = this.findViewById(R.id.desciption_text_view);
		desciptionTextView.setText("Características");

		// Configuro condicion del item (nuevo o usado)
		conditionTextView = this.findViewById(R.id.condition_text_view);
		conditionTextView.setText(item.getCondition());

		// Configuro etiqueta condicion
		conditionLabelTextView = this.findViewById(R.id.condition_label_text_view);

		// Configuro el precio
		priceTextView = this.findViewById(R.id.price_text_view);
		priceTextView.setText(item.getPrice());

		// Configuro etiqueta precio
		priceLabelTextView = this.findViewById(R.id.price_label_text_view);

		// Configuro buscador
		searchItemEditText = this.findViewById(R.id.search_item_edit_text);
		searchItemEditText.setHint(query);

		// Configuro ubicacion
		locationTextView = this.findViewById(R.id.location_text_view);
		locationTextView.setText(item.getCity());

		// Configuro etiquieta ubicacion
		quantityLabelTextView = this.findViewById(R.id.location_label_text_view);

		// Configuro cantidad de stock
		quantityTextView = this.findViewById(R.id.condition_two_text_view);
		ventasLabelTextView = this.findViewById(R.id.ventas_three_text_view);
		ventasLabelTextView.setText("Cantidad");
		quantityTextView.setText(controllerResults.getItem().getQuantity());

		// Configuro envio gratis
		shippingTextView = this.findViewById(R.id.ventas_text_view);
		shippingLabelTextView = this.findViewById(R.id.ventas_two_text_view);
		shippingLabelTextView.setText("Envio Gratis");
		shippingTextView.setText(controllerResults.getItem().getShipping());

		// Configuro BackButton
		backButton = this.findViewById(R.id.back_button);
		backButton.setOnClickListener((view) -> {
			controllerItemSpecsSingleton.back(query);
		});

	}

	// Botones

	public void onMoreInfo(View view){
		controllerItemSpecsSingleton.onMoreInfo(item);}

	public void onNewQueryItem(View view){
		controllerItemSpecsSingleton.onNewQuery(String.valueOf(this.searchItemEditText.getText()));
	}

}
