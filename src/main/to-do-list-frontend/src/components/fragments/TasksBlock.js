import React from "react";
import '../../styles/common.css'
import '../../styles/fragments/TasksBlock.css'
import {TaskAddition} from "./AddTaskBlock";
import {ReqUtilities} from "../utilities/ReqUtilities";
import {CheckmarkButton, CrossButton, PlusButton} from "../Buttons";
import {Logging} from "../../js/utils/Logging";
import {DateTimeUtilities} from "../utilities/DateTimeUtilities";
import {Menu, MenuDirection, MenuItem} from "../ui/Menu";
import {TaskView, TaskViewLoadingStatus} from "./task/TaskView";
import {TaskBuilder, TaskDtoBuilder, TaskStatus} from "../../js/models/Task";
import {StringUtilities} from "../../js/utils/StringUtilities";
import {LevelLogger, Logger} from "../../js/logging/Logger";
import {Properties} from "../../Properites";
import {ListIcon} from "../ui/Icons";
import {FilteredContentBlock, TASKS_FILTER_TYPE, TasksFilterType} from "./task/FilteredContentBlock";

const TasksFooter =() => {
    return(<div className={"tasks-footer"}>
        <p className={"tasks-footer-text"}> LOAD MORE ... </p>
    </div>);
}

/**
 * Task content block is root element that has and render the TasksList and AddTaskBlock elements.
 * @property showAddTaskBlock - boolean flag.
 * @property showAddTaskBlockFunc - function to show/hide AddTaskBlock.
 * @property tasksList - list of users tasks.
 * @property funcOnSelectTask - call this function, when task is selected.
 * @property funcOnUnselectTask - parent function on unselect task action.
 * @property taskControlFuncs - task control function in object.
 * @property selectedTasksList - list of selected tasks.
 * @property taskViewsToRender - list of TaskViewProps to be rendered.
 */
class TasksContentBlock extends React.Component {

    /**
     * Render TaskContentBlock element.
     * @returns {JSX.Element} - html.
     */
    render() {
        return(
            <div className={"task-content-block-old"}>
                <TaskAddition isShow={this.props.showAddTaskBlock} at_appearanceFunc={this.props.showAddTaskBlockFunc} at_addTaskFunction={this.props.taskControlFuncs.addFunc} />
                <TaskViewsList viewsList={this.props.taskViewsToRender} taskControlFunctions={this.props.taskControlFunctions} />
            </div>
        );
    }


}


/**
 * Edit button statuses:
 * ACTIVE - 0;
 * DISABLED - 1;
 */

const TasksEditBtnStatus = {ACTIVE: 0, DISABLED: 1};

/**
 * Edit button types:
 * ADD - ADD;
 * REMOVE - REMOVE;
 * EDIT - EDIT;
 */
const TasksEditBtnTypes = {ADD: "ADD", DONE: 4, EDIT: "EDIT",REMOVE: "REMOVE"};

/**
 * Class represent a task edit button element.
 * @property btnType - TasksEditBtnTypes type btp value.
 * @property btnStatus - Status of edit button (see TasksEditBtnStatuses).
 * @property clickFunc - Function on click.
 *
 */
class TasksEditBtn extends React.Component {

    /**
     * Construct new TasksEditBtn element.
     * @param props - properties.
     */
    constructor(props) {
        super(props);

        // Bind functions:
        this.isEnabled.bind(this);
    }

