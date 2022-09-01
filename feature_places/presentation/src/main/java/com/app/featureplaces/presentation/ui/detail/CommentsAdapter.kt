package com.app.featureplaces.presentation.ui.detail

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.domain.Comment
import com.app.presentation.databinding.ViewCommentBinding
import com.app.featureplaces.presentation.ui.common.basicDiffUtil
import com.app.featureplaces.presentation.ui.common.inflate
import com.app.presentation.R

class CommentsAdapter(private val listener: (Comment) -> Unit) : ListAdapter<Comment, CommentsAdapter.ViewHolder>(
    basicDiffUtil { old, new -> old.id == new.id }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_comment,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment=getItem(position)
        holder.bind(comment)
        holder.itemView.setOnClickListener { listener(comment) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ViewCommentBinding.bind(view)
        fun bind(comment: Comment) {
            binding.comment=comment
        }
    }
}