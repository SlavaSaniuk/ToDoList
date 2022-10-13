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

class TaskSelector extends React.Component {
    render() {
        return (
            <div className={"col-1 task-selector-block"}>
                <input className={"task-selector-checkbox"} type={"checkbox"}/>
                <span className={"task-selector-checkbox-mark"} > AAA </span>
            </div>

        );
    }
}

class TaskBlock extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            "id": this.props.taskProps.taskId,
            "name": this.props.taskProps.taskName
        }
    }

    render() {
        let taskId = "task_"+this.props.taskProps.taskId;
        return(
            <div id={taskId} className={"taskBlock row"} >
                <TaskSelector />
                <TaskPanel taskProps={this.props.taskProps} />
            </div>
        )
    }

}

const Task =(props) => {
    return ( <TaskBlock taskProps={props} /> );
}

export default Task;