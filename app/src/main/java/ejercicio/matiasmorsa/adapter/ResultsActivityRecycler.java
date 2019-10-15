/**
 *  Created by Matias Morsa.
 */

package ejercicio.matiasmorsa.adapter;



import android.net.Uri;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import java.util.*;
import android.view.View;
import android.widget.ImageView;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;
import ejercicio.matiasmorsa.R;
import ejercicio.matiasmorsa.activity.ResultsActivity;
import ejercicio.matiasmorsa.Item.Item;
import ejercicio.matiasmorsa.controller.ControllerResults;
import ejercicio.matiasmorsa.repository.ItemRepo;


public class ResultsActivityRecycler extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	public static final int RESULTS_VIEW_HOLDER_VIEW_TYPE = 1;
	public static final List<Integer> FIVE_CELLS = Arrays.asList(RESULTS_VIEW_HOLDER_VIEW_TYPE,RESULTS_VIEW_HOLDER_VIEW_TYPE,RESULTS_VIEW_HOLDER_VIEW_TYPE,RESULTS_VIEW_HOLDER_VIEW_TYPE);

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		switch (viewType) {
			case RESULTS_VIEW_HOLDER_VIEW_TYPE:
				return new ResultsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.results_view_holder, parent, false));
		}

		throw new RuntimeException("Unsupported view type");
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

	}

	@Override
	public int getItemViewType(int position) {
		return FIVE_CELLS.get(position);
	}

	@Override
	public int getItemCount() {
		return FIVE_CELLS.size();
	}


	public static class ResultsViewHolder extends RecyclerView.ViewHolder {
		private AppCompatImageButton specificationsButton = null;
		private AppCompatButton specificationsButton2 = null;
		private ControllerResults controllerResults = ControllerResults.getInstance();
		private ImageView itemImageView = null;
		private ItemRepo itemRepo;
		private Item item;
		private ResultsActivity resultsActivity;
		private TextView itemNameTextView = null;
		private TextView descriptionTextView = null;
		private TextView priceTextView = null;



		public ResultsViewHolder(View itemView) {
			super(itemView);
			itemRepo = ItemRepo.getInstance();
			resultsActivity = controllerResults.getResultActivity();
			item = itemRepo.pushArticulo();
			this.init();
		}


		public void init() {

			// Configuro imagen del item
			itemImageView = this.itemView.findViewById(R.id.item_image_view);
			itemImageView.setImageURI(null);
			itemImageView.setImageBitmap(item.getImageBitmapScaledMini());



			// Configuro el nombre
			itemNameTextView = this.itemView.findViewById(R.id.item_name_text_view);
			itemNameTextView.setText(item.getTitle());


			// Configuro la descripcion
			descriptionTextView = this.itemView.findViewById(R.id.description_text_view);
			descriptionTextView.setText(item.getCondition());

			// Configuro el price
			priceTextView = this.itemView.findViewById(R.id.price_text_view);
			priceTextView.setText(item.getPrice());

			// Configure botton > para ver mas
			specificationsButton = this.itemView.findViewById(R.id.specifications_button);
			specificationsButton.setOnClickListener((view) -> {
				this.onMorePressed();
			});

			// Configuro que el item entero sea un boton
			specificationsButton2 = this.itemView.findViewById(R.id.specification2_button);
			specificationsButton2.setOnClickListener((view) -> {
				this.onMorePressed();
			});
		}

		public void onMorePressed() {
			controllerResults.startItemSpecsActivity(item);
		}


	}
}