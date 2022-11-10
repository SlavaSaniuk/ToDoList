import React, {useEffect, useRef} from "react";
import '../../../styles/common.css'
import '../../../styles/fragments/task/task-view.css'
import DatePicker from "react-datepicker";
import {Localization} from "../../../js/localization/localization";
import {Menu, MenuDirection} from "../../ui/Menu";
import {CrossButton, DoneButton, EditButton, TextButton} from "../../Buttons";

/**
 * TaskView component user to display, edit single user task.
 * @props - component props.
 * @property - task - Task model object.
 * @property - loadingStatus - task loading status ({TaskViewLoadingStatus}).
 * @stateProperty - task - task state model object.
 * @stateProperty - loadingStatus - loading status of this view ({TaskViewLoadingStatus}).
 * @stateProperty - inEdit - flag indicate if task is editing now.
 */
export class TaskView extends React.Component {

    // Constants:
    /** TASK_EDIT_CONTROL_PANEL render {TaskEditorControlPanel} component and task EDITOR control buttons.
     *  If this task is edit now (state.inEdit=true), then render this panel.
     */
    TASK_EDIT_CONTROL_PANEL;

    /**
     * TASK_CONTROL_PANEL render {<TaskControlMenu>} component and task control buttons (DONE, REMOVE, EDIT).
     * If this task is edit now (state.inEdit=true), then do not render this panel.
     */
    TASK_CONTROL_PANEL;

    /**
     * TASK_SELECTOR render {<TaskSelection>} selector component.
     */
    TASK_SELECTOR;

    constructor(props) {
        super(props);

        // Component state:
        this.state = {
            task: Object.assign(this.props.task), // Task property copy;
            loadingStatus: this.props.loadingStatus, // By default task is loading;
            inEdit: false
        }

        // Bind functions:
        this.onEdit.bind(this);
        this.onChange.bind(this);
        this.onCancel.bind(this);

        // Initialize class variables:
        this.TASK_EDIT_CONTROL_PANEL = (<TaskEditorControlPanel>
            <TextButton btnText={Localization.getLocalizedString("tv_edit_control_btn_update")}
                        classes={"task-editor-control-button"} />
            <TextButton btnText={Localization.getLocalizedString("tv_edit_control_btn_cancel")}
                        classes={"task-editor-control-button"} clickFunc={this.onCancel} />
        </TaskEditorControlPanel>);
        this.TASK_CONTROL_PANEL = (<TaskControlMenu >
            <DoneButton classes={"control-btn"} />
            <EditButton classes={"control-btn"} clickFunc={this.onEdit}/>
            <CrossButton classes={"control-btn"}/>
        </TaskControlMenu>);
        this.TASK_SELECTOR = <TaskSelection />;

    }

    /**
     * Update component state when props is updated.
     * @param props - new props.
     * @param state - state object.
     * @returns {*} - new props.
     */
    static getDerivedStateFromProps(props, state) {
        state.loadingStatus = props.loadingStatus;
        return state;
    }

    /**
     * Edit this task.
     * Function calling when user click on edit control button.
     * Function set "inEdit" property to true and update this component.
     */
    onEdit =() => {
        this.setState({
            inEdit: true
        })
    }

    /**
     * Change task property.
     * Function calling when user edit task and change task property.
     * Based on aPropertyField argument function set new task (with changed property value) into component state.
     * @param event - onChange action event.
     * @param aPropertyField - task property field constant [{PropertyField}].
     */
    onChange =(event, aPropertyField) => {

        switch (aPropertyField) {
            case PropertyField.NAME: {
                this.setState({
                    task: {...this.state.task, taskName: event.target.value}
                });
                return;
            }
            case PropertyField.DESC: {
                this.setState({
                    task: {...this.state.task, taskDescription: event.target.value}
                });
                return;
            }
            case PropertyField.CREATION: {
                this.setState({
                    task: {...this.state.task, taskCreationDate: event.target.value}
                });
                return;
            }
            case PropertyField.COMPLETION: {
                this.setState({
                    task: {...this.state.task, taskCompletionDate: event}
                });
                return;
            }
            default: return;
        }

    }

    /**
     * Cancel task edit.
     * Function calling when user click on cancel text button on task editor control panel.
     * Function reset all task changes (change task in state with task from props) and reset inEdit state flag.
     */
    onCancel =() => {
        this.setState({
            task: this.props.task,
            inEdit: false
        })
    }

    /**
     * Render component.
     * @returns {JSX.Element}
     */
    render() {


        // If task is loading, return loader:
        if (this.state.loadingStatus === TaskViewLoadingStatus.LOADING)
            return (<div className={"purple-loader task-view-loader"}></div>)

        // Check if task is in editing now:
        const task_edit_control_panel = this.state.inEdit ? this.TASK_EDIT_CONTROL_PANEL : null;
        const task_control_panel = this.state.inEdit ? null : this.TASK_CONTROL_PANEL;
        const task_selector = this.state.inEdit ? null : this.TASK_SELECTOR;


        const VIEW_CONTENT = (
            <div className={"task-properties-panel"}>
                <TaskPropertyArea field={PropertyField.NAME} value={this.state.task.taskName}
                                  disabled={!this.state.inEdit} onChange={this.onChange} />
                <TaskPropertyArea field={PropertyField.DESC} value={this.state.task.taskDescription}
                                  disabled={!this.state.inEdit} onChange={this.onChange} />
                <div className={"task-date-properties-panel"}>
                    <div className={"date-property-section"}>
                        <p> {Localization.getLocalizedString("tv_task_date_creation")} </p>
                        <TaskPropertyField type={TaskPropertyFieldType.INPUT} value={this.state.task.taskCreationDate} disabled={true} />
                    </div>
                    <div className={"date-property-section"}>
                        <p> {Localization.getLocalizedString("tv_task_date_completion")} </p>
                        <TaskPropertyInput field={PropertyField.COMPLETION} value={this.state.task.taskCompletionDate}
                                           disabled={!this.state.inEdit} onChange={this.onChange} />
                    </div>
                </div>
            </div>)

        return(
            <div className={"task-view"}>
                {task_selector}
                <div className={"task-view-content"}>
                    {VIEW_CONTENT}
                    {task_edit_control_panel}
                </div>
                {task_control_panel}
            </div>
        )
    }
}

