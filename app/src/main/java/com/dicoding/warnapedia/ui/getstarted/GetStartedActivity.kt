package com.dicoding.warnapedia.ui.getstarted

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.dicoding.warnapedia.R
import com.dicoding.warnapedia.data.SliderData
import com.dicoding.warnapedia.databinding.ActivityGetStartedBinding
import com.dicoding.warnapedia.ui.MainActivity

class GetStartedActivity : AppCompatActivity() {

    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var sliderList: ArrayList<SliderData>
    private lateinit var binding: ActivityGetStartedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sliderList = ArrayList()
        sliderList.add(
            SliderData(
                resources.getString(R.string.color_palette_recomendation),
                resources.getString(R.string.color_palette_recomendation_desc),
                R.drawable.get_started_1
            )
        )

        sliderList.add(
            SliderData(
                resources.getString(R.string.save_color_palette_recomendation),
                resources.getString(R.string.save_color_palette_recomendation_desc),
                R.drawable.get_started_2
            )
        )

        sliderList.add(
            SliderData(
                resources.getString(R.string.access_saved_color_palette),
                resources.getString(R.string.access_saved_color_palette_desc),
                R.drawable.get_started_3
            )
        )

        sliderList.add(
            SliderData(
                resources.getString(R.string.color_blind_feature),
                resources.getString(R.string.color_blind_feature_desc),
                R.drawable.get_started_4
            )
        )

        sliderAdapter = SliderAdapter(this, sliderList)
        binding.idViewPager.adapter = sliderAdapter
        binding.idViewPager.addOnPageChangeListener(viewListener)

        binding.bSkip.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    var viewListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }
        override fun onPageSelected(position: Int) {
            when(position){
                0 -> {
                    binding.idTVSlideOne.setTextColor(resources.getColor(R.color.black))
                    binding.idTVSlideTwo.setTextColor(resources.getColor(R.color.D8D8D8))
                    binding.idTVSlideThree.setTextColor(resources.getColor(R.color.D8D8D8))
                    binding.idTVSlideFour.setTextColor(resources.getColor(R.color.D8D8D8))
                    binding.bSkip.text = resources.getString(R.string.SKIP)
                }
                1 -> {
                    binding.idTVSlideOne.setTextColor(resources.getColor(R.color.D8D8D8))
                    binding.idTVSlideTwo.setTextColor(resources.getColor(R.color.black))
                    binding.idTVSlideThree.setTextColor(resources.getColor(R.color.D8D8D8))
                    binding.idTVSlideFour.setTextColor(resources.getColor(R.color.D8D8D8))
                    binding.bSkip.text = resources.getString(R.string.SKIP)
                }
                2 -> {
                    binding.idTVSlideOne.setTextColor(resources.getColor(R.color.D8D8D8))
                    binding.idTVSlideTwo.setTextColor(resources.getColor(R.color.D8D8D8))
                    binding.idTVSlideThree.setTextColor(resources.getColor(R.color.black))
                    binding.idTVSlideFour.setTextColor(resources.getColor(R.color.D8D8D8))
                    binding.bSkip.text = resources.getString(R.string.SKIP)
                }
                3 -> {
                    binding.idTVSlideOne.setTextColor(resources.getColor(R.color.D8D8D8))
                    binding.idTVSlideTwo.setTextColor(resources.getColor(R.color.D8D8D8))
                    binding.idTVSlideThree.setTextColor(resources.getColor(R.color.D8D8D8))
                    binding.idTVSlideFour.setTextColor(resources.getColor(R.color.black))
                    binding.bSkip.text = resources.getString(R.string.GET_STARTED)
                }
            }
        }
        override fun onPageScrollStateChanged(state: Int) {}
    }
}