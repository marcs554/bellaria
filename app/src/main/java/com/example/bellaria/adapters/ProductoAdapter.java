package com.example.bellaria.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bellaria.R;

import com.example.bellaria.model.Clientes;
import com.example.bellaria.model.Pedidos;
import com.example.bellaria.model.Productos;
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

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {

    private static final String url = "https://bellaria.herokuapp.com/";
    private ArrayList<Productos> listaProductos;
    private Context context;
    private Clientes cliente;

    /**
     * @param listaProductos
     * @param context
     */
    public ProductoAdapter(ArrayList<Productos> listaProductos, Context context, Clientes cliente) {
        this.listaProductos = listaProductos;
        this.context = context;
        this.cliente = cliente;
    }


    /**
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ProductoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_productos,
                parent, false);

        return new ViewHolder(view);
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nombreProducto.setText(listaProductos.get(position).getNombre());
        holder.precioProducto.setText(String.valueOf(listaProductos.get(position).getPrecioActual()) + " €/KG");
        Picasso.get().load(listaProductos.get(position).getUrlimagenProducto()).into(holder.imagenProducto);

        /**
         * Al pulsar un item se llamará a la función "callDialogIngresarCantidad" pasandole por
         * parametro la posición del item.
         */
        holder.item_producto.setOnClickListener(v -> callDialogIngresarCantidad(listaProductos.get(position)));
    }


    /**
     *
     * @return
     */
    @Override
    public int getItemCount() { return listaProductos.size(); }

    /**
     *
     * @param producto
     */
    private void callDialogIngresarCantidad(Productos producto) {
        /**
         * Se iniciará un dialog con un editText pidiendole al usuario la cantidad y dos botones:
         * aceptar y cancelar.
         */
        Pedidos pedidos = new Pedidos();
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_ingresar_cantidad_pedido);
        dialog.setTitle("Ingresar cantidad");
        EditText cantidad = (EditText) dialog.findViewById(R.id.edt_dialog_cantidad);
        Button aceptar = dialog.findViewById(R.id.bttn_dialog_aceptar);
        Button cancelar = dialog.findViewById(R.id.bttn_dialog_cancelar);

        aceptar.setOnClickListener(v -> {

            /**
             * Chequea si el EditText cantidad está vacío. En caso de estarlo se lanzará un Toast para
             * avisar al usuario de que introduzca la cantidad. En caso contrario se irá almacenando
             * los datos del producto, la cantidad, el importe (precioActual * cantidad) y los datos
             * del cliente en una variable objeto de la clase Pedidos y aparecerá por
             * pantalla un dialog con el resumen del pedido que quiere realizar el usuario.
             */
            if (!cantidad.getText().toString().isEmpty()) {
                pedidos.setProductos(producto);
                pedidos.setCantidad((Float.parseFloat(cantidad.getText().toString())));
                pedidos.setImporte(producto.getPrecioActual() * (Float.parseFloat(cantidad.getText().toString())));
                pedidos.setCliente(cliente);

                dialog.dismiss();
                callDialogResumenPedido(pedidos);
            } else {
                Toast.makeText(context, "Ingrese la cantidad", Toast.LENGTH_LONG).show();
            }

        });

        cancelar.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    /**
     * Muestra un dialog por pantalla con un resumen del pedido del usuario: nombre del producto,
     * cantidad, importe y dos botones: pedir y cancelar.
     * @param pedidos
     */
    private void callDialogResumenPedido(Pedidos pedidos) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_informe_pedido);
        dialog.setTitle("Resumen");

        //nombre del producto
        TextView txvProducto = (TextView) dialog.findViewById(R.id.txv_dialog_resumen_pedido_producto);
        txvProducto.append(pedidos.getProductos().getNombre());

        //cantidad de producto
        TextView txvCantidad = (TextView) dialog.findViewById(R.id.txv_dialog_resumen_pedido_cantidad);
        txvCantidad.append(String.valueOf(pedidos.getCantidad()) + " KGs");

        //importe del producto
        TextView txvImporte = (TextView) dialog.findViewById(R.id.txv_dialog_resumen_pedido_importe);
        txvImporte.append(String.valueOf(pedidos.getImporte()) + " €");

        Button pedir = dialog.findViewById(R.id.bttn_dialog_resumen_pedido_pedir);
        Button cancelar = dialog.findViewById(R.id.bttn_dialog_resumen_pedido_cancelar);

        /**
         * Al pulsar el botón aceptar se cerrará el dialog y se llamará a la función "realizarPedido"
         * pasando por parametro el objeto pedido.
         */
        pedir.setOnClickListener(v -> {
            dialog.dismiss();
            realizarPedido(pedidos);
        });

        //Simplemente cerrará el dialog.
        cancelar.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    /**
     * Esta función se encarga de enviar al servidor los datos del pedido del usuario.
     * @param pedido
     */
    private void realizarPedido(Pedidos pedido) {
        if (!EstadoConexion.checkConnectionServer(url)) {
            DialogInforme.dialogResultado(context, "No se ha podido" +
                    " realizar el pedido, vuelva a intentarlo o cierre sesión y " +
                    "vuelva a iniciar sesión");
        } else {
            ClienteService clienteService = RetrofitInstancia.retrofitllamada(url).create(ClienteService.class);
            Call<List<Pedidos>> call = clienteService.nuevoPedido(pedido.getCantidad(),
                    pedido.getImporte(), pedido.getCliente().getId(), pedido.getProductos().getId());

            call.enqueue(new Callback<List<Pedidos>>() {

                /**
                 * Si el pedido se realizó correctamente se mostrará por pantalla un dialog
                 * informando al usuario que el pedido se realizó correctamente.
                 * @param call
                 * @param response
                 */
                @Override
                public void onResponse(Call<List<Pedidos>> call, Response<List<Pedidos>> response) {
                    DialogInforme.dialogResultado(context, "El pedido se realizó correctamente. Si desea anularlo hagalo " +
                            "antes de que cambie del estado (En espera) a otro, si no no podrá anularse.\n" +
                            "Visite el apartado \"Mis Pedidos\" para ver su pedido");
                }

                /**
                 * En caso de que ocurriera un problema durante el proceso se mostrara por pantalla
                 * un dialog informado al usuario de que el pedido no se realizó.
                 * @param call
                 * @param t
                 */
                @Override
                public void onFailure(Call<List<Pedidos>> call, Throwable t) {
                    DialogInforme.dialogResultado(context, "Ha surgido un problema, el pedido no se realizó");
                }
            });
        }
    }


    /**
     * Declaración de los widgets de "layout_productos"
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nombreProducto;
        private TextView precioProducto;
        private ImageView imagenProducto;
        private RelativeLayout item_producto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_producto = (RelativeLayout) itemView.findViewById(R.id.item_producto);
            nombreProducto = (TextView) itemView.findViewById(R.id.txv_nombreProducto);
            precioProducto = (TextView) itemView.findViewById(R.id.txv_precioProducto);
            imagenProducto = (ImageView) itemView.findViewById(R.id.imageView_producto);

        }

    }

}
