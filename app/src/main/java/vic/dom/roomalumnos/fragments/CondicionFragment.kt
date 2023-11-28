package vic.dom.roomalumnos.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import vic.dom.roomalumnos.R
import vic.dom.roomalumnos.adapters.ItemAlumnoAdapter
import vic.dom.roomalumnos.adapters.OnAlumnoClickListener
import vic.dom.roomalumnos.database.AlumnoApplication.Companion.database
import vic.dom.roomalumnos.databinding.FragmentCondicionBinding
import vic.dom.roomalumnos.models.Alumno

class CondicionFragment : Fragment(), OnAlumnoClickListener {

    private lateinit var binding: FragmentCondicionBinding

    private lateinit var mAdapter: ItemAlumnoAdapter
    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCondicionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()

        ArrayAdapter.createFromResource(requireContext(), R.array.condiciones, android.R.layout.simple_spinner_dropdown_item).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.condicion.adapter = it
        }

        binding.condicion.onItemSelectedListener = object :AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                binding.numero.isVisible = p2 != 3
                actualizarLista(p2)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        binding.numero.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                actualizarLista(binding.condicion.selectedItemPosition)
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun setRecyclerView() {
        mAdapter = ItemAlumnoAdapter(mutableListOf(), this)
        mLayoutManager = LinearLayoutManager(requireContext())

        binding.recycler.apply {
            layoutManager = mLayoutManager
            adapter = mAdapter
        }
    }

    private fun actualizarLista(indice: Int) {
        mAdapter.setAlumnos(
            if (binding.numero.text.toString() != "" || !binding.numero.isVisible) {
                database.AlumnoDao().getAllAlumnos().filter {
                    when (indice) {
                        0 -> it.edad > binding.numero.text.toString().toInt()
                        1 -> it.edad < binding.numero.text.toString().toInt()
                        2 -> it.edad == binding.numero.text.toString().toInt()
                        else -> it.fav
                    }
                }.sortedBy { it.edad }.toMutableList()
            } else emptyList<Alumno>().toMutableList()
        )
    }

    override fun onClick(id: Long) {
    }

    override fun onDelete(alumno: Alumno) {
    }

}