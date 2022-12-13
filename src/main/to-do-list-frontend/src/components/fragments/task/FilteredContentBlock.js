// noinspection JSUnresolvedVariable

import React, {useEffect, useState} from "react";
import "../../../styles/fragments/task/filtered-content-block.css"
import {LevelLogger} from "../../../js/logging/Logger";
import {DateTimeUtilities} from "../../utilities/DateTimeUtilities";
import {TaskView, TaskViewAddingBlock} from "./TaskView";
import {Localization} from "../../../js/localization/localization";
import {PlusButton} from "../../Buttons";
import {Properties} from "../../../Properites";

/**
 * All supported tasks filters.
 * @type {{WEEK: number}} - filter task at week period.
 */
export const TASKS_FILTER_TYPE = {WEEK: 0};

/**
 * FilteredContentBlock is a common component that's render task object.
 * Based on property "activeFilter" render specific filtered content.
 * @props - component props.
 * @propsProperty activeFilter - current active filter type [{TasksFilterType}];
 * @propsProperty tasksToRender - {List<Task>} - list of users tasks to be rendered.
 * @propsProperty tasksControlFuncs - {Object} - object with tasks control functions.
 *
 * @propsProperty serverDate - {Date} - today server date;
 * @propsProperty parentControlFunctions - {object} - parent control functions object.
 */
export const FilteredContentBlock =(props) => {

    const LOGGER = new LevelLogger("FilteredContentBlock", Properties.GLOBAL_LEVEL_LOGS); // Logger;
    let renderingContent; // Filtered content to be rendered:

    // Filter user task based on current active filter:
    switch (props.activeFilter) {
        case TASKS_FILTER_TYPE.WEEK: {
            // Week filtered content :
            renderingContent = <WeekFilterContent todayDate={props.serverDate}
                                                  tasksToFilter={props.tasksToRender}
                                                  tasksControlFuncs={props.tasksControlFuncs} />
            break;
        }
        default: {
            LOGGER.error("Filter type of [%O] is not supported.", [props.activeFilter]);
        }
    }

    // Render:
    return (<div className={"filtered-content-block"}> {renderingContent} </div>)

}

/**
 * Week filtered content component. Filter specified tasks ("tasksToFilter") list for week.
 * Filtered content component filter user tasks and render {FilterCategoryBlock} component with task views.
 * @param props - component props.
 * @propsProperty todayDate - today {Date} date.
 * @propsProperty tasksToFilter - {List<Task>} - tasks to be filtered and rendered;
 * @propsProperty viewPropsList - list of view props to be filtered and rendered.
 */
const WeekFilterContent =(props) => {

    // Filter categories result list:
    const weekFilterCategories = [];

    // Get only today tasks:
    const todayCategoryName = Localization.getLocalizedString("ftb_week_filter_category_name_today_pf")
        +DateTimeUtilities.dateToFormattedStr(props.todayDate, "dd.mm.yyyy");
    const todayTasks = TasksFilter.dateTasks(props.todayDate, props.tasksToFilter);
    weekFilterCategories.push(<FilterCategoryBlock key={"today_tasks"}
                                                   categoryName={todayCategoryName}
                                                   tasksToRender={todayTasks}
                                                   tasksControlFuncs={props.tasksControlFuncs}
                                                   parentControlFunctions={props.parentControlFunctions}
                                                   taskViewAddingDate={DateTimeUtilities.addDays(new Date(), 2)} />);

    // Filter on tomorrow tasks:
    const tomorrowCategoryName = Localization.getLocalizedString("ftb_week_filter_category_name_tomorrow_pf")
        +DateTimeUtilities.dateToFormattedStr(
        DateTimeUtilities.addDays(props.todayDate,1), "dd.mm.yyyy");
    const tomorrowTasks = TasksFilter.dateTasks(DateTimeUtilities.addDays(props.todayDate, 1), props.tasksToFilter);
    weekFilterCategories.push(<FilterCategoryBlock key={"tomorrow_tasks"}
                                                   categoryName={tomorrowCategoryName}
                                                   tasksToRender={tomorrowTasks}
                                                   tasksControlFuncs={props.tasksControlFuncs}
                                                   parentControlFunctions={props.parentControlFunctions} />);
    // Filter on any week tasks:
    const weekCategoryName = Localization.getLocalizedString("ftb_week_filter_category_name_week_pf")
        +DateTimeUtilities.dateToFormattedStr(DateTimeUtilities.addDays(props.todayDate,7), "dd.mm.yyyy");
    const weekTasks = TasksFilter.betweenDateTasks(
        DateTimeUtilities.addDays(props.todayDate, 2), DateTimeUtilities.addDays(props.todayDate, 7), props.tasksToFilter);
    weekFilterCategories.push(<FilterCategoryBlock key={"week_tasks"}
                                                   categoryName={weekCategoryName}
                                                   tasksToRender={weekTasks}
                                                   tasksControlFuncs={props.tasksControlFuncs}
                                                   parentControlFunctions={props.parentControlFunctions} />);

    return weekFilterCategories;


}

