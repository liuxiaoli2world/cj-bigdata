import React from "react";


class User extends React.Component{

   
    render(){
        return (<div>
          	用户的默认页面
          <div>{this.props.children}</div>
        </div>)
    };;;
}

export default User;