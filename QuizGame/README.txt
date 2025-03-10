Software Requirements Specification (SRS) Document 
1. Introduction 
The objective of the quiz game is to allow users to answer questions from various categories 
within a time limit. The game will have three levels: Easy, Medium, and Hard. Each question 
has a 15-second timer for the user to select an answer. If no answer is selected within the time 
frame, the correct answer will be displayed. The game fetches questions via an API based on 
the selected category and level. 
2. Functional Requirements 
2.1 User Interface (UI) 
 Category Selection: The user can choose from various categories of questions. 
 Level Selection: The user can select the difficulty level (Easy, Medium, Hard). 
 Question Display: Display one question at a time, with four multiple-choice options (A, 
B, C, D). 
 Timer: A countdown timer of 15 seconds will be visible. 
 Answer Feedback: 
o When the timer expires, the correct answer is displayed. 
o If an answer is selected, it shows if the answer is correct or incorrect, and the 
correct answer will be displayed. 
o Increase the score if the answer is correct. 
o Show the correct answer if the selected answer is wrong. 
2.2 API Integration 
 API Endpoint: Use an API to fetch quiz questions based on the selected category and 
difficulty level. 
 Parameters: 
o Category (string) 
o Difficulty (Easy/Medium/Hard) 
2.3 Game Flow 
 Start Screen: Display a start screen with options to select category and level. 
 Question Flow: Questions are fetched from the API. For each question: 
1. Display question and options. 
2. Display a 15-second timer. 
3. The player selects an answer or the timer runs out. 
4. Show feedback on the answer. 
5. Move to the next question. 
 Score Display: The score will be updated based on correct answers. 
 End Screen: When all questions are answered, display the final score and option to play 
again. 
2.4 Game Rules 
 The user must answer questions within 15 seconds. 
 Correct answers will increase the score by 1 point. 
4thFloor, Brilliant Platina, Scheme Number 78, Vijay Nagar, Part II 
 Incorrect answers will show the correct answer. 
 The game ends when all questions for the selected category and difficulty are answered. 
3. Non-Functional Requirements 
3.1 Performance 
 The application must load within 2 seconds. 
 Each question should load within 3 seconds. 
3.2 Usability 
 The game should have a simple and clear UI. 
 The user should be able to start, play, and end the game easily. 
3.3 Compatibility 
 The quiz game should work on all major browsers (Chrome, Firefox, Safari, Edge). 
 The game should be responsive and work well on both desktop and mobile devices. 
3.4 Security 
 Ensure that any data fetched from the API is securely handled and free from malicious 
data. 
4. System Design 
4.1 High-Level Architecture 
 Frontend: HTML, CSS, JavaScript for the user interface and game logic. 
 Backend (API): The backend will handle fetching quiz questions based on category and 
difficulty. It will provide data to the frontend. 
4.2 Frontend Components 
1. Start Screen: 
o Buttons for selecting category and difficulty. 
o Button to start the game. 
2. Game Screen: 
o Display the question and options. 
o Display a 15-second timer. 
o Buttons for selecting an answer (A, B, C, D). 
3. Feedback Display: 
o Show whether the selected answer is correct or incorrect. 
o Display the correct answer if needed. 
o Show the updated score. 
4. End Screen: 
o Show the final score. 
o Button to play again. 
5. Development Technologies 
5.1 Frontend 
 HTML: Markup for the game structure. 
 CSS: Styling for an interactive and pleasing UI. 
4thFloor, Brilliant Platina, Scheme Number 78, Vijay Nagar, Part II 
o Use Flexbox or Grid for layout. 
o Use animations for transitions and timer countdown. 
o Responsive design for mobile compatibility. 
 JavaScript: For game logic and API interaction. 
o Use fetch() /async() await() to get questions from the API. 
o Implement the timer functionality using setTimeout() or setInterval(). 
o Handle UI updates and score logic. 
5.2 API 
 Use an external API like the Open Trivia Database (OTDB) to fetch quiz questions by 
category and difficulty. 
6. Conclusion 
This SRS outlines the functionality for a simple, interactive quiz game with three difficulty levels. 
The system will fetch questions based on the selected category and difficulty, offer a 15-second 
timer to answer, provide feedback, and track the user's score. The design ensures an engaging 
and easy-to-use interface, ensuring a fun user experience. 