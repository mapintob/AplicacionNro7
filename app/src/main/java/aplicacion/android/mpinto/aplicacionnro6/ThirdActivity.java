package aplicacion.android.mpinto.aplicacionnro6;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {
    private EditText editTextPhone;
    private EditText editTextWeb;
    private ImageButton imageButtonPhone;
    private ImageButton imageButtonWeb;
    private ImageButton imageButtonCamera;
    private final int PHONE_CALL_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextWeb = (EditText) findViewById(R.id.editTextWeb);
        imageButtonPhone = (ImageButton) findViewById(R.id.imageButtonPhone);
        imageButtonWeb = (ImageButton) findViewById(R.id.imageButtonWeb);
        imageButtonCamera = (ImageButton) findViewById(R.id.imageButtonCamera);

        imageButtonPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nroTelefono = editTextPhone.getText().toString();
                if (nroTelefono != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PHONE_CALL_CODE);
                        //NewerVersions();
                    } else {
                        olderVersions(nroTelefono);
                    }
                }
            }

            private void olderVersions(String nroTelefono) {
                Intent intentLlamar = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + nroTelefono));
                if (verificarPermiso(Manifest.permission.CALL_PHONE)) {
                    startActivity(intentLlamar);
                } else {
                    Toast.makeText(ThirdActivity.this, "sin permiso para llamadas", Toast.LENGTH_SHORT).show();
                }

            }

            private void NewerVersions() {
                //requestPermissions(new String[]{Manifest.permission.CALL_PHONE},PHONE_CALL_CODE);
            }
        });

        imageButtonWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editTextWeb.getText().toString();
                if (url != null && !url.isEmpty()) {
                    Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + url));
                    startActivity(intentWeb);
                    /*Intent intentWeb = new Intent();
                    intentWeb.setAction(Intent.ACTION_VIEW);
                    intentWeb.setData(Uri.parse("http://"+url));
                    startActivity(intentWeb); */
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PHONE_CALL_CODE:
                String permission = permissions[0];
                int result = grantResults[0];
                if (permission.equals(Manifest.permission.CALL_PHONE)) {
                    //comprobar si ha sido aceptada o denegada la peticion de permiso
                    if (result == PackageManager.PERMISSION_GRANTED) {
                        //tiene permiso
                        String nroTelefono = editTextPhone.getText().toString();
                        Intent intentLlamar = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + nroTelefono));

                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                           return;
                        }
                        startActivity(intentLlamar);
                    } else {
                        // no tiene permiso
                        Toast.makeText(ThirdActivity.this, "sin permiso para llamadas", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private boolean verificarPermiso(String permission){
        int resultado = checkCallingOrSelfPermission(permission);
        return resultado == PackageManager.PERMISSION_GRANTED;
    }
}
