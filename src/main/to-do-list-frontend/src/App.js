import './App.css';
import Home from './components/routes/Home.js'
import SignPage from "./components/routes/Sign";
import {UserPage, UserContent} from "./components/routes/UserPage.js";
import {BrowserRouter, Routes, Route} from "react-router-dom";


function App() {
  return (
      <BrowserRouter>
          <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/sign" element={<SignPage />} />
              <Route path={"/user/"} element={<UserPage />}>
                <Route path={":userId"} element={<UserContent />} />
              </Route>
          </Routes>
      </BrowserRouter>
  );
}

export default App;
