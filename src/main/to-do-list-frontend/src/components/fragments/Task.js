import React from "react";
import '../../styles/fragments/Task.css'
import "../Buttons.js";
import {CancelButton, DoneButton, EditButton, TextButton} from "../Buttons";
import {TaskBuilder} from "../dto/TaskDto";


/**
 * @property taskObj - Task object.
 * @property isInEdit - Flag indicate if user edit task.
 * @property taskEditClickFunc - Object of task edit click functions.
 * @function onCancelEdit - Function calling when user click on "Cancel" task edit button.
 * @function onApplyEdit - Function calling when user click on "Apply" task edit button.
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
        this.onCancelEdit.bind(this);
        this.onApplyEdit.bind(this);
        this.isRenderTaskDescTextArea.bind(this);

        // Create refs:
        this.nameInputRef = React.createRef();
        this.descInputRef = React.createRef();
    }

    /**
     * Function check if current task view is in edit and return inverted value of "isInEdit" property.
     * @returns {boolean} - true if is inEditFlag is false.
     */
    isEnabled() {
        return !this.props.isInEdit;
    }

    /**
     * Function calling when user click on "Cancel" task edit button.
     * Function reset input values to their default values and call parent onCancelFunction.
     */
    onCancelEdit =() => {
        // Reset inputs:
        this.nameInputRef.current.value = this.state.taskObj.taskName;
        this.descInputRef.current.value = this.state.taskObj.taskDesc;

        // Call parent function:
        this.props.taskEditClickFunc.onCancelFunction();
    }

    /**
     * Function calling when user click on "Apply" task edit button.
     * Function get modified task fields values from inputs and call parent onApplyFunction
     * (@see TaskBlock#onApplyTaskEdit).
     */
    onApplyEdit =() => {

        // Get modified fields values:
        const aTaskChanges = {
            taskName: this.nameInputRef.current.value,
            taskDesc: this.descInputRef.current.value
        };

        // Construct new state task object:
        const taskObj = TaskBuilder.ofId(this.state.taskObj.taskId).withName(aTaskChanges.taskName).withDescription(aTaskChanges.taskDesc).build();

        // Set new element state:
        this.setState({
            taskObj: taskObj
        });

        // Call parent function with new task fields values:
        this.props.taskEditClickFunc.onApplyFunction(aTaskChanges);
    }

    isRenderTaskDescTextArea =() => {
        // If task in edit, return true:
        if (this.props.isInEdit) return true;

        // If task desc not null, not undefined, not empty - return true:
        return !(this.state.taskObj.taskDesc === null || this.state.taskObj.taskDesc === undefined || this.state.taskObj.taskDesc === "");
    }

    /**
     * Render TaskView React element.
     * @returns {JSX.Element}
     */
    render() {

        // If isInEdit flag = true, render editing control buttons:
        let editControlButtons;
        if (this.props.isInEdit) editControlButtons = (<div>
            <TextButton btnText={"Apply"} classes={"task-edit-text-btn text-btn-apply-changes"} clickFunc={this.onApplyEdit} />
            <TextButton btnText={"Cancel"} classes={"task-edit-text-btn text-btn-cancel-changes"} clickFunc={this.onCancelEdit} />
        </div>);

        // If task description is null or empty and task is not in edit do not render TextArea block:
        let taskDescTextArea;
        if(this.isRenderTaskDescTextArea())
            taskDescTextArea = (
                <textarea className={"task-view-desc-area"} defaultValue={this.state.taskObj.taskDesc}
                          disabled={this.isEnabled()} ref={this.descInputRef} placeholder={"Task description"} />
            );

        return (
            <div className={"task-view"}>
                <input type={"text"} className={"task-view-name-input"} defaultValue={this.state.taskObj.taskName}
                       disabled={this.isEnabled()} ref={this.nameInputRef} />
                {taskDescTextArea}
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
 * @property isInEdit - task edit flag.
 * @property taskEditClickFunc - task edition click function.
 */
class TaskPanel extends React.Component {

    /**
     * Render TaskPanel React element.
     * @returns {JSX.Element}
     */
    render() {

        // Test taskObj property:
        const taskObj = TaskBuilder.ofId(this.props.taskProps.taskId).withName(this.props.taskProps.taskName).withDescription(this.props.taskProps.taskDesc).build();

        // Check if need to render <TaskMenu> element:
        const taskMenu = this.props.isInEdit ? null : <TaskMenu taskControlFuncs={this.props.taskControlFuncs} />;

        // div wrapper class name:
        const wrapperDivClass = this.props.isInEdit ? "col-12" : "col-11";

        return (<div className={wrapperDivClass}>
            {taskMenu}
            <TaskView taskObj={taskObj} isInEdit={this.props.isInEdit} taskEditClickFunc={this.props.taskEditClickFunc} />
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
 * Root element encapsulate all logic and inner elements of task element.
 * @property - taskProps - Task properties.
 * @property - funcOnSelectTask - parent function on select task action.
 * @property - funcOnUnselectTask - parent function on unselect task action.
 * @property - taskControlFuncs - object has parent task control functions.
 * @state - isInEdit - // Flag indicate if task is edit now;
 * @function - getTask - get task object.
 * @function - onSelectTask - function calling when user select task.
 * @function - onUnselectTask - function calling when user unselect task.
 * @function - onRemoveTask - function calling when user click on remove task button.
 * @function - onEditTask - function calling when user click on edit task control button.
 * @function - onCancelTaskEdit - Function calling when user click on "Cancel" task edit button.
 * @function - onApplyTaskEdit - Function calling when user click on "Apply" task edit button.
 */
class TaskBlock extends React.Component {

    // Class fields:
    taskEditClickFunc; // Object of task edition click functions;
    taskControlFuncs; // Object of task control buttons functions;

    /**
     * Construct new TaskBlock react element.
     * @param props - properties.
     */
    constructor(props) {
        super(props);

        // Element state:
        this.state = {
            isInEdit: false, // Flag indicate if task is edit now;
        }

        // Bind functions:
        this.getTask.bind(this);
        this.onSelectTask.bind(this);
        this.onUnselectTask.bind(this);
        this.onRemoveTask.bind(this);
        this.onEditTask.bind(this);
        this.onCancelTaskEdit.bind(this);
        this.onApplyTaskEdit.bind(this);

        // Initialize fields:
        this.taskEditClickFuncs = {
            onApplyFunction: this.onApplyTaskEdit,
            onCancelFunction: this.onCancelTaskEdit
        }
        this.taskControlFuncs = {
            removeFunc: this.onRemoveTask,
            editFunc: this.onEditTask
        };
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
        // noinspection JSCheckFunctionSignatures
        this.props.taskProps.taskControlFuncs.removeFunc(this.getTask());
    }

    /**
     * Function calling when user click on EDIT task control button.
     * Function set state "isInEdit" flag to true and rerender task.
     */
    onEditTask =() => {
        this.setState({
            isInEdit: true
        })
    }

    /**
     * Function calling when user click on "cancel" edit task button.
     * Function set state "isInEdit" flag to false and rerender task.
     */
    onCancelTaskEdit =() => {
        this.setState({
            isInEdit: false
        })
    }

    /**
     * Function calling when user click on "Apply" task edit button.
     * Function construct new task object of same ID as old with new task fields values, reset isInEdit flag, and call
     * parent updateUserTask function (@see TasksBlock#updateUsetTask).
     * @param aTaskChanges - task fields changes ({taskName: MODIFIED_NAME, taskDesc: MODIFIED_DESCRIPTION}).
     */
    onApplyTaskEdit =(aTaskChanges) => {

        // Construct new task object:
        // noinspection JSUnresolvedFunction
        const task = TaskBuilder.ofId(this.props.taskProps.taskId)
            .withName(aTaskChanges.taskName)
            .withDescription(aTaskChanges.taskDesc)
            .build();

        // Reset isInEdit flag:
        this.setState({
            isInEdit: false
        })

        // Call parent function:
        this.props.taskProps.taskControlFuncs.updateFunc(task);
    }

    /**
     * Render TaskBlock react element.
     * @returns {JSX.Element} - (<div>
     *     ???<TaskSelector> // if isInEdit flag = false;
     *     <TaskPanel>
     * </div>).
     */
    render() {
        // Get task ID:
        let taskId = "task_"+this.props.taskProps.taskId;

        // Check if task is edit now:
        // If isInEdit flag - true, do not render TaskSelector block.
        let taskSelector;
        if (!this.state.isInEdit) taskSelector = <TaskSelector funcOnSelectTask={this.onSelectTask} funcOnUnselectTask={this.onUnselectTask} />;

        return(
            <div id={taskId} className={"taskBlock row"} >
                {taskSelector}
                <TaskPanel taskProps={this.props.taskProps} taskControlFuncs={this.taskControlFuncs} isInEdit={this.state.isInEdit}
                           taskEditClickFunc={this.taskEditClickFuncs} />
            </div>
        )
    }

}

const Task =(props) => {
    return ( <TaskBlock taskProps={props} /> );
}

export default Task;