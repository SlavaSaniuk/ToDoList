import './App.css';
import Home from './components/routes/Home.js'
import SignPage from "./components/routes/Sign";
import {BrowserRouter, Routes, Route} from "react-router-dom";


function App() {
  return (
      <BrowserRouter>
          <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/sign" element={<SignPage />} />
          </Routes>
      </BrowserRouter>
  );
}

export default App;
