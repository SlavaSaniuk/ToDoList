class ReqUtilities {

    static absoluteUrl(relativeUrl) {
        return sessionStorage.getItem('SERVER_URL') +relativeUrl;
    }

    static restRequestHeaders(jwt) {
        return {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': jwt
        };
    }

    static sessionAuthRestRequestHeaders() {
        return ReqUtilities.restRequestHeaders(sessionStorage.getItem('JWT'));
    }

    static getReqParams(headers) {
        return {method: 'GET', headers: headers};
    }

    static postReqParams(headers, body) {
        return {method: 'POST', headers: headers, body: body};
    }


    static async getRequest(path) {
        return await fetch(ReqUtilities.absoluteUrl(path), ReqUtilities.getReqParams(ReqUtilities.sessionAuthRestRequestHeaders()));
    }

    static async postRequest(path, body) {
        return await fetch(ReqUtilities.absoluteUrl(path), ReqUtilities.postReqParams(ReqUtilities.sessionAuthRestRequestHeaders(), body));
    }

}

export {ReqUtilities}