    isEnabled =() => {
        return this.props.btnStatus === TasksEditBtnStatus.ACTIVE;
    }
    /**
     * Render EditTaskBtn element.
     * @returns {JSX.Element} - html.
     */
    render() {

        let btnClass = "tasks-edit-btn-";

        // Check type:
        switch (this.props.btnType) {
            case TasksEditBtnTypes.ADD:
                btnClass+="add";
                break;
            case TasksEditBtnTypes.EDIT:
                btnClass+="edit";
                break;
            case TasksEditBtnTypes.REMOVE:
                btnClass+="remove";
                break;
            case TasksEditBtnTypes.DONE:
                btnClass+="done";
                break;
            default:
                break;
        }

        // Check status:

        if (this.props.btnStatus === TasksEditBtnStatus.DISABLED) btnClass += "-disabled";
        // Set className based on button status:
        let buttonClass = "tasks-edit-svg-btn";
        if (this.props.btnStatus === TasksEditBtnStatus.DISABLED) buttonClass+="-disabled";


        if (this.props.btnType === TasksEditBtnTypes.REMOVE) {
            const clickFunc = this.isEnabled() ? this.props.clickFunc : null;
            return <CrossButton classes={buttonClass} clickFunc={clickFunc} />
        }

        if (this.props.btnType === TasksEditBtnTypes.DONE) {
            const clickFunc = this.isEnabled() ? this.props.clickFunc : null;
            return <CheckmarkButton classes={buttonClass} clickFunc={clickFunc} />
        }


        if (this.props.btnType === TasksEditBtnTypes.ADD) {
            const clickFunc = this.isEnabled() ? this.props.clickFunc : null;
            return <PlusButton classes={buttonClass} clickFunc={clickFunc} />
        }

        return(
            <input type={"button"} disabled={this.props.btnStatus}
                   className={"tasks-edit-btn " +btnClass} onClick={this.props.clickFunc}/>
        );
    }
}

/**
 * This panel render tasks edit buttons {TasksEditBtn}.
 * @param props - React properties.
 * @property - tasksEditBtnStatuses - statuses of tasks edit buttons.
 * @property - tasksEditBtnFunctions
 * @returns {JSX.Element} - html.
 */
const TasksEditPanel =(props) => {
    return(<div className={"tasks-edit-panel tasks-menu-panel col-4"}>
        <TasksEditBtn btnType={TasksEditBtnTypes.ADD} btnStatus={props.tasksEditBtnStatuses.addStatus}
                      clickFunc={props.tasksEditBtnFunctions.addFunction} />
        <TasksEditBtn btnType={TasksEditBtnTypes.DONE} btnStatus={props.tasksEditBtnStatuses.completeStatus}
                      clickFunc={props.tasksEditBtnFunctions.completeFunction} />
        <TasksEditBtn btnType={TasksEditBtnTypes.REMOVE} btnStatus={props.tasksEditBtnStatuses.completeStatus}
                      clickFunc={props.tasksEditBtnFunctions.removeFunction} />
    </div>)
}

/**
 * Tasks filter item types constants.
 * @type {{ALL: string, TODAY: string, TOMORROW: string, WEEK: string}}
 */
const FilterItemType = {ALL: "filter_item_type_ALL", TODAY: "filter_item_type_TODAY",
    TOMORROW: "filter_item_type_TOMORROW", WEEK: "filter_item_type_WEEK"}

/**
 * TaskFilterItem is wrapper for {MenuItem} component.
 * @param props - component props.
 * @property isActive - flag indicate if this item is active now.
 * @returns {JSX.Element}
 * @constructor
 */
const TaskFilterItem =(props) => {
    let itemClass = props.isActive ? "filter-item-active" : "filter-item";
    return (<MenuItem itemId={props.itemId} itemText={props.itemText} itemClass={itemClass} clickFunction={props.clickFunction}/>)
}

const TasksFilter =(props) => {
    return (<Menu menuDirection={MenuDirection.HORIZONTAL} menuClass={"tasks-filter col-6"} > {props.children} </Menu>)
}

const TasksFilterInfoPanel =(props) => {
    return (<div className={"col-6"}> <p> {props.infoText} </p> </div>)
}

const TasksFilterPanel =(props) => {
    return (<div className={"tasks-filter-panel col-8 row"}> {props.children} </div>)
}

