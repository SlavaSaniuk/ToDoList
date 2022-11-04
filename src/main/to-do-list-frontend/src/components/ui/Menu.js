import React from "react";
import '../../styles/ui/menu.css'

/**
 * Menu component is wrapper for {MenuItem} menu items.
 * If "menuDirection" props is not sets then menu will be vertical.
 * @param props - component props.
 * ============= Component props ==============
 * @property menuDirection - {MenuDirection} direction constant;
 * @property menuClass - menu wrapper div classnames;
 * @property children - JSX menu items.
 * @returns {JSX.Element} - menu wrapper div.
 */
export const Menu =(props) => {
    // Menu direction classname:
    let menu_direction_class = props.menuDirection === MenuDirection.HORIZONTAL ? "menu-horizontal" : "menu-vertical";

    return (<div className={menu_direction_class +" " +props.menuClass}>
        {props.children}
    </div>)
}

/**
 * Menu directions constants.
 * @field HORIZONTAL (0) - horizontal menu direction.
 * @field VERTICAL (1) - vertical menu direction.
 */
export const MenuDirection = {HORIZONTAL: 0, VERTICAL: 1}

/**
 * Single menu item component.
 * @param props - component props.
 * =========== Component props =============
 * @property itemId - item identification;
 * @property itemText - menu item text;
 * @property itemClass - menu item div classname;
 * @property itemTextClass - menu item inner p classname;
 * @property clickFunction - onClick action function;
 * @returns {JSX.Element} - menu item component.
 */
export class MenuItem extends React.Component {
    constructor(props) {
        super(props);

        // Bind functions:
        this.onClick.bind(this);
    }

    // ========= FUNCTIONS ===========
    onClick =() => {
        this.props.clickFunction(this.props.itemId);
    }

    render() {
        return (
            <div id={this.props.itemId} className={this.props.itemClass +" menu-item"} onClick={this.onClick}>
                <p className={"menu-item-p " +this.props.itemTextClass}> {this.props.itemText} </p>
            </div>
        );
    }



}