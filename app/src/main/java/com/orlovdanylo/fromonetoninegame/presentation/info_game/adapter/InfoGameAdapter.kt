package com.orlovdanylo.fromonetoninegame.presentation.info_game.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orlovdanylo.fromonetoninegame.R
import com.orlovdanylo.fromonetoninegame.GameModel
import com.orlovdanylo.fromonetoninegame.presentation.game.adapter.GameDiffCallback

class InfoGameAdapter : ListAdapter<GameModel, InfoGameAdapter.GameModelViewHolder>(GameDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameModelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_game_info_model, parent, false)
        return GameModelViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameModelViewHolder, position: Int) {
        val model = getItem(position)
        with(holder) {
            tvNumber.apply {
                text = if (!model.isCrossed) model.num.toString() else ""
                setTextColor(ContextCompat.getColor(itemView.context, R.color.gold))
            }
        }
    }

    inner class GameModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNumber: TextView = itemView.findViewById(R.id.tvNumber)
    }
}