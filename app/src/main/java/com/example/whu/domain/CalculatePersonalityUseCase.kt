package com.example.whu.domain

import com.example.whu.model.PersonalityResult
import com.example.whu.model.Question
import com.example.whu.model.QuestionCategory

/**
 * Interface for the personality calculation logic.
 * Adheres to Open/Closed Principle (can swap calculation logic).
 */
interface CalculatePersonalityUseCase {
    fun execute(questions: List<Question>, answers: Map<Int, Int>): PersonalityResult
}

class CalculatePersonalityUseCaseImpl : CalculatePersonalityUseCase {
    override fun execute(questions: List<Question>, answers: Map<Int, Int>): PersonalityResult {
        val scores = calculateScores(questions, answers)
        val mbti = getMBTI(scores)
        return PersonalityResult(
            mbtiType = mbti,
            jungType = getJungType(mbti),
            nickname = getNickname(mbti),
            description = getDescription(mbti)
        )
    }

    private fun calculateScores(questions: List<Question>, answers: Map<Int, Int>): Map<QuestionCategory, Int> {
        val totals = mutableMapOf(
            QuestionCategory.E to 0,
            QuestionCategory.S to 0,
            QuestionCategory.T to 0,
            QuestionCategory.J to 0
        )
        
        // Map answers back to categories based on the actual question list
        for ((id, score) in answers) {
            val question = questions.find { it.id == id }
            question?.let {
                totals[it.category] = (totals[it.category] ?: 0) + score
            }
        }
        return totals
    }

    private fun getMBTI(scores: Map<QuestionCategory, Int>): String {
        val eVal = scores[QuestionCategory.E] ?: 0
        val sVal = scores[QuestionCategory.S] ?: 0
        val tVal = scores[QuestionCategory.T] ?: 0
        val jVal = scores[QuestionCategory.J] ?: 0

        // Neutral point is 27.5 (avg 5.5 for 5 questions)
        val resE = if (eVal >= 28) "E" else "I"
        val resS = if (sVal >= 28) "S" else "N"
        val resT = if (tVal >= 28) "T" else "F"
        val resJ = if (jVal >= 28) "J" else "P"

        return "$resE$resS$resT$resJ"
    }

    private fun getJungType(mbti: String): String {
        return when (mbti) {
            "ESTJ", "ENTJ" -> "Extraverted Thinking (The Leader)"
            "ISTP", "INTP" -> "Introverted Thinking (The Analyzer)"
            "ESFJ", "ENFJ" -> "Extraverted Feeling (The Harmonizer)"
            "ISFP", "INFP" -> "Introverted Feeling (The Dreamer)"
            "ESTP", "ESFP" -> "Extraverted Sensation (The Adventurer)"
            "ISTJ", "ISFJ" -> "Introverted Sensation (The Protector)"
            "ENTP", "ENFP" -> "Extraverted Intuition (The Explorer)"
            "INTJ", "INFJ" -> "Introverted Intuition (The Visionary)"
            else -> "Unique Soul"
        }
    }

    private fun getNickname(mbti: String): String {
        return when (mbti) {
            "ISTJ" -> "Super Inspector"
            "ISFJ" -> "Kind Protector"
            "INFJ" -> "Wise Wizard"
            "INTJ" -> "Master Mind"
            "ISTP" -> "Expert Crafter"
            "ISFP" -> "Creative Artist"
            "INFP" -> "True Friend"
            "INTP" -> "Smart Inventor"
            "ESTP" -> "Action Hero"
            "ESFP" -> "Shining Star"
            "ENFP" -> "Sparkling Dreamer"
            "ENTP" -> "Clever Thinker"
            "ESTJ" -> "Great Boss"
            "ESFJ" -> "Happy Helper"
            "ENFJ" -> "Super Coach"
            "ENTJ" -> "Strong Leader"
            else -> "Mystery Hero"
        }
    }

    private fun getDescription(mbti: String): String {
        return when (mbti) {
            "ENFP" -> "You are a spark of energy! You love new ideas and making everyone feel happy and included. The world is your playground!"
            "ISTJ" -> "You are very reliable and like to make sure things are done the right way. Your friends know they can always count on you!"
            "ESFP" -> "You love the spotlight and making life a big party. You are great at making people laugh and enjoy the moment."
            "INTJ" -> "You have a big imagination and love to figure out how things work. You are a great problem solver!"
            "ISFJ" -> "You have a big heart and love helping others. You are like a cozy blanket for your friends!"
            "INFJ" -> "You are very wise and often know how others feel. You have a magical way of seeing the world."
            "ISTP" -> "You are a master at fixing things and exploring how they work. You are a real-life explorer!"
            "ISFP" -> "You are an artist at heart! You see beauty everywhere and love to express yourself in cool ways."
            "INFP" -> "You are a kind dreamer with a huge imagination. You always look for the good in everything."
            "INTP" -> "You are a super smart thinker! You love puzzles and coming up with original ideas."
            "ESTP" -> "You are brave and love excitement! You are always ready for a new adventure."
            "ENTP" -> "You are super clever and love to debate! You find the fun in every challenge."
            "ESTJ" -> "You are a natural leader who knows how to get things done. You keep everything organized and fun!"
            "ESFJ" -> "You are the best friend anyone could have! You love taking care of people and planning fun gatherings."
            "ENFJ" -> "You are a wonderful coach who brings out the best in everyone. You lead with a big smile!"
            "ENTJ" -> "You are a bold leader who can achieve anything you set your mind to. You inspire others to be their best!"
            else -> "You are a wonderful and unique person! You have special talents that make you a superhero in your own way. Keep being awesome!"
        }
    }
}
