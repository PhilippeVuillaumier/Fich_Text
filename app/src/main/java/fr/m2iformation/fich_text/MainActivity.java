package fr.m2iformation.fich_text;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {
    EditText etNom;
    EditText etPrenom;
    EditText etMail;
    EditText etTelephone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        etNom = findViewById(R.id.etNom);
        etPrenom = findViewById(R.id.etPrenom);
        etMail = findViewById(R.id.etMail);
        etTelephone = findViewById(R.id.etTelephone);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    public void sauver(View view) {
        String text = etNom.getText().toString() + ";" + etPrenom.getText().toString() + ";" + etMail.getText().toString() + ";" + etTelephone.getText().toString() + ";";
        try (FileOutputStream stream = openFileOutput("fichier.txt", MODE_PRIVATE)) {
            stream.write(text.getBytes());
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), LENGTH_LONG).show();
        }
    }

    public void Charger(View view) {
        byte[] bytes = new byte[1024];
        try (FileInputStream stream = this.openFileInput("fichier.txt")) {
            stream.read(bytes);
            String data = new String(bytes, "UTF-8");
            data = data.trim();
            String[] Donnees = data.split(";");
            etNom.setText(Donnees[0]);
            etPrenom.setText(Donnees[1]);
            etMail.setText(Donnees[2]);
            etTelephone.setText(Donnees[3]);
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), LENGTH_LONG).show();
        }
    }

    public void Sauver_Ext(View view) {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            String text = etNom.getText().toString() + ";" + etPrenom.getText().toString() + ";" + etMail.getText().toString() + ";" + etTelephone.getText().toString() + ";";
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            File file = new File(path, "fichier.txt");
            try (FileOutputStream stream = new FileOutputStream(path + "/fichier.txt")) {
                stream.write(text.getBytes());
            } catch (Exception ex) {
                Toast.makeText(this, ex.getMessage(), LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Pas de carte Mémoire :(", LENGTH_LONG).show();
        }
    }

    public void Charger_ext(View view) {
        byte[] bytes = new byte[1024];
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            try (FileInputStream stream = new FileInputStream(path + "/fichier.txt")) {
                stream.read(bytes);
                String data = new String(bytes, "UTF-8");
                data = data.trim();
                String[] Donnees = data.split(";");
                etNom.setText(Donnees[0]);
                etPrenom.setText(Donnees[1]);
                etMail.setText(Donnees[2]);
                etTelephone.setText(Donnees[3]);
            } catch (Exception ex) {
                Toast.makeText(this, ex.getMessage(), LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Pas de carte Mémoire :(", LENGTH_LONG).show();
        }
    }

    public void Sauver_json(View view) {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            String text = "{\"nom\":\"" + etNom.getText().toString() + "\",\"prenom\":\"" + etPrenom.getText().toString() + "\",\"mail\":\"" + etMail.getText().toString() + "\",\"tel\":\"" + etTelephone.getText().toString() + "\"}\n";
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            File file = new File(path, "fichier.json");
            try (FileOutputStream stream = new FileOutputStream(path + "/fichier.json")) {
                stream.write(text.getBytes());
            } catch (Exception ex) {
                Toast.makeText(this, ex.getMessage(), LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Pas de carte Mémoire :(", LENGTH_LONG).show();
        }
    }

    public void Charger_json(View view) {
        byte[] bytes = new byte[1024];
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            try (FileInputStream stream = new FileInputStream(path + "/fichier.json")) {
                stream.read(bytes);
                String data = new String(bytes, "UTF-8");
                data = data.trim();
                JSONObject json = new JSONObject(data);
                etNom.setText(json.getString("nom"));
                etPrenom.setText(json.getString("prenom"));
                etMail.setText(json.getString("mail"));
                etTelephone.setText(json.getString("tel"));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }
    }


}
