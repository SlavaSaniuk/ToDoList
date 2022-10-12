import React from "react";
import '../../styles/fragments/AddTaskBlock.css'

const AddTaskBlockControlPanel =(props) => {
    return (
        <div> </div>
    );
}

class AddTaskBlock extends React.Component {

    render() {
        return (
            <div className={"add-task-block"}>
                <input type={"text"} className={"add-task-block-name-input"} placeholder={"Do financial report."} />
                <textarea className={"add-task-block-desc-area"} placeholder={"Description"} />
                <AddTaskBlockControlPanel />
            </div>
        );
    }
}

export {AddTaskBlock};
