package quick.movie.pruebaquickandroid;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    //Parametros del MainActivity
    private String nameMovieSerie = "";
    private int type;
    private TextView quantity;
    private String typeMovieSerie = "";

    //Variables de uso para el SecondActivity
    private ListView listView;
    private JSONArray datosSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        listView = findViewById(R.id.listViewSearch);
        quantity = findViewById(R.id.textViewQuantity);

        //Recogemos el nombre y el tipo del activity anterior
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            nameMovieSerie = bundle.getString("nameMovieSerie");
            type = bundle.getInt("option");
        }

        getData(nameMovieSerie, type);

        //Datos a mostrar
        final ArrayList<String> listSearch = new ArrayList<String>();
        if (datosSearch != null) {
            int len = datosSearch.length();
            for (int i=0;i<len;i++){
                try {
                    listSearch.add(datosSearch.get(i).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //Adaptador
        MyAdapterListMovie myAdapterListMovie = new MyAdapterListMovie(this, R.layout.list_movie, listSearch);

        //Enlazamos adaptador con list view
        listView.setAdapter(myAdapterListMovie);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String currentCliked = listSearch.get(position);
                String nameTitle ="";
                try {
                    JSONObject myObject = new JSONObject(currentCliked);
                    nameTitle = myObject.optString("Title");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                intent.putExtra("nameTitle", nameTitle);
                startActivity(intent);
            }
        });

    }

    public void getData(String name, int type){

        if(type == 1){
            typeMovieSerie = "Movie";
        }else{
            typeMovieSerie = "Serie";
        }
        String apiSearch = "http://www.omdbapi.com/?s="+name+ "&apikey=6c43c325";


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url = null;
        HttpURLConnection conn;

        try {
            url = new URL(apiSearch);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;
            StringBuffer response = new StringBuffer();
            String json;

            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }

            json = response.toString();
            JSONObject myObject = new JSONObject(json);
            quantity.setText(myObject.optString("totalResults")+" Results for "+typeMovieSerie);
            JSONArray jsonArraySearch = myObject.getJSONArray("Search");
            datosSearch = jsonArraySearch;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
