import React from "react";
import '../../styles/fragments/MenuHeader.css'
import "bootstrap/dist/css/bootstrap.min.css";

class UserPrefTab extends React.Component {
    render() {
        return(
            <div className={"user-pref-tab col-3"} />
        );
    }
}

const MenuWideBarFiller = (props) => {
    return (<div className={"this.props.colClass"} />);
}

class SearchInputField extends React.Component {
    constructor(props) {
        super(props);

        this.focuses.bind(this);
        this.unfocuses.bind(this);

        this.state = {"focus": false};
    }

    focuses = () => {
        this.props.focusesFunc(true);
        this.setState({"focus": true});
    }

    unfocuses = () => {
        this.props.focusesFunc(false);
        this.setState({"focus": false})
    }

    render() {

        let inputClasses = this.state.focus ? "search-input-field-focuses" : "search-input-field-unfocuses";


        return(
            <input className={"search-input-field " +inputClasses} onFocus={this.focuses} onBlur={this.unfocuses}/>
        );
    }
}

const SearchIcon = () => {
    return (<div className={"search-icon"} />)
}

class SearchInputBox extends React.Component {
    constructor(props) {
        super(props);

        this.toggleFocus.bind(this);

        this.state = {
            "focus": false
        }
    }

    toggleFocus = (isFocus) => {
        console.log("Set focus");
        this.setState({"focus": isFocus});
    }

    render() {
        let boxClasses = this.state.focus ? "search-input-box-focuses col-3" : "search-input-box col-2";
        return(
            <div className={boxClasses}>
                <SearchIcon />
                <SearchInputField focusesFunc={this.toggleFocus}/>
            </div>
        );
    }
}

class MenuWideBar extends React.Component {
    constructor(props) {
        super(props);

        this.toggleFocus.bind(this);

        this.state = {
            "focus": false
        }
    }

    toggleFocus = (isFocus) => {
        this.setState({"focus": isFocus});
    }

    render() {
        return(
            <div className={"menu-wide-bar col row"}>
                    <SearchInputBox to />
                <MenuWideBarFiller />
                <UserPrefTab />
            </div>
        );
    }
}

const MenuSeparator = () => {
    return (<div className={"menu-separator col"} />);
}

class MenuTab extends React.Component {
    render() {
        return(
            <div className={"menu-tab col-1"}>
            </div>
        );
    }
}

class MenuHeaderContent extends React.Component {
    render() {
        return (
            <div className={"menu-header-content mx-auto row"}>
                <MenuTab />
                <MenuSeparator />
                <MenuWideBar />
            </div>
        );
    }
}

class MenuHeaderBlock extends React.Component {
    render() {
        return (
            <header className={"container-fluid menu-header-block"}>
                <MenuHeaderContent />
            </header>
        );
    }
}

const MenuHeaderFunctional = () => {
    return (
        <MenuHeaderBlock />
    );
}

class MenuHeader extends React.Component {
    render() {
        return (
            <MenuHeaderFunctional />
        )
    }
}

export default MenuHeader;