/**
 * This element is menu of {TasksEditBtn} control buttons.
 * @param props - react properties.
 * @property - editBtnAddStatus - status of "add" button.
 * @property - editBtnRemoveStatus- status of "remove" button.
 * @property - funcOnRemoveTasks - parent function on remove tasks action.
 * @property - tasksEditBtnStatuses - statuses of tasks edit buttons.
 * @property - tasksEditBtnFunctions - tasks edit buttons click functions.
 * @returns {JSX.Element} - html;
 * @constructor
 */
const TasksTopMenu =(props) => {
    return(
        <div className={"tasks-top-menu row"} >
            <TasksEditPanel tasksEditBtnStatuses={props.tasksEditBtnStatuses}
                            tasksEditBtnFunctions={props.tasksEditBtnFunctions} />
            {props.children}
        </div>
    );
}


/**
 * Tasks block loading statuses.
 * @field LOADING - status, when tasks block try to load user tasks.
 */
const TasksBlockLoadStatus = {LOADING: 1, LOADED: 2};
/**
 * TasksBlock is root component that's render user's tasks at user page.
 * @property userId - user ID.
 * @function loadUserTasks.
 * @function showAddTaskBlock(boolean);
 * @function onAddNewTask;
 * @function onRemoveTasks - function calls when user click remove edit button.
 * @function onRemoveTask - function call when user click task control button.
 * @function postNewTask;
 * @function onSelectTasks - function calls when user select task.
 * @function onUnselectTask - function calls when user unselect task.
 */
export class TasksBlock extends React.Component {
    // Class variables:
    logger = new Logger("TasksBlock", Properties.TASKS_BLOCK_LOGGING); // Logger;
    tasksEditBtnStatuses; // Statuses of TasksEdit buttons;
    tasksEditBtnFunctions; // TasksEdit buttons onClick action functions;
    taskControlFunctions; // Task control functions for TaskView;

    constructor(props) {
        super(props);

        // Initialize variables:
        this.LOGGER = new LevelLogger("TasksBlock", Properties.GLOBAL_LEVEL_LOGS); // Level logger;

        // Initialize TaskEdit buttons functions:
        this.tasksEditBtnFunctions = {
            addFunction: this.showAddTaskBlock,
            completeFunction: this.onCompleteUserTasks,
            removeFunction: this.onRemoveTasks
        };

        // Initial status of TasksEdit buttons:
        this.tasksEditBtnStatuses = {
            addStatus: TasksEditBtnStatus.ACTIVE,
            completeStatus: TasksEditBtnStatus.DISABLED,
            removeStatus: TasksEditBtnStatus.DISABLED
        };

        // Bind functions:
        this.loadUserTasks.bind(this);
        this.onSelectTasks.bind(this);
        this.onUnselectTask.bind(this);
        this.onRemoveUserTask.bind(this);
        this.removeUserTask.bind(this);
            // ====== Task completion functions =====
        this.onCompleteUserTask.bind(this);
        this.completeUserTask.bind(this);
        this.onCompleteUserTasks.bind(this);
        this.onClickFilterItem.bind(this);
        this.isActiveFilterItem.bind(this);
        // ====== Tasks adding functions =======
        this.addUserTask.bind(this);

        // Element state:
        this.state = {
            loadStatus: TasksBlockLoadStatus.LOADING, // Default load status (because need to load user tasks list);
            userTasksList: [], // list of user tasks to be rendered {List<Task>};
            // ==== LIST OF USERS TASKS ====
            isShowAddTaskBlock: false, // Flag to show AddTaskBlock element;
            tasksList: [], // Array of users tasks;
            // ==== TASKS SELECTION ====
            isTasksSelected: false, // Selected tasks flag;
            selectedTasksList: [], // List of selected tasks;
            // ==== EDIT BUTTON STATUSES ====
            statusAddBtn: TasksEditBtnStatus.ACTIVE, // "Add" button status;
            statusRemoveBtn: TasksEditBtnStatus.DISABLED, // "Remove" button status;
            // ==== FILTER =====
            filter_serverDate: new Date(2022, 0, 1), // Server date;
            filter_activeItem: null, // Active filter item [{Filter.FILTER_TYPE}]:
            filterInfoText: "",
            // ===== VIEWS =====
            taskViewPropsList: [] // List of task views;
        };

        // Initialize task control functions:
        this.taskControlFunctions = {
            addFunction: this.onAddUserTask,
            completeFunction: this.onCompleteUserTask,
            removeFunction: this.onRemoveUserTask
        };

        this.tasksControlFuncs = {
            add: this.addUserTask
        }
    }

