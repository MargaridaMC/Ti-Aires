package net.teamtruta.tiaires.views

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import net.teamtruta.tiaires.App
import net.teamtruta.tiaires.R
import net.teamtruta.tiaires.data.models.GeoCacheInTourWithDetails
import net.teamtruta.tiaires.viewModels.GeoCacheDetailViewModel
import net.teamtruta.tiaires.viewModels.GeoCacheDetailViewModelFactory

class GeoCacheDetailActivity : AppCompatActivity(){

    private val viewModel: GeoCacheDetailViewModel by viewModels{
        GeoCacheDetailViewModelFactory((application as App).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_geo_cache_detail)
        val toolbar = findViewById<Toolbar>(R.id.toolbar_geo_cache_detail)
        setSupportActionBar(toolbar)
        val geoCacheID = intent.getLongExtra(App.GEOCACHE_IN_TOUR_ID_EXTRA, -1L)

        // Setup Action Bar
        val ab = supportActionBar!!
        ab.setDisplayHomeAsUpEnabled(true)

        // Observe geocache that was clicked on
        viewModel.getGeoCacheInTourFromID(geoCacheID).observe(this,
                { geoCacheInTour: GeoCacheInTourWithDetails -> setupToolbarName(geoCacheInTour) })

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                val bundle = bundleOf("geoCacheID" to geoCacheID)
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add<GeoCacheDetailFragment>(R.id.fragment_container_view, args = bundle)
                }
            }
        }

    }

    private fun setupToolbarName(geoCacheInTour: GeoCacheInTourWithDetails) {
        val currentGeoCache = geoCacheInTour.geoCache.geoCache

        // Set GeoCache Title
        val ab = supportActionBar
        ab!!.title = currentGeoCache.name
    }

    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this, TourActivity::class.java)

        setResult(RESULT_OK, intent)
        finish()
        return true
    }
    /*
    override fun onBackPressed() {
        saveChanges()
        onSupportNavigateUp()
    }*/


}