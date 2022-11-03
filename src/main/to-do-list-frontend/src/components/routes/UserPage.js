import React from "react";
import {useParams, Outlet} from "react-router-dom";
import MenuHeader from "../fragments/MenuHeader";
import '../../styles/common.css';
import '../../styles/userpage.css';
import {TasksBlock} from "../fragments/TasksBlock";
import {AdministrationPanel} from "../fragments/AdministrationPanel";

/**
 * UserPageContent REACT component user to render user page content.
 * @property userId - user ID path variable.
 */
class UserPageContent extends React.Component {
    constructor(props) {
        super(props);

        // Bind functions:
        this.fetchUserByID.bind(this);

        // User object:
        this.userObj = {
            userId: null,
            userName: null }

        // User content statuses:
        this.contentStatus = ["LOADING", "LOADED"];

        this.state = {
            "contentStatus": this.contentStatus.LOADED
        }
    }

    componentDidMount() {
        this.fetchUserByID(this.props.userId);
    }

    /**
     * Method get user object from server by its ID.
     * @param userId - user ID (Specified by properties).
     * @returns {Promise<void>} - nothing.
     */
    fetchUserByID = async (userId) => {

        let request = await fetch("http://localhost:8080/rest/users/" +userId, {
            method: 'GET',
            mode: 'cors',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Authorization': sessionStorage.getItem('JWT')
            }
        });

        let userRestDto = await request.json();

        if (userRestDto.exception) {
            if (userRestDto.exceptionCode === 669) {
                console.log("User not found.");
                return;
            }
        }

        // Map properties:
        this.userObj.userId = userRestDto.userId;
        this.userObj.userName = userRestDto.userName;

        // Change content status to LOADED:
        this.setState({"contentStatus": this.contentStatus.LOADED});
        // Set page title:
        document.title = this.userObj.userName;
    }

    /**
     * Render user content block.
     * @returns {JSX.Element} - User content block.
     */
    render() {
        // Check content status:
        let contentBlock;
        if (this.state.contentStatus === this.contentStatus.LOADING) {
            contentBlock = <div className={"red-loader"}></div>
        }

        if (this.state.contentStatus === this.contentStatus.LOADED) {
            contentBlock = (
                <div>
                    <MenuHeader userObj={this.userObj} />
                    <div className={"user-page-content"} >
                        <TasksBlock userId={this.props.userId} />
                    </div>
                </div>
            );
        }

        return(
            <div>
                {contentBlock}
            </div>
        );
    }
}

/**
 * UserPageFunctional is React component which define router hooks.
 * @property gl_applicationUser - application user global variable.
 * @returns {JSX.Element} - not arguments.
 * @constructor - empty constructor.
 */
const UserPageFunctional = (props) => {
    let params = useParams();

    console.log("Application user: ", props.applicationUser);

    return (
        <div>
            <AdministrationPanel gl_applicationUser={props.gl_applicationUser} />
            <UserPageContent userId={params.userId} />
        </div>

    )
}

/**
 * UserPage REACT component is the root component, which render users page.
 */
class UserPage extends React.Component {
    render() {
        return(
            <div>
            <Outlet/>
            </div>
        );
    }
}

export {UserPage, UserPageFunctional};