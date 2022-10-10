import React from "react";
import '../../styles/common.css'
import '../../styles/fragments/TasksBlock.css'
import Task from "../dto/Task";

const TasksFooter =() => {
    return(<div className={"tasks-footer"}>
        <p className={"tasks-footer-text"}> LOAD MORE ... </p>
    </div>);
}

const TasksContentBlock =() => {
    return(<div>
        <Task taskId={1} taskName={"Do something!"} />
        <Task taskId={3} taskName={"To be."} />
        <Task taskId={5} taskName={"Or not be!"} />
    </div>);
}

const TasksFilterTab =(props) => {
    return(
        <p className={"tasks-filter-tab tasks-filter-tab-text col-2"}>{props.tabText}</p>
    );
}

const TasksFilterPanel =() => {
    return(<div className={"tasks-filter-panel tasks-menu-panel col-4 row"}>
        <TasksFilterTab tabText={"9"} />
        <TasksFilterTab tabText={"10"} />
        <TasksFilterTab tabText={"+7"} />
        <TasksFilterTab tabText={"ALL"} />
    </div>)
}

const TasksInfoFilterInfo =() => {
    return(<div>
        <p className={"tasks-info-text"}>TODAY [Current Date] </p>
    </div>);
}

const TasksInfoPanel =() => {
    return(<div className={"tasks-info-panel tasks-menu-panel col-4"}>
        <TasksInfoFilterInfo />
    </div>);
}

const TasksEditBtn =(props) => {
    return(<div className={"tasks-edit-btn "}>
        <div className={"tasks-edit-btn-in " +props.editBtnClass} />
    </div>);
}

const TasksEditPanel =() => {
    return(<div className={"tasks-edit-panel tasks-menu-panel col-4"}>
        <TasksEditBtn editBtnClass={"tasks-edit-add-btn"} />
        <TasksEditBtn editBtnClass={"tasks-edit-done-btn"} />
        <TasksEditBtn editBtnClass={"tasks-edit-del-btn"} />
    </div>)
}

const TasksTopMenu =() => {
    return(<div className={"tasks-top-menu row"}>
        <TasksEditPanel />
        <TasksInfoPanel />
        <TasksFilterPanel />
    </div>);
}

/**
 * TasksBlock is root component that's render user's tasks at user page.
 */
class TasksBlock extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (<div className={"tasks-block m-auto"}>
            <TasksTopMenu />
            <TasksContentBlock />
            <TasksFooter />
        </div>);
    }
}

export {TasksBlock}