package com.yalematta.trendingrepos.ui.repos

import com.yalematta.trendingrepos.data.model.Repo

interface RepoClickListener {
    fun onRepoClickListener(repo: Repo?)
}