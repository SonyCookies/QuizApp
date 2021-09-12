package com.sonnyapps.quizapp

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.sonnyapps.quizapp.databinding.ActivityQuizQuestionBinding

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {

    private var optionList: ArrayList<TextView>? = null
    private var mCurrentPosition:Int = 1
    private var mQuestionList: ArrayList<Question>? = null
    private var mSelectedOptionPosition : Int = 0
    private var checked: Boolean = false
    private var approve: Boolean = false

    private lateinit var binding: ActivityQuizQuestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz_question)

        optionList?.add(binding.tvOptionOne)
        optionList?.add(binding.tvOptionTwo)
        optionList?.add(binding.tvOptionThree)
        optionList?.add(binding.tvOptionFour)


        mQuestionList = Constants.getQuestions()
        setQuestion()

        binding.tvOptionOne.setOnClickListener(this)
        binding.tvOptionTwo.setOnClickListener(this)
        binding.tvOptionThree.setOnClickListener(this)
        binding.tvOptionFour.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)
    }

    private fun setQuestion() {

        val question: Question = mQuestionList!![mCurrentPosition-1]

        defaultOptionsView()

        "SUBMIT".also { binding.btnSubmit.text = it }

        binding.progressBar.progress = mCurrentPosition
        (mCurrentPosition.toString() + " / " + binding.progressBar.max).also { binding.tvProgress.text = it }

        (question.question).also { binding.tvQuestion.text = it }
        binding.ivImage.setImageResource(question.image)
        (question.optionOne).also { binding.tvOptionOne.text = it }
        (question.optionTwo).also { binding.tvOptionTwo.text = it }
        (question.optionThree).also { binding.tvOptionThree.text = it }
        (question.optionFour).also { binding.tvOptionFour.text = it }
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        options.add(0, binding.tvOptionOne)
        options.add(1, binding.tvOptionTwo)
        options.add(2, binding.tvOptionThree)
        options.add(3, binding.tvOptionFour)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }
    }

    override fun onClick(v: View?) {

        when(v?.id){
            R.id.tv_option_one -> {
                selectedOptionsView( binding.tvOptionOne, 1)
            }
            R.id.tv_option_two -> {
                selectedOptionsView( binding.tvOptionTwo, 2)
            }
            R.id.tv_option_three -> {
                selectedOptionsView( binding.tvOptionThree, 3)
            }
            R.id.tv_option_four -> {
                selectedOptionsView( binding.tvOptionFour, 4)
            }
            R.id.btn_submit -> {
                if(mSelectedOptionPosition == 0){

                    if(!approve) {
                        Toast.makeText(this, "You didn't pick an answer yet.", Toast.LENGTH_SHORT)
                            .show()
                    }
                    if(checked) {
                        when{
                            mCurrentPosition <= mQuestionList!!.size - 1-> {
                                mCurrentPosition++
                                approve = false
                                onClickDisabler(true)
                                setQuestion()
                            } else -> {
                            Toast.makeText(this, "You have successfully completed the Quiz", Toast.LENGTH_SHORT).show()
                            }
                        }
                        checked = false
                    }

                } else {
                    checker()
                    onClickDisabler(false)
                    checked = true
                    approve = true
                }
                mSelectedOptionPosition = 0
            }
        }

    }


    private fun onClickDisabler(key: Boolean) {
        binding.tvOptionOne.isClickable = key
        binding.tvOptionTwo.isClickable = key
        binding.tvOptionThree.isClickable = key
        binding.tvOptionFour.isClickable = key
    }

    private fun checker() {
        val question = mQuestionList!![mCurrentPosition - 1]
        if(question.correctAnswer != mSelectedOptionPosition) {
            answerView(mSelectedOptionPosition, R.drawable.wrong_option_bg)
        }
        answerView(question.correctAnswer, R.drawable.correct_option_bg)

        if(mCurrentPosition == mQuestionList!!.size) {
            "FINISH".also { binding.btnSubmit.text = it }
        }else {
            "GO TO NEXT QUESTION".also {
                binding.btnSubmit.text = it
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when(answer){
            1-> {
                binding.tvOptionOne.background = ContextCompat.getDrawable(this, drawableView)
            }
            2-> {
                binding.tvOptionTwo.background = ContextCompat.getDrawable(this, drawableView)
            }
            3-> {
                binding.tvOptionThree.background = ContextCompat.getDrawable(this, drawableView)
            }
            4-> {
                binding.tvOptionFour.background = ContextCompat.getDrawable(this, drawableView)
            }
        }
    }


    private fun selectedOptionsView(tv: TextView, selectedOptionNumber: Int) {

        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNumber

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )

    }
}