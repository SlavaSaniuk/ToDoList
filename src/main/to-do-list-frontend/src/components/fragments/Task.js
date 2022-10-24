import React from "react";
import '../../styles/fragments/Task.css'
import "../Buttons.js";
import {CancelButton, DoneButton, EditButton, TextButton} from "../Buttons";
import {TaskBuilder} from "../dto/TaskDto";


/**
 * @property taskObj - Task object.
 * @property isInEdit - flag indicate if user edit task.
 */
class TaskView extends React.Component {
    /**
     * Construct new TaskView element.
     * @param props - properties.
     */
    constructor(props) {
        super(props);

        // Initialize state:
        this.state = {
            taskObj: Object.assign({}, this.props.taskObj), // Copy of taskObj property;
        }

        // Bind functions:
        this.isEnabled.bind(this);
    }

    /**
     * Function check if current task view is in edit and return inverted value of "isInEdit" property.
     * @returns {boolean} - true if is inEditFlag is false.
     */
    isEnabled() {
        return !this.props.isInEdit;
    }

    render() {

        // If isInEdit flag = true, render editing control buttons:
        let editControlButtons;
        if (this.props.isInEdit) editControlButtons = (<div>
            <TextButton btnText={"Apply"} classes={"task-edit-text-btn text-btn-apply-changes"} />
            <TextButton btnText={"Cancel"} classes={"task-edit-text-btn text-btn-cancel-changes"} />
        </div>);



        return (
            <div className={"task-view"}>
                <input type={"text"} className={"task-view-name-input"} defaultValue={this.state.taskObj.taskName} disabled={this.isEnabled()} />
                <textarea className={"task-view-desc-area"} defaultValue={this.state.taskObj.taskDesc} disabled={this.isEnabled()}/>
                {editControlButtons}
            </div>

        )

    }
}

/**
 *
 * @type {{DONE: number, REMOVE: number, EDIT: number}}
 */
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
 */
const TaskMenu =(props) => {
    return (
        <div className={"task-menu"}>
            <TaskControlButton btnType={ControlButtonType.DONE}  />
            <TaskControlButton btnType={ControlButtonType.EDIT} clickFunc={props.taskControlFuncs.editFunc} />
            <TaskControlButton btnType={ControlButtonType.REMOVE} clickFunc={props.taskControlFuncs.removeFunc} />
        </div>
    )
}

/**
 * @property taskControlFuncs
 */
class TaskPanel extends React.Component {
    /**
     * Construct new TaskPanel object.
     * @param props - react properties.
     */
    constructor(props) {
        super(props);

        // Initialize state of element:
        this.state = {
            isInEdit: false // Flag indicate if user is edit this task;
        }

        // Bind functions:
        this.onEditTask.bind(this);
    }


    /**
     * Render TaskPanel React element.
     * @returns {JSX.Element}
     */
    render() {

        // Test taskObj property:
        const taskObj = TaskBuilder.ofId(56).withName("Programming hard!").withDescription("Programming JavaScript.").build();

        // Check if need to render <TaskMenu> element:
        const taskMenu = this.state.isInEdit ? null : <TaskMenu taskControlFuncs={this.props.taskControlFuncs} />;


        return (<div className={"col-11"}>
            {taskMenu}
            <TaskView taskObj={taskObj} isInEdit={this.state.isInEdit} />
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
            isInEdit: false, // Flag indicate if task is edit now;
            "name": this.props.taskProps.taskName
        }

        // Bind functions:
        this.getTask.bind(this);
        this.onSelectTask.bind(this);
        this.onUnselectTask.bind(this);
        this.onRemoveTask.bind(this);
        this.onEditTask.bind(this);
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
        // Call parent function with TaskDto object argument:
        this.props.taskProps.taskControlFuncs.removeFunc(this.getTask());
    }

    /**
     * Function calling when user click on EDIT task control button.
     * Function set state "isInEdit" flag to true and rerender task.
     */
    onEditTask =() => {
        console.log("Edit task!");
        this.setState({
            isInEdit: true
        })
    }

    render() {
        let taskId = "task_"+this.props.taskProps.taskId;

        // Object of task control buttons click funcs:
        const taskControlFuncs={removeFunc: this.onRemoveTask, editFunc: this.onEditTask};


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