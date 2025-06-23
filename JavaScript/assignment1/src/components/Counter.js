import React from 'react'
import { useState } from 'react'

export default function Counter() {

    const [count, setCount] = useState(0);
    const [user, setUser] = useState("");
    const [hide, setHide] = useState(false);

    const handleChange = (e) => {
        setUser(e.target.value);
    };

    const togglePara = () => {
        setHide((prev) => !prev);
    }

  return (
    <div style={styles.container}>
        <div style={styles.card}>
            <button onClick={()=>setCount(count+1)}>+1 </button>
            {count}
            <button onClick={()=>setCount(count-1)}> -1</button>
        </div>
        <div>
            <input type='text' value={user} onChange={handleChange}></input>
            <h3>{user}</h3>
        </div>
        <div style={styles.container}>
            <button onClick={togglePara}>View</button>
            {hide && (
                <p style={styles.paragraph}>This is hidden paragrapgh</p>
            )}
        </div>
    </div>
  )
}

const styles = {
  card: {
    padding: "1rem",
    margin: "1rem",
    border: "1px solid #ccc",
    borderRadius: "8px",
    width: "70px",
    background: "#f0f8ff",
    textAlign: "center",
  },
  container: {
    margin: "20px",
    textAlign: "center",
  },
  paragraph: {
    marginTop: "15px",
    color: "#444",
    fontSize: "1.1rem",
  },
};