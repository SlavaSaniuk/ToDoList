import React from "react";
import {Logging} from "../../js/utils/Logging";
import '../../styles/fragments/AdministrationPanel.css'
import {ReqUtilities} from "../utilities/ReqUtilities";

class AdministrationBlock extends React.Component {

    constructor(props) {
        super(props);

        //Set initial state:
        this.state = ({
            isRender: false
        })
    }

    componentDidMount() {

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
                // Render this component:
                this.setState({isRender: true});
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

            </div>
        );
    }

}

export const AdministrationPanel =(props) => {
    return <AdministrationBlock gl_applicationUser={props.gl_applicationUser} />
}