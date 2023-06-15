package com.example.game
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BoxAdapter(private val boxCount: Int) :
    RecyclerView.Adapter<BoxAdapter.BoxViewHolder>() {

    private val initialColor = Color.GRAY
    private val redColor = Color.RED
    private val greenColor = Color.GREEN

    private val boxColors = MutableList(boxCount) { initialColor }
    private var redBoxPosition = -1
    private var greenBoxPosition = -1

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoxViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.box, parent, false)
        return BoxViewHolder(view)
    }

    override fun onBindViewHolder(holder: BoxViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return boxCount
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    inner class BoxViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val boxView: View = itemView.findViewById(R.id.boxView)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(position: Int) {
            val color = boxColors[position]
            boxView.setBackgroundColor(color)
        }

        override fun onClick(view: View) {
            val clickedPosition = adapterPosition

            if (redBoxPosition == -1) {
                redBoxPosition = clickedPosition
                boxColors[redBoxPosition] = redColor
                makeRandomBoxGreen()
            } else if (clickedPosition != redBoxPosition && boxColors[clickedPosition] == initialColor) {
                boxColors[redBoxPosition] = initialColor
                redBoxPosition = clickedPosition
                boxColors[redBoxPosition] = redColor
                makeRandomBoxGreen()
            } else if (clickedPosition == redBoxPosition && isAllBoxesGreenExceptLast()) {
                resetBoxColors()
            }

            notifyDataSetChanged()
        }

        private fun isAllBoxesGreenExceptLast(): Boolean {
            for (i in 0 until boxCount) {
                if (i != redBoxPosition && boxColors[i] != greenColor) {
                    return false
                }
            }
            return true
        }

        private fun resetBoxColors() {
            boxColors.fill(initialColor)
            redBoxPosition = -1
            greenBoxPosition = -1
        }

        private fun makeRandomBoxGreen() {
            val remainingBoxes = boxColors.indices.filter { it != redBoxPosition && boxColors[it] == initialColor }
            if (remainingBoxes.isNotEmpty()) {
                greenBoxPosition = remainingBoxes.random()
                boxColors[greenBoxPosition] = greenColor
            }
        }
    }
}



