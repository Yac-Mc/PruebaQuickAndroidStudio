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
    private List<String> datosSearch;

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

        //Datos a mostrar
        datosSearch = new ArrayList<String>();
        getData(nameMovieSerie, type);

        //Adaptador
        ArrayAdapter<String> MyAdapterListMovie = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datosSearch);

        //Enlazamos adaptador con list view
        listView.setAdapter(MyAdapterListMovie);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JSONObject myObjectJson = (JSONObject) datosSearch;
                String nameTitle = myObjectJson.optString("Title");
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
        String apiTitle = "http://www.omdbapi.com/?t=matrix&apikey=6c43c325";
        String apiSearch = "http://www.omdbapi.com/?s="+name+ "&apikey=6c43c325";
        String search = "";

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

            for(int i = 0; i < jsonArraySearch.length(); i++ ){
                JSONObject jsonObjectSearch = jsonArraySearch.getJSONObject(i);
                search = jsonObjectSearch.optString("Search");
                datosSearch.add(search);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
