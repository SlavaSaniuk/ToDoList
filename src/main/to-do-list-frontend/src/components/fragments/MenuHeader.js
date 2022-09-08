import React from "react";
import '../../styles/fragments/MenuHeader.css'
import '../../styles/common.css'
import "bootstrap/dist/css/bootstrap.min.css";

/**
 * MenuHeader React component render top menu at user's page.
 */

/**
 * Username block. Print username at the menu.
 * @param props - username string.
 * @returns {JSX.Element} - <p> username </p>.
 * @constructor - react properties object.
 */
const UserName = (props) => {
    return(<p className={"user-name-block"}> {props.userName} </p>);
}

/**
 * User avatar. Print user avatar or default icon.
 * @returns {JSX.Element} - <div className={"user-avatar"} />
 * @constructor - react properties object.
 */
const UserAvatar = () => {
    return (<div className={"user-avatar"} />);
}

/**
 * Users menu tab is dropdown menu for access user preferences.
 * @returns - (<div className={"user-pref-tab col-3"}>
 *                 <UserAvatar />
 *                 <UserName userName={"[USER_NAME]"} />
 *             </div>)
 */
class UserMenuTab extends React.Component {
    render() {
        return(
            <div className={"user-pref-tab col-3"}>
                <UserAvatar />
                <UserName userName={"[USER_NAME]"} />
            </div>
        );
    }
}

/**
 * This filler used to fill empty space between SearchInputBox and UserMenuTab
 * @param props - {
 *     className - filler div column class name.
 * }
 * @returns {JSX.Element} - (<div className={props.colClass} />);
 * @constructor - react properties object.
 */
const MenuWideBarFiller = (props) => {
    return (<div className={props.colClass} />);
}

/**
 * SearchInputFiled is a html input which responds on user click to focus or blur events.
 * @constructor - React properties object.
 * @property focusesFunc - parent focus function.
 * @state - {
 *     focus: boolean - true if input has a focus.
 * };
 */
class SearchInputField extends React.Component {
    constructor(props) {
        super(props);

        this.focuses.bind(this);
        this.unfocuses.bind(this);

        this.state = {"focus": false};
    }

    /**
     * Function call when input get a user focus. Function call parent function via props,focusesFunc with true param.
     */
    focuses = () => {
        this.props.focusesFunc(true);
        this.setState({"focus": true});
    }

    /**
     * Function call when input lost user focus. Function call parent function via props,focusesFunc with false param.
     */
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

/**
 * SearchIcon render div with background search-icon-black.png image.
 * @returns {JSX.Element} - <div className={"search-icon"} />.
 */
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
        this.setState({"focus": isFocus});
        this.props.toggleParentFunc(isFocus);
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

/**
 * MenuWideBar object render search box, filler and user tab in boostrap row.
 */
class MenuWideBar extends React.Component {
    constructor(props) {
        super(props);

        this.toggleFocus.bind(this);

        this.state = {
            "focus": false
        }
    }

    /**
     * Rerender SearchInputBox and filler when search input get and lost focus.
     * @param isFocus - search input has or not focus flag.
     */
    toggleFocus = (isFocus) => {
        this.setState({"focus": isFocus});
    }

    render() {
        let fillerColClass = this.state.focus ? "col-6" : "col-7";
        console.log("Focus: " +this.state.focus);
        return(
            <div className={"menu-wide-bar col row"}>
                <SearchInputBox toggleParentFunc={this.toggleFocus} />
                <MenuWideBarFiller colClass={fillerColClass} />
                <UserMenuTab />
            </div>
        );
    }
}

/**
 * Tabs separator.
 * @returns {JSX.Element} - <div className={"menu-separator col"} />.
 */
const MenuSeparator = () => {
    return (<div className={"menu-separator col"} />);
}

/**
 * Menu tab.
 * @returns {JSX.Element} - <div className={"menu-tab col-1"}> </div>.
 */
const MenuTab = () => {
    return (
        <div className={"menu-tab col-1"}> </div>
    );
}

/**
 * menu content block align at center of screen and render menu tabs.
 * @returns {JSX.Element} - (
 *          <div className={"menu-header-content mx-auto row"}>
 *             <MenuTab />
 *             <MenuSeparator />
 *             <MenuWideBar />
 *         </div>).
 */
const MenuHeaderContent = () => {
    return (
        <div className={"menu-header-content mx-auto row"}>
            <MenuTab />
            <MenuSeparator />
            <MenuWideBar />
        </div>
    );
}

/**
 * Html header tag with fixed position on top of the screen.
 * @returns {JSX.Element} - (
 *          <header className={"container-fluid menu-header-block"}>
 *                 <MenuHeaderContent />
 *         </header>).
 */
const MenuHeaderBlock = () =>  {
    return (
        <header className={"container-fluid menu-header-block"}>
                <MenuHeaderContent />
        </header>
    );
}

/**
 * MenuHeaderFunctional react object defind any used hooks.
 * @returns {JSX.Element} - <MenuHeaderBlock />
 */
const MenuHeaderFunctional = () => {
    return (
        <MenuHeaderBlock />
    );
}

/**
 * Root react class that represent a user menu header.
 */
class MenuHeader extends React.Component {
    render() {
        return (
            <MenuHeaderFunctional />
        )
    }
}

export default MenuHeader;