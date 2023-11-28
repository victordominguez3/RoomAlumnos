package vic.dom.roomalumnos.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import vic.dom.roomalumnos.R
import vic.dom.roomalumnos.database.AlumnoApplication.Companion.database
import vic.dom.roomalumnos.databinding.ItemAlumnoLayoutBinding
import vic.dom.roomalumnos.models.Alumno

class ItemAlumnoAdapter(private var listItem: MutableList<Alumno>, private var listener: OnAlumnoClickListener) : RecyclerView.Adapter<ItemAlumnoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alumno_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItem[position]
        holder.bind(item)
        holder.setListener(item)
    }

    fun setAlumnos(alumnos: MutableList<Alumno>) {
        listItem = alumnos
        notifyDataSetChanged()
    }

    fun delete(alumno: Alumno) {
        val index = listItem.indexOf(alumno)
        if (index != -1){
            listItem.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    inner class ViewHolder (view: View): RecyclerView.ViewHolder(view) {

        val binding = ItemAlumnoLayoutBinding.bind(view)

        fun bind(item: Alumno) {

            binding.nombre.text = item.nombre
            binding.edad.text = item.edad.toString() + " años"
            if (item.bool) {
                binding.mayorEdad.text = "Mayor de edad: Si"
            } else binding.mayorEdad.text = "Mayor de edad: No"
            if (item.imagen.isNotEmpty()) {
                try {
                    Glide.with(binding.imagen)
                        .load(item.imagen)
                        .transform(CenterCrop(), RoundedCorners(20))
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.imagen)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Glide.with(binding.imagen)
                        .load(R.drawable.fotonula)
                        .transform(CenterCrop(), RoundedCorners(20))
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.imagen)
                }
            } else {
                Glide.with(binding.imagen)
                    .load(R.drawable.fotonula)
                    .transform(CenterCrop(), RoundedCorners(20))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.imagen)
            }
            if (item.fav) {
                binding.fav.setBackgroundResource(R.drawable.favorito_true)
            } else {
                binding.fav.setBackgroundResource(R.drawable.favorito_false)
            }

            binding.fav.setOnClickListener {
                if (binding.fav.background.constantState == ContextCompat.getDrawable(itemView.context, R.drawable.favorito_false)?.constantState) {
                    binding.fav.setBackgroundResource(R.drawable.favorito_true)
                    item.fav = true
                    database.AlumnoDao().updateAlumno(item)
                } else {
                    binding.fav.setBackgroundResource(R.drawable.favorito_false)
                    item.fav = false
                    database.AlumnoDao().updateAlumno(item)
                }
            }

        }

        fun setListener(alumno: Alumno) {

            binding.root.setOnClickListener { listener.onClick(alumno.id) }
            binding.root.setOnLongClickListener {
                listener.onDelete(alumno)
                true
            }
        }

    }

}