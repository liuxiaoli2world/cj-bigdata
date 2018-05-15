import React from "react";
import TabBox from '../components/TabBox';
import {get,post} from "util/request.js";

export default class Aside extends React.Component{
    constructor(props){
        super(props);
        this.state={
            sugInfo:[],
            sugExpert:[]
        }
    }

    componentDidMount() {
        get(`/book/content/queryAll?pageNum=1&pageSize=5&flag=headline`)
            .then(res => {
                if(res && res.data ){
				    this.setState({
					    sugInfo:res.data.list || []
			    	})
			    }
            })
        get(`/expert/expert/selectRecommendExpertAndComposition`)
            .then(res => {
                if(res.meta.success){
                    this.setState({
                        sugExpert: res.data
                    })
                }
            })
    }

    render(){
        const {sugInfo,sugExpert} = this.state;
        return (
            <div className="aside left">
                <TabBox title={'推荐资讯'} items={sugInfo} mode={'normal'} linkurl={`/information/headline/detail/`}/>
                <TabBox title={'推荐学者'} items={sugExpert} mode={'tag'} />
            </div>
        )
    }
}