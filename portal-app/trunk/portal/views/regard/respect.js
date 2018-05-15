import React from 'react';
import {Link,hashHistory} from 'react-router';
import {Icon,Pagination,Breadcrumb} from 'antd';
import Wrapper from './Wrapper.js';
import { autobind } from 'core-decorators';
import {resizeEvent} from 'util/carousel-helper';
import {get,post} from 'util/request.js';

export default class Respect extends React.Component{
	constructor(props){
		super(props);
		this.state={
		   list:[
               {title:'数据库功能：',body1:'采用dbms的数据库管理；',body2:'基于mvc框架进行数据之间的交互呈现'},
               {title:'热点问题大型资讯专题：',body1:'权威的专题资源库；',body2:'资源深度文本挖掘'},
               {title:'个人定制服务：',body1:'个性化精准在线服务；       ',body2:'用户上网定制图书馆'},
               {title:'长江委专家沙龙：',body1:'最新专家资讯和行业动态；',body2:'专家在线互动、用户实时交流'},
               {title:'在线投稿功能：',body1:'采取高效跨媒介的b/s系统结构；',body2:'实时自主性的稿件管理'}
           ]
		}
	}

	componentDidMount(){
		
	}

	componentDidUpdate(){
		
	}
	render(){
        const {list}=this.state
		return(
			<Wrapper>
				<img src={require('assets/images/icon10.png')}/> >>
                <p style={{textIndent:'2em',marginTop:35,lineHeight:2}}>
                    作为世界第三、中国第一大江河，长江不仅是中华文明的摇篮之一，也是中国经济社会可持续发展的重要命脉。长江流域国土面积180万平方公里，不到全国1/5，但涉及19个省(直辖市、自治区)，汇集了超过全国1/3的人口、城市，生产了全国1/3的粮食，创造了全国1/3的GDP，提供了全国36.5%的水资源、48％的可开发水能资源、52.5%的内河通航里程、丰富多样的水生生物，是南水北调东、中、西三条线路等中国水资源配置的战略水源地、中国水电开发的主要基地、连接东中西部的“黄金水道”、珍稀水生生物的天然宝库，在中国经济社会发展中发挥着巨大的作用。
                </p>
                <p style={{textIndent:'2em',marginTop:35,lineHeight:2}}>
                     水利事业是一项特殊的事业。一方面，需要不断引进运用科学技术的新理论、新方法、新工艺；另一方面，又经常需要借鉴历史的经验，参照以往传统的做法，尤其是在紧急处理突发性的事件、解决影响因素复杂的问题、作出重大宏观问题决策等情况下，更是如此。长江治理开发保护涉及领域广泛、覆盖地域辽阔、影响因素复杂，因此，治江工作更需要依靠老专家的经验，借鉴以往成功的做法，参考历史的资料信息。为了抢救这一资源，尽快将健在的老同志所经历过的重大事件、记忆中的成功案例、领悟到的决策思想、感受到的经验教训，发掘整理出来，永久保存下去，长江出版社运用现代网络技术，将这些口碑资料同各种档案资料、文献资料数字化，建立起三维、实时、可视化重现的历史信息系统——长江大数据，为中国了解长江提供专业、方便、快捷的服务与支持。
                </p>
                <ul className="respect_list">
                    {
                        list && list.map((item,i)=>{
                            return(
                               <li className="respect_list_item" key={i}>
                                    <img src={require('assets/images/icon11.png')}/>
                                    <p style={{fontSize:16,color:'#666'}}>{item.title}</p>
                                    <p style={{fontSize:12,color:'#999'}}>{item.body1}</p>
                                    <p style={{fontSize:12,color:'#999'}}>{item.body2}</p>
                                </li> 
                            )
                        })
                    }
                </ul>
                <img src={require('assets/images/icon12.png')}/> >>
                <p style={{textIndent:'2em',marginTop:35,lineHeight:2}}>
                    长江出版社成立于2004年5月，是一家年轻而又富有朝气的出版社，是一个拼搏、创新、严谨、团结的文化团体。长江出版社的主管主办单位长江水利委员会是水利部在长江流域行使行政管理职能的派出机构，拥有近3万职工，其中包括院士、大师在内的技术人员达7500人，横跨100多个学科。基于长江委在治江实践中的特殊地位和取得的巨大成就，对长江治理开发保护的总结研究就是对相关研究领域最高成果的展示，是我国水利事业取得的巨大成就的展示。长江出版社成立以来，始终以“凝聚长江智慧，传承长江文明”为宗旨，以推动治江观念创新、理论创新为己任，不断推出学术含量高、文化品位上乘的各类图书，受到社会各界的关注，为积极推进治江思路的进一步调整、丰富和完善，推动治江事业向更新更高的层次迈进作出贡献。在全国经营性出版单位等级评估中，年轻的长江出版社以其良好的经营业绩和发展态势被评为二级出版社。社长别道玉同志入选全国新闻出版行业领军人才。在出版的1000多种图书中，有100多种图书获得国家、省部级奖励和资助。长江出版社依托长江水利委员会、面向全社会，在专业出版、特色出版上下工夫，凸显了“长江”特色，为弘扬伟大的治江事业、弘扬长江文化，作出了显著贡献。
                </p>
			</Wrapper>
		)
	}
}