    componentDidMount() {
        this.loadUserTasks().then();

        // Server date:
        let serverDate = null;

        // Get server date:
        ReqUtilities.getRequest("/rest/info/server-date").then(result => {
            if (result.ok) {
                result.json().then(dateTimeDto => {
                    serverDate = new Date(dateTimeDto.dateStr);
                    Logging.log("Server date: ", serverDate);
                    this.setState({
                        filter_serverDate: serverDate
                    })
                })
            }
        });

    }

    /**
     * Load users tasks from server.
     * Function create fetch http request to get users tasks array.
     * @returns {Promise<any>}  - result json.
     */
    loadUserTasks = async () => {
        let resultTasksList = [];
        const promise = await ReqUtilities.getRequest("/rest/tasks/" + this.props.userId);
        promise.json().then((taskListDto) => {
            if (!taskListDto.exception) {
                taskListDto.tasksList.forEach((taskDto) => {
                    resultTasksList.push(taskDto);
                })

            }

            // For each user tasks construct new TaskViewProps:
            const taskViewPropsList = [];
            const userTasksList = []; // Loaded user tasks:
            resultTasksList.forEach((taskDto) => {
                const taskObj = TaskBuilder.builder().ofId(taskDto.taskId)
                    .withName(taskDto.taskName)
                    .withDescription(taskDto.taskDesc)
                    .withDateOfCreation(new Date(taskDto.dateOfCreation))
                    .withDateOfCompletion(new Date(taskDto.dateOfCompletion))
                    .withStatus(taskDto.taskStatus)
                    .build();

                // Push to user tasks list:
                userTasksList.push(taskObj);

                taskViewPropsList.push(new TaskViewProps(taskObj, TaskViewLoadingStatus.LOADED));
            })

            // Set state:
            this.setState(prevState => ({
                userTasksList: prevState.userTasksList.concat(userTasksList),
                tasksList: prevState.tasksList.concat(resultTasksList),
                loadStatus: TasksBlockLoadStatus.LOADED,
                taskViewPropsList: prevState.taskViewPropsList.concat(taskViewPropsList)
            }));
        })


    }


    /**
     * Function calling when user select any task {Task.TaskSelector.checkbox selected}.
     * Function set isTasksSelected state to true and add task to state selected task list.
     * @param aTask - selected task.
     */
    onSelectTasks =(aTask) => {
        if (this.state.isTasksSelected !== true)
            // Set TaskEdit btn statuses:
            this.tasksEditBtnStatuses = {
                addStatus: TasksEditBtnStatus.DISABLED,
                completeStatus: TasksEditBtnStatus.ACTIVE,
                removeStatus: TasksEditBtnStatus.ACTIVE
            }

        this.setState(prevState => ({
            isTasksSelected: true,
            selectedTasksList: prevState.selectedTasksList.concat(aTask),
        }));
    }

    /**
     * Function remove unselected task from state selectedTasksList array and change isTasksSelected flag if needed.
     * @param aTask
     */
    onUnselectTask =(aTask) => {

        let elementToRemoveIndex = -1;

        // Iterate selected tasks list:
        this.state.selectedTasksList.forEach((value, index) => {
            if (value.taskId === aTask.taskId) elementToRemoveIndex = index;
        });

        // Remove element from selected tasks list:
        if (elementToRemoveIndex > -1)
            this.state.selectedTasksList.splice(elementToRemoveIndex, 1);

        // Change flag if needed:
        if (this.state.selectedTasksList.length === 0) {
            this.tasksEditBtnStatuses = {
                addStatus: TasksEditBtnStatus.ACTIVE,
                completeStatus: TasksEditBtnStatus.DISABLED,
                removeStatus: TasksEditBtnStatus.DISABLED
            }
            this.setState({isTasksSelected: false});
        }
    }

