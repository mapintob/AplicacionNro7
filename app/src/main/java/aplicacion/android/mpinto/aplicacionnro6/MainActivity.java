package aplicacion.android.mpinto.aplicacionnro6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private View btn;
    private final String SALUDO = "Hola a todos desde otro activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.buttonMain);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

               Intent intent = new Intent(MainActivity.this,SecondActivity.class);
               intent.putExtra("saludo",SALUDO);
               startActivity(intent);
            }
        });
    }
}