/**
 * Task view loading status.
 * @type {{LOADING: number, LOADED: number}}
 */
export const TaskViewLoadingStatus = {LOADING: 1, LOADED: 2};

/**
 * Task selection component used to select/unselect user task.
 * @stateProperty - checked - flag indicate if this task view is selected.
 */
class TaskSelection extends React.Component {
    /**
     * Construct new TaskSelection component.
     * @param props
     */
    constructor(props) {
        super(props);

        // Component state:
        this.state = {
            checked: false // Checkbox state;
        }

        // Bind functions:
        this.onClick = this.onClick.bind(this);
    }

    /**
     * Action performed when user select or unselect task.
     */
    onClick =() => {
        this.setState(prevSate => {
            return {checked: !prevSate.checked}
        })
    }

    /**
     * Render component.
     * @returns {JSX.Element}
     */
    render() {
        return (
            <div className={"task-selector"}>
                <input type={"checkbox"} onChange={this.onClick} checked={this.state.checked} />
            </div>
        )
    }
}

/**
 * TaskPropertyField component represent a user task field (e.g. name, description).
 * @param props - component props.
 * @property type - type of inner input ({TaskPropertyFieldType}).
 * @property value - inner input value.
 * @property disabled - flag indicate if inner input is enabled.
 * @property classesStr - additional classes string.
 * @property onChange - onChange action function.
 */
const TaskPropertyField = React.memo((props) => {

    const textAreaRef = useRef(null);

    /**
     * Similar to componentDidMount, componentDidUpdate.
     */
    useEffect(() => {
        if (props.type === TaskPropertyFieldType.AREA) {
            textAreaRef.current.style.height = "1px";
            textAreaRef.current.style.height = textAreaRef.current.scrollHeight + "px";
        }
    })

    // Bases on props.type return textarea or DatePicker:
    if (props.type === TaskPropertyFieldType.AREA)
        return (<textarea ref={textAreaRef} value={props.value} disabled={props.disabled}
                         className={"task-property-area " +props.classesStr} onChange={props.onChange} />);
    else
        return <DatePicker selected={props.value} className={"task-property-date-input"} onChange={(event) => {
            props.onChange(event, "COMPLETION")}}
                           disabled={props.disabled} dateFormat={"dd.MM.yyyy"} />
});

const TaskPropertyFieldType = {AREA: 1, INPUT: 2};

const PropertyField = {NAME: 0, DESC: 1, CREATION: 2, COMPLETION: 3};

/**
 * Render task property text area (for task name/description).
 * @param props - component props.
 * @property field - task property ({PropertyField}).
 * @property value - text area value.
 * @property disabled - flag indicate if textarea is enabled/disabled.
 * @property onChange - onChange action function.
 * @returns {JSX.Element} - TaskPropertyField text area.
 */
class TaskPropertyArea extends React.Component {
    constructor(props) {
        super(props);
        // bind functions:
        this.onChange.bind(this);
    }

    onChange =(event) => {
        this.props.onChange(event, this.props.field);
    }

    shouldComponentUpdate =(nextProps) => {
        return (nextProps.value !== this.props.value) || (nextProps.disabled !== this.props.disabled);
    }

    render() {

        // Class bases on props:
        let addClass;
        if (this.props.field === PropertyField.NAME) addClass = "task-name-property-field";
        else if (this.props.field === PropertyField.DESC) addClass = "task-desc-property-field";
        else addClass ="";


        // Return text area:
        return <TaskPropertyField type={TaskPropertyFieldType.AREA} classesStr={addClass} value={this.props.value}
                                  disabled={this.props.disabled} onChange={this.onChange} />
    }
}

class TaskPropertyInput extends React.Component {
    constructor(props) {
        super(props);

        // Bind methods:
        this.onChange.bind(this);
    }

    onChange =(event) => {
        this.props.onChange(event, this.props.field);
    }

    render() {

        return <TaskPropertyField type={TaskPropertyFieldType.INPUT} value={this.props.value}
                                  disabled={this.props.disabled} onChange={this.onChange} />

    }
}


const TaskControlMenu =(props) => {
    return <Menu menuDirection={MenuDirection.HORIZONTAL} menuClass={"task-control-menu"}> {props.children} </Menu>
}

const TaskEditorControlPanel =(props) => {
    return <Menu menuDirection={MenuDirection.HORIZONTAL} menuClass={"task-editor-control-panel"}> {props.children} </Menu>
}
