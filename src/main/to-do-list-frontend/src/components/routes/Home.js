import React from "react";
import ReactDOM from "react-dom/client";
import '../../styles/common.css';
import '../../styles/home.css';

class SignMessageBlock extends React.Component {
    render() {
        return (
          <div className={"sign-message-block"}> Please, <a href={"/sign"}> Sign </a> to start usage To Do list app.</div>
        );
    }
}

class ApplicationNameBlock extends React.Component {
    render() {
        return(
            <h1> To Do list </h1>
        );
    }
}

class WelcomeMessageText extends React.Component {
    render() {
        return (
            <div>
                <p>
                    Hello dear user! We are happy to welcome you at to-do-list.com web application.
                    Our application helps you to organize you tasks and a whole your life.
                    <br/>
                    Good luck!
                </p>
            </div>
        );
    }
}

class WelcomeMessage extends React.Component {
    render() {
        return (
            <div className={"welcome-message"}>
                <ApplicationNameBlock />
                <WelcomeMessageText />
                <SignMessageBlock />
            </div>
        );
    }
}

class Home extends React.Component {
    render() {
        return (
            <WelcomeMessage />
        )
    }
}

export default Home;