    /**
     * Post new task object to server.
     * @param aTask - new task.
     * @return {Promise<null|*>} - task object if task is created, or null.
     */
    addUserTask =async (aTask) => {
        // Trace:
        this.LOGGER.trace("addUserTask function with task argument: [%o];", [aTask]);

        // Post new task:
        try {
            this.LOGGER.debug("Try to post new user task[%o] to server;", [aTask]);
            const result = await ReqUtilities.postRequest("/rest/task/create-task", JSON.stringify(TaskDtoBuilder.ofTask(aTask)));
            const taskDto = await result.json();

            this.LOGGER.debug("Task creation response json: [%o];", [taskDto]);
            // If exception -> return null;
            if (taskDto.exception) {
                this.LOGGER.error("Error [code: %o, message: %o] when adding new task;", [taskDto.exceptionCode, taskDto.exceptionMessage]);
                return null;
            }

            return TaskBuilder.ofDto(taskDto);
        }catch (e) {
            this.LOGGER.error(e);
            return null;
        }
    }

    /**
     * OnRemoveUserTask function calling when user click on remove button in TaskView component.
     * Function first set Loading status to removed TaskView. Then function calling removeUserTask() function to remove
     * user task from server. And based on server response remove TaskView from state taskViewPropsList.
     * @param aTaskViewProps - TaskViewProps which will be removed.
     */
    onRemoveUserTask =async(aTaskViewProps) => {
        // log:
        this.logger.log("Remove TaskView[%o]:", [aTaskViewProps]);

        // Set "loading" status to task view which will be removed:
        this.setState(prevState => {
            const newTaskViewList = prevState.taskViewPropsList.map(viewProps => {
                if (viewProps.viewId === aTaskViewProps.viewId) viewProps.loadStatus = TaskViewLoadingStatus.LOADING;
                return viewProps;
            })

            // Return new state property:
            return {taskViewPropsList: newTaskViewList};
        })

        // Remove user task from database:
        const isRemoved = await this.removeUserTask(aTaskViewProps.taskObj);

        // If task is removed:
        if (isRemoved)
            // then remove task view from list:
            this.setState(prevState => {
                return {taskViewPropsList: prevState.taskViewPropsList.filter((viewProps) => {
                    return viewProps.viewId !== aTaskViewProps.viewId;
                    })
                }
            });
        else
            // Else: log error:
            console.error(StringUtilities.format("TaskView[%o] cannot be removed.", [aTaskViewProps]));

    }

    /**
     * Remove user task.
     * Function send post http request on "rest/task/delete-task?id=" url to remove user task.
     * @param aTask - task to be removed.
     * @return {Promise<boolean>} - true if task is removed.
     */
    removeUserTask =async (aTask) => {

        try {
            this.logger.log("Try to remove user task[%o];", [aTask]);
            const result = await ReqUtilities.getRequest("/rest/task/delete-task?id=" + aTask.taskId);
            const exceptionDto = await result.json();

            this.logger.log("Task[taskId: %o] is removed - %o;", [aTask.taskId, !exceptionDto.exception]);
            return !exceptionDto.exception;
        }catch (e) {
            console.error(e);
            return false;
        }

    }

