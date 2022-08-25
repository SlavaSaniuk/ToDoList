import React from "react";
import '../../styles/common.css';
import '../../styles/sign.css';

class SignExceptionMessageBlock extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className={"sign-exception-message-block"}>
                <p> {this.props.exceptionMessage} </p>
            </div>
        );
    }
}

class SignSubmitBlock extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className={"sign-submit-block"}>
                <input className={"sign-submit"} type="submit" value={this.props.inputValue} onClick={this.props.clickFunc} />
            </div>
        );
    }
}

class SignInputBlock extends React.Component {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <div className={"sign-input-block"}>
                <div className={"sign-input-img " +this.props.signImgClass} />
                <input className={"sign-input " +this.props.signInputClass} id={this.props.id}
                       type={this.props.type} name={this.props.name} placeholder={this.props.placeholder}
                        ref={this.props.inputRef}
                />
            </div>
        );
    }
}

class SignText extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className={"sign-text"}>
                <p>{this.props.text}</p>
            </div>
        );
    }
}

class SignUpForm extends React.Component {
    render() {
        return (
            <div>
                <form className={"sign-form"}>
                    <SignInputBlock id="sign-email" type="text" name="email" placeholder="Your email address"
                                    signImgClass="sign-input-img-email" />
                    <SignInputBlock id="sign-name" type="text" name="name" placeholder="Your name"
                                    signImgClass="sign-input-img-name" />
                    <SignInputBlock id="sign-password" type="password" name="password" placeholder="Your password"
                                    signImgClass="sign-input-img-password" signInputClass="sign-input-password" />
                    <SignSubmitBlock inputValue={"Sign Up"}/>
                </form>
            </div>
        );
    }
}

class SignInForm extends React.Component {
    constructor(props) {
        super(props);
        this.logIn.bind(this);

        // Create refs to child inputs:
        this.emailInputRef = React.createRef();
        this.passwordInputRef = React.createRef();

        this.state = {isException: false};
    }


    logIn = async (event) => {
        event.preventDefault();

        this.props.hideExceptionMessageFunc();

        console.log("Try to login account:");
        // Construct account object
        let loginAccount = {
            email: this.emailInputRef.current.value,
            password: this.passwordInputRef.current.value
        };
        console.log("Account to login: ", JSON.stringify(loginAccount));

        // Fetch date:
        let response = await fetch("http://localhost:8080/rest/sign/login", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'},
            body: JSON.stringify(loginAccount)
        });

        // Handle result:
        let signRestDto = await response.json();
        if (signRestDto.exception) {
            // If 601 Bad credentials' exception:
            if (signRestDto.exceptionCode === 601)
                this.props.showExceptionMessageFunc("Email or password is incorrect");
        }

    }

    render() {
        return (
            <div>
                <form className={"sign-form"}>
                    <SignInputBlock id="sign-email" type="text" name="email" placeholder="Your email address"
                                    signImgClass="sign-input-img-email" inputRef={this.emailInputRef} />
                    <SignInputBlock id="sign-password" type="password" name="password" placeholder="Your password"
                                    signImgClass="sign-input-img-password" signInputClass="sign-input-password"
                                    inputRef={this.passwordInputRef} />
                    <SignSubmitBlock inputValue={"Sign In"} clickFunc={this.logIn} />
                </form>
            </div>
        );
    }
}

class SignFormBlock extends React.Component {
    constructor(props) {
        super(props);
        this.state = {isSignIn: this.props.isSignIn};
    }

    render() {
        let signForm = this.props.isSignIn ?
            <SignInForm showExceptionMessageFunc={this.props.showExceptionMessageFunc}
                        hideExceptionMessageFunc={this.props.hideExceptionMessageFunc}/> : <SignUpForm />;
        let signFormText = this.props.isSignIn ? "Please, sign in" : "Please, sign up";
        return (
            <div className={"sign-form-block"}>
                <SignText text={signFormText}/>
                {signForm}
            </div>
        );
    }
}

class SignSwitchText extends React.Component {
    constructor(props) {
        super(props);
        this.handleSwitchClick.bind(this);
    }

    handleSwitchClick = () => {
        this.props.hideExceptionMessageFunc();
        this.props.changeFormFunc();
    }

    render() {
        let signQuestion = this.props.isSignIn ? "Not registered?" : "Already registered?";
        let signText = this.props.isSignIn ? "Create account" : "Log in";
        return (
            <div className={"sign-switch-text"}>
                <p> {signQuestion} <span className={"sign-switch-text-link"} onClick={this.handleSwitchClick}> {signText} </span></p>
            </div>
        );
    }
}

class SignSwitchImage extends React.Component {
    constructor(props) {
        super(props);
    }
    render() {
        let imageAddClas = this.props.isSignIn ? "sign-switch-image-block-in" : "sign-switch-image-block-up";
        return (
            <div className={imageAddClas} />
        );
    }
}

class SignSwitch extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className={"sign-switch"}>
                <SignSwitchImage isSignIn={this.props.isSignIn}/>
                <SignSwitchText changeFormFunc={this.props.changeFormFunc} isSignIn={this.props.isSignIn}
                                hideExceptionMessageFunc={this.props.hideExceptionMessageFunc} />
            </div>
        );
    }
}

class SignBlock extends React.Component {
    constructor(props) {
        super(props);
        this.changeForm.bind(this);

        this.state = {
            isSignForm: true,
        };
    }

    changeForm = () => {
        this.setState({isSignForm: !this.state.isSignForm});
    }

    render() {
        return (
            <div className={"sign-block"}>
                <SignFormBlock isSignIn={this.state.isSignForm} showExceptionMessageFunc={this.props.showExceptionMessageFunc}
                               hideExceptionMessageFunc={this.props.hideExceptionMessageFunc}/>
                <SignSwitch changeFormFunc={this.changeForm} isSignIn={this.state.isSignForm}
                            hideExceptionMessageFunc={this.props.hideExceptionMessageFunc}/>
            </div>
        );
    }
}

class SignContainer extends React.Component {
    constructor(props) {
        super(props);

        this.hideExceptionMessage.bind(this);
        this.showExceptionMessage.bind(this);

        this.state = {
            isException: false,
            exceptionMessage: ""
        };
    }

    hideExceptionMessage = () => {
        this.setState({
            isException: false,
            exceptionMessage: ""
        });
    }

    showExceptionMessage = (anExceptionMessage) => {
        // Render exception message block:
        this.setState({
            isException: true,
            exceptionMessage: anExceptionMessage
        });
    }

    render() {
        let signExceptionMessageBlock;
        if(this.state.isException)
            signExceptionMessageBlock = <SignExceptionMessageBlock exceptionMessage={this.state.exceptionMessage} />;
        return(
            <div className={"sign-container"}>
                <SignBlock showExceptionMessageFunc={this.showExceptionMessage} hideExceptionMessageFunc={this.hideExceptionMessage}/>
                {signExceptionMessageBlock}
            </div>
        );
    }
}

class SignPage extends React.Component {



    render() {
        return (
            <SignContainer />
        );
    }
}

export default SignPage;