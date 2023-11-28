package vic.dom.roomalumnos.database

import androidx.room.Database
import androidx.room.RoomDatabase
import vic.dom.roomalumnos.models.Alumno

@Database(entities = [Alumno::class], version = 2)
abstract class AlumnoDatabase: RoomDatabase() {
    abstract fun AlumnoDao(): AlumnoDao
}