    onCompleteUserTask = async(aViewProps) => {

        // Set loading status to task view which task will be completed:
        this.setState(prevState => {
            const taskViewPropsList = prevState.taskViewPropsList.map(viewProps => {
                if (viewProps.viewId === aViewProps.viewId) viewProps.loadStatus = TaskViewLoadingStatus.LOADING;
                return viewProps;
            })
            return {taskViewPropsList: taskViewPropsList};
        });

        // Complete user task:
        const isUpdated = await this.completeUserTask(aViewProps.taskObj);

        if (isUpdated) {
            this.logger.log("Task in TaskView[%o] is not completed;", [aViewProps]);
            this.setState(prevState => {
                const taskViewPropsList = prevState.taskViewPropsList.map(viewProps => {
                    if (viewProps.viewId === aViewProps.viewId) {
                        viewProps.taskObj.taskStatus = TaskStatus.COMPLETED;
                        viewProps.loadStatus = TaskViewLoadingStatus.LOADED;
                    }
                    return viewProps;
                })
                return {taskViewPropsList: taskViewPropsList}}
            );
        }else {
            this.logger.log("Task in TaskView[%o] is not completed;", [aViewProps]);
            // Set loading status to task view which task will be completed:
            this.setState(prevState => {
                const taskViewPropsList = prevState.taskViewPropsList.map(viewProps => {
                    if (viewProps.viewId === aViewProps.viewId) viewProps.loadStatus = TaskViewLoadingStatus.LOADED;
                    return viewProps;
                })
                return {taskViewPropsList: taskViewPropsList};
            });
        }
    }

    /**
     * Complete user task.
     * @param aTask - task to be completed.
     * @return boolean - true, if task is completed.
     */
    completeUserTask = async (aTask) => {

        try {
            this.logger.log("Try to complete user task[%o];", [aTask]);
            const result = await ReqUtilities.getRequest("/rest/task/complete-task?id=" + aTask.taskId);
            const exceptionDto = await result.json();

            this.logger.log("Task[taskId: %o] is completed - %o;", [aTask.taskId, !exceptionDto.exception]);
            return !exceptionDto.exception;
        }catch (e) {
            console.error(e);
            return false;
        }

    }

    /**
     * Complete selected tasks.
     * Function calling when user click on TasksEdit "Done" button.
     */
    onCompleteUserTasks =() => {
        Logging.log("Complete user tasks: ", this.state.selectedTasksList);
        let tasksListDto = {tasksList: this.state.selectedTasksList};


        ReqUtilities.postRequest("/rest/task/complete-tasks", JSON.stringify(tasksListDto)).then((response) => {
            if(response.ok) {
                Logging.log("Response status - OK;");
                // Get JSON:
                response.json().then((tasksListRespDto) => {
                    tasksListRespDto.tasksList.forEach((taskDto) => {
                        // Set task status:
                        this.state.tasksList.forEach((taskInList) => {
                            if (taskInList.taskId === taskDto.taskId) taskInList.taskStatus = TaskStatus.COMPLETED;
                        });

                        // Remove from selected:
                        this.state.selectedTasksList.forEach((selectedTask, index) => {
                           if (selectedTask.taskId === taskDto.taskId) this.state.selectedTasksList.splice(index, 1);
                        });
                    });

                    // Update component:
                    this.forceUpdate();

                });


            }
        });

    }

    /**
     * Filter item onClick action function.
     * @param aItemType - filter item type.
     */
    onClickFilterItem =(aItemType) => {

        // Set component state:
        this.setState({
            filter_activeItem: aItemType
        })
    }

    /**
     * Check if specified filter item is active now.
     * @param aItemType - filter item type.
     * @returns {boolean} - true if filter item is active now.
     */
    isActiveFilterItem =(aItemType) => {
        return aItemType === this.state.filter_activeItem;
    }

