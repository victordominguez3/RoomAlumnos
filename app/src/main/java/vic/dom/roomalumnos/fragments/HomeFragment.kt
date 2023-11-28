package vic.dom.roomalumnos.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import vic.dom.roomalumnos.adapters.ItemAlumnoAdapter
import vic.dom.roomalumnos.adapters.OnAlumnoClickListener
import vic.dom.roomalumnos.database.AlumnoApplication.Companion.database
import vic.dom.roomalumnos.databinding.FragmentHomeBinding
import vic.dom.roomalumnos.models.Alumno
import java.util.concurrent.LinkedBlockingQueue


class HomeFragment : Fragment(), OnAlumnoClickListener {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var mAdapter: ItemAlumnoAdapter
    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()

        binding.busqueda.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val queue = LinkedBlockingQueue<MutableList<Alumno>>()

                Thread {
                    val list = database.AlumnoDao().getAllAlumnos()
                    queue.add(list)
                }.start()

                mAdapter.setAlumnos(queue.take()
                    .filter { it.nombre.lowercase().contains(s.toString().lowercase()) }.toMutableList())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })


    }


    private fun setRecyclerView() {

        val lista = database.AlumnoDao().getAllAlumnos()


        mAdapter = ItemAlumnoAdapter(lista, this)
        mLayoutManager = LinearLayoutManager(requireContext())

        binding.recycler.apply {
            layoutManager = mLayoutManager
            adapter = mAdapter
        }
    }

    override fun onClick(id: Long) {
    }

    override fun onDelete(alumno: Alumno) {

        val queue = LinkedBlockingQueue<Alumno>()

        Thread {
            database.AlumnoDao().deleteAlumno(alumno)
            queue.add(alumno)
        }.start()

        mAdapter.delete(queue.take())

    }

}