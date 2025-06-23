import React from 'react'
import UserDetails from './UserDetails'

export default function UserCard({userData}){
  return (
    <div style={styles.container}>
        <div style={styles.card}>
            <h3>{userData.name}</h3>
            <UserDetails age={userData.age} email={userData.email} />
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
    width: "300px",
    background: "#f0f8ff",
  },
  container: {
    margin: "20px",
    textAlign: "center",
  },
};
