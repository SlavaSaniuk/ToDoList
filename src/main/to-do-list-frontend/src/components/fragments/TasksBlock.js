import React from "react";
import '../../styles/common.css'
import '../../styles/fragments/TasksBlock.css'
import {TaskComponent} from "./Task";
import {AddTaskBlock, TaskAddition} from "./AddTaskBlock";
import {ReqUtilities} from "../utilities/ReqUtilities";
import {TaskStatus} from "../dto/TaskDto";
import {CheckmarkButton, CrossButton, PlusButton} from "../Buttons";
import {Logging} from "../../js/utils/Logging";
import {DateTimeUtilities} from "../utilities/DateTimeUtilities";
import {Menu, MenuDirection, MenuItem} from "../ui/Menu";

const TasksFooter =() => {
    return(<div className={"tasks-footer"}>
        <p className={"tasks-footer-text"}> LOAD MORE ... </p>
    </div>);
}

const Task =(props) => <TaskComponent
    taskObj={props.task} key={props.task.taskId}
    funcOnSelectTasks={props.funcOnSelectTasks} funcOnUnselectTask={props.funcOnUnselectTask}
    taskControlFuncs={props.taskControlFuncs} taskIsSelected={props.isTasksSelected}
/>


/**
 * This element render list of users tasks.
 * @property tasksList - list of users tasks.
 * @property funcOnSelectTask - parent function on select task action.
 * @property funcOnUnselectTask - parent function on unselect task action.
 * @property taskControlFuncs - task control function in object.
 */
class TasksList extends React.Component {
    /**
     * Construct new TaskList component.
     * @param props
     */
    constructor(props) {
        super(props);

        // Bind functions:
        this.isTaskSelected.bind(this);
    }

    /**
     * Check if current 'aTask' task is selected.
     * Function iterate props selectedTaskList and if 'aTask' is in the list - return true.
     * @param aTask - task obj.
     * @returns {boolean} - true - if task is selected.
     */
    isTaskSelected =(aTask) => {
        let isSelected = false;

        // Check if task in selected tasks list:
        this.props.selectedTasksList.forEach(selectedTask => {
            if(selectedTask.taskId === aTask.taskId) isSelected=true;
        })

        return isSelected;
    }

    /**
     * Render tasks list.
     * @returns {JSX.Element}
     */
    render() {
        const tasks = this.props.tasksList.map((task) =>

            <Task task={task} taskName={task.taskName} taskId={task.taskId} taskDesc={task.taskDesc} key={task.taskId}
                  funcOnSelectTasks={this.props.funcOnSelectTasks} funcOnUnselectTask={this.props.funcOnUnselectTask}
                  taskControlFuncs={this.props.taskControlFuncs} isTasksSelected={this.isTaskSelected(task)}
            />
        );

        return (
            <div>
                {tasks}
            </div>
        )
    }
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
 */
class TasksContentBlock extends React.Component {

    /**
     * Render TaskContentBlock element.
     * @returns {JSX.Element} - html.
     */
    render() {
        return(
            <div>
                <TaskAddition isShow={this.props.showAddTaskBlock} at_appearanceFunc={this.props.showAddTaskBlockFunc} at_addTaskFunction={this.props.taskControlFuncs.addFunc} />

                <TasksList tasksList={this.props.tasksList}
                           funcOnSelectTasks={this.props.funcOnSelectTasks} funcOnUnselectTask={this.props.funcOnUnselectTask}
                           taskControlFuncs={this.props.taskControlFuncs}
                           selectedTasksList={this.props.selectedTasksList}
                />
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

const TasksBlockLoader =() => {
    return (
        <div className={"tasks-block-loading-block row"}>
            <div className={"tasks-block-loader"} />
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
class TasksBlock extends React.Component {
    // Class variables:
    tasksEditBtnStatuses; // Statuses of TasksEdit buttons;
    tasksEditBtnFunctions; // TasksEdit buttons onClick action functions:

    constructor(props) {
        super(props);

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
        this.showAddTaskBlock.bind(this);
        this.onAddNewTask.bind(this);
        this.onRemoveTasks.bind(this);
        this.onRemoveTask.bind(this);
        this.postNewTask.bind(this);
        this.onSelectTasks.bind(this);
        this.onUnselectTask.bind(this);
        this.removeUserTask.bind(this);
        this.updateUserTask.bind(this);
        this.completeUserTask.bind(this);
        this.onCompleteUserTasks.bind(this);
        this.onClickFilterItem.bind(this);
        this.isActiveFilterItem.bind(this);

        // Element state:
        this.state = {
            loadStatus: TasksBlockLoadStatus.LOADING, // Default load status (because need to load user tasks list);
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
            filter_activeItem: FilterItemType.TODAY, // By default, filter "TODAY" tab is active:
            filterInfoText: ""
        };
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
                    Logging.log("Server date: ", DateTimeUtilities.dateToStr(serverDate));
                    this.setState({
                        filter_serverDate: serverDate
                    })
                })
            }
        });

        // Set component state:
        this.setState({
           // filter_serverDate: serverDate,
           // filter_serverDate: serverDate,
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

            // Set state:
            this.setState(prevState => ({
                tasksList: prevState.tasksList.concat(resultTasksList),
                loadStatus: TasksBlockLoadStatus.LOADED
            }));
        })


    }

