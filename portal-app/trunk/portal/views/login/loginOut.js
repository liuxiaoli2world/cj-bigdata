import React from 'react';
import {Link,hashHistory} from 'react-router';

export default class loginOut extends React.Component{
    constructor(props){
        super(props);
    }

    componentDidMount(){
        window.localStorage.clear();
        hashHistory.push("/login");
    }

    render() {
        return  (
            <div></div>
    )

    }

}