/**
 * Common filter category block.
 * @param props - component props.
 * @propsProperty categoryName - {String} filter category name text (user to display).
 * @propsProperty tasksToRender - {List<Task>} - list of task to be rendered.
 * @propsProperty taskViewAddingDate - {Date} - default task view adding block task completion date.
 * @return {JSX.Element} - simple filter category block.
 */
const FilterCategoryBlock =(props) => {

    //const LOGGER = new LevelLogger("FilterCategoryBlock", Properties.GLOBAL_LEVEL_LOGS);

    // State hooks:
    let [renderTasks, setRenderTasks] = useState([]); // list of tasks object to be rendered.
    let [isShowAddingBlock, setShowFlag] = useState(false);

    // Initialize render views state array:
    useEffect(() => {
        if (renderTasks.length === 0) {
            setRenderTasks(props.tasksToRender);
        }
    }, [renderTasks.length, props.tasksToRender])

    /**
     * Create new user task.
     * Function call {TaskBlock#addUserTask function}.
     * @param aNewTask - {Task} new task.
     * @return {Promise<void>}
     */
    const addNewTask =async (aNewTask) => {
        //LOGGER.debug("Add new user task: [%o];", [aNewTask]);
        const addedTask = await props.tasksControlFuncs.add(aNewTask);
        //LOGGER.debug("Created task: [%o];", [addedTask]);
        setRenderTasks([...renderTasks, addedTask]);
    }

    //LOGGER.debug("Render filter category block: \"%o\" with tasks to render: [%o]", [props.categoryName, renderTasks]);
    // Render filter category:
    return (
        <div className={"filter-category-block"} >
            <div className={"category-name"}>
                <p> {props.categoryName} </p>
                <PlusButton classes={"add-task-btn"} clickFunc={() => {setShowFlag(true)}} />
            </div>
            <TaskViewAddingBlock isShow={isShowAddingBlock} defaultCompletionDate={props.taskViewAddingDate}
                                 addFunction={(aNewTask) => addNewTask(aNewTask)} hideBlockFunction={setShowFlag}/>

            {
                // Create and render task views for tasks to be rendered:
                renderTasks.map(taskObj => { return <TaskView key={taskObj.taskId} task={taskObj} />})
            }

        </div>
    )
}

/**
 * TaskFilter static class has static functions for filter user tasks for any filtered content.
 */
class TasksFilter {

    /**
     * Filter specified list of task on specified completion date.
     * @param aDate - filter date completion value.
     * @param aTasksToFilter - list of tasks objects to be filtered.
     * @return {*} - filtered list of task view props.
     */
    static dateTasks(aDate, aTasksToFilter) {
        return aTasksToFilter.filter(taskObj => {
            return DateTimeUtilities.isDatesEquals(taskObj.taskCompletionDate, aDate);
        })
    }

    /**
     * Filter specified list of tasks by condition: if task date completion is in period between start and end date.
     * @param aStartDate - start period date.
     * @param aFinishDate - end period date.
     * @param aTasksToFilter - list of user tasks.
     * @return {*} - filtered list of user tasks.
     */
    static betweenDateTasks(aStartDate, aFinishDate, aTasksToFilter) {
        return aTasksToFilter.filter(taskObj => {
            return DateTimeUtilities.isDateInPeriod(taskObj.taskCompletionDate, aStartDate, aFinishDate);
        })
    }

}