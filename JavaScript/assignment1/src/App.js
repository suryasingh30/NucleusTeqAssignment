// import './App.css';
import Counter from './components/Counter';
import UserCard from './components/UserCard';

function App() {

  const user = {name: "surya", age: 22, email: "3suryasingh@gamil.com"};

  return (
    <div className="App">
      {/* <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header> */}
      <UserCard userData={user}/>
      <span></span>
      <Counter/>
    </div>
  );
}

export default App;
