import './App.css';
import Home from './components/routes/Home.js'
import {BrowserRouter, Route} from "react-router-dom";


function App() {
  return (
      <BrowserRouter>

        <Route path="/" render={() => {
            return (<h1> Hello world! </h1>);
        }} />

      </BrowserRouter>
  );
}

export default App;
