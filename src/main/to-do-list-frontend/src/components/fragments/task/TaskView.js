import React, {useEffect, useRef} from "react";
import '../../../styles/common.css'
import '../../../styles/fragments/task/task-view.css'
import DatePicker from "react-datepicker";
import {Localization} from "../../../js/localization/localization";
import {Menu, MenuDirection} from "../../ui/Menu";
import {CrossButton, DoneButton, EditButton, TextButton} from "../../Buttons";
import {LevelLogger, Logger} from "../../../js/logging/Logger";
import {Properties} from "../../../Properites";
import {TaskBuilder} from "../../../js/models/Task";
import {DateTimeUtilities} from "../../utilities/DateTimeUtilities";
import {ClientLocalization} from "../../../js/utils/ClientUtilities";
import {DatePickerInput, ScalableTextArea} from "../../ui/InputsUi";

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
    LOGGER = new LevelLogger("TaskView.js", Properties.GLOBAL_LEVEL_LOGS);

    /**
     * Construct new TaskView react component,
     * @param props
     */
    constructor(props) {
        super(props);
        this.LOGGER.debug("Construct new TaskView component with props[%o];", [props]);

        // Component state:
        this.state = {
            task: Object.assign(this.props.task), // Task property copy;
            inEdit: false
        }

        // Bind functions:
        this.taskViewProps.bind(this);
        this.onEdit.bind(this);
        this.onCancelEdit.bind(this);
        this.onChange.bind(this);
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

        // Functions to pass to task-view-edition-block:
        this.editFunctions = {updateFunction: this.onUpdate, cancelFunction: this.onCancelEdit};

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
     * Cancel task edit.
     * Function calling when user click on cancel text button in task view edit block.
     */
    onCancelEdit =() => {
        this.setState({
            inEdit: false
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
     *  Update current edited task.
     *  Function calling when click on update text button in task editor.
     *  function calling parent update function.
     */
    onUpdate =(taskToUpdate) => {
        this.LOGGER.debug("Update TaskView[viewId: %o] with new task properties[%o];", [this.props.viewId, this.state.task]);


        // Reset state inEdit flag:
        this.setState({
            inEdit: false
        });

        // Create TaskViewProps:
        const taskViewProps = this.taskViewProps();

        this.props.setLoadingStatus(true, this.props.viewId);

        // Call parent function:
        // this.props.parentControlFunctions.updateFunction(taskViewProps);
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
     * @returns {JSX.Element} - component.
     */
    render() {

        // If task is loading, return loader:
        if (this.props.loadingStatus === TaskViewLoadingStatus.LOADING)
            return (<div className={"purple-loader task-view-loader"}></div>)


        // Check if task is in editing now:
        if (this.state.inEdit) return <TaskViewEditingBlock editableTask={this.state.task} editFunctions={this.editFunctions} />

        const task_edit_control_panel = this.state.inEdit ? this.TASK_EDIT_CONTROL_PANEL : null;
        const task_control_panel = this.state.inEdit ? null : this.TASK_CONTROL_PANEL;
        const task_selector = this.state.inEdit ? null : this.TASK_SELECTOR;


        const VIEW_CONTENT = (
            <div className={"task-properties-panel"}>
                <TextTaskProperty inEdit={this.state.inEdit} value={this.state.task.taskName} // Task name text property;
                                  field={PropertyField.NAME} onChange={this.onChange} />
                <TextTaskProperty inEdit={this.state.inEdit} value={this.state.task.taskDescription} // Task description text property;
                                  field={PropertyField.DESC} onChange={this.onChange} />
                <div className={"date-task-properties-panel"}>
                    <p> <DateTaskProperty value={this.state.task.taskCompletionDate} dateFormat={"Tt-DD.mm"} /> </p>
                    <p> {ClientLocalization.getLocalizedText("tv_created_at")} <DateTaskProperty value={this.state.task.taskCreationDate} dateFormat={"dd.mm"} /> </p>
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
 * @propsProperty - onChange - {Function} - onChange function.
 */
const TextTaskProperty =(props) => {

    // Refs:
    const textAreaRef = useRef(null);

    // Effects:
    useEffect(() => {
        // If inner text area value changed, then change its height:
        if (props.inEdit) {
            // Get text area element:
            const textArea = textAreaRef.current;
            // Set height:
            textArea.style.height = "0px";
            textArea.style.height = textArea.scrollHeight +"px";
        }

    })

    // Additional classname based on props property field:
    let additionalClassName = null;
    // If field value is task name:
    if (props.field === PropertyField.NAME) additionalClassName = "text-task-property-p-name";
    // If field value is task desc:
    if (props.field === PropertyField.DESC) additionalClassName = "text-task-property-p-desc";

    // Render based on "inEdit" flag:
    if (!props.inEdit || typeof props.inEdit == 'undefined') { // If property not is in edit now, then return p:
        return <p className={"text-task-property-p " +additionalClassName}> {props.value} </p>
    }else { // If property is in edit now, then return textarea:
        return <textarea rows={1} ref={textAreaRef} className={"text-task-property-ta"} value={props.value}
                         onChange={event => props.onChange(event, props.field)} />
    }
}

/**
 * Date task property component.
 * @param props - component props.
 * @propsProperty value - {Date} - task property date value.
 * @propsProperty - inEdit = {boolean} - flag indicate if current property can be modified.
 * @propsProperty - dateFormat - {String} - date format {see JsDateFormatter}.
 */
const DateTaskProperty =(props) => {
    // Return element based on ieEdit prop value:
    if (!props.inEdit) {
        return <span> {DateTimeUtilities.dateToFormattedStr(props.value, props.dateFormat, Properties.CLIENT_LOCALE)} </span>;
    }
}

/**
 * Values for {TaskPropertyArea/TaskPropertyInput} field property.
 * @type {{CREATION: number, COMPLETION: number, DESC: number, NAME: number}}
 */
const PropertyField = {NAME: 0, DESC: 1, CREATION: 2, COMPLETION: 3};

// noinspection JSUnresolvedVariable
/**
 * @param props - component props.
 * @propsProperty - isShow - {boolean} - flag indicate if this component must be showed to user.
 * @propsProperty - defaultCompletionDate - {Date} - default completion date value.
 * @propsProperty - addFunction - {Object[Function]} - task adding parent function.
 * @propsProperty - hideBlockFunction - {Object[Function]} - task adding block hide function.
 */
export class TaskViewAddingBlock extends React.Component {
    constructor(props) {
        super(props);

        // Variables:
        this.LOGGER = new LevelLogger("TaskViewAddingBlock", Properties.GLOBAL_LEVEL_LOGS);

        // Bind functions:
        this.onCreateTask.bind(this);
        this.onCancel.bind(this);


        // Component state:
        this.state = {
            taskName: "", // new task name;
            taskDesc: "", // new task description;
            creationDate: new Date, // new task creation date;
            completionDate: this.props.defaultCompletionDate // new task completion date;
        }
    }

    /**
     * Create user task.
     * Function calling when user click on "create" task control button.
     */
    onCreateTask =() => {
        const taskToCreate = TaskBuilder.builder()
            .withName(this.state.taskName)
            .withDescription(this.state.taskDesc)
            .withDateOfCreation(this.state.creationDate)
            .withDateOfCompletion(this.state.completionDate).build();
        this.LOGGER.trace("onCreateTask function with task to be created: [%o];", [taskToCreate]);

        // Construct new task view props and call parent task adding function:
        this.props.addFunction(taskToCreate);

        // Hide adding block:
        this.props.hideBlockFunction(false);
    }

    /**
     * Cancel adding task.
     * Clear user inputs and hide this element.
     * Function calling when user click on "Cancel" task control button.
     */
    onCancel =() => {
        // Clear user inputs:
        this.setState({
            taskName: "", // new task name;
            taskDesc: "", // new task description;
            completionDate: this.props.defaultCompletionDate // new task completion date;
        })

        // Hide this block:
        this.props.hideBlockFunction(false);
    }

    /**
     * Render TaskViewAddingBlock component.
     * @return {JSX.Element} - component.
     */
    render() {

        const showClass = this.props.isShow ? "adding-block-showed" : null;

        return (
            <div className={"task-view-adding-block " +showClass}>
                <p> {Localization.getLocalizedString("tvab_title")} </p>
                <textarea className={"task-name-textarea"} rows={1} placeholder={Localization.getLocalizedString("tvab_name")} value={this.state.taskName} onChange={event => this.setState({taskName: event.target.value})} />
                <textarea className={"task-desc-textarea"} rows={1} placeholder={Localization.getLocalizedString("tvab_desc")} value={this.state.taskDesc} onChange={event => this.setState({taskDesc: event.target.value})}/>
                <DatePicker className={"task-completion-input"} selected={this.state.completionDate} dateFormat="dd/MM" onChange={date => this.setState({completionDate: date})} />
                <div className={"adding-block-controls"}>
                    <TextButton btnText={Localization.getLocalizedString("tvab_btn_create")} classes={"add-blk-ctrl-btn add-blk-ctrl-btn-create"} clickFunc={this.onCreateTask} />
                    <TextButton btnText={Localization.getLocalizedString("tvab_btn_cancel")} classes={"add-blk-ctrl-btn add-blk-ctrl-btn-cancel"} clickFunc={this.onCancel} />
                </div>
            </div>
        );
    }
}

/**
 * TaskViewEditingBlock used to edit already existed user task.
 * @param props - component props.
 * @propsProperty editableTask - {Object[Task]} - editable task object.
 * @propsProperty editFunctions - {Object} - object with parent task edit functions.
 * @stateProperty - taskName - editable task name.
 * @stateProperty - taskDescription - editable task description.
 * @stateProperty - taskCompletionDate - editable task completion date.
 */
class TaskViewEditingBlock extends React.Component {
    constructor(props) {
        super(props);

        // Logger:
        this.LOGGER = new LevelLogger("TaskViewEditingBlock.js", Properties.GLOBAL_LEVEL_LOGS);

        // Bind functions:
        this.onNameUpdate.bind(this);
        this.onDescriptionUpdate.bind(this);
        this.onDateCompletionUpdate.bind(this);
        this.onUpdate.bind(this);

        // Component state:
        this.state = {
            taskName: this.props.editableTask.taskName, // task name;
            taskDescription: this.props.editableTask.taskDescription, // task desc;
            taskCompletionDate: this.props.editableTask.taskCompletionDate // task completion date;
        }
    }

    /**
     * Update task name.
     * @param event - textarea onChange event.
     */
    onNameUpdate =(event) => {
        this.setState({taskName: event.target.value});
    }

    /**
     * Update task description.
     * @param event - textarea onChange event.
     */
    onDescriptionUpdate =(event) => {
        this.setState({taskDescription: event.target.value});
    }

    /**
     * Update task completion date.
     * @param newDate - {Date} - new task completion date value.
     */
    onDateCompletionUpdate =(newDate) => {
        this.setState({taskCompletionDate: newDate});
    }

    /**
     * Update task.
     * Function calling when user click on update control button.
     */
    onUpdate =() => {
        // Construct new task object:
        const updateTask = TaskBuilder.builder().ofId(this.props.editableTask.taskId)
            .withName(this.state.taskName)
            .withDescription(this.state.taskDescription)
            .withDateOfCompletion(this.state.taskCompletionDate)
            .withDateOfCreation(this.props.editableTask.taskCreationDate)
            .withStatus(this.props.editableTask.taskStatus)
            .build();
        this.LOGGER.debug("Update task[%o];",[updateTask]);

        // Call parent function:
        this.props.editFunctions.updateFunction(updateTask);
    }

    /**
     * Render task view editing block.
     * @return {JSX.Element} - {TaskViewEditingBlock}.
     */
    render() {
        return (
            <div className={"task-view-editing-block"}>
                <p> {ClientLocalization.getLocalizedText("tveb_title")} </p>
                <ScalableTextArea value={this.state.taskName} onChange={this.onNameUpdate} placeholder={"Hello world!"}/>
                <ScalableTextArea value={this.state.taskDescription} onChange={this.onDescriptionUpdate} placeholder={"Hello world!"}/>
                <DatePickerInput wrapperClassName={"task-editing-completion-date"} inputClassName={"task-editing-completion-date-input"}
                                 date={this.state.taskCompletionDate} onChange={this.onDateCompletionUpdate} />
                <div className={"task-edition-block-control-panel"}>
                    <TextButton btnText={ClientLocalization.getLocalizedText("tveb_update_text")} classes={"task-editing-update-btn"} clickFunc={this.onUpdate} />
                    <TextButton btnText={ClientLocalization.getLocalizedText("tveb_cancel_text")} classes={"task-editing-cancel-btn"} clickFunc={this.props.editFunctions.cancelFunction} />
                </div>
            </div>
        )
    }
}

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