    /**
     * Function rerender TasksContentBlock to show AddTaskBlock element.
     * @param isShow - Boolean - true to show tasks block.
     */
    showAddTaskBlock =(isShow) => {
        this.setState({isShowAddTaskBlock: isShow})
    }

    /**
     * Add new users task to tasks list in element state.
     * @param aTask - task to add.
     */
    onAddNewTask =(aTask) => {
        // Post task to server:
        this.postNewTask(aTask).then((createdTask) => {
            // Add task to list in state and hide AddTaskBlock:
            this.setState(prevState => ({
            tasksList:  [createdTask, ...prevState.tasksList],
            isShowAddTaskBlock: false
        }));
        });
    }

    /**
     * Remove selected tasks.
     * Function calling removeTask function and update state tasks list.
     */
    onRemoveTasks =() => {
        this.state.selectedTasksList.forEach(aTask => {
            this.onRemoveTask(aTask, false);
        })

        this.setState({
            isTasksSelected: false,
            selectedTasksList: []
            }
        );
    }

    /**
     * Remove specified task.
     * @param aTask - task to remove.
     */
    onRemoveTask =(aTask) => {
        this.removeUserTask(aTask)
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
     * @param aTask - task to post.
     * @returns {Promise<any>} - created task.
     */
    postNewTask = async(aTask) => {
        // Post request:
        let response = await fetch( sessionStorage.getItem('SERVER_URL') +"/rest/task/create-task", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Authorization': sessionStorage.getItem('JWT')},
            body: JSON.stringify(aTask)
            });

        // Check task object:
        let taskDto = await response.json();
        if (taskDto.exception) {
            console.log("Exception when post new task occurs. ExceptionDto: ", taskDto);
            return;
        }

        // Return task object:
        return taskDto;
    }

    /**
     * Remove user task.
     * Function send http get request on url /rest/task/delete-task?id=taskId. Then remove task from state tasks list.
     * @param aTask - Task object with ID.
     * @returns {Promise<void>} - promise.
     */
    removeUserTask =async (aTask) => {
        const promise = await ReqUtilities.getRequest("/rest/task/delete-task?id=" + aTask.taskId);
        promise.json().then((exceptionDto => {
            if (exceptionDto.exception === false) {
                this.setState(prevState => ({
                    tasksList:  prevState.tasksList.filter((task) => {
                        return task.taskId !== aTask.taskId;
                    }),
                }));
            }
        }));

    }

    updateUserTask = async (aModifiedTask) => {

        const promise = await ReqUtilities.postRequest("/rest/task/update-task", JSON.stringify(aModifiedTask));
        promise.json().then((taskRestDto) => {
            if (taskRestDto.exception === false) {
                console.log("Modified task: ", taskRestDto);
            }
        })
    }

    /**
     * Complete user task.
     * @param aTask - task to be completed.
     * @returns {Promise<void>}
     */
    completeUserTask = (aTask) => {
        ReqUtilities.getRequest("/rest/task/complete-task?id=" + aTask.taskId).then((response) =>
            response.json().then((exceptionDto => {
                if (exceptionDto.exception === false) {
                    this.state.tasksList.forEach((task) => {
                        if (task.taskId === aTask.taskId) {
                            task.taskStatus = TaskStatus.COMPLETED;
                            this.forceUpdate();
                        }
                    })
                }
            })));
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
                />
            );
        }

        // ===== Render TaskTopMenuBlock ====
        const TASKS_TOP_MENU = (
            <TasksTopMenu tasksEditBtnFunctions={this.tasksEditBtnFunctions} tasksEditBtnStatuses={this.tasksEditBtnStatuses} >
                <TasksFilterPanel>
                    <TasksFilterInfoPanel infoText={this.state.filterInfoText}/>
                    <TasksFilter>
                        <TaskFilterItem itemId={FilterItemType.ALL} itemText={"ALL"} clickFunction={this.onClickFilterItem}
                                        isActive={this.isActiveFilterItem(FilterItemType.ALL)} />
                        <TaskFilterItem itemId={FilterItemType.TODAY} clickFunction={this.onClickFilterItem} isActive={this.isActiveFilterItem(FilterItemType.TODAY)}
                                        itemText={DateTimeUtilities.dateMonthAndDayToStr(this.state.filter_serverDate)} />
                        <TaskFilterItem itemId={FilterItemType.TOMORROW} clickFunction={this.onClickFilterItem} isActive={this.isActiveFilterItem(FilterItemType.TOMORROW)}
                                        itemText={DateTimeUtilities.dateMonthAndDayToStr(DateTimeUtilities.addDays(this.state.filter_serverDate,1))} />
                        <TaskFilterItem itemId={FilterItemType.WEEK} itemText={"+7"} clickFunction={this.onClickFilterItem}
                                        isActive={this.isActiveFilterItem(FilterItemType.WEEK)} />
                    </TasksFilter>
                </TasksFilterPanel>
            </TasksTopMenu>
        );


        return (
            <div className={"tasks-block m-auto"} >
                {TASKS_TOP_MENU}

                {contentBlock}
                <TasksFooter />
            </div>
        );
    }
}

export {TasksBlock}