    /**
     * Render TasksBlock element.
     * @returns {JSX.Element} - TasksBlock.
     */
    render() {

        // Object has references on task control functions:
        const taskControlFuncs = {
            addFunc: this.postNewTask,
            completeFunc: this.completeUserTask,
            updateFunc: this.updateUserTask,
            removeFunc: this.removeUserTask
        }

        // Initialize tasksToRender list based on current active filter:
        const taskViewsToRender = this.state.taskViewPropsList;

        /*
        // Render content block based on load status:
        let contentBlock;
        if(this.state.loadStatus === TasksBlockLoadStatus.LOADING) {
            contentBlock = (<TasksBlockLoader />);
        }else {
            contentBlock = (
                <TasksContentBlock showAddTaskBlock={this.state.isShowAddTaskBlock}
                                   showAddTaskBlockFunc={this.showAddTaskBlock}
                                   tasksList={this.state.tasksList} // List of users tasks;
                                   funcOnAddNewTask={this.onAddNewTask}
                                   funcOnSelectTasks={this.onSelectTasks} funcOnUnselectTask={this.onUnselectTask}
                                   taskControlFuncs={taskControlFuncs}
                                   selectedTasksList={this.state.selectedTasksList}
                                   showedTaskViewList={this.state.showedTaskViewList}
                                   taskViewsToRender={taskViewsToRender}
                                   taskControlFunctions={this.taskControlFunctions}
                />
            );
        }

         */

        // ===== Render TaskTopMenuBlock ====
        const TASKS_TOP_MENU = (
            <TasksTopMenu tasksEditBtnFunctions={this.tasksEditBtnFunctions} tasksEditBtnStatuses={this.tasksEditBtnStatuses} >
                <TasksFilterPanel>
                    <TasksFilterInfoPanel infoText={this.state.filterInfoText}/>
                    <TasksFilter>
                        <TaskFilterItem itemId={FilterItemType.TODAY} clickFunction={this.onClickFilterItem} isActive={this.isActiveFilterItem(FilterItemType.TODAY)}
                                        itemText={DateTimeUtilities.dateMonthAndDayToStr(this.state.filter_serverDate)} />
                        <TaskFilterItem itemId={FilterItemType.TOMORROW} clickFunction={this.onClickFilterItem} isActive={this.isActiveFilterItem(FilterItemType.TOMORROW)}
                                        itemText={DateTimeUtilities.dateMonthAndDayToStr(DateTimeUtilities.addDays(this.state.filter_serverDate,1))} />
                        <TaskFilterItem itemId={FilterItemType.WEEK} itemText={"+7"} clickFunction={this.onClickFilterItem}
                                        isActive={this.isActiveFilterItem(FilterItemType.WEEK)} />
                        <ListIcon id={"list_icon_1"} />
                    </TasksFilter>
                </TasksFilterPanel>
            </TasksTopMenu>
        );


        return (
            <div className={"tasks-block m-auto"} >
                {TASKS_TOP_MENU}

                <FilteredContentBlock activeFilter={TASKS_FILTER_TYPE.WEEK}
                                      tasksToRender={this.state.userTasksList}
                                      tasksControlFuncs={this.tasksControlFuncs}
                                      serverDate={this.state.filter_serverDate} />
                <TasksFooter />
            </div>
        );
    }
}

/**
 * Component used to render {TaskViews} views.
 * @param props - component props.
 * @property viewsList - list of task views props to be rendered.
 * @returns {*} - List of JSX TaskView.
 */
const TaskViewsList =(props) => {

    // Create TaskViews:
    const taskViewsList = props.viewsList.map(viewProps =>
        <TaskView key={viewProps.viewId} viewId={viewProps.viewId} task={viewProps.taskObj}
                  loadingStatus={viewProps.loadStatus} parentControlFunctions={props.taskControlFunctions} />);

    return (<div> {taskViewsList} </div>);

}

/**
 * Component props of ({TaskView}) component.
 */
export class TaskViewProps {
    /**
     * Application TaskView ID length.
     * @type {number} - id length.
     */
    static UNIQUE_ID_LENGTH = 10;

    viewId; // Task view unique ID;
    taskObj; // Wrapper task object;
    loadStatus; // View loading status;

    /**
     * Construct new {TaskView} props.
     * Constructor generate unique view ID.
     * @param aTaskObj - task object.
     * @param aLoadStatus - task view loading status.
     */
    constructor(aTaskObj, aLoadStatus) {
        this.viewId = StringUtilities.uniqueString(TaskViewProps.UNIQUE_ID_LENGTH); // Generate random id;
        this.taskObj = aTaskObj;
        this.loadStatus = aLoadStatus;
    }
}
