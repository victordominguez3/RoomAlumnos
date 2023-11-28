package vic.dom.roomalumnos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.room.Room
import vic.dom.roomalumnos.database.AlumnoApplication.Companion.database
import vic.dom.roomalumnos.database.AlumnoDatabase
import vic.dom.roomalumnos.databinding.ActivityMainBinding
import vic.dom.roomalumnos.fragments.CondicionFragment
import vic.dom.roomalumnos.fragments.HomeFragment
import vic.dom.roomalumnos.fragments.NuevoFragment
import vic.dom.roomalumnos.models.Alumno

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //Color de la barra de notificaciones
        window.statusBarColor = ContextCompat.getColor(this, R.color.fondo)

        //Teclado por encima del layout
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        // Comentar para empezar a guardar cambios, si no siempre salen los mismos datos iniciales
        //opcional
        borrarDatosIniciales()
        //opcional
        insertarDatosIniciales()


        replaceFragment(HomeFragment())
        binding.nav.selectedItemId = R.id.home

        binding.nav.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.nuevo -> replaceFragment(NuevoFragment())
                R.id.buscar -> replaceFragment(CondicionFragment())
                else -> {}
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }

    private fun insertarDatosIniciales() {
        val itemList = mutableListOf(
            Alumno(nombre = "Victor", edad = 24, imagen = "https://www.elagoradiario.com/wp-content/uploads/2020/11/animales-salvajes-coronavirus-1140x600.jpg"),
            Alumno(nombre = "Marta", edad = 18, imagen = "https://www.telemundo.com/sites/nbcutelemundo/files/styles/fit-760w/public/images/article/cover/2018/04/19/tigre-caminando.jpg?ramen_itok=iqwQftIcTf"),
            Alumno(nombre = "Rares", edad = 22, imagen = "https://imagenes.muyinteresante.es/files/composte_image/uploads/2023/10/04/651cfe5781e5a.jpeg"),
            Alumno(nombre = "Sara", edad = 17, imagen = "https://cdn.nubika.es/wp-content/uploads/2021/06/animales-parecidos-alpaca.jpg"),
            Alumno(nombre = "Yeray", edad = 13, imagen = "https://www.britishcouncil.org.mx/sites/default/files/animales_en_ingles.jpeg"),
            Alumno(nombre = "Natalia", edad = 36, imagen = "https://okdiario.com/img/2021/10/04/las-mejores-frases-para-celebrar-el-dia-de-los-animales-635x358.jpg"),
            Alumno(nombre = "Sergio", edad = 22, imagen = "https://hospitalveterinariodonostia.com/wp-content/uploads/2018/12/6-lugares-donde-puedes-ver-animales-exoticos-6.jpg"),
            Alumno(nombre = "Lucia", edad = 9, imagen = "https://www.fundacionaquae.org/wp-content/uploads/2018/10/proteger-a-los-animales.jpg"),
            Alumno(nombre = "Raquel", edad = 20, imagen = "https://www.bioparcvalencia.es/wp-content/uploads/2017/06/ficha-animal-bioparc-valencia-leon.jpg"),
            Alumno(nombre = "Carlos", edad = 15, imagen = "https://www.nationalgeographic.com.es/medio/2022/12/12/ardilla-2_d0a43045_221212154055_310x310.jpg"),
        )

        for (i in itemList) {
            database.AlumnoDao().addAlumno(i)
        }
    }

    private fun borrarDatosIniciales() {
        database.AlumnoDao().deleteAllAlumnos()
    }
}