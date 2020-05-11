package com.example.bellaria.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bellaria.R;
import com.example.bellaria.model.Pedidos;
import com.example.bellaria.reutilizacion.DialogInforme;
import com.example.bellaria.services.ClienteService;
import com.example.bellaria.services.EstadoConexion;
import com.example.bellaria.services.RetrofitInstancia;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.ViewHolder> {

    private static final String url = "https://bellaria.herokuapp.com/";
    private Context context;
    private ArrayList<Pedidos> pedidosList;

    /**
     *
     * @param context
     * @param pedidosList
     */
    public PedidoAdapter(Context context, ArrayList<Pedidos> pedidosList) {
        this.context = context;
        this.pedidosList = pedidosList;
    }

    /**
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public PedidoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_pedidos, parent, false);

        return new ViewHolder(view);
    }

    /**
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull PedidoAdapter.ViewHolder holder, int position) {
        //Se pasa la url de la imagen del producto al imagenview usando la librería "Picasso".
        Picasso.get().load(pedidosList.get(position).getProductos().getUrlimagenProducto())
                .into(holder.imageViewProducto);

        holder.nombreProducto.setText(pedidosList.get(position).getProductos().getNombre());
        holder.cantidad.setText(String.valueOf(pedidosList.get(position).getCantidad()));
        holder.fechaPedido.setText(pedidosList.get(position).getFechaPedido());
        holder.importe.setText(String.valueOf(pedidosList.get(position).getImporte()));
        holder.estadoPedido.setText(pedidosList.get(position).getEstado().getNombreEstado());

        /**
         * Cuando un cardview sea pulsado se iniciará un dialog en el cual le preguntará
         * al usuario si quiere anular el pedido y dos botones: aceptar y cancelar.
          */
        holder.item_pedido.setOnClickListener(v -> {
            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_confirmar_anular_pedido);
            dialog.setTitle("¿Confirmar anular el pedido?");
            TextView textView = (TextView) dialog.findViewById(R.id.txv_dialog_confirmar_anular_pedido);
            Button aceptar = dialog.findViewById(R.id.bttn_dialog_confirmar_anular_pedido_aceptar);
            Button cancelar = dialog.findViewById(R.id.bttn_dialog_confirmar_anular_pedido_cancelar);

            //Al pulsar el botón aceptar
            aceptar.setOnClickListener(v1 -> {
                if(pedidosList.get(position).getEstado().getId() < 2) {
                    /**
                     * Si el id del estado es menor que 2 se procede a entrar en la función "anularPedido"
                     * pasando por parametro el id del pedido.
                     */
                    anularPedido(pedidosList.get(position));
                } else if(pedidosList.get(position).getEstado().getId() > 1 &&
                        pedidosList.get(position).getEstado().getId() < 5) {

                    /**
                     * Si el id del estado es mayor que 1 pero menor que 5 se llamará a la función
                     * dialogResultado situada en la clase DialogInforme para mostrar por pantalla un
                     * dialog informando al usuario de que ya no es posible anular el pedido.
                     */
                    DialogInforme.dialogResultado(context, "Ya no es posible anular el pedido");
                } else if(pedidosList.get(position).getEstado().getId() > 4) {

                    /**
                     * Si el id del estado es mayor que 4 se llamará a la función
                     * dialogResultado situada en la clase DialogInforme para mostrar por pantalla un
                     * dialog informando al usuario de que el pedido ya está anulado.
                     */
                    DialogInforme.dialogResultado(context, "El pedido ya está anulado");
                }

                dialog.dismiss();
            });

            //Al pulsar el botón cancelar simplemente se cierra el dialog
            cancelar.setOnClickListener(v1 -> dialog.dismiss());

            dialog.show();
        });
    }

    /**
     *
     * @return
     */
    @Override
    public int getItemCount() { return pedidosList.size(); }

    private void anularPedido(Pedidos pedido) {
        if(!EstadoConexion.checkConnectionServer(url)){
            DialogInforme.dialogResultado(context, "No se ha podido realizar el pedido, vuelva " +
                    "a intentarlo o cierre sesión y vuelva a iniciar sesión");
        } else {
            ClienteService clienteService = RetrofitInstancia.retrofitllamada(url).create(ClienteService.class);
            Call<List<Pedidos>> call = clienteService.anularPedido(pedido.getId());
            call.enqueue(new Callback<List<Pedidos>>() {
                @Override
                public void onResponse(Call<List<Pedidos>> call, Response<List<Pedidos>> response) {
                    DialogInforme.dialogResultado(context, "se anulo el pedido correctamente");
                }

                @Override
                public void onFailure(Call<List<Pedidos>> call, Throwable t) {

                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout item_pedido;
        private TextView nombreProducto;
        private TextView cantidad;
        private TextView fechaPedido;
        private TextView importe;
        private TextView estadoPedido;
        private ImageView imageViewProducto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //Definición de componentes del layout layout_pedidos.
            nombreProducto = (TextView) itemView.findViewById(R.id.txv_layout_pedidos_nombreProducto_insert);
            cantidad = (TextView) itemView.findViewById(R.id.txv_layout_pedidos_cantidad_insert);
            fechaPedido = (TextView) itemView.findViewById(R.id.txv_layout_pedido_fecha_insert);
            importe = (TextView) itemView.findViewById(R.id.txv_layout_pedidos_importe_insert);
            estadoPedido = (TextView) itemView.findViewById(R.id.txv_layout_pedidos_estado_insert);
            imageViewProducto = (ImageView) itemView.findViewById(R.id.imageView_pedido);
            item_pedido = (RelativeLayout) itemView.findViewById(R.id.item_pedido);
        }
    }
}
