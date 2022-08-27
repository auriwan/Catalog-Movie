package com.elthobhy.catalogmovie.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.elthobhy.catalogmovie.R
import com.elthobhy.catalogmovie.core.domain.model.DomainModel
import com.elthobhy.catalogmovie.core.utils.Constants
import com.elthobhy.catalogmovie.databinding.ActivityDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent.getParcelableExtra<DomainModel>(Constants.DATA)
        if (intent != null) {
            showDetail(intent)
        }
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.detail)
    }


    private fun showDetail(intent: DomainModel) {
        binding.apply {
            Glide.with(this@DetailActivity)
                .load(Constants.IMAGE_LINK + intent.backdrop_path)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }
                })
                .into(imageBackdrop)
            titleDetail.text = intent.title
            tvReleaseDate.text = intent.releaseDate
            voteCount.text = intent.voteCount.toString()
            popularCount.text = intent.popularity.toString()
            starCount.text = intent.voteAverage.toString()
            overviewDetail.text = intent.overview

            var favorite = intent.favorite
            setFavoriteState(favorite)
            fabFavorite.setOnClickListener {
                favorite = !favorite
                detailViewModel.setFavoriteMovie(intent, favorite)
                setFavoriteState(favorite)
            }
        }
    }

    private fun setFavoriteState(favorite: Boolean) {
        if (favorite) {
            binding.fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_bookmark_24
                )
            )
        } else {
            binding.fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_bookmark_border_24
                )
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> true
        }
    }
}