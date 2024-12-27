package com.dream.live.cricket.score.hd.scores.ui.fragments.matchdetail

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dream.live.cricket.score.hd.R
import com.dream.live.cricket.score.hd.databinding.CommentaryFragmentBinding
import com.dream.live.cricket.score.hd.scores.adapter.CommentaryAdapter
import com.dream.live.cricket.score.hd.scores.adapter.OverAdapter
import com.dream.live.cricket.score.hd.scores.model.CommentryModelClass
import com.dream.live.cricket.score.hd.scores.utility.listeners.ApiResponseListener
import com.dream.live.cricket.score.hd.scores.viewmodel.CommentaryViewModel

class CommentaryFragment : Fragment(), ApiResponseListener {
    private val commentaryViewModel by lazy {
        ViewModelProvider(requireParentFragment())[CommentaryViewModel::class.java]
    }
    private var binding: CommentaryFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.commentary_fragment, container, false)
        binding = DataBindingUtil.bind(layout)
        binding?.lifecycleOwner = this
        commentaryViewModel.apiResponseListener = this
        initialState()
        getAllCommentaryRelatedToMatch()

        getSquadInfo()
        return layout
    }



    private fun initialState() {

        binding?.recentOvs?.visibility = View.GONE
        binding?.liveMatchDetail?.visibility = View.GONE
        binding?.recyclerViewCommentary?.visibility = View.GONE
        binding?.ketStatResult?.visibility = View.GONE
        binding?.playerOftheMatch?.visibility = View.GONE
        binding?.playerOftheMatchName?.visibility = View.GONE
        binding?.commentaryNotAvailable?.visibility = View.GONE
    }

    private fun getSquadInfo() {
        commentaryViewModel.getMatchTeamSquad()
        commentaryViewModel.squadModel.observe(viewLifecycleOwner, Observer {
            if (it != null) {

            }
        })
    }

    private fun getAllCommentaryRelatedToMatch() {
        commentaryViewModel?.commentaryModel?.observe(viewLifecycleOwner, Observer {
            if (!it?.commentaryList.isNullOrEmpty()) {
                binding?.model2 = it
                if (it != null) {
                    setUpCommentaryData(it)
                }
            }
            else{
                binding?.recentOvs?.visibility = View.GONE
                binding?.liveMatchDetail?.visibility = View.GONE
                binding?.recyclerViewCommentary?.visibility = View.GONE
                binding?.ketStatResult?.visibility = View.GONE
                binding?.playerOftheMatch?.visibility = View.GONE
                binding?.playerOftheMatchName?.visibility = View.GONE
                binding?.commentaryNotAvailable?.visibility = View.VISIBLE
            }
        })
    }

    //Set up of commentary data....
    private fun setUpCommentaryData(commentryModelClass: CommentryModelClass) {
        if (commentryModelClass?.matchHeader != null) {
            binding?.commentaryNotAvailable?.visibility = View.GONE
            if (commentryModelClass?.matchHeader?.state?.equals("Preview", true) == true
                || commentryModelClass?.matchHeader?.state?.equals("Delay", true) == true
                ||commentryModelClass?.matchHeader?.state?.equals("Abandon", true) == true

            ) {
                //match is preview means will started soon.
                binding?.ketStatResult?.visibility = View.GONE
                binding?.rcText?.visibility = View.GONE
                binding?.recentOvs?.visibility = View.GONE
                binding?.playerOftheMatch?.visibility = View.GONE
                binding?.playerOftheMatchName?.visibility = View.GONE
                binding?.liveMatchDetail?.visibility = View.GONE
                if (!commentryModelClass.commentaryList.isNullOrEmpty()) {

                    val listAdapter = CommentaryAdapter()
                    binding?.recyclerViewCommentary?.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    binding?.recyclerViewCommentary?.adapter = listAdapter
                    listAdapter.submitList(commentryModelClass.commentaryList)
                    binding?.recyclerViewCommentary?.visibility = View.VISIBLE

                }
            } else {

                if (commentryModelClass?.matchHeader?.state?.equals("Complete", true) == true) {

                    if (!commentryModelClass.matchHeader!!.playersOfTheMatch.isNullOrEmpty()) {

                        commentryModelClass.matchHeader!!.playersOfTheMatch.forEach {
                            binding?.playerOftheMatch?.visibility = View.VISIBLE
                            binding?.playerOftheMatchName?.visibility = View.VISIBLE
                            binding?.playerOftheMatchName?.text = it.fullName
                        }
                    } else {
                        binding?.playerOftheMatch?.visibility = View.GONE
                        binding?.playerOftheMatchName?.visibility = View.GONE
                    }
                } else {
                    binding?.playerOftheMatch?.visibility = View.GONE
                    binding?.playerOftheMatchName?.visibility = View.GONE
                }
                if (!commentryModelClass.commentaryList.isNullOrEmpty()) {

                    val listAdapter = CommentaryAdapter()
                    binding?.recyclerViewCommentary?.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    binding?.recyclerViewCommentary?.adapter = listAdapter
                    listAdapter.submitList(commentryModelClass.commentaryList)
                    binding?.recyclerViewCommentary?.visibility = View.VISIBLE

                } else {
                    binding?.recyclerViewCommentary?.visibility = View.GONE
                }

                if (commentryModelClass?.matchHeader?.state?.equals("Toss", true) == true) {
                    binding?.liveMatchDetail?.visibility = View.GONE
                    binding?.ketStatResult?.visibility = View.GONE
                    binding?.recentOvs?.visibility = View.GONE
                    binding?.rcText?.visibility = View.GONE
                    binding?.playerOftheMatch?.visibility = View.GONE
                    binding?.playerOftheMatchName?.visibility = View.GONE

                } else {
                    if (commentryModelClass.miniscore != null) {
                        binding?.ketStatResult?.visibility = View.VISIBLE
                        if (commentryModelClass.miniscore!!.recentOvsStats != null) {
                            val array = commentryModelClass.miniscore!!.recentOvsStats?.toCharArray()
                            val newcharArray = ArrayList<String>()
                            var count = 0
                            var scoreCount = 0

                            array?.forEach {
                                if (!Character.isWhitespace(it)) {
                                    if (it != '|') {
                                        if (it != '.') {
//                                            newcharArray.add(it.toString())
//                                            Log.d("RecentOvssss", "ovs" + it)

                                            if (count == "5".toInt() || count == "11".toInt())
                                            {
                                                newcharArray.add(it.toString())
                                                newcharArray.add("|")
//                                                if (newcharArray.remove("|")){
//                                                    scoreCount = newcharArray.sumOf { it.toInt() }
//                                                }

                                                Log.d("SumofScore", scoreCount.toString())
                                              //  newcharArray.add("| "+scoreCount)
                                                count ++
                                            }else{
                                                newcharArray.add(it.toString())
                                                Log.d("RecentOvssss", "ovs" + it)
                                                count ++
                                            }
                                        }
                                    }
                                }
                            }
                            binding?.recentOvs?.visibility = View.VISIBLE
                            binding?.rcText?.visibility = View.VISIBLE

                            val listAdapter = OverAdapter(requireContext())
                            binding?.recentOvs?.layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                            binding?.recentOvs?.adapter = listAdapter
                            listAdapter.submitList(newcharArray)
//                            binding?.recentOvs?.text = "Recent:..."+commentryModelClass.miniscore!!.recentOvsStats.toString()
                        } else {
                            binding?.recentOvs?.visibility = View.GONE
                        }


                        if (commentryModelClass.miniscore!!.partnerShip != null) {
                            if (commentryModelClass.miniscore!!.partnerShip?.runs != null && commentryModelClass.miniscore!!.partnerShip?.runs != 0) {
                                binding?.presult?.visibility = View.VISIBLE
                                binding?.presult?.text =
                                    "" + commentryModelClass.miniscore!!.partnerShip?.runs.toString() + "(" +
                                            commentryModelClass.miniscore!!.partnerShip?.balls.toString() + ")"


                            } else {
                                binding?.patner?.visibility = View.GONE
                                binding?.presult?.visibility = View.GONE
                            }
                        } else {
                            binding?.patner?.visibility = View.GONE
                            binding?.presult?.visibility = View.GONE
                        }

                        if (commentryModelClass.miniscore!!.lastWicket != null) {

                            if (commentryModelClass.miniscore!!.lastWicket != null) {
                                binding?.lastwicresult?.visibility = View.VISIBLE
                                binding?.lastwicresult?.text =
                                    commentryModelClass.miniscore!!.lastWicket.toString()
                            } else {
                                binding?.lastwic?.visibility = View.GONE
                                binding?.lastwicresult?.visibility = View.GONE
                            }
                        } else {
                            binding?.lastwic?.visibility = View.GONE
                            binding?.lastwicresult?.visibility = View.GONE
                        }

                        //overs left....
                        if (commentryModelClass.miniscore!!.oversRem != null) {

                            if (commentryModelClass.miniscore!!.oversRem != null) {
                                binding?.overLeftResult?.visibility = View.VISIBLE
                                binding?.overLeftResult?.text =
                                    commentryModelClass.miniscore!!.oversRem.toString()
                            } else {
                                binding?.overLeft?.visibility = View.GONE
                                binding?.overLeftResult?.visibility = View.GONE
                            }
                        } else {
                            binding?.overLeft?.visibility = View.GONE
                            binding?.overLeftResult?.visibility = View.GONE
                        }

                        //Toss Result
                        if (commentryModelClass.miniscore!!.matchScoreDetails != null) {

                            if (commentryModelClass.miniscore!!.matchScoreDetails?.tossResults != null) {
                                binding?.tossResult?.visibility = View.VISIBLE
                                binding?.tossResult?.text =
                                    "" + commentryModelClass.miniscore!!.matchScoreDetails?.tossResults?.tossWinnerName.toString() + "" +
                                            "(" + commentryModelClass.miniscore!!.matchScoreDetails?.tossResults?.decision.toString() + ")"
                            } else {
                                binding?.toss?.visibility = View.GONE
                                binding?.tossResult?.visibility = View.GONE
                            }
                        } else {
                            binding?.toss?.visibility = View.GONE
                            binding?.tossResult?.visibility = View.GONE
                        }

                        binding?.liveMatchDetail?.visibility = View.VISIBLE

                        /////////////////
                        if (commentryModelClass?.matchHeader?.state?.equals("Complete", true) == true) {
                            binding?.liveMatchDetail?.visibility = View.GONE
                        }

                    } else {
                        binding?.liveMatchDetail?.visibility = View.GONE
                    }
                }


            }

        }
    }

    override fun onStarted() {

    }

    override fun onSuccess() {

    }

    override fun onFailure(message: String) {
        binding?.recentOvs?.visibility = View.GONE
        binding?.liveMatchDetail?.visibility = View.GONE
        binding?.recyclerViewCommentary?.visibility = View.GONE
        binding?.ketStatResult?.visibility = View.GONE
        binding?.playerOftheMatch?.visibility = View.GONE
        binding?.playerOftheMatchName?.visibility = View.GONE
        binding?.commentaryNotAvailable?.visibility = View.VISIBLE
    }
}