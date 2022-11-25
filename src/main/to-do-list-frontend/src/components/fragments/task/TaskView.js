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

    /**
     * Construct new TaskView react component,
     * @param props
     */
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
        this.onComplete.bind(this);

        // Initialize class variables:
        this.TASK_EDIT_CONTROL_PANEL = (<TaskEditorControlPanel>
            <TextButton btnText={Localization.getLocalizedString("tv_edit_control_btn_update")}
                        classes={"task-editor-control-button"} clickFunc={this.onUpdate} />
            <TextButton btnText={Localization.getLocalizedString("tv_edit_control_btn_cancel")}
                        classes={"task-editor-control-button"} clickFunc={this.onCancel} />
        </TaskEditorControlPanel>);

        this.TASK_CONTROL_PANEL = (<TaskControlMenu >
            <DoneButton classes={"control-btn"} clickFunc={this.onComplete} />
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
     * Complete user task.
     * Function calling when user click on "DONE" control button.
     * Function call parent complete function.
     */
    onComplete =async () => {
        const viewProps = this.taskViewProps();
        this.LOGGER.log("onComplete task in TaskView[%o];", [viewProps]);

        // Call parent function:
       await this.props.parentControlFunctions.completeFunction(viewProps);
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
                <TextTaskProperty inEdit={this.state.inEdit} value={this.state.task.taskName} field={PropertyField.NAME} />
                <TextTaskProperty inEdit={this.state.inEdit} value={this.state.task.taskDescription} field={PropertyField.DESC} />
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
                <input type={"checkbox"} className={"task-selector-inp"} onChange={this.onClick} checked={this.state.checked} />
        )
    }
}

/**
 * Component display any text {Task} task property (e.g. task name, task desc).
 * Based on component props boolean property "inEdit" render html(if true) <p> or <textarea> element(or if else).
 * @param props - component props.
 * @propsProperty - inEdit - {boolean} - current text property is in edit now.
 * @propsProperty - value - {String} - <p> text or <textarea> value.
 * @propsProperty - field - {PropertyField} - task object property.
 */
const TextTaskProperty =(props) => {

    // Additional classname based on props property field:
    let additionalClassName = null;
    // If field value is task name:
    if (props.field === PropertyField.NAME) additionalClassName = "text-task-property-p-name";
    // If field value is task desc:
    if (props.field === PropertyField.DESC) additionalClassName = "text-task-property-p-desc";

    // Render based on "inEdit" flag:
    if (!props.inEdit || typeof props.inEdit == 'undefined') { // If property not is in edie now, then return p:
        return <p className={"text-task-property-p " +additionalClassName}> {props.value} </p>
    }else {
        return <textarea value={props.value} />
    }
}


// noinspection JSUnresolvedVariable
/**
 * @param props - component props.
 * @propsProperty - isShow - {boolean} - flag indicate if this property must be showed to user.
 * @propsProperty - defaultCompletionDate - {Date} - default completion date value.
 */
export class TaskViewAddingBlock extends React.Component {
    constructor(props) {
        super(props);

        // Bind functions:

        // Component state:
        this.state = {
            completionDate: this.props.defaultCompletionDate
        }
    }

    render() {

        const showClass = this.props.isShow ? "adding-block-showed" : null;

        return (
            <div className={"task-view-adding-block " +showClass}>
                <p> {Localization.getLocalizedString("tvab_title")} </p>
                <textarea className={"task-name-textarea"} rows={1} placeholder={Localization.getLocalizedString("tvab_name")} />
                <textarea className={"task-desc-textarea"} rows={1} placeholder={Localization.getLocalizedString("tvab_desc")} />
                <DatePicker className={"task-completion-input"} selected={this.state.completionDate} dateFormat="dd/MM" />
                <div className={"adding-block-controls"}>
                    <TextButton btnText={Localization.getLocalizedString("tvab_btn_create")} classes={"add-blk-ctrl-btn add-blk-ctrl-btn-create"} />
                    <TextButton btnText={Localization.getLocalizedString("tvab_btn_cancel")} classes={"add-blk-ctrl-btn add-blk-ctrl-btn-cancel"} />
                </div>
            </div>
        );
    }
}

/*

class TaskPropertyArea extends React.Component {
    constructor(props) {
        super(props);

        // Refs:
        this.areaRef = React.createRef();

        // Bind functions:
        this.onChange.bind(this);
    }

    componentDidMount() {
    }


    onChange =(event) => {
        this.props.onChange(event, this.props.field);
    }

    shouldComponentUpdate =(nextProps) => {
        return (nextProps.value !== this.props.value) || (nextProps.disabled !== this.props.disabled);
    }

    render() {

        // Class name based on task field type:
        let addClass;
        if (this.props.field === PropertyField.NAME) addClass = "task-name-property-area";
        else if (this.props.field === PropertyField.DESC) addClass = "task-desc-property-area";
        else addClass ="";

        const completedFlag = this.props.taskStatus === TaskStatus.COMPLETED;

        return <p className={"task-property-area " +addClass}> {this.props.value} </p>


    }
}

 */



/**
 * Task property input represent a Date input for task creation and completion dates.
 * @param props - component props.
 * @property value - text area value.
 * @property disabled - flag indicate if textarea is enabled/disabled.
 * @property onChange - onChange action function.
 * @property field - inner task field {PropertyField}.
 * @property taskStatus - task status property {TaskStatus}.
 * @returns {JSX.Element} - {DatePicker} date picker.
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

/**
 * Values for {TaskPropertyField} type property.
 * @type {{AREA: number, INPUT: number}}
 */
const TaskPropertyFieldType = {AREA: 1, INPUT: 2};

/**
 * Values for {TaskPropertyArea/TaskPropertyInput} field property.
 * @type {{CREATION: number, COMPLETION: number, DESC: number, NAME: number}}
 */
const PropertyField = {NAME: 0, DESC: 1, CREATION: 2, COMPLETION: 3};

/**
 * Task control menu render task control buttons: "Complete", "Edit", "Remove";
 * @param props - component props.
 * @returns {JSX.Element} - {Menu} horizontal menu.
 */
const TaskControlMenu =(props) => {
    return <Menu menuDirection={MenuDirection.HORIZONTAL} menuClass={"task-control-menu"}> {props.children} </Menu>
}

/**
 * Editor control panel render block with task editor control buttons "Update", "Cancel".
 * @param props - component props.
 * @returns {JSX.Element} - {Menu} horizontal menu.
 */
const TaskEditorControlPanel =(props) => {
    return <Menu menuDirection={MenuDirection.HORIZONTAL} menuClass={"task-editor-control-panel"}> {props.children} </Menu>
}
