package com.yalematta.trendingrepos.ui.repos

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yalematta.trendingrepos.data.model.Repo
import com.yalematta.trendingrepos.databinding.ItemTrendingRepoBinding


class RepoViewHolder(private val binding: ItemTrendingRepoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(repo: Repo) {
        binding.apply {

            Glide.with(itemView)
                .load(repo.owner.avatar_url)
                .centerCrop()
                .error(android.R.drawable.stat_notify_error)
                .into(avatar)

            val str = SpannableString( repo.owner.login + " / " + repo.name)
            str.setSpan(
                StyleSpan(Typeface.BOLD),
                repo.owner.login.length,
                str.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            name.text = str

            description.text = repo.description

            language.text = repo.language

        }
    }
}