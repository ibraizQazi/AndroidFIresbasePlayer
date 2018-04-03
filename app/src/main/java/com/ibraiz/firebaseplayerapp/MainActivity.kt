package com.ibraiz.firebaseplayerapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v4.app.FragmentPagerAdapter
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.ibraiz.firebaseplayerapp.models.VideoItem
import com.ibraiz.firebaseplayerapp.view.VideosListFragment


class MainActivity : AppCompatActivity() {

    private lateinit var mPagerAdapter: FragmentPagerAdapter
    private lateinit var mViewPager: ViewPager


/*    override fun onStart() {
        super.onStart()
        //loadDatabase(firebaseInstanceRef)
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPagerAdapter = object : FragmentPagerAdapter(supportFragmentManager) {
            private val mFragments = arrayOf<Fragment>(VideosListFragment(), Fragment(), Fragment())
            private val mFragmentNames = arrayOf("Vd Lst", "Vd Cnt", "Plyr")
            override fun getItem(position: Int): Fragment {
                return mFragments[position]
            }

            override fun getCount(): Int {
                return mFragments.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return mFragmentNames[position]
            }
        }
        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container)
        mViewPager.adapter = mPagerAdapter
        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(mViewPager)
    }

 /*   fun loadDatabase(firebaseDataRef: DatabaseReference) {

        val availableVideos: List<VideoItem> = mutableListOf(
                VideoItem("Video1", "https://player.vimeo.com/external/257512761.hd.mp4?s=2f4ede50164f921df0e039f7e11281ebe665a9fc&profile_id=175"),
                VideoItem("Video2", "https://player.vimeo.com/external/257512779.hd.mp4?s=3ed4f4fc920fecb37121c77855b9069e5172f6c8&profile_id=175")
        )
        availableVideos.forEach {
            val key = firebaseDataRef.child("videoItems").push().key
            it.uuid = key
            firebaseDataRef.child("videoItems").child(key).setValue(it)
        }

    }*/
}
