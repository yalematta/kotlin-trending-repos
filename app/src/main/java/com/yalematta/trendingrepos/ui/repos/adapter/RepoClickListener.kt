package com.yalematta.trendingrepos.ui.repos.adapter

import com.yalematta.trendingrepos.data.model.Repo

interface RepoClickListener {
    fun onRepoClickListener(repo: Repo?)
}