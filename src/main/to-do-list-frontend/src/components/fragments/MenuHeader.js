import React from "react";
import '../../styles/fragments/MenuHeader.css'

/**
 * Single menu category.
 * @property id - id of menu category.
 * @property categoryName - name of category.
 * @property userObj - user object.
 */
class MenuCategory extends React.Component {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <div id={this.props.id} className={"menu-category"}> {this.props.categoryName} </div>
        )
    }
}

/**
 * Menu content block positioned at center page and render menu categories.
 * @property userObj - user object.
 */
class MenuHeaderContent extends React.Component {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <div className={"menu-header-content"}>
                <MenuCategory />
                <MenuCategory />
                <MenuCategory id={"menu-category-user"} userObj={this.props.userObj} categoryName={this.props.userObj.userName} />
            </div>
        )
    }
}

/**
 * Top menu block. Method render MenuHeaderContent block.
 * @property userObj - user object.
 */
class MenuHeaderBlock extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <header className={"menu-header-block"}>
                <MenuHeaderContent userObj={this.props.userObj}/>
            </header>
        )
    }
}

/**
 *  MenuHeader react element is the root of top menu.
 *  @property userObj - user object.
 */
class MenuHeader extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return(
            <MenuHeaderBlock userObj={this.props.userObj} />
        );
    }
}

export default MenuHeader;