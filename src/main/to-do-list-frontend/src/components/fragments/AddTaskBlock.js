import React from "react";
import '../../styles/fragments/AddTaskBlock.css'
import DatePicker from 'react-datepicker'
import 'react-datepicker/dist/react-datepicker.css'
import {Localization} from "../../js/localization/localization";

const AddTaskBlockControlBtnTypes = {ADD: 0, CANCEL: 1}

/**
 * @property btnText - button text value;
 * @property btnType - button text type (see AddTaskBlockControlBtnTypes).
 * @property btnAdClass - button additional class;
 */
class AddTaskBlockControlBtn extends React.Component {
    constructor(props) {
        super(props);

        this.getBtnClassName.bind(this);
    }

    getBtnClassName =() => {
        switch (this.props.btnType) {
            case AddTaskBlockControlBtnTypes.ADD:
                return "add-task-block-control-btn-add";
            case AddTaskBlockControlBtnTypes.CANCEL:
                return "add-task-block-control-btn-cancel";
            default:
                return "";
        }
    }

    render() {
        const btnClassName = this.getBtnClassName();
        return (
          <input type={"button"} value={this.props.btnText} className={"add-task-block-control-btn " +btnClassName} onClick={this.props.controlFunc} />
        );
    }
}

const AddTaskBlockControlPanel =(props) => {
    return (
        <div className={"add-task-block-control-panel"}>
            <AddTaskBlockControlBtn btnText={"Add"} btnType={AddTaskBlockControlBtnTypes.ADD}
                                    controlFunc={props.addFunc} btnAdClass={"add-task-block-control-btn-add"} />
            <AddTaskBlockControlBtn btnText={"Cancel"} btnType={AddTaskBlockControlBtnTypes.CANCEL}
                                    controlFunc={props.cancelFunc} btnAdClass={"add-task-block-control-btn-cancel"} />
        </div>
    );
}

/**
 * @property isShow - indicate need to be this element is shown.
 * @property showAddTaskBlockFunc - function to change visible of this element.
 * @function clearUsersInputs.
 * @function cancelAddingTask.
 */
class AddTaskBlock extends React.Component {
    constructor(props) {
        super(props);

        // Create refs:
        this.inputNameRef = React.createRef(); // Input name;
        this.areaDescRef = React.createRef(); // Textarea description;

        this.state = {isShow: this.props.isShow};

        // Binding functions:
        this.clearUsersInputs.bind(this);
        this.cancelAddingTask.bind(this);
    }

    /**
     * Clear users inputs.
     */
    clearUsersInputs =() => {
        // Clear inputs:
        this.inputNameRef.current.value = '';
        this.areaDescRef.current.value = '';
    }

    /**
     * Cancel adding task. Function clear users inputs and hide this element.
     */
    cancelAddingTask =() => {

        // Clear users inputs:
        this.clearUsersInputs();

        // Hide this block:
        this.props.showAddTaskBlockFunc(false);
    }

    /**
     * Add new task to task list. Function get users inputs values, clear inputs,
     * and call TasksBlock.onAddNewTask() function with this task parameter.
     */
    onAddNewTask =() => {
        const taskName = this.inputNameRef.current.value; // Get task name from user input;
        const taskDesc = this.areaDescRef.current.value;

        // Clear users inputs:
        this.clearUsersInputs();

        // Add new task:
        this.props.funcOnAddNewTask({
            taskId: "1",
            taskName: taskName, // Map task name;
            taskDesc: taskDesc
        });
    }

    render() {
        let addTaskBlockClassName = this.props.isShow ? "add-task-block" : "add-task-block-hide";
        return (
            <div className={addTaskBlockClassName}>
                <input type={"text"} className={"add-task-block-name-input"} placeholder={"Do financial report."} ref={this.inputNameRef} />
                <textarea className={"add-task-block-desc-area"} placeholder={"Description"} ref={this.areaDescRef} />
                <AddTaskBlockControlPanel cancelFunc={this.cancelAddingTask} addFunc={this.onAddNewTask} />
            </div>
        );
    }
}


export class TaskAddition extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            isShow: this.props.isShow
        }
    }


    /**
     * Set state fields from component props when component is in update.
     * @param nextProps - new props value.
     * @param nextContent - new content.
     */
    componentWillReceiveProps(nextProps, nextContent){
        if (nextProps.isShow !== this.props.isShow) {
            this.setState({ isShow: nextProps.isShow})
        }
    }


    render() {

        console.log(Localization.getLocalizedString("name"));
        // Check if needed to display this block:
        let showClass = this.state.isShow ? "task-addition-showed" : "task-addition-hided";

        return (
            <div className={"task-addition " +showClass}>
                <input type={"text"} placeholder={Localization.getLocalizedString("task_name_input_placeholder")} />
            </div>
        );
    }
}

export {AddTaskBlock};
