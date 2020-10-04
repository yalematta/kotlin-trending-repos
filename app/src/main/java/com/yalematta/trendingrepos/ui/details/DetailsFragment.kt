package com.yalematta.trendingrepos.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.transition.MaterialContainerTransform
import com.yalematta.trendingrepos.R
import com.yalematta.trendingrepos.data.model.Repo
import com.yalematta.trendingrepos.internal.DateUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_details, container, false)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val repo = arguments?.getParcelable<Repo>("repo")

        repo?.let {

            view.findViewById<TextView>(R.id.name).text = repo.name

            view.findViewById<ImageView>(R.id.avatar).apply {
                transitionName = repo.owner.avatar_url
                Glide.with(view)
                    .load(repo.owner.avatar_url)
                    .error(android.R.drawable.stat_notify_error)
                    .into(this)
            }

            view.findViewById<TextView>(R.id.username).text = repo.owner.login

            view.findViewById<TextView>(R.id.language).text = repo.language
            view.findViewById<TextView>(R.id.description).text = repo.description

            view.findViewById<TextView>(R.id.stars).text = repo.stars.toString()
            view.findViewById<TextView>(R.id.forks).text = repo.forks.toString()
            view.findViewById<TextView>(R.id.watchers).text = repo.watchers.toString()
            view.findViewById<TextView>(R.id.issuesOpened).text = repo.openIssues.toString()

            view.findViewById<TextView>(R.id.createDate).text =
                DateUtils.formatDate(repo.createDate)
            view.findViewById<TextView>(R.id.updateDate).text =
                DateUtils.formatDate(repo.updateDate)

            view.findViewById<TextView>(R.id.btnBrowse).setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(repo.url))
                startActivity(browserIntent)
            }

        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                view?.let { Navigation.findNavController(it).navigateUp() }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}