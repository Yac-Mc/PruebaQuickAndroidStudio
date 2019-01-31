package quick.movie.pruebaquickandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyAdapterListMovie extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<String> listSearch;


    public MyAdapterListMovie(Context context, int layout, ArrayList<String> listSearch){
        this.context = context;
        this.layout = layout;
        this.listSearch = listSearch;
    }
    @Override
    public int getCount() { return this.listSearch.size(); }

    @Override
    public Object getItem(int position) {
        return this.listSearch.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Copiamos la vista que nos pasa el metodo
        View v = convertView;

        //Inflamos la vista que nos ha llegado con el layout personalizado
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        v = layoutInflater.inflate(R.layout.list_movie, null);

        //Variables del objeto
        String currentImagen = "";
        String currentTitle = "";
        String currentYear = "";

        String currentSearch = listSearch.get(position);
        try {
            JSONObject myObject = new JSONObject(currentSearch);
             currentImagen = myObject.optString("Poster");
             currentTitle = myObject.optString("Title");
             currentYear = myObject.optString("Year");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Referenciamos el elemento a modificar y lo rellenamos
        TextView textViewTitle = v.findViewById(R.id.textViewTitleSearch);
        textViewTitle.setText(currentTitle);
        TextView textViewYear = v.findViewById(R.id.textViewYearSearch);
        textViewYear.setText(currentYear);
        ImageView imagen = v.findViewById(R.id.imageViewSearch);

        Picasso.get()
        .load(currentImagen)
        .error(R.mipmap.ic_launcher)
        .fit()
        .centerInside()
        .into(imagen);

        return v;
    }
}