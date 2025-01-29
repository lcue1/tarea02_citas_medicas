package com.example.citasmedicas.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.citasmedicas.R
import com.example.citasmedicas.data.appointments.Appointment

class AppointmentAdapter(private val appointments: List<AppointmentItem>) :
    RecyclerView.Adapter<AppointmentAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idOrName: TextView
        val dateAppointment: TextView
        val horAppointment: TextView
        val doctorName: TextView
        val specialityDoctor: TextView

        init {
            // Define click listener for the ViewHolder's View
            idOrName = view.findViewById(R.id.userName)
            dateAppointment = view.findViewById(R.id.dateAppointment)
            horAppointment = view.findViewById(R.id.horAppointment)
            doctorName = view.findViewById(R.id.doctorName)
            specialityDoctor = view.findViewById(R.id.specialityDoctor)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.appointment_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.idOrName.text = appointments[position].userName
        viewHolder.dateAppointment.text = appointments[position].dateAppointment
        viewHolder.horAppointment.text = appointments[position].hourAppointment
        viewHolder.doctorName.text = appointments[position].doctorName
        viewHolder.specialityDoctor.text = appointments[position].specialityDoctor
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = appointments.size

}