import React from "react";

class UserView extends React.Component{

    render(){
        return (<div>
          显示用户详情
          <div>用户id:{this.props.params.userId}</div>
        </div>)
    }
}

export default UserView;