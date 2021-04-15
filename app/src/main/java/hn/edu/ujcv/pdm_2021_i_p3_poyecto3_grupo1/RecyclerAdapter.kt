package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private val titles = arrayOf("EMPLEADO", "CLIENTE",
                "DEPARTAMENTO", "INSUMOS",
                "PRODUCCION", "PRODUCTOS",
                "PROVEEDORES", "VENTA", "COMPRA", "FORMA PAGO")

    private val details = arrayOf("EMPLEADOS", "CLIENTES",
            "DEPARTAMENTO", "INSUMOS",
            "PRODUCCION", "PRODUCTOS",
            "PROVEEDORES", "VENTAS", "COMPRAS", "FORMAS DE PAGO")

    private val images = intArrayOf(R.drawable.empleado,R.drawable.cliente,
            R.drawable.departamento, R.drawable.insumos,
            R.drawable.produccion, R.drawable.producto,
            R.drawable.proveedor, R.drawable.venta,
            R.drawable.compra,R.drawable.tipopago)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.card_layout, viewGroup, false)
        return ViewHolder(v)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView

        init {
            itemImage=itemView.findViewById(R.id.item_image)
            itemTitle=itemView.findViewById(R.id.item_title)
            itemDetail=itemView.findViewById(R.id.item_detail)


            itemView.setOnClickListener { v: View? ->
                var position:Int = getAdapterPosition()
                println(position.toString())


                when(position){
                    0 -> irEmpleado(itemView)
                    1 -> irCliente(itemView)
                    2 -> irDepartamento(itemView)
                    3 -> irInsumos(itemView)
                    4 -> irProduccion(itemView)
                    5 -> irProductos(itemView)
                    6 -> irProveedores(itemView)
                    7 -> irVentas(itemView)
                    8 -> irCompras(itemView)
                    9 -> irTipoPago(itemView)
                    else->regresarMain(itemView)

                }




            }
        }
    }
    private fun regresarMain(itemView: View) {
        val intent = Intent(itemView.context, MainActivity::class.java)
        itemView.context.startActivity(intent)
    }
    private fun irUsuario(itemView: View) {
        val intent = Intent(itemView.context, MainActivity::class.java)
        itemView.context.startActivity(intent)
    }
    private fun irEmpleado(itemView: View) {
        val intent = Intent(itemView.context, Empleado::class.java)
        itemView.context.startActivity(intent)
    }
    private fun irDepartamento(itemView: View) {
        val intent = Intent(itemView.context, Departamento::class.java)
        itemView.context.startActivity(intent)
    }
    private fun irVentas(itemView: View) {
        val intent = Intent(itemView.context, Ventas::class.java)
        itemView.context.startActivity(intent)
    }
    private fun irProductos(itemView: View) {
        val intent = Intent(itemView.context, Productos::class.java)
        itemView.context.startActivity(intent)
    }
    private fun irProduccion(itemView: View) {
        val intent = Intent(itemView.context, Produccion::class.java)
        itemView.context.startActivity(intent)
    }
    private fun irProveedores(itemView: View) {
        val intent = Intent(itemView.context, Proveedores::class.java)
        itemView.context.startActivity(intent)
    }
    private fun irCompras(itemView: View) {
        val intent = Intent(itemView.context, Compras::class.java)
        itemView.context.startActivity(intent)
    }
    private fun irCliente(itemView: View) {
        val intent = Intent(itemView.context, Cliente::class.java)
        itemView.context.startActivity(intent)
    }

    private fun irInsumos(itemView: View) {
        val intent = Intent(itemView.context, Insumos::class.java)
        itemView.context.startActivity(intent)
    }
    private fun irTipoPago(itemView: View) {
        val intent = Intent(itemView.context, TipoPago::class.java)
        itemView.context.startActivity(intent)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemTitle.text = titles[i]
        viewHolder.itemDetail.text = details[i]
        viewHolder.itemImage.setImageResource(images[i])
    }

    override fun getItemCount(): Int {
        return titles.size
    }
}