import React from "react";
import '../../styles/common.css'
import '../../styles/fragments/TasksBlock.css'
import Task from "../dto/Task";
import {AddTaskBlock} from "./AddTaskBlock";

const TasksFooter =() => {
    return(<div className={"tasks-footer"}>
        <p className={"tasks-footer-text"}> LOAD MORE ... </p>
    </div>);
}

class TasksList extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {

        const tasksListCommon = [{"taskName": "Hello world!"}, {"taskName": "My name is Slava!"}, {"taskName": "What is your name?"}];
        const tasksListObjs = tasksListCommon.map((task) =>
            <Task taskName={task.taskName} />
        );
        return (
            <div>
                {tasksListObjs}
            </div>
        )
    }
}

class TasksContentBlock extends React.Component {
    constructor(props) {
        super(props);
    }



    render() {
        let addTaskBlock = null;
        if(this.props.showAddTaskBlock) addTaskBlock = <AddTaskBlock />;
        return(
            <div>
                {addTaskBlock}
                <TasksList />
            </div>
        );
    }


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

const TasksEditBtnTypes = {ADD: "ADD", EDIT: "EDIT",REMOVE: "REMOVE"};

/**
 * Class represent a task edit button element.
 * @property btnType - TasksEditBtnTypes type btp value.
 * @function - handleClick - handle click on button. Function check whether which button is clicked and call function.
 */
class TasksEditBtn extends React.Component {

    constructor(props) {
        super(props);

        this.handleClick.bind(this);
    }

    /**
     * Handle click on this element. Function define which btn is clicked and then call proper function.
     */
    handleClick =() => {
        switch (this.props.btnType) {
            case TasksEditBtnTypes.ADD:
                this.props.showAddTaskBlockFunc(); // Show AddTaskBlock element in TasksContentBlock;
                break;
            case TasksEditBtnTypes.EDIT:
                console.log("Edit btn is pressed!");
                break;
            case TasksEditBtnTypes.REMOVE:
                console.log("Remove btn is pressed!");
                break;
        }
    }

    render() {

        let btnClass = "";
        switch (this.props.btnType) {
            case TasksEditBtnTypes.ADD:
                btnClass = "tasks-edit-add-btn";
                break;
            case TasksEditBtnTypes.EDIT:
                btnClass = "tasks-edit-done-btn";
                break;
            case TasksEditBtnTypes.REMOVE:
                btnClass = "tasks-edit-del-btn";
                break;
        }

        return(<div className={"tasks-edit-btn "}>
            <div className={"tasks-edit-btn-in " +btnClass} onClick={this.handleClick}/>
        </div>);
    }
}

const TasksEditPanel =(props) => {
    return(<div className={"tasks-edit-panel tasks-menu-panel col-4"}>
        <TasksEditBtn btnType={TasksEditBtnTypes.ADD} showAddTaskBlockFunc={props.showAddTaskBlockFunc} />
        <TasksEditBtn btnType={TasksEditBtnTypes.EDIT} />
        <TasksEditBtn btnType={TasksEditBtnTypes.REMOVE} />
    </div>)
}

const TasksTopMenu =(props) => {
    return(
        <div className={"tasks-top-menu row"} >
            <TasksEditPanel showAddTaskBlockFunc={props.showAddTaskBlockFunc} />
            <TasksInfoPanel />
            <TasksFilterPanel />
        </div>
    );
}

/**
 * TasksBlock is root component that's render user's tasks at user page.
 */
class TasksBlock extends React.Component {
    constructor(props) {
        super(props);

        this.showAddTaskBlock.bind(this);

        this.state = {
            isShowAddTaskBlock: false
        };
    }

    /**
     * Function rerender TasksContentBlock to show AddTaskBlock element.
     */
    showAddTaskBlock =() => {
        console.log("Show add task block.");
        this.setState({isShowAddTaskBlock: true})
    }


    render() {
        return (<div className={"tasks-block m-auto"} >
            <TasksTopMenu showAddTaskBlockFunc={this.showAddTaskBlock} />
            <TasksContentBlock showAddTaskBlock={this.state.isShowAddTaskBlock} />
            <TasksFooter />
        </div>);
    }
}

export {TasksBlock}