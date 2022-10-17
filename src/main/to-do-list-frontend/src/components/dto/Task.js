import React from "react";
import '../../styles/fragments/Task.css'

class TaskPanel extends React.Component {
    constructor(props) {
        super(props);
    }
    render() {
        return (<div className={"col-11"}>
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
 * @function - getTask - get task object.
 * @function - onSelectTask - function calling when user select task.
 * @function - onUnselectTask - function calling when user unselect task.
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
    }

    /**
     * Get inner task object.
     * @returns {{taskId: *}} - this task object.
     */
    getTask =() => {
        return {taskId: this.props.taskProps.taskId};
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




    render() {
        let taskId = "task_"+this.props.taskProps.taskId;
        return(
            <div id={taskId} className={"taskBlock row"} >
                <TaskSelector funcOnSelectTask={this.onSelectTask} funcOnUnselectTask={this.onUnselectTask} />
                <TaskPanel taskProps={this.props.taskProps} />
            </div>
        )
    }

}

const Task =(props) => {
    return ( <TaskBlock taskProps={props} /> );
}

export default Task;