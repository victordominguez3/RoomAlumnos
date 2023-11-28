package vic.dom.roomalumnos.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import vic.dom.roomalumnos.R
import vic.dom.roomalumnos.adapters.OnAlumnoClickListener
import vic.dom.roomalumnos.database.AlumnoApplication.Companion.database
import vic.dom.roomalumnos.databinding.FragmentNuevoBinding
import vic.dom.roomalumnos.models.Alumno


class NuevoFragment : Fragment() {

    private lateinit var binding: FragmentNuevoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNuevoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configInit()

        binding.urlImagen.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try {
                    Glide.with(binding.imagen)
                        .load(binding.urlImagen.text.toString())
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
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        binding.boton.setOnClickListener {

            if (binding.nombre.text.isNullOrEmpty() || binding.edad.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Se deben rellenar como mínimo el campo nombre y el campo edad", Toast.LENGTH_SHORT).show()
            } else {
                Thread {
                    database.AlumnoDao().addAlumno(
                        Alumno(
                            nombre = binding.nombre.text.toString(),
                            edad = binding.edad.text.toString().toInt(),
                            imagen = binding.urlImagen.text.toString()
                        )
                    )
                }.start()
                Toast.makeText(requireContext(), "Usuario añadido con éxito", Toast.LENGTH_SHORT).show()
                configInit()
            }

        }

    }

    private fun configInit() {
        binding.nombre.setText("")
        binding.edad.setText("")
        binding.urlImagen.setText("")
        Glide.with(binding.imagen)
            .load(R.drawable.fotonula)
            .transform(CenterCrop(), RoundedCorners(20))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.imagen)
    }

}