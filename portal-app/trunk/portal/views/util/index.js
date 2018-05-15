import {Subscribe,SubscribeDOM,Timer} from 'react-subscribe';
import React from "react";
import {EventEmitter} from "fbemitter";

let  Emiter = new EventEmitter();

class SubEmiter extends React.Component{
  

    render(){
        return (<Subscribe target={Emiter} eventName={this.props.eventName} listener={this.props.listener}>
                 {this.props.children|| null}
            </Subscribe>)
    }
}

export{
    SubEmiter,
    SubscribeDOM,
    Timer,
    Emiter
}