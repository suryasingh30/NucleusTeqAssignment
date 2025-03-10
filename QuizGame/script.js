let questions = [];
let currentQuestionIndex = 0;
let score = 0;
let timer;
let timeLeft = 15;

const startScreen = document.getElementById('start-screen');
const gameScreen = document.getElementById('game-screen');
const endScreen = document.getElementById('end-screen');
const categorySelect = document.getElementById('category');
const difficultySelect = document.getElementById('difficulty');
const startButton = document.getElementById('start-button');
const questionText = document.getElementById('question-text');
const optionsContainer = document.getElementById('options-container');
const timerElement = document.getElementById('timer');
const scoreElement = document.getElementById('score');
const feedbackElement = document.getElementById('feedback');
const feedbackText = document.getElementById('feedback-text');
const nextButton = document.getElementById('next-button');
const finalScoreElement = document.getElementById('final-score');
const playAgainButton = document.getElementById('play-again-button');

startButton.addEventListener('click', startGame);
nextButton.addEventListener('click', loadNextQuestion);
playAgainButton.addEventListener('click', resetGame);

async function fetchQuestions() {
    try {
        const category = categorySelect.value;
        const difficulty = difficultySelect.value;
        const url = `https://opentdb.com/api.php?amount=10&category=${category}&difficulty=${difficulty}&type=multiple`;
        
        const response = await fetch(url);
        const data = await response.json();
        
        if (data.response_code === 0) {
            return data.results;
        } else {
            throw new Error('Failed to fetch questions');
        }
    } catch (error) {
        console.error('Error fetching questions:', error);
        alert('Failed to load questions. Please try again.');
        return [];
    }
}

async function startGame() {
    startButton.textContent = 'Loading...';
    startButton.disabled = true;
    
    questions = await fetchQuestions();
    
    if (questions.length === 0) {
        startButton.textContent = 'Start Quiz';
        startButton.disabled = false;
        return;
    }
    
    currentQuestionIndex = 0;
    score = 0;
    scoreElement.textContent = `Score: ${score}`;
    
    startScreen.classList.add('hidden');
    gameScreen.classList.remove('hidden');
    
    loadQuestion();
}

function loadQuestion() {
    const currentQuestion = questions[currentQuestionIndex];
    
    feedbackElement.classList.add('hidden');
    optionsContainer.innerHTML = '';
    
    questionText.textContent = decodeHTMLEntities(currentQuestion.question);
    
    const options = [
        ...currentQuestion.incorrect_answers,
        currentQuestion.correct_answer
    ];
    
    shuffleArray(options);
    
    const optionLetters = ['A', 'B', 'C', 'D'];
    options.forEach((option, index) => {
        const optionElement = document.createElement('div');
        optionElement.className = 'option';
        optionElement.innerHTML = `
            <span class="option-letter">${optionLetters[index]}</span>
            <span>${decodeHTMLEntities(option)}</span>
        `;
        optionElement.addEventListener('click', () => selectAnswer(option));
        optionsContainer.appendChild(optionElement);
    });
    
    startTimer();
}

function startTimer() {
    timeLeft = 15;
    timerElement.textContent = timeLeft;
    
    timer = setInterval(() => {
        timeLeft--;
        timerElement.textContent = timeLeft;
        
        if (timeLeft <= 0) {
            clearInterval(timer);
            handleTimeUp();
        }
    }, 1000);
}

function handleTimeUp() {
    const currentQuestion = questions[currentQuestionIndex];
    const correctAnswer = currentQuestion.correct_answer;
    
    const options = document.querySelectorAll('.option');
    options.forEach(option => {
        option.style.pointerEvents = 'none';
        const optionText = option.querySelector('span:last-child').textContent;
        
        if (optionText === decodeHTMLEntities(correctAnswer)) {
            option.classList.add('correct');
        }
    });
    
    feedbackText.textContent = `Time's up! The correct answer is: ${decodeHTMLEntities(correctAnswer)}`;
    feedbackElement.classList.remove('hidden');
}

function selectAnswer(selectedAnswer) {
    clearInterval(timer);
    
    const currentQuestion = questions[currentQuestionIndex];
    const correctAnswer = currentQuestion.correct_answer;
    
    const options = document.querySelectorAll('.option');
    options.forEach(option => {
        option.style.pointerEvents = 'none';
        const optionText = option.querySelector('span:last-child').textContent;
        
        if (optionText === decodeHTMLEntities(correctAnswer)) {
            option.classList.add('correct');
        } else if (optionText === decodeHTMLEntities(selectedAnswer) && selectedAnswer !== correctAnswer) {
            option.classList.add('incorrect');
        }
    });
    
    if (selectedAnswer === correctAnswer) {
        score++;
        scoreElement.textContent = `Score: ${score}`;
        feedbackText.textContent = 'Correct answer!';
    } else {
        feedbackText.textContent = `Incorrect. The correct answer is: ${decodeHTMLEntities(correctAnswer)}`;
    }
    
    feedbackElement.classList.remove('hidden');
}

function loadNextQuestion() {
    currentQuestionIndex++;
    
    if (currentQuestionIndex < questions.length) {
        loadQuestion();
    } else {
        endGame();
    }
}

function endGame() {
    gameScreen.classList.add('hidden');
    endScreen.classList.remove('hidden');
    
    finalScoreElement.textContent = `Your final score: ${score}/${questions.length}`;
}

function resetGame() {
    endScreen.classList.add('hidden');
    startScreen.classList.remove('hidden');
    
    startButton.textContent = 'Start Quiz';
    startButton.disabled = false;
}

function decodeHTMLEntities(text) {
    const textArea = document.createElement('textarea');
    textArea.innerHTML = text;
    return textArea.value;
}

function shuffleArray(array) {
    for (let i = array.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [array[i], array[j]] = [array[j], array[i]];
    }
    return array;
}
