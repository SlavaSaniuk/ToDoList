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
                this.props.showAddTaskBlockFunc(true); // Show AddTaskBlock element in TasksContentBlock;
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