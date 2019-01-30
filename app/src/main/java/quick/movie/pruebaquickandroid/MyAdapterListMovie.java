package quick.movie.pruebaquickandroid;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyAdapterListMovie extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<String> search;


    public MyAdapterListMovie(Context context, int layout, ArrayList<String> search){
        this.context = context;
        this.layout = layout;
        this.search = search;
    }
    @Override
    public int getCount() { return this.search.size(); }

    @Override
    public Object getItem(int position) {
        return this.search.get(position);
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

        //Variables que se agregan al adaptador
        String currentTitle = "";
        String currentYear = "";

        //Se trae el valor actual dependiente de la posición
        JSONObject myObject = new JSONObject((Map) search);
        Log.d("SALIDA: ", myObject.optString("Title"));

        try {
            JSONArray jsonArraySearch = myObject.getJSONArray("Search");
            for(int i = 0; i < jsonArraySearch.length(); i++ ){
                JSONObject jsonObjectSearch = jsonArraySearch.getJSONObject(i);
                currentTitle = jsonObjectSearch.optString("Title");
                currentYear = jsonObjectSearch.optString("Year");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //Referenciamos el elemento a modificar y lo rellenamos
        ImageView imagen = v.findViewById(R.id.imageViewSearch);
        TextView textViewTitle = v.findViewById(R.id.textViewTitleSearch);
        textViewTitle.setText(currentTitle);
        TextView textViewYear = v.findViewById(R.id.textViewYearSearch);
        textViewYear.setText(currentYear);

        Picasso.get()
                .load(myObject.optString("Poster"))
                .error(R.mipmap.ic_launcher)
                .fit()
                .centerInside()
                .into(imagen);

        //Devolvemos la vista inflada y modificada con los datos
        return v;
    }

}
