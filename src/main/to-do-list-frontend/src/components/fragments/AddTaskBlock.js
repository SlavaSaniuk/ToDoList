import React from "react";
import '../../styles/fragments/AddTaskBlock.css'
import DatePicker from 'react-datepicker'
import 'react-datepicker/dist/react-datepicker.css'
import {Localization} from "../../js/localization/localization";
import {TextButton} from "../Buttons";

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

/**
 * @property at_appearanceFunc - Task addition appearance function.
 */
export class TaskAddition extends React.Component {
    constructor(props) {
        super(props);

        // Refs:
        this.atBlock = React.createRef(); // Ref on AddingTask div (task-addition);

        // Bind functions:
        this.onChangeNameAreaValue = this.onChangeNameAreaValue.bind(this);
        this.onChangeDescAreaValue = this.onChangeDescAreaValue.bind(this);
        this.onChangeDateCompletionValue = this.onChangeDateCompletionValue.bind(this);
        this.onClear.bind(this);
        this.onHide.bind(this);

        // Component state:
        this.state = {
            isShow: this.props.isShow,
            nameAreaValue: "",
            descAreaValue: "",
            completionInputValue: ""
        }
    }



    static getDerivedStateFromProps(props, state) {
        state.isShow = props.isShow;
        return state;
    }

    /**
     * Function calling when task name area value changed (onChange action).
     * Function set state nameAreaValue property to current area text value.
     * @param event - onChange event.
     */
    onChangeNameAreaValue =(event) => {
        event.preventDefault();
        event.target.style.height = event.target.scrollHeight +"px";
        this.setState({
            nameAreaValue: event.target.value
        })
    }

    onChangeDescAreaValue =(event) => {
        event.preventDefault();
        event.target.style.height = event.target.scrollHeight +"px";
        this.setState({
            descAreaValue: event.target.value
        })
    }

    onChangeDateCompletionValue =(aDate) => {
        this.setState({
            completionInputValue: aDate
        })
    }

    /**
     * Clear user inputs.
     */
    onClear =() => {
        this.setState({
            nameAreaValue: "",
            descAreaValue: "",
            completionInputValue: ""
        })
    }

    /**
     * Hide TaskAddition block.
     */
    onHide =() => {
        // Clear user inputs:
        this.onClear();

        // Call parent hide functions:
        this.props.at_appearanceFunc(false);
    }

    render() {

        // Check if needed to display this block:
        let showClass = this.state.isShow ? "task-addition-showed" : "at-hidden";

        // Construct AT Name/desc panel:

        // Construct AT date selection panel:

        // Construct AT control buttons BLOCK:
        const CONTROL_BUTTONS_BLOCK = (
            <div className={"at_control-buttons-block"}>
                <TextButton btnText={Localization.getLocalizedString("at_control_btn_add")} classes={"at_control-btn"} />
                <TextButton btnText={Localization.getLocalizedString("at_control_btn_clear")} classes={"at_control-btn"}
                            clickFunc={this.onClear} />
                <TextButton btnText={Localization.getLocalizedString("at_control_btn_cancel")} classes={"at_control-btn"}
                            clickFunc={this.onHide} />
            </div>
        );

        return (
            <div className={"row task-addition " +showClass}>
                <div className={"name-desc-blk"}>
                    <TaskAdditionTextArea value={this.state.nameAreaValue} onChange={this.onChangeNameAreaValue}
                                          placeholder={Localization.getLocalizedString("task_name_input_placeholder")}
                                          classesStr={"text-area-task text-area-task-name"} />
                    <TaskAdditionTextArea value={this.state.descAreaValue} onChange={this.onChangeDescAreaValue}
                                          placeholder={Localization.getLocalizedString("task_desc_input_placeholder")}
                                          classesStr={"text-area-task text-area-task-desc"} />
                </div>
                <div className={"date-blk"}>
                    <DatePicker placeholderText={"Дата завершения"} selected={this.state.completionInputValue}
                                onChange={this.onChangeDateCompletionValue}
                                dateFormat="dd.MM.yyyy"
                    />
                </div>

                {CONTROL_BUTTONS_BLOCK}
            </div>
        );
    }
}



const TaskAdditionTextArea =(props) => {

    return (
        <textarea
            value={props.value}
            onChange={props.onChange}
            placeholder={props.placeholder}
            className={props.classesStr}
        />
    );
}

export {AddTaskBlock};
