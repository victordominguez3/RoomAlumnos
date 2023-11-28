package vic.dom.roomalumnos.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TAlumnos")
data class Alumno(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0L,
    @ColumnInfo(name = "nombre")
    var nombre: String = "",
    @ColumnInfo(name = "edad")
    var edad: Int = 0,
    @ColumnInfo(name = "imagen")
    var imagen: String = "",
    @ColumnInfo(name = "mayorDeEdad")
    var bool: Boolean = edad >= 18,
    @ColumnInfo(name = "favorito")
    var fav: Boolean = false
    //Al añadir este campo no inicia la aplicacion, incluso cambiando la version de la base de datos al haber efectuado cambios
) {
    fun customSetId(newId: Long) {
        id = newId
    }

}