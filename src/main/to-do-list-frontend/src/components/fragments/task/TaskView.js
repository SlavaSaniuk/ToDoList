import React, {useEffect, useRef} from "react";
import '../../../styles/common.css'
import '../../../styles/fragments/task/task-view.css'
import DatePicker from "react-datepicker";
import {Localization} from "../../../js/localization/localization";
import {Menu, MenuDirection} from "../../ui/Menu";
import {CrossButton, DoneButton, EditButton} from "../../Buttons";

/**
 * TaskView component user to display, edit single user task.
 * @props - component props.
 * @property - task - Task model object.
 * @property - loadingStatus - task loading status ({TaskViewLoadingStatus}).
 * @stateProperty - loadingStatus - loading status of this view ({TaskViewLoadingStatus}).
 * @stateProperty - inEdit - flag indicate if task is editing now.
 */
export class TaskView extends React.Component {
    constructor(props) {
        super(props);

        // Component state:
        this.state = {
            task: Object.assign(this.props.task), // Task property copy;
            loadingStatus: this.props.loadingStatus, // By default task is loading;
            inEdit: false
        }
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
     * Render component.
     * @returns {JSX.Element}
     */
    render() {

        // VIEW content variable:
        let VIEW_CONTENT;

        // If task is loading, return loaded:
        if (this.state.loadingStatus === TaskViewLoadingStatus.LOADING)
            VIEW_CONTENT = (<div className={"purple-loader task-view-loader"}></div>)

        if (this.state.loadingStatus === TaskViewLoadingStatus.LOADED) {
            VIEW_CONTENT = (
                <div className={"task-view-content"}>
                    <TaskSelection />
                    <div className={"task-properties-panel"}>
                        <TaskPropertyField type={TaskPropertyFieldType.AREA} value={this.state.task.taskName} disabled={!this.state.inEdit} />
                        <TaskPropertyField type={TaskPropertyFieldType.AREA} value={this.state.task.taskDescription} disabled={!this.state.inEdit} />
                        <div className={"task-date-properties-panel"}>
                            <p> {Localization.getLocalizedString("tv_task_date_creation")}
                                <TaskPropertyField type={TaskPropertyFieldType.INPUT} value={this.state.task.taskCreationDate} disabled={true} /> </p>
                            <p> {Localization.getLocalizedString("tv_task_date_completion")}
                                <TaskPropertyField type={TaskPropertyFieldType.INPUT} value={this.state.task.taskCompletionDate} disabled={false} /> </p>
                        </div>
                    </div>
                </div>)
        }

        return(
            <div className={"task-view"}>
                {VIEW_CONTENT}
                <TaskControlMenu >
                    <DoneButton classes={"control-btn"} />
                    <EditButton classes={"control-btn"}/>
                    <CrossButton classes={"control-btn"}/>
                </TaskControlMenu>
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
        return <textarea ref={textAreaRef} value={props.value} disabled={props.disabled} className={"task-property-area"} />;
    else
        return <DatePicker selected={props.value} className={"task-property-date-input"} disabled={props.disabled}  />
});

const TaskPropertyFieldType = {AREA: 1, INPUT: 2};

const TaskControlMenu =(props) => {
    return <Menu menuDirection={MenuDirection.HORIZONTAL} menuClass={"task-control-menu"}> {props.children} </Menu>
}