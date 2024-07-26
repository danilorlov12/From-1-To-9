package com.orlovdanylo.fromonetoninegame.presentation.game.adapter

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orlovdanylo.fromonetoninegame.R
import com.orlovdanylo.fromonetoninegame.presentation.core.ClickListener
import com.orlovdanylo.fromonetoninegame.GameModel
import com.orlovdanylo.fromonetoninegame.utils.cancelViewAnimation
import com.orlovdanylo.fromonetoninegame.utils.pulseAnimation

class GameAdapter(
    private val clickListener: ClickListener<GameModel>,
) : ListAdapter<GameModel, RecyclerView.ViewHolder>(GameDiffCallback) {

    private var firstViewObjAnimator: ObjectAnimator? = null
    private var secondViewObjAnimator: ObjectAnimator? = null

    override fun getItemViewType(position: Int): Int {
        val model = getItem(position)
        return when {
            model.isSelected -> SELECTED_TYPE
            model.isCrossed -> EMPTY_TYPE
            else -> NUMBER_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            EMPTY_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_game_model_empty, parent, false)
                GameViewHolder.Empty(view)
            }
            SELECTED_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_game_model_selected, parent, false)
                GameViewHolder.SelectedNumber(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_game_model, parent, false)
                GameViewHolder.Number(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = getItem(position)
        when (holder.itemViewType) {
            NUMBER_TYPE, SELECTED_TYPE -> {
                holder.itemView.setOnClickListener {
                    clickListener.click(model)
                }
                (holder as GameViewHolder.Selectable).tvNumber.text = model.num.toString()
            }
        }
    }

    fun notifyPairItemsChanged(first: Int, second: Int) {
        notifyItemChanged(first)
        notifyItemChanged(second)
    }

    fun startPulseAnimation(firstView: View?, secondView: View?) {
        firstViewObjAnimator = firstView?.pulseAnimation()
        secondViewObjAnimator = secondView?.pulseAnimation()
    }

    fun stopPreviousAnimation(firstView: View?, secondView: View?) {
        firstView?.cancelViewAnimation(firstViewObjAnimator)
        secondView?.cancelViewAnimation(secondViewObjAnimator)
    }

    abstract class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        class Number(itemView: View) : GameViewHolder(itemView), Selectable {
            override val tvNumber: TextView = itemView.findViewById(R.id.tvNumber)
        }

        class SelectedNumber(itemView: View) : GameViewHolder(itemView), Selectable {
            override val tvNumber: TextView = itemView.findViewById(R.id.tvNumber)
        }

        class Empty(itemView: View) : GameViewHolder(itemView)

        interface Selectable {
            val tvNumber: TextView
        }
    }

    companion object {
        private const val NUMBER_TYPE = 1
        private const val SELECTED_TYPE = 2
        private const val EMPTY_TYPE = 3
    }
}