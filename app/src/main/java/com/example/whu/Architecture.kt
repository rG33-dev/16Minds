package com.example.whu

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class QuestionCategory {
    E, S, T, J
}

data class Question(
    val id: Int,
    val text: String,
    val category: QuestionCategory
)

data class PersonalityResult(
    val mbtiType: String,
    val jungType: String,
    val nickname: String,
    val description: String
)

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

class PersonalityViewModel(
    private val repository: QuestionRepository,
    private val calculatePersonalityUseCase: CalculatePersonalityUseCase
) : ViewModel() {

    private val _currentScreen = mutableStateOf("welcome")
    val currentScreen: State<String> = _currentScreen

    private val _currentPage = mutableIntStateOf(0)
    val currentPage: State<Int> = _currentPage

    private val _answers = mutableStateMapOf<Int, Int>()
    val answers: Map<Int, Int> = _answers

    private val _questions = mutableStateOf(repository.getQuestions())
    val questions: State<List<Question>> = _questions

    private val _result = mutableStateOf<PersonalityResult?>(null)
    val result: State<PersonalityResult?> = _result

    private val pageSize = 5

    fun startToInstructions() {
        _currentScreen.value = "instructions"
    }

    fun startQuiz() {
        _currentScreen.value = "home"
    }

    fun updateAnswer(questionId: Int, score: Int) {
        _answers[questionId] = score
    }

    fun nextPage() {
        val totalPages = (questions.value.size + pageSize - 1) / pageSize
        if (_currentPage.intValue < totalPages - 1) {
            _currentPage.intValue++
        } else {
            processResults()
        }
    }

    fun previousPage() {
        if (_currentPage.intValue > 0) {
            _currentPage.intValue--
        } else {
            _currentScreen.value = "instructions"
        }
    }

    private fun processResults() {
        viewModelScope.launch {
            _currentScreen.value = "loading"
            delay(2500)
            questions.value.forEach { q ->
                if (!_answers.containsKey(q.id)) {
                    _answers[q.id] = 5
                }
            }
            _result.value = calculatePersonalityUseCase.execute(_questions.value, _answers)
            _currentScreen.value = "result"
        }
    }

    fun resetQuiz() {
        _answers.clear()
        _currentPage.intValue = 0
        _result.value = null
        _currentScreen.value = "welcome"
    }

    fun getQuestionsForCurrentPage(): List<Question> {
        val start = _currentPage.intValue * pageSize
        val end = minOf(start + pageSize, questions.value.size)
        return if (start < questions.value.size) {
            questions.value.subList(start, end)
        } else {
            emptyList()
        }
    }

    fun isLastPage(): Boolean {
        val totalPages = (questions.value.size + pageSize - 1) / pageSize
        return _currentPage.intValue == totalPages - 1
    }
}

class PersonalityViewModelFactory(
    private val repository: QuestionRepository,
    private val calculatePersonalityUseCase: CalculatePersonalityUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonalityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PersonalityViewModel(repository, calculatePersonalityUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
