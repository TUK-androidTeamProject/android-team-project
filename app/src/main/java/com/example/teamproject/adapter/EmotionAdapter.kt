package com.example.teamproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmotionAdapter(
    private val emotions: List<Emotion>,
    private val onClick: (Emotion) -> Unit
) : RecyclerView.Adapter<EmotionAdapter.EmotionViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    inner class EmotionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvEmotion: TextView = itemView.findViewById(R.id.tvEmotion)
        private val ivEmotionIcon: ImageView = itemView.findViewById(R.id.ivEmotionIcon)

        init {
            itemView.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = adapterPosition
                notifyItemChanged(previousPosition)
                notifyItemChanged(adapterPosition)
                onClick(emotions[adapterPosition])
            }
        }

        fun bind(emotion: Emotion, isSelected: Boolean) {
            tvEmotion.text = emotion.name
            ivEmotionIcon.setImageResource(emotion.iconResId)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmotionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_emotion_button, parent, false)
        return EmotionViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmotionViewHolder, position: Int) {
        val emotion = emotions[position]
        holder.bind(emotion, position == selectedPosition)

        holder.itemView.scaleX = if (position == selectedPosition) 1.05f else 1.0f
        holder.itemView.scaleY = if (position == selectedPosition) 1.05f else 1.0f
    }


    override fun getItemCount(): Int = emotions.size
}
