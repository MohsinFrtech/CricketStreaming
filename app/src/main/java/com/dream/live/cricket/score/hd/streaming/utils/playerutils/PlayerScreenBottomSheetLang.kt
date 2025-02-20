package com.dream.live.cricket.score.hd.streaming.utils.playerutils

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.databinding.PlayerScreenBottmLayoutLangBinding
import com.dream.live.cricket.score.hd.streaming.utils.objects.Constants
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.common.collect.ImmutableList
import com.dream.live.cricket.score.hd.streaming.models.FormatDataAudio
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionOverride
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters
import com.traumsportzone.live.cricket.tv.ui.app.adapters.FormatDataAdapterAudio
import com.traumsportzone.live.cricket.tv.utils.interfaces.FormatSelectionAudio

class PlayerScreenBottomSheetLang(player: ExoPlayer, size: Int) : BottomSheetDialogFragment(),
    FormatSelectionAudio {
    private val sizeBottomSheet = (size + 2.0) * 10.5

    private var playerScreenBottomSheet: PlayerScreenBottmLayoutLangBinding? = null

    private val playerInstance = player
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransparentBackgroundDialog)
    }

    override fun onStart() {
        super.onStart()
        //this forces the sheet to appear at max height even on landscape
        val behavior = BottomSheetBehavior.from(requireView().parent as View)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.player_screen_bottm_layout_lang, container, false)
        playerScreenBottomSheet = DataBindingUtil.bind(layout)
        playerScreenBottomSheet?.lifecycleOwner = this
        setFormatDataAdapter()
        playerScreenBottomSheet?.doneBtn?.setOnClickListener {
            dismiss()
        }

        playerScreenBottomSheet?.backClick?.setOnClickListener {
            dismiss()
        }

        return layout
    }

    private fun setFormatDataAdapter() {
        if (!Constants.dataFormatsAudio.isNullOrEmpty()) {
            val adapter = FormatDataAdapterAudio(requireActivity(), this)
            playerScreenBottomSheet?.audioRecycler?.layoutManager = LinearLayoutManager(context)
            playerScreenBottomSheet?.audioRecycler?.adapter = adapter
            adapter?.submitList(Constants.dataFormatsAudio)

//            Log.d("List of Audios", Constants.dataFormatsAudio.toString())

//            Log.d("List of Apdater", adapter.toString())
        }
    }

    override fun onResume() {
        super.onResume()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomSheet: FrameLayout =
            dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!

        val percent = sizeBottomSheet.toFloat() / 100
        val dm = Resources.getSystem().displayMetrics
        val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
        val percentWidth = rect.width() * percent
        val percentheight = rect.height() * percent
        // Height of the view
        bottomSheet.layoutParams.height = percentheight.toInt()

        // Behavior of the bottom sheet
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.apply {
            peekHeight = resources.displayMetrics.heightPixels // Pop-up height
            state = BottomSheetBehavior.STATE_EXPANDED

            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                }
            })
        }
    }

//    @androidx.annotation.OptIn(UnstableApi::class)
//    @OptIn(UnstableApi::class)
    override fun navigation(viewId: FormatDataAudio, pos: Int, countryCode: String?) {
        val SUPPORTED_TRACK_TYPES = ImmutableList.of(
            C.TRACK_TYPE_VIDEO, C.TRACK_TYPE_AUDIO, C.TRACK_TYPE_TEXT, C.TRACK_TYPE_IMAGE
        )
        if (viewId.token == null) {
            val trackSelector = playerInstance.trackSelector as DefaultTrackSelector
            val newParameters = TrackSelectionParameters.Builder(/* context */).build()
            trackSelector.setParameters(newParameters)
        } else {

            var trackSelectionParameters: TrackSelectionParameters? = null
            ////
            val builder: TrackSelectionParameters.Builder? =
                playerInstance?.trackSelectionParameters?.buildUpon()
            builder?.clearOverridesOfType(
                C.TRACK_TYPE_AUDIO
            )
            // Getting track format and language.....
//            val format = viewId.trckGroup?.getTrackFormat(pos)
//            val trackLanguage = format?.language


            val trackSelector = playerInstance.trackSelector as DefaultTrackSelector
            // Assuming you have the trackGroup and trackIndex of the selected format

            var overrides: TrackSelectionOverride?=null
            ////////////////////////
            for (i in 0 until viewId.trckGroup?.length!!) {

                val trackFormat = viewId.trckGroup?.mediaTrackGroup!!.getFormat(i)
                if (trackFormat.language == countryCode) {
                    // Change to your target language
                    overrides = viewId.trckGroup?.mediaTrackGroup?.let {
                        TrackSelectionOverride(
                            it, i
                        )
                    }
                    Log.d("msgss","m"+trackFormat.language+" "+i)
//                    addOverride(TrackSelectionOverrides.TrackOverride(group.mediaTrackGroup!!, i))
                }
            }

            ////////////


//            val trackSelectionOverride =
//                viewId.trckGroup?.mediaTrackGroup?.let { TrackSelectionOverride(it, 0) }

            overrides?.let { builder?.setOverrideForType(it) }
            trackSelectionParameters = builder?.build()
            if (trackSelectionParameters != null) {
                trackSelector.setParameters(trackSelectionParameters)
            }
        }
        dismiss()
    }


}