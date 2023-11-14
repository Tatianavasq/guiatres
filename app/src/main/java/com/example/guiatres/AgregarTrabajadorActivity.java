package com.example.guiatres;

import static com.example.guiatres.MainActivity.lstTrabajadores;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.guiatres.modelo.TrabajadorHora;
import com.example.guiatres.modelo.TrabajadorTiempoCompleto;

public class AgregarTrabajadorActivity extends AppCompatActivity {
    Button btnAgregar;
    EditText edtSalario, edtCodigo, edtNombre, edtApellido, edtEdad, edtValor, edtHora;
    //TextView tvSalario, tvCodigo, tvNombre, tvApellido, tvEdad, tvValor, tvHora;
    private Bundle bundle;
    private int idEleccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_trabajador);

        btnAgregar = findViewById(R.id.btnAgregar);

        edtSalario = findViewById(R.id.edtSalario);
        edtCodigo = findViewById(R.id.edtCodigo);
        edtNombre = findViewById(R.id.edtNombre);
        edtApellido = findViewById(R.id.edtApellido);
        edtEdad = findViewById(R.id.edtEdad);
        edtValor = findViewById(R.id.edtValor);
        edtHora = findViewById(R.id.edtHora);

        //tvSalario = findViewById(R.id.tvSalario);
        //tvValor = findViewById(R.id.tvValor);
        //tvHora= findViewById(R.id.tvHora);

        bundle = getIntent().getExtras();
        idEleccion = bundle.getInt("tipoEleccion");

        if(idEleccion == 1){
            // Es Trabajador hora
            //tvSalario.setVisibility(View.GONE);
            edtSalario.setVisibility(View.GONE);

        }else{
            // Es tiempo completo
            //tvValor.setVisibility(View.GONE);
            edtValor.setVisibility(View.GONE);
            //tvHora.setVisibility(View.GONE);
            edtHora.setVisibility(View.GONE);

        }

        btnAgregar.setOnClickListener(view -> {
            if(idEleccion == 1){
                // Es Trabajador hora
                AgregarTrabajadorHora();

            }else{
                // Es tiempo completo
                AgregarTrabajadorTiempoCompleto();

            }

            // Una vez insertado el registro, se puede mostrar una alerta y sacar de la pila a la actvidad
            new AlertDialog.Builder(this).setMessage("Insertado con éxito")
                    .setPositiveButton("Ok", (dialog, which) -> finish()).show();

        });

    }

    private void AgregarTrabajadorTiempoCompleto() {
        if(edtCodigo.getText().toString().equals("")){
            Toast.makeText(this, "Debe ingresar el codigo", Toast.LENGTH_SHORT).show();
            return;
        }
        if(edtNombre.getText().toString().equals("")){
            Toast.makeText(this, "Debe ingresar el nombre", Toast.LENGTH_SHORT).show();
            return;
        }
        if(edtApellido.getText().toString().equals("")){
            Toast.makeText(this, "Debe ingresar el apellido", Toast.LENGTH_SHORT).show();
            return;
        }
        if(edtEdad.getText().toString().equals("")){
            Toast.makeText(this, "Debe ingresar la edad", Toast.LENGTH_SHORT).show();
            return;
        }
        if(edtSalario.getText().toString().equals("")){
            Toast.makeText(this, "Debe ingresar el salario", Toast.LENGTH_SHORT).show();
            return;
        }

        lstTrabajadores.add(
                new TrabajadorTiempoCompleto(
                        edtCodigo.getText().toString(), edtNombre.getText().toString(),
                        edtApellido.getText().toString(), Float.valueOf(edtSalario.getText().toString()))
        );
    }

    private void AgregarTrabajadorHora() {
        if(edtCodigo.getText().toString().equals("")){
            Toast.makeText(this, "Debe ingresar el codigo", Toast.LENGTH_SHORT).show();
            return;
        }
        if(edtNombre.getText().toString().equals("")){
            Toast.makeText(this, "Debe ingresar el nombre", Toast.LENGTH_SHORT).show();
            return;
        }
        if(edtApellido.getText().toString().equals("")){
            Toast.makeText(this, "Debe ingresar el apellido", Toast.LENGTH_SHORT).show();
            return;
        }
        if(edtEdad.getText().toString().equals("")){
            Toast.makeText(this, "Debe ingresar la edad", Toast.LENGTH_SHORT).show();
            return;
        }
        if(edtValor.getText().toString().equals("")){
            Toast.makeText(this, "Debe ingresar el valor de la hora", Toast.LENGTH_SHORT).show();
            return;
        }
        if(edtHora.getText().toString().equals("")){
            Toast.makeText(this, "Debe ingresar las horas trabajadas", Toast.LENGTH_SHORT).show();
            return;
        }

        lstTrabajadores.add(
                new TrabajadorHora(
                        edtCodigo.getText().toString(), edtNombre.getText().toString(),
                        edtApellido.getText().toString(), Integer.parseInt(edtHora.getText().toString()),
                        Float.valueOf(edtValor.getText().toString()))
        );
    }

    public static class LoginActivity extends AppCompatActivity {
        private ActivityLoginBinding binding;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // setContentView(R.layout.activity_login);
            // Configuracion de viewbinding
            binding = ActivityLoginBinding.inflate(getLayoutInflater());
            View view = binding.getRoot();
            setContentView(view);
            // Grabando en el sharedpreference
            configSharedPreference();
            // Configurando evento click del boton
            binding.btnIniciarSesion.setOnClickListener(v -> {
                showLoading();
            });
        }
        // Procedimiento que permite mostrar una animacion mientras carga la siguiente pantalla
        private void showLoading(){
            AlertDialog.Builder builder = new
                    AlertDialog.Builder(LoginActivity.this);
            builder.setCancelable(false);
            // Crear un ProgressBar circular indeterminado (plantilla predefinida por Android).
            // ProgressBar progressBar = new ProgressBar(LoginActivity.this);
            // progressBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            // Aca utilizamos la plantilla personalizada que se creo en el archivo progressbar.xml
            View progressBar = getLayoutInflater().inflate(R.layout.progressbar,
                    null);
            // Agregar el ProgressBar al diseño del AlertDialog.
            builder.setView(progressBar);
            // Creacion del alertdialog
            final AlertDialog dialog = builder.create();
            // Mostrar alerta
            dialog.show();
            new Handler().postDelayed(() -> {
                try {
                    if (dialog.isShowing()) {
                        // Aca programar que debe hacer despues de pasados 3 segundos

                        if(verificarCredenciales(binding.edtEmail.getText().toString(),binding.edtPassword.getText().toString())){
                            startActivity(new Intent(LoginActivity.this,
                                    MainActivity.class));
                            dialog.dismiss();
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Credenciales no validas", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 3000); // 3000 milisegundos
        }
        // Configurar SharedPrefence
        private void configSharedPreference(){
            SharedPreferences sharedPreferences = getSharedPreferences("MISDATOS",
                    Context.MODE_PRIVATE);
            // Guardando cadenas en el shared preference
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("email", "admin@admin.com");
            editor.putString("pass","Admin");
            editor.apply();
        }
        // Verificar si las credenciales son correctas
        private boolean verificarCredenciales(String email, String pass){
            boolean esValido = false;
            SharedPreferences sharedPreferences = getSharedPreferences("MISDATOS",
                    Context.MODE_PRIVATE);
            // extrayendo valores del sharedpreference
            String _email = sharedPreferences.getString("email","default");
            String _pass = sharedPreferences.getString("pass", "default");
            if (email.equals(_email) && pass.equals(_pass)){
                esValido = true;
            }
            return esValido;
        }
    }
}