package com.appgee.proyectoandroid.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appgee.proyectoandroid.R;
import com.appgee.proyectoandroid.activities.MainActivity;
import com.appgee.proyectoandroid.listeners.OnPonenteClickListener;
import com.appgee.proyectoandroid.models.Ponente;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PonenteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private List<Ponente> ponentes;
    private Context context;

    //Para llevar los registros filtrados
    private List<Ponente> ponentesFiltrados;

    //Para recibir el ponente de cada posicion en el recyclerView
    private Ponente ponenteItem;

    private final int TYPE_LETTER = 1;
    private final int TYPE_MEMBER = 2;

    private OnPonenteClickListener onPonenteClickListener;

    public PonenteAdapter(List<Ponente> ponentes, Context context) {
        if(ponentes == null){
            this.ponentes = new ArrayList<>();
            this.ponentesFiltrados = new ArrayList<>();
        } else {
            this.ponentes = ponentes;
            ponentesFiltrados = new ArrayList<>(ponentes);
        }

        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case TYPE_LETTER:
                ViewGroup vGroupImage = (ViewGroup) mInflater.inflate(R.layout.ponente_separador_letra, parent, false);
                ViewHolderLetter image = new ViewHolderLetter(vGroupImage);

                return image;
            case TYPE_MEMBER:
                ViewGroup vGroupText = (ViewGroup) mInflater.inflate(R.layout.ponente_item_lista, parent, false);
                PonenteViewHolder contactViewHolder = new PonenteViewHolder(vGroupText);
                contactViewHolder.setOnPonenteClickListener(onPonenteClickListener);
                return contactViewHolder;
            default:
                ViewGroup vGroupText2 = (ViewGroup) mInflater.inflate(R.layout.ponente_item_lista, parent, false);
                PonenteViewHolder contactViewHolder1 = new PonenteViewHolder(vGroupText2);
                contactViewHolder1.setOnPonenteClickListener(onPonenteClickListener);
                return contactViewHolder1;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int posicion) {
        Object item = ponentes.get(posicion);

        if (holder instanceof PonenteViewHolder) {
            ponenteItem = (Ponente) item;
            ((PonenteViewHolder) holder).setPonente(ponenteItem);
            ((PonenteViewHolder) holder).tvNombre.setText(ponenteItem.getNombre());
            ((PonenteViewHolder) holder).tvApellidos.setText(ponenteItem.getApellidos());
            ((PonenteViewHolder) holder).tvInstitucion.setText(ponenteItem.getInstitucion());

            String urlFoto = ponenteItem.getFoto();
            if (!urlFoto.isEmpty()) {
                Glide.with(context).load(urlFoto).into(((PonenteViewHolder) holder).ivImagen);
            } else {
                ((PonenteViewHolder) holder).ivImagen.setImageResource(R.drawable.ic_usuario);
            }
        } else {
            ((ViewHolderLetter) holder).bind((Ponente) item);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;
        if (ponentes.get(position).getType() == TYPE_LETTER) {
            viewType = TYPE_LETTER;
        } else if (ponentes.get(position).getType() == TYPE_MEMBER) {
            viewType = TYPE_MEMBER;
        }

        return viewType;
    }

    @Override
    public int getItemCount() {
        return ponentes.size();
    }

    public void setOnPonenteClickListener(OnPonenteClickListener onPonenteClickListener) {
        this.onPonenteClickListener = onPonenteClickListener;
    }

    static class PonenteViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombre;
        protected TextView tvApellidos;
        protected TextView tvInstitucion;
        protected ImageView ivImagen;
        private Ponente ponente;

        private OnPonenteClickListener onPonenteClickListener;

        public PonenteViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tv_nombre);
            tvApellidos = itemView.findViewById(R.id.tv_apellidos);
            tvInstitucion = itemView.findViewById(R.id.tv_institucion);
            ivImagen = itemView.findViewById(R.id.iv_imagen);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (onPonenteClickListener == null) {
                        Toast.makeText(v.getContext(), "onPonenteClickListener es NULL", Toast.LENGTH_SHORT).show();
                    }
                    if (ponente == null) {
                        Toast.makeText(v.getContext(), "ponente es NULL", Toast.LENGTH_SHORT).show();
                    }

                    if(onPonenteClickListener != null && ponente != null)
                        onPonenteClickListener.onPonenteClick(ponente);
                }
            });
        }

        public PonenteViewHolder(View itemView, Ponente ponente) {
            this(itemView);
            this.ponente = ponente;
        }

        public void setPonente(Ponente ponente) {
            this.ponente = ponente;
        }

        public void setOnPonenteClickListener(OnPonenteClickListener onPonenteClickListener) {
            this.onPonenteClickListener = onPonenteClickListener;
        }

        public void bind(Ponente ponente) {
            // display your object
            tvNombre.setText(ponente.getNombre());
            tvApellidos.setText(ponente.getApellidos());
            tvInstitucion.setText(ponente.getInstitucion());
        }
    }

    static class ViewHolderLetter extends RecyclerView.ViewHolder {
        protected TextView tvLetra;

        public ViewHolderLetter(View itemView) {
            super(itemView);
            tvLetra = itemView.findViewById(R.id.tv_letra);
        }

        public void bind(Ponente ponente) {
            // display your object
            tvLetra.setText(String.valueOf(ponente.getNombre().charAt(0)));
        }
    }

    //Implementacion para filtrar la busqueda
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Ponente> filteredList = new ArrayList<>();
            List<Ponente> filteredLetras = new ArrayList<>();

            //letras guarda las Letras iniciales de los registros que si coinciden con la busqueda
            ArrayList<String> letras = new ArrayList<String>();
            HashSet<String> hashSet = new HashSet<String>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(ponentesFiltrados);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Ponente item : ponentesFiltrados) {
                    if (item.getNombre().toLowerCase().contains(filterPattern) && item.getType() != TYPE_LETTER) {
                        filteredList.add(item);
                        letras.add(String.valueOf(item.getNombre().charAt(0))); //Se pueden repetir
                    }
                    if (item.getType() == TYPE_LETTER) {
                        filteredLetras.add(item);
                    }
                }

                //Eliminamos las letras repetidas
                hashSet.addAll(letras);
                letras.clear();
                letras.addAll(hashSet);

                //Agregamos solo las secciones de Letras que corresponden con los registros filtrados
                for (Ponente itemLetra : filteredLetras) {
                    if (letras.contains(String.valueOf(itemLetra.getNombre().charAt(0)))) {
                        for (Ponente itemFinal : filteredList) {
                            //Buscamos el primer registro que empiece con la letra y de coincidir
                            //agregamos la letra a la lista antes de esos registros que empiezan con esa letra
                            if (String.valueOf(itemFinal.getNombre().charAt(0)).equals(String.valueOf(itemLetra.getNombre().charAt(0)))){
                                filteredList.add(filteredList.indexOf(itemFinal), itemLetra);
                                break;
                            }
                        }
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ponentes.clear();
            ponentes.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}