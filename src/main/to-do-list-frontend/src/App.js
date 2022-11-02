import './App.css';
import Home from './components/routes/Home.js'
import SignPage from "./components/routes/Sign";
import {UserPage, UserPageFunctional} from "./components/routes/UserPage.js";
import {BrowserRouter, Routes, Route} from "react-router-dom";
import React from 'react';
import {ApplicationUser} from "./GlobalVars";

const globalVars = {
    applicationUser: new ApplicationUser()
}

function App() {
  sessionStorage.setItem("SERVER_URL", "http://localhost:8080")
  return (
      <BrowserRouter>
          <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/sign" element={<SignPage applicationUser={globalVars.applicationUser} />} />
              <Route path={"/user/"} element={<UserPage />}>
                <Route path={":userId"} element={<UserPageFunctional applicationUser={globalVars.applicationUser} />} />
              </Route>
          </Routes>
      </BrowserRouter>
  );
}

export default App;
