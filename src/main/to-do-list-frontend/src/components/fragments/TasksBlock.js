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


    render() {

        const tasks = this.props.tasksList.map((task) =>
            <Task taskName={task.taskName} taskId={task.taskId} key={task.taskId}/>
        );
        return (
            <div>
                {tasks}
            </div>
        )
    }
}

/**
 * Task content block is root element that has and render the TasksList and AddTaskBlock elements.
 * @property showAddTaskBlock - boolean flag.
 * @property showAddTaskBlockFunc - function to show/hide AddTaskBlock.
 * @property tasksList - list of users tasks.
 */
class TasksContentBlock extends React.Component {
    constructor(props) {
        super(props);
    }

    /**
     * Render TaskContentBlock element.
     * @returns {JSX.Element} - html.
     */
    render() {
        return(
            <div>
                <AddTaskBlock isShow={this.props.showAddTaskBlock} showAddTaskBlockFunc={this.props.showAddTaskBlockFunc}
                              funcOnAddNewTask={this.props.funcOnAddNewTask} />
                <TasksList tasksList={this.props.tasksList} />
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

/**
 * Edit button statuses:
 * ACTIVE - 0;
 * DISABLED - 1;
 */
const TasksEditBtnStatus = {ACTIVE: 0, DISABLED: 1};

/**
 * Edit button types:
 * ADD - ADD;
 * REMOVE - REMOVE;
 * EDIT - EDIT;
 */
const TasksEditBtnTypes = {ADD: "ADD", DONE: 4, EDIT: "EDIT",REMOVE: "REMOVE"};

/**
 * Class represent a task edit button element.
 * @property btnType - TasksEditBtnTypes type btp value.
 * @property btnStatus - Status of edit button (see TasksEditBtnStatuses).
 * @property clickFunc - Function on click.
 */
class TasksEditBtn extends React.Component {

    /**
     * Construct new TasksEditBtn element.
     * @param props - properties.
     */
    constructor(props) {
        super(props);
    }

    /**
     * Render EditTaskBtn element.
     * @returns {JSX.Element} - html.
     */
    render() {

        let btnClass = "tasks-edit-btn-";

        // Check type:
        switch (this.props.btnType) {
            case TasksEditBtnTypes.ADD:
                btnClass+="add";
                break;
            case TasksEditBtnTypes.EDIT:
                btnClass+="edit";
                break;
            case TasksEditBtnTypes.REMOVE:
                btnClass+="remove";
                break;
            case TasksEditBtnTypes.DONE:
                btnClass+="done";
                break;
            default:
                break;
        }

        // Check status:
        if (this.props.btnStatus === TasksEditBtnStatus.DISABLED) btnClass += "-disabled";


        return(
            <input type={"button"} disabled={this.props.btnStatus}
                   className={"tasks-edit-btn " +btnClass} onClick={this.props.clickFunc}/>
        );
    }
}

const TasksEditPanel =(props) => {
    return(<div className={"tasks-edit-panel tasks-menu-panel col-4"}>
        <TasksEditBtn btnType={TasksEditBtnTypes.ADD} btnStatus={TasksEditBtnStatus.ACTIVE}
                      clickFunc={props.showAddTaskBlockFunc} />
        <TasksEditBtn btnType={TasksEditBtnTypes.DONE} btnStatus={TasksEditBtnStatus.DISABLED} />
        <TasksEditBtn btnType={TasksEditBtnTypes.REMOVE} btnStatus={TasksEditBtnStatus.DISABLED} />
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
 * @function showAddTaskBlock(boolean);
 * @function onAddNewTask;
 */
class TasksBlock extends React.Component {
    constructor(props) {
        super(props);

        // Bind functions:
        this.showAddTaskBlock.bind(this);
        this.onAddNewTask.bind(this);

        this.state = {
            isShowAddTaskBlock: false, // Flag to show AddTaskBlock element;
            tasksList: [] // Array of users tasks;
        };
    }

    /**
     * Function rerender TasksContentBlock to show AddTaskBlock element.
     * @param isShow - Boolean - true to show tasks block.
     */
    showAddTaskBlock =(isShow) => {
        this.setState({isShowAddTaskBlock: isShow})
    }

    /**
     * Add new users task to tasks list in element state.
     * @param aTask - task to add.
     */
    onAddNewTask =(aTask) => {

        // Add task to list in state and hide AddTaskBlock:
        this.setState(prevState => ({
            tasksList:  [aTask, ...prevState.tasksList],
            isShowAddTaskBlock: false
        }));
    }

    /**
     * Render TasksBlock element.
     * @returns {JSX.Element} - TasksBlock.
     */
    render() {
        return (<div className={"tasks-block m-auto"} >
            <TasksTopMenu showAddTaskBlockFunc={this.showAddTaskBlock} />
            <TasksContentBlock showAddTaskBlock={this.state.isShowAddTaskBlock}
                               showAddTaskBlockFunc={this.showAddTaskBlock}
                               tasksList={this.state.tasksList} // List of users tasks;
                               funcOnAddNewTask={this.onAddNewTask}
            />
            <TasksFooter />
        </div>);
    }
}

export {TasksBlock}