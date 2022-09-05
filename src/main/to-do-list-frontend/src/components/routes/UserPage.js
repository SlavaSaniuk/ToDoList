import React from "react";
import {useParams, Outlet} from "react-router-dom";
import MenuHeader from "../fragments/MenuHeader";
import {HttpConfiguration} from "../../objects/HttpConfiguration";
import '../../styles/common.css';

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
            "contentStatus": this.contentStatus.LOADING
        }
    }

    componentDidMount() {
        this.fetchUserByID(this.props.userId);
    }

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

    }

    render() {
        // Check content status:
        let contentBlock;
        if (this.state.contentStatus === this.contentStatus.LOADING) {
            contentBlock = <div className={"red-loader"}></div>
        }


        return(
            <div>
                <MenuHeader />
                {contentBlock}
            </div>
        );
    }
}

/**
 * UserPageFunctional is React component which define router hooks.
 * @returns {JSX.Element} - not arguments.
 * @constructor - empty constructor.
 */
const UserPageFunctional = () => {
    let params = useParams();
    return (<UserPageContent userId={params.userId} />);
}

/**
 * UserPage REACT component is the root component, which render users page.
 */
class UserPage extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return(
            <div>
            <Outlet />
            </div>
        );
    }
}

export {UserPage, UserPageFunctional};