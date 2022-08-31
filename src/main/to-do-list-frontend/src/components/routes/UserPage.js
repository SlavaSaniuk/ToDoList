import React from "react";
import {useParams, Outlet} from "react-router-dom";
import MenuHeader from "../fragments/MenuHeader";

/**
 * UserPageWrapper is the root dynamic element of user page.
 * @property userId - user ID path variable.
 */
class UserPageWrapper extends React.Component {
    constructor(props) {
        super(props);
    }
    render() {
        return(
            <div>
                <MenuHeader />
                Hello user {this.props.userId}!
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