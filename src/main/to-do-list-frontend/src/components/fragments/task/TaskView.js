import React, {useEffect, useRef} from "react";
import '../../../styles/common.css'
import '../../../styles/fragments/task/task-view.css'
import DatePicker from "react-datepicker";
import {Localization} from "../../../js/localization/localization";
import {Menu, MenuDirection} from "../../ui/Menu";
import {CrossButton, DoneButton, EditButton, TextButton} from "../../Buttons";
import {Logger} from "../../../js/logging/Logger";
import {Properties} from "../../../Properites";
import {TaskStatus} from "../../../js/models/Task";

/**
 * TaskView component user to display, edit single user task.
 * @props - component props.
 * @property - viewId - unique view identification ({String});
 * @property - task - Task model object.
 * @property - loadingStatus - task loading status ({TaskViewLoadingStatus}).
 * @property - parentControlFunctions - parent task control function.
 * @stateProperty - task - task state model object.
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

    /**
     * Task view logger.
     * @type {Logger}
     */
    LOGGER = new Logger("TaskView", Properties.TaskViewLogging);

    constructor(props) {
        super(props);
        this.LOGGER.log("Construct new TaskView component with props[%o];", [props]);

        // Component state:
        this.state = {
            task: Object.assign(this.props.task), // Task property copy;
            inEdit: false
        }

        // Bind functions:
        this.taskViewProps.bind(this);
        this.onEdit.bind(this);
        this.onChange.bind(this);
        this.onCancel.bind(this);
        this.onUpdate.bind(this);
        this.onRemove.bind(this);

        // Initialize class variables:
        this.TASK_EDIT_CONTROL_PANEL = (<TaskEditorControlPanel>
            <TextButton btnText={Localization.getLocalizedString("tv_edit_control_btn_update")}
                        classes={"task-editor-control-button"} clickFunc={this.onUpdate} />
            <TextButton btnText={Localization.getLocalizedString("tv_edit_control_btn_cancel")}
                        classes={"task-editor-control-button"} clickFunc={this.onCancel} />
        </TaskEditorControlPanel>);
        this.TASK_CONTROL_PANEL = (<TaskControlMenu >
            <DoneButton classes={"control-btn"} />
            <EditButton classes={"control-btn"} clickFunc={this.onEdit} />
            <CrossButton classes={"control-btn"} clickFunc={this.onRemove} />
        </TaskControlMenu>);
        this.TASK_SELECTOR = <TaskSelection />;

    }

    /**
     * Construct {TaskViewProps} props from this TaskView.
     * @return {{viewId, loadStatus, taskObj: *}} - props.
     */
    taskViewProps =() => {
        return {
            viewId: this.props.viewId,
            taskObj: this.state.task,
            loadStatus: this.props.loadingStatus
        }
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
     *  Update current edited task.
     *  Function calling when click on update text button in task editor.
     *  function calling parent update function.
     */
    onUpdate =() => {
        this.LOGGER.log("Update TaskView[viewId: %o] with new task properties[%o];", [this.props.viewId, this.state.task]);

        // Reset state inEdit flag:
        this.setState({
            inEdit: false
        });

        // Create TaskViewProps:
        const taskViewProps = this.taskViewProps();

        // Call parent function:
         this.props.parentControlFunctions.updateFunction(taskViewProps);
    }

    /**
     * Remove user task and task view.
     * Function calling when user click on remove control button.
     * Function call parent remove function.
     */
    onRemove =() => {

        // Create task view props:
        const props = this.taskViewProps();
        this.LOGGER.log("Remove this TaskView[%o]:", [props]);

        // Call parent remove function:
        this.props.parentControlFunctions.removeFunction(props);
    }


    /**
     * Render component.
     * @returns {JSX.Element}
     */
    render() {

        // If task is loading, return loader:
        if (this.props.loadingStatus === TaskViewLoadingStatus.LOADING)
            return (<div className={"purple-loader task-view-loader"}></div>)


        // Check if task is in editing now:
        const task_edit_control_panel = this.state.inEdit ? this.TASK_EDIT_CONTROL_PANEL : null;
        const task_control_panel = this.state.inEdit ? null : this.TASK_CONTROL_PANEL;
        const task_selector = this.state.inEdit ? null : this.TASK_SELECTOR;


        const VIEW_CONTENT = (
            <div className={"task-properties-panel"}>
                <TaskPropertyArea field={PropertyField.NAME} value={this.state.task.taskName}
                                  disabled={!this.state.inEdit} onChange={this.onChange} taskStatus={this.state.task.taskStatus} />
                <TaskPropertyArea field={PropertyField.DESC} value={this.state.task.taskDescription}
                                  disabled={!this.state.inEdit} onChange={this.onChange} taskStatus={this.state.task.taskStatus} />
                <div className={"task-date-properties-panel"}>
                    <div className={"date-property-section"}>
                        <p> {Localization.getLocalizedString("tv_task_date_creation")} </p>
                        <TaskPropertyField type={TaskPropertyFieldType.INPUT} value={this.state.task.taskCreationDate}
                                           disabled={true} taskStatus={this.state.task.taskStatus} />
                    </div>
                    <div className={"date-property-section"}>
                        <p> {Localization.getLocalizedString("tv_task_date_completion")} </p>
                        <TaskPropertyInput field={PropertyField.COMPLETION} value={this.state.task.taskCompletionDate}
                                           disabled={!this.state.inEdit} onChange={this.onChange} taskStatus={this.state.task.taskStatus} />
                    </div>
                </div>
            </div>)

        return(
            <div id={this.props.viewId} className={"task-view"}>
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
 * @property completed - task completion flag.
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

    const completedStyle = props.completed ? "linethrough-text " : "";

    // Bases on props.type return textarea or DatePicker:
    if (props.type === TaskPropertyFieldType.AREA)
        return (<textarea ref={textAreaRef} value={props.value} disabled={props.disabled}
                         className={"task-property-area " +completedStyle +props.classesStr} onChange={props.onChange} />);
    else
        return <DatePicker selected={props.value} className={"task-property-date-input " +completedStyle} onChange={(event) => {
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
 * @property taskStatus - task status property {TaskStatus};
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

        const completedFlag = this.props.taskStatus === TaskStatus.COMPLETED;


        // Return text area:
        return <TaskPropertyField type={TaskPropertyFieldType.AREA} classesStr={addClass} value={this.props.value}
                                  disabled={this.props.disabled} onChange={this.onChange} completed={completedFlag} />
    }
}

/**
 * @property taskStatus - task status property {TaskStatus};
 */
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
        const completedFlag = this.props.taskStatus === TaskStatus.COMPLETED;
        return <TaskPropertyField type={TaskPropertyFieldType.INPUT} value={this.props.value}
                                  disabled={this.props.disabled} onChange={this.onChange} completed={completedFlag} />

    }
}


const TaskControlMenu =(props) => {
    return <Menu menuDirection={MenuDirection.HORIZONTAL} menuClass={"task-control-menu"}> {props.children} </Menu>
}

const TaskEditorControlPanel =(props) => {
    return <Menu menuDirection={MenuDirection.HORIZONTAL} menuClass={"task-editor-control-panel"}> {props.children} </Menu>
}
