package com.corona.covid_19tracker.Activity.Fragment

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.corona.covid_19tracker.R
import com.corona.covid_19tracker.databinding.ActivitySymptomBinding
import com.corona.covid_19tracker.utils.AdsTask
import com.google.android.gms.ads.AdRequest
import com.google.android.material.color.MaterialColors

class SymptomActivity : Fragment() {
    private lateinit var binding: ActivitySymptomBinding
    private var countAd = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivitySymptomBinding.inflate(layoutInflater)
        binding.sliderItem1.background =
            ContextCompat.getDrawable(requireActivity(), R.drawable.symptom_btn_background_with_rgb)
        binding.sliderItem1.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.symptomTitile.text = resources.getString(R.string.symptomTitle1) + " (Symptoms)"
        binding.symptomDetails.text = resources.getString(R.string.SymptomDetails1)
        binding.symptomImg.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_symptoms1
            )
        )

        val adTask = AdsTask(requireContext())

        binding.sliderItem1.setOnClickListener {
            binding.symptomTitile.text = resources.getString(R.string.symptomTitle1) + " (Symptoms)"
            binding.symptomDetails.text = resources.getString(R.string.SymptomDetails1)
            binding.symptomImg.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_symptoms1
                )
            )
            binding.sliderItem1.setTextColor(Color.WHITE)
            binding.sliderItem2.setTextColor((MaterialColors.getColor(it, R.attr.blue_and_white)))
            binding.sliderItem3.setTextColor((MaterialColors.getColor(it, R.attr.blue_and_white)))
            binding.sliderItem4.setTextColor((MaterialColors.getColor(it, R.attr.blue_and_white)))
            binding.sliderItem5.setTextColor((MaterialColors.getColor(it, R.attr.blue_and_white)))
            binding.sliderItem6.setTextColor((MaterialColors.getColor(it, R.attr.blue_and_white)))
            binding.sliderItem1.background =
                ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.symptom_btn_background_with_rgb
                )
            binding.sliderItem2.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.symptom_btn_background_without_rgb
            )
            binding.sliderItem3.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.symptom_btn_background_without_rgb
            )
            binding.sliderItem4.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.symptom_btn_background_without_rgb
            )
            binding.sliderItem5.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.symptom_btn_background_without_rgb
            )
            binding.sliderItem6.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.symptom_btn_background_without_rgb
            )


            if (countAd == 0) {
                adTask.showInterstitialAds()
                countAd++
            }
        }
        binding.sliderItem2.setOnClickListener {
            binding.symptomTitile.text = resources.getString(R.string.symptomTitle2) + " (Symptoms)"
            binding.symptomDetails.text = resources.getString(R.string.SymptomDetails2)
            binding.symptomImg.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.symptoms2
                )
            )
            binding.sliderItem2.setTextColor(Color.WHITE)
            binding.sliderItem1.setTextColor((MaterialColors.getColor(it, R.attr.blue_and_white)))
            binding.sliderItem3.setTextColor((MaterialColors.getColor(it, R.attr.blue_and_white)))
            binding.sliderItem4.setTextColor((MaterialColors.getColor(it, R.attr.blue_and_white)))
            binding.sliderItem5.setTextColor((MaterialColors.getColor(it, R.attr.blue_and_white)))
            binding.sliderItem6.setTextColor((MaterialColors.getColor(it, R.attr.blue_and_white)))
            binding.sliderItem2.background =
                ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.symptom_btn_background_with_rgb
                )
            binding.sliderItem1.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.symptom_btn_background_without_rgb
            )
            binding.sliderItem3.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.symptom_btn_background_without_rgb
            )
            binding.sliderItem4.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.symptom_btn_background_without_rgb
            )
            binding.sliderItem5.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.symptom_btn_background_without_rgb
            )
            binding.sliderItem6.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.symptom_btn_background_without_rgb
            )
        }
        binding.sliderItem3.setOnClickListener {
            binding.symptomTitile.text = resources.getString(R.string.symptomTitle3) + " (Symptoms)"
            binding.symptomDetails.text = resources.getString(R.string.SymptomDetails3)
            binding.symptomImg.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_symptoms3
                )
            )
            binding.sliderItem3.setTextColor(Color.WHITE)
            binding.sliderItem1.setTextColor((MaterialColors.getColor(it, R.attr.blue_and_white)))
            binding.sliderItem2.setTextColor((MaterialColors.getColor(it, R.attr.blue_and_white)))
            binding.sliderItem4.setTextColor((MaterialColors.getColor(it, R.attr.blue_and_white)))
            binding.sliderItem5.setTextColor((MaterialColors.getColor(it, R.attr.blue_and_white)))
            binding.sliderItem6.setTextColor((MaterialColors.getColor(it, R.attr.blue_and_white)))
            binding.sliderItem3.background =
                ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.symptom_btn_background_with_rgb
                )
            binding.sliderItem1.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.symptom_btn_background_without_rgb
            )
            binding.sliderItem2.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.symptom_btn_background_without_rgb
            )
            binding.sliderItem4.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.symptom_btn_background_without_rgb
            )
            binding.sliderItem5.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.symptom_btn_background_without_rgb
            )
            binding.sliderItem6.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.symptom_btn_background_without_rgb
            )
        }
        binding.sliderItem4.setOnClickListener {
            binding.symptomTitile.text = resources.getString(R.string.symptomTitle4) + " (Symptoms)"
            binding.symptomDetails.text = resources.getString(R.string.SymptomDetails4)
            binding.symptomImg.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_symptoms4
                )
            )
            binding.sliderItem4.setTextColor(Color.WHITE)
            binding.sliderItem1.setTextColor((MaterialColors.getColor(it, R.attr.blue_and_white)))
            binding.sliderItem2.setTextColor((MaterialColors.getColor(it, R.attr.blue_and_white)))
            binding.sliderItem3.setTextColor((MaterialColors.getColor(it, R.attr.blue_and_white)))
            binding.sliderItem5.setTextColor((MaterialColors.getColor(it, R.attr.blue_and_white)))
            binding.sliderItem6.setTextColor((MaterialColors.getColor(it, R.attr.blue_and_white)))
            binding.sliderItem4.background =
                ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.symptom_btn_background_with_rgb
                )
            binding.sliderItem1.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.symptom_btn_background_without_rgb
            )
            binding.sliderItem2.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.symptom_btn_background_without_rgb
            )
            binding.sliderItem3.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.symptom_btn_background_without_rgb
            )
            binding.sliderItem5.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.symptom_btn_background_without_rgb
            )
            binding.sliderItem6.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.symptom_btn_background_without_rgb
            )
        }
        binding.sliderItem5.setOnClickListener {
            binding.symptomTitile.text = resources.getString(R.string.symptomTitle5) + " (Symptoms)"
            binding.symptomDetails.text = resources.getString(R.string.SymptomDetails5)
            binding.symptomImg.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_symptoms5
                )
            )
            binding.sliderItem5.setTextColor(Color.WHITE)
            binding.sliderItem1.setTextColor((MaterialColors.getColor(it, R.attr.blue_and_white)))
            binding.sliderItem2.setTextColor((MaterialColors.getColor(it, R.attr.blue_and_white)))
            binding.sliderItem3.setTextColor((MaterialColors.getColor(it, R.attr.blue_and_white)))
            binding.sliderItem4.setTextColor((MaterialColors.getColor(it, R.attr.blue_and_white)))
            binding.sliderItem6.setTextColor((MaterialColors.getColor(it, R.attr.blue_and_white)))
            binding.sliderItem5.background =
                ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.symptom_btn_background_with_rgb
                )
            binding.sliderItem1.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.symptom_btn_background_without_rgb
            )
            binding.sliderItem2.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.symptom_btn_background_without_rgb
            )
            binding.sliderItem3.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.symptom_btn_background_without_rgb
            )
            binding.sliderItem4.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.symptom_btn_background_without_rgb
            )
            binding.sliderItem6.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.symptom_btn_background_without_rgb
            )
            if (countAd == 0) {
                adTask.showInterstitialAds()
                countAd++
            }
        }
        binding.sliderItem6.setOnClickListener {
            binding.symptomTitile.text = resources.getString(R.string.symptomTitle6) + " (Symptoms)"
            binding.symptomDetails.text = resources.getString(R.string.SymptomDetails6)
            binding.symptomImg.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_symptoms6
                )
            )
            binding.sliderItem6.setTextColor(Color.WHITE)
            binding.sliderItem1.setTextColor((MaterialColors.getColor(it, R.attr.blue_and_white)))
            binding.sliderItem2.setTextColor((MaterialColors.getColor(it, R.attr.blue_and_white)))
            binding.sliderItem3.setTextColor((MaterialColors.getColor(it, R.attr.blue_and_white)))
            binding.sliderItem4.setTextColor((MaterialColors.getColor(it, R.attr.blue_and_white)))
            binding.sliderItem5.setTextColor((MaterialColors.getColor(it, R.attr.blue_and_white)))
            binding.sliderItem6.background =
                ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.symptom_btn_background_with_rgb
                )
            binding.sliderItem1.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.symptom_btn_background_without_rgb
            )
            binding.sliderItem2.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.symptom_btn_background_without_rgb
            )
            binding.sliderItem3.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.symptom_btn_background_without_rgb
            )
            binding.sliderItem4.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.symptom_btn_background_without_rgb
            )
            binding.sliderItem5.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.symptom_btn_background_without_rgb
            )
            if (countAd == 0) {
                adTask.showInterstitialAds()
                countAd++
            }

        }

//admob ads
        adTask.nativeAdLoader(binding.myTemplate)


        return binding.root
    }
}