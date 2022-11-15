// noinspection JSUnresolvedVariable

import React from "react";
import "../../../styles/fragments/task/filtered-content-block.css"
import {Logger} from "../../../js/logging/Logger";
import {DateTimeUtilities} from "../../utilities/DateTimeUtilities";
import {TaskView} from "./TaskView";

/**
 * @propsProperty activeFilter - current active filter type [{TasksFilterType}];
 * @propsProperty taskViewPropsList - list of task view properties [{TaskViewProps}];
 */
export class FilteredContentBlock extends React.Component {

    // Logger:
    LOGGER = new Logger("FilteredContentBlock", true);

    constructor(props) {
        super(props);
    }

    render() {

        // Filter user task based on current active filter:
        let renderingContent =[]; // TaskView to be rendered:
        switch (this.props.activeFilter) {
            case TasksFilterType.WEEK: {
                // Filter today tasks:
                const todayTasks = TasksFilter.weekFilterTodayTasks(new Date(), this.props.taskViewPropsList);
                renderingContent.push(<FilterCategoryBlock categoryName={"Today, 15.11"} categoryViews={todayTasks}  /> );
                break;
            }
            default: {

            }

        }



        return (
            <div className={"filtered-content-block"}>
                {renderingContent}
            </div>
        )
    }


}

/**
 *
 * @param props - component props.
 * @propsProperty categoryName - name of filter category.
 * @propsProperty categoryViews - list of category views.
 * @return {JSX.Element}
 */
const FilterCategoryBlock =(props) => {

    const views = props.categoryViews.map(viewProps => {
        return <TaskView key={viewProps.viewId} viewId={viewProps.viewId} task={viewProps.taskObj}
                  loadingStatus={viewProps.loadStatus} />
    })

    return (
        <div className={"filter-category-block"}>
            <p> {props.categoryName} </p>
            {views}
        </div>
    )
}

class TasksFilter {

    static weekFilterTodayTasks(aToday, aListViewProps) {
        return aListViewProps.filter(viewProps => {
            console.log("!!! aToday: ", aToday);
            console.log("!!! aViewProps: ", viewProps);
            return DateTimeUtilities.isDayEquals(viewProps.taskObj.taskCompletionDate, aToday);
        })
    }

}

export const TasksFilterType = {WEEK: 0, ALL: 1};