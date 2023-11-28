package vic.dom.roomalumnos.adapters

import vic.dom.roomalumnos.models.Alumno

interface OnAlumnoClickListener {

    fun onClick(id: Long)
    fun onDelete(alumno: Alumno)
}