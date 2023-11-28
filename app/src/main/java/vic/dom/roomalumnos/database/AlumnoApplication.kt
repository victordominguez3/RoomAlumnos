package vic.dom.roomalumnos.database

import android.app.Application
import androidx.room.Room

class AlumnoApplication: Application() {

    companion object{
        lateinit var database: AlumnoDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this,
            AlumnoDatabase::class.java,
            "db_alumnos")
            .allowMainThreadQueries().enableMultiInstanceInvalidation().build()
    }
}