import React from "react";
import {useParams, Outlet} from "react-router-dom";
import MenuHeader from "../fragments/MenuHeader";
import {HttpConfiguration} from "../../objects/HttpConfiguration";

/**
 * UserPageWrapper is the root dynamic element of user page.
 * @property userId - user ID path variable.
 */
class UserPageWrapper extends React.Component {
    constructor(props) {
        super(props);

        // Bind functions:
        this.fetchUserByID.bind(this);

        // User object:
        this.userObj = {
            userId: null,
            userName: null }
    }

    componentDidMount() {
        this.fetchUserByID(this.props.userId);
    }

    fetchUserByID = async (userId) => {

        console.log("JWT: ", HttpConfiguration.jwt);

        let request = await fetch("http://localhost:8080/rest/users/" +userId, {
            method: 'GET',
            mode: 'cors',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Authorization': 'Beaver ' +HttpConfiguration.jwt
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
        return(
            <div>
                <MenuHeader />
                <h1> Hello user {this.userObj.userName}! </h1>
            </div>
        );
    }
}

const UserContent = () => {
    let params = useParams();
    return (<UserPageWrapper userId={params.userId} />);
}

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

export {UserPage, UserContent};