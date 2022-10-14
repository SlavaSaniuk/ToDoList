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
 * @property - funcOnSelectTask
 */
class TaskBlock extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            "id": this.props.taskProps.taskId,
            "name": this.props.taskProps.taskName
        }

        // Bind functions:
        this.onSelectTask.bind(this);
    }

    onSelectTask =() => {
        console.log("Select task!");
    }

    render() {
        let taskId = "task_"+this.props.taskProps.taskId;
        return(
            <div id={taskId} className={"taskBlock row"} >
                <TaskSelector funcOnSelectTask={this.onSelectTask} />
                <TaskPanel taskProps={this.props.taskProps} />
            </div>
        )
    }

}

const Task =(props) => {
    return ( <TaskBlock taskProps={props} /> );
}

export default Task;