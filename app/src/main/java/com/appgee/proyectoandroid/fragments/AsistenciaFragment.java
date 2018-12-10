package com.appgee.proyectoandroid.fragments;


import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.appgee.proyectoandroid.R;
import com.appgee.proyectoandroid.Session.SessionGee;
import com.appgee.proyectoandroid.activities.DetallesAsistenciaActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class AsistenciaFragment extends Fragment implements View.OnClickListener {

    private Button btnDetallesAsistencia;
    private ImageView imageViewQR;
    private TextView textViewNombreasistente;
    private TextView textViewMensajeQR;
    private ProgressBar progressBar;

    private SessionGee sesion;
    private String imgQrName;
    private File imgQRLocal;

    //tvMensajeQR

    public AsistenciaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_asistencia, container, false);

        sesion = new SessionGee(getContext());


        imageViewQR = v.findViewById(R.id.ivQR);
        textViewNombreasistente = v.findViewById(R.id.tvNombreAsistente);
        textViewMensajeQR = v.findViewById(R.id.tvMensajeQR);
        btnDetallesAsistencia = v.findViewById(R.id.btnDetallesAsistencia);
        progressBar = v.findViewById(R.id.progressBar);

        btnDetallesAsistencia.setOnClickListener(this);

        if (sesion.usuarioTieneSesionActiva())
        {
            HashMap<String, String> datosSesion = sesion.getDetallesSesion();
            textViewNombreasistente.setText(datosSesion.get(sesion.KEY_NOMBRE));


            imgQrName = "asistenciaQR_"+ sesion.getUsuarioId()+".png";
            imgQRLocal = new File(getContext().getDir("Images", Context.MODE_PRIVATE), imgQrName);
            if(imgQRLocal.exists())
            {
                imageViewQR.setImageURI(Uri.parse(imgQRLocal.getAbsolutePath()));
                progressBar.setVisibility(View.GONE);
                Log.wtf("IMG-EXISTE: ", "EXISTE "+Uri.parse(imgQRLocal.getAbsolutePath()));
            }
            else
            {
                descargarImgQR();
            }
        }
        else
        {
            textViewNombreasistente.setText("Usuario sin sesión");
            textViewMensajeQR.setText("Es necesario iniciar sesión para registrar su asistencia");
            progressBar.setVisibility(View.GONE);
        }
        return v;
    }

    @Override
    public void onClick(View v) {
        Intent irDetallesAsistencia = new Intent(getContext(), DetallesAsistenciaActivity.class);
        getActivity().startActivity(irDetallesAsistencia);

    }

    private void descargarImgQR(){
        String urlQR = "https://api.qrserver.com/v1/create-qr-code/?size=250x250&color=004168&data=roman.cele.unam.mx/wsgee/asistentes/"+sesion.getUsuarioId();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        ImageRequest imageRequest = new ImageRequest(
                urlQR, // Image URL
                new Response.Listener<Bitmap>() { // Bitmap listener
                    @Override
                    public void onResponse(Bitmap response) {
                        //Configurar imageview con la imagen descargada
                        imageViewQR.setImageBitmap(response);
                        // Guardar la imagen descargada en almacenamiento interno
                        Uri uri = guardarImagenQR(response);
                        //imageViewQR.setImageURI(uri);
                        progressBar.setVisibility(View.GONE);
                    }
                },
                0, // Ancho máximo para la img, 0 = tamaño original
                0, // Alto máximo para la img, 0 = tamaño original
                ImageView.ScaleType.CENTER_CROP, // Image scale type
                Bitmap.Config.RGB_565, //Formato para decodificar la imagen
                new Response.ErrorListener() { // Error listener
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Error en la solictud QR", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(imageRequest);
    }

    protected Uri guardarImagenQR(Bitmap bitmap){

        ContextWrapper wrapper = new ContextWrapper(getContext());
        File file = wrapper.getDir("Images", Context.MODE_PRIVATE);
        file = new File(file, imgQrName);

        try{
            OutputStream stream = null;
            stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
            stream.flush();
            stream.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        Uri imagenGuardadaURI = Uri.parse(file.getAbsolutePath());
        return imagenGuardadaURI;
    }
}
