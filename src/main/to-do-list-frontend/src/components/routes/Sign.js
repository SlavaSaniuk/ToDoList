import React from "react";
import '../../styles/common.css';
import '../../styles/sign.css';

class SignSubmitBlock extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className={"sign-submit-block"}>
                <input className={"sign-submit"} type="submit" value={this.props.inputValue} />
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
                <input className={"sign-input " +this.props.signInputClass} id={this.props.id} type={this.props.type} name={this.props.name} placeholder={this.props.placeholder}/>
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
    render() {
        return (
            <div>
                <form className={"sign-form"}>
                    <SignInputBlock id="sign-email" type="text" name="email" placeholder="Your email address"
                                    signImgClass="sign-input-img-email" />
                    <SignInputBlock id="sign-password" type="password" name="password" placeholder="Your password"
                                    signImgClass="sign-input-img-password" signInputClass="sign-input-password" />
                    <SignSubmitBlock inputValue={"Sign In"}/>
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
        let signForm = this.props.isSignIn ? <SignInForm /> : <SignUpForm />;
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
                <SignSwitchText changeFormFunc={this.props.changeFormFunc} isSignIn={this.props.isSignIn}/>
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
                <SignFormBlock isSignIn={this.state.isSignForm}/>
                <SignSwitch changeFormFunc={this.changeForm} isSignIn={this.state.isSignForm} />
            </div>
        );
    }
}

class SignPage extends React.Component {
    render() {
        return (
            <SignBlock />
        );
    }
}

export default SignPage;