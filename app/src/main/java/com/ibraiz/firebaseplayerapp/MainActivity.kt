package com.ibraiz.firebaseplayerapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import com.ibraiz.firebaseplayerapp.view.SmartFragmentStatePagerAdapter
import com.ibraiz.firebaseplayerapp.view.VideoCountFragment
import com.ibraiz.firebaseplayerapp.view.VideoPlayerFragment
import com.ibraiz.firebaseplayerapp.view.VideosListFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewPagerVar: ViewPager

    private lateinit var adapterViewPager: FragmentStatePagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapterViewPager = object : SmartFragmentStatePagerAdapter(supportFragmentManager) {

            private val fragmentArray = arrayOf( VideosListFragment(), VideoCountFragment(), VideoPlayerFragment())
            private val fragmentNames = arrayOf("VDS", "VDS CLKS", "PLYR")

            override fun getItem(position: Int): Fragment = fragmentArray[position]

            override fun getCount(): Int = fragmentArray.size

            override fun getPageTitle(position: Int): CharSequence? = fragmentNames[position]

            /*override fun getItemPosition(`object`: Any): Int {
                return when(`object`){
                    VideosListFragment -> 0
                    VideoCountFragment -> 1
                    VideoPlayerFragment -> 2
                    else -> PagerAdapter.POSITION_UNCHANGED
                }
            }*/

        }
        // Set up the ViewPager with the sections adapter.
        viewPagerVar = findViewById(R.id.container)
        viewPagerVar.adapter = adapterViewPager
        tabs.setupWithViewPager(viewPagerVar)
    }

    /*{
        0 // Fragment # 0 - This will show FirstFragment
        -> VideosListFragment.newInstance(0, "Videos")
        1 // Fragment # 0 - This will show FirstFragment different title
        -> VideoCountFragment.newInstance(1, "Video Clicks")
        2 // Fragment # 1 - This will show SecondFragment
        -> VideoPlayerFragment.newInstance(2, "Vd Plyr")
        else -> null
    }*/

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
