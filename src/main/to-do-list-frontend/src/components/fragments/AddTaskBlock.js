import React from "react";
import '../../styles/fragments/AddTaskBlock.css'
import DatePicker from 'react-datepicker'
import 'react-datepicker/dist/react-datepicker.css'
import {Localization} from "../../js/localization/localization";
import {TextButton} from "../Buttons";
import {TaskBuilder} from "../dto/TaskDto";

/**
 * TaskAddition used to add user tasks.
 * @props - component props.
 * @property at_appearanceFunc - Task addition appearance function (Show/Hide).
 * @property at_addTaskFunction - Add task parent function.
 */
export class TaskAddition extends React.Component {
    /**
     * Construct new TaskAddition component.
     * @param props - component props.
     */
    constructor(props) {
        super(props);

        // Bind functions:
        this.onChangeNameAreaValue = this.onChangeNameAreaValue.bind(this);
        this.onChangeDescAreaValue = this.onChangeDescAreaValue.bind(this);
        this.onChangeDateCompletionValue = this.onChangeDateCompletionValue.bind(this);
        this.onClear.bind(this);
        this.onHide.bind(this);
        this.onAdd.bind(this);

        // Component state:
        this.state = {
            isShow: this.props.isShow, // Flag indicate if component must be showed;
            nameAreaValue: "", // text area task name value;
            descAreaValue: "", // text area task description value;
            completionInputValue: "" // input task date of completion value (Date);
        }
    }

    /**
     * Update component state when component is rendered from parent.
     * @param props - new props.
     * @param state - component state
     * @returns {*} - return new component state.
     */
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
        event.target.style.height = event.target.scrollHeight +"px";
        this.setState({
            nameAreaValue: event.target.value
        })
    }

    /**
     * Update task description value.
     * Function calling when onChange action is performed on description text area.
     * If value string is too big function update text area height.
     * @param event - onChange event.
     */
    onChangeDescAreaValue =(event) => {
        event.target.style.height = event.target.scrollHeight +"px";
        this.setState({
            descAreaValue: event.target.value
        })
    }

    /**
     * Update task date of comletion input value.
     * @param aDate - new Date.
     */
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

    // noinspection JSUnresolvedFunction
    /**
     * Adding task.
     */
    onAdd =() => {

        // Create task dto:
        const taskDto = TaskBuilder.withName(this.state.nameAreaValue).withDescription(this.state.descAreaValue).withDateCompletion(this.state.completionInputValue).build();

        // Call parent adding task func:
        this.props.at_addTaskFunction(taskDto);
    }

    /**
     * Render component.
     * @returns {JSX.Element}
     */
    render() {

        // Check if needed to display this block:
        let showClass = this.state.isShow ? "task-addition-showed" : "at-hidden";

        // Construct AT control buttons BLOCK:
        const CONTROL_BUTTONS_BLOCK = (
            <div className={"at_control-buttons-block"}>
                <TextButton btnText={Localization.getLocalizedString("at_control_btn_add")} classes={"at_control-btn"}
                            clickFunc={this.onAdd} />
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

/**
 * AT name / description text area.
 * @param props - component props.
 * @returns {JSX.Element}
 */
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

