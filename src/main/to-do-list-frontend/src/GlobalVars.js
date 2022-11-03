/**
 * JS file has application global variables definitions.
 */

/**
 * This global variable represent application user.
 */
export class ApplicationUser {

    userId; // user ID;
    userName; // username;

    /**
     * Construct new ApplicationUser object.
     * Bind functions and initialize user properties.
     */
    constructor() {
        // Initialize variables:
        this.userId = 0;
        this.userName = "";

        // Bind functions:
        this.saveApplicationUser.bind(this);
        this.loadApplicationUser.bind(this);
    }

    /**
     * Save application user properties to client session storage.
     */
    saveApplicationUser =() => {
        // Save user id and username:
        sessionStorage.setItem("APPLICATION_USER_ID", this.userId);
        sessionStorage.setItem("APPLICATION_USER_NAME", this.userName);
    }

    /**
     * Load application user properties from client session storage.
     * @returns {ApplicationUser} - this application user.
     */
    loadApplicationUser =() => {
        // Read user id and name from client session storage:
        this.userId = sessionStorage.getItem("APPLICATION_USER_ID");
        this.userName = sessionStorage.getItem("APPLICATION_USER_NAME");
        return this;
    }

}