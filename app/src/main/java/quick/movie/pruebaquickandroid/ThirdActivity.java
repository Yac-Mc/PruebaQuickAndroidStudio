package quick.movie.pruebaquickandroid;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ThirdActivity extends AppCompatActivity {

    //Variable para extraer data del json
    private String nameTitle = "";
    private String image = "";

    //Elementos UI
    private ImageView imagen;
    private TextView nameTitleDetail;
    private TextView yearDetail;
    private TextView calificationValue;
    private TextView genreDetail;
    private TextView actorDetail;
    private TextView descriptionDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        imagen = findViewById(R.id.imageViewDetail);
        nameTitleDetail = findViewById(R.id.textViewTitleDetail);
        yearDetail = findViewById(R.id.textViewYear);
        calificationValue = findViewById(R.id.textViewCalification);
        genreDetail = findViewById(R.id.textViewGenre);
        actorDetail = findViewById(R.id.textViewActor);
        descriptionDetail = findViewById(R.id.textViewDescription);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            nameTitle = bundle.getString("nameTitle");
        }

        //Llamado de la data
        getDataDetail(nameTitle);

        Picasso.get()
                .load(image)
                .error(R.mipmap.ic_launcher)
                .fit()
                .centerInside()
                .into(imagen);
    }

    public void getDataDetail(String nameTitle){
        String apiTitle = "http://www.omdbapi.com/?t="+ nameTitle +"&apikey=6c43c325";
        String ratingsValue = "";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url = null;
        HttpURLConnection conn;

        try {
            url = new URL(apiTitle);
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

            nameTitleDetail.setText(myObject.optString("Title"));
            image = myObject.optString("Poster");
            yearDetail.setText(myObject.optString("Year"));
            genreDetail.setText(myObject.optString("Genre"));
            actorDetail.setText(myObject.optString("Actors"));
            descriptionDetail.setText(myObject.optString("Plot"));

            JSONArray jsonArrayRatings = myObject.getJSONArray("Ratings");

            for(int i = 0; i < jsonArrayRatings.length(); i++ ){
                JSONObject jsonObjectRatings = jsonArrayRatings.getJSONObject(i);
                ratingsValue = jsonObjectRatings.optString("Value");
                calificationValue.setText(ratingsValue);
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
