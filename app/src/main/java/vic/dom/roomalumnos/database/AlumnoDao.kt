package vic.dom.roomalumnos.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import vic.dom.roomalumnos.models.Alumno

@Dao
interface AlumnoDao {

    @Query("SELECT * FROM TAlumnos")
    fun getAllAlumnos() : MutableList<Alumno>

    @Query("SELECT * FROM TAlumnos where id = :id")
    fun getAlumnoById(id: Long): Alumno

    @Insert
    fun addAlumno(alumno: Alumno)

    @Update
    fun updateAlumno(alumno: Alumno)

    @Delete
    fun deleteAlumno(alumno: Alumno)

    @Query("DELETE FROM TAlumnos")
    fun deleteAllAlumnos()

//    @Query("ALTER TABLE TAlumno ADD favorito INTEGER;")
//    fun updateTable()
}
