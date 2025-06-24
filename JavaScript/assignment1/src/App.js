// import './App.css';
import Counter from './components/Counter';
import UserCard from './components/UserCard';

function App() {

  const user = {name: "surya", age: 22, email: "3suryasingh@gamil.com"};

  return (
    <div className="App">
      <UserCard userData={user}/>
      <span></span>
      <Counter/>
    </div>
  );
}

export default App;
