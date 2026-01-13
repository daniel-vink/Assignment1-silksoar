package com.example.assignment1.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment1.interfaces.ScoreClickedCallback
import com.example.assignment1.model.ScoreItem
import android.view.LayoutInflater
import com.example.assignment1.databinding.ScoreItemBinding


class ScoreAdapter(private val scores: List<ScoreItem>) :
    RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>(){

    var scoreClickedCallback: ScoreClickedCallback? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScoreViewHolder {
        val binding = ScoreItemBinding
            .inflate(LayoutInflater.from(parent.context),
                parent,
                false)
        return ScoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        with(holder) {
            with(getItem(position)){
                binding.scoreLBLName.text = name
                binding.scoreLBLScore.text = score.toString()
            }
        }
    }

    override fun getItemCount(): Int = scores.size
    fun getItem(position: Int): ScoreItem = scores[position]

    inner class ScoreViewHolder(val binding: ScoreItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
                init {
                    binding.scoreCVEntry.setOnClickListener {
                        val position = bindingAdapterPosition // Use this instead
                        if (position != RecyclerView.NO_POSITION) {
                            scoreClickedCallback?.onScoreClicked(getItem(position))
                        }
                    }
                }
            }
}