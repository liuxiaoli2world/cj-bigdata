import React from "react";
import styled from "styled-components";

const Bar = styled.div`
    position: relative;
    border-bottom: 1px solid #ccc;
    .triangleBox {
        width: 0;
        height: 0;
        border-bottom: 22px solid #eee;
        border-right: 22px solid transparent;
        .triangle {
            padding-top:11px;
            width: 0;
            height: 0;
            border-bottom: 11px solid #ccc;
            border-right: 11px solid transparent;
        }
    }
    .title{
        position:absolute;
        left: 32px;
        bottom: 0;
        font-size:14px;
        color:#369;
        font-weight:bold;
    }
`;

export default class TitleBar extends React.Component{
	constructor(props){
		super(props);
	}

	render(){
		return(
			<Bar>
                <div className="triangleBox">
                    <div className="triangle"></div>
                </div>
                <div className="title">{this.props.title}</div>
			</Bar>
		)
	}
}