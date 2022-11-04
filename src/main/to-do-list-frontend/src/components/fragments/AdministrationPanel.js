import React from "react";
import {Logging} from "../../js/utils/Logging";
import '../../styles/fragments/AdministrationPanel.css'
import {ReqUtilities} from "../utilities/ReqUtilities";
import {Menu, MenuDirection, MenuItem} from "../ui/Menu";

export const AdministrationPanel =(props) => {
    return <AdministrationBlock gl_applicationUser={props.gl_applicationUser} />
}

/**
 * AdministrationBlock is root component of AdministrationPanel fragment that check render or do not render
 * AdministrationPanel. If application user has administration roles - ApplicationPanel will be rendered (see
 * ComponentDidMount function).
 * Component props:
 * @property gl_applicationUSer - application user global variable;
 */
class AdministrationBlock extends React.Component {

    /**
     * Construct new react component.
     * Constructor set initial component state.
     * @param props - component props.
     */
    constructor(props) {
        super(props);

        //Set initial state:
        this.state = ({
            isRender: false, // Rendering flag;
            userRoles: [] // Array of user roles;
        })
    }

    /**
     * Check if application user has administration roles, and if it has - render this component (set state isRender
     * flag to true). Send get request on "/rest/administration/hello" url and if response status code is 200 - user
     * has administration roles (Spring security work), else if response code <> 200 (403) - user doesn't have
     * administration roles.
     * If user have administration roles, function get it's by sending get request on "/rest/administration/user-roles"
     * url. Then map fetched roles to state "userRoles" property and render this component.
     */
    componentDidMount() {

        const fetchedUserRoles = []; // Fetched roles:

        // Create fetch request to check if application user has admin roles:
        fetch(ReqUtilities.absoluteUrl("/rest/administration/hello"), {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'text/html',
                'Authorization': sessionStorage.getItem('JWT')
            }
        }).then(result => {
            if (result.ok) {
                Logging.log("Hello admin user: ", this.props.gl_applicationUser.loadApplicationUser().userId);

                // Get user roles:
                ReqUtilities.getRequest("/rest/administration/user-roles").then(response => {
                    if (response.ok) {
                        // Get response body (list of user roles):
                        response.json().then((listOfUserRoles => {
                            // Add user roles to user roles list:
                            listOfUserRoles.forEach(userRole => fetchedUserRoles.push(userRole));

                            // Render component:
                            this.setState(prevState => {
                                // Return new state:
                                return {
                                    isRender: true,
                                    userRoles: prevState.userRoles.concat(fetchedUserRoles)
                                }
                            });
                        }))
                    }
                })
            }else {
                Logging.log("User is not admin!");
                this.setState({isRender: false});
            }
        });


    }

    /**
     * Render this component,
     * @returns {JSX.Element|null} - administration block.
     */
    render() {

        // Check if needed render this component:
        // If not needed, not render:
        if (!this.state.isRender) return null;

        return (
            <div className={"admin-block"}>
                <AdministrationMenu >
                    <AdministrationMenuItem itemText={"Users management"} />
                    <AdministrationMenuItem itemText={"Tasks management"} />
                    <AdministrationMenuItem itemText={"Server configuration"} />
                </AdministrationMenu>
            </div>
        );
    }

}

const AdministrationMenu =(props) => {
    return <Menu menuDirection={MenuDirection.VERTICAL} menuClass={"administration-menu"}> {props.children} </Menu>
}

const AdministrationMenuItem =(props) => {
    return <MenuItem itemClass={"administration-menu-item"} itemText={props.itemText} />
}