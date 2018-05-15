import {SubEmiter,Emiter} from 'util';
import {Carousel} from "antd";
import React from "react";

class UCarousel extends React.Component{
 
  fireplay = () =>{
     this.refs.carousel.refs.slick.play();
  }
  render (){
      return   (
          <SubEmiter  eventName={this.props.eventName || "fireplay"} listener={this.props.listener || this.fireplay}>
             <Carousel {...this.props} ref="carousel">
                 {this.props.children || null}
             </Carousel>
        </SubEmiter>
      )
  }
}
export {Emiter};
export default UCarousel;