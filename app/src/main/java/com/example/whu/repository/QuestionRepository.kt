package com.example.whu.repository

import com.example.whu.model.Question
import com.example.whu.model.QuestionCategory

interface QuestionRepository {
    fun getQuestions(): List<Question>
}

class StaticQuestionRepository : QuestionRepository {
    override fun getQuestions(): List<Question> {
        return listOf(
            Question(1, "I love playing in big groups with lots of friends!", QuestionCategory.E),
            Question(2, "I feel super happy when I meet new people!", QuestionCategory.E),
            Question(3, "I like being the leader in a game!", QuestionCategory.E),
            Question(4, "I prefer talking and telling stories to others!", QuestionCategory.E),
            Question(5, "I feel full of energy when I am around people!", QuestionCategory.E),
            Question(6, "I like to follow instructions step-by-step when building toys!", QuestionCategory.S),
            Question(7, "I prefer playing with things I already know and like!", QuestionCategory.S),
            Question(8, "I notice tiny details that others might miss!", QuestionCategory.S),
            Question(9, "I like things to be real rather than just make-believe!", QuestionCategory.S),
            Question(10, "I like to focus on what I am doing right now!", QuestionCategory.S),
            Question(11, "I make decisions based on what is fair for everyone!", QuestionCategory.T),
            Question(12, "I use my brain to solve problems instead of my feelings!", QuestionCategory.T),
            Question(13, "I think rules are very important when we play together!", QuestionCategory.T),
            Question(14, "I love solving puzzles and logical brain-teasers!", QuestionCategory.T),
            Question(15, "I like a clear 'yes' or 'no' answer to my questions!", QuestionCategory.T),
            Question(16, "I like to have a plan for what I will do all day!", QuestionCategory.J),
            Question(17, "I like to put my toys away right after I finish playing!", QuestionCategory.J),
            Question(18, "I feel better when I finish my chores or homework early!", QuestionCategory.J),
            Question(19, "I don't really like surprises that change my plans!", QuestionCategory.J),
            Question(20, "I like to know exactly what we are going to do next!", QuestionCategory.J)
        )
    }
}
