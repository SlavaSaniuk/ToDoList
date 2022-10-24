import React from "react";
import '../../styles/fragments/Task.css'
import "../Buttons.js";
import {CancelButton, DoneButton, EditButton} from "../Buttons";
import {TaskBuilder} from "../dto/TaskDto";

const ControlButtonType = {DONE: 1, EDIT: 2, REMOVE: 3};

/**
 * Button do control action on task object.
 * @property btnType - Type of button (see ControlButtonType).
 * @property clickFunc - On click function.
 */
class TaskControlButton extends React.Component {
    render() {
        switch (this.props.btnType) {
            case ControlButtonType.REMOVE:
                return <CancelButton classes={"task-control-button"} clickFunc={this.props.clickFunc} />;
            case ControlButtonType.EDIT:
                return <EditButton classes={"task-control-button"} clickFunc={this.props.clickFunc} />
            case ControlButtonType.DONE:
                return <DoneButton classes={"task-control-button"} clickFunc={this.props.clickFunc} />
            default:
                return "";
        }
    }
}


/**
 * @property taskControlFuncs
 * @returns {JSX.Element}
 * @constructor
 */
const TaskMenu =(props) => {
    return (
        <div className={"task-menu"}>
            <TaskControlButton btnType={ControlButtonType.DONE}  />
            <TaskControlButton btnType={ControlButtonType.EDIT} />
            <TaskControlButton btnType={ControlButtonType.REMOVE} clickFunc={props.taskControlFuncs.removeFunc} />
        </div>
    )
}

/**
 * @property taskControlFuncs
 */
class TaskPanel extends React.Component {
    render() {
        return (<div className={"col-11"}>
            <TaskMenu taskControlFuncs={this.props.taskControlFuncs} />
            <h1> {this.props.taskProps.taskName}</h1>
        </div>);
    }
}

/**
 * @property funcOnUnselectTask - parent function on unselect task action.
 * @function onSelectorChange.
 */
class TaskSelector extends React.Component {
    /**
     * Construct new TaskSelector element.
     * @param props - properties.
     */
    constructor(props) {
        super(props);

        // Refs:
        this.selector = React.createRef();

        // Functions:
        this.onSelectorChange.bind(this);
    }

    /**
     * Function calling when user check or uncheck selector checkbox.
     * If user check checkbox, function call parent TaskBlock onSelectTask function.
     */
    onSelectorChange =() => {
        // Get checkbox state:
        // if checked call parent onSelectTask function:
        if(this.selector.current.checked) this.props.funcOnSelectTask();
        else this.props.funcOnUnselectTask();
    }

    render() {
        return (
            <div className={"col-1 task-selector-block"}>
                <input className={"task-selector-checkbox"} type={"checkbox"} onChange={this.onSelectorChange}
                ref={this.selector} />
                <span className={"task-selector-checkbox-mark"} />
            </div>

        );
    }
}

/**
 * Root element encapsulate all logic and markup of task element.
 * @property - funcOnSelectTask - parent function on select task action.
 * @property - funcOnUnselectTask - parent function on unselect task action.
 * @property - taskControlFuncs - object has parent task control functions.
 * @function - getTask - get task object.
 * @function - onSelectTask - function calling when user select task.
 * @function - onUnselectTask - function calling when user unselect task.
 * @function - onRemoveTask - function calling when user remove task.
 */
class TaskBlock extends React.Component {
    constructor(props) {
        super(props);

        // Element state:
        this.state = {
            "name": this.props.taskProps.taskName
        }

        // Bind functions:
        this.getTask.bind(this);
        this.onSelectTask.bind(this);
        this.onUnselectTask.bind(this);
        this.onRemoveTask.bind(this);
    }

    /**
     * Get inner task object.
     * @returns {{taskId: *}} - this task object.
     */
    getTask =() => {
        // Construct TaskDto object:
        return TaskBuilder.ofId(this.props.taskProps.taskId).build()
    }

    /**
     * Function calls when user select task.
     * Function call parent funcOnSelectTasks();
     */
    onSelectTask =() => {
        this.props.taskProps.funcOnSelectTasks(this.getTask());
    }

    /**
     * Function call parent funcOnUnselectTasks();
     */
    onUnselectTask =() => {
        this.props.taskProps.funcOnUnselectTask(this.getTask());
    }

    /**
     * Remove user task.
     * Function calling when user click on remove control button.
     * Function call parent function to remove task.
     */
    onRemoveTask =() => {
        // Construct TaskDto object:
        console.log("Remove task: ", this.getTask());
        this.props.taskProps.taskControlFuncs.removeFunc(this.getTask());
    }

    render() {
        let taskId = "task_"+this.props.taskProps.taskId;

        // Object of task control buttons click funcs:
        const taskControlFuncs={removeFunc: this.onRemoveTask};

        return(
            <div id={taskId} className={"taskBlock row"} >
                <TaskSelector funcOnSelectTask={this.onSelectTask} funcOnUnselectTask={this.onUnselectTask} />
                <TaskPanel taskProps={this.props.taskProps} taskControlFuncs={taskControlFuncs} />
            </div>
        )
    }

}

const Task =(props) => {
    return ( <TaskBlock taskProps={props} /> );
}

export default Task;