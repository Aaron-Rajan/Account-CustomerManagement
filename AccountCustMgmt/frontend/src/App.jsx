import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import Homepage from './components/Homepage'
import ShowCustomers from './components/ShowCustomers'
import CreateCustomer from './components/CreateCustomer'

function App() {
  const [count, setCount] = useState(0)

  return (
    <div>
      <Homepage/>
      <ShowCustomers/>
      <CreateCustomer/>
    </div>
  )
}

export default App
